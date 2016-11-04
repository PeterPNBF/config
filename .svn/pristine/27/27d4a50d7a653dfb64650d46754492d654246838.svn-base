package com.tydic.vds.demo;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Scfd {

	@Test
	public void test() throws UnsupportedEncodingException {
		try {
			/**
			 * spring方式加载VDS配置
			 */
			String xmlfile = "applicationContext.xml";
			ApplicationContext context = new ClassPathXmlApplicationContext(xmlfile);
			/**
			 * 取得VDS连接,url需与spring中environmentContext配置url相同
			 */
			Connection conn = TestHelper.getConnection();
			Assert.assertNotNull(conn);
			/**
			 * 使用方式与ORACLE MySql相同，额外的支持自定义选项、分库分表。
			 */
			String sql = "SELECT id id2,name name3, name||id name4 FROM test_a_partitions where id>10 order by 3 limit 5";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rest = stmt.executeQuery();
			ResultSetMetaData meta = rest.getMetaData();

			for (int i = 0; i < meta.getColumnCount(); i++) {
				System.out.print(meta.getColumnLabel(i + 1) + "|");
			}

			System.out.println("OK");

			while (rest.next()) {
				for (int i = 0; i < meta.getColumnCount(); ++i) {
					if(rest.getObject(i + 1) instanceof Clob){
						System.out.print("clob=[" + ((Clob)rest.getObject(i + 1)).getSubString(1, (int) ((Clob)rest.getObject(i + 1)).length()) + "]|");
					} else if (rest.getObject(i + 1) instanceof Blob){
						System.out.print("blob=[" + new String(((Blob)rest.getObject(i + 1)).getBytes(1, (int) ((Blob)rest.getObject(i + 1)).length()), "GBK") + "]|");
					} else {
						System.out.print(rest.getObject(i + 1) + "|");
					}
				}
				System.out.println();
			}
			rest.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
