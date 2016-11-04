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
public class PropertyConfigurator implements Configurator{
	public static final String CONFIGFILENAME = "centralized-config.properties";
	private static Logger log = Logger.getLogger(PropertyConfigurator.class);

	public static final String INDEXHOMEENVNAME = "index-home-env-name";
	public static final String INDEX_HOME = "index_home";
	public static final String ROOT = "root";
	public static final String DATA = "data";
	public static final String INDEX = "index";
	public static final String ENV = "env";
	public static final String HOSTS = "hosts";
	public static final String MAXCONFIGURATIONSIZE = "max-config-size";
	public static final String CHARSET = "charset";
	public static final String MAXINDEXSIZE = "max-index-size";
	public static final String MAXDATASIZE = "max-data-size";
	public static final String DEFAULTVERSION = "default_version";
	public static final String CLASSPATH = "classpath";
	public static final String DATANODEPATTERN = "data-node-pattern";
	private Properties props;
	@Override
	public void doConfigure() {
		// TODO Auto-generated method stub
		if(log.isDebugEnabled()){
			log.debug("get configure from properties");
		}
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(PropertyConfigurator.CONFIGFILENAME); 
		if(in==null){
			log.debug(PropertyConfigurator.CONFIGFILENAME + " is not found");
			return ;
		}
		props = new Properties();
		try {
			props.load(in);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(PropertyConfigurator.CONFIGFILENAME + " read error");
			e.printStackTrace();
		}
		String tmp = props.getProperty(PropertyConfigurator.ROOT);
		if(tmp!=null){
			Configuration.ROOT = tmp;
		}
		String tmp_root = tmp;
		tmp = props.getProperty(PropertyConfigurator.DATA);
		if(tmp!=null){
			Configuration.DATA = tmp;
		} else if(tmp_root!=null){
			Configuration.DATA = tmp_root + "/data";
		}
		tmp = props.getProperty(PropertyConfigurator.INDEX);
		if(tmp!=null){
			Configuration.INDEX = tmp;
		} else if(tmp_root!=null){
			Configuration.INDEX = tmp_root + "/index";
		}
		tmp = props.getProperty(PropertyConfigurator.ENV);
		if(tmp!=null){
			Configuration.ENV = tmp;
		} else if(tmp_root!=null){
			Configuration.ENV = tmp_root + "/env";
		}
		tmp = props.getProperty(PropertyConfigurator.HOSTS);
		if(tmp!=null){
			Configuration.HOSTS = tmp;
		}
		tmp = props.getProperty(PropertyConfigurator.MAXCONFIGURATIONSIZE);
		int tmp_int = 0;
		tmp = props.getProperty(PropertyConfigurator.MAXINDEXSIZE);
		if(tmp!=null){
			tmp_int = Integer.parseInt(tmp);
			Configuration.MAXINDEXSIZE = tmp_int;
		}
		tmp = props.getProperty(PropertyConfigurator.CHARSET);
		if(tmp!=null){
			Configuration.CHARSET = tmp;
		}
		tmp = props.getProperty(PropertyConfigurator.MAXDATASIZE);
		if(tmp!=null){
			tmp_int = Integer.parseInt(tmp);
			Configuration.MAXDATASIZE = tmp_int;
		}
		tmp = props.getProperty(PropertyConfigurator.DEFAULTVERSION);
		if(tmp!=null){
			Configuration.DEFAULTVERSION = tmp;
		}
		tmp = props.getProperty(PropertyConfigurator.INDEXHOMEENVNAME);
		if(tmp!=null){
			Configuration.INDEXHOMEENVNAME = tmp;
		}
		tmp = props.getProperty(PropertyConfigurator.INDEX_HOME);
		if(tmp!=null){
			Configuration.INDEX_HOME = tmp;
		} else if(tmp_root!=null){
			Configuration.INDEX_HOME = tmp_root + "/index";
		}
		tmp = props.getProperty(PropertyConfigurator.DATANODEPATTERN);
		if(tmp!=null){
			Configuration.DATANODEPATTERN = tmp;
		}
		if(log.isDebugEnabled()){
			log.debug("after configure from properties");
			log.debug(Configuration.ROOT + " " + Configuration.DATA + " " + Configuration.INDEX + " " + Configuration.ENV + " " + Configuration.HOSTS);
		}
	}
	public Properties getProps() {
		return props;
	}
	public void setProps(Properties props) {
		this.props = props;
	}
}
