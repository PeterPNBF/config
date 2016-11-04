package com.tydic.config.test;

import org.junit.Test;

import com.tydic.config.ConfigurationProcess;
import com.tydic.config.impl.GenaralZKConfigProcess;

/**
 * 测试写配置，并Zookeeper目录结构
 * @author yuhaiming
 * @date 2014-9-11
 */
public class Test4WriteConfig {
	@Test
	public void test() throws Exception{
		ConfigurationProcess cp = new GenaralZKConfigProcess();
		cp.init();
		cp.writeConfig("service/vds.xml", "1.4", null);
//		cp.writeConfig("vds.properties", "1.2", "yuhaiming@tydic.com");
		cp.terminate();
	}
}
