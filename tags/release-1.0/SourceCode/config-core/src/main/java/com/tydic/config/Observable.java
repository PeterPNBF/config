package com.tydic.config;

import java.util.Vector;

/**
 * 应用需实现接口，其中包含callService回调方法
 * @author yuhaiming
 * @date 2014-8-28
 */
public interface Observable {

	/**
	 * 回调函数
	 * @param configName 配置名
	 * @param version 配置版本
	 * @param args former配置与current配置不同点
	 */
	public void callService(String configName, String version, Vector<String> args);
}
