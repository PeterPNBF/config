package com.tydic.config;

import com.tydic.config.impl.GenaralZKConfigProcess;
import com.tydic.config.spi.PropertyConfigurator;
/**
 * 写配置
 * @author yuhaiming
 * @date 2014-9-11
 */
public class Cat {
    private static final String HELP = "Usage:\n option: [file]\n";
	public static void main(String[]args) throws Exception{
		String file = null;
		if(args.length==1){
			file = args[0];
		} else if(args.length>1){
			System.out.println(HELP);
			System.exit(0);
		}
		ZKConfigProcess cp = new GenaralZKConfigProcess();
		cp.init();
		if(!file.startsWith("/")){
			file = PropertyConfigurator.INDEX_HOME + "/" + file;
		}
		if(file!=null){
			System.out.println(cp.getData(file));
		}
		cp.terminate();
	}
	
}
