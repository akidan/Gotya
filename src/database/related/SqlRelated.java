package database.related;

import database.*;
import java.util.*;

import controller.spider.PicInfo;

public class SqlRelated extends AbstractRelated {

	/**
	 * 获取一次爬虫的图片
	 */
	public ArrayList<PicInfo> getPicList(int oid) {
		ArrayList<PicInfo> spiderList = new ArrayList<PicInfo>();
		

		String sqlstr = "select * from related where oid='" + oid + "'";
		try {
			DBConnect dbconnect = new DBConnect();
			dbconnect.excuteQuery(sqlstr);
			while (dbconnect.next()) {
				PicInfo temp = new PicInfo();
				temp.setThumbURL(dbconnect.getString("thumburl"));
				temp.setObjURL(dbconnect.getString("objurl"));
				temp.setFromURL(dbconnect.getString("fromurl"));
				temp.setFromURLHost(dbconnect.getString("fromurlhost"));
				temp.setWidth(dbconnect.getString("width"));
				temp.setHeight(dbconnect.getString("height"));
				temp.setType(dbconnect.getString("type"));
				temp.setFileSize(dbconnect.getString("filesize"));
				temp.setFromPageTitle(dbconnect.getString("frompagetitle"));
				temp.setCurNum(dbconnect.getString("curnum"));
				temp.setPicName(dbconnect.getString("picname"));
				spiderList.add(temp);
			}
			dbconnect.close();
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
		}
		return spiderList;
	}

	public boolean setPicList(int oid, ArrayList<PicInfo> spiderList) {
		for (PicInfo p : spiderList) {
			String sqlstr = "insert into related (oid,thumburl,objurl,fromurl,fromurlhost,width,height,type,filesize,frompagetitle,curnum,picname) values('"
					+ oid
					+ "','"
					+ p.getThumbURL()
					+ "','"
					+ p.getObjURL()
					+ "','"
					+ p.getFromURL()
					+ "','"
					+ p.getFromURLHost()
					+ "','"
					+ p.getWidth()
					+ "','"
					+ p.getHeight()
					+ "','"
					+ p.getType()
					+ "','"
					+ p.getFileSize()
					+ "','"
					+ p.getFromPageTitle()
					+ "','"
					+ p.getCurNum()
					+ "','"
					+ p.getPicName() + "')";
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
