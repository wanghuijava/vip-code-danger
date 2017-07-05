package com.gsafety.starscream.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gsafety.starscream.basedata.model.Org;
import com.gsafety.starscream.basedata.service.OrgService;

public class CodesUtils {
	
	private static Map<String, String> orgNameMap = null;
	
	//获取机构名称map
	public static Map<String, String> getOrgCodeMap(){
		if(orgNameMap == null){
			orgNameMap = new HashMap<String, String>();
			OrgService orgService = (OrgService)RoseContextUtils.getBean("orgService");
			//获取所有角色权限列表存储到内存中
			List<Org> list = orgService.findByParentCode("9006005002008006");
			for(Org org : list){
				orgNameMap.put(org.getOrgName(), org.getOrgCode());
			}
		}
		return orgNameMap;
	}
	
	//根据属性name获取value值
	public static String getOrgCodeByOrgName(String key){
		return getOrgCodeMap().get(key);
	}
}
