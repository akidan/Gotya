package database.related;

public abstract class AbstractRelated implements Related{
	
	private int oid;			//����ͼƬ��ID
	
	private String thumbURL;	//ͼƬ�����ַ
	private String objURL;		//ԭͼ��ַ
	private String fromURL;		//ͼƬ��Դҳ��
	private String fromURLHost;	//��Դ��վ
	private String width;		//ԭͼ��
	private String height;		//ԭͼ��
	private String type;		//��׺
	private String fileSize;	//��KΪ��λ
	private String fromPageTitle;//����
	
	private String curNum;		//ͼƬ���
	private String picName;		//ͼƬ�ļ���
	
	public void setOID(int i){
		this.oid=i;
	}
	
	public int getOID(){
		return oid;
	}
	
	public void setThumbURL(String s){
		this.thumbURL=s;
	}
	
	public String getThumbURL(){
		return thumbURL;
	}
	
	public void setObjURL(String s){
		this.objURL=s;
	}
	
	public String getObjURL(){
		return objURL;
	}
	
	public void setFromURL(String s){
		this.fromURL=s;
	}
	
	public String getFromURL(){
		return fromURL;
	}
	
	public void setFromURLHost(String s){
		this.fromURLHost=s;
	}
	
	public String getFromURLHost(){
		return fromURLHost;
	}
	
	public void setWeight(String s){
		this.width=s;
	}
	
	public String getWeight(){
		return width;
	}
	
	public void setHeight(String s){
		this.height=s;
	}
	
	public String getHeight(){
		return height;
	}
	
	public void setType(String s){
		this.type=s;
	}
	
	public String setType(){
		return type;
	}
	
	public void setFileSize(String s){
		this.fileSize=s;
	}
	
	public String getFileSize(){
		return fileSize;
	}
	
	public void setFromPageTitle(String s){
		this.fromPageTitle=s;
	}
	
	public String getFromPageTitle(){
		return fromPageTitle;
	}
	
	public void setCurNum(String s){
		this.curNum=s;
	}
	
	public String getCurNum(){
		return curNum;
	}
	
	public void setPicName(String s){
		this.picName=s;
	}
	
	public String getPicName(){
		return picName;
	}

}
