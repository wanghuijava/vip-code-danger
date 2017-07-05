package com.gsafety.starscream.site.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.gsafety.common.utils.AjaxUtils;
import com.gsafety.common.utils.CookieUtils;
import com.gsafety.starscream.annotation.NotCareLogin;
import com.gsafety.starscream.basedata.model.User;
import com.gsafety.starscream.basedata.service.TokenService;
import com.gsafety.starscream.constant.BasedataConstant;
import com.gsafety.starscream.constant.CodeConstant;
import com.gsafety.starscream.memcached.MCache;
import com.gsafety.starscream.utils.BasSysCfgpropUtils;
import com.gsafety.starscream.utils.StarscreamProperties;

@Component
public class SiteInterceptor extends ControllerInterceptorAdapter {

	private final String PARAM_TOKEN = "token";
	private final String GSAFETY_SUPER_USER = "GSAFETY_SUPER_USER";
	//用户token是否进行数据库检查标识
	private final boolean DB_REFETCH_TAG = true;
	
	@Autowired
	TokenService tokenService;
	
	@Override
	protected Object before(Invocation inv) throws Exception {		
		
		
		if(StringUtils.isEmpty(BasedataConstant.CONTEXT_PATH)){
			BasedataConstant.CONTEXT_PATH = inv.getRequestPath().getCtxpath();
		}
		
		StarscreamProperties p = StarscreamProperties.getInstance();
		inv.addModel("siteUrl", BasedataConstant.CONTEXT_PATH);
		inv.addModel("fileUrl", p.getPropValue("FILE_PATH"));
		//文件解析器view Url
		inv.addModel("officeUrl", p.getOfficeAnalysisViewUrl());
		
		//系统参数配置
		Map<String,String> map=BasSysCfgpropUtils.getSysCfgpropMap();
		inv.addModel("sysCfgMap",map);
		
		//return true;
		//检查方法是否要求检验登陆及权限
		Class<? extends Object> controller = inv.getControllerClass();
		//是否需要权限校验参数
		boolean noAuthoriyRequire = controller.isAnnotationPresent(NotCareLogin.class) || inv.getMethod().isAnnotationPresent(NotCareLogin.class);
		if(noAuthoriyRequire){
			return true;
		}
		
		//对用户登陆权限进行校验
		HttpServletRequest request = inv.getRequest();
		String userToken = request.getHeader(PARAM_TOKEN);
		if(StringUtils.isEmpty(userToken)){
			userToken = CookieUtils.getCookieValue(request, PARAM_TOKEN);
		}

//		System.out.println(controller+":"+inv.getRequestPath().getRosePath()+"---SiteInterceptor----"+userToken);
		User userMCache = (User)MCache.get(userToken);
		String userAuthority = null;
		if (userMCache != null){
			userAuthority = userMCache.getAuthority();
			inv.addModel("user", userMCache);
		}

		//判断是否为超级用户
		if(GSAFETY_SUPER_USER.equalsIgnoreCase(userAuthority)){
			return true;
		}
		
		//MCache验证失败时，是否再进行一次数据库验证
		if(DB_REFETCH_TAG && StringUtils.isEmpty(userAuthority)){
			User user = tokenService.findByTokenCode(userToken);
			if(null != user){
				userAuthority = user.getAuthority();
				inv.addModel("user", user);
			}
		}
		
		if(StringUtils.isEmpty(userAuthority)){
			if(inv.getRequest().getHeader("Accept").contains("application/json")){
				JsonObject json = new JsonObject();
				json.addProperty("error_code", "1");
				return AjaxUtils.printErrorJson(CodeConstant.ERROR_NOT_LOGIN, CodeConstant.ERROR_NOT_LOGIN_STR, inv);
			}else{
				return "jump-to-login";
			}
		}else 
		return true;
//		
//		//判断用户访问路径权限
//		String rosePath = inv.getRequestPath().getRosePath();
//		String patternStr = null;
//		//递归匹配用户路径访问权限
//		do{
//			patternStr = "\"" + rosePath + "\"";
//			if(userAuthority.contains(patternStr)){
//				return true;
//			}
//		}while(StringUtils.isNotEmpty(rosePath = StringUtils.substringBeforeLast(rosePath, "/")));
//		
//		return AjaxUtils.printErrorJson(CodeConstant.ERROR_NO_AUTHORITY, CodeConstant.ERROR_NO_AUTHORITY_STR, inv);
	}
	
	
	
}
