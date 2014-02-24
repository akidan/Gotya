package controller.process;

import java.util.ArrayList;

import controller.spider.SubDeepSpider;
import controller.spider.PicInfo;

public class DeepSpider {

	private ArrayList<PicInfo> picDeepSpider = new ArrayList<PicInfo>();

	public DeepSpider(ArrayList<PicInfo> picList){
		ArrayList<SubDeepSpider> subSpiderList=new ArrayList<SubDeepSpider>(); 
		ArrayList<Thread> threadList=new ArrayList<Thread>();
		
		for (int i = 0; i < picList.size(); i++) {
			subSpiderList.add(new SubDeepSpider(picList.get(i),i));
			threadList.add(new Thread(subSpiderList.get(i)));
			threadList.get(i).start();
		}

		for (int i=0;i<picList.size();i++){
			try {
				/**
				 * 等待线程结束并汇总
				 */
				threadList.get(i).join(GotyaConst.maxMillsWaitDeepSpider);
			} catch (InterruptedException e) {
				Log.printErr(e);
				e.printStackTrace();
			}
		}
		
		for (int i=0;i<picList.size();i++)
		{
			if (subSpiderList.get(i).getPicInfo()!=null){
				picDeepSpider.add(subSpiderList.get(i).getPicInfo());
			}
		}
	}

	public ArrayList<PicInfo> getPicDeepSpider() {
		return picDeepSpider;
	}
	
}