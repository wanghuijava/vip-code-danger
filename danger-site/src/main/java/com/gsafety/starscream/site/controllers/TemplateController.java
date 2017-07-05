package com.gsafety.starscream.site.controllers;

import java.io.File;

import javax.annotation.Resource;

import com.gsafety.starscream.utils.SysUserUtils;
import com.gsafety.starscream.utils.file.FileUtils;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;


@Path("")
public class TemplateController {

	@Resource
	private Invocation inv;

	@Get("resource/importPage/{resourceType:[a-zA-Z]+}")
	public String importPage(@Param("resourceType")String resourceType){
		String fileName = getFileNameByResourceType(resourceType, SysUserUtils.getCurrentUser(inv).isSuperior());
		
		inv.addModel("resourceType", resourceType);
		inv.addModel("fileName", fileName);
		return "inc/resource-import.jsp";
	}
	
	/**
	 * 数据导入下载模板
	 * @param attachId
	 * @return
	 */
	@Get("resource/downloadTemplate/{resourceType:[a-zA-Z]+}")
	public String downloadTemplate(@Param("resourceType")String resourceType) {
		//模板路径
		String filePath = inv.getServletContext().getRealPath("/")+File.separator+"office-template"+File.separator;
		String fileName = getFileNameByResourceType(resourceType, SysUserUtils.getCurrentUser(inv).isSuperior());
		try {

			filePath += fileName;
			
			return FileUtils.downloadFile(inv.getResponse(), fileName, filePath);
		
		} catch (Exception e) {
			return null;
		}
		
	}
	
	private String getFileNameByResourceType(String resourceType, boolean isSuperior){
		String fileName = null;
		//分析参数
		if("WorkPlan".equals(resourceType)){
			fileName = "危险作业项目计划导入模板";
		}else if("ProducePlan".equals(resourceType)){
			fileName = "试运投产项目计划导入模板";
		}
		
		if(isSuperior){
			fileName += "-上级.xls";
		} else {
			fileName += ".xls";
		}
		
		return fileName;
	}
}
