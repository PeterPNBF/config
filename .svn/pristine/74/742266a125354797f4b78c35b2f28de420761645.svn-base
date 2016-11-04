package com.tydic.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.tydic.config.impl.GenaralZKConfigProcess;
import com.tydic.config.spi.PropertyConfigurator;

/**
 * 写配置
 * @author yuhaiming
 * @date 2014-9-11
 */
public class Load {
    private static class Options{
    	/*-p*/
    	String path;
    	/*-f*/
    	String fileName;
    	/*-v*/
    	String version;
    	String des;
    }
    private static final String HELP = "Usage:\n option: file1(source file/path) file2(destination)\n";
    private static Options getOptions(String[] args){
    	Options options = new Options();
    	for(int idx =0; idx < args.length; ++idx){
    		options.path = args[0];
    		if(options.path.endsWith(".xml")||options.path.endsWith(".properties")){
    			options.fileName = options.path;
    		}
    		options.des = args[1];
    	}
    	return options;
    }
	public static void main(String[]args) throws Exception{
		Options options = getOptions(args);
		if(options.path==null&&options.fileName==null){
			System.out.println(HELP);
			System.exit(0);
		}
		ZKConfigProcess cp = new GenaralZKConfigProcess();
		cp.init();
		if(options.fileName!=null){
			File file = createFile(options.des + "/" + options.fileName);
			write2File(options.fileName, options.version, file, cp);
		} else if(options.path!=null){
			List<String> children = cp.getAllChild(PropertyConfigurator.INDEX_HOME+ "/" +  options.path);
			for(String child: children){
				if(child.endsWith(".xml")||child.endsWith(".properties")){
					File file = createFile(options.des + "/" + child.substring(PropertyConfigurator.INDEX_HOME.length()+1));
					write2File(child.substring(PropertyConfigurator.INDEX_HOME.length()+1), options.version, file, cp);
				}
			}
		}
		cp.terminate();
	}
	
	public static void write2File(String configName, String version, File file, ZKConfigProcess cp){
		byte[] confData = cp.getConfigAsBytes(configName, version, version==null?true:false, null);
		if(confData==null){
			return;
		}
		System.out.println("write " + configName + " to file " + file.getAbsolutePath());
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(confData);
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static File createFile(String fullFileName){
		File file = new File(fullFileName);
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
	
}
