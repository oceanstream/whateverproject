package org.common.util;

import java.text.DecimalFormat;

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
	
	 public static String formatDouble(double result){
		 DecimalFormat  df = new  DecimalFormat("#.00"); 
		 return df.format(result);
	 }
	 
}
