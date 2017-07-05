package com.gsafety.starscream.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式
 * @author chenwenlong
 *
 */
public class RegUtils {

	/**
	 * 判断字符是否是数字
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("^-?[0-9]+(\\.[0-9]+)?$");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
}
