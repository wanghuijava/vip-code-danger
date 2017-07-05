package com.gsafety.starscream.admin.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
import com.gsafety.starscream.basedata.model.OrgUser;
import com.gsafety.starscream.basedata.model.Role;
import com.gsafety.starscream.basedata.model.User;
import com.gsafety.starscream.basedata.service.OrgService;
import com.gsafety.starscream.basedata.service.OrgUserService;
import com.gsafety.starscream.basedata.service.RoleService;
import com.gsafety.starscream.basedata.service.UserService;
import com.gsafety.util.json.JsonUtils;

/**
 * @ClassName:OrgUserController
 * @Description:组织机构下通讯录用户Controller
 * @Author: duzhigang
 * @Version:1.0
 * @Date:2015/01/19
 */
@Path("admin/orguser")
public class OrgUserController {
	@Autowired
	private Invocation inv;
	@Autowired
	private OrgService orgService;      //组织机构service注入
	@Autowired
	private OrgUserService orguserService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	
	/**
	 * 跳转到组织机构用户页面
	 * @return
	 */
	@Get("list")
	public String orgList() {
		return "authority/org-user-list";
	}
	
	/**
	 * 跳转原页面并展示原页面数据
	 * @return
	 */
	@Get("backlist")
	public String backlist(@Param("orgCode")String orgCode) {
		inv.addModel("orgCode", orgCode);
		return "authority/org-user-list";
	}
	
	/**
	 * 加载组织机构树
	 * @param 
	 * @return
	 */
	@Get("findOrgTree")
	public String findOrgTree() {
		List<Org> orgList = orgService.findTree(null);
		JsonArray jsonArr = JsonUtils.getJsonArray(orgList, new String[]{});
		return AjaxUtils.printSuccessJson(jsonArr.toString().replace("orgCode", "id").replace("orgName", "name"), inv);
	}
	
	
	/**
	 * 编辑页面加载树形组织机构
	 * @return
	 */
	@Post("findtree")
	public String findTree() {
		List<Org> orgList = orgService.findTree(null);
		return AjaxUtils.printSuccessJson(orgList, inv);
	}
	
	/**
	 * 查询通讯录用户
	 * @param  orgCode
	 * @return
	 */
	@Get("userList")
	public String userList(@Param("orgCode") String orgCode) {
		//根据机构code查询用户
		List<OrgUser> userList = orguserService.findOrgUserByOrgCode(orgCode);
		JsonArray jsonArr = JsonUtils.getJsonArray(userList, new String[]{});
		return AjaxUtils.printSuccessJson(jsonArr.toString(), inv);
	}
	
	
	
	/**
	 * 新增通讯录用戶
	 * @param  OrgUser
	 * @return
	 */
	@Post("userAdd")
	public String userAdd(@Param("username")String username,
			@Param("orgUserSex")String sex,
			@Param("mobileTel")String mobileTel,
			@Param("officeTel")String officeTel,
			@Param("position")String position,
			@Param("orgCode")String orgCode) {
		OrgUser orgUser = new OrgUser();
		orgUser.setUsername(username);
		orgUser.setSex(sex);
		orgUser.setPosition(position);
		orgUser.setMobileTel(mobileTel);
		orgUser.setOfficeTel(officeTel);
		orgUser.setStatus(1);
		orgUser.setOrg(orgService.findByOrgCode(orgCode));
		orguserService.save(orgUser);
		return AjaxUtils.printSuccessJson("[{success:true}]", inv);
	}
	
	
	@Get("orgEditPage/{userId:[0-9]+}")
	public String orgEditPage(@Param("userId") String userId) {
		// 跳转到系统用户编辑界面
		OrgUser orgUser = orguserService.findById(userId);
		inv.addModel("orgUser", orgUser);
		return "authority/org-user-edit";
	}
	
	/**
	 * 编辑通讯录用户
	 * @param  OrgUser
	 * @return
	 */
	@Post("orgEdit")
	public String orgEdit(@Param("userId") String userId,
			@Param("username") String username,
			@Param("orgUserSex") String sex,
			@Param("mobileTel") String mobileTel,
			@Param("officeTel") String officeTel,
			@Param("position") String position,
			@Param("status") Integer status,
			@Param("orgName") String orgName,
			@Param("orgCode") String orgCode) {
		OrgUser orgUser = orguserService.findById(userId);
		orgUser.setUsername(username);
		orgUser.setSex(sex);
		orgUser.setPosition(position);
		orgUser.setMobileTel(mobileTel);
		orgUser.setOfficeTel(officeTel);
		orgUser.setStatus(status);
		orgUser.setOrg(orgService.findByOrgCode(orgCode));
		orguserService.update(orgUser);
		return "authority/org-user-list";
	}

	
	/**
	 * 注销通讯录用户
	 * @return
	 */
	@Get("delete/{userId:[0-9]+}")
	public String orguser_delete(@Param("userId") String userId) {
		orguserService.delete(userId);
		return "authority/org-user-list";
	}
	
