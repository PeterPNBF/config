package com.tydic.config;

import org.junit.Test;

import com.tydic.config.spi.ConfigFile;
import com.tydic.config.spi.ConfigFile.Type;

/**
 * 测试配置文件类型获取
 * @author yuhaiming
 * @date 2014-9-11
 */
public class ConfigFileTest {
	@Test
	public void test(){
		Type [] types = Type.values();
		for(Type type:types){
			switch (type) { 
			case PROPERTIES:
				System.out.println(type);
				break;
			case XML:
				System.out.println(type);
				break;
			default:
				break;
			}
		}
		System.out.println(ConfigFile.getTypes());
	}
}
