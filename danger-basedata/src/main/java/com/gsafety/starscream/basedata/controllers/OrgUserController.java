package com.gsafety.starscream.basedata.controllers;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;

import com.gsafety.common.utils.AjaxUtils;
import com.gsafety.starscream.basedata.model.Org;
import com.gsafety.starscream.basedata.model.OrgUser;
import com.gsafety.starscream.basedata.model.User;
import com.gsafety.starscream.basedata.service.OrgService;
import com.gsafety.starscream.basedata.service.OrgUserService;
import com.gsafety.starscream.basedata.service.UserService;
import com.gsafety.starscream.basedata.view.OrgUserStat;
import com.gsafety.starscream.basedata.view.OrgUserStatDTO;
import com.gsafety.starscream.constant.BasedataConstant;
import com.gsafety.starscream.constant.CodeConstant;
import com.gsafety.starscream.utils.ExportExcelCommon;
import com.gsafety.starscream.utils.SysUserUtils;
import com.gsafety.starscream.utils.page.ParamPage;
import com.gsafety.starscream.utils.page.ScrollPage;
import com.gsafety.util.json.JsonUtils;

@Path("basedata")
public class OrgUserController {
	
	@Resource
	private Invocation inv;

	@Resource
	private OrgService orgService;

	@Resource
	private OrgUserService orgUserService;
	
	@Resource
	private UserService userService;
	

	String viewUrl = "/views/basedata/orguser/"; 
	
	//数据维护中的应急通讯录
	@Get("orguser/contacts")
	public String orguser_listPage(){
		return "/views/contacts/emergencyContact-list";
	}
	
	
	@Get("orgUser/listPage")
	public String orgUser_listPage(@Param("orgCode")String orgCode){
		
		if(StringUtils.isEmpty(orgCode)){
			orgCode = SysUserUtils.getCurrentUser(inv).getOrgCode();
		}
		Org org = orgService.findByOrgCode(orgCode);
		inv.addModel("orgCode", orgCode);
		inv.addModel("chargeOrg", org.getChargeOrg());
		return viewUrl+"orguser-list";
	}
	
	@Get("orgUser/addPage/{orgCode:[0-9]+}")
	public String orgUser_addPage(@Param("orgCode")String orgCode){
		Org org = orgService.findByOrgCode(orgCode);
		inv.addModel("orgCode", orgCode);
		inv.addModel("orgName", org.getOrgName());
		return viewUrl+"orguser-add";
	}
	
	@Get("orgUser/editPage/{id:[0-9]+}")
	public String orgUser_editPage(@Param("id")String id){
		inv.addModel("id", id);
		return viewUrl+"orguser-edit";
	}
	
	@Get("orgUser/viewPage")
	public String orgUser_viewPage(@Param("id")String id){
		inv.addModel("id", id);
		return viewUrl+"orguser-view";
	}
	
	
	/**
	 * 添加通讯录用户
	 * @param orgUserJson
	 * @return
	 */
	@Post("orguser/save")
	public String orguser_save(@Param("param")String orgUserJson){
		OrgUser orgUser = JsonUtils.getObject(orgUserJson, OrgUser.class);
		//组织机构编码不能为空
		if(StringUtils.isEmpty(orgUser.getOrgCode())){
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM,"请选择组织机构", inv);
		}
		//设置组织机构
		orgUser.setOrg(new Org(orgUser.getOrgCode()));
		if(StringUtils.isEmpty(orgUser.getUsername())) {
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM,"用户名不能为空", inv);
		}
		
		//保存通讯录用户
		orgUser = orgUserService.save(orgUser);
		
		if(orgUser.getImageUrl()!=null&&!orgUser.getImageUrl().startsWith(BasedataConstant.CONTEXT_PATH)) {
			orgUser.setImageUrl(BasedataConstant.CONTEXT_PATH+orgUser.getImageUrl());
		}
		return AjaxUtils.printSuccessJson(orgUser,"用户修改成功", inv);
	}
	
	/**
	 * 用户删除（真删除）
	 * @param idsStr
	 * @return
	 */
	@Post("orguser/realdelete")
	public String orguser_realdelete(@Param("id")String id){
		User user = userService.findById(id);
		if(user != null){
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM,"该人员为系统用户，不能删除", inv);
		}
		//删除通讯录用户
		orgUserService.realdelete(id);
		return AjaxUtils.printSuccessJson(null,"用户删除成功", inv);
	}
	
	/**
	 * 用户删除（逻辑删除）
	 * @param idsStr
	 * @return
	 */
	@Post("orguser/delete")
	public String orguser_delete(@Param("ids")String idsStr){
		String[] ids = idsStr.split(BasedataConstant.PARAM_SPLIT);
		//删除通讯录用户
		orgUserService.delete(ids);
		//删除系统用户
		userService.deleteByIds(ids);
		return AjaxUtils.printSuccessJson(null,"用户删除成功", inv);
	}
	
	/**
	 * 修改通讯录用户
	 * @param orgUser
	 * @return
	 */
	@Post("orguser/update")
	public String orguser_update(@Param("param")String orgUserJson){
		OrgUser orgUserTemp = JsonUtils.getObject(orgUserJson, OrgUser.class);
		if(orgUserTemp.getId()==null) {
			AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM,"用户ID不能为空", inv);
		}
		OrgUser orgUser = orgUserService.findById(orgUserTemp.getId());
