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
		 * ץȡpicVec��ÿ��ͼƬ����Դ��ҳ
		 */
		try {
			doc = Jsoup.connect(pic.getFromURL()).get();
		} catch (Exception e) {
			System.out.println("DeepSpider("+n+")��ȡʧ�ܡ�");
			Log.print("DeepSpider("+n+")(��������)��ȡʧ�ܡ�");
			Log.printErr(e);
			this.pic=null;
			return ;
		}

		/**
		 * �趨picVec��ÿ��ͼƬ��title����
		 */
		pic.setTitle(doc.title());

		/**
		 * ��λ��Դ��ҳ��ͼƬ���ڵ����ڵ�
		 */
		Elements image = doc.select("img[src$="
				+ pic.getPicName() + "]");

		if (image.size() == 0) {
			image = doc.select("a[href$=" + pic.getPicName()
					+ "]");
		}
		/**
		 * ���ͼƬ���ڣ���ץȡ������
		 */
		if (image.size() != 0) {
			Element img = image.get(0);
			pic.setAboveText(FindText.above(img));
			pic.setFollowText(FindText.follow(img));
			String s="���� @" + n + ":" + pic.getTitle()+"\n"+
					"���� @" + n + ":" + pic.getAboveText()+"\n"+
					"���� @" + n + ":" + pic.getFollowText();
			System.out.println(s);
			Log.print(s);
			
		} else {
			String s="û���ҵ�ͼƬ @" + n;
			System.out.println(s);
			Log.print(s);
			this.pic=null;
		}
	}
	
	public PicInfo getPicInfo(){
		return pic;
	}
}