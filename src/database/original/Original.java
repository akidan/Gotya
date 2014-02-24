package database.original;

public interface Original {
	
	public abstract void setOID(int i);
	public abstract int getOID();
	
	public abstract void setMD5(byte[] content);
	public abstract String getMD5();
	
	public abstract void setCreateDate(String s);
	public abstract String getCreateDate();
	
	public abstract void setLastestDate(String s);
	public abstract String getLastestDate();

	public abstract void setSearchTimes(int i);
	public abstract int getSearchTimes();
	
	public abstract void setSimilarPicSize(int i);
	public abstract int getSimilarPicSize();
	
	public abstract void setRelatedPicSize(int i);
	public abstract int getRelatedPicSize();
	
	public abstract void setKeywordSize(int i);
	public abstract int getKeywordSize();
	
	public abstract void setFilterKeywordSize(int i);
	public abstract int getFilterKeywordSize();
}
