package com.tydic.config.impl;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.apache.zookeeper.KeeperException;

import com.tydic.config.ZKConfigProcess;
import com.tydic.config.spi.Configuration;
import com.tydic.config.util.ZooKeeperTemplate;

/**
 * 集中配置基础操作类。环境，索引，数据节点均保存于Zookeeper
 * @see ZKConfigProcess
 * @author yuhaiming
 * @date 2014-9-11
 */
public class GenaralZKConfigProcess extends ZKConfigProcess{
	private Logger logger = Logger.getLogger(GenaralZKConfigProcess.class);
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		if(zooKeeperOperator==null){
			zooKeeperOperator = new ZooKeeperTemplate();
			zooKeeperOperator.setConfigurationProcess(this);
		}
		super.init();
	}
	
	
	public byte[] getConfigAsBytes(String confName, String version, boolean flag, StringBuilder targetIndex){
		String absoluteDataPath = null;
		String targetDataNameList[] = getDataNameList(confName, version, flag, targetIndex);
		if(targetDataNameList==null){
			return null;
		}
		byte[] targetData_b = null;
		byte[] targetData_tmp = null;
		try {
			for (int i = 0; i < targetDataNameList.length; ++i) {
				absoluteDataPath = Configuration.DATA + "/"
						+ targetDataNameList[i];
				if(i>0){
					targetData_tmp = zooKeeperOperator.getData(absoluteDataPath);
					targetData_b = ArrayUtils.addAll(targetData_tmp, targetData_b);
				} else {
					targetData_b = zooKeeperOperator.getData(absoluteDataPath);
				}
			}
		} catch (KeeperException e) {
			logger.warn("path=[" + absoluteDataPath + "] is not found");
			return targetData_b;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return targetData_b;
	}
	
	@Override
	public String getConfig(String confName, String version, boolean flag, StringBuilder targetIndex) {
		if(confName.startsWith(Configuration.INDEX_HOME)){
			confName = confName.substring(Configuration.INDEX_HOME.length()+1);
		}
		byte[] targetData_b = getConfigAsBytes(confName, version, flag, targetIndex);
		if(targetData_b==null){
			return null;
		}
		try {
			return new String(targetData_b, Configuration.CHARSET).trim();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean writeConfig(String confName, String version, String author) {
		// TODO Auto-generated method stub

		String absoluteIndexPath = Configuration.INDEX + "/" + confName;
		StringBuilder indexData_bld = new StringBuilder("");
		if(version==null){
			version = Configuration.DEFAULTVERSION;
		}
		String targetIndexData = getIndex(confName, version, false, indexData_bld);
		String indexData = new String(indexData_bld);
		String[] dataNodeNameList = null;
		int dataNodeNamePos = -1;
		int dataNodeNameListEndPos = -1;
		if (targetIndexData != null) {
			dataNodeNameList = targetIndexData.split("\t")[3].split(",");
			dataNodeNameListEndPos = dataNodeNameList.length - 1;
		}
		String dataNodeName;
		if (dataNodeNamePos < dataNodeNameListEndPos) {
			dataNodeName = dataNodeNameList[++dataNodeNamePos];
		} else {
			dataNodeName = generateNodeName(confName);
		}
		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream(confName);
		System.out.println("confName[" + confName + "]");
		if(in==null){
			return false;
		}
		File file = new File(this.getClass().getClassLoader()
				.getResource(confName).getPath());
		float blockNum = 0f;
		if (file.exists()) {
			blockNum = file.length() / (float)Configuration.MAXDATASIZE;
		}
		logger.debug("block number=[" + String.valueOf(blockNum) + "]");
		byte[] b = new byte[Configuration.MAXDATASIZE];
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String date_s = sdf.format(date);
		logger.debug("if configuration changed twice in one millisecond,the latter will be just ignored till the next change in some other millisecond.");
		String dataNameList = null;
		try {
			zooKeeperOperator.createNode(Configuration.DATA);
			if (blockNum <= 1) {
				in.read(b);
				zooKeeperOperator.createOrUpdate(Configuration.DATA + "/"
						+ dataNodeName, new String(b, Configuration.CHARSET).trim().getBytes(Configuration.CHARSET));
				dataNameList = dataNodeName;
			} else {
				for (int i = 0; i < blockNum - 1; ++i) {
					in.read(b);
					dataNameList += dataNodeName + ",";
					zooKeeperOperator.createOrUpdate(Configuration.DATA + "/"
							+ dataNodeName, b);
					if (dataNodeNamePos < dataNodeNameListEndPos) {
						dataNodeName = dataNodeNameList[++dataNodeNamePos];
					} else {
						dataNodeName = generateNextNodeName(dataNodeName);
					}
				}
				in.read(b);
				zooKeeperOperator.createOrUpdate(Configuration.DATA + "/"
						+ dataNodeName, new String(b, Configuration.CHARSET).trim().getBytes());
				dataNameList += dataNodeName;
			}
			String newIndex = version + "\t" + author + "\t" + date_s + "\t" + dataNameList + "\n";
			indexData = simplifyData(indexData, targetIndexData, newIndex, dataNodeNameListEndPos);
			zooKeeperOperator.createNode(absoluteIndexPath);
			zooKeeperOperator.createOrUpdate(absoluteIndexPath, indexData.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				while (dataNodeNamePos < dataNodeNameListEndPos) {
					dataNodeName = dataNodeNameList[++dataNodeNamePos];
					zooKeeperOperator.delete(Configuration.DATA + "/"
							+ dataNodeName, -1);
				}
			} catch (KeeperException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	
	/**
	 * 索引数据清理函数， 返回新索引
	 * @param indexData 原索引数据
	 * @param formerIndex
	 * @param newIndex
	 * @param dataNodeNameListEndPos
	 * @return 
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public String simplifyData(String indexData, String formerIndex, String newIndex, int dataNodeNameListEndPos) throws KeeperException, InterruptedException{
		if (indexData.length() > Configuration.MAXINDEXSIZE) {
			logger.debug("Clear the index,for it is too large.");
			int fromIndex = indexData.length();
			int endIndex = 0;
			int startPos;
			for (int i = 0; i < 100; ++i) {
				if (fromIndex == -1) {
					break;
				}
				fromIndex = indexData.lastIndexOf("\n", fromIndex);
			}
			startPos = fromIndex <= 0 ? 0 : fromIndex + 1;
			fromIndex = 0;
			String temptargetIndexData;
			while (fromIndex != -1 && fromIndex < startPos) {
				endIndex = indexData.indexOf('\n', fromIndex);
				temptargetIndexData = indexData.substring(fromIndex,
						endIndex);
				fromIndex = endIndex + 1;
				if (temptargetIndexData != null) {
					for (String tempDataNodeName : temptargetIndexData
							.split("\t")[3].split(",")) {
						zooKeeperOperator.delete(Configuration.DATA
								+ "/" + tempDataNodeName, -1);
					}
				}
			}
			indexData = indexData.substring(startPos);
		}
		if (dataNodeNameListEndPos >= 0) {
			int indexPos = indexData.indexOf(formerIndex);
			while (indexPos != -1) {
				StringBuilder sb = new StringBuilder(indexData.substring(0,
						indexPos));
				sb.append(indexData.substring(indexPos
						+ formerIndex.length() + 1));
				indexData = new String(sb);
				indexPos = indexData.indexOf(formerIndex);
			}
		}
		indexData += newIndex;
		return indexData;
	}

	/**
	 * 由confName生成节点名
	 * @param confName
	 * @return 节点名
	 */
	public String generateNodeName(String confName) {
		String nodeName;
		String[] confNamel = confName.split("/");
		nodeName = confNamel[confNamel.length-1] + "_" + UUID.randomUUID().toString() + "_00";
		return nodeName;
	}

	/**
	 * 由nodeName生成下一个节点名
	 * @param nodeName
	 * @return
	 */
	public String generateNextNodeName(String nodeName) {
		int pos1 = nodeName.lastIndexOf('_');
		if (pos1 == -1) {
			return nodeName + "_00";
		}
		String prefix = nodeName.substring(0, pos1 + 1);
		String number = nodeName.substring(pos1 + 1, nodeName.length());
		int number_i = 0;
		try {
			number_i = Integer.parseInt(number);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
		String number2 = String.valueOf(number_i);
		String nodeName2 = prefix + number2;
		return nodeName2;
	}

	/**
	 * 删除名为confName,版本为version的配置， 返回新索引
	 * @param confName
	 * @param version
	 * @return 索引数据
	 */
	public String deleteConfig(String confName, String version) {
		String absoluteDataPath;
		String indexData = getIndex(confName);
		int pos = -1;
		if(indexData==null){
			logger.debug("config is already empty|configName=[" + confName + "], version=[" + version + "]");
			return null;
		}
		if (version != null) {
			pos = indexData.lastIndexOf(version + "\t");
		}
		String targetIndex = null;
		if (pos != -1) {
			targetIndex = indexData.substring(pos,
					indexData.indexOf('\n', pos + 1));
		} else {
			return indexData;
		}
		String targetIndexData[] = targetIndex.trim().split("\t");
		String targetDataNameList[] = targetIndexData[3].split(",");
		logger.debug(targetIndex);
		try {
			for (int i = 0; i < targetDataNameList.length; ++i) {
				absoluteDataPath = Configuration.DATA + "/"
						+ targetDataNameList[i];
				zooKeeperOperator.delete(absoluteDataPath, -1);
				if(logger.isDebugEnabled()){
					logger.debug("data deleted|path=[" + absoluteDataPath +"]");
				}
			}
			int indexPos = indexData.indexOf(targetIndex);
			StringBuilder sb = new StringBuilder(indexData.substring(0,
					indexPos));
			sb.append(indexData.substring(indexPos + targetIndex.length() + 1));
			indexData = new String(sb);
			zooKeeperOperator.createOrUpdate(getIndexPath(confName),
					indexData.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return indexData; 
	}

	/**
	 * 删除名为confName的配置
	 * @param confName
	 */
	public void deleteConfig(String confName) {
		String indexData = getIndex(confName);
		if(indexData==null){
			logger.debug("config is already empty|configName=[" + confName + "]");
			return ;
		}
		String tempIndexData;
		String tempVersion;
		int posFeedCode = indexData.indexOf('\n');
		while (posFeedCode > 0) {
			tempIndexData = indexData.substring(0, posFeedCode);
			tempVersion = tempIndexData.split("\t")[0];
			indexData = deleteConfig(confName, tempVersion);
			posFeedCode = indexData.indexOf('\n');
		}
		try {
			String indexPath = getIndexPath(confName);
			zooKeeperOperator.delete(indexPath, -1);
			if(logger.isDebugEnabled()){
				logger.debug("data deleted|path=[" + indexPath +"]");
			}
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
