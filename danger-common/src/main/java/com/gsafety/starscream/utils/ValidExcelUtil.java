package com.gsafety.starscream.utils;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.BooleanCell;
import jxl.Cell;
import jxl.CellType;
import jxl.NumberCell;

import org.apache.commons.lang.StringUtils;

import com.gsafety.starscream.utils.format.DateUtil;

/**
 * 
 */
public class ValidExcelUtil  {
	
	public static final String DATA_SPLIT = "_";
	
	
	public static String null2Empty(String src){
		if(src == null){
			return "";
		}
		return src;
	}
	
	public static String arrayData2SqlStr(String[] codes){
		if(codes == null || codes.length == 0){
			return null;
		}
		String rtn = "";
		for(int i=0;i<codes.length;i++){
			rtn += ",'"+codes[i]+"'";
		}
		return rtn.substring(1);
	}
	
	/**
	 * 通过cell单元格获取内容
	 * @param cell
	 * @return String 
	 */
	public static String getCellString(Cell cell){
		if(StringUtils.isEmpty(cell.getContents())){
			return null;
		}
		String result = "";
		if (cell.getType() == CellType.BOOLEAN) {
			result = Boolean.toString(((BooleanCell)cell).getValue()).trim();
		} else if (cell.getType() == CellType.NUMBER || cell.getType() == CellType.NUMBER_FORMULA) {
			result = Double.toString(((NumberCell)cell).getValue()).trim();
			if(result.endsWith(".0")){
				result = result.substring(0,result.length()-2); 
			}
			
		} else {
			result = cell.getContents().trim();
		}
		return result;
	}
	
   /**
	 * 检查数字1到8位
	 * @param number
	 * @return
	 */
  public static boolean checkIsNum(String number) {
	   String  regex="[0-9]{1,8}";
	   Pattern pattern = Pattern.compile(regex);
	   Matcher matcher = pattern.matcher(number);   
	   return matcher.matches();
  }
  	/**
	 * 检查数字1到11位
	 * @param number
	 * @return
	 */
	public static boolean checkIsNum11(String number) {
		   String  regex="[0-9]{1,11}";
		   Pattern pattern = Pattern.compile(regex);
		   Matcher matcher = pattern.matcher(number);   
		   return matcher.matches();
	}

  	/**
	 * 检查邮箱
	 * @param mail
	 * @return
	 */
	public static boolean checkMail(String mail) {
		   String  regex="[\\w]+([\\w-]+)*@[\\w-]+(.[\\w-]+)+";
		   Pattern pattern = Pattern.compile(regex);
		   if (StringUtils.isEmpty(mail)){
			   return false;
		   }
		   Matcher matcher = pattern.matcher(mail.trim());   
		   return matcher.matches();
	}
	
	
	/**
	 * 根据传递过来的值进行转化成double类型数字。
	 * @param str 字段
	 * @return 返回转换后数字，若是转化失败，则返回默认的0.0
	 */
	public static double getDoubleByStr(String str){
		double number = 0.0;
		try {
			number = Double.parseDouble(str);
		} catch (NumberFormatException e) {
			number =0.0;
		}
		return number;
	}
	
	/**
	 * 过滤字符
	 * @param object
	 * @return
	 */
	public static String changeNull(String value){
		if(value == null){
			value = "";
		}
		
		return value;
	}
	
