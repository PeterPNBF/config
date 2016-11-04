package com.tydic.config.spi;

import java.util.Collection;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.tydic.config.Observable;
import com.tydic.config.ZKConfigProcess;

/**
 * 配置观察者信息及回调类
 * @author yuhaiming
 * @date 2014-9-3
 */
public class Watcher {
	private Vector<String> checkPoint = new Vector<String>();
	private String configName=null;
	private String version = null;
	private ZKConfigProcess configurationProcess=null;
	private Vector<Observable> observers = new Vector<Observable>();
	private Logger log = Logger.getLogger(Watcher.class);
	public void addCheckPoint(String point){
		checkPoint.add(point);
	}
	public void addCheckPoint(Collection<? extends String> points){
		checkPoint.addAll(points);
	}
	public Vector<String> getCheckPoint(){
		return checkPoint;
	}
	public void setConfigName(String configName) {
		this.configName = configName;
	}
	public String getConfigName() {
		return configName;
	}
	public void setConfigurationProcess(ZKConfigProcess configurationProcess) {
		this.configurationProcess = configurationProcess;
	}
	public ZKConfigProcess getConfigurationProcess() {
		return configurationProcess;
	}
	

	/**
	 * Call service.
	 */
	public void callService(Vector<String> args){
		if(!observers.isEmpty()){
			for(int i=0; i<observers.size(); ++i){
				observers.get(i).callService(configName, version, args);
			}
		} else {
			log.warn("No Observer, just do nothing");
		}
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getVersion() {
		return version;
	}
	public void addObserver(Observable observer) {
		this.observers.add(observer);
	}
	public Vector<Observable> getObserver() {
		return observers;
	}
}
