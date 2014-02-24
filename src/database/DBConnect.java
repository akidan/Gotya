package database;

import java.sql.*;

public class DBConnect {
	private Connection con;
	private Statement sql;
	private PreparedStatement psql;
	private ResultSet rs;

	/**
	 * 获得一个数据库连接
	 */
	private void init() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url = controller.process.GotyaConst.jdbcURL;
			con = DriverManager.getConnection(url,
					controller.process.GotyaConst.sqlUser,
					controller.process.GotyaConst.sqlPassword);
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
		}
	}

	/**
	 * 构造函数
	 */
	public DBConnect() {
		try {
			init();
			sql = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
		}
	}

	/**
	 * 对数据库执行查询操作，s为查询语句，结果赋给rs
	 * 
	 * @param 查询语句
	 */
	public void excuteQuery(String s) {
		try {
			if (sql != null) {
				rs = sql.executeQuery(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
		}
	}

	/**
	 * 对数据库进行update操作
	 * 
	 * @param update操作语句
	 * @return
	 */
	public int excuteUpdate(String s) {
		int status = 0;
		try {
			if (sql != null) {
				status = sql.executeUpdate(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
		}
		return status;
	}

	// 以下为赋值方法
	public void setString(int i, String s) {// 字符串赋值
		try {
			psql.setString(i, s);
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
		}
	}

	public void setBoolean(int i, Boolean flag) {// 布尔型赋值
		try {
			psql.setBoolean(i, flag);
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
		}
	}

	public void setDate(int i, Date date) {// 日期类型赋值
		try {
			psql.setDate(i, date);
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
		}
	}

	public void setTime(int i, Time time) {// 时间类型赋值
		try {
			psql.setTime(i, time);
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
		}
	}

	public void setShort(int i, Short n) {// 短整数类型赋值
		try {
			psql.setShort(i, n);
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
		}
	}

	public void setInt(int i, int j) {// 整数类型赋值
		try {
			psql.setInt(i, j);
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
		}
	}

	public void setLong(int i, Long l) {// 长整数类型赋值
		try {
			psql.setLong(i, l);
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
		}
	}

	public void setFloat(int i, Float f) {// 浮点型赋值
		try {
			psql.setFloat(i, f);
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
		}
	}

	public void setDouble(int i, Double d) {// 双精度类型赋值
		try {
			psql.setDouble(i, d);
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
		}
	}

	// 以下为取值方法
	public boolean getBoolean(int i) throws Exception {// 取得boolean类型的字段，通过列号
		return rs.getBoolean(i);
	}

	public boolean getBoolean(String s) throws Exception {// 取得boolean类型的字段，通过字段名
		return rs.getBoolean(s);
	}

	public Date getDate(int i) throws Exception {// 取得Date类型的字段，通过列号
		return rs.getDate(i);
	}

	public Date getDate(String s) throws Exception {// 取得Date类型的字段，通过字段名
		return rs.getDate(s);
	}

	public Time getTime(int i) throws Exception {// 取得Time类型的字段，通过列号
		return rs.getTime(i);
	}

	public Time getTime(String s) throws Exception {// 取得Time类型的字段，通过字段名
		return rs.getTime(s);
	}

	public float getFloat(int i) throws Exception {// 取得Float类型的字段，通过列号
		return rs.getFloat(i);
	}

	public float getFloat(String s) throws Exception {// 取得Float类型的字段，通过字段名
		return rs.getFloat(s);
	}

	public double getDouble(int i) throws Exception {// 取得Double类型的字段，通过列号
		return rs.getDouble(i);
	}

	public double getDouble(String s) throws Exception {// 取得Double类型的字段，通过字段名
		return rs.getDouble(s);
	}

	public short getShort(int i) throws Exception {// 取得Short类型的字段，通过列号
		return rs.getShort(i);
	}

	public short getShort(String s) throws Exception {// 取得Short类型的字段，通过字段名
		return rs.getShort(s);
	}

	public int getInt(int i) throws Exception {// 取得Int类型的字段，通过列号
		return rs.getInt(i);
	}

	public int getInt(String s) throws Exception {// 取得Int类型的字段，通过字段名
		return rs.getInt(s);
	}

	public long getLong(int i) throws Exception {// 取得Long类型的字段，通过列号
		return rs.getLong(i);
	}

	public long getLong(String s) throws Exception {// 取得Long类型的字段，通过字段名
		return rs.getLong(s);
	}

	public String getString(int i) throws Exception {// 取得String类型的字段，通过列号
		return rs.getString(i);
	}

	public String getString(String s) throws Exception {// 取得String类型的字段，通过字段名
		return rs.getString(s);
	}

	/**
	 * 指针下移一位
	 * 
	 * @return
	 */
	public boolean next() {
		try {
			return rs.next();
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
			return false;
		}
	}

	/**
	 * 释放内容
	 */
	public void close() {
		try {
			if (con != null)
				con.close();
			if (sql != null)
				sql.close();
			if (rs != null)
				rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
		}
	}
}
