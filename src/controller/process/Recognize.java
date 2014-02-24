package controller.process;

import java.net.URLEncoder;
import java.util.ArrayList;

import org.ictclas4j.bean.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import controller.spider.PicInfo;
import controller.spider.Reghtml;

public class Recognize {
	
	private ArrayList<PicInfo> picRcn;
	private ArrayList<String> searchWordsVec;
	private ArrayList<Float> weights;
	private String searchWords;
	
	public Recognize(ArrayList<Data> picWords){
		searchWordsVec = new ArrayList<String>();
		weights = new ArrayList<Float>();
		searchWords=new String();
		
		if (picWords.size()<GotyaConst.searchWordsNum){
			for(int i=0;i<picWords.size();i++){
				searchWordsVec.add(picWords.get(i).getWord());
				weights.add(picWords.get(i).getFreq());
			}
		}
		else if(picWords.size()<GotyaConst.searchWordsNum+1){
			for(int i=0;i<GotyaConst.searchWordsNum;i++){
				searchWordsVec.add(picWords.get(i).getWord());
				weights.add(picWords.get(i).getFreq());
			}
		}
		else{
			for(int i=0;i<GotyaConst.searchWordsNum;i++){
				searchWordsVec.add(picWords.get(i).getWord());
				weights.add(picWords.get(i).getFreq());
			}
			for(int i=GotyaConst.searchWordsNum-1;i>0;i--){
				if(picWords.get(i).getFreq()==picWords.get(GotyaConst.searchWordsNum-1).getFreq()){
					searchWordsVec.remove(i);
					weights.remove(i);
				}
			}
		}
		
		for (int i=0;i<searchWordsVec.size();i++){
			searchWords+=searchWordsVec.get(i)+" ";
		}
		searchWords=searchWords.trim();
		
		Document doc=null;
		doc=tryConnect(doc,GotyaConst.trySpider);
		String result=new String();
		if (doc!=null) {
			result="Recognize.doc(结果爬虫)爬取成功！";
			
			String webSource=doc.toString();
			picRcn=Reghtml.recognizeText(webSource);
		}else{
			result="三次爬虫没有结果，请检查分词结果是否有误。";
		}
		System.out.println(result);
		Log.print(result);
	}
	
	public ArrayList<PicInfo> getPicRcn(){
		return picRcn;
	}
	
	public ArrayList<String> getFilterKeyword(){
		return searchWordsVec;
	}
	
	public ArrayList<Float> getWeights(){
		return weights;
	}
	
	public Document tryConnect(Document doc, int tryNum) {
		try {
			doc = Jsoup.connect(GotyaConst.recognizePath
					+URLEncoder.encode(searchWords,"gb2312")
					+"&pn="+GotyaConst.rcnPicIndex
					+"&rn="+GotyaConst.rcnPicNum
					).get();
		} catch (Exception e) {
			System.out.println("Recognize.doc(结果爬虫)爬取失败" + (GotyaConst.trySpider-tryNum+1) + "。");
			Log.print("Recognize.doc(结果爬虫)爬取失败" + (GotyaConst.trySpider-tryNum+1) + "。");
			Log.printErr(e);
			
			if (tryNum > 0) {
				doc=tryConnect(doc, tryNum - 1);
			}
		}
		return doc;
	}
}
