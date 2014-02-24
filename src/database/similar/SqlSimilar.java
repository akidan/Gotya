package database.similar;

import database.*;
import java.util.*;

import controller.spider.PicInfo;

public class SqlSimilar extends AbstractSimilar {

	/**
	 * 获取一次爬虫的图片
	 */
	public ArrayList<PicInfo> getPicList(int oid) {
		ArrayList<PicInfo> spiderList = new ArrayList<PicInfo>();

		String sqlstr = "select * from similar where oid='" + oid + "'";
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
				temp.setTextHost(dbconnect.getString("texthost"));
				temp.setCurNum(dbconnect.getString("curnum"));
				temp.setPicName(dbconnect.getString("picname"));
				
				temp.setAboveText(dbconnect.getString("abovetext"));
				temp.setFollowText(dbconnect.getString("followtext"));
				temp.setTitle(dbconnect.getString("title"));
				temp.setCenter(dbconnect.getString("center"));
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
			String sqlstr = "insert into similar (oid,thumburl,objurl,fromurl,fromurlhost,width,height,type,filesize,frompagetitle,texthost,curnum,picname,title,abovetext,followtext,center) values('"
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
					+ p.getTextHost()
					+ "','"
					+ p.getCurNum()
					+ "','"
					+ p.getPicName()
					+ "','"
					+ p.getTitle()
					+ "','"
					+ p.getAboveText()
					+ "','"
					+ p.getFollowText() + "','" + p.getCenter() + "')";
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
