package com.gsafety.starscream.interceptor;

import javax.servlet.http.HttpServletRequest;

import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gsafety.common.utils.AjaxUtils;
import com.gsafety.starscream.annotation.NotCareLogin;
import com.gsafety.starscream.basedata.model.User;
import com.gsafety.starscream.basedata.service.TokenService;
import com.gsafety.starscream.constant.BasedataConstant;
import com.gsafety.starscream.constant.CodeConstant;
import com.gsafety.starscream.memcached.MCache;

@Component
public class AuthorityInterceptor extends ControllerInterceptorAdapter {

	private final String PARAM_TOKEN = "token";
	private final String GSAFETY_SUPER_USER = "GSAFETY_SUPER_USER";
	//用户token是否进行数据库检查标识
	private final boolean DB_REFETCH_TAG = false;
	
	@Autowired
	TokenService tokenService;
	
	@Override
	protected Object before(Invocation inv) throws Exception {		
		
		
		if(StringUtils.isEmpty(BasedataConstant.CONTEXT_PATH)){
			BasedataConstant.CONTEXT_PATH = inv.getRequestPath().getCtxpath();
		}
		return true;
//		//检查方法是否要求检验登陆及权限
//		Class<? extends Object> controller = inv.getControllerClass();
//		//是否需要权限校验参数
//		boolean noAuthoriyRequire = controller.isAnnotationPresent(NotCareLogin.class) || inv.getMethod().isAnnotationPresent(NotCareLogin.class);
//		if(noAuthoriyRequire){
//			return true;
//		}
//		
//		//对用户登陆权限进行校验
//		HttpServletRequest request = inv.getRequest();
//		String userToken = request.getParameter(PARAM_TOKEN);
//		String userAuthority = (String)MCache.get(userToken);
//
//		//判断是否为超级用户
//		if(userAuthority.equalsIgnoreCase(GSAFETY_SUPER_USER)){
//			return true;
//		}
//		
//		//MCache验证失败时，是否再进行一次数据库验证
//		if(DB_REFETCH_TAG && StringUtils.isEmpty(userAuthority)){
//			User user = tokenService.findByTokenCode(userToken);
//			if(null != user){
//				userAuthority = user.getAuthority();
//			}
//		}
//		
//		if(StringUtils.isEmpty(userAuthority)){
//			return AjaxUtils.printErrorJson(CodeConstant.ERROR_NOT_LOGIN, CodeConstant.ERROR_NOT_LOGIN_STR, inv);
//		}
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
