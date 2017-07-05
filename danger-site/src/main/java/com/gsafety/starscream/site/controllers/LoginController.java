package com.gsafety.starscream.site.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gsafety.starscream.annotation.NotCareLogin;
import com.gsafety.starscream.basedata.service.SystemOperationLogsService;
import com.gsafety.starscream.constant.BasedataConstant;
import com.gsafety.starscream.utils.R;

@Path("")
public class LoginController {

	@Autowired
	Invocation inv;

	@Autowired
	private SystemOperationLogsService logsService;
	
	@Get("")
	public String none(){
		return R.redirect("/index", inv);
	}

	
	/**
	 * 应急资源检索系统(后续需要更换模块名称) Add by DX
	 * @return
	 */
	@Get("index")
    public String index() {
    	return "index";
    }
	
	@NotCareLogin
	@Get("login")
	public String login(){
		String errorMSG = inv.getFlash().get("errorMSG");
		if(!StringUtils.isEmpty(errorMSG)){
			inv.addModel("errorMSG", errorMSG);
		}
		
		return "login";
	}
	
	@Get("logout")
	public String logout(){
		
		//添加操作日志 
		Map<String,String> opeationMap = new HashMap<String, String>();
		opeationMap.put("operationType",BasedataConstant.SYSTEM_LOG);
		opeationMap.put("funName","注销");
		logsService.addLogs(opeationMap, inv);
		
		HttpSession userSession = inv.getRequest().getSession();
		userSession.removeAttribute("userId");
		return R.redirect("/login", inv);
	}
	
	@Get("menu/{url:[\\w-]+}")
    public String basedataM(@Param("url") String url) {
    	return "menu/" + url;
    }
	
	
	@Get("empty")
    public String empty() {
    	return "empty";
    }
}
