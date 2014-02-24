package database.keyword;

public abstract class AbstractKeyword implements Keyword{
	
	private int oid;			//±¾µØÍ¼Æ¬µÄID
	
	private String keyword;
	private String type;
	private float freq;
	
	public void setOID(int i){
		this.oid=i;
	}
	
	public int getOID(){
		return oid;
	}
	
	public void setKeyword(String s){
		this.keyword=s;
	}
	
	public String getKeyword(){
		return keyword;
	}
	
	public void setType(String s){
		this.type=s;
	}
	
	public String getType(){
		return type;
	}
	
	public void setFreq(float f){
		this.freq=f;
	}
	
	public float getFreq(){
		return freq;
	}
	
	
}
