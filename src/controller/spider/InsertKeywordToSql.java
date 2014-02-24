package controller.spider;

import java.util.ArrayList;
import java.util.Date;

import org.ictclas4j.bean.Data;
import database.initial.Factory;
import database.keyword.SqlKeyword;

public class InsertKeywordToSql implements Runnable {

	private int oid;
	private ArrayList<Data> picWords;

	public InsertKeywordToSql(int oid, ArrayList<Data> picWords) {
		this.oid = oid;
		this.picWords = picWords;
	}

	public void run() {
		// 将分词结果写入数据库
		
		double mills1=new Date().getTime();
		SqlKeyword sqlKeyword = Factory.getInstance().initSqlKeyword();
		sqlKeyword.setKeyword(oid, picWords);
		double mills2=new Date().getTime();
		
		String s="##写入分词结果的线程耗时"+(mills2-mills1)/1000+"s";
		System.out.println(s);
		//controller.process.Log.print(s);
	}
}