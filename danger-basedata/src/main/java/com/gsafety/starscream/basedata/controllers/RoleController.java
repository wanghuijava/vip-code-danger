package com.gsafety.starscream.basedata.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Post;

import org.springframework.util.StringUtils;

import com.gsafety.common.utils.AjaxUtils;
import com.gsafety.starscream.basedata.model.Authority;
import com.gsafety.starscream.basedata.model.Role;
import com.gsafety.starscream.basedata.service.RoleService;
import com.gsafety.starscream.constant.CodeConstant;
import com.gsafety.util.json.JsonUtils;

@Path("basedata")
public class RoleController {

	@Resource
	private Invocation inv;
	
	@Resource
	private RoleService roleService;
	
	/**
	 * 添加角色
	 * @param roleJson
	 * @return
	 */
	@Post("role/save")
	public String role_save(@Param("param")String roleJson) {
		//Role的json字符串转换成对象
		Role role = JsonUtils.getObject(roleJson, Role.class);
		
		//角色名称必填
		if(StringUtils.isEmpty(role.getName())){
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM, "角色名称不能为空", inv);
		}
		//角色类型必填
		if(role.getTypeCode()==null){
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM, "角色类型不能为空", inv);
		}
		//设置角色对应权限
		if(role.getAuthorityIds()!=null&&role.getAuthorityIds().length>0) {
			List<Authority> authoritys = new ArrayList<Authority>();
			for(String authorityId:role.getAuthorityIds()) {
				Authority authority = new Authority();
				authority.setId(authorityId);
				authoritys.add(authority);
			}
		}
		//数据保存
		role = roleService.save(role);
		return AjaxUtils.printSuccessJson(role,"角色保存成功", inv);
	}
	
	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	@Post("role/delete")
	public String role_delete(@Param("id")String id) {
		//角色ID必填
		if(id==null){
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM, "角色ID不能为空", inv);
		}
		roleService.delete(id);
		return AjaxUtils.printSuccessJson(null,"角色删除成功", inv);
	}
	
	/**
	 * 修改角色
	 * @param roleJson
	 * @return
	 */
	@Post("role/update")
	public String role_update(@Param("param")String roleJson) {
		//Role的json字符串转换成对象
		Role role = JsonUtils.getObject(roleJson, Role.class);
		
		//角色ID必填
		if(role.getId()==null){
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM, "角色ID不能为空", inv);
		}
		//角色名称必填
		if(StringUtils.isEmpty(role.getName())){
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM, "角色名称不能为空", inv);
		}
		//角色类型必填
		if(role.getTypeCode()==null){
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM, "角色类型不能为空", inv);
		}
		//设置角色对应权限
		if(role.getAuthorityIds()!=null&&role.getAuthorityIds().length>0) {
			List<Authority> authoritys = new ArrayList<Authority>();
			for(String authorityId:role.getAuthorityIds()) {
				Authority authority = new Authority();
				authority.setId(authorityId);
				authoritys.add(authority);
			}
		}
		//数据保存
		role = roleService.update(role);
		return AjaxUtils.printSuccessJson(role,"角色修改成功", inv);
	}
	
	/**
	 * 加载角色
	 * @param id
	 * @return
	 */
	@Post("role/load")
	public String role_load(@Param("id")String id) {
		//角色ID必填
		if(id==null){
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM, "角色ID不能为空", inv);
		}
		Role role = roleService.findById(id);
		return AjaxUtils.printSuccessJson(role, inv);
	}
}
