package com.tydic.config.test;

import org.junit.Test;

import com.tydic.config.ZKConfigProcess;
import com.tydic.config.impl.ZKConfigProcessTemplate;


public class ZKDeleteNode {
	@Test
	public void test(){
		ZKConfigProcess cp = new ZKConfigProcessTemplate();
		cp.init();
		cp.deleteAllNode("/Configuration/CentralizedConfiguration");
		cp.getAllChild("/", "");
		cp.terminate();
	}
}
