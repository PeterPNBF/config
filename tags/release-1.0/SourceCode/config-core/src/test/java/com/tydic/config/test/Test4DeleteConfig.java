package com.tydic.config.test;

import org.junit.Test;

import com.tydic.config.ConfigurationProcess;
import com.tydic.config.impl.ZKConfigProcessTemplate;

/**
 * 删除测试
 * @author yuhaiming
 * @date 2014-9-11
 */
public class Test4DeleteConfig {
	@Test
	public void test(){
		ConfigurationProcess cp = new ZKConfigProcessTemplate();
		cp.init();
		
		//根据配置名删除
		cp.deleteConfig("vds.properties");
		cp.deleteConfig("vds.xml");
		
		cp.terminate();
	}
}
