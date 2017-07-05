package com.gsafety.starscream.basedata.controllers;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Post;

import org.springframework.util.StringUtils;

import com.gsafety.common.utils.AjaxUtils;
import com.gsafety.starscream.basedata.model.ComGroup;
import com.gsafety.starscream.basedata.model.User;
import com.gsafety.starscream.basedata.service.ComGroupService;
import com.gsafety.starscream.basedata.service.OrgUserService;
import com.gsafety.starscream.constant.CodeConstant;
import com.gsafety.starscream.utils.SysUserUtils;
import com.gsafety.util.json.JsonUtils;
/**
 * 组织机构controller
 * @author chenwenlong
 *
 */
@Path("basedata")
public class ComGroupController {

	@Resource
	private Invocation inv;
	
	@Resource
	private ComGroupService comGroupService;
	
	@Resource
	private OrgUserService orgUserService;
	
	/**
	 * 添加常用组
	 * @param comGroupJson
	 * @return
	 */
	@Post("comgroup/save")
	public String comgroup_save(@Param("param")String comGroupJson){
		//ComGroup的json字符串转换成对象
		ComGroup comGroup = JsonUtils.getObject(comGroupJson, ComGroup.class);
		
		User user = SysUserUtils.getCurrentUser(inv);
		
		//组织机构名称必填
		if(StringUtils.isEmpty(comGroup.getGroupName())){
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM, "常用组名称不能为空", inv);
		}
		comGroup.setCreateTime(new Date());
		comGroup.setUpdateTime(new Date());
//		comGroup.setCreatorName(user.getOrgUser().getUsername());
//		comGroup.setCreatorId(user.getId());
		//数据保存
		comGroup = comGroupService.save(comGroup);
		return AjaxUtils.printSuccessJson(comGroup,"常用组保存成功", inv);
	}
	
	/**
	 * 常用组删除
	 * @param id
	 * @return
	 */
	@Post("comgroup/delete")
	public String comgroup_delete(@Param("id")String id){
		//删除常用组，级联删除通讯录用户
		comGroupService.delete(id);
		//删除系统用户
		orgUserService.deleteFromGroup(id, null);
		return AjaxUtils.printSuccessJson(null,"常用组删除成功", inv);
	}
	
	/**
	 * 加载常用组
	 * @param id
	 * @return
	 */
	@Post("comgroup/load")
	public String comgroup_load(@Param("id")String id) {
		ComGroup comGroup = comGroupService.findById(id);
		return AjaxUtils.printSuccessJson(comGroup, inv);
	}
 
}
