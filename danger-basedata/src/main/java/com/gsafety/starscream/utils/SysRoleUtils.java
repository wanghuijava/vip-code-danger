package com.gsafety.starscream.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.gsafety.starscream.basedata.model.Authority;
import com.gsafety.starscream.basedata.model.Role;
import com.gsafety.starscream.basedata.service.RoleService;
import com.gsafety.util.json.JsonUtils;

public class SysRoleUtils {
	
	private static final Logger log = Logger.getLogger(SysRoleUtils.class);
	public static Map<String, List<Authority>> roleMap = new HashMap<String, List<Authority>>();
	
	static {
		RoleService roleService = (RoleService)RoseContextUtils.getBean("roleService");
		//获取所有角色权限列表存储到内存中
		List<Role> roleList = roleService.findAll();
		for(Role role : roleList){
			roleMap.put(role.getId(), role.getAuthoritys());
			
			if(log.isDebugEnabled()){
				log.debug("权限ID:" + role.getId() + "---" + JsonUtils.getJsonStr(role.getAuthoritys()));
			}
		}
	}
	
	//根据用户角色ID获取角色所有权限对象
	public static List<Authority> getRoleAuthorityList(String roleId){
		return roleMap.get(roleId);
	}
	
	//根据用户角色ID获取权限JSON字符串（主要用户系统用户权限保存）
	public static String getRoleAuthority2Json(String roleIds){
		String[] roleIdsStr = roleIds.split(",");
		Map<String, String> authorityMap = new HashMap<String, String>();
		for(String roleIdStr:roleIdsStr) {
			String roleId = roleIdStr;
			List<Authority> authorityList = roleMap.get(roleId);
			//获取对象判断是否存在
			if(authorityList == null){
				continue;
			}
			//便利该角色所属权限列表，生成权限JSON字符串
			for(Authority authority : authorityList){
				authorityMap.put(authority.getId(), authority.getUrl());
			}
		}
		return JsonUtils.getJsonStr(authorityMap);
	}
	
	//根据用户角色ID获取权限ID List转成JSON数组形式 （主要用户前端用户权限到的显示）
	public static String getRoleAuthorityIDList2Json(String roleIds){
		String[] roleIdsStr = roleIds.split(",");
		List<String> authorityIdList = new ArrayList<String>();
		for(String roleIdStr:roleIdsStr) {
			String roleId = roleIdStr;
			List<Authority> authorityList = roleMap.get(roleId);
			//获取对象判断是否存在
			if(authorityList == null){
				continue;
			}
			//便利该角色所属权限列表，生成权限JSON字符串
			for(Authority authority : authorityList){
				authorityIdList.add(authority.getId());
			}
		}
		return JsonUtils.getJsonStr(authorityIdList);
	}
	
	//刷新用户角色对应表 （用户角色或权限更新时调用）
	public static void refreshRoleMap(){
		
		if(log.isDebugEnabled()){
			log.debug("---权限roleMap开始刷新---");
		}
		
		roleMap.clear();
		RoleService roleService = (RoleService)RoseContextUtils.getBean("roleService");
		//获取所有角色权限列表存储到内存中
		List<Role> roleList = roleService.findAll();
		for(Role role : roleList){
			roleMap.put(role.getId(), role.getAuthoritys());
			
			if(log.isDebugEnabled()){
				log.debug("权限ID:" + role.getId() + "---" + JsonUtils.getJsonStr(role.getAuthoritys()));
			}
		}
		
	}
}
