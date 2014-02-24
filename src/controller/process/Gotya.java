package controller.process;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;

import org.ictclas4j.bean.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import controller.spider.PicInfo;
import controller.spider.InsertKeywordToSql;
import controller.spider.Reghtml;
import controller.spider.Wiki;
import database.initial.Factory;
import database.keyword.SqlKeyword;
import database.original.Original;
import database.original.SqlOriginal;
import database.related.SqlRelated;
import database.similar.SqlSimilar;

public class Gotya {
	public Gotya() {

	}

	// 本地上传图片
	public int startGotya(byte[] content, String filename,
			boolean localFirstSearch) {
		int oid = 0;
		int keywordSize = 0;
		try {
			/**
			 * @step 1 前台图片信息转化为流，上传到服务器
			 */
			// 获取当前时间
			double mills1 = new Date().getTime();
			// 上传文件
			Upload u = new Upload(content, filename);
			String uploadDate = u.uploadFile();
			// 将图片信息写入数据库
			Original original = Factory.getInstance().initOriginal();
			SqlOriginal sqlOriginal = Factory.getInstance().initSqlOriginal();

			original.setMD5(content);
			if (!sqlOriginal.checkIfExist(original)) {
				// 数据库中不存在该图片
				original.setCreateDate(uploadDate);
				sqlOriginal.insertOriginal(original);
				oid = sqlOriginal.getNewOID();
			} else {
				// 数据库中已存在该图片
				original.setLastestDate(uploadDate);
				sqlOriginal.updateOriginal(original);

				if (localFirstSearch == true) {
					// 若开启本地优先搜索（极速搜索），直接返回给前台
					oid = sqlOriginal.getOID();
					String res1 = "****极速搜索完成，从本地返回结果！OID=" + oid + "\n";
					System.out.println(res1);
					Log.print(res1);
					return oid;
				} else {
					// 反之，则进行网络优先搜索（普通搜索）
					keywordSize = sqlOriginal.getKeywordSize();
				}
			}

			/**
			 * 上传图片路径
			 */
			// 3G、Linux部署测试用
			String srcurl = GotyaConst.uploadInternetPath + u.getFileName();
			// 本地测试用
			// String srcurl = GotyaConst.uploadTestInternetPath;

			/**
			 * @step 2 第一次爬虫抓取
			 */
			// 获取当前时间
			double mills2 = new Date().getTime();
			// 抓取相似图像基本信息
			Spider s = new Spider(srcurl);
			ArrayList<PicInfo> picList = s.getPicInfo();
			if (picList.size() == 0) {
				/** 错误1：一次搜索没有返回结果 **/
				Log.printErr("搜索完成，结果返回-1。一次搜索没有返回结果。");
				return -1;
			}

			/**
			 * @step 3 第二次爬虫抓取
			 */
			// 抓取相似图像上下文
			DeepSpider ds = new DeepSpider(picList);
			picList = ds.getPicDeepSpider();
			if (picList.size() == 0) {
				/** 错误2：二次搜索没有返回结果 **/
				Log.printErr("搜索完成，结果返回-2。二次搜索没有返回结果。");
				return -2;
			}

			/**
			 * @step 4 分词、除噪处理（多线程并行调度）
			 */
			// 获取当前时间
			double mills3 = new Date().getTime();
			// 进行分词处理
			Segment sm = new Segment(picList);
			ArrayList<Data> picWords = sm.getWeightList();
			if (picWords.size() == 0) {
				/** 错误3：分词没有返回结果 **/
				Log.printErr("搜索完成，结果返回-3。分词没有返回结果。");
				return -3;
			}

			// 普通搜索时出现新结果更好的情况，更新数据库
			if (localFirstSearch == false) {
				if (picWords.size() < keywordSize) {
					sqlOriginal.copyOriginal();
					sqlOriginal.invalidMD5();
					oid = sqlOriginal.getNewOID();
				} else {
					String res3 = "****普通搜索完成，从本地返回更好结果！OID=" + oid + "\n";
					System.out.println(res3);
					Log.print(res3);
					return oid;
				}
			}

			// 新开一个线程写入
			Thread threadKeyword = new Thread(new InsertKeywordToSql(oid,
					picWords));
			threadKeyword.start();

			// 将爬虫抓取的相似图片信息（含上下文）写入数据库
			SqlSimilar sqlSimilar = Factory.getInstance().initSqlSimilar();
			sqlSimilar.setPicList(oid, picList);

			/**
			 * @step 5 对分词结果进行搜索得出最后结果
			 */
			// 获取当前时间
			double mills4 = new Date().getTime();
			// 抓取相关图像
			Recognize rcn = new Recognize(picWords);
			ArrayList<PicInfo> picRcn = rcn.getPicRcn();
			if (picRcn.size() == 0) {
				/** 错误4：三次搜索没有返回结果 **/
				Log.printErr("搜索完成，结果返回-4。三次搜索没有返回结果。");
				return -4;
			}

			// 将爬虫抓取的相关图片信息写入数据库
			SqlRelated sqlRelated = Factory.getInstance().initSqlRelated();
			sqlRelated.setPicList(oid, picRcn);
			// 将本次搜索的一次、二次图片总数和关键词总数写入数据库
			original.setSimilarPicSize(picList.size());
			original.setRelatedPicSize(picRcn.size());
			original.setKeywordSize(picWords.size());
			original.setFilterKeywordSize(rcn.getFilterKeyword().size());
			sqlOriginal.updateSize(original);

			// 等待子进程结束
			threadKeyword.join();

			// 获取结束时间
			double mills5 = new Date().getTime();
			// 将时间数据写入日志
			String result = "Spider use: " + (mills2 - mills1) / 1000 + "s"
					+ "\n" + "DeepSpider use: " + (mills3 - mills2) / 1000
					+ "s" + "\n" + "Segment use: " + (mills4 - mills3) / 1000
					+ "s" + "\n" + "Recognize use: " + (mills5 - mills4) / 1000
					+ "s" + "\n" + "Totally use: " + (mills5 - mills1) / 1000
					+ "s";
			System.out.println(result);
			Log.print(result);

			if (localFirstSearch == true) {
				String res2 = "****极速搜索完成，从网络返回结果！OID=" + oid + "\n";
				System.out.println(res2);
				Log.print(res2);
			} else {
				String res4 = "****普通搜索完成，从网络返回更好结果，并更新本地数据！OID=" + oid + "\n";
				System.out.println(res4);
				Log.print(res4);
			}

			/**
			 * @step 6 发送消息给前台
			 */
			Log.printErr("搜索完成，结果返回" + oid + "。");
			return oid;

		} catch (Exception e) {
			e.printStackTrace();
			Log.printErr(e);
		}
		/** 错误5：抛出异常，请检查 **/
		Log.printErr("搜索完成，结果返回-5。抛出异常，请检查。");
		return -5;
	}

