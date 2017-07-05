package com.gsafety.starscream.basedata.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import org.springframework.beans.factory.annotation.Autowired;
import com.gsafety.common.utils.AjaxUtils;
import com.gsafety.starscream.basedata.model.BasSysCfgprop;
import com.gsafety.starscream.basedata.service.BasSysCfgpropService;
import com.gsafety.starscream.utils.R;

@Path("basedata")
public class Admin_BasSysCfgpropController {
	@Autowired
	private Invocation inv;
	@Autowired
	private BasSysCfgpropService basSysCfgpropService;
	
	
	//系统属性配置页面
	@Get("basSysCfgProp/list")
	public String basSysCfgProp_listPage(){
		List<BasSysCfgprop> list=basSysCfgpropService.findAll();
		inv.addModel("list", list);
		return "/views/basSysCfgProp/basSysCfgProp-list";
	}
	
	//系统属性配置添加页面
	@Get("basSysCfgProp/add")
	public String basSysCfgProp_add(){
		return "/views/basSysCfgProp/basSysCfgProp-add";
	}
	
	/**
	 * 添加系统属性
	 * @param BasSysCfgprop
	 * @return
	 */
	@Post("basSysCfgProp/save")
	public String basSysCfgProp_save(BasSysCfgprop model){
		basSysCfgpropService.save(model);
		return R.redirect("/basedata/basSysCfgProp/list", inv);
	}
	
	/**
	 * 查询属性名称是否存在
	 * @param propName
	 * @return
	 */
	@Post("basSysCfgProp/findByPropName")
	public String basSysCfgProp_findByPropName(@Param("propName") String propName) {
		BasSysCfgprop model=basSysCfgpropService.findByPropName(propName);
		Map<String, String> map=new HashMap<String, String>();
		if (model!=null) {
			map.put("flag", "0");
		}else{
			map.put("flag", "1");
		}
		System.out.println(AjaxUtils.printSuccessJson(map, inv));
		return AjaxUtils.printSuccessJson(map, inv);
	}
	
	
	/**
	 * 系统属性编辑页面
	 * @param propId
	 * @return
	 */
	@Get("basSysCfgProp/edit/{propId:[0-9]+}")
	public String basSysCfgProp_edit(@Param("propId")String propId){
		BasSysCfgprop model=basSysCfgpropService.findByPropId(propId);
		inv.addModel("entity", model);
		return "/views/basSysCfgProp/basSysCfgProp-edit";
	}
	
	/**
	 * 编辑系统属性
	 * @param BasSysCfgprop
	 * @return
	 */
	@Post("basSysCfgProp/edit")
	public String basSysCfgProp_edit(BasSysCfgprop model){
		basSysCfgpropService.save(model);
		return R.redirect("/basedata/basSysCfgProp/list", inv);
	}
	
	/**
	 * 删除系统属性
	 * @param propId
	 * @return
	 */
	@Get("basSysCfgProp/delete/{propId:[0-9]+}")
	public String district_delete(@Param("propId") String propId) {
		basSysCfgpropService.delete(propId);
		return R.redirect("/basedata/basSysCfgProp/list", inv);
	}

}
