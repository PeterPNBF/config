package com.tydic.config.compare;

import java.io.InputStream;
import java.util.Vector;

import org.apache.log4j.Logger;

public class InputStreamCompare implements Compare<InputStream>{
	private Logger log = Logger.getLogger(InputStreamCompare.class);

	@Override
	public boolean compare(InputStream data1, InputStream data2, Vector<String> point, Vector<String> result) {
		// TODO Auto-generated method stub
		if(data1==null){
			result = point;
			log.debug("data1 is null");
			return false;
		} 
		if(data2==null){
			result = point;
			log.debug("data2 is null");
			return false;
		}
		return true;
	}
}
