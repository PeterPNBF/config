package com.tydic.config.compare;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filter;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

public class TestSimtableXpath {
	public InputStream getResource(){
		 return this.getClass().getClassLoader().getResourceAsStream("table.xml");
	}
	public static void main(String []args) throws JDOMException, IOException{
		TestSimtableXpath tj= new TestSimtableXpath(); 
	    SAXBuilder builder;
	    Document doc1;
	    Element root1;
	    XPathExpression<Element> objs1;
	    List<Element> list1;
	    InputStream xml1 = tj.getResource();
	    builder = new SAXBuilder();
	    doc1 = builder.build(xml1);
	    root1 = doc1.getRootElement();
	    System.out.println(root1.getChildren());
	    Filter<Element> filter = Filters.element();
	    XPathBuilder<Element> xPathBuilder = new XPathBuilder<Element>("/ns:beans/ns:bean[ns:property[1][@value='VDS'] and ns:property[2][@value='TEST_D_PARTITIONS_FRAGMENTS']] ", filter);
	    xPathBuilder.setNamespace("ns", "http://www.springframework.org/schema/beans");
	    objs1 = xPathBuilder.compileWith(XPathFactory.instance());
	    list1 = objs1.diagnose(root1, false).getResult();
	    System.out.println("list1 size=" + list1.size());
	    for(Object obj : list1){
	    	Element tempEle;
			if(obj instanceof Element){
	    		tempEle = (Element) obj;
	    		
	    		System.out.println("Element1:" + tempEle.getName() + tempEle.getChildren());
	    	}
	    	if(obj instanceof Attribute){
	    		tempEle = (Element) obj;
	    		
	    		System.out.println("Attribute1:" + tempEle.getName().trim());
	    	}
	    }
	    xml1.close();
	}
	
}
