package com.gsafety.starscream.utils;

import java.util.HashMap;
import java.util.Map;

import com.gsafety.starscream.basedata.service.BasSysCfgpropService;

public class BasSysCfgpropUtils {
	
	private static Map<String, String> basSysCfgpropMap = null;
	
	//获取属性配置map
	public static Map<String, String> getSysCfgpropMap(){
		if(basSysCfgpropMap == null){
			basSysCfgpropMap = new HashMap<String, String>();
			BasSysCfgpropService basSysCfgpropService = (BasSysCfgpropService)RoseContextUtils.getBean("sysCfgpropService");
			//获取所有角色权限列表存储到内存中
			basSysCfgpropMap = basSysCfgpropService.getSysCfgPropMap();
		}
		return basSysCfgpropMap;
	}
	
	//根据属性name获取value值
	public static String getSysCfgpropValue(String key){
		return basSysCfgpropMap.get(key);
	}
}
