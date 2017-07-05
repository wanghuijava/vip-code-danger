package com.gsafety.starscream.admin.controllers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonArray;
import com.gsafety.common.utils.AjaxUtils;
import com.gsafety.common.utils.PinyinUtils;
import com.gsafety.starscream.basedata.model.Authority;
import com.gsafety.starscream.basedata.model.Org;
import com.gsafety.starscream.basedata.model.Role;
import com.gsafety.starscream.basedata.model.User;
import com.gsafety.starscream.basedata.service.OrgService;
import com.gsafety.starscream.basedata.service.RoleService;
import com.gsafety.starscream.basedata.service.UserService;
import com.gsafety.starscream.utils.R;
import com.gsafety.util.json.JsonUtils;
/**
 * @ClassName:UserController
 * @Description:系统用户管理Controller
 * @Author: duzhigang
 * @Version:1.0
 * @Date:2015/01/14
 */
@Path("admin/user")
public class UserController {

	@Autowired
	Invocation inv;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	@Autowired
	private OrgService orgService;      //组织机构service注入
	
	
	@Get("list")
	public String finduser() {
		// 查询所有用户
		List<User> userList = userService.findAll();
		inv.addModel("userList", userList);
		return "authority/user-list";
	}
	
	/**
	 * 查询系统用户
	 * @param  orgCode
	 * @return
	 */
	@Get("userList")
	public String userList(@Param("orgCode") String orgCode) {
		//根据机构code查询用户
		List<User> userList = userService.findByOrgCode(orgCode);
		inv.addModel("userList", userList);
		return "authority/user-list-table";
	}
	
	// 跳转到用户添加界面
	@Get("userAddPage")
	public String userAddPage() {
		return "authority/user-add";
	}

	@Post("userAdd")
	public String userAdd(@Param("username") String username,
			@Param("password") String password,
			@Param("typeCode") Integer typeCode,
			@Param("orgName") String orgName,
			@Param("orgCode") String orgCode,
			@Param("roleIds") String roleIds,
			@Param("userRole") String userRole) {
		User sysUser = new User();
		sysUser.setUsername(username);
		sysUser.setPassword(DigestUtils.md5Hex(password));
		sysUser.setTypeCode(typeCode);
		sysUser.setOrgCode(orgCode);
		sysUser.setOrgName(orgName);
		sysUser.setStatus(1);
		
		//如果用户没有角色，那么就直接继承所在机构的角色
		if(StringUtils.isEmpty(roleIds)){
			Org org = orgService.findByOrgCode(orgCode);
			String [] roleIdAndName = org.getRoleIdAndName();
			
			roleIds = roleIdAndName[0];
			userRole = roleIdAndName[1];
			System.out.println("继承所在机构的角色！");
		}
		
		//根据角色id得到用户权限
		sysUser.setRoles(roleIds);
		sysUser.setRoleNames(userRole);
		sysUser.setAuthority(getAuthority(roleIds));
		
		// 新增系统用户
		userService.save(sysUser);
		return AjaxUtils.printSuccessJson("[{success:true}]", inv);
	}
	
	@Post("modifyPassword")
	public String modifyPassword(@Param("username") String username,
			@Param("oldPassword") String oldPassword,
			@Param("password") String password) {
		password=DigestUtils.md5Hex(password);
		oldPassword=DigestUtils.md5Hex(oldPassword);
		User user=userService.findByUsername(username);
		Map<String, String> map=new HashMap<String, String>();
		if (user!=null && user.getPassword()!=null) {
			if (!oldPassword.equals(user.getPassword())) {
				map.put("flag", "0");
			}else{
				map.put("flag", "1");
				userService.modifyPassword(username, password);
			}
		}

		return AjaxUtils.printSuccessJson(map, inv);
	}
	
