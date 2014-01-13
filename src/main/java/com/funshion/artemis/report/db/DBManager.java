package com.funshion.artemis.report.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库管理类
 * @author shenwf
 * Reviewed-by zengyc
 */
public class DBManager {
	
	private Connection conn;
	private String url;
	private String username;
	private String password;


	public void init(String url, String username, String password) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		this.url = url;
		this.username = username;
		this.password = password;
	}


	public void connect() throws Exception {
		if(conn == null){
			conn = DriverManager.getConnection(url, username, password);
		}
	}


	public void close() {
		if(conn != null){
			try {conn.close();} catch (SQLException e) {}
			conn = null;
		}
	}
	
	public void commit(){
		if(conn != null){
			try {conn.commit();} catch (SQLException e) {}
		}
	}


	public Statement createStatement() throws Exception {
		return conn.createStatement();
	}
}
