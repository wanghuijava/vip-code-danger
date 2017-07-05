package com.gsafety.starscream.basedata.controllers;

import java.util.List;

import javax.annotation.Resource;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Post;

import org.springframework.data.repository.query.Param;
import org.springframework.util.StringUtils;

import com.gsafety.common.utils.AjaxUtils;
import com.gsafety.common.utils.PinyinUtils;
import com.gsafety.starscream.basedata.model.District;
import com.gsafety.starscream.basedata.service.DistrictService;
import com.gsafety.starscream.constant.CodeConstant;
import com.gsafety.util.json.JsonUtils;

@Path("basedata")
public class DistrictController {

	@Resource
	private Invocation inv;
	
	@Resource
	private DistrictService districtService;
	
	/**
	 * 行政区划保存
	 * @param districtJson
	 * @return
	 */
	@Post("district/save")
	public String district_save(@Param("param")String districtJson){
		District district = JsonUtils.getObject(districtJson, District.class);
		//行政区划名称必填
		if(StringUtils.isEmpty(district.getDistName())) {
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM,"行政区划名称不能为空", inv);
		}
		//行政区划全拼
		district.setFullSpelling(PinyinUtils.getFullSpelling(district.getDistName()));
		//行政区划简拼
		district.setShortSpelling(PinyinUtils.getShortSpelling(district.getDistName()));
		
		district = districtService.save(district);
		return AjaxUtils.printSuccessJson(district,"行政区划保存成功", inv);
	}
	
	/**
	 * 行政区划删除
	 * @param distCode
	 * @return
	 */
	@Post("district/delete")
	public String district_delete(@Param("distCode")String distCode){
		//行政区划CODE必填
		if(StringUtils.isEmpty(distCode)) {
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM,"行政区划编码不能为空", inv);
		}
		districtService.delete(distCode);
		return AjaxUtils.printSuccessJson(null,"行政区划删除成功", inv);
	}
	
	/**
	 * 行政区划修改
	 * @param districtJson
	 * @return
	 */
	@Post("district/update")
	public String district_update(@Param("param")String districtJson){
		District district = JsonUtils.getObject(districtJson, District.class);
		//行政区划CODE必填
		if(StringUtils.isEmpty(district.getDistCode())) {
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM,"行政区划编码不能为空", inv);
		}
		//行政区划名称必填
		if(StringUtils.isEmpty(district.getDistName())) {
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM,"行政区划名称不能为空", inv);
		}
		//行政区划全拼
		district.setFullSpelling(PinyinUtils.getFullSpelling(district.getDistName()));
		//行政区划简拼
		district.setShortSpelling(PinyinUtils.getShortSpelling(district.getDistName()));
		
		district = districtService.update(district);
		return AjaxUtils.printSuccessJson(district,"行政区划修改成功", inv);
	}
	
	/**
	 * 根据行政区划编码查询
	 * @param distCode
	 * @return
	 */
	@Post("district/load")
	public String district_load(@Param("distCode")String distCode){
		//行政区划CODE必填
		if(StringUtils.isEmpty(distCode)) {
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM,"行政区划编码不能为空", inv);
		}
		District district = districtService.findByDistCode(distCode);
		return AjaxUtils.printSuccessJson(district, inv);
	}
	
	/**
	 * 行政区划查询
	 * @return
	 */
	@Post("district/find")
	public String district_find(){
		//查询所有行政区划，返回树形结构
		List<District> districts = districtService.findTree(null);
		return AjaxUtils.printSuccessJson(districts, inv);
	}
}
