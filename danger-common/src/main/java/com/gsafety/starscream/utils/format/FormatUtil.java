package com.gsafety.starscream.utils.format;

import java.math.BigDecimal;

import org.springframework.util.StringUtils;

public class FormatUtil {

	
	/**
	 * DOuble 四舍五入 保留2为小数
	 * @param money
	 * @return
	 */
	public static Double formatPercent(Double percent) {
		BigDecimal decimal = new BigDecimal(percent);
		Double percent1 = decimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		return percent1;
	}
	

	/**
	 * @author wanghui 2015-7-2
	 * 去掉字符编码的末尾的0，最多4个
	 * @param someCode
	 * @return
	 */
	public static String removeEndZero(String someCode) {
		if(StringUtils.isEmpty(someCode)){
			return someCode;
		}
		if(someCode.length()>4 && someCode.endsWith("0000")){
			return someCode.substring(0,someCode.length()-4);
		}
		if(someCode.length()>3 && someCode.endsWith("000")){
			return someCode.substring(0,someCode.length()-3);
		}
		if(someCode.length()>2 && someCode.endsWith("00")){
			return someCode.substring(0,someCode.length()-2);
		}
		if(someCode.length()>1 && someCode.endsWith("0")){
			return someCode.substring(0,someCode.length()-1);
		}
		return someCode;
	}
	
	public static void main (String[] args){
		System.out.println(removeEndZero("A0000"));
		System.out.println(removeEndZero("A2000"));
		System.out.println(removeEndZero("2100"));
		System.out.println(removeEndZero("2110"));
	}
}
