package com.tydic.config;

import org.junit.Test;

import com.tydic.config.ZKConfigProcess;
import com.tydic.config.impl.ZKConfigProcessTemplate;

/**
 * 测试写环境节点函数
 * @author yuhaiming
 * @date 2014-9-11
 */
public class ZKConfigProcessTest4WriteEnv {
	@Test
	public void test(){
		ZKConfigProcess cp = new ZKConfigProcessTemplate();
		cp.init();
		
		/**
		 * 写方法
		 */
		cp.writeEnv2ZK("classpath", "/Configuration/projectName/index");
		
		cp.getAllChild("/", "");
		cp.terminate();
		
	}
}
