package controller.spider;

import java.util.Date;
/**
 * @author ��֮Խ
 * @date 2010-6-5
 * ֹͣ����,���������CPUʹ����100%
 * @param mills ֹͣ��ǰ����mills����
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