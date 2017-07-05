package com.gsafety.starscream.basedata.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gsafety.common.utils.AjaxUtils;
import com.gsafety.starscream.annotation.NotCareLogin;
import com.gsafety.starscream.basedata.model.Org;
import com.gsafety.starscream.basedata.model.OrgUser;
import com.gsafety.starscream.basedata.model.Token;
import com.gsafety.starscream.basedata.model.User;
import com.gsafety.starscream.basedata.service.OrgService;
import com.gsafety.starscream.basedata.service.OrgUserService;
import com.gsafety.starscream.basedata.service.SystemOperationLogsService;
import com.gsafety.starscream.basedata.service.TokenService;
import com.gsafety.starscream.basedata.service.UserService;
import com.gsafety.starscream.constant.BasedataConstant;
import com.gsafety.starscream.constant.CodeConstant;
import com.gsafety.starscream.memcached.MCache;
import com.gsafety.starscream.utils.BrowserUtils;
import com.gsafety.starscream.utils.StarscreamProperties;
import com.gsafety.starscream.utils.SysUserUtils;
import com.gsafety.starscream.utils.format.DateUtil;
import com.gsafety.util.json.JsonUtils;

@Path("basedata")
public class UserController {

	@Resource
	private Invocation inv;
	
	@Resource
	private UserService userService;
	
	@Resource
	private OrgUserService orgUserService;
	
	@Autowired
	private OrgService orgService;
	
	@Resource
	private TokenService tokenService;
	
	@Autowired
	private SystemOperationLogsService logsService;
	/**
	 * @author wanghui 2015-5-11
	 * 获取当前请求的用户信息
	 * @return
	 */
	@Post("user/getUser")
	public String user_getUser() {
		User user = SysUserUtils.getCurrentUser(inv);
		return AjaxUtils.printSuccessJson(user,"获取用户信息成功", inv);
	}
	
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	@NotCareLogin
	@Post("user/login")
	public String user_login(@Param("username")String username,@Param("password")String password) {
		if(StringUtils.isEmpty(BasedataConstant.CONTEXT_PATH)){
			BasedataConstant.CONTEXT_PATH = inv.getRequestPath().getCtxpath();
		}
		//判断用户名和密码是否为空
		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_NOT_LOGIN, "用户名密码不能为空", inv);
		}
		
		User user = userService.findByUsername(username);
		//判断用户是否存在
		if(user == null) {
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_NOT_LOGIN, "用户不存在", inv);
		}
		//判断密码是否正确
		if(StringUtils.isEmpty(password)||!user.getPassword().equals(DigestUtils.md5Hex(password))){
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_NOT_LOGIN, "用户密码输入不正确", inv);
		}
		//判断用户是否注销
		if(user.getStatus()==0) {
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_NOT_LOGIN, "该用户已注销", inv);
		}
		
		//查询通讯录用户
		OrgUser orgUser = orgUserService.findById(user.getId());
		if(orgUser != null){
			if(orgUser.getImageUrl()!=null&&!orgUser.getImageUrl().startsWith(BasedataConstant.CONTEXT_PATH)) {
				orgUser.setImageUrl(BasedataConstant.CONTEXT_PATH+orgUser.getImageUrl());
			}
			//用户所在机构
			Org org = orgUser.getOrg();
			if(org == null){
				org = orgService.findByOrgCode(user.getOrgCode());
			}else if(org != null &&org.getOrgIcon()!=null&&!org.getOrgIcon().startsWith(BasedataConstant.CONTEXT_PATH)) {
				org.setOrgIcon(BasedataConstant.CONTEXT_PATH+org.getOrgIcon());
			}
			user.setOrg(org);            //用户设置对应的机构
		}
		
		StarscreamProperties p = StarscreamProperties.getInstance();
		boolean userLoginOnePlaceTag = Boolean.getBoolean(p.getPropValue("USER_LOGIN_ONE_PLACE"));
		
		//将sessionID的MD5加密作为每次请求的token
		String sessionID = inv.getRequest().getSession().getId();
		String tokenCode;
		if(userLoginOnePlaceTag){//仅允许用户一个地方登录，其他地方登录会将先前用户踢出
			
			//使用sessionID + 用户登录名 + 3位随机校正码生成token码
			tokenCode = DigestUtils.md5Hex(sessionID + user.getUsername() + RandomStringUtils.randomNumeric(3));
			
		} else {//允许用户多个地方登录
			
			Token userToken = tokenService.findByLoginNameType(username, BrowserUtils.getLoginType(inv.getRequest()));
			if(userToken != null){
				tokenCode = userToken.getTokenCode();
			}else {
				//使用sessionID + 用户登录名 + 3位随机校正码生成token码
				tokenCode = DigestUtils.md5Hex(sessionID + user.getUsername() + RandomStringUtils.randomNumeric(3));
			}
		}
		
		user.setOrgUser(orgUser);
		
		MCache.set(tokenCode, user);
		
		Cookie cookie = new Cookie("userToken",tokenCode);
		cookie.setPath("/");
		inv.getResponse().addCookie(cookie);
