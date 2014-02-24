package database;

import java.sql.*;

public class DBConnect {
	private Connection con;
	private Statement sql;
	private PreparedStatement psql;
	private ResultSet rs;

	/**
	 * ���һ�����ݿ�����
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
	 * ���캯��
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
	 * �����ݿ�ִ�в�ѯ������sΪ��ѯ��䣬�������rs
	 * 
	 * @param ��ѯ���
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
	 * �����ݿ����update����
	 * 
	 * @param update�������
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

	// ����Ϊ��ֵ����
	public void setString(int i, String s) {// �ַ�����ֵ
		try {
			psql.setString(i, s);
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
		}
	}

	public void setBoolean(int i, Boolean flag) {// �����͸�ֵ
		try {
			psql.setBoolean(i, flag);
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
		}
	}

	public void setDate(int i, Date date) {// �������͸�ֵ
		try {
			psql.setDate(i, date);
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
		}
	}

	public void setTime(int i, Time time) {// ʱ�����͸�ֵ
		try {
			psql.setTime(i, time);
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
		}
	}

	public void setShort(int i, Short n) {// ���������͸�ֵ
		try {
			psql.setShort(i, n);
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
		}
	}

	public void setInt(int i, int j) {// �������͸�ֵ
		try {
			psql.setInt(i, j);
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
		}
	}

	public void setLong(int i, Long l) {// ���������͸�ֵ
		try {
			psql.setLong(i, l);
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
		}
	}

	public void setFloat(int i, Float f) {// �����͸�ֵ
		try {
			psql.setFloat(i, f);
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
		}
	}

	public void setDouble(int i, Double d) {// ˫�������͸�ֵ
		try {
			psql.setDouble(i, d);
		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
		}
	}

	// ����Ϊȡֵ����
	public boolean getBoolean(int i) throws Exception {// ȡ��boolean���͵��ֶΣ�ͨ���к�
		return rs.getBoolean(i);
	}

	public boolean getBoolean(String s) throws Exception {// ȡ��boolean���͵��ֶΣ�ͨ���ֶ���
		return rs.getBoolean(s);
	}

	public Date getDate(int i) throws Exception {// ȡ��Date���͵��ֶΣ�ͨ���к�
		return rs.getDate(i);
	}

	public Date getDate(String s) throws Exception {// ȡ��Date���͵��ֶΣ�ͨ���ֶ���
		return rs.getDate(s);
	}

	public Time getTime(int i) throws Exception {// ȡ��Time���͵��ֶΣ�ͨ���к�
		return rs.getTime(i);
	}

	public Time getTime(String s) throws Exception {// ȡ��Time���͵��ֶΣ�ͨ���ֶ���
		return rs.getTime(s);
	}

	public float getFloat(int i) throws Exception {// ȡ��Float���͵��ֶΣ�ͨ���к�
		return rs.getFloat(i);
	}

	public float getFloat(String s) throws Exception {// ȡ��Float���͵��ֶΣ�ͨ���ֶ���
		return rs.getFloat(s);
	}

	public double getDouble(int i) throws Exception {// ȡ��Double���͵��ֶΣ�ͨ���к�
		return rs.getDouble(i);
	}

	public double getDouble(String s) throws Exception {// ȡ��Double���͵��ֶΣ�ͨ���ֶ���
		return rs.getDouble(s);
	}

	public short getShort(int i) throws Exception {// ȡ��Short���͵��ֶΣ�ͨ���к�
		return rs.getShort(i);
	}

	public short getShort(String s) throws Exception {// ȡ��Short���͵��ֶΣ�ͨ���ֶ���
		return rs.getShort(s);
	}

	public int getInt(int i) throws Exception {// ȡ��Int���͵��ֶΣ�ͨ���к�
		return rs.getInt(i);
	}

	public int getInt(String s) throws Exception {// ȡ��Int���͵��ֶΣ�ͨ���ֶ���
		return rs.getInt(s);
	}

	public long getLong(int i) throws Exception {// ȡ��Long���͵��ֶΣ�ͨ���к�
		return rs.getLong(i);
	}

	public long getLong(String s) throws Exception {// ȡ��Long���͵��ֶΣ�ͨ���ֶ���
		return rs.getLong(s);
	}

	public String getString(int i) throws Exception {// ȡ��String���͵��ֶΣ�ͨ���к�
		return rs.getString(i);
	}

	public String getString(String s) throws Exception {// ȡ��String���͵��ֶΣ�ͨ���ֶ���
		return rs.getString(s);
	}

	/**
	 * ָ������һλ
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
	 * �ͷ�����
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
