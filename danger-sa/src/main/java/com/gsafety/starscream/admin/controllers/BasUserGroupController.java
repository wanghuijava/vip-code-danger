package com.gsafety.starscream.admin.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.google.gson.JsonArray;
import com.gsafety.common.utils.AjaxUtils;
import com.gsafety.starscream.basedata.model.ComGroup;
import com.gsafety.starscream.basedata.service.ComGroupService;
import com.gsafety.starscream.constant.BasedataConstant;
import com.gsafety.util.json.JsonUtils;

/**
 * @ClassName:BasUserGroupController
 * @Description:分组管理Controller
 * @Author: duzhigang
 * @Version:1.0
 * @Date:2015/04/25
 */
@Path("admin/userGroup")
public class BasUserGroupController {
	@Autowired
	private Invocation inv;
	@Autowired
	private ComGroupService comGroupService;

	@Get("list")
	public String findUserGroup() {
		return "authority/userGroup-list";
	}

	/**
	 * 查询通讯录分组数据
	 * 
	 * @return
	 */
	@Get("findTree")
	public String userGroup_findTree() {
		List<ComGroup> userGroup = comGroupService
				.findTree(BasedataConstant.USERGROUP_ROOT);
		JsonArray jsonArr = JsonUtils.getJsonArray(userGroup, new String[] {});
		return AjaxUtils.printSuccessJson(
				jsonArr.toString().replace("groupName", "name"), inv);
	}

	@Post("findUserGroupById")
	public String userGroup_findUserGroupById(@Param("id") String id) {
		ComGroup userGroup = comGroupService.findById(id);
		String jsonArr = "";
		if (userGroup != null) {
			jsonArr = JsonUtils.getJsonStr(userGroup);
		}
		return AjaxUtils.printSuccessJson(jsonArr.toString(), inv);
	}

	@Post("save")
	public String userGroup_save(@Param("id") String id,
			@Param("parentId") String parentId,
			@Param("groupName") String groupName,
			@Param("groupInfo") String groupInfo) {
		ComGroup groupDto=null;
		if (!StringUtils.isEmpty(id)) {
			groupDto=comGroupService.findById(id);
			groupDto.setParentId(parentId);
			groupDto.setGroupName(groupName);
			groupDto.setGroupInfo(groupInfo);
			groupDto.setUpdateTime(new Date());
		}else{
			groupDto=new ComGroup();
			groupDto.setId(id);
			groupDto.setParentId(parentId);
			groupDto.setGroupName(groupName);
			groupDto.setGroupInfo(groupInfo);
			groupDto.setUpdateTime(new Date());
			groupDto.setCreateTime(new Date());
		}
		//comGroupService.save(groupDto);
		return AjaxUtils.printSuccessJson("[{success:true}]", inv);
	}
	
	@Get("delete")
	public String userGroup_delete(@Param("id") String id){
		comGroupService.delete(id);
		return AjaxUtils.printSuccessJson(null, "删除成功", inv);
	}
	
	@Post("findByParentId")
	public String userGroup_findByParentId(@Param("id") String id){
		List<ComGroup> comList=comGroupService.findByParentId(id);
		Map<String, String> map=new HashMap<String, String>();
		if (comList!=null && comList.size()>0) {
			map.put("flag", "1");
		}else{
			map.put("flag", "0");
		}
		return AjaxUtils.printJson(map, inv);
	}
	

}
