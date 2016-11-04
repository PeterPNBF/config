package com.tydic.config.test;

import org.junit.Test;

import com.tydic.config.ConfigurationProcess;
import com.tydic.config.impl.ZKConfigProcessTemplate;

/**
 * 删除测试
 * @author yuhaiming
 * @date 2014-9-11
 */
public class Test4ReadConfig {
	@Test
	public void test(){
		ConfigurationProcess cp = new ZKConfigProcessTemplate();
		cp.init();
		
		//根据配置名获取，版本为默认版本
		String config = cp.getConfig("vds.properties");
		System.out.println("vds.properties=[\n" + config + "\n]");
		
		//根据配置名和版本号获取
		config = cp.getConfig("service", "1.4");
		System.out.println("vds.xml=[\n" + config + "\n]");
		cp.terminate();
	}
}
