/**
 * 
 */
package com.tydic.vds.demo;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestHelper {
	public TestHelper() {
	}
	static{
		try {
			String driver = "com.tydic.vds.jdbc.VdsDriver";
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static ApplicationContext springCtx;
	public static Connection getConnection(){
		String url = getUrl();
		try {
			return DriverManager.getConnection(url);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Connection getMysqlJDBCConnection(){
		String url = getMysqlJDBCUrl();
		try {
			return DriverManager.getConnection(url,"root", "12345");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Connection getOracleJDBCConnection(){
		String url = getOracleJDBCUrl();
		try {
			return DriverManager.getConnection(url,"vds", "vds");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String getUrl(){
		return "jdbc:vds:local://classpath?sid=demo";
	}
	public static String getOracleJDBCUrl(){
		return "jdbc:oracle:thin:@172.168.1.240:1521:jfdb1";
	}
	public static String getMysqlJDBCUrl(){
		return "jdbc:mysql://192.168.10.210:3306/db1?rewriteBatchedStatements=true";
	}
	
	public static String resultSetObject2String(Object obj, String ... charset){
		if(obj==null){
			return null;
		}
		String cs = charset!=null&&charset.length>0?charset[0]:"GBK";
		try {
			if(obj instanceof Clob){
				return "[type=CLOB, value=[" + ((Clob)obj).getSubString(1, (int) ((Clob)obj).length()) + "]";
			} else if(obj instanceof Blob){
				return "[type=BLOB, value=[" + new String(((Blob)obj).getBytes(1, (int) ((Blob)obj).length()), cs) + "]";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return obj.toString();
	}

	public static ApplicationContext getApplicationContext(){
		if(springCtx == null){
			springCtx = new ClassPathXmlApplicationContext("applicationContext.xml");
		}
		return springCtx;
	}

	public static ApplicationContext getJTAApplicationContext(){
		if(springCtx == null){
			springCtx = new ClassPathXmlApplicationContext("jta/applicationContext-jta.xml");
		}
		return springCtx;
	}
}
