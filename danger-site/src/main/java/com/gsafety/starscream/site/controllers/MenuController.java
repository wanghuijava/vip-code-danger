package com.gsafety.starscream.site.controllers;

import java.util.Date;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.springframework.beans.factory.annotation.Autowired;

import com.gsafety.starscream.utils.format.DateUtil;

@Path("")
public class MenuController {

	@Autowired
	Invocation inv;
	

	/**
	 * 进入到危险作业系统模块首页
	 * @return
	 */
	@Get("indexProjectPage")
    public String index_data() {
    	return "index-project";
    }
	

	/**
	 * 进入到危险作业大屏展示页
	 * @return
	 */
	@Get("indexDapingPage")
    public String index_daping() {
		inv.addModel("nowDate", DateUtil.formatDate(new Date()));
    	return "index-daping";
    }
}
