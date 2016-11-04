package com.tydic.config;

import java.io.InputStream;
import java.util.List;

import com.tydic.config.spi.ConfWatcher;

/**
 * 集中配置接口，包含init，terminate，及取得配置、删除配置、添加观察者
 * @author yuhaiming
 * @date 2014-9-11
 */
public interface ConfigurationProcess {
	
	/**
	 * 初始化操作，如连接及变量
	 * @throws Exception 
	 */
	public void init() throws Exception;
	
	/**
	 * 关闭操作，如关闭连接
	 */
	public void terminate();
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
	 * if version can not find, you can set flag=true to find the latest version.
	 * @param confName
	 * @param version
	 * @param flag
	 * @return
	 */
	public String getConfig(String confName, String version, boolean flag);

	/**
	 * if config with version can not find, find the latest version.
	 * @param confName
	 * @param version
	 * @return
	 */
	public String getConfig(String confName, String version);

	/**
	 * Find the latest version config.
	 * @param confName
	 * @return
	 */
	public String getConfig(String confName);
	

	/**
	 * 取得配置，返回InputStream,
	 * 设置flag=true时，若找不到该版本配置则返回最新配置。
	 * 配置文件以utf-8编码，其他编码可能出错
	 * @param confName 配置名
	 * @param version 配置版本
	 * @param flag 
	 * @return InputStream
	 */
	public InputStream getConfigAsInputStream(String confName, String version, boolean flag, StringBuilder targetIndex);
	
	/**
	 * 取得配置，返回InputStream,
	 * 设置flag=true时，若找不到该版本配置则返回最新配置。
	 * 配置文件以utf-8编码，其他编码可能出错
	 * @param confName 配置名
	 * @param version 配置版本
	 * @param flag 
	 * @return InputStream
	 */
	public InputStream getConfigAsInputStream(String confName, String version, boolean flag);

	/**
	 * 取得配置，返回InputStream,
	 * 若找不到该版本配置则返回最新配置。
	 * 配置文件以utf-8编码，其他编码可能出错
	 * @param confName 配置名
	 * @param version 配置版本
	 * @return InputStream
	 */
	public InputStream getConfigAsInputStream(String confName, String version);
	
	/**
	 * 取得配置，返回InputStream,
	 * 若找不到该版本配置则返回最新配置。
	 * 配置文件以utf-8编码，其他编码可能出错
	 * @param confName 配置名
	 * @param version 配置版本
	 * @return InputStream
	 */
	public InputStream getConfigAsInputStream(String confName);
	
	/**
	 * 删除名为confName的配置
	 * @param confName
	 */
	public void deleteConfig(String confName);
	
	/**
	 * 删除名为confName,版本为version的配置， 返回新索引
	 * @param confName
	 * @param version
	 * @return 索引数据
	 */
	public String deleteConfig(String confName, String version);
	
	/**
	 * 添加配置观察者，当该配置变更时，对比检测点，若变更则调用{@link Observable#callService(Vector)}}
	 * @param configName 配置名
	 * @param version 配置版本
	 * @param xpath 配置是否变更检查点
	 * @param observer 回调类
	 */
	public void addWatcher(String configName, String version, List<String> xpath, Observable observer);
	
	/**
	 * 添加配置观察者，当该配置（最新版本）变更时，对比检测点，若变更则调用{@link Observable#callService(Vector)}}
	 * @param configName 配置名
	 * @param xpath 配置是否变更检查点
	 * @param observer 回调类
	 */
	public void addWatcher(String configName, List<String> xpath, Observable observer);

	/**
	 * 添加配置观察者，当该配置（名字为xpath第一个'/'前的部分，版本为最新版本）变更时，对比检测点，若变更则调用{@link Observable#callService(Vector)}}
	 * @param xpath 配置是否变更检查点
	 * @param observer 回调类
	 */
	public void addWatcher(List<String> xpath, Observable observer);
	
	/**
	 * 添加配置观察者，当该配置变更时，对比检测点，若变更则调用{@link Observable#callService(Vector)}}
	 * @param configName 配置名
	 * @param version 配置版本
	 * @param xpath 配置是否变更检查点
	 * @param observer 回调类
	 */
	public void addWatcher(String configName, String version, String xpath, Observable observer);
	
	/**
	 * 添加配置观察者，当该配置（最新版本）变更时，对比检测点，若变更则调用{@link Observable#callService(Vector)}}
	 * @param configName 配置名
	 * @param xpath 配置是否变更检查点
	 * @param observer 回调类
	 */
	public void addWatcher(String configName, String xpath, Observable observer);

	/**
	 * 添加配置观察者，当该配置（名字为xpath第一个'/'前的部分，版本为最新版本）变更时，对比检测点，若变更则调用{@link Observable#callService(Vector)}}
	 * @param xpath 配置是否变更检查点
	 * @param observer 回调类
	 */
	public void addWatcher(String xpath, Observable observer);
	
	/**
	 * 添加配置观察者
	 * @param watcher 观察者信息
	 */
	public void addWatcher(ConfWatcher watcher);
	

	/**
	 * 写名为confName的配置到配置中心
	 * @param confName 配置名
	 * @return true if success
	 */
	public boolean writeConfig(String confName);
	
	/**
	 * 写名为confName, 作者为author的配置到配置中心
	 * @param confName 配置名
	 * @param author 作者
	 * @return true if success
	 */
	public boolean writeConfig(String confName, String author);
	

	/**
	 * 写名为confName, 作者为author， 版本为version的配置到配置中心
	 * @param confName 配置名
	 * @param version 版本
	 * @param author 作者
	 * @return true if success
	 */
	public boolean writeConfig(String confName, String version, String author);
}
