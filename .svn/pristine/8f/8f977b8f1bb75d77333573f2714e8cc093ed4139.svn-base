package com.tydic.config.spi;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 配置文件为centralized-config.properties，加载基础配置，若无则使用默认值。
 * @see Class {@link Configurator}
 * @author yuhaiming
 * @date 2014-9-3
 */
public class ConfigProperties{
	public static final String LOCALCONFIG = "centralized-config.properties";
	private static Logger log = Logger.getLogger(ConfigProperties.class);

	public static final String CONFIGDIRECTORY = "config-directory";
	public static final String CONFIGNAME = "config-name";
	public static final String CONFIGPATH = "config-path";
	public static final String CHECKPOINTPREFIX = "check-point-";
	private Map<String, List<String>> configMaps = new HashMap<String, List<String>>();
	private String directory;
	private String[] names;
	private String[] paths;
	public Map<String, List<String>> getConfigMaps() {
		return configMaps;
	}
	public void setConfigMaps(Map<String, List<String>> configMaps) {
		this.configMaps = configMaps;
	}
	public boolean doConfigure() {
		// TODO Auto-generated method stub
		if(log.isDebugEnabled()){
			log.debug("get configure from properties");
		}
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(ConfigProperties.LOCALCONFIG); 
		if(in==null){
			log.debug(ConfigProperties.LOCALCONFIG + " is not found, use default config");
		}
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
		String tmp = props.getProperty(ConfigProperties.CONFIGDIRECTORY);
		if(tmp!=null){
			log.debug("config-derectory=" + tmp);
			directory = tmp;
		} else {
			log.error("directory not config");
			return false;
		}
		tmp = props.getProperty(ConfigProperties.CONFIGPATH);
		if(tmp!=null){
			setPaths(tmp.split(","));
		}
		tmp = props.getProperty(ConfigProperties.CONFIGNAME);
		String[] configs = null;
		if(tmp!=null){
			configs = tmp.split(",");
			setNames(configs);
		}
		for(int i=0; i<configs.length; ++i){
			tmp = props.getProperty(ConfigProperties.CHECKPOINTPREFIX+configs[i]);
			String[] tmps = tmp.split(",");
			List<String> tmpl = new ArrayList<String>();
			for(int j=0; j<tmps.length; ++j){
				tmpl.add(tmps[j]);
			}
			configMaps.put(configs[i], tmpl);
		}
		if(log.isDebugEnabled()){
			log.debug("after configure from properties");
			log.debug(configMaps);
		}
		return true;
	}
	public String getDirectory() {
		return directory;
	}
	public void setDirectory(String directory) {
		this.directory = directory;
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
}
