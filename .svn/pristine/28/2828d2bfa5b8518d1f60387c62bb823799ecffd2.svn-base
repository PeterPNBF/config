package com.tydic.config.test;

import org.junit.Test;

import com.tydic.config.ZKConfigProcess;
import com.tydic.config.impl.GenaralZKConfigProcess;
import com.tydic.config.spi.PropertyConfigurator;


public class WriteEnv2ZK {
	@Test
	public void test() throws Exception{
		ZKConfigProcess cp = new GenaralZKConfigProcess();
		cp.init();
		cp.deleteNode(cp.getConf().getEnv());
		cp.writeEnv2ZK(PropertyConfigurator.INDEX_HOME, cp.getConf().getIndex());
		String config = new String(cp.getData(cp.getConf().getEnv()));
		System.out.println(cp.getConf().getEnv() + "=[\n" + config + "\n]");
		cp.getAllChild("/", "");
		cp.terminate();
	}
}
