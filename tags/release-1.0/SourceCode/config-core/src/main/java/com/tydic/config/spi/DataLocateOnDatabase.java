package com.tydic.config.spi;

import java.sql.Connection;

/**
 * 由配置位置信息加载配置
 * @author yuhaiming
 * @date 2014-9-11
 */
public abstract class DataLocateOnDatabase implements DataLocation{

	@Override
	public String getData(String args) {
		// TODO Auto-generated method stub
		init();
		terminate();
		return null;
	}
	
	public abstract Connection getConnection(String args);
	public abstract void init();
	public abstract void terminate();

}
