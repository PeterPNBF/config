package com.tydic.config.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event.EventType;

import com.tydic.config.compare.Compare;
import com.tydic.config.compare.PropertyInputStreamCompare;
import com.tydic.config.compare.XMLInputStreamCompare;
import com.tydic.config.spi.ConfigFile;
import com.tydic.config.spi.Configuration;
import com.tydic.config.spi.ConfigFile.Type;
import com.tydic.config.spi.ConfWatcher;

/**
 * zookeeper 基础操作工具类, 节点数据变化触发比较InputStream类型数据
 * @see Class {@link ZooKeeperOperator}
 * @author yuhaiming
 * @date 2014-9-3
 */
public class ZooKeeperTemplate extends ZooKeeperOperator{
	
	@Override
	public void doCheck(WatchedEvent event){
		log.debug(event.toString() + " event.getType=" + event.getType());
		if (event.getType() == EventType.NodeDataChanged) {
			log.info("checking whether config data changed or not|path=[" + event.getPath() + "]");
			Configuration conf = configurationProcess.getConf();
			ConfWatcher watcher = conf.getConfWatchers().get(event.getPath());
			byte[] formerData = watcher.getData();
			Object currentData = prepare4Compare(watcher);
			if(currentData==null){
				log.error("can not find config data| configName=[" + conf.getName()
						+ "], version=[" + conf.getVersion() + "], index=[" + conf.getIndex() + "]");
				return;
			}
			doCompare(formerData, watcher);
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
	public Object prepare4Compare(ConfWatcher watcher){
		String formerIndex = watcher.getIndex();
		byte[] currentData = null;
		String currentIndex = configurationProcess.getIndex(watcher.getConfigName(), watcher.getVersion(), true);
		if(currentIndex!=null&&currentIndex.equals(formerIndex)){
			if(log.isDebugEnabled()){
				log.debug("index of current version config not changed|index=[" + currentIndex + "]");
			}
			return null;
		}
		currentData = configurationProcess.getConfigAsBytes(null, null, false, new StringBuilder(currentIndex));
		if(currentData==null){
			log.error("can not find config data| configName=[" + watcher.getConfigName()
					+ "], version=[" + watcher.getVersion() + "], index=[" + currentIndex + "]");
			return null;
		}
		watcher.setIndex(currentIndex);
		watcher.setData(currentData);
		return currentData;
	}
	
	/**
	 * 比较配置是否更新，若更新则调用对应{@link com.tydic.config.spi.ConfWatcher#callService(Vector)}
	 * @param formerData 先前配置数据
	 * @param configNode 当前配置节点
	 */
	public void doCompare(byte[] formerData, ConfWatcher watcher){
		Compare<InputStream> compare = createComparator(watcher.getConfigName());
		InputStream inputStream1 = null;
		InputStream inputStream2 = null;
		if(compare==null){
			return ;
		}
		List<String> res = new ArrayList<String>();
		try {
			inputStream1 = new ByteArrayInputStream(formerData);
			inputStream2 = new ByteArrayInputStream(watcher.getData());
			if(!compare.compare(inputStream1, inputStream2, watcher.getCheckPoint(),res)){
				log.info("Configuration changed|configname=[" + watcher + "]");
				watcher.callService(res);
			} else {
				log.info("Configuration not changed|configname=[" + watcher.getConfigName() + "]");
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
