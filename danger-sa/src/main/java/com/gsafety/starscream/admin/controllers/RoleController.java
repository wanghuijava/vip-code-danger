package com.gsafety.starscream.admin.controllers;

import java.util.ArrayList;
import java.util.List;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.google.gson.JsonArray;
import com.gsafety.common.utils.AjaxUtils;
import com.gsafety.starscream.basedata.model.Authority;
import com.gsafety.starscream.basedata.model.Role;
import com.gsafety.starscream.basedata.model.User;
import com.gsafety.starscream.basedata.service.OrgService;
import com.gsafety.starscream.basedata.service.RoleService;
import com.gsafety.starscream.basedata.service.UserService;
import com.gsafety.starscream.utils.R;
import com.gsafety.starscream.utils.page.PageModel;
import com.gsafety.util.json.JsonUtils;

/**
 * @ClassName:RoleController
 * @Description:系统角色管理Controller
 * @Author: duzhigang
 * @Version:1.0
 * @Date:2015/01/14
 */
@Path("admin")
public class RoleController {

	@Autowired
	Invocation inv;
	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	PageModel pageModel;

	@Get("role/list")
	public String roleList() {
		pageModel = new PageModel();
		int count = roleService.findCount();
		pageModel.setTotalRecords(count);
		Sort sort = new Sort("id");
		Pageable pageableObj = new PageRequest(pageModel.getcurrentPage(), pageModel.getPageSize(), sort);
		Role role = null;
		Page<Role> roles = roleService.find(role, pageableObj);
		List<Role> roleList = roles.getContent();

		// 分页查询角色
		inv.addModel("roleList", roleList);

		return "authority/role-list";
	}

	@Get("role/pagelist")
	public String pagelist(@Param("operator") String operator) {

		if (operator != null && !operator.equals("")) {
			if (operator.equals("start")) {
				// 首页
				pageModel.setTopcurrentPage();
			} else if (operator.equals("up")) {
				// 上一页
				pageModel.setPreviouscurrentPage();
			} else if (operator.equals("down")) {
				// 下一页
				pageModel.setNextcurrentPage();
			} else if (operator.equals("end")) {
				// 尾页
				pageModel.setBottomcurrentPage();
			}
		}

		Sort sort = new Sort("id");
		int currentPage = pageModel.getcurrentPage();
		int pageSize = pageModel.getPageSize();
		Pageable pageableObj = new PageRequest(currentPage, pageSize, sort);
		Role role = null;
		Page<Role> roles = roleService.find(role, pageableObj);

		List<Role> roleList = roles.getContent();
		// 分页查询角色
		inv.addModel("roleList", roleList);

		return "authority/role-list";
	}

	@Get("role/roleAddPage")
	public String roleAddPage() {
		// 跳转到系统角色添加界面
		return "authority/role-add";
	}

	/**
	 * 加载角色树
	 * 
	 * @param
	 * @return
	 */
	@Get("role/findRoleTree")
	public String findRoleTree() {
		List<Role> roleList = roleService.findAll();
		JsonArray jsonArr = JsonUtils.getJsonArray(roleList, new String[] {});
		return AjaxUtils.printSuccessJson(jsonArr.toString(), inv);
	}

	/**
	 * 添加角色
	 * 
	 * @param rolename
	 *            ,typeCode,comments
	 * @return
	 */
	@Post("role/roleAdd")
	public String roleAdd(@Param("rolename") String rolename, @Param("typeCode") Integer typeCode,
			@Param("comments") String comments) {
		Role role = new Role();
		role.setName(rolename);
		role.setTypeCode(typeCode);
		role.setComments(comments);
		// 新增系统角色
		roleService.save(role);
		return R.redirect("/admin/role/list", inv);
	}

	/**
	 * 根据角色id查询角色对象
	 * 
	 * @param roleId
	 * @return
	 */
	@Get("role/roleEditPage/{id:[0-9]+}")
	public String roleEditPage(@Param("id") String roleId) {
		// 查询角色对象，跳转到角色编辑界面
		Role role = roleService.findById(roleId);
		inv.addModel("role", role);
		return "authority/role-edit";
	}

	/**
	 * 删除角色
	 * 
	 * @param roleId
	 * @return
	 */
	@Get("role/roleDelete/{roleId:[0-9]+}")
	public String roleDelete(@Param("roleId") String roleId) {
		roleService.delete(roleId);
		return AjaxUtils.printSuccessJson("[{success:true}]", inv);
	}

	/**
	 * 根据角色id查询角色对象
	 * 
	 * @param roleId
	 * @return
	 */
	@Get("role/roleAuthorityByRoleId")
	public String roleAuthorityByRoleId(@Param("roleId") String roleId) {
		Role role = roleService.findById(roleId);
		List<Authority> authoritys = role.getAuthoritys();
		JsonArray jsonArr = JsonUtils.getJsonArray(authoritys, new String[] {});
		return AjaxUtils.printSuccessJson(jsonArr.toString(), inv);
	}

	/**
	 * 编辑角色
	 * 
	 * @param roleObj
	 * @return
	 */
	@Post("role/roleEdit")
	public String roleEdit(@Param("roleId") String roleId, @Param("rolename") String rolename,
			@Param("typeCode") Integer typeCode, @Param("comments") String comments) {
		Role role = roleService.findById(roleId);
		role.setName(rolename);
		role.setTypeCode(typeCode);
		role.setComments(comments);
		// 更新系统角色
		roleService.save(role);
		return R.redirect("/admin/role/list", inv);
	}

	/**
	 * 给角色添加权限
	 * 
	 * @param rolename
	 *            ,typeCode,comments
	 * @return
	 */
	@Post("role/roleAddAuthority")
	public String roleAddAuthority(@Param("roleId") String roleId, @Param("authorityIds") String authorityIds) {
		Role role = roleService.findById(roleId);
		List<Authority> authorityList = new ArrayList<Authority>();
		if (authorityIds != null && !authorityIds.equals("")) {
			String[] strAuthorityId = authorityIds.split(",");
			for (int i = 0; i < strAuthorityId.length; i++) {
				Authority authority = new Authority();
				authority.setId(strAuthorityId[i]);
				authorityList.add(authority);
			}
			role.setAuthoritys(authorityList);
			roleService.update(role);
		}
		
		//TODO..
		List<User> users = userService.findUserByRoleId(roleId);
		for (User user : users) {
			user.setAuthority(getAuthority(user.getRoles()));
			userService.save(user);
		}
		
		return AjaxUtils.printSuccessJson("[{success:true}]", inv);
	}
	/**
	 * 根据角色id查询权限
	 * @param  roleIds
	 * @return authorityIds
	 */
	private String getAuthority(String roleIds){
		JsonArray jsonArr =null;
		String authorityId="";
		if (StringUtils.isNotEmpty(roleIds)) {
			List<List<Authority>> authorityList=new ArrayList<List<Authority>>();
			String roleIdsArray []=roleIds.split(",");
			for (int i = 0; i < roleIdsArray.length; i++) {
				List<Authority> authorityListTemp=new ArrayList<Authority>();
				Role role=roleService.findById(roleIdsArray[i]);
				authorityListTemp=role.getAuthoritys();
				if (authorityListTemp!=null && authorityListTemp.size()>0) {
					authorityList.add(authorityListTemp);
				}
			}
			jsonArr=JsonUtils.getJsonArray(authorityList, new String[]{});
			authorityId=jsonArr.toString();
		}else{
			authorityId="null";
		}
		return authorityId;
	}
}
