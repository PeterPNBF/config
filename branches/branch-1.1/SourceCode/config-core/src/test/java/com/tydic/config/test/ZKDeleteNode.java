package com.tydic.config.test;

import org.junit.Test;

import com.tydic.config.ZKConfigProcess;
import com.tydic.config.impl.GenaralZKConfigProcess;


public class ZKDeleteNode {
	@Test
	public void test() throws Exception{
		ZKConfigProcess cp = new GenaralZKConfigProcess();
		cp.init();
		cp.deleteAllNode("/Configuration/CentralizedConfiguration");
		cp.getAllChild("/", "");
		cp.terminate();
	}
}
