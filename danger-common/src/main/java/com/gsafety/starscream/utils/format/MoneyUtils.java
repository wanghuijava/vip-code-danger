package com.gsafety.starscream.utils.format;

import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;

import com.gsafety.starscream.utils.RegUtils;

/**
 * 金额格式化工具类
 * @author chenwenlong
 *
 */
public class MoneyUtils {

	
	/**
	 * 将数字转换成指定格式(##,###.000)的金额形式。
	 * @param money
	 * @return
	 */
	public static String format(String money) {
		return format(money,"##,###.000");
	}
	
	/**
	 * 将数字转换成指定格式(##,###.000)的金额形式。
	 * @param money
	 * @return
	 */
	public static String format(double money) {
		return format(money,"##,###.000");
	}
	
	/**
	 * 将数字转换成指定格式的金额形式。
	 * 例：format("12345.4567","##,###.00") 结果 12,345.45
	 * @param money
	 * @param format
	 * @return
	 */
	public static String format(String money,String format) {
		if(!StringUtils.isEmpty(money)&&RegUtils.isNumber(money)){
			return format(Double.parseDouble(money),format);
		}
		return "“"+money+"”不能转换成金额";
	}
	
	/**
	 * 将数字转换成指定格式的金额形式。
	 * 例：format(1234.456,"##,###.000") 结果 1,234.456
	 * @param money
	 * @param format
	 * @return
	 */
	public static String format(double money,String format) {
		DecimalFormat moneyFormat = new DecimalFormat();
		moneyFormat.applyPattern(format);
		return moneyFormat.format(money);
	}

	/**
	 * 将数字转换成大写人民币形式
	 * @param money
	 * @return
	 */
	public static String RMBFormat(String money) {
		if(!StringUtils.isEmpty(money)&&RegUtils.isNumber(money)){
			return RMBFormat(Double.parseDouble(money));
		}
		return "“"+money+"”不能转换成金额";
	}
	
	/**
	 * 将数字转换成大写人民币形式
	 * @param money
	 * @return
	 */
	public static String RMBFormat(double money){
        String fraction[] = {"角", "分"};
        String digit[] = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
        String unit[][] = {{"元", "万", "亿"},{"", "拾", "佰", "仟"}};
 
        String head = money < 0? "负": "";
        money = Math.abs(money);
         
        String s = "";
        for (int i = 0; i < fraction.length; i++) {
            s += (digit[(int)(Math.floor(money * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", "");
        }
        if(s.length()<1){
            s = "整";    
        }
        int integerPart = (int)Math.floor(money);
 
        for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
            String p ="";
            for (int j = 0; j < unit[1].length && money > 0; j++) {
                p = digit[integerPart%10]+unit[1][j] + p;
                integerPart = integerPart/10;
            }
            s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
        }
        return head + s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
    }
	
}
