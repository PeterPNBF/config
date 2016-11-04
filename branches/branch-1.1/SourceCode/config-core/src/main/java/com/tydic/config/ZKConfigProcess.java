package com.tydic.config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.zookeeper.KeeperException;

import com.tydic.config.spi.Configuration;
import com.tydic.config.spi.ConfigurationImpl;
import com.tydic.config.spi.ConfWatcher;
import com.tydic.config.spi.PropertyConfigurator;
import com.tydic.config.util.ZooKeeperOperator;
import com.tydic.config.util.ZooKeeperTemplate;

/**
 * 集中配置Zookeeper基础操作类，用于配置读、写、删除，配置更新检测
 * @see ConfigurationProcess
 * @author yuhaiming
 * @date 2014-9-3
 */
public abstract class ZKConfigProcess implements ConfigurationProcess{
	protected ZooKeeperOperator zooKeeperOperator = null;
	private Logger logger = Logger.getLogger(ZKConfigProcess.class);
	protected Configuration conf = null;
	public Configuration getConf() {
		return conf;
	}

	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	public static final String INDEX_HOME = "INDEX_HOME";
	
	public ZKConfigProcess(){
		
	}
	
	public ZKConfigProcess(Configuration conf){
		this.conf = conf;
	}

	public ZooKeeperOperator getZooKeeperOperator() {
		return zooKeeperOperator;
	}

	public void setZooKeeperOperator(ZooKeeperOperator zooKeeperOperator) {
		this.zooKeeperOperator = zooKeeperOperator;
	}
	
