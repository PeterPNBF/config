package com.tydic.config;

import com.tydic.config.ZKConfigProcess;
import com.tydic.config.impl.GenaralZKConfigProcess;
import com.tydic.config.spi.PropertyConfigurator;

/**
 * 打印Zookeeper目录结构
 * @author yuhaiming
 * @date 2014-9-12
 */
public class Ls {
    private static final String HELP = "Usage:\n option: [path [placeholder]]\n";
	public static void main(String[]args) throws Exception{
		String placeholder = null;
		String path = null;
		if(args.length==1){
			path = args[0];
		} else  if(args.length==2){
			path = args[0];
			placeholder = args[1];
		} else if(args.length>2){
			System.out.println(HELP);
			System.exit(0);
		}
		ZKConfigProcess cp = new GenaralZKConfigProcess();
		cp.init();
		if(placeholder==null){
			placeholder = "  ";
		}
		if(path==null){
			path = PropertyConfigurator.INDEX_HOME;
		}
		if(path!=null){
			cp.getAllChild(path, placeholder);
		}
		cp.terminate();
	}
}
