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
import com.gsafety.starscream.project.model.WorkPlan;
import com.gsafety.starscream.project.service.WorkPlanService;
import com.gsafety.starscream.project.util.ExportExcel4Project;
import com.gsafety.starscream.utils.SysUserUtils;
import com.gsafety.starscream.utils.format.DateUtil;
import com.gsafety.starscream.utils.page.ParamPage;
import com.gsafety.util.json.JsonUtils;

/**
 * 危险作业项目-计划
 * 
 * @author wanghui
 * @date 2016-1-1
 */
@Path("project")
public class WorkPlanController {
	@Autowired
	private Invocation inv;

	@Autowired
	private WorkPlanService workPlanService;

	@Autowired
	AttachService attachService;


	String viewUrl = "project/workPlan/";
	
	
	/**
	 * 添加页面
	 * @return
	 */
	@Get("workPlan/addPage")
	public String workPlan_addPage(){
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH, 1);
		now.set(Calendar.DAY_OF_MONTH, 1);
		inv.addModel("nextMonthBegin", DateUtil.formatDate(now.getTime()));
		now.add(Calendar.MONTH, 1);
		now.set(Calendar.DAY_OF_MONTH, -1);
		inv.addModel("nextMonthEnd", DateUtil.formatDate(now.getTime()));
		return viewUrl + "workPlan-add";
	}

	/**
	 * 编辑页面
	 * @param id
	 * @return
	 */
	@Get("workPlan/editPage/{id:[0-9]+}")
	public String workPlan_editPage(@Param("id")String id){
		inv.addModel("id", id);
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH, 1);
		now.set(Calendar.DAY_OF_MONTH, 1);
		inv.addModel("nextMonthBegin", DateUtil.formatDate(now.getTime()));
		now.add(Calendar.MONTH, 1);
		now.set(Calendar.DAY_OF_MONTH, -1);
		inv.addModel("nextMonthEnd", DateUtil.formatDate(now.getTime()));
		return viewUrl + "workPlan-edit";
	}

	/**
	 * 详情页面
	 * @param id
	 * @return
	 */
	@Get("workPlan/viewPage/{id:[0-9]+}")
	public String workPlan_viewPage(@Param("id")String id){
		WorkPlan workPlan = workPlanService.findById(id);
		workPlan.setMore();
		
		inv.addModel("dto",workPlan);
		
		return viewUrl + "workPlan-view";
	}

	/**
	 * 列表页面
	 * @param id
	 * @return
	 */
	@Get("workPlan/listPage")
	public String workPlan_listPage(){
		String id = SysUserUtils.getCurrentUser(inv).getId();
		inv.addModel("id",id);
		
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH, 1);
		inv.addModel("nextYearMonth", DateUtil.formatDate(now.getTime(), "yyyy-MM"));
		return viewUrl + "workPlan-list";
	}

	/**
	 * 新增
	 * 
	 * @param workPlanJson
	 * @return
	 */
	@Post("workPlan/save")
	public String workPlan_save(@Param("param") String workPlanJson) {
		WorkPlan workPlan = JsonUtils.getObject(workPlanJson,
				WorkPlan.class);
		User user = SysUserUtils.getCurrentUser(inv);
		if(user.isSuperior()){
			workPlan.setCheckFlag(1);
		}
		workPlan.setCreateBy(user.getId());
		workPlan.setModel();
		workPlan = workPlanService.save(workPlan);

		return AjaxUtils.printSuccessJson(workPlan, "操作成功", inv);
	}

	/**
	 * 确认上报
	 * 
	 * @param id
	 * @return
	 */
	@Post("workPlan/report")
	public String workPlan_report(@Param("id") String id) {
		WorkPlan workPlan = workPlanService.findById(id);
		workPlan.setCheckFlag(1);
		workPlan = workPlanService.update(workPlan);

		return AjaxUtils.printSuccessJson(null, "操作成功", inv);
	}

	/**
	 * 删除
	 * 
	 * @param event
	 * @return
	 */
	@Post("workPlan/delete")
	public String workPlan_delete(@Param("id") String id) {
		workPlanService.delete(id);
		return AjaxUtils.printSuccessJson(null, "删除成功", inv);
	}


	/**
	 * 查询
	 * 
	 * @param id
	 * @return workPlan
	 */
	@Post("workPlan/load")
	public String workPlan_load(@Param("id") String id) {
		WorkPlan workPlan = workPlanService.findById(id);
		workPlan.setMore();
		
		return AjaxUtils.printSuccessJson(workPlan, inv);
	}

	/**
	 * 查询
	 * 
	 * @param workPlanJson
	 *            ,pageJson
	 * @return List<WorkPlan>
	 */
	@Post("workPlan/find")
	public String workPlan_find(@Param("param") String workPlanJson,
			@Param("page") String pageJson) {
		WorkPlan workPlan = JsonUtils.getObject(workPlanJson,
				WorkPlan.class);
		// 如果是下级机构，则显示本机构数据......此处ing
		User user = SysUserUtils.getCurrentUser(inv);
		if(!user.isSuperior()){
			workPlan.setOrgCode(user.getOrgCode());
		}else{
			workPlan.setCheckFlag(1);//上级只看已上报的
		}
		
		if(StringUtils.isNotEmpty(workPlan.getNextMonth())){
			Calendar now = Calendar.getInstance();
			now.add(Calendar.MONTH, 1);
			now.set(Calendar.DAY_OF_MONTH, 1);
			workPlan.setSearchStartTimeStr(DateUtil.formatDate(now.getTime()));
			
			now.add(Calendar.MONTH, 1);
			now.set(Calendar.DAY_OF_MONTH, -1);
			workPlan.setSearchEndTimeStr(DateUtil.formatDate(now.getTime()));
		}else if(StringUtils.isNotEmpty(workPlan.getThisMonth())){
			Calendar now = Calendar.getInstance();
			now.set(Calendar.DAY_OF_MONTH, 1);
			workPlan.setSearchStartTimeStr(DateUtil.formatDate(now.getTime()));
			
			now.add(Calendar.MONTH, 1);
			now.set(Calendar.DAY_OF_MONTH, -1);
			workPlan.setSearchEndTimeStr(DateUtil.formatDate(now.getTime()));
		}
		
		ParamPage page = JsonUtils.getObject(pageJson, ParamPage.class);
		if (StringUtils.isEmpty(page.getOrderBy())) {
			page.setDir(PageConstant.DIR_DESC);
			page.setOrderBy("executeDate");
		}

		Page<WorkPlan> workPlanPages = workPlanService.find(workPlan, page.getPageable());
		page.setTotal(workPlanPages.getTotalElements());
		List<WorkPlan> workPlans = workPlanPages.getContent();
		List<WorkPlan> list = new ArrayList<WorkPlan>();
		for (WorkPlan model : workPlans) {
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
	@Get("workPlan/exportData")
	public String workPlan_exportData(@Param("param") String workPlanJson){

		WorkPlan workPlan = JsonUtils.getObject(workPlanJson,
				WorkPlan.class);
		// 如果是下级机构，则显示本机构数据......此处ing
		User user = SysUserUtils.getCurrentUser(inv);
		if(!user.isSuperior()){
			workPlan.setOrgCode(user.getOrgCode());
		}else{
			workPlan.setCheckFlag(1);//上级只看已上报的
		}
		
		//if(StringUtils.isNotEmpty(workPlan.getNextMonth())){
			Calendar now = Calendar.getInstance();
			now.add(Calendar.MONTH, 1);
			now.set(Calendar.DAY_OF_MONTH, 1);
			workPlan.setSearchStartTimeStr(DateUtil.formatDate(now.getTime()));
			
			now.add(Calendar.MONTH, 1);
			now.set(Calendar.DAY_OF_MONTH, -1);
			workPlan.setSearchEndTimeStr(DateUtil.formatDate(now.getTime()));
		//}
		
		String pageJson="{count:100000}";
		ParamPage page = JsonUtils.getObject(pageJson, ParamPage.class);
		page.setDir(PageConstant.DIR_ASC);
		page.setOrderBy("executeDate");
		Page<WorkPlan> expPages = workPlanService.find(workPlan, page.getPageable());
		
		List<WorkPlan> workPlanList = expPages.getContent();
		String exprotName="湖北分公司" + (now.get(Calendar.MONTH)+1) + "月危险作业计划";
		exportWorkPlan(exprotName, workPlanList);
		return null;
	}

	
	/**
	 * 时间段导出Excel
	 * @param id
	 * @return
	 */
	@Get("workPlan/exportData2")
	public String workPlan_exportData2(@Param("startTime") String startTime,@Param("endTime") String endTime){

		WorkPlan workPlan = new WorkPlan();
		workPlan.setSearchStartTimeStr(startTime);
		workPlan.setSearchEndTimeStr(endTime);
		// 如果是下级机构，则显示本机构数据......此处ing
		User user = SysUserUtils.getCurrentUser(inv);
		if(!user.isSuperior()){
			workPlan.setOrgCode(user.getOrgCode());
		}else{
			workPlan.setCheckFlag(1);//上级只看已上报的
		}
		
		String pageJson="{count:100000}";
		ParamPage page = JsonUtils.getObject(pageJson, ParamPage.class);
		page.setDir(PageConstant.DIR_ASC);
		page.setOrderBy("executeDate");
		Page<WorkPlan> expPages = workPlanService.find(workPlan, page.getPageable());
		
		List<WorkPlan> workPlanList = expPages.getContent();
		String exprotName="湖北分公司" + startTime + "至" + endTime + "危险作业计划";
		exportWorkPlan(exprotName, workPlanList);
		return null;
	}
	
	
	public String exportWorkPlan(String exprotName, List<WorkPlan> workPlanList){
		PrintWriter out = null;
		HttpServletResponse response=inv.getResponse();
		response.setCharacterEncoding("UTF-8");
		try {
			String[] title = new String []{"序号","公司名称","危险作业项目名称","作业级别","工程概况","计划实施时间","作业审批人","作业复核人","现场指挥"};
			String[] width = new String []{"5","12","12","12","12","12","12","12","12"};
			out=response.getWriter();
			List<Object[]> listObj=new ArrayList<Object[]>();
			
			if (workPlanList!=null && workPlanList.size()>0) {
				for (WorkPlan data : workPlanList) {
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