//		//设置名称
		if(StringUtils.isNotEmpty(orgUserTemp.getUsername())) {
			orgUser.setUsername(orgUserTemp.getUsername());
////			orgUser.setFirstSpelling(PinyinUtils.getShortSpellingFirst(orgUserTemp.getUsername()));
//			orgUser.setFullSpelling(PinyinUtils.getFullSpelling(orgUserTemp.getUsername()));
//			orgUser.setShortSpelling(PinyinUtils.getShortSpelling(orgUserTemp.getUsername()));
		}
		//设置性别
		if(StringUtils.isNotEmpty(orgUserTemp.getSex())) {
			orgUser.setSex(orgUserTemp.getSex());
		}

		orgUser.setPosition(orgUserTemp.getPosition());
		orgUser.setMobileTel(orgUserTemp.getMobileTel());
		orgUser.setOfficeTel(orgUserTemp.getOfficeTel());
		orgUser.setNotes(orgUserTemp.getNotes());
		orgUser.setContactInfo(orgUserTemp.getContactInfo());
		//修改通讯录用户
		orgUserService.save(orgUser);
		return AjaxUtils.printSuccessJson(null,"用户修改成功", inv);
	}
	
	/**
	 * 查看用户
	 * @param id
	 * @return
	 */
	@Post("orguser/load")
	public String orguser_load(@Param("id")String id){
		OrgUser orgUser = orgUserService.findById(id);
		if(orgUser.getImageUrl()!=null&&!orgUser.getImageUrl().startsWith(BasedataConstant.CONTEXT_PATH)) {
			orgUser.setImageUrl(BasedataConstant.CONTEXT_PATH+orgUser.getImageUrl());
		}
		return AjaxUtils.printSuccessJson(orgUser, inv);
	}
	
	/**
	 * 查看通讯录用户
	 * @param orgCode
	 * @return
	 */
	@Post("orguser/findScroll")
	public String orguser_findScroll(@Param("param")String orgUserJson,@Param("scrollPage")String scrollPageJson){
		//ScrollPage和OrgUser的json字符串转换成对象
		ScrollPage scrollPage = JsonUtils.getObject(scrollPageJson, ScrollPage.class);
		OrgUser orgUserParam = JsonUtils.getObject(orgUserJson, OrgUser.class);
		//滚动查询
		List<OrgUser> orgUsers = orgUserService.find(scrollPage,orgUserParam);
		for(OrgUser orgUser:orgUsers) {
			if(orgUser.getImageUrl()!=null&&!orgUser.getImageUrl().startsWith(BasedataConstant.CONTEXT_PATH)) {
				orgUser.setImageUrl(BasedataConstant.CONTEXT_PATH+orgUser.getImageUrl());
			}
		}
		return AjaxUtils.printSuccessJson(orgUsers, inv);
	}
	
	/**
	 * 查看通讯录用户
	 * @param orgCode
	 * @return
	 */
	@Post("orguser/find")
	public String orguser_find(@Param("param")String orgUserJson,@Param("page") String pageJson){
		
		OrgUser orgUserParam = JsonUtils.getObject(orgUserJson, OrgUser.class);
		ParamPage page = JsonUtils.getObject(pageJson, ParamPage.class);
		
		//如果 是点击进行查询的，那么就去掉 机构的限制
		if(orgUserParam.getClickSearch().equals("1")){
			orgUserParam.setOrgCode("");
		}
		Page<OrgUser> pageList = orgUserService.find(orgUserParam, page.getPageable());
		
		List<OrgUser> list = pageList.getContent();
		List<OrgUser> dtoList = new ArrayList<OrgUser>();
		for(OrgUser orgUser : list) {
			if(orgUser.getImageUrl()!=null&&!orgUser.getImageUrl().startsWith(BasedataConstant.CONTEXT_PATH)) {
				orgUser.setImageUrl(BasedataConstant.CONTEXT_PATH+orgUser.getImageUrl());
			}
			dtoList.add(orgUser);
		}
		page.setTotal(pageList.getTotalElements());
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("page",page);
		resultMap.put("result", dtoList);
		
		return AjaxUtils.printSuccessJson(resultMap,"查询成功", inv);
	}
	/**
	 * 查询通讯录用户
	 * 手机平台 信息发送
	 * @param orgCode
	 * @return
	 */
	@Post("orguser/phone/find")
	public String orguser_phone_find(@Param("param")String orgUserJson,@Param("page") String pageJson){
		
		OrgUser orgUserParam = JsonUtils.getObject(orgUserJson, OrgUser.class);
		ParamPage page = new ParamPage();
		
		Page<OrgUser> pageList = orgUserService.find(orgUserParam, page.getPageable());
		
		List<OrgUser> list = pageList.getContent();
		List<Map<String,String>> dtoList = new ArrayList<Map<String,String>>();
		for(OrgUser orgUser : list) {
			Map<String,String> rtnMap = new HashMap<String, String>();
			rtnMap.put("userId",orgUser.getId());
			rtnMap.put("name",orgUser.getUsername());
			rtnMap.put("position",orgUser.getPosition());
			rtnMap.put("orgName",orgUser.getOrg().getOrgShortName());
			rtnMap.put("phone",orgUser.getPhoneNum());
			String info = "{'name':'"+orgUser.getUsername()+"','position':'"+orgUser.getPosition()
					+"','orgName':'"+orgUser.getOrg().getOrgShortName()+"'}";
			rtnMap.put("info",info);
			dtoList.add(rtnMap);
		}
		return AjaxUtils.printSuccessJson(dtoList,"查询成功", inv);
	}
	
	/**
	 * 查看通讯录用户
	 * @param orgCode
	 * @return
	 */
	@Get("orguser/exportOrguser")
	public String orguser_exportOrguser(@Param("param")String orgUserJson,@Param("page") String pageJson){
		
		OrgUser orgUserParam = JsonUtils.getObject(orgUserJson, OrgUser.class);
		ParamPage page = JsonUtils.getObject(pageJson, ParamPage.class);
		//如果 是点击进行查询的，那么就去掉 机构的限制
		if(orgUserParam.getClickSearch().equals("1")){
			orgUserParam.setOrgCode("");
		}
		page.setCount(10000);
		Page<OrgUser> pageList = orgUserService.find(orgUserParam, page.getPageable());
		List<OrgUser> list = pageList.getContent();
		List<OrgUser> dtoList = new ArrayList<OrgUser>();
		for(OrgUser orgUser : list) {
			if(orgUser.getImageUrl()!=null&&!orgUser.getImageUrl().startsWith(BasedataConstant.CONTEXT_PATH)) {
				orgUser.setImageUrl(BasedataConstant.CONTEXT_PATH+orgUser.getImageUrl());
			}
			dtoList.add(orgUser);
		}
		PrintWriter out = null;
		HttpServletResponse response=inv.getResponse();
		response.setCharacterEncoding("UTF-8");
		try {
			String exprotName="应急通讯录列表";
			String title[]=new String[]{"序号","姓名","部门","职务","办公电话","移动电话"};
			String width[]=new String []{"5","25","25","25","25","25"};
			out=response.getWriter();
			List<Object[]> listObj=new ArrayList<Object[]>();
			if (dtoList!=null && dtoList.size()>0) {
				for (OrgUser data : dtoList) {
					Object[] obj =new Object[title.length-1];
					obj[0] =data.getUsername();
					obj[1] =data.getOrg().getOrgName();
					obj[2] =data.getPosition();
					obj[3] =data.getOfficeTel();
					obj[4] =data.getMobileTel();
					listObj.add(obj);
				}
			}
			ExportExcelCommon.setExportExcel(response, exprotName, true);
			ExportExcelCommon.createExcel(response.getOutputStream(),listObj, title, exprotName,width);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			out.flush();
			out.close();
		}
		
		return null;
	}
	
	

	
	/**
	 * 将用户添加进用户组
	 * @param userId
	 * @return
	 */
	@Post("orguser/saveGroupToUser")
	public String saveGroupToUser(@Param("userId") String userId,
			@Param("userGroupId") String userGroupId) {
		if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(userGroupId)){
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM,"用户ID或组ID不能为空", inv);
		}
		orgUserService.addToGroup(userGroupId, userId.split(","));
		return AjaxUtils.printSuccessJson("", "保存成功", inv);
	}
	
	/**
	 * 将用户移除出用户组
	 * @param userId
	 * @return
	 */
	@Post("orguser/removeUser")
	public String removeUser(@Param("userId") String userId,
			@Param("userGroupId") String userGroupId) {
		
		OrgUser user = orgUserService.findById(userId);
		String groupId=user.getComGroupIds();
		String groupIdArray[];
		String groupResult[];
		String groupIdStr="";
		if (groupId!=null && !groupId.equals("")) {
			groupIdArray=groupId.split(",");
			groupResult=new String[groupIdArray.length];
			for (int i = 0; i < groupIdArray.length; i++) {
				if (!userGroupId.equals(groupIdArray[i])) {
					groupResult[i]=groupIdArray[i];
				}
			}
			for (int i = 0; i < groupResult.length; i++) {
				if (groupResult[i]!=null && !groupResult[i].equals("")) {
					groupIdStr=groupIdStr+groupResult[i]+",";
				}
			}
			if (!groupIdStr.equals("")) {
				groupIdStr= groupIdStr.substring(0, groupIdStr.length() -1);
			}
		}
		userService.updateUserToGroup(groupIdStr, userId);
		return AjaxUtils.printSuccessJson("", "移除成功", inv);
	}
	
	/**
	 * 根据组id查询组下面的用户
	 * @param userId
	 * @return
	 */
	@Post("orguser/findUserGroupById")
	public String findUserGroupById(@Param("groupId") String groupId) {
		//根据秘书组id查询秘书组下的用户
		List<OrgUser> orgUser = orgUserService.findByComGroupIdsLike(groupId);
		return AjaxUtils.printSuccessJson(orgUser, inv);
	}
	
	/**
	 * 根据用户id查询用户所在的组
	 * @param userId
	 * @return
	 */
	@Post("orguser/findGroupByUserId")
	public String findGroupByUserId(@Param("userId") String userId){
		OrgUser user = orgUserService.findById(userId);
		String groupId = user.getComGroupIds();
		String[] groupIdArray = null;
		if (StringUtils.isNotEmpty(groupId)) {
			 groupIdArray = groupId.split(",");
		}
		
		return AjaxUtils.printSuccessJson(groupIdArray, inv); 
	}
	
	@Get("orgUser/statPage")
	public String orgUser_statPage(){
		return viewUrl+"orguser-stat";
	}
	
	/**
	 * 查询应急通讯录汇总
	 * @param
	 * @return
	 */
	@Post("orguser/findOrgUserStat")
	public String findOrgUserStat(){
		List<Org> orgs=orgService.findByParentCode("9001001");
		Map<String, List<OrgUserStat>> map=new HashMap<String,List<OrgUserStat>>();
		List<OrgUserStat> orgStatList=new ArrayList<OrgUserStat>();
		if (orgs!=null) {
			for (Org org :orgs) {
				int total=0;
				OrgUserStat orgStat=new OrgUserStat();
				orgStat.setOrgName(org.getOrgName());
				List<Org> orgChild=orgService.findLikeParentCode(org.getOrgCode());
				List<OrgUserStatDTO> orgDtoList=new ArrayList<OrgUserStatDTO>();
				if (orgChild!=null && orgChild.size()>0) {
					for (Org orgChil:orgChild) {
						OrgUserStatDTO orgDto=new OrgUserStatDTO();
						List<OrgUser> orgUsers=orgUserService.findOrgUserByOrgCode(orgChil.getOrgCode());
						orgDto.setOrgName(orgChil.getOrgName());
						orgDto.setOrgCount(orgUsers==null?0:orgUsers.size());
						orgDtoList.add(orgDto);
					}
				}else{
					List<OrgUser> orgUsers=orgUserService.findOrgUserByOrgCode(org.getOrgCode());
					OrgUserStatDTO orgDto=new OrgUserStatDTO();
					orgDto.setOrgName("无");
					orgDto.setOrgCount(orgUsers==null?0:orgUsers.size());
					orgDtoList.add(orgDto);
				}
				
				
				if (orgDtoList!=null && orgDtoList.size()>0) {
					for (int i = 0; i < orgDtoList.size(); i++) {
						total=total+orgDtoList.get(i).getOrgCount();
					}
				}
				orgStat.setOrgStat(orgDtoList);
				orgStat.setOrgTotal(total);
				orgStat.setMergeRow(orgDtoList.size());
				orgStatList.add(orgStat);
				map.put("results", orgStatList);
			}
			System.out.println(AjaxUtils.printSuccessJson(map, inv));
		}
		return AjaxUtils.printSuccessJson(map, inv); 
	}
}
