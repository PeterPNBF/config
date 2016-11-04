package com.tydic.config.compare;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import org.junit.Test;

import com.tydic.config.compare.PropertyInputStreamCompare;


public class PropertyInputStreamCompareTest {
	@Test
	public void test(){
		PropertyInputStreamCompare pisc = new PropertyInputStreamCompare();
		InputStream data1 = this.getClass().getClassLoader().getResourceAsStream("vds.properties");
		InputStream data2 = this.getClass().getClassLoader().getResourceAsStream("vds2.properties");
		String point_s = "driver";
		Vector<String> point = new Vector<String>();
		point.add(point_s);
		System.out.println("compare result=" + pisc.compare(data1, data2, point, null));
		try {
			data1.close();
			data2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
