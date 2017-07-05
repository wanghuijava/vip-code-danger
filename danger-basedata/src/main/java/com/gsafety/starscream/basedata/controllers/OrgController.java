package com.gsafety.starscream.basedata.controllers;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.lang.StringUtils;

import com.google.gson.JsonArray;
import com.gsafety.common.utils.AjaxUtils;
import com.gsafety.common.utils.PinyinUtils;
import com.gsafety.starscream.basedata.model.Org;
import com.gsafety.starscream.basedata.model.User;
import com.gsafety.starscream.basedata.service.OrgService;
import com.gsafety.starscream.basedata.service.UserService;
import com.gsafety.starscream.constant.BasedataConstant;
import com.gsafety.starscream.constant.CodeConstant;
import com.gsafety.starscream.exception.SequenceNoValException;
import com.gsafety.starscream.memcached.MCache;
import com.gsafety.starscream.utils.SysUserUtils;
import com.gsafety.starscream.utils.page.ScrollPage;
import com.gsafety.starscream.utils.tree.TreeUtils;
import com.gsafety.util.json.JsonUtils;
/**
 * 组织机构controller
 * @author chenwenlong
 *
 */
@Path("basedata")
public class OrgController {

	@Resource
	private Invocation inv;
	
	@Resource
	private OrgService orgService;      //组织机构service注入
	
	@Resource
	private UserService userService;    //系统用户service注入

	String viewUrl = "/views/basedata/org/"; 
	
	@Get("org/addPage/{parentCode:[0-9]+}")
	public String org_addPage(@Param("parentCode")String parentCode){
		Org org = orgService.findByOrgCode(parentCode);
		inv.addModel("parentCode", parentCode);
		inv.addModel("parentName", org.getOrgName());
		return viewUrl+"org-add";
	}
	
