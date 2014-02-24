package database.keyword;

public interface Keyword {
	
	public abstract void setOID(int i);
	public abstract int getOID();
	
	public abstract void setKeyword(String s);
	public abstract String getKeyword();
	
	public abstract void setFreq(float f);
	public abstract float getFreq();

}
