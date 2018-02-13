package com.demo.javabean;

import lombok.Getter;
import lombok.Setter;

import java.sql.*;

@Getter
@Setter
public class DBAccess {
	
	public static String drv = "org.gjt.mm.mysql.Driver";
	public static String url = "jdbc:mysql://localhost:3306/infor_manage_sys";
	public static String usr = "root";
	public static String pwd = "123";
	
	private Connection conn = null;
	private Statement stm = null;
	private ResultSet rs = null;

	public boolean createConn() {
		boolean b = false;
		try {
			Class.forName(drv).newInstance();
			conn = DriverManager.getConnection(url, usr, pwd);
			b = true;
		} catch (SQLException e) {
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
		return b;
	}

	public boolean update(String sql) {
		boolean b = false;
		try {
			stm = conn.createStatement();
			stm.execute(sql);
			b = true;
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return b;
	}

	public void query(String sql) {
		try {
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
		} catch (Exception e) {
		}
	}
	
	public boolean next() {
		boolean b = false;
		try {
			if(rs.next())b = true;
		} catch (Exception e) {
		}
		return b;		
	}
	
	public String getValue(String field) {
		String value = "";
		try {
			if(rs!=null)value = rs.getString(field);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (value == null) value = "";
		return value;
	}

	public void closeConn() {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
		}
	}

	public void closeStm() {
		try {
			if (stm != null)
				stm.close();
		} catch (SQLException e) {
		}
	}

	public void closeRs() {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
		}
	}
}
