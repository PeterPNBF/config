package com.tydic.config;

import org.apache.zookeeper.KeeperException;
import org.junit.Test;

import com.tydic.config.ZKConfigProcess;
import com.tydic.config.impl.ZKConfigProcessTemplate;


/**
 * 测试删除配置根据配置名和版本
 * @author yuhaiming
 * @date 2014-9-11
 */
public class ZKConfigProcessTest4DeleteConfig {
	@Test
	public void test() throws KeeperException, InterruptedException{
		ZKConfigProcess cp = new ZKConfigProcessTemplate();
		cp.init();
		cp.deleteConfig("vds2.xml","1.0");
		cp.getAllChild("/", "");
		cp.terminate();
	}
}
