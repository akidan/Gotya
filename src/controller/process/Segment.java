package controller.process;

import java.util.ArrayList;

import org.ictclas4j.bean.Data;

import controller.spider.PicInfo;

import org.ictclas4j.segment.SegTag;

public class Segment {
	private ArrayList<Data> aboveTextList=new ArrayList<Data>();
	private ArrayList<Data> followTextList=new ArrayList<Data>();
	private ArrayList<Data> titleList=new ArrayList<Data>();
	private ArrayList<Data> weightList=new ArrayList<Data>();
	
	String aboveTextAll=new String();
	String followTextAll=new String();
	String titleAll=new String();

	public Segment(ArrayList<PicInfo> picInfo) {
		for (int i=0;i<picInfo.size();i++){
			aboveTextAll+=picInfo.get(i).getAboveText()+"¡£";
			followTextAll+=picInfo.get(i).getFollowText()+"¡£";
			titleAll+=picInfo.get(i).getTitle()+"¡£";
		}
	
		aboveTextList = SegTag.segment(aboveTextAll);

		followTextList = SegTag.segment(followTextAll);

		titleList = SegTag.segment(titleAll);
		
		
		for (int i=0;i<titleList.size();i++){
			weightList.add(titleList.get(i));
			weightList.get(i).setFreq(weightList.get(i).getFreq()*GotyaConst.titleWeight);
		}
		
		for (int i=0;i<aboveTextList.size();i++){
			boolean exist=false;
			for (int j=0;j<weightList.size();j++){
				if (weightList.get(j).getWord().equals(aboveTextList.get(i).getWord())){
					weightList.get(j).setFreq(weightList.get(j).getFreq()
							+aboveTextList.get(i).getFreq()*GotyaConst.aboveTextWeight);
					exist=true;
					break;
				}
			}
			if (!exist){
				weightList.add(aboveTextList.get(i));
			}
		}
		
		for (int i=0;i<followTextList.size();i++){
			boolean exist=false;
			for (int j=0;j<weightList.size();j++){
				if (weightList.get(j).getWord().equals(followTextList.get(i).getWord())){
					weightList.get(j).setFreq(weightList.get(j).getFreq()
							+followTextList.get(i).getFreq()*GotyaConst.followTextWeight);
					exist=true;
					break;
				}
			}
			if (!exist){
				weightList.add(followTextList.get(i));
			}
		}
		
		
		//¿ìËÙÅÅÐò
		for(int i=0;i<weightList.size()-1;i++){
			for(int j=i+1;j<weightList.size();j++){
				if(weightList.get(i).getFreq()<weightList.get(j).getFreq()){
					Data temp=new Data();
					temp=weightList.get(i);
					weightList.set(i, weightList.get(j));
					weightList.set(j, temp);
				}
			}
		}
		
		printWeightList();
	}

	public ArrayList<Data> getWeightList() {
		return weightList;
	}
	
	public ArrayList<Data> getTitleList() {
		return titleList;
	}
	
	public ArrayList<Data> getAboveTextList() {
		return aboveTextList;
	}
	
	public ArrayList<Data> getFollowTextList() {
		return followTextList;
	}
	
	public void printWeightList(){
		System.out.println("=========Start print Weight ArrayList=========");
		Log.print("=========Start print Weight ArrayList=========");
		for (int i=0;i<weightList.size();i++){
			String s=weightList.get(i).getWord()+"  "
					+weightList.get(i).getType()+"  "
					+weightList.get(i).getFreq();
			System.out.println(s);
			Log.print(s);
		}
		System.out.println("=============End print "+weightList.size()+" Words=============");
		Log.print("=============End print "+weightList.size()+" Words=============");

	}
}
