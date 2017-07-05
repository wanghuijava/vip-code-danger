package com.gsafety.starscream.utils;

import net.paoding.rose.web.Invocation;

public class R {

	/**
	 * r: reditect 重定向到相应请求 
	 * @param inv
	 * @param url
	 * @return
	 */
	public static String redirect(String url,Invocation inv){
		String roseContext = RoseRequestContextPath.getInstance().getRoserequestContext(inv);
		return "r:"+roseContext+url ;
	}
	
	/**
	 * 转到相应的页面 
	 * @param inv
	 * @param url
	 * @return
	 */
	public static String view(String url,Invocation inv){
		String roseContext = RoseRequestContextPath.getInstance().getRoserequestContext(inv);
		return roseContext+url ;
	}

	/**
	 * r: forwrad 重定向到相应请求 
	 * @param inv
	 * @param url
	 * @return
	 */
	public static String forwrad(String url,Invocation inv){
		String roseContext = RoseRequestContextPath.getInstance().getRoserequestContext(inv);
		return "a:"+roseContext+url ;
	}
	
	
}
