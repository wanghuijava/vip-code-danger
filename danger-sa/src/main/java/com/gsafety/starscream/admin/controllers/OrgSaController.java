package com.gsafety.starscream.admin.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.gsafety.common.utils.AjaxUtils;
import com.gsafety.starscream.basedata.model.District;
import com.gsafety.starscream.basedata.model.Org;
import com.gsafety.starscream.basedata.model.OrgUser;
import com.gsafety.starscream.basedata.model.Role;
import com.gsafety.starscream.basedata.service.DistrictService;
import com.gsafety.starscream.basedata.service.OrgService;
import com.gsafety.starscream.basedata.service.OrgUserService;
import com.gsafety.starscream.exception.SequenceNoValException;
import com.gsafety.starscream.utils.R;

/**
 * 组织机构controller
 * @author duxi
 *
 */
@Path("admin/basedata")
public class OrgSaController {

	@Autowired
	private Invocation inv;
	
	@Autowired
	private OrgService orgService;      //组织机构service注入
	
	@Autowired
	private DistrictService districtService;
	@Autowired
	private OrgUserService orguserService;
	
	
	/**
	 * 查询树形结构组织机构
	 * @return
	 */
	@Get("org/findAll")
	public String org_findAll() {
		//查询所有机构，以树形结构返回
		List<District> districts = districtService.findTree(null);
		inv.addModel("districtList", districts);
		return "basedata/org-list";
	}

	/**
	 * 组织机构键字模糊匹配
	 * @param keyword
	 * @return
	 */
	@Get("org/loadSearchList")
	public String org_loadSearchList(@Param("maxRows")Long maxRows,@Param("keyword") String keyword){
		return AjaxUtils.printSuccessJson(orgService.getSearchList(maxRows, keyword), inv);
	}
	
	
	/**
	 * 查询树形结构组织机构
	 * @return
	 */
	@Post("org/findTree")
	public String org_findTree() {
		//查询所有机构，以树形结构返回
		List<Org> orgs = orgService.findTree(null);
		return AjaxUtils.printSuccessJson(orgs, inv);
	}
	
	
	/**
	 * 添加组织机构
	 * @param orgJson
	 * @return
	 * @throws SequenceNoValException 
	 */
	@Post("org/save")
	public String org_save(
			@Param("districtCode") String districtCode,
			@Param("orgName") String orgName,
			@Param("orgShortName") String orgShortName,
			@Param("typeCode") Integer typeCode,
			@Param("parentCode") String parentCode,
			@Param("orgIcon") MultipartFile multipartFile,
			@Param("principal") String principal,
			@Param("contactTel") String contactTel, 
			@Param("fax") String fax,
			@Param("address") String address, 
			@Param("orgInfo") String orgInfo,
			@Param("contactInfo") String contactInfo,
			@Param("roleIds") String roleIds,
			@Param("chargeOrg") String chargeOrg)
			throws SequenceNoValException {
		// Org的json字符串转换成对象
		Org org = new Org();
		org.setDistrictCode(districtCode);
		org.setOrgName(orgName);
		org.setTypeCode(typeCode);
		if (null != parentCode) {
			org.setParentCode(parentCode);
		}
		org.setOrgShortName(orgShortName);
		org.setPrincipal(principal);
		org.setContactTel(contactTel);
		org.setFax(fax);
		org.setOrgInfo(orgInfo);
		org.setAddress(address);
		org.setContactInfo(contactInfo);//其他联系方式的json串
		org.setChargeOrg(chargeOrg);
		
		//机构中新增 角色
		Set<Role> roles = new HashSet<Role>();
		if(StringUtils.isNotEmpty(roleIds)){
			String[] roleIdArr = roleIds.split(",");
			for (String id : roleIdArr) {
				Role r = new Role();
				r.setId(id);
				roles.add(r);
			}
		}
		org.setRoles(roles);

		// 数据保存
		org = orgService.save(org);
		List<District> districts = districtService.findTree(null);
		inv.addModel("districtList", districts);
		return "basedata/org-list";
	}
	
