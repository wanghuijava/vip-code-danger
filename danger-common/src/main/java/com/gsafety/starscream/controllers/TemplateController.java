package com.gsafety.starscream.controllers;

import java.io.File;

import javax.annotation.Resource;

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
		String fileName = getFileNameByResourceType(resourceType);
		
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
		String fileName = getFileNameByResourceType(resourceType);
		try {

			filePath += fileName;
			
			return FileUtils.downloadFile(inv.getResponse(), fileName, filePath);
		
		} catch (Exception e) {
			return null;
		}
		
	}
	
	private String getFileNameByResourceType(String resourceType){
		String fileName = null;
		//分析参数
		if("Expert".equals(resourceType)){
			fileName = "专家导入模板.xls";
		}else if("MatStorage".equals(resourceType)){
			fileName = "储备库导入模板.xls";
		}else if("RescueTeams".equals(resourceType)){
			fileName = "救援队伍导入模板.xls";
		}else if("Material".equals(resourceType)){
			fileName = "应急物资导入模板.xls";
		}else if("AsylumArea".equals(resourceType)){
			fileName = "避难场所导入模板.xls";
		}else if("MatMaterialFirm".equals(resourceType)){
			fileName = "物资企业导入模板.xls";
		}else if("Health".equals(resourceType)){
			fileName = "医疗卫生导入模板.xls";
		}else if("ComResource".equals(resourceType)){
			fileName = "通讯保障导入模板.xls";
		}else if("TransSecurity".equals(resourceType)){
			fileName = "运输资源导入模板.xls";
		}else if("PopStat".equals(resourceType)){
			fileName = "人口信息导入模板.xls";
		}else if("Financial".equals(resourceType)){
			fileName = "经济信息导入模板.xls";
		}else if("EmFund".equals(resourceType)){
			fileName = "应急救援资金导入模板.xls";
		}else if("Danger".equals(resourceType)){
			fileName = "危险源导入模板.xls";
		}else if("Defobj".equals(resourceType)){
			fileName = "防护目标导入模板.xls";
		}
		
		return fileName;
	}
}
