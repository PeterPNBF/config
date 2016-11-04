package com.tydic.config;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.tydic.config.impl.ZKConfigProcessTemplate;
import com.tydic.config.spi.Configuration;

/**
 * 写配置
 * @author yuhaiming
 * @date 2014-9-11
 */
public class Read {
    private static class Options{
    	/*-p*/
    	String path;
    	/*-f*/
    	String fileName;
    	/*-v*/
    	String version;
    }
    private static final String HELP = "need options:\n"
    		+ "[-p configure file path]\n"
    		+ "[-f configure file name]\n"
    		+ "[-v configure file version]\n"
    		+ "[-a all]\n\n";
    private static Options getOptions(String[] args){
    	Options options = new Options();
    	for(int idx =0; idx < args.length; ++idx){
    		if(args[idx].equals("-h")){
    			System.out.println(HELP);
    			System.exit(0);
    		}else if(args[idx].equals("-p")){
    			options.path = "/" + args[++idx];
    		}else if(args[idx].equals("-f")){
    			options.fileName = args[++idx];
    		}else if(args[idx].equals("-v")){
    			options.version = args[++idx];
    		}else if(args[idx].equals("-a")){
    			options.path = "";
    		}else{
    			System.out.println(HELP);
    			System.exit(0);
    		}
    	}
    	return options;
    }
	public static void main(String[]args) throws UnsupportedEncodingException{
		Options options = getOptions(args);
		if(options.path==null&&options.fileName==null){
			System.out.println(HELP);
			System.exit(0);
		}
		ZKConfigProcess cp = new ZKConfigProcessTemplate();
		cp.init();
		if(options.path!=null){
			List<String> children = cp.getAllChild(Configuration.INDEX_HOME+ options.path);
			for(String child: children){
				if(child.endsWith(".xml")||child.endsWith(".properties")){
					System.out.println("path=[" + child + "]");
					System.out.println("[\n" + cp.getConfig(child, options.version) + "\n]");
				}
			}
		}
		if(options.fileName!=null){
			System.out.println(cp.getConfig(options.fileName, options.version));
		}
		cp.terminate();
	}
	
}
