package com.tydic.config;

import java.util.List;

import com.tydic.config.impl.GenaralZKConfigProcess;
import com.tydic.config.spi.PropertyConfigurator;

/**
 * 写配置
 * @author yuhaiming
 * @date 2014-9-11
 */
public class Delete {
    private static final String HELP = "Usage:\n option: file(file/path) [version]\n";
	public static void main(String[]args) throws Exception{
		String path = null;
		String fileName = null;
		String version = null;
		if(args.length==1){
			path = args[0];
		} else if(args.length==2){
			path = args[0];
			version = args[1];
		} else if(args.length>2){
			System.out.println(HELP);
			System.exit(0);
		}
		if(path.endsWith(".xml")||path.endsWith(".properties")){
			fileName = path;
		}
		ZKConfigProcess cp = new GenaralZKConfigProcess();
		cp.init();
		if(fileName!=null){
			System.out.print("deleting " + fileName);
			if(version==null){
				System.out.println();
				cp.deleteConfig(fileName);
			} else {
				System.out.println("version="+ version);
				cp.deleteConfig(fileName, version);
			}
			System.out.println(fileName + " deleted");
		} else if(path!=null){
			List<String> children = cp.getAllChild(PropertyConfigurator.INDEX_HOME+ "/" +  path);
			for(String child: children){
				if(child.endsWith(".xml")||child.endsWith(".properties")){
					System.out.println("deleting " + child);
					if(version==null){
						cp.deleteConfig(child.substring(PropertyConfigurator.INDEX_HOME.length()+1));
					} else {
						cp.deleteConfig(child.substring(PropertyConfigurator.INDEX_HOME.length()+1), version);
					}
					System.out.println(child + "deleted");
				}
			}
			cp.deleteAllNode(PropertyConfigurator.INDEX_HOME+ "/" +  path);
		}
		cp.terminate();
	}
	
}
