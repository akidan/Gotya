package controller.spider;

public class PicInfo {
	private String thumbURL;	//图片缓存地址
	private String objURL;		//原图地址
	private String fromURL;		//图片来源页面
	private String fromURLHost;	//来源网站
	private String width;		//原图宽
	private String height;		//原图高
	private String type;		//后缀
	private String fileSize;	//以K为单位
	private String fromPageTitle;//标题
	private String textHost;	//识图简述
	private String curNum;		//图片序号
	private String picName;		//图片文件名
	//private String encodeMethod;	//页面编码格式

	//第一次后台用
	private String title;		//原网页标题
	private String aboveText;	//上文
	private String followText;	//下文
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
