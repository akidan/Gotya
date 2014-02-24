package controller.process;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

//import Controller.IOAsFile.IOAsFile;
import controller.spider.*;

public class Spider {
	private ArrayList<PicInfo> picList = new ArrayList<PicInfo>();
	private String srcurl;
	//private String pageNum;
	private String showNum;
	private String url;
	
	public Spider(String _srcurl) throws IOException {

		/**
		 * 基于二次爬虫的可控结果抓取。 pageNum表示从第几张图开始抓取，0为开始 showNum表示抓取多少图
		 */
		//pageNum = GotyaConst.showPicIndex;
		showNum = GotyaConst.showPicNum;

		/**
		 * 建立Jsoup连接
		 */
		//Document docTemp = new Document("");

		srcurl = _srcurl;
		//docTemp=tryConnect(docTemp, GotyaConst.trySpider);

		/**
		 * 抓取第一张图片路径用以分析动态sign
		 */
		//Elements image = docTemp
		//		.select("img[src^=" + GotyaConst.tutuHost + "]");
		//Elements nothing = docTemp.select("p.nothing");
		//if (nothing.size() > 0) {
		//	System.out.println("docTemp.select(\"img\")没有搜索结果。");
		//	return;
		//}
		//String urlBaidu = image.get(0).attr("src");
		//int sign1 = urlBaidu.lastIndexOf('/');
		//int sign2 = urlBaidu.lastIndexOf('.');
		//String sign = urlBaidu.substring(sign1 + 1, sign2);

		/**
		 * URL替换为含有sign的新网页，可以进行自由抓取
		 */
		//String url = GotyaConst.spiderAnyPics + "&pn=" + pageNum + "&rn="
		//		+ showNum + GotyaConst.spiderAnyPics2 + sign;

		// 基于一次爬虫的结果抓取（可控rn）
		url = "http://stu.baidu.com/i?rt=0&pn=0&rn=" + showNum + "&ct=1&tn=baiduimage&objurl=" + srcurl;
		
		
		/**
		 * 对新网页进行抓取
		 */
		Document doc=null;
		doc=tryConnectForOnceSpider(doc,GotyaConst.trySpider);
		String result=new String();
		if (doc!=null) {
			System.out.println("Spider.doc(一次爬虫)爬取成功！");
			Log.print("Spider.doc(一次爬虫)爬取成功！");
		

			/**
			 * 提取网页中的关键信息，整理成PicInfo
			 */
			String webSource = doc.toString();
			picList = Reghtml.spiderText(webSource);
			
			result="一次爬虫有"+ picList.size() + "个结果。";
			
		}else{
			result="一次爬虫没有结果，请更换图片，或检查图片链接的有效性。";
		}
		System.out.println(result);
		Log.print(result);
	}

	public ArrayList<PicInfo> getPicInfo() {
		return picList;
	}

	public Document tryConnect(Document doc, int tryNum) {
		try {
			doc = Jsoup.connect(GotyaConst.spiderTenPics + srcurl).post();
		} catch (Exception e) {
			System.out.println("Spider.docTemp(预备一次爬虫)爬取失败" + (GotyaConst.trySpider-tryNum+1) + "。");
			Log.print("Spider.docTemp(预备一次爬虫)爬取失败" + (GotyaConst.trySpider-tryNum+1) + "。");
			Log.printErr(e);
			
			if (tryNum > 0) {
				doc=tryConnect(doc, tryNum - 1);
			}
		}
		return doc;
	}
	
	public Document tryConnectForOnceSpider(Document doc, int tryNum) {
		try {
			doc = Jsoup.connect(url).post();
		} catch (Exception e) {
			System.out.println("Spider.doc(一次爬虫)爬取失败" + (GotyaConst.trySpider-tryNum+1) + "。");
			Log.print("Spider.doc(一次爬虫)爬取失败" + (GotyaConst.trySpider-tryNum+1) + "。");
			Log.printErr(e);
			
			if (tryNum > 0) {
				doc=tryConnectForOnceSpider(doc, tryNum - 1);
			}
		}
		return doc;
	}
}