	/**
	 * 过滤字符
	 * @param object
	 * @return
	 */
	public static String splitValue(String value){
		String temp = value;
		if(temp == null){
			temp = "";
		}

		if(temp.length() > 0){
			String tempsplit =  temp.substring(0, 1);
			if(tempsplit.equals("=")){
				tempsplit = " ";
			}else if(tempsplit.equals("-")){
				tempsplit = " ";
			}else if(tempsplit.equals("‘")){
				tempsplit = " ";
			}else if(tempsplit.equals("’")){
				tempsplit = " ";
			}else if(tempsplit.equals("’")){
				tempsplit = " ";
			}
			temp = tempsplit+temp.substring(1);
		}
		temp = temp.replaceAll("\"", "“").replaceAll(",", "，").replaceAll("'", "“")
		.replaceAll(";", "；").replaceAll("\\\\", "").replaceAll("\t", "")
		.replaceAll("\r", "").replaceAll("\n", "").replaceAll("\b", "");
		
		return temp;
	}
	
	
	/***********************一下方法经过验证可以验证相关属性的格式***************************************/
	/**
	 * 通用方法
	 * @param regex
	 * @param target
	 * @return
	 */
	public static boolean validate(String regex,String target){
		   if(StringUtils.isEmpty(target)){
				return false;
		   }
		   Pattern pattern = Pattern.compile(regex);
		   Matcher matcher = pattern.matcher(target);
		   return matcher.matches();
	}
	 /**
	 * 检查英文字母
	 * @param number
	 * @param flag 1大写，2小写，其余大小写均可以
	 * @return
	 */
  public static boolean IsChar(String s,String flag) {
	   String  regex="^[A-Z]+$";
	   if("1".equals(flag)){
		   regex="^[A-Za-z]+$";
	   }else if("2".equals(flag)){
		   regex="^[a-z]+$";
	   }
	   return validate(regex,s);
  }
	
	 /**
	 * 检查数字
	 * @param number
	 * @return
	 */
  public static boolean IsNum(String number) {
	  if(StringUtils.isEmpty(number)){
			return true;
		}
	   String  regex="^[0-9]*$";
	   return validate(regex,number);
  }
	
	/**
	 * 校验非0的正整数
	 * @param ss
	 * @return
	 */
	public static boolean isDigit(String s){
		if(StringUtils.isEmpty(s)){
			return true;
		}
		String  regex="^(0|[1-9][0-9]*)$";
		return validate(regex,s);
	}
	/**
	 * 校验IP
	 * @param ss
	 * @return
	 */
	public static boolean isIP(String ip){
		if(StringUtils.isEmpty(ip)){
			return true;
		}
		String  regex="^[0-9.]{1,20}$";
		return validate(regex,ip);
	}
	
	/**
	 * 校验非负整数
	 * @param ss
	 * @return
	 */
	public static boolean isFFDigit(String s){
		if(StringUtils.isEmpty(s)){
			return true;
		}
		String  regex="^\\d+$";
		return validate(regex,s);
	}
	
	/**
	 * 校验非负数
	 * @param ss
	 * @return
	 */
	public static boolean isFFFloat(String s){
		if(StringUtils.isEmpty(s)){
			return true;
		}
		String  regex="^\\d+(\\.\\d+)?$";
		return validate(regex,s);
	}
	/**
	 * 检查邮编
	 * @param postcode
	 * @return
	 */
   public static boolean checkPostCode(String postcode) {
	   if(StringUtils.isEmpty(postcode)){
			return true;
		}
	   String  regex="^[0-9]{6,6}$";
	   return validate(regex,postcode);
   }
	
	/**
	 * 验证手机格式，只能是以1开头的数字11位
	 * @param mobile
	 * @return
	 */
	public static boolean isMobile(String mobile){
		if(StringUtils.isEmpty(mobile)){
			return true;
		}
		String  regex="^1\\d{10}$";
		return validate(regex,mobile);
	}
	
	/**
	 * 校验email
	 * @param tel
	 * @return
	 */
	public static boolean isEmail(String email){
		if(StringUtils.isEmpty(email)){
			return true;
		}
		 String  regex="^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
		 return validate(regex,email);
	}
	
	/**
	 * 校验普通电话、传真号码：--可以“+”开头--，除数字外，可含有“-”
	 * @param tel
	 * @return
	 */
	public static boolean isTel(String tel){
		if(StringUtils.isEmpty(tel)){
			return true;
		}
		 String  regex="^[+]{0,1}(\\d){1,3}?([-]?((\\d)){1,12})+$";
		   return validate(regex,tel);
	}
	
	/**
	 * 根据字符串进行验证传真格式是否正确(同电话)
	 * @param str
	 * @return
	 */
	public static boolean isFax(String fax){
		if(StringUtils.isEmpty(fax)){
			return true;
		}
		String regex = "^[+]{0,1}(\\d){1,3}?([-]?((\\d)){1,12})+$";
		return validate(regex,fax);
	}
	