	/**
	 * 删除组织机构
	 * 判断组织机构下是否有通讯录用户
	 * 若有用户则不能删除该机构
	 * @param orgCode
	 * @return
	 */
	@Get("org/delete/{orgCode:[0-9]+}")
	public String org_delete(@Param("orgCode")String orgCode){
		List<OrgUser> orgUsers=orguserService.findOrgUserByOrgCode(orgCode);
		Map<String, String> map=new HashMap<String, String>();
		if (orgUsers!=null && orgUsers.size()>0) {
			map.put("flag", "0");
		}else{
			map.put("flag", "1");
			orgService.delete(orgCode);
		}
		
		return AjaxUtils.printJson(map, inv);
	}
	
	/**
	 * 初始化选中组织机构
	 * @param orgCode
	 * @return
	 */
	@Get("org/edit/{orgCode:[0-9]+}")
	public String org_edit(@Param("orgCode") String orgCode){
		Org org = orgService.findByOrgCode(orgCode);
		String parentName="无上级机构";
		if(null!=org.getParentCode()){
			parentName = orgService.findByOrgCode(org.getParentCode()).getOrgName();
		}
		
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(org.getDistrictCode())){
			jsonmap.put("districtCode", org.getDistrictCode());
		}
		jsonmap.put("org", org);
		System.out.println(org.getRoleIdAndName()[0]);
		System.out.println(org.getRoleIdAndName()[1]);
		jsonmap.put("roleIdAndName", org.getRoleIdAndName());
		jsonmap.put("parentName", parentName);
		return AjaxUtils.printSuccessJson(jsonmap, inv);
	}

	/**
	 * 保存组织机构
	 * @param 
	 * @return
	 * @throws SequenceNoValException 
	 */
	@Post("org/update")
	public String org_update(
			@Param("parentDistrict") String parentDistrict,
			@Param("orgCode") String orgCode, 
			@Param("orgName") String orgName,
			@Param("orgShortName") String orgShortName,
			@Param("typeCode") String typeCode,
			@Param("orgIcon") MultipartFile multipartFile,
			@Param("principal") String principal,
			@Param("contactTel") String contactTel, 
			@Param("fax") String fax,
			@Param("address") String address, 
			@Param("orgInfo") String orgInfo,
			@Param("contactInfo") String contactInfo,
			@Param("roleIds") String roleIds,
			@Param("chargeOrg") String chargeOrg)
			throws SequenceNoValException {
		Org org = orgService.findByOrgCode(orgCode);
		org.setDistrictCode(parentDistrict);
		org.setOrgName(orgName);
		org.setOrgShortName(orgShortName);
		org.setTypeCode(Integer.parseInt(typeCode));
		org.setPrincipal(principal);
		org.setContactTel(contactTel);
		org.setFax(fax);
		org.setOrgInfo(orgInfo);
		org.setAddress(address);
		org.setContactInfo(contactInfo);//其他联系方式的json串
		org.setChargeOrg(chargeOrg);
		
		//机构中新增 角色
		Set<Role> roles = new HashSet<Role>();
		if(StringUtils.isNotEmpty(roleIds)){
			String[] roleIdArr = roleIds.split(",");
			for (String id : roleIdArr) {
				Role r = new Role();
				r.setId(id);
				roles.add(r);
			}
		}
		org.setRoles(roles);
		
		// 数据保存
		orgService.save(org);
		List<District> districts = districtService.findTree(null);
		inv.addModel("districtList", districts);
		return "basedata/org-list";
	}
	
	@Get("org/moveUp/{orgCode:[0-9]+}")
	public String org_moveUp(@Param("orgCode") String orgCode){
		orgService.moveUp(orgCode);
		return R.redirect("/admin/basedata/org/findAll", inv);
	}
	
	@Get("org/moveDown/{orgCode:[0-9]+}")
	public String org_moveDown(@Param("orgCode") String orgCode){
		orgService.moveDown(orgCode);
		return R.redirect("/admin/basedata/org/findAll", inv);
	}
	
}

