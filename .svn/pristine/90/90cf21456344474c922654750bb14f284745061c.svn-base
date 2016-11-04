package com.tydic.config.compare;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

public class TestJdom {
	public InputStream getResource(){
		 return this.getClass().getClassLoader().getResourceAsStream("vds11.xml");
	}
	public static void main(String []args) throws JDOMException, IOException{
		TestJdom tj= new TestJdom(); 
	    SAXBuilder builder;
	    Document doc1;
	    Element root1;
	    XPathExpression<Object> objs1;
	    List<Object> list1;
	    InputStream xml1 = tj.getResource();
	    builder = new SAXBuilder();
	    doc1 = builder.build(xml1);
	    root1 = doc1.getRootElement();
	    objs1 = XPathFactory.instance().compile("/vds");
	    list1 = objs1.diagnose(root1, false).getResult();
	    for(Object obj : list1){
	    	Element tempEle;
			if(obj instanceof Element){
	    		tempEle = (Element) obj;
	    		
	    		System.out.println("Element1:" + tempEle.getName() + " test="+ tempEle.getText());
	    	}
	    	if(obj instanceof Attribute){
	    		tempEle = (Element) obj;
	    		
	    		System.out.println("Attribute1:" + tempEle.getName());
	    	}
	    }
	    xml1.close();
	}
	
}