	/**
	 * 通讯录用户转登录用户
	 * @param  sysUser
	 * @return
	 */
	@Post("orgUserChangeSysUser")
	public String orgUserChangeSysUser(@Param("username") String username,
			@Param("userId") String userId,
			@Param("password") String password,
			@Param("typeCode") Integer typeCode,
			@Param("orgName") String orgName,
			@Param("orgCode") String orgCode,
			@Param("roleIds") String roleIds,
			@Param("userRole") String userRole) {
		User sysUser = new User();
		sysUser.setId(userId);
		sysUser.setUsername(username);
		sysUser.setPassword(DigestUtils.md5Hex(password));
		sysUser.setTypeCode(typeCode);
		sysUser.setOrgCode(orgCode);
		sysUser.setOrgName(orgName);
		sysUser.setStatus(1);
		
		//如果用户没有角色，那么就直接继承所在机构的角色
		if(StringUtils.isEmpty(roleIds)){
			Org org = orgService.findByOrgCode(orgCode);
			String [] roleIdAndName = org.getRoleIdAndName();
			
			roleIds = roleIdAndName[0];
			userRole = roleIdAndName[1];
			System.out.println("继承所在机构的角色！");
		}
		
		//根据角色得到用户权限
		sysUser.setRoles(roleIds);
		sysUser.setAuthority(getAuthority(roleIds));
		sysUser.setRoleNames(userRole);

			// 新增系统用户
		userService.save(sysUser);
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
	
	/**
	 * 新增系统用户
	 * 判断系统用户是否存在
	 * 若存在请重新输入账号
	 * @param  sysUser
	 * @return
	 */
	@Post("sysUserIfExist")
	public String sysUserIfExist(@Param("username") String username) {
		User sysUser = userService.findByUsername(username);
		Map<String, String> map=new HashMap<String, String>();
		if (sysUser!=null) {
			//系统用户中存在该账号，请重新输入账号
			map.put("flag", "0");
		}else{
			map.put("flag", "1");
		}
		
		return AjaxUtils.printJson(map, inv);
	}
	
	
	/**
	 * 通讯录用户转系统用户
	 * 判断系统用户是否存在
	 * 若存在请重新输入账号
	 * @param  sysUser
	 * @return
	 */
	@Post("validateUserIfExist")
	public String validateUserIfExist(@Param("username") String username,@Param("userId") String userId) {
		
		User sysUserTemp = userService.findById(userId);
		User sysUser = userService.findByUsername(username);
		Map<String, String> map=new HashMap<String, String>();
		if (sysUserTemp==null && sysUser==null) {
			//系统用户中不存在该用户可以转入
			map.put("flag", "0");
		}else{
			//该通讯录用户已转入到系统用户中，不能再次转入
			if (sysUserTemp!=null && sysUser==null) {
				map.put("flag", "1");
			//系统用户中存在该账号，请重新输入账号
			}else if (sysUser!=null && sysUserTemp==null) {
				map.put("flag", "2");
			}else{
				//该通讯录用户已转入到系统用户中,且账号已存在不能转入
				map.put("flag", "3");
			}
		}
		return AjaxUtils.printJson(map, inv);
	}
	
	
	/**
	 * 根据用户名生成账号
	 * @param  sysUser
	 * @return
	 */
	@Post("createAccount")
	public String createAccount(@Param("username") String username) {
		String loginName=PinyinUtils.getLoginName(username);
		Map<String, String> map = new HashMap<String, String>();
		map.put("loginName", loginName);
		return AjaxUtils.printJson(map, inv);
	}

	@Get("userEditPage/{userId:[0-9]+}")
	public String userEditPage(@Param("userId") String userId) {
		// 跳转到系统用户编辑界面
		User user = userService.findById(userId);
		inv.addModel("user", user);
		return "authority/user-edit";
	}

	@Post("userEdit")
	public String userEdit(@Param("userId") String userId,
			@Param("username") String username,
			@Param("password") String password,
			@Param("typeCode") Integer typeCode,
			@Param("orgName") String orgName,
			@Param("orgCode") String orgCode,
			@Param("roleIds") String roleIds,
			@Param("userRole") String userRole,
			@Param("status") Integer status) {
		User sysUser = userService.findById(userId);
		sysUser.setUsername(username);
		if(StringUtils.isNotEmpty(password)){
			sysUser.setPassword(DigestUtils.md5Hex(password));
		}
		sysUser.setTypeCode(typeCode);
		sysUser.setOrgCode(orgCode);
		sysUser.setOrgName(orgName);
		sysUser.setStatus(status);
		sysUser.setRoles(roleIds);
		sysUser.setRoleNames(userRole);
		//得到用户权限
		sysUser.setAuthority(getAuthority(roleIds));
		// 更新系统用户
		userService.update(sysUser);
		return R.redirect("/admin/user/list", inv);
	}

	/**
	 * 注销系统用户
	 * @param  userId
	 * @return
	 */
	@Get("delete/{userId:[0-9]+}")
	public String userDelete(@Param("userId") String userId) {
		User sysUser = userService.findById(userId);
		// 修改用户状态为注销状态
		sysUser.setStatus(0);
		userService.save(sysUser);
		return AjaxUtils.printSuccessJson("[{success:true}]", inv);
	}
}
