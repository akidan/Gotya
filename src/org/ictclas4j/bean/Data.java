package org.ictclas4j.bean;

public class Data {
	private String word;
	private String type;
	private float n=1;
	
	public void setWord(String tempword){
		word=tempword;
	}
	
	public void setType(String temptype){
		type=temptype;
	}
	
	public void setFreq(float tempN){
		n=tempN;
	}
	
	public String getWord(){
		return word;
	}
	
	public String getType(){
		return type;
	}
	
	public float getFreq(){
		return n;
	}
}
