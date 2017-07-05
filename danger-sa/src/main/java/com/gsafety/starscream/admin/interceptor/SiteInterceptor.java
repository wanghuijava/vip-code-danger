package com.gsafety.starscream.admin.interceptor;

import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;

import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.gsafety.starscream.admin.annotation.NotCareLogin;
import com.gsafety.starscream.admin.utils.UserUtils;

@Component
public class SiteInterceptor extends ControllerInterceptorAdapter {
	
	@Override
	protected Object before(Invocation inv) throws Exception {
		Class<? extends Object> controller = inv.getController().getClass();
		boolean isPresent = controller.isAnnotationPresent(NotCareLogin.class) || inv.getMethod().isAnnotationPresent(NotCareLogin.class);
		if(isPresent){
			return true;
		}
		
		int userId = UserUtils.getUserId(inv);
		if(userId <= 0){
			return notLoginRedirect(inv);
		}
		
		return true;
	}

	@Override
	protected Object after(Invocation inv, Object instruction) throws Exception {
		inv.addModel("adminUrl", inv.getRequest().getContextPath());
		return super.after(inv, instruction);
	}
	
	Object notLoginRedirect(Invocation inv) {
		if(inv.getRequest().getHeader("Accept").contains("application/json")){
			JsonObject json = new JsonObject();
			json.addProperty("error_code", "1");
			return "@json:" + json.toString();
		}else{
			return "jump-to-login";
		}
	}
}
