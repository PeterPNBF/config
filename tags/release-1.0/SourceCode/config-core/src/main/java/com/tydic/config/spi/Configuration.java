package com.tydic.config.spi;

import java.util.Vector;


/**
 * 保存配置信息，对应一个zookeeper path
 * @author yuhaiming
 * @date 2014-9-3
 */
public class Configuration {
	public static String ROOT = "/Configuration/default";
	public static String DATA = "/Configuration/default/data";
	public static String INDEX = "/Configuration/default/index";
	/**
	 * 由配置文件读取若，无则</br>
	 * 默认zookeeper环境path=/Configuration/default/env
	 */
	public static String ENV = "/Configuration/default/env";
	public static int MAXINDEXSIZE = 102400;
	public static int MAXDATASIZE = 1048576;
	public static String DEFAULTVERSION = "default";
	public static String INDEXHOMEENVNAME = "INDEX_HOME";
	public static String DATANODEPATTERN = ".*\\..*_.*";
	public static String CHARSET="utf-8";
	/**
	 * 由配置文件读取若无，则
	 * 默认连接主机127.0.0.1:2181
	 */
	public static String HOSTS = "127.0.0.1:2181";
	public static String INDEX_HOME = "/Configuration/default/index";
	private boolean ischanged = false;
	private Vector<Watcher> watchers = new Vector<Watcher>();
	static{
		Configurator configurator = new PropertyConfigurator();
		configurator.doConfigure();
	}
	private String name = null;
	private Object data = null;
	private String index = null;
	private String path = null;
	private String version = null;
	private String charset = null;
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean getIschanged() {
		return ischanged;
	}
	public void setIschanged(boolean ischanged) {
		this.ischanged = ischanged;
	}

	public void addWatcher(Watcher watcher){
		watchers.add(watcher);
	}
	public Vector<Watcher> getWatchers(){
		return watchers;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getVersion() {
		return version;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
}
