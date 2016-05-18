package com.ilyon.tools.utils;

import java.util.regex.Pattern;

/**
 * @author lyon
 *
 */
public abstract class MaskTool {
	private static final Pattern MASK_COMPILE = Pattern.compile("(.+)?\\[MASK\\].+\\[/MASK\\](.+)?");
	public static String extract(String string) {
		 
		if(string==null||!MASK_COMPILE.matcher(string).find()){
			return string;
		}
		return string.replace("[MASK]", "").replace("[/MASK]", "");
	}
	public static void main(String a[]){
		System.out.println(MASK_COMPILE.matcher("[MASK].+21212121[/MASK]").find());
		System.out.println(mask("erwr[MASK].+21212121[/MASK]cd"));
	}
	public static String mask(String string) {
		if(string==null||!MASK_COMPILE.matcher(string).find()){
			return string;
		}
		String pre=string.substring(0,string.indexOf("[MASK]"));
		String end=string.substring(string.indexOf("[/MASK]"));
		string = string.substring(string.indexOf("[MASK]"));
		string = string.substring(0,string.lastIndexOf(("[/")));
		return pre+"[MASK]"+string.replaceAll(".","*")+end;
	}
}
