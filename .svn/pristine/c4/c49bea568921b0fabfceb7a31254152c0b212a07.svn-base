package com.tydic.config.spi;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * 配置文件为centralized-config.properties，加载基础配置，若无则使用默认值。
 * @see Class {@link Configurator}
 * @author yuhaiming
 * @date 2014-9-3
 */
public class PropertyConfigurator{
	public static final String CONFIGFILENAME = "centralized-config.properties";
	private static Logger log = Logger.getLogger(PropertyConfigurator.class);

	public static final String INDEX_HOME = "index_home";
	public static final String ROOT = "root";
	public static final String DATA = "data";
	public static final String INDEX = "index";
	public static final String ENV = "env";
	public static final String HOSTS = "hosts";
	public static final String CHARSET = "charset";
	public static final String MAXINDEXSIZE = "max-index-size";
	public static final String MAXDATASIZE = "max-data-size";
	public static final String DEFAULTVERSION = "default_version";
	public static final String CLASSPATH = "classpath";
	public static final String DATANODEPATTERN = "data-node-pattern";
	
	public Configuration parseProperties(Properties props) throws Exception {
		Configuration configuration = new ConfigurationImpl();
		// TODO Auto-generated method stub
		if(props==null){
			InputStream in = this.getClass().getClassLoader().getResourceAsStream(PropertyConfigurator.CONFIGFILENAME); 
			if(in==null){
				log.debug(PropertyConfigurator.CONFIGFILENAME + " is not found");
				return null;
			}
			props = new Properties();
			try {
				props.load(in);
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error(PropertyConfigurator.CONFIGFILENAME + " read error");
				throw e;
			}
		}
		String tmp = props.getProperty(PropertyConfigurator.ROOT);
		if(tmp!=null){
			configuration.setRoot(tmp);
		}
		String tmp_root = tmp;
		tmp = props.getProperty(PropertyConfigurator.DATA);
		if(tmp!=null){
			configuration.setData(tmp);
		} else if(tmp_root!=null){
			configuration.setData(tmp_root + "/data");
		}
		tmp = props.getProperty(PropertyConfigurator.INDEX);
		if(tmp!=null){
			configuration.setIndex(tmp);
		} else if(tmp_root!=null){
			configuration.setIndex(tmp_root + "/index");
		}
		tmp = props.getProperty(PropertyConfigurator.ENV);
		if(tmp!=null){
			configuration.setEnv(tmp);
		} else if(tmp_root!=null){
			configuration.setEnv(tmp_root + "/env");
		}
		tmp = props.getProperty(PropertyConfigurator.HOSTS);
		if(tmp!=null){
			configuration.setHosts(tmp);
		}
		int tmp_int = 0;
		tmp = props.getProperty(PropertyConfigurator.MAXINDEXSIZE);
		if(tmp!=null){
			tmp_int = Integer.parseInt(tmp);
			configuration.setMaxIndexSize(tmp_int);
		}
		tmp = props.getProperty(PropertyConfigurator.CHARSET);
		if(tmp!=null){
			configuration.setCharset(tmp);
		}
		tmp = props.getProperty(PropertyConfigurator.MAXDATASIZE);
		if(tmp!=null){
			tmp_int = Integer.parseInt(tmp);
			configuration.setMaxDataSize(tmp_int);
		}
		tmp = props.getProperty(PropertyConfigurator.DEFAULTVERSION);
		if(tmp!=null){
			configuration.setDefaultVersion(tmp);
		}
		tmp = props.getProperty(PropertyConfigurator.DATANODEPATTERN);
		if(tmp!=null){
			configuration.setDataNodePattern(tmp);
		}
		return configuration;
	}
}
