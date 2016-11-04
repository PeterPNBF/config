package com.tydic.vds.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tydic.config.load.BeanTestContext;

public class AutoLoad {
	public static ApplicationContext context;
	public static void main(String [] args){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		BeanTestContext beanTestContext = (BeanTestContext) context.getBean("testContext");
		beanTestContext.print();
	}
}
