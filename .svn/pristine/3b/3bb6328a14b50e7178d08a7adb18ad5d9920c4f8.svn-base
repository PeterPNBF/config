package com.tydic.config.test;

import java.util.Vector;

import com.tydic.config.ConfigurationProcess;
import com.tydic.config.Observable;
import com.tydic.config.impl.GenaralZKConfigProcess;

/**
 * 应用测试集中配置于Zookeeper，配置动态更新后触发:<br>
 * {@link Observable#callService(String, String, Vector)}
 * @author yuhaiming
 * @date 2014-9-11
 */
public class Test1 {
	public static void main(String[] args) throws InterruptedException{
		
		//创建集中处理类并初始化
		ConfigurationProcess configurationProcess = new GenaralZKConfigProcess();
		configurationProcess.init();

		Vector<String> checkPoint = new Vector<String>();
		checkPoint.add("/vds/sharding/table/partition/accept");
		checkPoint.add("/vds/sharding/table/partition/accept2");
		
		// 添加vds.xml的观察者
		Observable observer = new ObserverImpl();
		configurationProcess.addWatcher("vds.xml", checkPoint, observer);
		
		// 添加 vds.properties的观察者
		configurationProcess.addWatcher("vds.properties", "1.2", "passwd", observer);
		
		
		//等待配置更新
		//配置更新后，若对应watcher的检测点发生变化则调用对应watcher.update()
		Thread.sleep(5000000);
	}
}
