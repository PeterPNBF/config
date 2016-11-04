package com.tydic.config.test;

import org.junit.Test;

import com.tydic.config.ZKConfigProcess;
import com.tydic.config.impl.ZKConfigProcessTemplate;
import com.tydic.config.spi.Configuration;


public class WriteEnv2ZK {
	@Test
	public void test(){
		ZKConfigProcess cp = new ZKConfigProcessTemplate();
		cp.init();
		cp.deleteNode(Configuration.ENV);
		cp.writeEnv2ZK(Configuration.INDEXHOMEENVNAME, Configuration.INDEX_HOME);
		String config = new String(cp.getData(Configuration.ENV));
		System.out.println(Configuration.ENV + "=[\n" + config + "\n]");
		cp.getAllChild("/", "");
		cp.terminate();
	}
}
