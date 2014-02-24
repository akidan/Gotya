package database.related;

public interface Related {
	
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
	public abstract String setType();
	
	public abstract void setFileSize(String s);
	public abstract String getFileSize();
	
	public abstract void setFromPageTitle(String s);
	public abstract String getFromPageTitle();
	
	public abstract void setCurNum(String s);
	public abstract String getCurNum();
	
	public abstract void setPicName(String s);
	public abstract String getPicName();
}
