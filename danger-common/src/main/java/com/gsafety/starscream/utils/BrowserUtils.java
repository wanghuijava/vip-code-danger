package com.gsafety.starscream.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class BrowserUtils {

	//浏览器类型
	public static final String[] browsers = {"IE10","IE9","IE8","IE7","FIREFOX","CHROME","OPERA","SAFARI","QQ"};
	//userAgent包含浏览器类型信息
	public static final String[] browserTypes = {"MSIE 10.0","MSIE 9.0","MSIE 8.0","MSIE 7.0","Firefox","Chrome","Opera","Safari","QQBrowser"};
	//移动设备类型
	public static final String[] deviceTypes = {"Android","iPhone","iPad","iPod","Windows Phone","Opera Mobi","Opera Mini","MQQBrowser"};
	
	/**
	 * 根据request获取userAgent信息
	 * @param request
	 * @return
	 */
	private static String getUserAgent(HttpServletRequest request){
		String userAgent = StringUtils.EMPTY;
		if(request!=null) {
			userAgent = request.getHeader("USER-AGENT");
		}
		return userAgent;
	}
	
	/**
	 * 获取登录设备类型 PC：0，手机：1
	 * @param request
	 * @return
	 */
	public static int getLoginType(HttpServletRequest request){
		String userAgent = getUserAgent(request);
		if(userAgent.contains("Windows NT")){
			return 0;
		}
		for(String deviceType:deviceTypes) {
			if(userAgent.contains(deviceType)){
				return 1;
			}
		}
		return 0;
	}
	
	/**
	 * 获取浏览器类型
	 * @param request
	 * @return
	 */
	public static String getBrowserType(HttpServletRequest request){
		String userAgent = getUserAgent(request);
		for(int i=0;i<browserTypes.length;i++) {
			if(userAgent.contains(browserTypes[i])){
				return browsers[i];
			}
		}
		return "OTHER";
	}
	
}
