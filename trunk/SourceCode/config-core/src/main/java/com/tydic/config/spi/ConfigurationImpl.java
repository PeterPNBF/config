package com.tydic.config.spi;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 保存配置信息，对应一个zookeeper path
 * @author yuhaiming
 * @date 2014-9-3
 */
public class ConfigurationImpl implements Configuration {
	private volatile String root = "/Configuration/default";
	private volatile String data = "/Configuration/default/data";
	private volatile String index = "/Configuration/default/index";
	private volatile String env = "/Configuration/default/env";
	private volatile int maxIndexSize = 102400;
	private volatile int maxDataSize = 1048576;
	private volatile String defaultVersion = "default";
	private volatile String dataNodePattern = ".*\\..*_.*";
	private volatile String charset ="utf-8";
	private volatile String hosts;
	private volatile boolean ischanged = false;
	private volatile Map<String, ConfWatcher> watchers = new HashMap<String, ConfWatcher>();
	private volatile String name = null;
	private volatile String path = null;
	private volatile String version = null;
	private volatile Properties envProperties = new Properties();
	private volatile int status = 0x0000;
	
	
	/* @inheritDoc
	 * @see com.tydic.config.spi.Configuration#getRoot()
	 */
	@Override
	public String getRoot() {
		return root;
	}
	/* (non-Javadoc)
	 * @see com.tydic.config.spi.Configuration#setRoot(java.lang.String)
	 */
	@Override
	public void setRoot(String root) {
		this.root = root;
	}
	/* (non-Javadoc)
	 * @see com.tydic.config.spi.Configuration#getData()
	 */
	@Override
	public String getData() {
		return data;
	}
	/* (non-Javadoc)
	 * @see com.tydic.config.spi.Configuration#setData(java.lang.String)
	 */
	@Override
	public void setData(String data) {
		this.data = data;
	}
	/* (non-Javadoc)
	 * @see com.tydic.config.spi.Configuration#getIndex()
	 */
	@Override
	public String getIndex() {
		return index;
	}
	/* (non-Javadoc)
	 * @see com.tydic.config.spi.Configuration#setIndex(java.lang.String)
	 */
	@Override
	public void setIndex(String index) {
		this.index = index;
	}
	/* (non-Javadoc)
	 * @see com.tydic.config.spi.Configuration#getEnv()
	 */
	@Override
	public String getEnv() {
		return env;
	}
	/* (non-Javadoc)
	 * @see com.tydic.config.spi.Configuration#setEnv(java.lang.String)
	 */
	@Override
	public void setEnv(String env) {
		this.env = env;
	}
	/* (non-Javadoc)
	 * @see com.tydic.config.spi.Configuration#getMaxIndexSize()
	 */
	@Override
	public int getMaxIndexSize() {
		return maxIndexSize;
	}
	/* (non-Javadoc)
	 * @see com.tydic.config.spi.Configuration#setMaxIndexSize(int)
	 */
	@Override
	public void setMaxIndexSize(int maxIndexSize) {
		this.maxIndexSize = maxIndexSize;
	}
	/* (non-Javadoc)
	 * @see com.tydic.config.spi.Configuration#getMaxDataSize()
	 */
	@Override
	public int getMaxDataSize() {
		return maxDataSize;
	}
	/* (non-Javadoc)
	 * @see com.tydic.config.spi.Configuration#setMaxDataSize(int)
	 */
	@Override
	public void setMaxDataSize(int maxDataSize) {
		this.maxDataSize = maxDataSize;
	}
	/* (non-Javadoc)
	 * @see com.tydic.config.spi.Configuration#getDefaultVersion()
	 */
	@Override
	public String getDefaultVersion() {
		return defaultVersion;
	}
	/* (non-Javadoc)
	 * @see com.tydic.config.spi.Configuration#setDefaultVersion(java.lang.String)
	 */
	@Override
	public void setDefaultVersion(String defaultVersion) {
		this.defaultVersion = defaultVersion;
	}
	/* (non-Javadoc)
	 * @see com.tydic.config.spi.Configuration#getDataNodePattern()
	 */
	@Override
	public String getDataNodePattern() {
		return dataNodePattern;
	}
	/* @inheritDoc
	 * @see com.tydic.config.spi.Configuration#setDataNodePattern(java.lang.String)
	 */
	@Override
	public void setDataNodePattern(String dataNodePattern) {
		this.dataNodePattern = dataNodePattern;
	}
	/* (non-Javadoc)
	 * @see com.tydic.config.spi.Configuration#getCharset()
	 */
	@Override
	public String getCharset() {
		return charset;
	}
	/* (non-Javadoc)
	 * @see com.tydic.config.spi.Configuration#setCharset(java.lang.String)
	 */
	@Override
	public void setCharset(String charset) {
		this.charset = charset;
	}
	/* (non-Javadoc)
	 * @see com.tydic.config.spi.Configuration#getHosts()
	 */
	@Override
	public String getHosts() {
		return hosts;
	}
	/* (non-Javadoc)
	 * @see com.tydic.config.spi.Configuration#setHosts(java.lang.String)
	 */
	@Override
	public void setHosts(String hosts) {
		this.hosts = hosts;
	}
	/* (non-Javadoc)
	 * @see com.tydic.config.spi.Configuration#isIschanged()
	 */
	@Override
	public boolean isIschanged() {
		return ischanged;
	}
	/* (non-Javadoc)
	 * @see com.tydic.config.spi.Configuration#setIschanged(boolean)
	 */
	@Override
	public void setIschanged(boolean ischanged) {
		this.ischanged = ischanged;
	}
	/* (non-Javadoc)
	 * @see com.tydic.config.spi.Configuration#getWatchers()
	 */
	@Override
	public Map<String, ConfWatcher> getConfWatchers() {
		return watchers;
	}
	/* (non-Javadoc)
	 * @see com.tydic.config.spi.Configuration#setWatchers(java.util.List)
	 */
	@Override
	public void setConfWatchers(Map<String, ConfWatcher> watchers) {
		this.watchers = watchers;
	}
	/* (non-Javadoc)
	 * @see com.tydic.config.spi.Configuration#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	/* (non-Javadoc)
	 * @see com.tydic.config.spi.Configuration#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}
	/* (non-Javadoc)
	 * @see com.tydic.config.spi.Configuration#getPath()
	 */
	@Override
	public String getPath() {
		return path;
	}
	/* (non-Javadoc)
	 * @see com.tydic.config.spi.Configuration#setPath(java.lang.String)
	 */
	@Override
	public void setPath(String path) {
		this.path = path;
	}
	/* (non-Javadoc)
	 * @see com.tydic.config.spi.Configuration#getVersion()
	 */
	@Override
	public String getVersion() {
		return version;
	}
	/* (non-Javadoc)
	 * @see com.tydic.config.spi.Configuration#setVersion(java.lang.String)
	 */
	@Override
	public void setVersion(String version) {
		this.version = version;
	}
	@Override
	public Properties getEnvProperties() {
		return envProperties;
	}
	@Override
	public void setEnvProperties(Properties envProperties) {
		this.envProperties = envProperties;
	}
	@Override
	public int getStatus() {
		// TODO Auto-generated method stub
		return this.status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
}