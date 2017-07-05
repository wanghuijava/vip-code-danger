package com.gsafety.starscream.basedata.controllers;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Post;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import com.gsafety.common.utils.AjaxUtils;
import com.gsafety.starscream.basedata.model.Authority;
import com.gsafety.starscream.basedata.service.AuthorityService;
import com.gsafety.starscream.constant.CodeConstant;
import com.gsafety.starscream.utils.page.ParamPage;
import com.gsafety.util.json.JsonUtils;

/**
 * 权限controller
 * @author chenwenlong
 *
 */
@Path("basedata")
public class AuthorityController {

	@Resource
	private Invocation inv;
	
	@Resource
	private AuthorityService authorityService;
	
	/**
	 * 权限保存
	 * @param authorityJson
	 * @return
	 */
	@Post("authority/save")
	public String authority_save(@Param("param")String authorityJson){
		Authority authority = JsonUtils.getObject(authorityJson, Authority.class);
		//权限名称必填
		if(StringUtils.isEmpty(authority.getName())) {
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM,"权限名称不能为空", inv);
		}
		//权限URL必填
		if(StringUtils.isEmpty(authority.getUrl())) {
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM,"权限路径不能为空", inv);
		}
		authority = authorityService.save(authority);
		return AjaxUtils.printSuccessJson(authority,"权限保存成功", inv);
	}
	
	/**
	 * 权限删除
	 * @param id
	 * @return
	 */
	@Post("authority/delete")
	public String authority_delete(@Param("id")String id){
		if(id==null) {
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM,"权限ID不能为空", inv);
		}
		authorityService.delete(id);
		return AjaxUtils.printSuccessJson(null,"权限删除成功", inv);
	}
	
	/**
	 * 权限修改
	 * @param authorityJson
	 * @return
	 */
	@Post("authority/update")
	public String authority_update(@Param("param")String authorityJson){
		Authority authority = JsonUtils.getObject(authorityJson, Authority.class);
		if(authority.getId()==null) {
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM,"权限ID不能为空", inv);
		}
		authority = authorityService.update(authority);
		return AjaxUtils.printSuccessJson(authority,"权限修改成功", inv);
	}
	
	/**
	 * 根据ID查看权限
	 * @return
	 */
	@Post("authority/load")
	public String authority_load(@Param("id")String id){
		if(id==null) {
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM,"权限ID不能为空", inv);
		}
		Authority authority = authorityService.findById(id);
		return AjaxUtils.printSuccessJson(authority, inv);
	}
	
	/**
	 * 权限列表查询
	 * @return
	 */
	@Post("authority/find")
	public String authority_find(){
		Authority authority=null;
		String pageJson="";
		ParamPage page = JsonUtils.getObject(pageJson, ParamPage.class);
		Page<Authority> authorityList = authorityService.find(authority, page.getPageable());
		page.setTotal(authorityList.getTotalElements());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("page", page);
		resultMap.put("result", authorityList);
		return AjaxUtils.printSuccessJson(authorityList, inv);
	}
}
