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

	// �����ϴ�ͼƬ
	public int startGotya(byte[] content, String filename,
			boolean localFirstSearch) {
		int oid = 0;
		int keywordSize = 0;
		try {
			/**
			 * @step 1 ǰ̨ͼƬ��Ϣת��Ϊ�����ϴ���������
			 */
			// ��ȡ��ǰʱ��
			double mills1 = new Date().getTime();
			// �ϴ��ļ�
			Upload u = new Upload(content, filename);
			String uploadDate = u.uploadFile();
			// ��ͼƬ��Ϣд�����ݿ�
			Original original = Factory.getInstance().initOriginal();
			SqlOriginal sqlOriginal = Factory.getInstance().initSqlOriginal();

			original.setMD5(content);
			if (!sqlOriginal.checkIfExist(original)) {
				// ���ݿ��в����ڸ�ͼƬ
				original.setCreateDate(uploadDate);
				sqlOriginal.insertOriginal(original);
				oid = sqlOriginal.getNewOID();
			} else {
				// ���ݿ����Ѵ��ڸ�ͼƬ
				original.setLastestDate(uploadDate);
				sqlOriginal.updateOriginal(original);

				if (localFirstSearch == true) {
					// ��������������������������������ֱ�ӷ��ظ�ǰ̨
					oid = sqlOriginal.getOID();
					String res1 = "****����������ɣ��ӱ��ط��ؽ����OID=" + oid + "\n";
					System.out.println(res1);
					Log.print(res1);
					return oid;
				} else {
					// ��֮�����������������������ͨ������
					keywordSize = sqlOriginal.getKeywordSize();
				}
			}

			/**
			 * �ϴ�ͼƬ·��
			 */
			// 3G��Linux���������
			String srcurl = GotyaConst.uploadInternetPath + u.getFileName();
			// ���ز�����
			// String srcurl = GotyaConst.uploadTestInternetPath;

			/**
			 * @step 2 ��һ������ץȡ
			 */
			// ��ȡ��ǰʱ��
			double mills2 = new Date().getTime();
			// ץȡ����ͼ�������Ϣ
			Spider s = new Spider(srcurl);
			ArrayList<PicInfo> picList = s.getPicInfo();
			if (picList.size() == 0) {
				/** ����1��һ������û�з��ؽ�� **/
				Log.printErr("������ɣ��������-1��һ������û�з��ؽ����");
				return -1;
			}

			/**
			 * @step 3 �ڶ�������ץȡ
			 */
			// ץȡ����ͼ��������
			DeepSpider ds = new DeepSpider(picList);
			picList = ds.getPicDeepSpider();
			if (picList.size() == 0) {
				/** ����2����������û�з��ؽ�� **/
				Log.printErr("������ɣ��������-2����������û�з��ؽ����");
				return -2;
			}

			/**
			 * @step 4 �ִʡ����봦�����̲߳��е��ȣ�
			 */
			// ��ȡ��ǰʱ��
			double mills3 = new Date().getTime();
			// ���зִʴ���
			Segment sm = new Segment(picList);
			ArrayList<Data> picWords = sm.getWeightList();
			if (picWords.size() == 0) {
				/** ����3���ִ�û�з��ؽ�� **/
				Log.printErr("������ɣ��������-3���ִ�û�з��ؽ����");
				return -3;
			}

			// ��ͨ����ʱ�����½�����õ�������������ݿ�
			if (localFirstSearch == false) {
				if (picWords.size() < keywordSize) {
					sqlOriginal.copyOriginal();
					sqlOriginal.invalidMD5();
					oid = sqlOriginal.getNewOID();
				} else {
					String res3 = "****��ͨ������ɣ��ӱ��ط��ظ��ý����OID=" + oid + "\n";
					System.out.println(res3);
					Log.print(res3);
					return oid;
				}
			}

			// �¿�һ���߳�д��
			Thread threadKeyword = new Thread(new InsertKeywordToSql(oid,
					picWords));
			threadKeyword.start();

			// ������ץȡ������ͼƬ��Ϣ���������ģ�д�����ݿ�
			SqlSimilar sqlSimilar = Factory.getInstance().initSqlSimilar();
			sqlSimilar.setPicList(oid, picList);

			/**
			 * @step 5 �Էִʽ�����������ó������
			 */
			// ��ȡ��ǰʱ��
			double mills4 = new Date().getTime();
			// ץȡ���ͼ��
			Recognize rcn = new Recognize(picWords);
			ArrayList<PicInfo> picRcn = rcn.getPicRcn();
			if (picRcn.size() == 0) {
				/** ����4����������û�з��ؽ�� **/
				Log.printErr("������ɣ��������-4����������û�з��ؽ����");
				return -4;
			}

			// ������ץȡ�����ͼƬ��Ϣд�����ݿ�
			SqlRelated sqlRelated = Factory.getInstance().initSqlRelated();
			sqlRelated.setPicList(oid, picRcn);
			// ������������һ�Ρ�����ͼƬ�����͹ؼ�������д�����ݿ�
			original.setSimilarPicSize(picList.size());
			original.setRelatedPicSize(picRcn.size());
			original.setKeywordSize(picWords.size());
			original.setFilterKeywordSize(rcn.getFilterKeyword().size());
			sqlOriginal.updateSize(original);

			// �ȴ��ӽ��̽���
			threadKeyword.join();

			// ��ȡ����ʱ��
			double mills5 = new Date().getTime();
			// ��ʱ������д����־
			String result = "Spider use: " + (mills2 - mills1) / 1000 + "s"
					+ "\n" + "DeepSpider use: " + (mills3 - mills2) / 1000
					+ "s" + "\n" + "Segment use: " + (mills4 - mills3) / 1000
					+ "s" + "\n" + "Recognize use: " + (mills5 - mills4) / 1000
					+ "s" + "\n" + "Totally use: " + (mills5 - mills1) / 1000
					+ "s";
			System.out.println(result);
			Log.print(result);

			if (localFirstSearch == true) {
				String res2 = "****����������ɣ������緵�ؽ����OID=" + oid + "\n";
				System.out.println(res2);
				Log.print(res2);
			} else {
				String res4 = "****��ͨ������ɣ������緵�ظ��ý���������±������ݣ�OID=" + oid + "\n";
				System.out.println(res4);
				Log.print(res4);
			}

			/**
			 * @step 6 ������Ϣ��ǰ̨
			 */
			Log.printErr("������ɣ��������" + oid + "��");
			return oid;

		} catch (Exception e) {
			e.printStackTrace();
			Log.printErr(e);
		}
		/** ����5���׳��쳣������ **/
		Log.printErr("������ɣ��������-5���׳��쳣�����顣");
		return -5;
	}

	// URL�ṩͼƬ
	public int startGotya2(String _url, boolean localFirstSearch) {
		int oid = 0;
		try {
			URL url = new URL(_url);
			int size = 0;
			HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
			// ������Ӧ��ȡ�ļ���С
			size = urlcon.getContentLength();

			byte[] buffer = new byte[size];
			String filename = new String();
			int i = _url.lastIndexOf('/');
			if (i != -1) {
				filename = _url.substring(i + 1).toLowerCase();
			} else {
				System.out.println("URLͼƬ��ַ����");
				Log.print("URLͼƬ��ַ����");
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

	// ������ͼ
	public ArrayList<PicInfo> startGotya3(String _word) {
		ArrayList<PicInfo> picRcn = new ArrayList<PicInfo>();
		Document doc = null;
		doc = tryConnect(doc, GotyaConst.trySpider, _word);
		String result = new String();
		if (doc != null) {
			result = "��������ͼ����ȡ�ɹ���";

			String webSource = doc.toString();
			picRcn = Reghtml.recognizeText(webSource);
		} else {
			result = "��������ͼ��û�н��������ִʽ���Ƿ�����";
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
			System.out.println("��������ͼ����ȡʧ��"
					+ (GotyaConst.trySpider - tryNum + 1) + "��");
			Log.print("��������ͼ����ȡʧ��" + (GotyaConst.trySpider - tryNum + 1) + "��");
			Log.printErr(e);

			if (tryNum > 0) {
				doc = tryConnect(doc, tryNum - 1, searchWords);
			}
		}
		return doc;
	}

	// ǰ̨�ڻ�ȡ���ص�oid�󣬵����������������������ݵĻ�ȡ
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
