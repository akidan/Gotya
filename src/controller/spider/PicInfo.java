package controller.spider;

public class PicInfo {
	private String thumbURL;	//ͼƬ�����ַ
	private String objURL;		//ԭͼ��ַ
	private String fromURL;		//ͼƬ��Դҳ��
	private String fromURLHost;	//��Դ��վ
	private String width;		//ԭͼ��
	private String height;		//ԭͼ��
	private String type;		//��׺
	private String fileSize;	//��KΪ��λ
	private String fromPageTitle;//����
	private String textHost;	//ʶͼ����
	private String curNum;		//ͼƬ���
	private String picName;		//ͼƬ�ļ���
	//private String encodeMethod;	//ҳ������ʽ

	//��һ�κ�̨��
	private String title;		//ԭ��ҳ����
	private String aboveText;	//����
	private String followText;	//����
	private String center;		//

	public PicInfo(){
		fromPageTitle="null";
		textHost="null";
		curNum="null";
		
		title="null";
		aboveText="null";
		followText="null";
		center="null";
	}
	
	public void setThumbURL(String s) {
		thumbURL = s;
	}

	public void setObjURL(String s) {
		objURL = s;
	}

	public void setFromURL(String s) {
		fromURL = s;
	}

	public void setFromURLHost(String s) {
		fromURLHost = s;
	}

	public void setWidth(String s) {
		width = s;
	}

	public void setHeight(String s) {
		height = s;
	}

	public void setType(String s) {
		type = s;
	}

	public void setFileSize(String s) {
		fileSize = s;
	}

	public void setFromPageTitle(String s) {
		fromPageTitle = s;
	}

	public void setTextHost(String s) {
		textHost = s;
	}

	public void setCurNum(String s) {
		curNum = s;
	}

	public void setPicName(String s) {
		picName = s;
	}

	public void setTitle(String s) {
		title = s;
	}

	public void setAboveText(String s) {
		aboveText = s;
	}

	public void setFollowText(String s) {
		followText = s;
	}

	public void setCenter(String s) {
		center = s;
	}

	// ------------------------------
	public String getThumbURL() {
		return thumbURL;
	}

	public String getObjURL() {
		return objURL;
	}

	public String getFromURL() {
		return fromURL;
	}

	public String getFromURLHost() {
		return fromURLHost;
	}

	public String getWidth() {
		return width;
	}

	public String getHeight() {
		return height;
	}

	public String getType() {
		return type;
	}

	public String getFileSize() {
		return fileSize;
	}

	public String getFromPageTitle() {
		return fromPageTitle;
	}

	public String getTextHost() {
		return textHost;
	}

	public String getCurNum() {
		return curNum;
	}

	public String getPicName() {
		return picName;
	}

	public String getTitle() {
		return title;
	}

	public String getAboveText() {
		return aboveText;
	}

	public String getFollowText() {
		return followText;
	}

	public String getCenter() {
		return center;
	}
}
