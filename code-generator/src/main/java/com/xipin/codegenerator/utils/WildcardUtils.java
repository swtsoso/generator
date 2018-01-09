package com.xipin.codegenerator.utils;

import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mybatis.generator.internal.util.StringUtility;

/**
 * 通配符
 * @author songchangyou
 *
 */
public class WildcardUtils {

	/**
	 * 给定的字符串是否可以被通配符字符串匹配
	 * @param source
	 * @param wildcardStr
	 * @return
	 */
	public static boolean stringInWildcarString(String source,String wildcardStr){
		boolean result = false;
		if(StringUtility.stringHasValue(source)||
				StringUtility.stringHasValue(wildcardStr)){
			String pattern = "^"+wildcardStr.replace("*", ".*")+"$";
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(source);
			result =  m.matches();
		}
		return result;
	}
	
	/**
	 * 给定的字符串是否可以被通配符字符串匹配
	 * @param source
	 * @param wildcardSet
	 * @return
	 */
	public static boolean stringInWildcarString(String source,Set<String> wildcardSet){
		boolean result = false;
		if(StringUtility.stringHasValue(source)||wildcardSet != null){
			Iterator<String> it = wildcardSet.iterator();
			while(it.hasNext()){
				String wildcardStr = it.next();
				if(StringUtility.stringHasValue("wildcardStr")){
					String pattern = "^"+wildcardStr.replace("*", ".*")+"$";
					Pattern p = Pattern.compile(pattern);
					Matcher m = p.matcher(source);
					result =  m.matches();
					if(result){
						break;
					}
				}
			}
		}
		return result;
	}
	
}
