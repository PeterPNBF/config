package com.tydic.config;

import org.apache.zookeeper.KeeperException;
import org.junit.Test;

import com.tydic.config.ZKConfigProcess;
import com.tydic.config.impl.GenaralZKConfigProcess;


/**
 * 测试删除节点及其子节点
 * @author yuhaiming
 * @date 2014-9-11
 */
public class ZKConfigProcessTest4DeleteAllNode {
	@Test
	public void test() throws KeeperException, InterruptedException{
		ZKConfigProcess cp = new GenaralZKConfigProcess();
		cp.connect();
		cp.deleteAllNode("/Configuration/projectName");
		cp.getAllChild("/", "");
		cp.terminate();
		
	}
}
