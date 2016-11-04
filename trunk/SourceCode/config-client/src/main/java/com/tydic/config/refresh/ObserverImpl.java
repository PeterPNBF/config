package com.tydic.config.refresh;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tydic.config.ConfigurationProcess;
import com.tydic.config.Observable;


public class ObserverImpl implements Observable{
	
	private String des;
	private ConfigurationProcess configurationProcess;
	private static Log log = LogFactory.getLog(ObserverImpl.class);
	private Set<String> fileNames;
	

	@Override
	public void callService(String configName, String version,
			List<String> args) {
		// TODO Auto-generated method stub
		List<String> tempVec = args;
		log.debug("----------------------- Observer begin-----------------------");
		log.debug("changed:" + tempVec.size());
		File file = createFile(configName);
		write2File(configName, version, file);
		log.debug("------------------------ Observer test end------------------------");
	}
	
	public void write2File(String configName, String version, File file){
		byte[] confData = configurationProcess.getConfigAsBytes(configName, version, version==null?true:false, null);
		if(confData==null){
			return;
		}
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(confData);
			fileOutputStream.close();
			log.debug("write " + confData.length + "b data to" +  file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public File createFile(String configName){
		File file = new File(des+ "/" + configName);
		if(!file.getAbsoluteFile().exists()){
			file.getAbsoluteFile().mkdirs();
		}
		if(file.exists()){
			file.delete();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}


	public String getDes() {
		return des;
	}


	public void setDes(String des) {
		this.des = des;
	}


	public ConfigurationProcess getConfigurationProcess() {
		return configurationProcess;
	}


	public void setConfigurationProcess(ConfigurationProcess configurationProcess) {
		this.configurationProcess = configurationProcess;
	}


	public void init() {
		// TODO Auto-generated method stub
		File file = null;
		if(fileNames==null){
			return;
		}
		for(String configName:fileNames){
			file = new File(des+ "/" + configName);
			if(file.exists()){
				continue;
			}
			createFile(configName);
			write2File(configName, null, file);
		}
	}

	public Set<String> getFileNames() {
		return fileNames;
	}

	public void setFileNames(Set<String> set) {
		this.fileNames = set;
	}
}
