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
		 * ���ڶ�������Ŀɿؽ��ץȡ�� pageNum��ʾ�ӵڼ���ͼ��ʼץȡ��0Ϊ��ʼ showNum��ʾץȡ����ͼ
		 */
		//pageNum = GotyaConst.showPicIndex;
		showNum = GotyaConst.showPicNum;

		/**
		 * ����Jsoup����
		 */
		//Document docTemp = new Document("");

		srcurl = _srcurl;
		//docTemp=tryConnect(docTemp, GotyaConst.trySpider);

		/**
		 * ץȡ��һ��ͼƬ·�����Է�����̬sign
		 */
		//Elements image = docTemp
		//		.select("img[src^=" + GotyaConst.tutuHost + "]");
		//Elements nothing = docTemp.select("p.nothing");
		//if (nothing.size() > 0) {
		//	System.out.println("docTemp.select(\"img\")û�����������");
		//	return;
		//}
		//String urlBaidu = image.get(0).attr("src");
		//int sign1 = urlBaidu.lastIndexOf('/');
		//int sign2 = urlBaidu.lastIndexOf('.');
		//String sign = urlBaidu.substring(sign1 + 1, sign2);

		/**
		 * URL�滻Ϊ����sign������ҳ�����Խ�������ץȡ
		 */
		//String url = GotyaConst.spiderAnyPics + "&pn=" + pageNum + "&rn="
		//		+ showNum + GotyaConst.spiderAnyPics2 + sign;

		// ����һ������Ľ��ץȡ���ɿ�rn��
		url = "http://stu.baidu.com/i?rt=0&pn=0&rn=" + showNum + "&ct=1&tn=baiduimage&objurl=" + srcurl;
		
		
		/**
		 * ������ҳ����ץȡ
		 */
		Document doc=null;
		doc=tryConnectForOnceSpider(doc,GotyaConst.trySpider);
		String result=new String();
		if (doc!=null) {
			System.out.println("Spider.doc(һ������)��ȡ�ɹ���");
			Log.print("Spider.doc(һ������)��ȡ�ɹ���");
		

			/**
			 * ��ȡ��ҳ�еĹؼ���Ϣ�������PicInfo
			 */
			String webSource = doc.toString();
			picList = Reghtml.spiderText(webSource);
			
			result="һ��������"+ picList.size() + "�������";
			
		}else{
			result="һ������û�н���������ͼƬ������ͼƬ���ӵ���Ч�ԡ�";
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
			System.out.println("Spider.docTemp(Ԥ��һ������)��ȡʧ��" + (GotyaConst.trySpider-tryNum+1) + "��");
			Log.print("Spider.docTemp(Ԥ��һ������)��ȡʧ��" + (GotyaConst.trySpider-tryNum+1) + "��");
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
			System.out.println("Spider.doc(һ������)��ȡʧ��" + (GotyaConst.trySpider-tryNum+1) + "��");
			Log.print("Spider.doc(һ������)��ȡʧ��" + (GotyaConst.trySpider-tryNum+1) + "��");
			Log.printErr(e);
			
			if (tryNum > 0) {
				doc=tryConnectForOnceSpider(doc, tryNum - 1);
			}
		}
		return doc;
	}
}
