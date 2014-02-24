package controller.spider;

import java.util.Date;
/**
 * @author 王之越
 * @date 2010-6-5
 * 停止进程,但可能造成CPU使用率100%
 * @param mills 停止当前进程mills毫秒
 */
public class MyThread { 
	public static void sleep(long mills){ 
		long mills1 = new Date().getTime(); 
		long mills2; 
		for(;;){ 
			mills2 = new Date().getTime(); 
			if(mills2 - mills1== mills){ 
				return; 
			} 
		} 
	} 
}