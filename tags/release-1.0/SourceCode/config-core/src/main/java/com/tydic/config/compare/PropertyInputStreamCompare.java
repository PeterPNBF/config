package com.tydic.config.compare;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

/**
 * .properties类型比较
 * @see {@link Compare}
 * @author yuhaiming
 * @date 2014-9-3
 */
public class PropertyInputStreamCompare extends InputStreamCompare{
	private Logger log = Logger.getLogger(PropertyInputStreamCompare.class);
	@Override
	public boolean compare(InputStream data1, InputStream data2, Vector<String> point, Vector<String> result) {
		// TODO Auto-generated method stub
		if(!super.compare(data1, data2, point, result)){
			return false;
		}
		boolean tempres = true;
		Properties props1 = new Properties();
		Properties props2 = new Properties();
		try {
			props1.load((InputStream)data1);
			props2.load((InputStream)data2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String value1;
		String value2;
		for(String tempExpression : point){
			value1 = props1.getProperty(tempExpression);
			value2 = props2.getProperty(tempExpression);
			if(value1!=null&&value2!=null){
				if(!value1.equals(value2)){
					result.add(tempExpression);
					tempres = false;
				}
			} else if(value1==null&&value2==null){
				log.warn(tempExpression + "not exist in both data.");
			} else if(value1==null||value2==null){
				result.add(tempExpression);
			}
		}
		return tempres;
	}
}
