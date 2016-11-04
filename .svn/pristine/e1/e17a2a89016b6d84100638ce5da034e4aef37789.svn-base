package com.tydic.config.compare;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import org.junit.Test;

import com.tydic.config.compare.XMLInputStreamCompare;


public class XMLInputStreamCompareTest {
	@Test
	public void test() throws IOException{
		XMLInputStreamCompare xmlcom = new XMLInputStreamCompare();
		InputStream data1 = this.getClass().getClassLoader().getResourceAsStream("vds.xml");
		InputStream data2 = this.getClass().getClassLoader().getResourceAsStream("vds22.xml");
		String point_s = "/vds";
		Vector<String> point = new Vector<String>();
		point.add(point_s);
		byte[] b = new byte[10240];
		data1.read(b);
		String str1 = new String(b,"utf-8");
		System.out.println(xmlcom.compare(new ByteArrayInputStream(str1.getBytes("utf-8")), data2, point, null));
		data1.close();
		data2.close();
	}
}
