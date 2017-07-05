package com.gsafety.starscream.project.controllers;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.gsafety.common.utils.AjaxUtils;
import com.gsafety.starscream.basedata.model.User;
import com.gsafety.starscream.basedata.service.AttachService;
import com.gsafety.starscream.constant.PageConstant;
import com.gsafety.starscream.project.model.ProducePlan;
import com.gsafety.starscream.project.service.ProducePlanService;
import com.gsafety.starscream.project.util.ExportExcel4Project;
import com.gsafety.starscream.utils.SysUserUtils;
import com.gsafety.starscream.utils.format.DateUtil;
import com.gsafety.starscream.utils.page.ParamPage;
import com.gsafety.util.json.JsonUtils;

/**
 * 试运投产项目-计划
 * 
 * @author wanghui
 * @date 2016-1-1
 */
@Path("project")
public class ProducePlanController {
	@Autowired
	private Invocation inv;

	@Autowired
	private ProducePlanService producePlanService;

	@Autowired
	AttachService attachService;


	String viewUrl = "project/producePlan/";
	
	
	/**
	 * 添加页面
	 * @return
	 */
	@Get("producePlan/addPage")
	public String producePlan_addPage(){
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH, 1);
		now.set(Calendar.DAY_OF_MONTH, 1);
		inv.addModel("nextMonthBegin", DateUtil.formatDate(now.getTime()));
		now.add(Calendar.MONTH, 1);
		now.set(Calendar.DAY_OF_MONTH, -1);
		inv.addModel("nextMonthEnd", DateUtil.formatDate(now.getTime()));
		return viewUrl + "producePlan-add";
	}

	/**
	 * 编辑页面
	 * @param id
	 * @return
	 */
	@Get("producePlan/editPage/{id:[0-9]+}")
	public String producePlan_editPage(@Param("id")String id){
		inv.addModel("id", id);
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH, 1);
		now.set(Calendar.DAY_OF_MONTH, 1);
		inv.addModel("nextMonthBegin", DateUtil.formatDate(now.getTime()));
		now.add(Calendar.MONTH, 1);
		now.set(Calendar.DAY_OF_MONTH, -1);
		inv.addModel("nextMonthEnd", DateUtil.formatDate(now.getTime()));
		return viewUrl + "producePlan-edit";
	}

	/**
	 * 详情页面
	 * @param id
	 * @return
	 */
	@Get("producePlan/viewPage/{id:[0-9]+}")
	public String producePlan_viewPage(@Param("id")String id){
		ProducePlan producePlan = producePlanService.findById(id);
		producePlan.setMore();
		
		inv.addModel("dto",producePlan);
		
		return viewUrl + "producePlan-view";
	}

	/**
	 * 列表页面
	 * @param id
	 * @return
	 */
	@Get("producePlan/listPage")
	public String producePlan_listPage(){
		String id = SysUserUtils.getCurrentUser(inv).getId();
		inv.addModel("id",id);
		
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH, 1);
		inv.addModel("nextYearMonth", DateUtil.formatDate(now.getTime(), "yyyy-MM"));
		return viewUrl + "producePlan-list";
	}

	/**
	 * 新增
	 * 
	 * @param producePlanJson
	 * @return
	 */
	@Post("producePlan/save")
	public String producePlan_save(@Param("param") String producePlanJson) {
		ProducePlan producePlan = JsonUtils.getObject(producePlanJson,
				ProducePlan.class);
		User user = SysUserUtils.getCurrentUser(inv);
		if(user.isSuperior()){
			producePlan.setCheckFlag(1);
		}
		producePlan.setCreateBy(SysUserUtils.getCurrentUser(inv).getId());
		producePlan.setModel();
		producePlan = producePlanService.save(producePlan);

		return AjaxUtils.printSuccessJson(producePlan, "操作成功", inv);
	}

	/**
	 * 确认上报
	 * 
	 * @param id
	 * @return
	 */
	@Post("producePlan/report")
	public String producePlan_report(@Param("id") String id) {
		ProducePlan producePlan = producePlanService.findById(id);
		producePlan.setCheckFlag(1);
		producePlan = producePlanService.update(producePlan);

		return AjaxUtils.printSuccessJson(null, "操作成功", inv);
	}

	/**
	 * 删除
	 * 
	 * @param event
	 * @return
	 */
	@Post("producePlan/delete")
	public String producePlan_delete(@Param("id") String id) {
		producePlanService.delete(id);
		return AjaxUtils.printSuccessJson(null, "删除成功", inv);
	}


	/**
	 * 查询
	 * 
	 * @param id
	 * @return producePlan
	 */
	@Post("producePlan/load")
	public String producePlan_load(@Param("id") String id) {
		ProducePlan producePlan = producePlanService.findById(id);
		producePlan.setMore();
		
		return AjaxUtils.printSuccessJson(producePlan, inv);
	}

	/**
	 * 查询
	 * 
	 * @param producePlanJson
	 *            ,pageJson
	 * @return List<ProducePlan>
	 */
	@Post("producePlan/find")
	public String producePlan_find(@Param("param") String producePlanJson,
			@Param("page") String pageJson) {
		ProducePlan producePlan = JsonUtils.getObject(producePlanJson,
				ProducePlan.class);
		// 如果是下级机构，则显示本机构数据......此处ing
		User user = SysUserUtils.getCurrentUser(inv);
		if(!user.isSuperior()){
			producePlan.setOrgCode(user.getOrgCode());
		}else{
			producePlan.setCheckFlag(1);//上级只看已上报的
		}
		
		if(StringUtils.isNotEmpty(producePlan.getNextMonth())){
			Calendar now = Calendar.getInstance();
			now.add(Calendar.MONTH, 1);
			now.set(Calendar.DAY_OF_MONTH, 1);
			producePlan.setSearchStartTimeStr(DateUtil.formatDate(now.getTime()));
			
			now.add(Calendar.MONTH, 1);
			now.set(Calendar.DAY_OF_MONTH, -1);
			producePlan.setSearchEndTimeStr(DateUtil.formatDate(now.getTime()));
		}else if(StringUtils.isNotEmpty(producePlan.getThisMonth())){
			Calendar now = Calendar.getInstance();
			now.set(Calendar.DAY_OF_MONTH, 1);
			producePlan.setSearchStartTimeStr(DateUtil.formatDate(now.getTime()));
			
			now.add(Calendar.MONTH, 1);
			now.set(Calendar.DAY_OF_MONTH, -1);
			producePlan.setSearchEndTimeStr(DateUtil.formatDate(now.getTime()));
		}
		
		ParamPage page = JsonUtils.getObject(pageJson, ParamPage.class);
		if (StringUtils.isEmpty(page.getOrderBy())) {
			page.setDir(PageConstant.DIR_DESC);
			page.setOrderBy("executeDate");
		}

		Page<ProducePlan> producePlanPages = producePlanService.find(producePlan, page.getPageable());
		page.setTotal(producePlanPages.getTotalElements());
		List<ProducePlan> producePlans = producePlanPages.getContent();
		List<ProducePlan> list = new ArrayList<ProducePlan>();
		for (ProducePlan model : producePlans) {
			model.setMore();
			list.add(model);
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", list);
		resultMap.put("page", page);
		
		return AjaxUtils.printSuccessJson(resultMap, inv);
	}
	

	
	/**
	 * 本月导出Excel
	 * @param id
	 * @return
	 */
	@Get("producePlan/exportData")
	public String producePlan_exportData(@Param("param") String producePlanJson){

		ProducePlan producePlan = JsonUtils.getObject(producePlanJson,
				ProducePlan.class);
		// 如果是下级机构，则显示本机构数据......此处ing
		User user = SysUserUtils.getCurrentUser(inv);
		if(!user.isSuperior()){
			producePlan.setOrgCode(user.getOrgCode());
		}else{
			producePlan.setCheckFlag(1);//上级只看已上报的
		}
		
		//if(StringUtils.isNotEmpty(producePlan.getNextMonth())){
			Calendar now = Calendar.getInstance();
			now.add(Calendar.MONTH, 1);
			now.set(Calendar.DAY_OF_MONTH, 1);
			producePlan.setSearchStartTimeStr(DateUtil.formatDate(now.getTime()));
			
			now.add(Calendar.MONTH, 1);
			now.set(Calendar.DAY_OF_MONTH, -1);
			producePlan.setSearchEndTimeStr(DateUtil.formatDate(now.getTime()));
		//}
		
		String pageJson="{count:100000}";
		ParamPage page = JsonUtils.getObject(pageJson, ParamPage.class);
		page.setDir(PageConstant.DIR_ASC);
		page.setOrderBy("executeDate");
		Page<ProducePlan> expPages = producePlanService.find(producePlan, page.getPageable());
		
		List<ProducePlan> producePlanList = expPages.getContent();
		String exprotName="湖北分公司" + (now.get(Calendar.MONTH)+1) + "月试运投产作业计划";
		exportProducePlan(exprotName, producePlanList);
		return null;
	}

	
	/**
	 * 时间段导出Excel
	 * @param id
	 * @return
	 */
	@Get("producePlan/exportData2")
	public String producePlan_exportData2(@Param("startTime") String startTime,@Param("endTime") String endTime){

		ProducePlan producePlan = new ProducePlan();
		producePlan.setSearchStartTimeStr(startTime);
		producePlan.setSearchEndTimeStr(endTime);
		// 如果是下级机构，则显示本机构数据......此处ing
		User user = SysUserUtils.getCurrentUser(inv);
		if(!user.isSuperior()){
			producePlan.setOrgCode(user.getOrgCode());
		}else{
			producePlan.setCheckFlag(1);//上级只看已上报的
		}
		
		String pageJson="{count:100000}";
		ParamPage page = JsonUtils.getObject(pageJson, ParamPage.class);
		page.setDir(PageConstant.DIR_ASC);
		page.setOrderBy("executeDate");
		Page<ProducePlan> expPages = producePlanService.find(producePlan, page.getPageable());
		
		List<ProducePlan> producePlanList = expPages.getContent();
		String exprotName="湖北分公司" + startTime + "至" + endTime + "试运投产作业计划";
		exportProducePlan(exprotName, producePlanList);
		return null;
	}
	
	
	public String exportProducePlan(String exprotName, List<ProducePlan> producePlanList){
		PrintWriter out = null;
		HttpServletResponse response=inv.getResponse();
		response.setCharacterEncoding("UTF-8");
		try {
			String[] title = new String []{"序号","公司名称","试运投产项目名称","作业级别","工程概况","计划实施时间","作业审批人","作业复核人","现场指挥"};
			String[] width = new String []{"5","12","12","12","12","12","12","12","12"};
			out=response.getWriter();
			List<Object[]> listObj=new ArrayList<Object[]>();
			
			if (producePlanList!=null && producePlanList.size()>0) {
				for (ProducePlan data : producePlanList) {
					Object[] obj =new Object[title.length-1];
					int i=0;
					obj[i++] =data.getOrgName();
					obj[i++] =data.getWorkName();
					obj[i++] =data.getWorkLevel();
					obj[i++] =data.getSummary();
					obj[i++] = DateUtil.formatDate(data.getExecuteDate());
					obj[i++] =data.getExaminer();
					obj[i++] =data.getChecker();
					obj[i++] =data.getDirector();
					
					listObj.add(obj);
				}
			}
			ExportExcel4Project.setExportExcel(response, exprotName, false);
			ExportExcel4Project.createExcel(response.getOutputStream(),listObj, title, exprotName,width);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			out.flush();
			out.close();
		}
		return null;
	}
	
}
