package database.original;

public abstract class AbstractOriginal implements Original {
	private int oid;
	private String MD5;
	private String createDate;
	private String lastestDate;
	private int searchTimes;
	private int similarPicSize;
	private int relatedPicSize;
	private int keywordSize;
	private int filterKeywordSize;
	
	public void setOID(int i) {
		this.oid=i;
	}

	public int getOID() {
		return oid;
	}

	public void setMD5(byte[] content) {
		this.MD5 = database.md5.MD5.toMD5(content);
	}

	public void setMD5(String s) {
		this.MD5 = s;
	}
	
	public String getMD5() {
		return MD5;
	}
	
	public void setCreateDate(String s){
		this.createDate = s;
	}
	
	public String getCreateDate(){
		return createDate;
	}
	
	public void setLastestDate(String s){
		this.lastestDate = s;
	}
	
	public String getLastestDate(){
		return lastestDate;
	}

	public void setSearchTimes(int i){
		this.searchTimes=i;
	}
	public int getSearchTimes(){
		return searchTimes;
	}
	
	public void setSimilarPicSize(int i){
		this.similarPicSize=i;
	}
	public int getSimilarPicSize(){
		return similarPicSize;
	}
	
	public void setRelatedPicSize(int i){
		this.relatedPicSize=i;
	}
	public int getRelatedPicSize(){
		return relatedPicSize;
	}
	
	public void setKeywordSize(int i){
		this.keywordSize=i;
	}
	public int getKeywordSize(){
		return keywordSize;
	}
	
	public void setFilterKeywordSize(int i){
		this.filterKeywordSize=i;
	}
	public int getFilterKeywordSize(){
		return filterKeywordSize;
	}
}
