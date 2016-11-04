package com.tydic.config.compare;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.jdom2.Attribute;
import org.jdom2.CDATA;
import org.jdom2.Comment;
import org.jdom2.Content;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Text;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.jdom2.xpath.jaxen.JaxenXPathFactory;

/**
 * .xml类型数据比较
 * @see {@link Compare}
 * @author yuhaiming
 * @date 2014-9-3
 */
public class XMLInputStreamCompare extends InputStreamCompare{
	private Logger log = Logger.getLogger(XMLInputStreamCompare.class);
	@Override
	public boolean compare(InputStream data1, InputStream data2, Vector<String> point, Vector<String> result) {
		// TODO Auto-generated method stub
		if(!super.compare(data1, data2, point, result)){
			return false;
		}
		boolean tempres = true;
	    SAXBuilder builder1;
	    SAXBuilder builder2;
	    Document doc1;
	    Document doc2;
	    Element root1;
	    Element root2;
	    XPathFactory factory1;
	    XPathFactory factory2;
	    XPathExpression<Object> objs1;
	    XPathExpression<Object> objs2;
	    List<Object> list1;
	    List<Object> list2;
		try {
		    builder1 = new SAXBuilder();
		    builder2 = new SAXBuilder();
		    doc1 = builder1.build((InputStream)data1);
		    doc2 = builder2.build((InputStream)data2);
		    root1 = doc1.getRootElement();
		    root2 = doc2.getRootElement();
		    factory1 = JaxenXPathFactory.instance();
		    factory2 = JaxenXPathFactory.instance();
		    for(String tempExpression: point){
			    objs1 = factory1.compile(tempExpression);
			    objs2 = factory2.compile(tempExpression);
			    list1 = objs1.diagnose(root1, false).getResult();
			    list2 = objs2.diagnose(root2, false).getResult();
			    if(list1.size()!=list2.size()){
			    	log.debug("num of " + tempExpression + " find in two file not equal|"+ list1.size() + "!=" + list2.size());
			    	result.add(tempExpression);
			    	tempres = false;
			    } else if(!compareList(list1, list2)){
			    	result.add(tempExpression);
			    	tempres = false;
			    }
		    }

		} catch (JDOMException e){
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tempres;
	}
	public boolean compareList(List<Object> list1, List<Object> list2) {
		// TODO Auto-generated method stub
		Object obj1,obj2;
	    for(int i=0; i<list1.size(); ++i){
	    	obj1 = list1.get(i);
	    	obj2 = list2.get(i);
	    	if((obj1 instanceof Attribute)&&(obj2 instanceof Attribute)){
				log.debug("Attribute unmatch|attribute1=[" + ((Attribute) obj1).getName() + "=" + ((Attribute) obj1).getValue()
						+"], attribute2=[" + ((Attribute) obj2).getName() + "=" + ((Attribute) obj2).getValue() +"]");
	    		if(!((Attribute) obj1).getValue().equals(((Attribute) obj2).getValue())){
	    			return false;
	    		}
	    		log.debug((Attribute) obj1);
	    	}
	    	if((obj1 instanceof CDATA)&&(obj2 instanceof CDATA)){
	    		if(!((CDATA) obj1).getTextTrim().equals(((CDATA) obj2).getTextTrim())){
	    			log.debug("CDATA unmatch|CDATA1=[" + (CDATA) obj1 + "], CDATA2=[" + (CDATA)obj2 + "]");
	    			return false;
	    		}
	    		log.debug((CDATA) obj1);
	    	}
	    	if((obj1 instanceof DocType)&&(obj2 instanceof DocType)){
	    		if(!((DocType) obj1).getValue().equals(((DocType) obj2).getValue())){
	    			log.debug("DocType unmatch|DocType1=[" + (DocType) obj1 + "], DocType2=[" + (DocType)obj2 + "]");
	    			return false;
	    		}
	    		log.debug((DocType) obj1);
	    	}
	    	if((obj1 instanceof Text)&&(obj2 instanceof Text)){
    			log.debug("Text unmatch|Text1=[" + (Text) obj1 + "], Text2=[" + (Text)obj2 + "]");
	    		if(!((Text) obj1).getValue().equals(((Text) obj2).getValue())){
	    			return false;
	    		}
	    		log.debug((Text) obj1);
	    	}
	    	if((obj1 instanceof Element)&&(obj2 instanceof Element)){
	    		if(!compareElement((Element) obj1, (Element) obj2)){
	    			return false;
	    		}
	    	}
	    }
	    return true;
	}

	public boolean compareElement(Element element1, Element element2) {
		Element child1 = (Element) element1;
		Element child2 = (Element) element2;
		log.debug("Element compare ElementName1=[" + child1.getName() + "], ElementName2=[" + child1.getName() + "]");
		if(!child1.getName().equals(child2.getName())){
			log.debug("Element name unmatch|ElementName1=[" + child1.getName() + "], ElementName2=[" + child1.getName() + "]");
			return false;
		}
		List<Attribute> attr1 = child1.getAttributes();
		List<Attribute> attr2 = child1.getAttributes();
		if(attr1.size()!=attr2.size()){
	    	log.debug("size of Attribute in Element:" + child1 + " find in two file not equal|"+ attr1.size() + "!=" + attr2.size());
			return false;
		}
		for(int i=0; i<attr1.size(); ++i){
			if(!(attr1.get(i).getName().equals(attr2.get(i).getName())||attr1.get(i).getValue().equals(attr2.get(i).getValue()))){
				log.debug("Attribute unmatch|attribute1=[" + attr1.get(i).getName() + "=" + attr1.get(i).getValue()
						+"], attribute2=[" + attr2.get(i).getName() + "=" + attr2.get(i).getValue() +"]");
				return false;
			}
		}
		if(!child1.getTextTrim().equals(child2.getTextTrim())){
			log.debug("Text unmatch|Text1=[" + child1.getTextTrim() + "], Text2=[" + child2.getTextTrim() + "]");
			return false;
		}
		List<Content> content1 = element1.getContent();// 取元素的所有内容
		List<Content> content2 = element2.getContent();// 取元素的所有内容
		Iterator<Content> iterator1 = content1.iterator();
		Iterator<Content> iterator2 = content2.iterator();
		Object obj1 = null;
		Object obj2 = null;
		while (iterator1.hasNext()&&iterator2.hasNext()) {
			while(iterator1.hasNext()){
				obj1 = iterator1.next();
				if(!(obj1 instanceof Comment)){
					break;
				}
			}
			while(iterator2.hasNext()){
				obj2 = iterator2.next();
				if(!(obj2 instanceof Comment)){
					break;
				}
			}
	    	if((obj1 instanceof CDATA)&&(obj2 instanceof CDATA)){
	    		if(!((CDATA) obj1).getTextTrim().equals(((CDATA) obj2).getTextTrim())){
	    			log.debug("CDATA unmatch|CDATA1=[" + (CDATA) obj1 + "], CDATA2=[" + (CDATA)obj2 + "]");
	    			return false;
	    		}
	    	}
	    	if((obj1 instanceof DocType)&&(obj2 instanceof DocType)){
	    		if(!((DocType) obj1).getValue().equals(((DocType) obj2).getValue())){
	    			log.debug("DocType unmatch|DocType1=[" + (DocType) obj1 + "], DocType2=[" + (DocType)obj2 + "]");
	    			return false;
	    		}
	    	}
	    	if((obj1 instanceof Text)&&(obj2 instanceof Text)){
	    		if(!((Text) obj1).getValue().equals(((Text) obj2).getValue())){
	    			log.debug("Text unmatch|Text1=[" + (Text) obj1 + "], Text2=[" + (Text)obj2 + "]");
	    			return false;
	    		}
	    	}
			if ((obj1 instanceof Element)&&(obj2 instanceof Element)) {// 如果是子元
				if (!compareElement((Element)obj1, (Element)obj2)) {
					return false;
				}
			}
		}

		while(iterator1.hasNext()){
			obj1 = iterator1.next();
			if(!(obj1 instanceof Comment)){
				break;
			}
		}
		while(iterator2.hasNext()){
			obj2 = iterator2.next();
			if(!(obj2 instanceof Comment)){
				break;
			}
		}
		if(iterator1.hasNext()||iterator2.hasNext()){
	    	log.debug("size of Content in Element=[" + element1 + ", " + element2 + "] find in two file not equal");
			return false;
		}
		return true;
	}
}
