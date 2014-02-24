package controller.spider;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
public class GetHtml {
	
	public static String decodeURL(String url,String encodeMethod) throws UnsupportedEncodingException{
		String result=new String();
		if (encodeMethod.equals("gb2312")){
			result=URLDecoder.decode(url, "gb2312");
		}else if (encodeMethod.equals("utf-8")){
			result=URLDecoder.decode(url, "utf-8");
		}else if (encodeMethod.equals("gbk")){
			result=URLDecoder.decode(url, "gbk");
		}else{
			result=url;
		}
		return result;
	}
}