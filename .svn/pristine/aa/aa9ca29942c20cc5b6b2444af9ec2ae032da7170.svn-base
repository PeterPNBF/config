package com.tydic.config;

import org.junit.Test;

import com.tydic.config.ConfigurationProcess;
import com.tydic.config.impl.ZKConfigProcessTemplate;

/**
 * 测试写配置，并Zookeeper目录结构
 * @author yuhaiming
 * @date 2014-9-11
 */
public class ConfigurationProcessTest4Write {
	@Test
	public void test(){
		ConfigurationProcess cp = new ZKConfigProcessTemplate();
		cp.init();
		cp.writeConfig("vds.xml", "1.2", "yuhaiming@tydic.com");
		cp.writeConfig("vds.properties", "1.2", "yuhaiming@tydic.com");
		cp.terminate();
	}
}
