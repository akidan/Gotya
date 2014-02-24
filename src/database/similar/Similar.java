package database.similar;

public interface Similar {
	
	public abstract void setOID(int i);
	public abstract int getOID();
	
	public abstract void setThumbURL(String s);
	public abstract String getThumbURL();
	
	public abstract void setObjURL(String s);
	public abstract String getObjURL();
	
	public abstract void setFromURL(String s);
	public abstract String getFromURL();
	
	public abstract void setFromURLHost(String s);
	public abstract String getFromURLHost();
	
	public abstract void setWeight(String s);
	public abstract String getWeight();
	
	public abstract void setHeight(String s);
	public abstract String getHeight();
	
	public abstract void setType(String s);
	public abstract String getType();
	
	public abstract void setFileSize(String s);
	public abstract String getFileSize();
	
	public abstract void setFromPageTitle(String s);
	public abstract String getFromPageTitle();
	
	public abstract void setTextHost(String s);
	public abstract String getTextHost();
	
	public abstract void setCurNum(String s);
	public abstract String getCurNum();
	
	public abstract void setPicName(String s);
	public abstract String getPicName();
	
	public abstract void setTitle(String s);
	public abstract String getTitle();
	
	public abstract void setAboveText(String s);
	public abstract String getAboveText();
	
	public abstract void setFollowText(String s);
	public abstract String getFollowText();
	
	public abstract void setCenter(String s);
	public abstract String getCenter();
}