//		
		//将token保存到数据库
		int loginType = BrowserUtils.getLoginType(inv.getRequest());//获取当前登录设备类型（电脑：0，手机：1）
		Token token = new Token(user.getUsername(), loginType, tokenCode, new Date(), DateUtil.addDaysToCurrentDate(30));
		tokenService.save(token);
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("user", user);
		result.put("token", tokenCode);
		
		Map<String,String> opeationMap = new HashMap<String, String>();
		opeationMap.put("operationType",BasedataConstant.SYSTEM_LOG);
		opeationMap.put("funName","登录");
		opeationMap.put("detail",JsonUtils.getJsonStr(user));
		opeationMap.put("operator",user.getUsername());
		logsService.addLogs(opeationMap, inv);
		
		return AjaxUtils.printSuccessJson(result,"用户登录成功", inv);
	}
	
	/**
	 * 用户注销
	 * @return
	 */
	@NotCareLogin
	@Get("user/logout")
	@Post("user/logout")
	public String user_logout(){
		
		//对于用户注销操作，仅需要移除用户客户端的token值的cookie，
		//不需要删除后台数据库中的token码，以免引起用户在多处登录场景中的登录问题。
		/**************** Update by DX **************************/
		/*
		String userToken = SysUserUtils.getToken(inv);
		//在memcached中删除注销用户token
		MCache.delete(userToken);
		//数据库删除用户对应的token
		tokenService.delete(userToken);
		*/
		/********************************************************/
		
		eraseCookie(inv.getRequest(),inv.getResponse());
		
		return AjaxUtils.printSuccessJson(null,"用户注销成功", inv);
	}
	
	private void eraseCookie(HttpServletRequest req, HttpServletResponse resp) {
	    Cookie[] cookies = req.getCookies();
	    if (cookies != null)
	        for (int i = 0; i < cookies.length; i++) {
	            cookies[i].setValue("");
	            cookies[i].setPath("/");
	            cookies[i].setMaxAge(0);
	            resp.addCookie(cookies[i]);
	        }
	}

	
	/**
	 * 修改密码
	 * @return
	 */
	@Post("user/modifypwd")
	public String user_modifypwd(@Param("oldPwd")String oldPwd,@Param("password")String password){
		User user = SysUserUtils.getCurrentUser(inv);
		if(user==null){
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_NOT_LOGIN,CodeConstant.ERROR_NOT_LOGIN_STR, inv);
		}
		String oldPwdMd5 = DigestUtils.md5Hex(oldPwd);
		if(user.getPassword()!= null && !user.getPassword().equals(oldPwdMd5)){
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_NOT_LOGIN,"原密码不正确", inv);
		}
		user.setPassword(DigestUtils.md5Hex(password));
		//修改数据库
		user = userService.update(user);
		//修改memcached中信息
		MCache.set(SysUserUtils.getToken(inv), user);
		
		return AjaxUtils.printSuccessJson(CodeConstant.SUCCESS_CODE,"修改密码成功", inv);
	}
	
	/**
	 * 修改用户
	 * @return
	 */
	@Post("user/update")
	public String user_update(@Param("param")String userJson){
		User user = JsonUtils.getObject(userJson, User.class);
		//用户ID不能为空
		if(StringUtils.isEmpty(user.getUsername())) {
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM, "用户名称不能为空", inv);
		}
		user = userService.update(user);
		
		return AjaxUtils.printSuccessJson(null,"修改密码成功", inv);
	}
}