	// URL提供图片
	public int startGotya2(String _url, boolean localFirstSearch) {
		int oid = 0;
		try {
			URL url = new URL(_url);
			int size = 0;
			HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
			// 根据响应获取文件大小
			size = urlcon.getContentLength();

			byte[] buffer = new byte[size];
			String filename = new String();
			int i = _url.lastIndexOf('/');
			if (i != -1) {
				filename = _url.substring(i + 1).toLowerCase();
			} else {
				System.out.println("URL图片地址出错。");
				Log.print("URL图片地址出错。");
				return -6;
			}
			InputStream is = url.openStream();
			is.read(buffer, 0, size);

			oid = startGotya(buffer, filename, localFirstSearch);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oid;
	}

	// 文字搜图
	public ArrayList<PicInfo> startGotya3(String _word) {
		ArrayList<PicInfo> picRcn = new ArrayList<PicInfo>();
		Document doc = null;
		doc = tryConnect(doc, GotyaConst.trySpider, _word);
		String result = new String();
		if (doc != null) {
			result = "文字搜索图像爬取成功！";

			String webSource = doc.toString();
			picRcn = Reghtml.recognizeText(webSource);
		} else {
			result = "文字搜索图像没有结果，请检查分词结果是否有误。";
		}
		System.out.println(result);
		Log.print(result);

		return picRcn;
	}

	public Document tryConnect(Document doc, int tryNum, String searchWords) {
		try {
			doc = Jsoup.connect(
					GotyaConst.wordSearchPath
							+ URLEncoder.encode(searchWords, "gb2312") + "&pn="
							+ GotyaConst.rcnPicIndex + "&rn="
							+ GotyaConst.rcnPicNum).get();
		} catch (Exception e) {
			System.out.println("文字搜索图像爬取失败"
					+ (GotyaConst.trySpider - tryNum + 1) + "。");
			Log.print("文字搜索图像爬取失败" + (GotyaConst.trySpider - tryNum + 1) + "。");
			Log.printErr(e);

			if (tryNum > 0) {
				doc = tryConnect(doc, tryNum - 1, searchWords);
			}
		}
		return doc;
	}

	// 前台在获取返回的oid后，调用下面三个方法进行数据的获取
	public ArrayList<PicInfo> getSimilar(int oid) {
		SqlSimilar sqlSimilar = Factory.getInstance().initSqlSimilar();
		return sqlSimilar.getPicList(oid);
	}

	public ArrayList<PicInfo> getRelated(int oid) {
		SqlRelated sqlRelated = Factory.getInstance().initSqlRelated();
		return sqlRelated.getPicList(oid);
	}

	public ArrayList<Data> getKeyword(int oid) {
		SqlKeyword sqlKeyword = Factory.getInstance().initSqlKeyword();
		Thread sleep = new Thread();
		try {
			sleep.sleep(300);
			sleep.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			Log.printErr(e);
		}
		return sqlKeyword.getKeyword(oid);
	}

	public int getFilterKeywordSize(int oid) {
		int i = 0;
		SqlOriginal sqlOriginal = Factory.getInstance().initSqlOriginal();
		i = sqlOriginal.searchFilterKeywordSize(oid);
		return i;
	}

	public boolean checkInvitationCode(String s) {
		return Invite.check(s);
	}

	public String getWiki(String word) {
		Wiki phrase = new Wiki(word);
		String wikiUrl = Log.createWikiHtml(phrase.getHtml(), database.md5.MD5
				.toMD5(word.getBytes()));
		return wikiUrl;
	}
}
