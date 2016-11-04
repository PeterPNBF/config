package com.tydic.config.test;

import org.junit.Test;

import com.tydic.config.ZKConfigProcess;
import com.tydic.config.impl.ZKConfigProcessTemplate;
import com.tydic.config.spi.Configuration;

/**
 * 写配置到Zookeeper，并打印Zookeeper目录结构
 * @author yuhaiming
 * @date 2014-9-12
 */
public class CopyOfWrite2ZKAndPrintDirectory {
	@Test
	public void test(){
		ZKConfigProcess cp = new ZKConfigProcessTemplate();
		cp.init();
		
		//写配置其他方法，无版本号则默认为{@link class Configuration#DEFAULTVERSION}
//		cp.write2ZK("vds.xml", "1.8", "yuhaiming@tydic.com");
//		cp.write2ZK("vds.properties");
		
		/**
		 * 打印完整目录结构
		 */
		//打印vds.xml索引数据
		String config = cp.getIndex("service/vds.xml");
		System.out.println(Configuration.INDEX + "/service/vds.xml=[\n" + config + "\n]");

		
		//打印环境节点数据
		config = cp.getData(Configuration.ENV);
		System.out.println(Configuration.ENV + "=[\n" + config + "\n]");
		
		//获取vds.xml 1.8版本的数据并打印
		config = cp.getConfig("vds.xml", "1.8");
		System.out.println("vds.xml=[\n" + config + "\n]");
		
		//获取vds.properties 1.6版本的数据并打印
		config = cp.getConfig("vds.properties", "1.6");
		System.out.println("vds.properties=[\n" + config + "\n]");
		
		//打印目录结构
		cp.getAllChild("/", "  ");
		cp.terminate();
		
	}
}
