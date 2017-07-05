package com.gsafety.starscream.utils;

import javax.annotation.Resource;

import net.paoding.rose.web.Invocation;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.gsafety.common.utils.CookieUtils;
import com.gsafety.starscream.basedata.model.User;
import com.gsafety.starscream.basedata.service.RoleService;
import com.gsafety.starscream.basedata.service.TokenService;
import com.gsafety.starscream.constant.BasedataConstant;
import com.gsafety.starscream.memcached.MCache;
import com.gsafety.starscream.utils.file.PropertiesUtils;

@Component("sysUserService")
public class SysUserUtils {

	@Resource
	private RoleService roleService;
	
	/**
	 * 获取当前请求token
	 * @param inv
	 * @return
	 */
	public static String getToken(Invocation inv){
		if(inv==null) {
			return StringUtils.EMPTY;
		}
		String userToken = inv.getRequest().getHeader(BasedataConstant.PARAM_TOKEN);
		if(StringUtils.isEmpty(userToken)){
			userToken = CookieUtils.getCookieValue(inv.getRequest(), BasedataConstant.PARAM_TOKEN);
		}
		return userToken==null?StringUtils.EMPTY:userToken;
	}
	
	/**
	 * 获取当前登录用户
	 * @param inv
	 * @return      user
	 */
	public static User getCurrentUser(Invocation inv){
		String userToken = getToken(inv);
		Object userObj = MCache.get(userToken);
		//memcache中获取未成功，继续从数据库中取
		if(userObj==null) {
			TokenService tokenService = RoseContextUtils.getBean(TokenService.class);
			return tokenService.findByTokenCode(userToken);
		}
		return (User)userObj;
	}
	/**
	 * 配置，
	 * 数据管理中，下级用户 所看到的数据，
	 * 1，当showData为true时，能看到所有模块中的数据，但是只能对比数据自己的机构的数据进行修改
	 * 2，当showData为false时，能看到各模块 中自己机构上传的数据，当然也是可以修改的，
	 * @return
	 */
	public static boolean showAllDate(){
		String showData = PropertiesUtils.getPropertiesValue("config.properties", "DATA_MANAGEMENT_SHOWSELFDATA");
		if(StringUtils.isEmpty(showData)) return true;
		return showData.equals("true")?true:false;
	}
	
}
