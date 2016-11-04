package com.tydic.config.spi;

import java.util.Map;
import java.util.Properties;

/**
 * @author yhm
 */
public interface Configuration {

	/**
	 * Root node of centralized configuration in zookeeper, all other configuration node belongs to.
	 * @return
	 */
	public String getRoot();

	/**
	 * Root node of centralized configuration in zookeeper, all other configuration node belongs to.
	 * @return
	 */
	public void setRoot(String root);

	/**
	 * Data node of centralized configuration, store configuration data.
	 * @return
	 */
	public String getData();
	
	/**
	 * data node of centralized configuration, store configuration data.
	 * @return
	 */
	public void setData(String data);

	/**
	 * Index node of centralized configuration, store index of data with version info. 
	 * @return
	 */
	public String getIndex();

	/**
	 * Index node of centralized configuration, store index of data with version info. 
	 * @return
	 */
	public void setIndex(String index);

	/**
	 * Env node of centralized configuration, store classpath info. 
	 * @return
	 */
	public String getEnv();

	/**
	 * Env node of centralized configuration, store classpath info. 
	 * @return
	 */
	public void setEnv(String env);

	public int getMaxIndexSize();

	public void setMaxIndexSize(int maxIndexSize);

	public int getMaxDataSize();

	public void setMaxDataSize(int maxDataSize);

	public String getDefaultVersion();

	public void setDefaultVersion(String defaultVersion);

	public String getDataNodePattern();

	public void setDataNodePattern(String dataNodePattern);

	public String getCharset();

	public void setCharset(String charset);

	public String getHosts();

	public void setHosts(String hosts);

	public boolean isIschanged();

	public void setIschanged(boolean ischanged);

	public Map<String, ConfWatcher> getConfWatchers();

	public void setConfWatchers(Map<String, ConfWatcher> watchers);

	public String getName();

	public void setName(String name);

	public String getPath();

	public void setPath(String path);

	public String getVersion();

	public void setVersion(String version);
	
	public Properties getEnvProperties();
	
	public void setEnvProperties(Properties envProperties);
	
	/**
	 * Initial status 0x0001 means start use notification when data of configuration changed.
	 * 
	 * @return
	 */
	public int getStatus();
	
	/**
	 * Initial status 0x0001 means start use notification when data of configuration changed.
	 * 
	 * @return
	 */
	public void setStatus(int status);

}