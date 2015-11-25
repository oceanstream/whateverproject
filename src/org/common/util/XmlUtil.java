package org.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.common.dataobject.Dish;
import org.common.dataobject.Seasoning;
import org.common.dataobject.SemiProduct;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

@SuppressWarnings("unchecked")
public class XmlUtil {

	/**
	 * 解析dishes，获取所有数据
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static HashMap<String,Dish> getDishes(File file) throws Exception{
		HashMap<String,Dish> result = new HashMap<String,Dish>();
		SAXReader reader = new SAXReader();
		Document document = reader.read(file); 
		Element root = document.getRootElement();
		List<Element> list = root.elements();
		for(Element e : list){
			String name = e.element("name").getText().toString();
			List<SemiProduct> list_semi = new ArrayList<SemiProduct>();
			List<Seasoning> list_seasonings = new ArrayList<Seasoning>();
			Element e1 = e.element("semi-products");
			Element e2 = e.element("seasonings");
			List<Element> list1 = e1.elements();
			List<Element> list2 = e2.elements();
			
			for(Element e3 : list1){
				String semi_name = e3.elementText("name");
				String semi_count = e3.elementText("count");
				System.out.println(Float.parseFloat(semi_count));
				String semi_ingredient = e3.elementText("ingredient");
				String semi_scale = e3.elementText("scale");
				SemiProduct semiProduct = new SemiProduct(semi_name, Float.parseFloat(semi_count), semi_ingredient, Float.parseFloat(semi_scale));
				list_semi.add(semiProduct);
			}
			
			for(Element e4 : list2){
				String seasoning_name = e4.elementText("name");
				String seasoning_count = e4.elementText("count");
				Seasoning seasoning = new Seasoning(seasoning_name, Float.parseFloat(seasoning_count));
				list_seasonings.add(seasoning);
			}
			
			Dish dish = new Dish(name,list_semi, list_seasonings);
			result.put(name,dish);
		}
		return result;
	}

	public static HashMap<String, String> getIngrediants(File file) throws Exception{
		// TODO Auto-generated method stub
		HashMap<String,String> result = new HashMap<String,String>();
		
		SAXReader reader = new SAXReader();
		Document document = reader.read(file);
		Element root = document.getRootElement();
		
		List<Element> list = root.elements("dish");
		for(Element e:list){
			List<Element> list2 = e.elements("ingrediant");
			String s = "";
			for(Element e1:list2){
					s = s+e1.attribute("name").getValue()+"×"+e1.getText()+"，";
			}
			String sub_s = s.substring(0,s.length()-1);
			
			result.put(e.attributeValue("name"), sub_s);
		}
		return result;
	}
}
