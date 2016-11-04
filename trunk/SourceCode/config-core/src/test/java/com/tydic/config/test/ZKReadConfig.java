package com.tydic.config.test;

import org.junit.Test;

import com.tydic.config.ZKConfigProcess;
import com.tydic.config.impl.GenaralZKConfigProcess;

/**
 * 读取Zookeeper中索引、环境节点数据及vds.xml 1.8版本，vds.properties 1.6版本的数据。
 * @author yuhaiming
 * @date 2014-9-11
 */
public class ZKReadConfig {
	@Test
	public void test() throws Exception{
		ZKConfigProcess cp = new GenaralZKConfigProcess();
		cp.init();
		String config = cp.getData(cp.getConf().getIndex() + "/vds.xml");
		System.out.println(cp.getConf().getIndex() + "/vds.xml=[\n" + config + "\n]");
		config = cp.getData(cp.getConf().getIndex() + "/vds.properties");
		System.out.println(cp.getConf().getIndex() + "/vds.properties=[\n" + config + "\n]");
		config = cp.getData(cp.getConf().getEnv());
		System.out.println(cp.getConf().getEnv() + "=[\n" + config + "\n]");
		config = cp.getConfig("vds.xml", "1.8");
		System.out.println("vds.xml=[\n" + config + "\n]");
		config = cp.getConfig("vds.properties", "1.6");
		System.out.println("vds.properties=[\n" + config + "\n]");
		
		cp.getAllChild("/", "");
		cp.terminate();
		
	}
}
