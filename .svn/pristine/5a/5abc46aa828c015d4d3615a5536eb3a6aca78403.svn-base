package com.tydic.config.spi;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class ConfigManager {
	private Map<String, Configuration> index = new HashMap<String, Configuration>();
	private Properties env = new Properties();

	public void registerIndex(Configuration conf){
		index.put(conf.getPath(), conf);	
	}
	public void deregisterIndex(String path){
		index.remove(path);
	}

	public void registerEnv(String key, String value){
		env.put(key, value);	
	}
	public void deregisterEnv(String key){
		env.remove(key);
	}
	public void setEnv(Properties props){
		env = props;	
	}
	public String getEnv(String name){
		return env.getProperty(name);
	}
	public Configuration getIndex(String indexNodeName){
		Configuration  conf= index.get(indexNodeName);
		return conf;
	}

	public Properties getEnvs(){
		return env;
	}
}
