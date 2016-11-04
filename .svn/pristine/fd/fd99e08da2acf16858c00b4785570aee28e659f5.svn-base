package com.tydic.config.impl;

import com.tydic.config.spi.DataLocation;

/**
 * 配置位置信息位于Zookeeper，由Zookeeper取得配置的位置信息后，<br>
 * 进一步去该location读取配置，返回json串。<br>
 * <b><u><i>
 * 暂未实现
 * </i></u></b>
 * @author yuhaiming
 * @date 2014-9-11
 */
public class ZKConfigProcessWithDataLocation extends GenaralZKConfigProcess{
	
	private DataLocation dataLocation = null;
	@Override
	public boolean writeConfig(String confName, String version, String author) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String getConfig(String confName, String version, boolean flag,
			StringBuilder targetIndex) {
		// TODO Auto-generated method stub
		String location =  super.getConfig(confName, version, flag, targetIndex);
		return this.dataLocation.getData(location);
	}

	public DataLocation getDataLocation() {
		return dataLocation;
	}

	public void setDataLocation(DataLocation dataLocation) {
		this.dataLocation = dataLocation;
	}
}
