package com.gsafety.starscream.admin.controllers;

import java.util.List;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.google.gson.JsonArray;
import com.gsafety.common.utils.AjaxUtils;
import com.gsafety.starscream.basedata.model.Authority;
import com.gsafety.starscream.basedata.service.AuthorityService;
import com.gsafety.starscream.utils.R;
import com.gsafety.starscream.utils.page.PageModel;
import com.gsafety.util.json.JsonUtils;
/**
 * @ClassName:AuthorityController
 * @Description:系统权限管理Controller
 * @Author: duzhigang
 * @Version:1.0
 * @Date:2015/01/14
 */
@Path("admin")
public class AuthorityController {

	@Autowired
	Invocation inv;
	@Autowired
	private AuthorityService authorityService;
	
	PageModel pageModel;

	@Get("authority/list")
	public String authorityList() {
		pageModel=new PageModel();
		Authority authority=null;
		int count=authorityService.findCount(authority);
		pageModel.setTotalRecords(count);
		Sort sort = new Sort("url");
		
		Pageable pageableObj = new PageRequest(pageModel.getcurrentPage(), 1000, sort);
		// 查询所有权限
		Page<Authority> authoritys = authorityService.find(authority,pageableObj);
		List<Authority> authorityList =authoritys.getContent();
		inv.addModel("authorityList", authorityList);
		// 跳转到权限管理页面
		return "authority/authority-list";
	}

	@Get("authority/authorityAddPage")
	public String authorityAddPage() {
		// 跳转到权限添加界面
		return "authority/authority-add";
	}
	
	/**
	 * 添加权限
	 * @param authorityName,authorityUrl
	 * @return
	 */
	@Post("authority/authorityAdd")
	public String authorityAdd(@Param("authorityName") String authorityName,
			@Param("authorityUrl") String authorityUrl) {
		Authority authority = new Authority();
		authority.setName(authorityName);
		authority.setUrl(authorityUrl);
		authorityService.save(authority);
		return R.redirect("/admin/authority/list", inv);
	}
	
	
	@Get("authority/authorityEditPage/{id:[0-9]+}")
	public String authorityEditPage(@Param("id") String authorityId) {
		// 查询权限对象并跳转到权限编辑页面
		Authority authority = authorityService.findById(authorityId);
		inv.addModel("authority", authority);
		return "authority/authority-edit";
	}
	
	/**
	 * 编辑权限
	 * @param authorityName,authorityUrl
	 * @return
	 */
	@Post("authority/authorityEdit")
	public String authorityEdit(@Param("authorityId") String authorityId,
			@Param("authorityName") String authorityName,
			@Param("authorityUrl") String authorityUrl) {
		Authority authority =authorityService.findById(authorityId);
		authority.setName(authorityName);
		authority.setUrl(authorityUrl);
		authorityService.save(authority);
		return R.redirect("/admin/authority/list", inv);
	}
	
	/**
	 * 删除权限
	 * @param authorityId
	 * @return
	 */
	@Get("authority/delete/{authorityId:[0-9]+}")
	public String authorityDelete(@Param("authorityId") String authorityId) {
		authorityService.delete(authorityId);
		return AjaxUtils.printSuccessJson("[{success:true}]", inv);
	}
	
	/**
	 * 加载权限树
	 * @param  
	 * @return
	 */
	@Get("authority/findAuthorityTree/{roleId:[0-9]+}")
	public String findAuthorityTree(@Param("roleId")int roleId) {
		PageModel page = new PageModel();
		Authority authority=null;
		int count=authorityService.findCount(authority);
		page.setTotalRecords(count);
		Sort sort = new Sort("name");
		Pageable pageableObj = new PageRequest(page.getcurrentPage(), 1000, sort);
		// 查询所有权限
		Page<Authority> authoritys = authorityService.find(authority,pageableObj);
		List<Authority> authorityList =authoritys.getContent();
		JsonArray jsonArr = JsonUtils.getJsonArray(authorityList, new String[]{});
		return AjaxUtils.printSuccessJson(jsonArr.toString(), inv);
	}

}
