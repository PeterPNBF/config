package com.tydic.config.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event.EventType;

import com.tydic.config.compare.Compare;
import com.tydic.config.compare.PropertyInputStreamCompare;
import com.tydic.config.compare.XMLInputStreamCompare;
import com.tydic.config.spi.ConfigFile;
import com.tydic.config.spi.Configuration;
import com.tydic.config.spi.ConfigFile.Type;

/**
 * zookeeper 基础操作工具类, 节点数据变化触发比较InputStream类型数据
 * @see Class {@link ZooKeeperOperator}
 * @author yuhaiming
 * @date 2014-9-3
 */
public final class ZooKeeperTemplate extends ZooKeeperOperator{
	
	@Override
	public void doCheck(WatchedEvent event){
		log.debug(event.toString() + " event.getType=" + event.getType());
		if (event.getType() == EventType.NodeDataChanged) {
			log.info("checking whether config data changed or not|path=[" + event.getPath() + "]");
			Configuration configNode = configurationProcess.getConfigManager().getIndex(event.getPath());
			Object formerData = configNode.getData();
			Object currentData = prepare4Compare(configNode);
			if(currentData==null){
				log.error("can not find config data| configName=[" + configNode.getName()
						+ "], version=[" + configNode.getVersion() + "], index=[" + configNode.getIndex() + "]");
				return;
			}
			doCompare(formerData, configNode);
		}
	}

	/**
	 * 根据文件名configName创建配置比较器<br>
	 * 如 configName 以.xml结尾则创建XMLInputStreamCompare
	 * @param configName 文件名
	 * @return 比较器
	 */
	public Compare<InputStream> createComparator(String configName) {
		ConfigFile configFile = new ConfigFile(configName);
		Type dataType = configFile.judgeType();
		if(dataType == null){
			if(log.isDebugEnabled()){
				log.debug("ignore this data node, name as " + ConfigFile.getTypes() + " is expected.| dataNodeName=[" + configName + "]");
			}
			return null;
		}
		Compare<InputStream> compare = createCompare(dataType);
		return compare;
	}
	
	/**
	 * 取得当前配置<br>
	 * 比较当前索引与原索引若相同（索引中包含一个创建时间），则表示配置未更改，返回空。<br>
	 * 索引改变后有索引取得配置后，更新confIndexNode并返回当前配置
	 * @param confIndexNode 配置信息
	 * @return currentData 当前配置
	 */
	public Object prepare4Compare(Configuration configNode){
		if (configNode == null) {
			log.error("Current configuration is null");
			return null;
		}
		String formerIndex = configNode.getIndex();
		Object currentData = null;
		String currentIndex = configurationProcess.getIndex(configNode.getName(), configNode.getVersion(), true);
		if(currentIndex!=null&&currentIndex.equals(formerIndex)){
			if(log.isDebugEnabled()){
				log.debug("index of current version config not changed|index=[" + currentIndex + "]");
			}
			return null;
		}
		currentData = configurationProcess.getConfigAsBytes(null, null, false, new StringBuilder(currentIndex));
		if(currentData==null){
			log.error("can not find config data| configName=[" + configNode.getName()
					+ "], version=[" + configNode.getVersion() + "], index=[" + currentIndex + "]");
			return null;
		}
		configNode.setIndex(currentIndex);
		configNode.setData(currentData);
		return currentData;
	}
	
	/**
	 * 比较配置是否更新，若更新则调用对应{@link com.tydic.config.spi.Watcher#callService(Vector)}
	 * @param formerData 先前配置数据
	 * @param configNode 当前配置节点
	 */
	public void doCompare(Object formerData, Configuration configNode){
		Compare<InputStream> compare = createComparator(configNode.getName());
		InputStream inputStream1 = null;
		InputStream inputStream2 = null;
		if(compare==null){
			return ;
		}
		Vector<com.tydic.config.spi.Watcher> watcher = configNode.getWatchers();
		if(!watcher.isEmpty()){
			Vector<String> res = new Vector<String>();
			com.tydic.config.spi.Watcher tempWatcher;
			for(int i=0; i< watcher.size(); ++i){
				tempWatcher = watcher.get(i);
				res.clear();
				try {
					inputStream1 = new ByteArrayInputStream((byte[]) formerData);
					inputStream2 = new ByteArrayInputStream((byte[]) configNode.getData());
					if(!compare.compare(inputStream1, inputStream2, tempWatcher.getCheckPoint(),res)){
						log.info("Configuration changed|configname=[" + configNode + "]");
						tempWatcher.callService(res);
					} else {
						log.info("Configuration not changed|configname=[" + configNode.getName() + "]");
					}
				}  finally {
					try {
						inputStream1.close();
						inputStream2.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} else {
			log.warn("watcher does not exist.| configName=[" + configNode.getName() + "]");
		}
	}
	
	public Compare<InputStream> createCompare(Type type){
		Compare<InputStream> compare = null;
		switch(type){
		case PROPERTIES:
			compare = new PropertyInputStreamCompare();
			break;
		case XML:
			compare = new XMLInputStreamCompare();
			break;
		default:
			log.error("unknown config type, .xml or .properties file is expected");
			break;
		}
		return compare;
	}
}
