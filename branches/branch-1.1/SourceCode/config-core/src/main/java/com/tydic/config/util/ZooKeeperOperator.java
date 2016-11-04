package com.tydic.config.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import com.tydic.config.ZKConfigProcess;

/**
 * zookeeper 基础操作工具类，忽略节点数据更新变化
 * @author yuhaiming
 * @date 2014-9-3
 */
public class ZooKeeperOperator {
	protected static final int SESSION_TIME = 3000;
	protected static Logger log = Logger.getLogger(ZooKeeperOperator.class
			.getName());
	protected ZooKeeper zooKeeper;
	protected CountDownLatch countDownLatch = new CountDownLatch(1);
	protected ZKConfigProcess configurationProcess;

	public void connect(String hosts) throws IOException, InterruptedException {
		zooKeeper = new ZooKeeper(hosts, SESSION_TIME, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				// TODO Auto-generated method stub
				if (event.getState() == KeeperState.SyncConnected) {
					countDownLatch.countDown();
				}
				doCheck(event);
			}
		});
		countDownLatch.await();
	}
	
	/**
	 * 检测event对应的配置信息是否更新，若更新则调用对应{@link com.tydic.config.spi.ConfWatcher#callService(Vector)}
	 * @param event 节点变化触发事件
	 */
	public void doCheck(WatchedEvent event){
		
	}
	
	public void close() throws InterruptedException {
		zooKeeper.close();
	}

	/**
	 * 
	 * <b>function:</b>创建持久态的znode,不支持多层创建.比如在创建/parent/child的情况下,无/parent.无法通过
	 * 
	 * @param path
	 * @param data
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void create(String path, byte[] data) throws KeeperException,
			InterruptedException {
		/**
		 * 此处采用的是CreateMode是PERSISTENT 表示The znode will not be automatically
		 */
		this.zooKeeper.create(path, data, Ids.OPEN_ACL_UNSAFE,
				CreateMode.PERSISTENT);
	}

	/**
	 * 
	 * <b>function:</b>删除持久态的znode
	 * 
	 * @param path
	 * @param version
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void delete(String path, int version) throws KeeperException,
			InterruptedException {
		/**
		 * 此处采用的是CreateMode是PERSISTENT 表示The znode will not be automatically
		 */
		if(zooKeeper.exists(path, false)!=null){
			this.zooKeeper.delete(path, version);
		}
	}

	public void deleteAll(String path) throws KeeperException,InterruptedException {
		try {
			List<String> list = this.zooKeeper.getChildren(path, false);
			if (list.isEmpty()) {
				delete(path, -1);
			} else {
				for (String child : list) {
					if (path.equals("/") == true) {
						deleteAll(path + child);
					} else {
						deleteAll(path + "/" + child);
					}
				}
				delete(path, -1);
			}
		} catch (KeeperException.NoNodeException e) {
			// TODO: handle exception
			throw e;
	
		}
	}

	public void createOrUpdate(String path, byte[] data)
			throws KeeperException, InterruptedException {
		/**
		 * 此处采用的是CreateMode是PERSISTENT 表示The znode will not be automatically
		 */
		Stat stat = this.zooKeeper.exists(path, false);
		if (stat != null) {
			this.zooKeeper.setData(path, data, -1);
		} else {
			this.zooKeeper.create(path, data, Ids.OPEN_ACL_UNSAFE,
					CreateMode.PERSISTENT);
		}
	}

	/**
	 * create father node if not exist
	 * 
	 * @param path
	 * @throws InterruptedException
	 * @throws KeeperException
	 */
	public void createNode(String path) throws KeeperException,
			InterruptedException {
		int posseparator = path.lastIndexOf('/');
		if (posseparator < 0)
			return;
		if (posseparator == 0) {
			if (zooKeeper.exists(path, false) == null) {
				create(path, null);
			}
			return;
		}
		String fatherPath = path.substring(0, posseparator);
		if (zooKeeper.exists(fatherPath, false) == null) {
			createNode(fatherPath);
		}
		if (zooKeeper.exists(path, false) == null) {
			create(path, null);
		}
	}

	/**
	 * 
	 * <b>function:</b>获取节点信息
	 * 
	 * @param path
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void getAllChild(String path, String prefix) throws KeeperException,
			InterruptedException {
		getAllChild(path,prefix,0);
	}
	
	public void getAllChild(String path, String prefix, int deepth) throws KeeperException,
			InterruptedException {
		try {
			List<String> list = this.zooKeeper.getChildren(path, false);
			for(int i=0; i<deepth; ++i){
				System.out.print(prefix);
			}
			System.out.println(path);
			if (list.isEmpty()) {
			} else {
				for (String child : list) {
					if (path.equals("/") == true) {
						getAllChild(path + child, prefix, deepth+1);
					} else {
						getAllChild(path + "/" + child, prefix, deepth+1);
					}
				}
			}
		} catch (KeeperException.NoNodeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	
	public List<String> getAllChild(String path) throws KeeperException,
			InterruptedException {
		List<String> list = new ArrayList<String>();
		try {
			List<String> tmp = this.zooKeeper.getChildren(path, false);
			if(tmp!=null&&tmp.size()>0){
				if (path.equals("/") == true) {
					for(String tmps:tmp){
						list.add("/" +tmps);
					}
				} else {
					for(String tmps:tmp){
						list.add(path+ "/" +tmps);
					}
				}
			}
			if (tmp.isEmpty()) {
			} else {
				List<String> tmp2 = null;
				for (String child : tmp) {
					if (path.equals("/") == true) {
						tmp2 = getAllChild(path + child);
						if(tmp2!=null&&tmp2.size()>0){
							for(String tmps:tmp2){
								list.add(tmps);
							}
						}
					} else {
						tmp2 = getAllChild(path + "/" + child);
						if(tmp2!=null&&tmp2.size()>0){
							for(String tmps:tmp2){
								list.add(tmps);
							}
						}
					}
				}
			}
			return list;
		} catch (KeeperException.NoNodeException e) {
			// TODO: handle exception
			throw e;
		}
	}

	public List<String> getChild(String path) throws KeeperException,
			InterruptedException {
		List<String> list = null;
		try {
			if(this.zooKeeper.exists(path, false)!=null){
				list = this.zooKeeper.getChildren(path, false);
			}
		} catch (KeeperException.NoNodeException e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}

	public byte[] getData(String path) throws KeeperException,
			InterruptedException {
		return this.zooKeeper.getData(path, false, null);
	}
	
	public byte[] getData(String path, boolean watcher) throws KeeperException,
	InterruptedException {
		return this.zooKeeper.getData(path, watcher, null);
	}

	public String getString(String path, boolean watcher) throws UnsupportedEncodingException,
			KeeperException, InterruptedException {
		return new String(this.getData(path, watcher),"utf-8");
	}

	public String getString(String path) throws UnsupportedEncodingException,
			KeeperException, InterruptedException {
		return new String(this.getData(path),"utf-8");
	}
	
	public InputStream getDataAsInputStream(String path) throws KeeperException, InterruptedException{
		return new ByteArrayInputStream(getData(path));
	}
	
	public InputStream getDataAsInputStream(String path, boolean watcher) throws KeeperException, InterruptedException{
		return new ByteArrayInputStream(getData(path, watcher));
	}
	
	public void watch(String path) throws KeeperException, InterruptedException {
		log.debug("watch node, nodepath=[" + path + "]");
		this.zooKeeper.exists(path, true);
	}
	
	public Stat exists(String path, boolean watch) throws KeeperException, InterruptedException{
		return this.zooKeeper.exists(path, watch);
	}

	public void setConfigurationProcess(ZKConfigProcess configurationProcess) {
		this.configurationProcess = configurationProcess;
	}

	public ZKConfigProcess getConfigurationProcess() {
		return configurationProcess;
	}
}
