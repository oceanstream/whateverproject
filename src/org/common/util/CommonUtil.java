package org.common.util;

import java.awt.Component;
import java.awt.Point;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import java.util.List;

import org.common.dataobject.Ingredient;
import org.common.dataobject.Seasoning;
import org.common.dataobject.SemiProduct;



public class CommonUtil {

	/** 
	 * 判断字符串是否是整数 
	 */  
	public static boolean isInteger(String value) {  
		try {  
			int result = Integer.parseInt(value);  
			if(result>0){
				return true;  
			}
			return false;
		} catch (NumberFormatException e) {  
			return false;  
		}  
	}  

	/** 
	 * 判断字符串是否是浮点数 
	 */  
	public static boolean isDouble(String value) {  
		try {  
			Double.parseDouble(value);  
			if (value.contains("."))  
				return true;  
			return false;  
		} catch (NumberFormatException e) {  
			return false;  
		}  
	}  


	/** 
	 * 判断字符串是否是数字 
	 */  
	public static boolean isNumber(String value) {  
		return isInteger(value) || isDouble(value);  
	}    

	/**
	 * 
	 * double保留两位小数
	 * 
	 */

	public static String formatFloat(float result){
		DecimalFormat  df = new  DecimalFormat("#.0"); 
		return df.format(result);
	}


	public static Point getCenterPointOnScreen(Component p){
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		return new Point((width-p.getWidth())/2, (height-p.getHeight())/2);
	}

	public static int SemiContainedInTheList(List<SemiProduct> list, String name){
		for(int i=0;i<list.size();i++){
			SemiProduct semi = list.get(i);
			if(semi.getName().equals(name)){
				return i;
			}
		}
		return -1;
	}
	
	public static int IngreContainedInTheList(List<Ingredient> list, String name){
		for(int i=0;i<list.size();i++){
			Ingredient ingre = list.get(i);
			if(ingre.getName().equals(name)){
				return i;
			}
		}
		return -1;
	}
	
	
	public static int SeaContainedInTheList(List<Seasoning> list, String name){
		for(int i=0;i<list.size();i++){
			Seasoning sea = list.get(i);
			if(sea.getName().equals(name)){
				return i;
			}
		}
		return -1;
	}
	
	
}
