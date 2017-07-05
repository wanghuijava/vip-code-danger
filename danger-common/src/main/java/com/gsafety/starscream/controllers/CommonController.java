package com.gsafety.starscream.controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.util.StringUtils;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import com.gsafety.common.utils.AjaxUtils;
import com.gsafety.starscream.constant.CodeConstant;
import com.gsafety.starscream.memcached.MCache;
import com.gsafety.starscream.utils.tree.TreeUtils;

/**
 * 公用controller编写
 * @author chenwenlong
 *
 */
@Path("common")
public class CommonController {

	@Resource
	private Invocation inv;

	/**
	 * 清空memcache缓存
	 * @return
	 */
	@Get("memcache/flush")
	public String memcache_flush(){
		MCache.flush();
		return AjaxUtils.printSuccessJson("memcache缓存清空", inv);
	}
	
	/**
	 * 根据openid取得树机构数据，配置项在tree.properties
	 * @return
	 */
	@Post("tree/findTree")
	public String tree_findTreeTest(@Param("treeid")String treeid, @Param("type")String type, @Param("filter")String filter){
		if(!StringUtils.isEmpty(filter)){
//			String pattern = "[0-9]*";
//			Pattern p = Pattern.compile(pattern);
//			Matcher m = p.matcher(filter);
//			if(!m.matches()){
//				return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM, "filter不为数字，参数不正确", inv);
//			}
			//filter = "user_org_code ='"+filter+"'";
			filter = filter.replaceAll("#", "'");
		}
		
		return AjaxUtils.printSuccessJson(TreeUtils.findTreesByIdAndType(treeid, type, filter), inv);
	}
}
