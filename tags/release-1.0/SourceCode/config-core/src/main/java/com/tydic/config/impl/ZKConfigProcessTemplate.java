package com.tydic.config.impl;

import com.tydic.config.ZKConfigProcess;
import com.tydic.config.util.ZooKeeperOperator;

/**
 * 集中配置操作类，忽略配置更新处理，用于配置读、写、删除等不需监控配置更新的操作
 * @see ZKConfigProcess
 * @author yuhaiming
 * @date 2014-9-3
 */
public final class ZKConfigProcessTemplate extends GenaralZKConfigProcess{
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		if(zooKeeperOperator==null){
			zooKeeperOperator = new ZooKeeperOperator();
			zooKeeperOperator.setConfigurationProcess(this);
		}
		super.init();
	}
}
