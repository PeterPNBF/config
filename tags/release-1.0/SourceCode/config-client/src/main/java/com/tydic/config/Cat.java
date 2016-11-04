package com.tydic.config;

import com.tydic.config.impl.ZKConfigProcessTemplate;
import com.tydic.config.spi.Configuration;
/**
 * 写配置
 * @author yuhaiming
 * @date 2014-9-11
 */
public class Cat {
    private static final String HELP = "Usage:\n option: [file]\n";
	public static void main(String[]args){
		String file = null;
		if(args.length==1){
			file = args[0];
		} else if(args.length>1){
			System.out.println(HELP);
			System.exit(0);
		}
		ZKConfigProcess cp = new ZKConfigProcessTemplate();
		cp.init();
		if(!file.startsWith("/")){
			file = Configuration.INDEX_HOME + "/" + file;
		}
		if(file!=null){
			System.out.println(cp.getData(file));
		}
		cp.terminate();
	}
	
}