	@Get("org/editPage/{id:[0-9]+}")
	public String org_editPage(@Param("id")String orgCode){
		Org org = orgService.findByOrgCode(orgCode);
		String parentName="无上级机构";
		if(null!=org.getParentCode()){
			Org parent = orgService.findByOrgCode(org.getParentCode());
			if (parent != null){
				parentName = parent.getOrgName();
			}
		}
		inv.addModel("id", orgCode);
		inv.addModel("parentName", parentName);
		inv.addModel("orgLevelName", TreeUtils.getNameById(BasedataConstant.ORGLEVEL, org.getOrgLevel()));
		inv.addModel("districtName", TreeUtils.getNameById(BasedataConstant.DISTRICT, org.getDistrictCode()));
		return viewUrl+"org-edit";
	}
	
	
	/**
	 * 添加组织机构
	 * @param orgJson
	 * @return
	 * @throws SequenceNoValException 
	 */
	@Post("org/save")
	public String org_save(@Param("param")String orgJson) throws SequenceNoValException {
		//Org的json字符串转换成对象
		Org org = JsonUtils.getObject(orgJson, Org.class);
		//获取当前用户，设置维护机构
		User user = SysUserUtils.getCurrentUser(inv);
		if(!user.isSuperior() || StringUtils.isEmpty(org.getChargeOrg())){
			org.setChargeOrg(user.getOrgCode());
		}
		
		//行政区划必填
		if(StringUtils.isEmpty(org.getDistrictCode())){
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM, "行政区划不能为空", inv);
		}
		//组织机构名称必填
		if(StringUtils.isEmpty(org.getOrgName())){
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM, "组织机构名称不能为空", inv);
		}
		//组织机构名称全称
		if(StringUtils.isEmpty(org.getFullSpelling())) {
			org.setFullSpelling(PinyinUtils.getFullSpelling(org.getOrgName()));
		}
		//组织机构名称简称
		if(StringUtils.isEmpty(org.getShortSpelling())) {
			org.setShortSpelling(PinyinUtils.getShortSpelling(org.getOrgName()));
		}
		org.setCreateTime(new Date());
		org.setUpdateTime(new Date());
		//数据保存
		org = orgService.save(org);
		if(org.getOrgIcon()!=null&&!org.getOrgIcon().startsWith(BasedataConstant.CONTEXT_PATH)) {
			org.setOrgIcon(BasedataConstant.CONTEXT_PATH+org.getOrgIcon());
		}
		
		deleteMCache();//删除缓存
		return AjaxUtils.printSuccessJson(org,"组织机构保存成功", inv);
	}
	
	/**
	 * 删除组织机构
	 * @param orgCode
	 * @return
	 */
	@Post("org/delete")
	public String org_delete(@Param("orgCode")String orgCode){
		//删除组织机构，级联删除通讯录用户
		boolean result = false;
		try {
			orgService.delete(orgCode);
		} catch (Exception e) {
			return AjaxUtils.printSuccessJson(result,"组织机构删除成功", inv);
		}
		result = true;
		//删除系统用户
		userService.deleteByOrgCode(orgCode);
		
		deleteMCache();//删除缓存
		return AjaxUtils.printSuccessJson(result,"组织机构删除成功", inv);
	}
	
	/**
	 * 加载组织机构
	 * @param orgCode
	 * @return
	 */
	@Post("org/load")
	public String org_load(@Param("orgCode")String orgCode) {
		Org org = orgService.findByOrgCode(orgCode);
		if(org==null) {
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM, "机构编码不对", inv);
		}
		if(StringUtils.isNotEmpty(org.getOrgIcon())&&!org.getOrgIcon().startsWith(BasedataConstant.CONTEXT_PATH)) {
			org.setOrgIcon(BasedataConstant.CONTEXT_PATH+org.getOrgIcon());
		}
		return AjaxUtils.printSuccessJson(org, inv);
	}
	
	/**
	 * 滚动查询组织机构
	 * @param orgJson
	 * @param scrollPageJson
	 * @return
	 */
	@Post("org/find")
	public String org_find(@Param("param")String orgJson,@Param("scrollPage")String scrollPageJson){
		//ScrollPage和Org的json字符串转换成对象
		ScrollPage scrollPage = JsonUtils.getObject(scrollPageJson, ScrollPage.class);
		Org orgParam = JsonUtils.getObject(orgJson, Org.class);
		//排除当前用户所在机构
		if("true".equals(orgParam.getIgnoreOrgCode())) {
			//获取当前用户，设置发送人，机构
			User user = SysUserUtils.getCurrentUser(inv);
			if(user==null){
				return AjaxUtils.printErrorJson(CodeConstant.ERROR_NOT_LOGIN,CodeConstant.ERROR_NOT_LOGIN_STR, inv);
			}
			orgParam.setIgnoreOrgCode(user.getOrgCode());
		}
		//滚动查询
		List<Org> orgs = orgService.find(scrollPage,orgParam);
		for(Org org:orgs) {
			if(org.getOrgIcon()!=null&&!org.getOrgIcon().startsWith(BasedataConstant.CONTEXT_PATH)) {
				org.setOrgIcon(BasedataConstant.CONTEXT_PATH+org.getOrgIcon());
			}
		}
		return AjaxUtils.printSuccessJson(orgs, inv);
	}
	
	/**
	 * 按父节点查询子结构，返回json
	 * @param parentCode
	 * @return
	 */
	@Post("org/findTreeByParent")
	public String org_findTreeByParent(@Param("parentCode")String parentCode) {
		//查询所有机构，以树形结构返回
		List<Org> orgs = orgService.findTree(parentCode);
		return AjaxUtils.printSuccessJson(orgs, inv);
	}
	
	/**
	 * 查询树形结构组织机构
	 * @return
	 */
	@Post("org/findTree")
	public String org_findTree() {
		//查询所有机构，以树形结构返回
		List<Org> orgs = orgService.findTree(null);
		for(Org org:orgs) {
			if(org.getOrgIcon()!=null&&!org.getOrgIcon().startsWith(BasedataConstant.CONTEXT_PATH)) {
				org.setOrgIcon(BasedataConstant.CONTEXT_PATH+org.getOrgIcon());
			}
		}
		List<Org> orgList = orgService.findTree(null);
		JsonArray jsonArr = JsonUtils.getJsonArray(orgList, new String[]{});
		return AjaxUtils.printSuccessJson(jsonArr.toString().replace("orgCode", "id").replace("orgName", "name"), inv);
	}
	
	private void deleteMCache(){
		MCache.delete(BasedataConstant.ORG+"_list");
		MCache.delete(BasedataConstant.ORG+"_tree");
		MCache.delete(BasedataConstant.ORG+"_map");
	}
}
