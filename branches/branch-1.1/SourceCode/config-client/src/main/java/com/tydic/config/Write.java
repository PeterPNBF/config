package com.tydic.config;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.tydic.config.ConfigurationProcess;
import com.tydic.config.impl.GenaralZKConfigProcess;

/**
 * 写配置
 * @author yuhaiming
 * @date 2014-9-11
 */
public class Write {
    private static class Options{
    	/*-c*/
    	String categories;
    	/*-p*/
    	String path;
    	/*-f*/
    	String fileName;
    	/*-v*/
    	String version;
    	/*-a*/
    	String author;
    }
    private static final String HELP = "need options:\n"
    		+ "[-p configure file path -c file categories(x:xml, p:properties, xp:xml&properties, a:all)]"
    		+ "[-f configure file name]\n"
    		+ "[-v configure file version]\n"
    		+ "[-a author]\n\n";
    private static Options getOptions(String[] args){
    	Options options = new Options();
    	for(int idx =0; idx < args.length; ++idx){
    		if(args[idx].equals("-h")){
    			System.out.println(HELP);
    			System.exit(0);
    		}else if(args[idx].equals("-c")){
    			options.categories = args[++idx];
    		}else if(args[idx].equals("-p")){
    			options.path = args[++idx];
    		}else if(args[idx].equals("-f")){
    			options.fileName = args[++idx];
    		}else if(args[idx].equals("-v")){
    			options.version = args[++idx];
    		}else if(args[idx].equals("-a")){
    			options.author = args[++idx];
    		}else{
    			System.out.println(HELP);
    			System.exit(0);
    		}
    	}
    	return options;
    }
	public static void main(String[]args) throws Exception{
		Options options = getOptions(args);
		if(options.path==null&&options.fileName==null){
			System.out.println(HELP);
			System.exit(0);
		}
		ConfigurationProcess cp = new GenaralZKConfigProcess();
		cp.init();
		if(options.fileName!=null){
			cp.writeConfig(options.fileName, options.version, options.author);
		}
		if(options.path!=null){
			URL url = Write.class.getClassLoader().getResource(options.path);
			List<String> files = null;
			if(url!=null){
				File tmp = new File(url.getFile());
				if(tmp.exists()){
					String nameRegex = null;
					if(options.categories==null){
						
					} else if(options.categories.equals("x")){
						nameRegex = ".*\\.xml";
					} else if(options.categories.equals("p")){
						nameRegex = ".*\\.properties";
					} else if(options.categories.equals("xp")){
						nameRegex = ".*\\.xml|.*\\.properties";
					}
					files = getFileNames(tmp, nameRegex, "");
				}
			}
			for(String file : files){
				cp.writeConfig(file, options.version, options.author);
			}
		}
		((ZKConfigProcess) cp).getAllChild("/", "  ");
		cp.terminate();
	}
	
	

	public static List<String> getFileNames(File file, final String nameRegex, String preffix){
		List<String> confFiles = new ArrayList<String>();
		if(!file.exists()){
			return null;
		}
		File files[];
		FilenameFilter filenameFilter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				if(name.matches(nameRegex)){
					return true;
				}
				return false;
			}
		};
		if(file.isDirectory()){
			if(nameRegex==null){
				files = file.listFiles();
			} else {
				files = file.listFiles(filenameFilter);
			}
			for(File tmp : files){
				confFiles.addAll(getFileNames(tmp, nameRegex, preffix==""?file.getName():preffix + "/" + file.getName()));
			}
		} else if (nameRegex==null||file.getName().matches(nameRegex)){
			confFiles.add(preffix + "/" + file.getName());
		}
		return confFiles;
	}
}
