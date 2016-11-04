package com.tydic.config;

import org.apache.zookeeper.KeeperException;
import org.junit.Test;

import com.tydic.config.ZKConfigProcess;
import com.tydic.config.impl.ZKConfigProcessTemplate;

/**
 * 测试删除配置根据配置名
 * @author yuhaiming
 * @date 2014-9-11
 */
public class ZKConfigProcessTest4DeleteAllConfig {
	@Test
	public void test() throws KeeperException, InterruptedException{
		
		//创建一个忽略配置更新处理的配置处理类，用于删除配置操作
		ZKConfigProcess cp = new ZKConfigProcessTemplate();
		cp.init();
		/**
		 * 删除所有各个版本的该配置
		 */
		cp.deleteConfig("vds2.xml");
		cp.getAllChild("/", "");
		cp.terminate();
	}
}
