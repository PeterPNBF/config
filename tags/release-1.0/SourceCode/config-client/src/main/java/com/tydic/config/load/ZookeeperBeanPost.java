package com.tydic.config.load;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.xml.sax.InputSource;

import com.tydic.config.ZKConfigProcess;
import com.tydic.config.impl.ZKConfigProcessTemplate;
import com.tydic.config.spi.AutoLoadConfig;
import com.tydic.config.spi.Configuration;

public class ZookeeperBeanPost extends AutoLoadConfig implements BeanFactoryPostProcessor {

	private static Log log = LogFactory.getLog(ZookeeperBeanPost.class);
	private ConfigurableListableBeanFactory beanFactory;

	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		if(!doConfigure()){
			return;
		}
		ZKConfigProcess cp = new ZKConfigProcessTemplate();
		cp.init();
		this.beanFactory = beanFactory;
		XmlBeanDefinitionReader definitionReader = new XmlBeanDefinitionReader(
				(BeanDefinitionRegistry) this.beanFactory);
		definitionReader.setValidationMode(XmlBeanDefinitionReader.VALIDATION_XSD);
		InputStream inputStream = null;
		InputSource inputSource = null;
		if(getNames()!=null){
			for(int i=0; i<getNames().length; ++i){
				if(log.isDebugEnabled()){
					log.debug("load configName=[" + getNames()[i] + "] config=[" + cp.getConfig(getNames()[i]) + "]");
				}
				inputStream = cp.getConfigAsInputStream(getNames()[i]);
				inputSource = new InputSource(inputStream);
				inputSource.setEncoding(Configuration.CHARSET);
				definitionReader.loadBeanDefinitions(inputSource);
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if(getPaths()!=null){
			for(int i=0; i<getPaths().length; ++i){
				List<String> children = cp.getAllChild(Configuration.INDEX_HOME + "/" + getPaths()[i]);
				for(String child: children){
					if(child.endsWith(".xml")){
						if(log.isDebugEnabled()){
							log.debug("load configName=[" + child + "] config=[" + cp.getConfig(child) + "]");
						}
						inputStream = cp.getConfigAsInputStream(child);
						inputSource = new InputSource(inputStream);
						inputSource.setEncoding(Configuration.CHARSET);
						definitionReader.loadBeanDefinitions(inputSource);
						try {
							inputStream.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		
	}

}