	/**
	 * 根据字符串进行验证日期格式
	 * @param str
	 * @return
	 */
	public static boolean isDate(String date){
		if(StringUtils.isEmpty(date)){
			return true;
		}
		return DateUtil.isDate(date, null);
	}
	
	/**
	 * 根据字符串进行验证传真格式是否正确(同电话)
	 * @param str
	 * @return
	 */
	public static boolean isIdCard(String idCard){
		if(StringUtils.isEmpty(idCard)){
			return true;
		}
		if(idCard.length()!=15 && idCard.length() != 18) 
			return false;

		if(idCard.length() == 15)
		{
			idCard = idCard.substring(0,6)+"19"+idCard.substring(6);
		}
		if(!idCard.substring(0,17).matches("^\\d{17}$"))
		{
			return false;
		}
		return true;
	}
	
	/**
	 * 值验证
	 * @param rowId 第几行，从0开始
	 * @param label 名称
	 * @param value 值
	 * @param size 长度限制:为0则不验证长度
	 * @param rules 验证规则 notnull:0；1:mobile；2:tel；3:fax；4:date；5:int；6:float；7:email；8:idcard；9：postcode;
	 * @return
	 */
	public static String checkValue(int rowId,String label,String value ,int size, int[] rules){
		if(size != 0){
			if (StringUtils.isNotEmpty(value) && value.length() > size) {
				return "第" + (rowId + 1) + "行"+label+"最多只允许输入"+size+"个字！";
			}
		}
		if(rules == null || rules.length == 0){
			return null;
		}
		
		String result= null;
		
		for(int rule : rules){
			switch (rule){
			//notnull非空
			case 0:
				if(StringUtils.isEmpty(value)){
					return "第" + (rowId + 1) + "行"+label+"不能为空！";
				}
				break;
			//mobile
			case 1:
				if(!isMobile(value)){
					return "第" + (rowId + 1) + "行"+label+"格式不正确！正确格式为:11位手机号";
				}
				break;
			//tel
			case 2:
				if(!isTel(value)){
					return "第" + (rowId + 1) + "行"+label+"格式不正确！";
				}
				break;
			//fax
			case 3:
				if(!isFax(value)){
					return "第" + (rowId + 1) + "行"+label+"格式不正确！";
				}
				break;
			//date
			case 4:
				if(!isDate(value)){
					return "第" + (rowId + 1) + "行"+label+"格式不正确！正确格式如：2015-08-10";
				}
				break;
			//int
			case 5:
				if(!isFFDigit(value)){
					return "第" + (rowId + 1) + "行"+label+"格式不正确！应为整数！";
				}
				break;
			//float
			case 6:
				if(!isFFFloat(value)){
					return "第" + (rowId + 1) + "行"+label+"格式不正确！应为数值！";
				}
				break;
			//email
			case 7:
				if(!isEmail(value)){
					return "第" + (rowId + 1) + "行"+label+"格式不正确，正确格式如:admin@domian.com";
				}
				break;
			//postcode
			case 9:
				if(!checkPostCode(value)){
					return "第" + (rowId + 1) + "行"+label+"格式不正确";
				}
				break;
			//idcard
			case 8:
				if(!isIdCard(value)){
					return "第" + (rowId + 1) + "行"+label+"格式不正确！";
				}
				break;
			}
			
			//只要一个验证不通过就返回
			if(result != null){
				return result;
			}

		}
		
		return result;
	}
	
	/**
	 * 替换提示信息
	 * @param i
	 * @param s
	 * @param flag ,标记提示为空和格式错误，超长 0,空，1:格式错误，2，超长,3,填写错误
	 * @return
	 */
	public static String changeValue(int i,String s,String flag ,int size){
		String result = "第" + (i + 1) + "行"+s+"为空！";
		if("1".equals(flag)){
			result = "第" + (i + 1) + "行"+s+"格式错误！";
		}else if("2".equals(flag)){
			result = "第" + (i + 1) + "行"+s+"最多只允许输入"+size+"个字！";
		}else if("3".equals(flag)){
			result = "第" + (i + 1) + "行"+s+"填写错误！";
		}
		return result;
	}
	
	
   public static void main(String[] args) throws ParseException{
	   String ss = "333333";
	   System.out.println(checkPostCode(ss));
   }
}
