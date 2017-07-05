package com.gsafety.starscream.admin.controllers;

import java.util.Date;

import javax.servlet.http.HttpSession;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gsafety.starscream.admin.annotation.NotCareLogin;
import com.gsafety.starscream.admin.model.User;
import com.gsafety.starscream.utils.R;
import com.gsafety.starscream.utils.file.PropertiesUtils;

@Path("")
public class HomeController {

	@Autowired
	Invocation inv;

	@Get("")
	public String none(){
		return R.redirect("/index", inv);
	}

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
		HttpSession userSession = inv.getRequest().getSession();
		userSession.removeAttribute("userId");
		return R.redirect("/login", inv);
	}
	
	@NotCareLogin
	@Post("login")
	public String userLogin(@Param("username") String username, @Param("password") String password){
		
		User user = new User();
		if(StringUtils.isEmpty(user.getPassword())){
			user.setPassword(PropertiesUtils.getPropertiesValue("config.properties", "user-login-password"));
		}
		
		if((!user.getUsername().equals(username)) || (!user.getPassword().equals(password))){
			inv.addFlash("errorMSG", "用户名或密码不正确！");
			return R.redirect("/login", inv);
		}
		
		user.setLastloginip(inv.getRequest().getRemoteHost());
		user.setLastlogintime(new Date());
		
		HttpSession userSession = inv.getRequest().getSession();
		userSession.setAttribute("userId", user.getUserid());
		userSession.setAttribute("lastloginip", user.getLastloginip());
		userSession.setAttribute("lastlogintime", user.getLastlogintime());
		
		return R.redirect("/index", inv);
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
