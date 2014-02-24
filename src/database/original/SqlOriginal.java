package database.original;

import database.*;

public class SqlOriginal extends AbstractOriginal {

	/**
	 * 根据MD5码检查图片是否已经存在
	 * 
	 * @param
	 * @return
	 */
	public boolean checkIfExist(Original pic) {
		this.setMD5(pic.getMD5());
		String sqlstr = "select * from original where md5='" + this.getMD5()
				+ "'";
		this.setOID(0);
		try {
			DBConnect dbconnect = new DBConnect();
			dbconnect.excuteQuery(sqlstr);
			if (dbconnect.next()) {
				this.setOID(dbconnect.getInt("oid"));
				this.setKeywordSize(dbconnect.getInt("keywordsize"));
				this.setSearchTimes(dbconnect.getInt("searchtimes"));
				dbconnect.close();
				return true;
			}
			dbconnect.close();
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
		}
		return false;
	}

	/**
	 * 图片信息第一次存入数据库
	 * 
	 * @param
	 * @return
	 */
	public boolean insertOriginal(Original pic) {
		String createDate = pic.getCreateDate();
		String sqlstr = "insert into original (md5,createdate,lastestdate,searchtimes,similarpicsize,relatedpicsize,keywordsize,filterkeywordsize) values('"
				+ this.getMD5()
				+ "','"
				+ createDate
				+ "','"
				+ createDate
				+ "','1','0','0','0','0" + "')";
		try {
			DBConnect dbconnect = new DBConnect();
			dbconnect.excuteUpdate(sqlstr);
			dbconnect.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
			return false;
		}
	}

	/**
	 * 更新已在数据库的图片信息
	 * 
	 * @param
	 * @return
	 */
	public boolean updateOriginal(Original pic) {
		String lastestDate = pic.getLastestDate();
		String sqlstr = "update original set lastestdate='" + lastestDate
				+ "'," + "searchtimes='" + (this.getSearchTimes() + 1)
				+ "' where md5='" + this.getMD5() + "'";
		try {
			DBConnect dbconnect = new DBConnect();
			dbconnect.excuteUpdate(sqlstr);
			dbconnect.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
			return false;
		}
	}

	/**
	 * 图片在数据库中不存在时，分配最新的OID
	 * 
	 * @return
	 */
	public int getNewOID() {
		String sqlstr = "select count(*) c from original";
		int oid = 0;
		try {
			DBConnect dbconnect = new DBConnect();
			dbconnect.excuteQuery(sqlstr);
			if (dbconnect.next()) {
				oid = dbconnect.getInt("c");
			}
			dbconnect.close();
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
		}
		return oid;
	}

	/**
	 * 更新图片的搜索结果数量信息
	 * 
	 * @param pic
	 * @return
	 */
	public boolean updateSize(Original pic) {
		int similarPicSize = pic.getSimilarPicSize();
		int relatedPicSize = pic.getRelatedPicSize();
		int keywordSize = pic.getKeywordSize();
		int filterKeywordSize = pic.getFilterKeywordSize();
		String md5 = pic.getMD5();
		String sqlstr = "update original set similarpicsize='" + similarPicSize
				+ "'," + "relatedpicsize='" + relatedPicSize + "',"
				+ "keywordsize='" + keywordSize + "'," + "filterkeywordsize='"
				+ filterKeywordSize + "' where md5='" + md5 + "'";
		try {
			DBConnect dbconnect = new DBConnect();
			dbconnect.excuteUpdate(sqlstr);
			dbconnect.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
			return false;
		}
	}

	/**
	 * 使无用的已存结果MD5码无效化
	 * 
	 * @return
	 */
	public boolean invalidMD5() {
		String sqlstr = "update original SET md5='invaild' where md5='"
				+ this.getMD5() + "'";
		try {
			DBConnect dbconnect = new DBConnect();
			dbconnect.excuteUpdate(sqlstr);
			dbconnect.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
			return false;
		}
	}

	/**
	 * 复制一条图片信息
	 * 
	 * @return
	 */
	public void copyOriginal() {
		String sqlstr = "select * from original where md5='" + this.getMD5()
				+ "'";
		try {
			DBConnect dbconnect = new DBConnect();
			dbconnect.excuteQuery(sqlstr);
			if (dbconnect.next()) {
				String newMD5 = dbconnect.getString("md5");
				String newCreateDate = dbconnect.getString("createdate");
				String newLastestDate = dbconnect.getString("lastestdate");
				int newSearchTimes = dbconnect.getInt("searchtimes");
				int newSimilarPicSize = dbconnect.getInt("similarpicsize");
				int newRelatedPicSize = dbconnect.getInt("relatedpicsize");
				int newKeywordSize = dbconnect.getInt("keywordsize");
				int newFilterKeywordSize = dbconnect
						.getInt("filterkeywordsize");
				String sqlstr2 = "insert into original (md5,createdate,lastestdate,searchtimes,similarpicsize,relatedpicsize,keywordsize,filterkeywordsize) values('"
						+ newMD5
						+ "','"
						+ newCreateDate
						+ "','"
						+ newLastestDate
						+ "','"
						+ newSearchTimes
						+ "','"
						+ newSimilarPicSize
						+ "','"
						+ newRelatedPicSize
						+ "','"
						+ newKeywordSize + "','" + newFilterKeywordSize + "')";
				dbconnect.excuteUpdate(sqlstr2);
				dbconnect.close();
			}
			dbconnect.close();
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
		}
	}
	
	/**
	 * 返回除噪后的关键词数量
	 */
	public int searchFilterKeywordSize(int oid) {
		String sqlstr = "select * from original where oid='" + oid
				+ "'";
		int result = controller.process.GotyaConst.searchWordsNum;
		try {
			DBConnect dbconnect = new DBConnect();
			dbconnect.excuteQuery(sqlstr);
			if (dbconnect.next()) {
				 result = dbconnect.getInt("filterkeywordsize");
			}
			dbconnect.close();
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
		}
		return result;
	}
}