	/**
	 * 真实删除通讯录用户
	 * @return
	 */
	@Get("realDelete/{userId:[0-9]+}")
	public String realDelete(@Param("userId") String userId) {
		orguserService.realdelete(userId);
		return AjaxUtils.printSuccessJson("[{success:true}]", inv);
	}
	
	/**
	 * 将用户添加进用户组
	 * @param userId
	 * @return
	 */
	@Get("saveGroupToUser")
	public String saveGroupToUser(@Param("userId") String userId,
			@Param("userGroupId") String userGroupId) {
		userService.updateUserToGroup(userGroupId, userId);
		System.out.println(AjaxUtils.printSuccessJson("", "操作成功", inv));
		return AjaxUtils.printSuccessJson("", "操作成功", inv);
	}
	
	/**
	 * 将用户移除出用户组
	 * @param userId
	 * @return
	 */
	@Get("removeUser")
	public String removeUser(@Param("userId") String userId,
			@Param("userGroupId") String userGroupId) {
		
		OrgUser user=orguserService.findById(userId);
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
	@Post("findUserGroupById")
	public String findUserGroupById(@Param("groupId") String groupId) {
		//根据秘书组id查询秘书组下的用户
		List<OrgUser> orgUser=orguserService.findByComGroupIdsLike(groupId);
		JsonArray jsonArr = JsonUtils.getJsonArray(orgUser, new String[]{});
		return AjaxUtils.printSuccessJson(jsonArr.toString(), inv);
	}
	
	/**
	 * 根据用户id查询用户所在的组
	 * @param userId
	 * @return
	 */
	@Post("findGroupByUserId")
	public String findGroupByUserId(@Param("userId") String userId){
		OrgUser user=orguserService.findById(userId);
		String groupId=user.getComGroupIds();
		String groupIdArray[]=null;
		JsonArray jsonArr=null;
		if (groupId!=null && !groupId.equals("")) {
			 groupIdArray=groupId.split(",");
			 jsonArr = JsonUtils.getJsonArray(groupIdArray, new String[]{});
		}else{
			return AjaxUtils.printSuccessJson("", inv);
		}
		
		return AjaxUtils.printSuccessJson(jsonArr.toString(), inv); 
	}
	 
	/**
	 * 下级机构并且为叶子节点的机构
	 * 自动创建通讯录用户
	 * @return
	 */
	@Post("createOrgUser")
	public String createOrgUser() {
		//用户生成后不能再次生成
		List<OrgUser> orgUsers=orguserService.findByLikeUserShort("org");
		if (orgUsers.size()==0) {
			List<String> orgList = orgService.findOrgByOrgTypeCode();
			for (int i = 0; i < orgList.size(); i++) {
				Org org=orgService.findByOrgCode(orgList.get(i));
				OrgUser orgUser = new OrgUser();
				orgUser.setUsername(org.getOrgShortName());
				orgUser.setSex("0");
				orgUser.setPosition(org.getOrgShortName());
				orgUser.setMobileTel(org.getContactTel());
				orgUser.setOfficeTel(org.getContactTel());
				orgUser.setStatus(1);
				orgUser.setOrg(orgService.findByOrgCode(org.getOrgCode()));
				orguserService.createOrgUser(orgUser);
			}
		}
		return AjaxUtils.printSuccessJson("[{success:true}]", inv);
	}
	
	/**
	 * 自动创建登陆用户
	 * 下级机构并且为叶子节点的机构
	 * @return
	 */
	@Post("createSysUser")
	public String createSysUser() {
		List<OrgUser> orgUsers=orguserService.findByLikeUserShort("org");
		StringBuffer buffer=new StringBuffer();
		if (orgUsers!=null && orgUsers.size()>0) {
			for (int i = 0; i < orgUsers.size(); i++) {
				OrgUser orguser=orgUsers.get(i);
				String userName=orguser.getUsername();
				User sysUser=new User();
				sysUser.setId(orguser.getId());
				String loginName=PinyinUtils.getLoginName(userName);
				sysUser.setUsername(loginName);
				String password=autoNum();
				sysUser.setPassword(DigestUtils.md5Hex(password));
				//下级用户
				sysUser.setTypeCode(0);
				sysUser.setOrgCode(orguser.getOrg().getOrgCode());
				sysUser.setOrgName(userName);
				//用户状态正常
				sysUser.setStatus(1);
				//根据角色id得到用户权限(下级一级单位),现暂时默认赋予下级一级单位角色
				sysUser.setRoles("2015031815234757");
				sysUser.setRoleNames("下级一级单位_org");
				sysUser.setAuthority(getAuthority("2015031815234757"));
				// 更新系统用户
				userService.save(sysUser);
				buffer.append("机构:"+userName+"--账号--:"+loginName+"--密码--:"+password+";");
			}
			System.out.println(buffer.toString());
		}
		return AjaxUtils.printSuccessJson("[{success:true}]", inv);
	}
	
	public static String autoNum(){
		Random random = new Random();
		String result="";
		for(int i=0;i<6;i++){
			result+=random.nextInt(10);
		}
		return result;
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