	/**
	 * zookeeper连接到{@link Configuration#HOSTS}
	 */
	public void connect() {
		try {
			zooKeeperOperator.connect(conf.getHosts());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 连接到zookeeper及其他初始化
	 * @throws Exception 
	 */
	public void init() throws Exception {

		if(conf==null){
			PropertyConfigurator propertyConfigurator = new PropertyConfigurator();
			conf = propertyConfigurator.parseProperties(null);
		}
		if((conf.getStatus()&0x0001)==0){
			zooKeeperOperator = new ZooKeeperOperator();
		} else {
			zooKeeperOperator = new ZooKeeperTemplate();
		}
		zooKeeperOperator.setConfigurationProcess(this);
		connect();
	}

	public void terminate() {
		try {
			zooKeeperOperator.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *
	 * 根据一条索引获取配置数据
	 * @param targetIndex
	 * @return
	 */
	public String getConfigFromIndex(String targetIndex){
		if(targetIndex!=null&&targetIndex.length()>0){
			return getConfig(null, null, false, new StringBuilder(targetIndex));
		}
		if(logger.isDebugEnabled()){
			logger.debug("Can not find config|targetIndex=[" + targetIndex + "]");
		}
		return null;
	}
	/**
	 *
	 * 根据一条索引获取配置数据
	 * @param targetIndex
	 * @return
	 */
	public InputStream getConfigFromIndexAsInputStream(String targetIndex){
		if(targetIndex!=null&&targetIndex.length()>0){
			return getConfigAsInputStream(null, null, false, new StringBuilder(targetIndex));
		}
		if(logger.isDebugEnabled()){
			logger.debug("Can not find config|targetIndex=[" + targetIndex + "]");
		}
		return null;
	}
	
	/**
	 * 返回zookeeper path节点数据，节点不存在则返回null
	 * @param path
	 * @return String
	 */
	public String getData(String path){
		try {
			return zooKeeperOperator.getString(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("get data error|path=[" + path + "]");
		}
		return null;
	}
	
	/**
	 * 删除zookeeper path节点，path需为叶子节点
	 * @param path
	 */
	public void deleteNode(String path){
		try {
			zooKeeperOperator.delete(path, -1);
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除zookeeper path节点及其子节点
	 * @param path
	 */
	public void deleteAllNode(String path){
		try {
			zooKeeperOperator.deleteAll(path);
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/**
	 * 获取配置，若version版本配置未找到，当flag为true时，找最新的配置，否则返回null
	 * 
	 * @param confName 配置名
	 * @param version 配置版本
	 * @param flag
	 * @param targetIndex 返回找到的目标配置索引
	 * @return 返回配置
	 */
	public abstract byte[] getConfigAsBytes(String confName, String version, boolean flag, StringBuilder targetIndex);
	
	/**
	 * 获取配置，若version版本配置未找到，当flag为true时，找最新的配置，否则返回null
	 * 
	 * @param confName 配置名
	 * @param version 配置版本
	 * @param flag
	 * @param targetIndex 返回找到的目标配置索引
	 * @return 返回配置
	 */
	public abstract String getConfig(String confName, String version, boolean flag, StringBuilder targetIndex);
	
	/**
	 * if version can not find, you can set flag=true to find the latest version.
	 * @param confName
	 * @param version
	 * @param flag
	 * @return
	 */
	public String getConfig(String confName, String version, boolean flag){
		return getConfig(confName, version, flag, null);
	}

	/**
	 * if config with version can not find, find the latest version.
	 * @param confName
	 * @param version
	 * @return
	 */
	public String getConfig(String confName, String version) {
		return getConfig(confName, version, true, null);
	}

	/**
	 * Find the latest version config.
	 * @param confName
	 * @return
	 */
	public String getConfig(String confName) {
		return getConfig(confName, null, true, null);
	}
	
	/**
	 * 取得配置，返回InputStream,
	 * 设置flag=true时，若找不到该版本配置则返回最新配置。
	 * @param confName 配置名
	 * @param version 配置版本
	 * @param flag 
	 * @return InputStream
	 */
	public InputStream getConfigAsInputStream(String confName, String version, boolean flag, StringBuilder targetIndex){
		if(confName.startsWith(conf.getIndex())){
			confName = confName.substring(conf.getIndex().length()+1);
		}
		byte[] configData = getConfigAsBytes(confName, version, flag, targetIndex);
		if(configData==null){
			return null;
		}
		InputStream inputSteam = new ByteArrayInputStream(configData);
		return inputSteam;
	}
	

	/**
	 * 取得配置，返回InputStream,
	 * 设置flag=true时，若找不到该版本配置则返回最新配置。
	 * 配置文件以utf-8编码，其他编码可能出错
	 * @param confName 配置名
	 * @param version 配置版本
	 * @param flag 
	 * @return InputStream
	 */
	public InputStream getConfigAsInputStream(String confName, String version, boolean flag){
		return getConfigAsInputStream(confName, version, flag, null);
	}

	/**
	 * 取得配置，返回InputStream,
	 * 若找不到该版本配置则返回最新配置。
	 * 配置文件以utf-8编码，其他编码可能出错
	 * @param confName 配置名
	 * @param version 配置版本
	 * @return InputStream
	 */
	public InputStream getConfigAsInputStream(String confName, String version) {
		return getConfigAsInputStream(confName, version, true);
	}
	
	/**
	 * 取得配置，返回InputStream,
	 * 若找不到该版本配置则返回最新配置。
	 * 配置文件以utf-8编码，其他编码可能出错
	 * @param confName 配置名
	 * @param version 配置版本
	 * @return InputStream
	 */
	public InputStream getConfigAsInputStream(String confName) {
		return getConfigAsInputStream(confName, null, true);
	}
	

	/**
	 * if version can not find, you can set flag=true to find the lastest
	 * version.
	 * 
	 * @param confName
	 * @param version
	 * @param flag
	 * @return
	 */
	public String getIndex(String confName, String version, boolean flag) {
		return getIndex(confName, version, flag, null);
	}
	
	/**
	 * if version can not find, you can set flag=true to find the lastest
	 * version.
	 * @param confName
	 * @param version
	 * @param flag
	 * @param indexData 用于获取索引数据
	 * @return
	 */
	public String getIndex(String confName, String version, boolean flag, StringBuilder indexData) {
		String indexData_str = getIndex(confName);
		if (indexData_str == null) {
			logger.debug("index not exist|configname=[" + confName + "], version=[" + version + "]");
			return null;
		}
		if(indexData!=null&&indexData.length()==0){
			indexData.append(indexData_str);
		}
		int pos = -1;
		if (version == null) {
			version = new String("default");
		} 
		pos = indexData_str.lastIndexOf(version + "\t");
		String targetIndex = null;
		int lastlinepos = -1;
		if (pos != -1) {
			targetIndex = indexData_str.substring(pos,
					indexData_str.indexOf('\n', pos + 1));
		} else if (flag) {
			lastlinepos = indexData_str.lastIndexOf('\n', indexData_str.length() - 2);
			lastlinepos = lastlinepos == -1 ? 0 : lastlinepos + 1;
			targetIndex = indexData_str.substring(lastlinepos);
			logger.warn("version=[" + version
					+ "] do not find, try to version=["	+ targetIndex.split("\t")[0] + "]|confName=[" + confName + "]");
		} else {
			return null;
		}
		return targetIndex.trim();
	}

	/**
	 * 获取confName 版本为version 配置的索引，没有则返回null
	 * @param confName
	 * @param version
	 * @return
	 */
	public String getIndex(String confName, String version) {
		return getIndex(confName, version, false);
	}

	/**
	 * 获取confName的zookeeper索引path
	 * @param confName
	 * @return
	 */
	public String getIndexPath(String confName) {
		String envData = null;
		String indexPath = null;
		String classpath[] = null;
		List<String> classpathNodeChild = null;
		int i = -1;
		envData = getIndex();
		String[] confNamel = confName.split("/");
		try {
			classpath = envData.split(":");
			for (i = 0; i < classpath.length; ++i) {
				boolean find = true;
				String tmpClasspath = classpath[i];
				for(int j=0; j<confNamel.length&&find; ++j){
					find = false;
					classpathNodeChild = zooKeeperOperator.getChild(tmpClasspath);
					if(classpathNodeChild!=null){
						for (String child : classpathNodeChild) {
							if (child.equals(confNamel[j])) {
								tmpClasspath = tmpClasspath + "/" + confNamel[j];
								find = true;
								break;
							}
						}
					}
				}
				if(find){
					indexPath = tmpClasspath;
					break;
				}
			}
			if (indexPath == null) {
				logger.debug("can not find target config in index path|configName=["
						+ confName + "]");
				return null;
			}
		} catch (KeeperException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (i != -1) {
			conf.getEnvProperties().setProperty(INDEX_HOME, classpath[i]);
		}
		return indexPath;
	}
	
	public String getIndex(){
		String envData = conf.getEnvProperties().getProperty(INDEX_HOME);
		Properties props = new Properties();
		String absoluteEnvPath = conf.getEnv();
		if (envData == null) {
			try {
				props.load(new ByteArrayInputStream(zooKeeperOperator
						.getData(absoluteEnvPath)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (KeeperException e) {
				// TODO Auto-generated catch block
				logger.debug("env path not exist|path=[" + absoluteEnvPath
						+ "], use default index_home");
				props.setProperty(INDEX_HOME, conf.getIndex());
				writeEnv2ZK(INDEX_HOME, conf.getIndex());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			envData = props.getProperty(INDEX_HOME);
			if (envData == null) {
				envData = conf.getIndex();
			}
			conf.setEnvProperties(props);
		}
		return envData;
	}

	/**
	 * 获取confName的所有索引数据
	 * @param confName
	 * @return
	 */
	public String getIndex(String confName) {
		String indexPath = getIndexPath(confName);
		String indexData = null;
		if (indexPath == null) {
			return null;
		}
		try {
			if(zooKeeperOperator.exists(indexPath, true)!=null){
				indexData = zooKeeperOperator.getString(indexPath, true);
			}
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			logger.error("read index error|indexPath=[" + indexPath + "]\n" + e);
			return null;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return indexData;
	}

	/**
	 * 获取confName version 的数据节点zookeeper中path，version不存在时可设置flag=true获取最新版本，否则返回null
	 * @param confName
	 * @param version
	 * @param flag
	 * @param targetIndex
	 * @return
	 */
	public String[] getDataNameList(String confName, String version, boolean flag, StringBuilder targetIndex){
		String targetIndex_str = null;
		if(version==null){
			version = new String(conf.getDefaultVersion());
		}
		if(targetIndex!=null&&targetIndex.length()>0){
			targetIndex_str = new String(targetIndex);
		} else {
			targetIndex_str = getIndex(confName, version, flag);
		}
		if (targetIndex_str == null) {
			logger.debug("Can not find config|configname=[" + confName
					+ "],version=[" + version + "], targetIndex=[" + targetIndex + "]");
			return null;
		}
		logger.debug("targetIndex=[" + targetIndex_str + "]");
		String targetIndexData[] = targetIndex_str.trim().split("\t");
		if(targetIndex!=null&&targetIndex.length()==0){
			targetIndex.append(targetIndex_str.trim());
		}
		String targetDataNameList[] = targetIndexData[3].split(",");
		return targetDataNameList;
	}
	

	/**
	 * 以Property(key, value)形式写到{@link Configuration#ENV}
	 * @param key
	 * @param value
	 */
	public void writeEnv2ZK(String key, String value) {
		String absoluteEnvPath = conf.getEnv();
		byte[] envData = null;
		Properties props = new Properties();
		OutputStream os = null;
		try {
			zooKeeperOperator.createNode(absoluteEnvPath);
			if (zooKeeperOperator.exists(absoluteEnvPath, false) != null) {
				envData = zooKeeperOperator.getData(absoluteEnvPath);
				if (envData != null) {
					props.load(new ByteArrayInputStream(envData));
				}
			}
			props.setProperty(key, value);
			os = new ByteArrayOutputStream();
			props.store(os, null);
			zooKeeperOperator.createOrUpdate(absoluteEnvPath, os.toString()
					.getBytes());

		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void initEnv() {
		Properties props = new Properties();
		try {
			props.load(new ByteArrayInputStream(zooKeeperOperator
					.getData(conf.getEnv())));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 添加配置观察者，当该配置变更时，对比检测点，若变更则调用{@link Observable#callService(Vector)}}
	 * @param configName 配置名
	 * @param version 配置版本
	 * @param xpath 配置是否变更检查点
	 * @param observer 回调类
	 */
	public void addWatcher(String configName, String version, List<String> xpath, Observable observer) {
		ConfWatcher watcher = new ConfWatcher();
		watcher.setConfigName(configName);
		watcher.setVersion(version);
		watcher.addCheckPoint(xpath);
		watcher.addObserver(observer);
		watcher.setConfigurationProcess(this);
		addWatcher(watcher);
	}
	
	/**
	 * 添加配置观察者，当该配置（最新版本）变更时，对比检测点，若变更则调用{@link Observable#callService(Vector)}}
	 * @param configName 配置名
	 * @param xpath 配置是否变更检查点
	 * @param observer 回调类
	 */
	public void addWatcher(String configName, List<String> xpath, Observable observer) {
		addWatcher(configName, null, xpath, observer);
	}

	/**
	 * 添加配置观察者，当该配置（名字为xpath第一个'/'前的部分，版本为最新版本）变更时，对比检测点，若变更则调用{@link Observable#callService(Vector)}}
	 * @param xpath 配置是否变更检查点
	 * @param observer 回调类
	 */
	public void addWatcher(List<String> xpath, Observable observer) {
		addWatcher(null, null, xpath, observer);
	}
	
	/**
	 * 添加配置观察者，当该配置变更时，对比检测点，若变更则调用{@link Observable#callService(Vector)}}
	 * @param configName 配置名
	 * @param version 配置版本
	 * @param xpath 配置是否变更检查点
	 * @param observer 回调类
	 */
	public void addWatcher(String configName, String version, String xpath, Observable observer) {
		ConfWatcher watcher = new ConfWatcher();
		watcher.setConfigName(configName);
		watcher.setVersion(version);
		watcher.addCheckPoint(xpath);
		watcher.addObserver(observer);
		watcher.setConfigurationProcess(this);
		addWatcher(watcher);
	}
	
	/**
	 * 添加配置观察者，当该配置（最新版本）变更时，对比检测点，若变更则调用{@link Observable#callService(Vector)}}
	 * @param configName 配置名
	 * @param xpath 配置是否变更检查点
	 * @param observer 回调类
	 */
	public void addWatcher(String configName, String xpath, Observable observer) {
		addWatcher(configName, null, xpath, observer);
	}

	/**
	 * 添加配置观察者，当该配置（名字为xpath第一个'/'前的部分，版本为最新版本）变更时，对比检测点，若变更则调用{@link Observable#callService(Vector)}}
	 * @param xpath 配置是否变更检查点
	 * @param observer 回调类
	 */
	public void addWatcher(String xpath, Observable observer) {
		addWatcher(null, null, xpath, observer);
	}
	
	@Override
	public void addWatcher(ConfWatcher watcher){
		watcher.setConfigurationProcess(this);
		String path = getIndexPath(watcher.getConfigName());
		if(path==null){
			logger.warn("Config is not found in zookeeper, for configName is null");
			return;
		}
		if (conf == null) {
			conf = new ConfigurationImpl();
			StringBuilder tempIndexData = new StringBuilder();
			watcher.setData(getConfigAsBytes(conf.getName(),watcher.getVersion(),true, tempIndexData));
			if(tempIndexData.length()==0){
				logger.error("Cannot find index|ConfigName=[" + conf.getName() + "], version=[" + watcher.getVersion() + "]");
			}
			conf.setIndex(tempIndexData.toString());
			conf.getConfWatchers().put(path, watcher);
			conf.setVersion(watcher.getVersion());
			try {
				zooKeeperOperator.watch(path);
			} catch (KeeperException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// in future version watch env changes to change config
			// for now, just do nothing.
			logger.warn("env is unwatched!");
		}
	}

	@Override
	public boolean writeConfig(String confName) {
		// TODO Auto-generated method stub
		return writeConfig(confName, null, null);
	}

	@Override
	public boolean writeConfig(String confName, String author) {
		// TODO Auto-generated method stub
		return writeConfig(confName, null, author);
	}

	public void getAllChild(String path, String prefix) {
		// TODO Auto-generated method stub
		try {
			zooKeeperOperator.getAllChild(path, prefix);
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<String> getAllChild(String path) {
		// TODO Auto-generated method stub
		try {
			return zooKeeperOperator.getAllChild(path);
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
