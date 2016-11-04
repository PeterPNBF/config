package com.tydic.config.refresh;


import java.util.List;
import java.util.Map.Entry;
import java.util.Vector;

import com.tydic.config.ConfigurationProcess;
import com.tydic.config.Observable;
import com.tydic.config.impl.GenaralZKConfigProcess;
import com.tydic.config.spi.ConfigProperties;


/**
 * 应用测试集中配置于Zookeeper，配置动态更新后触发:<br>
 * {@link Observable#callService(String, String, Vector)}
 * @author yuhaiming
 * @date 2014-9-11
 */
public class CentralizedConfig {

	public static void main(String[] args){
		
		//创建集中处理类并初始化
		ConfigurationProcess configurationProcess = new GenaralZKConfigProcess();
		configurationProcess.init();
		
		ConfigProperties configurator = new ConfigProperties();

		if(!configurator.doConfigure()){
			return;
		}
		
		ObserverImpl observer = new ObserverImpl();
		observer.setConfigurationProcess(configurationProcess);
		observer.setDes(configurator.getDirectory());
		observer.setFileNames(configurator.getConfigMaps().keySet());
		observer.init();
		for(Entry<String, List<String>> entrySet : configurator.getConfigMaps().entrySet()){
			String key = entrySet.getKey();
			configurationProcess.addWatcher(key, entrySet.getValue(), observer);
		}
		
		//等待配置更新
		//配置更新后，若对应watcher的检测点发生变化则调用对应watcher.update()
		while(true){
			try {
				Thread.sleep(5000000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
