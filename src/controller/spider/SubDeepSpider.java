package controller.spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import controller.process.Log;

public class SubDeepSpider implements Runnable {

	PicInfo pic;
	Document doc;
	int n;
	
	public SubDeepSpider(PicInfo pic,int n) {
		this.pic=pic;
		this.n=n;
		
	}

	public void run() {
		/**
		 * 抓取picVec中每张图片的来源网页
		 */
		try {
			doc = Jsoup.connect(pic.getFromURL()).get();
		} catch (Exception e) {
			System.out.println("DeepSpider("+n+")爬取失败。");
			Log.print("DeepSpider("+n+")(二次爬虫)爬取失败。");
			Log.printErr(e);
			this.pic=null;
			return ;
		}

		/**
		 * 设定picVec中每张图片的title属性
		 */
		pic.setTitle(doc.title());

		/**
		 * 定位来源网页中图片所在的树节点
		 */
		Elements image = doc.select("img[src$="
				+ pic.getPicName() + "]");

		if (image.size() == 0) {
			image = doc.select("a[href$=" + pic.getPicName()
					+ "]");
		}
		/**
		 * 如果图片存在，则抓取上下文
		 */
		if (image.size() != 0) {
			Element img = image.get(0);
			pic.setAboveText(FindText.above(img));
			pic.setFollowText(FindText.follow(img));
			String s="标题 @" + n + ":" + pic.getTitle()+"\n"+
					"上文 @" + n + ":" + pic.getAboveText()+"\n"+
					"下文 @" + n + ":" + pic.getFollowText();
			System.out.println(s);
			Log.print(s);
			
		} else {
			String s="没有找到图片 @" + n;
			System.out.println(s);
			Log.print(s);
			this.pic=null;
		}
	}
	
	public PicInfo getPicInfo(){
		return pic;
	}
}