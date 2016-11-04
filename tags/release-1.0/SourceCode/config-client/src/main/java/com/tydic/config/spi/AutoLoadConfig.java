package com.tydic.config.spi;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AutoLoadConfig {
	private static Log log = LogFactory.getLog(AutoLoadConfig.class);
	private String root;
	private String data;
	private String index;
	private String env;
	private int maxConfigurationSize = -1;
	private int maxIndexSize = -1;
	private int maxDataSize = -1;
	private String defaultVersion;
	private String indexHomeEnvName;
	private String dataNodePattern;
	private String charset;
	private String hosts;
	private String indexHome;
	public String configName;
	private String configPath;
	
	private String[] names;
	private String[] paths;
	public boolean doConfigure() {
		if(log.isDebugEnabled()){
			log.debug("get configure from properties");
		}
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(ConfigProperties.LOCALCONFIG); 
		if(in==null){
			log.debug(ConfigProperties.LOCALCONFIG + " is not found, use default config");
		} else {
			Properties props = new Properties();
			try {
				props.load(in);
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error(ConfigProperties.LOCALCONFIG + " read error");
				e.printStackTrace();
			}
			log.debug("config file=[");
			log.debug(props.toString());
			log.debug("] end config file");
			String tmp = props.getProperty(ConfigProperties.CONFIGPATH);
			if(tmp!=null){
				paths = tmp.split(",");
			}
			tmp = props.getProperty(ConfigProperties.CONFIGNAME);
			if(tmp!=null){
				names = tmp.split(",");
			}
		}
		doConfigCoreConfig();
		if(paths==null&&names==null){
			return false;
		} else {
			return true;
		}
	}
	
	public void doConfigCoreConfig(){
		if(this.charset!=null){
			Configuration.CHARSET = this.charset;
		}
		if(root!=null){
			Configuration.ROOT = root;
		}
		if(data!=null){
			Configuration.DATA = data;
		} else if(root!=null){
			Configuration.DATA = root + "/data";
		}
		if(index!=null){
			Configuration.INDEX = index;
		} else if(root!=null){
			Configuration.INDEX = root + "/index";
		}
		if(env!=null){
			Configuration.ENV = env;
		} else if(root!=null){
			Configuration.ENV = root + "/env";
		}
		if(hosts!=null){
			Configuration.HOSTS = hosts;
		}
		if(maxIndexSize!=-1){
			Configuration.MAXINDEXSIZE = maxIndexSize;
		}
		if(charset!=null){
			Configuration.CHARSET = charset;
		}
		if(maxDataSize!=-1){
			Configuration.MAXDATASIZE = maxDataSize;
		}
		if(defaultVersion!=null){
			Configuration.DEFAULTVERSION = defaultVersion;
		}
		if(indexHome!=null){
			Configuration.INDEX_HOME = indexHome;
		} else if(root!=null){
			Configuration.INDEX_HOME = root + "/index";
		}
		if(this.dataNodePattern!=null){
			Configuration.DATANODEPATTERN = dataNodePattern;
		}
		if(this.configName!=null){
			names = configName.split(",");
		}
		if(configPath!=null){
			paths = configPath.split(",");
		}
	}
	
	public String[] getNames() {
		return names;
	}
	public void setNames(String[] names) {
		this.names = names;
	}
	public String[] getPaths() {
		return paths;
	}
	public void setPaths(String[] paths) {
		this.paths = paths;
	}

	public String getRoot() {
		return root;
	}
	public void setRoot(String root) {
		this.root = root;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getEnv() {
		return env;
	}
	public void setEnv(String env) {
		this.env = env;
	}
	public int getMaxConfigurationSize() {
		return maxConfigurationSize;
	}
	public void setMaxConfigurationSize(int maxConfigurationSize) {
		this.maxConfigurationSize = maxConfigurationSize;
	}
	public int getMaxIndexSize() {
		return maxIndexSize;
	}
	public void setMaxIndexSize(int maxIndexSize) {
		this.maxIndexSize = maxIndexSize;
	}
	public int getMaxDataSize() {
		return maxDataSize;
	}
	public void setMaxDataSize(int maxDataSize) {
		this.maxDataSize = maxDataSize;
	}
	public String getDefaultVersion() {
		return defaultVersion;
	}
	public void setDefaultVersion(String defaultVersion) {
		this.defaultVersion = defaultVersion;
	}
	public String getIndexHomeEnvName() {
		return indexHomeEnvName;
	}
	public void setIndexHomeEnvName(String indexHomeEnvName) {
		this.indexHomeEnvName = indexHomeEnvName;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getHosts() {
		return hosts;
	}
	public void setHosts(String hosts) {
		this.hosts = hosts;
	}
	public String getIndexHome() {
		return indexHome;
	}
	public void setIndexHome(String indexHome) {
		this.indexHome = indexHome;
	}
	public String getConfigName() {
		return configName;
	}
	public void setConfigName(String configName) {
		this.configName = configName;
	}
	public String getConfigPath() {
		return configPath;
	}
	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}
	public String getDataNodePattern() {
		return dataNodePattern;
	}
	public void setDataNodePattern(String dataNodePattern) {
		this.dataNodePattern = dataNodePattern;
	}
}
