package database.keyword;

import database.*;
import java.util.*;

import org.ictclas4j.bean.Data;

public class SqlKeyword extends AbstractKeyword {

	/**
	 * 获取一次爬虫的图片
	 */
	public ArrayList<Data> getKeyword(int oid) {
		ArrayList<Data> wordList = new ArrayList<Data>();

		String sqlstr = "select * from keyword where oid='" + oid + "'";
		try {
			DBConnect dbconnect = new DBConnect();
			dbconnect.excuteQuery(sqlstr);
			while (dbconnect.next()) {
				Data temp = new Data();
				temp.setWord(dbconnect.getString("keyword"));
				temp.setType(dbconnect.getString("type"));
				temp.setFreq(dbconnect.getFloat("freq"));
				wordList.add(temp);
			}
			dbconnect.close();
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
		}
		return wordList;
	}

	public boolean setKeyword(int oid,ArrayList<Data> wordList) {
		for (Data p : wordList) {
			String sqlstr = "insert into keyword (oid,keyword,type,freq) values('"
					+ oid
					+ "','"
					+ p.getWord()
					+ "','"
					+ p.getType()
					+ "','"
					+ p.getFreq()
					+
					"')";
			try {
				DBConnect dbconnect = new DBConnect();
				dbconnect.excuteUpdate(sqlstr);
				dbconnect.close();
			} catch (Exception e) {
				e.printStackTrace();
				controller.process.Log.printErr(e);
				return false;
			}
		}
		return true;
	}
}
