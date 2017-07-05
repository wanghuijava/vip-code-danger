package com.gsafety.starscream.utils.db;

import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import com.gsafety.starscream.exception.SequenceNoValException;
import com.gsafety.starscream.utils.format.DateUtil;

/**
 * 生成主键
 * @author 陈文龙
 *
 */
public class PrimaryKeyUtils {

	private static String preLongTime = StringUtils.EMPTY;
	private static String preLongTimeStr = StringUtils.EMPTY;
	
	private static String preFormatTime = StringUtils.EMPTY;
	private static String preFormatTimeStr = StringUtils.EMPTY;
	
	/**
	 * 根据时间获取16位long类型主键 【13位时间+3位校验码】
	 * @return
	 */
	public static long getLongTimeKey() {
		String currentTime = "";
		String rtnKey = "";
		
		do{
			currentTime = new Date().getTime() + "";
			rtnKey = currentTime + RandomStringUtils.randomNumeric(3);
		}while(preLongTimeStr.contains(rtnKey));
		
		if(preLongTime.equals(currentTime)){
			preLongTimeStr += rtnKey + "|";
		} else {
			preLongTime = currentTime;
			preLongTimeStr = rtnKey;
		}
		return Long.parseLong(rtnKey);
	}
	public static String getStringTimeKey(){
		return String.valueOf(getLongTimeKey());
	}
	
	/**
	 * 根据时间获取16位String类型主键 【14位时间+2位校验码】
	 * @return
	 */
	public static String getFormatTimeKey() {
		String currentTime;
		String rtnKey;
		
		do{
			currentTime = DateUtil.generateTimeStamp();
			rtnKey = currentTime + RandomStringUtils.randomNumeric(2);
		}while(preFormatTimeStr.contains(rtnKey));
		
		if(preFormatTime.equals(currentTime)){
			preFormatTimeStr += rtnKey + "|";
		} else {
			preFormatTime = currentTime;
			preFormatTimeStr = rtnKey;
		}
		
		return rtnKey;
	}
	
	/**
	 * 通过数据库函数GetSequenceVal拼接主键
	 * 主要用于机构Id的生成
	 * @param key 行政区划代码
	 * @param count 位数
	 * @return
	 * @throws SequenceNoValException
	 */
	public static String getSequenceKey(String key,int count) throws SequenceNoValException{
		String tempSequence = SequenceUtils.getSequenceVal(key);
		if(StringUtils.isEmpty(tempSequence)) {
			throw new SequenceNoValException();
		}
		tempSequence = String.format("%"+count+"s", tempSequence).replace(" ", "0");
		return key + tempSequence;
	}
	/**
	 * 通过数据库函数GetSequenceVal拼接主键
	 * 直接返回 生成的序号
	 * @param key 
	 * @return String 字符串
	 * @throws SequenceNoValException
	 */
	public static String getSequenceKey(String key) throws SequenceNoValException{
		return SequenceUtils.getSequenceVal(key);
	}
	/**
	 * 通过数据库函数GetSequenceVal拼接主键
	 * 直接返回 生成的序号
	 * @param key 
	 * @return Integer 数字
	 * @throws SequenceNoValException
	 */
	public static int getIntSequenceKey(String key) throws SequenceNoValException{
		return Integer.parseInt(SequenceUtils.getSequenceVal(key));
	}
}
