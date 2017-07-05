package com.gsafety.starscream.utils;

import net.paoding.rose.web.Invocation;


public class RoseRequestContextPath {

	private RoseRequestContextPath(){}
	
	private static RoseRequestContextPath instance = new  RoseRequestContextPath();
	
	public static RoseRequestContextPath getInstance(){
		if(instance == null){
			instance = new  RoseRequestContextPath();
		}
		return instance;
	}
	
	public String getRoserequestContext(Invocation inv){
		return inv.getRequest().getContextPath();
	}
	
}
