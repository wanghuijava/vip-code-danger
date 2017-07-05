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
import com.gsafety.starscream.project.model.WorkExecute;
import com.gsafety.starscream.project.model.WorkPlan;
import com.gsafety.starscream.project.service.WorkExecuteService;
import com.gsafety.starscream.project.service.WorkPlanService;
import com.gsafety.starscream.project.util.ExportExcel4Project;
import com.gsafety.starscream.utils.SysUserUtils;
import com.gsafety.starscream.utils.format.DateUtil;
import com.gsafety.starscream.utils.page.ParamPage;
import com.gsafety.util.json.JsonUtils;

/**
 * 危险作业项目-实施（今日录入）
 * 
 * @author wanghui
 * @date 2016-1-1
 */
@Path("project")
public class WorkExecuteController {
	@Autowired
	private Invocation inv;

	@Autowired
	private WorkExecuteService workExecuteService;

	@Autowired
	private WorkPlanService workPlanService;

	@Autowired
	AttachService attachService;


	String viewUrl = "project/workExecute/";
	
	
	/**
	 * 添加页面
	 * @return
	 */
	@Get("workExecute/addPage")
	public String workExecute_addPage(@Param("workPlanId")String workPlanId){

		Calendar now = Calendar.getInstance();
		inv.addModel("todayDate", DateUtil.formatDate(now.getTime()));
		
		if(StringUtils.isNotEmpty(workPlanId)){
			WorkPlan workPlanValue = workPlanService.findById(workPlanId);
			inv.addModel("workPlan", workPlanValue);
			return viewUrl + "workExecute-add4";
		}
		
		WorkPlan workPlan = new WorkPlan();
		// 如果是下级机构，则显示本机构数据......此处ing
		User user = SysUserUtils.getCurrentUser(inv);
		if(!user.isSuperior()){
			workPlan.setOrgCode(user.getOrgCode());
		}

		//本月的未关闭的计划
		
		now.set(Calendar.DAY_OF_MONTH, 1);
		workPlan.setSearchStartTimeStr(DateUtil.formatDate(now.getTime()));
		
		now.add(Calendar.MONTH, 1);
		now.set(Calendar.DAY_OF_MONTH, -1);
		workPlan.setSearchEndTimeStr(DateUtil.formatDate(now.getTime()));
		
		ParamPage page = new ParamPage();
		page.setCount(100);
		if (StringUtils.isEmpty(page.getOrderBy())) {
			page.setDir(PageConstant.DIR_ASC);
			page.setOrderBy("executeDate");
		}

		workPlan.setCloseFlag(2);
		workPlan.setCheckFlag(1);
		Page<WorkPlan> workPlanPages = workPlanService.find(workPlan, page.getPageable());

		inv.addModel("workPlans", workPlanPages.getContent());
		
		return viewUrl + "workExecute-add";
	}

	/**
	 * 编辑页面
	 * @param id
	 * @return
	 */
	@Get("workExecute/editPage/{id:[0-9]+}")
	public String workExecute_editPage(@Param("id")String id){
		WorkPlan workPlan = new WorkPlan();
		// 如果是下级机构，则显示本机构数据......此处ing
		User user = SysUserUtils.getCurrentUser(inv);
		if(!user.isSuperior()){
			workPlan.setOrgCode(user.getOrgCode());
		}

		//本月的未关闭的计划
		Calendar now = Calendar.getInstance();
		inv.addModel("todayDate", DateUtil.formatDate(now.getTime()));
		
		now.set(Calendar.DAY_OF_MONTH, 1);
		workPlan.setSearchStartTimeStr(DateUtil.formatDate(now.getTime()));
		
		now.add(Calendar.MONTH, 1);
		now.set(Calendar.DAY_OF_MONTH, -1);
		workPlan.setSearchEndTimeStr(DateUtil.formatDate(now.getTime()));
		
		ParamPage page = new ParamPage();
		if (StringUtils.isEmpty(page.getOrderBy())) {
			page.setDir(PageConstant.DIR_ASC);
			page.setOrderBy("executeDate");
		}

		workPlan.setCloseFlag(2);
		workPlan.setCheckFlag(1);
		Page<WorkPlan> workPlanPages = workPlanService.find(workPlan, page.getPageable());

		inv.addModel("workPlans", workPlanPages.getContent());
		return viewUrl + "workExecute-edit";
	}

	/**
	 * 详情页面
	 * @param id
	 * @return
	 */
	@Get("workExecute/viewPage/{id:[0-9]+}")
	public String workExecute_viewPage(@Param("id")String id){
		WorkExecute workExecute = workExecuteService.findById(id);
		workExecute.setMore();
		
		inv.addModel("dto",workExecute);
		
		return viewUrl + "workExecute-view";
	}

	/**
	 * 列表页面
	 * @param id
	 * @return
	 */
	@Get("workExecute/listPage")
	public String workExecute_listPage(){
		String id = SysUserUtils.getCurrentUser(inv).getId();
		inv.addModel("id",id);
		
		Calendar now = Calendar.getInstance();
		inv.addModel("todayDate", DateUtil.formatDate(now.getTime(), "yyyy-MM-dd"));
		return viewUrl + "workExecute-list";
	}

	/**
	 * 新增
	 * 
	 * @param workExecuteJson
	 * @return
	 */
	@Post("workExecute/save")
	public String workExecute_save(@Param("param") String workExecuteJson) {
		WorkExecute workExecute = JsonUtils.getObject(workExecuteJson,
				WorkExecute.class);
		workExecute.setCreateBy(SysUserUtils.getCurrentUser(inv).getId());
		workExecute.setWorkName(workPlanService.findById(workExecute.getWorkPlanId()).getWorkName());
		
		workExecute.setModel();
		workExecute = workExecuteService.save(workExecute);

		return AjaxUtils.printSuccessJson(workExecute, "操作成功", inv);
	}

	/**
	 * 修改
	 * 
	 * @param workExecuteJson
	 * @return
	 */
//	@Post("workExecute/update")
//	public String workExecute_update(@Param("param") String workExecuteJson) {
//		WorkExecute workExecute = JsonUtils.getObject(workExecuteJson,
//				WorkExecute.class);
//		workExecute.setModel();
//		workExecute = workExecuteService.update(workExecute);
//
//		return AjaxUtils.printSuccessJson(workExecute, "操作成功", inv);
//	}

	/**
	 * 删除
	 * 
	 * @param event
	 * @return
	 */
	@Post("workExecute/delete")
	public String workExecute_delete(@Param("id") String id) {
		workExecuteService.delete(id);
		return AjaxUtils.printSuccessJson(null, "删除成功", inv);
	}


	/**
	 * 查询
	 * 
	 * @param id
	 * @return workExecute
	 */
	@Post("workExecute/load")
	public String workExecute_load(@Param("id") String id) {
		WorkExecute workExecute = workExecuteService.findById(id);
		workExecute.setMore();
		
		return AjaxUtils.printSuccessJson(workExecute, inv);
	}

	/**
	 * 查询
	 * 
	 * @param workExecuteJson
	 *            ,pageJson
	 * @return List<WorkExecute>
	 */
	@Post("workExecute/find")
	public String workExecute_find(@Param("param") String workExecuteJson,
			@Param("page") String pageJson) {
		WorkExecute workExecute = JsonUtils.getObject(workExecuteJson,
				WorkExecute.class);
		// 如果是下级机构，则显示本机构数据......此处ing
		User user = SysUserUtils.getCurrentUser(inv);
		if(!user.isSuperior()){
			workExecute.setOrgCode(user.getOrgCode());
		}
		
		if(StringUtils.isNotEmpty(workExecute.getThisDay())){
			Calendar now = Calendar.getInstance();
			workExecute.setSearchStartTimeStr(DateUtil.formatDate(now.getTime()));
			
			workExecute.setSearchEndTimeStr(DateUtil.formatDate(now.getTime()));
		}else if(StringUtils.isNotEmpty(workExecute.getThisMonth())){
			Calendar now = Calendar.getInstance();
			now.set(Calendar.DAY_OF_MONTH, 1);
			workExecute.setSearchStartTimeStr(DateUtil.formatDate(now.getTime()));
			
			now.add(Calendar.MONTH, 1);
			now.set(Calendar.DAY_OF_MONTH, -1);
			workExecute.setSearchEndTimeStr(DateUtil.formatDate(now.getTime()));
		}
		
		ParamPage page = JsonUtils.getObject(pageJson, ParamPage.class);
		if (StringUtils.isEmpty(page.getOrderBy())) {
			page.setDir(PageConstant.DIR_DESC);
			page.setOrderBy("executeDate");
		}

		Page<WorkExecute> workExecutePages = workExecuteService.find(workExecute, page.getPageable());
		page.setTotal(workExecutePages.getTotalElements());
		List<WorkExecute> workExecutes = workExecutePages.getContent();
		List<WorkExecute> list = new ArrayList<WorkExecute>();
		for (WorkExecute model : workExecutes) {
			model.setMore();
			list.add(model);
		}
		
		
		//今日未实施的
		ParamPage page2 = new ParamPage();
		page2.setCount(100);
		if (StringUtils.isEmpty(page.getOrderBy())) {
			page2.setDir(PageConstant.DIR_ASC);
			page2.setOrderBy("executeDate");
		}
		WorkPlan workPlan = new WorkPlan();
		workPlan.setCloseFlag(3);
		Calendar now = Calendar.getInstance();
		workPlan.setSearchStartTimeStr(DateUtil.formatDate(now.getTime()));
		workPlan.setSearchEndTimeStr(DateUtil.formatDate(now.getTime()));
		
		Page<WorkPlan> workPlanPages = workPlanService.find(workPlan, page2.getPageable());
		List<WorkPlan> workPlans = workPlanPages.getContent();
		List<WorkPlan> listPlan = new ArrayList<WorkPlan>();
		for (WorkPlan model : workPlans) {
			model.setMore();
			listPlan.add(model);
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", list);
		resultMap.put("unExecute", listPlan);
		resultMap.put("page", page);
		
		return AjaxUtils.printSuccessJson(resultMap, inv);
	}
	

	
	/**
	 * 今日导出Excel
	 * @param id
	 * @return
	 */
	@Get("workExecute/exportData")
	public String workExecute_exportData(@Param("param") String workExecuteJson){

		WorkExecute workExecute = JsonUtils.getObject(workExecuteJson,
				WorkExecute.class);
		// 如果是下级机构，则显示本机构数据......此处ing
		User user = SysUserUtils.getCurrentUser(inv);
		if(!user.isSuperior()){
			workExecute.setOrgCode(user.getOrgCode());
		}

		Calendar now = Calendar.getInstance();
		workExecute.setSearchStartTimeStr(DateUtil.formatDate(now.getTime()));
		workExecute.setSearchEndTimeStr(DateUtil.formatDate(now.getTime()));
		
		String pageJson="{count:100000}";
		ParamPage page = JsonUtils.getObject(pageJson, ParamPage.class);
		page.setDir(PageConstant.DIR_ASC);
		page.setOrderBy("executeDate");
		Page<WorkExecute> expPages = workExecuteService.find(workExecute, page.getPageable());

		Calendar now2 = Calendar.getInstance();
		int currMonth = now2.get(Calendar.MONTH)+1;
		int currDay = now2.get(Calendar.DAY_OF_MONTH);
		String exprotName="湖北分公司" + currMonth + "月" + currDay + "日危险作业项目";
		List<WorkExecute> workExecuteList = expPages.getContent();
		exportWorkExecute(exprotName, workExecuteList);
		return null;
	}
	
	/**
	 * 时间段导出Excel
	 * @param id
	 * @return
	 */
	@Get("workExecute/exportData2")
	public String workExecute_exportData2(@Param("startTime") String startTime,@Param("endTime") String endTime){

		WorkExecute workExecute = new WorkExecute();
		workExecute.setSearchStartTimeStr(startTime);
		workExecute.setSearchEndTimeStr(endTime);
		// 如果是下级机构，则显示本机构数据......此处ing
		User user = SysUserUtils.getCurrentUser(inv);
		if(!user.isSuperior()){
			workExecute.setOrgCode(user.getOrgCode());
		}
		
		String pageJson="{count:100000}";
		ParamPage page = JsonUtils.getObject(pageJson, ParamPage.class);
		page.setDir(PageConstant.DIR_ASC);
		page.setOrderBy("executeDate");
		Page<WorkExecute> expPages = workExecuteService.find(workExecute, page.getPageable());

		String exprotName="湖北分公司" + startTime + "至" + endTime + "危险作业项目";
		List<WorkExecute> workExecuteList = expPages.getContent();
		exportWorkExecute(exprotName, workExecuteList);
		return null;
	}
	
	
	public String exportWorkExecute(String exprotName, List<WorkExecute> workExecuteList){
		PrintWriter out = null;
		HttpServletResponse response=inv.getResponse();
		response.setCharacterEncoding("UTF-8");
		try {
			String[] title = new String []{"序号","日期","公司名称","危险作业项目名称","作业级别","作业开始时间","作业关闭时间","工程概况","是否进行方案学习交底","作业审批人","作业复核人","现场指挥","作业进展","备注"};
			String[] width = new String []{"5","12","12","12","12","12","12","12","12","12","12","12","12","12"};
			out=response.getWriter();
			List<Object[]> listObj=new ArrayList<Object[]>();
			
			if (workExecuteList!=null && workExecuteList.size()>0) {
				for (WorkExecute data : workExecuteList) {
					Object[] obj =new Object[title.length-1];
					int i=0;
					obj[i++] = DateUtil.formatDate(data.getExecuteDate());
					obj[i++] =data.getOrgName();
					obj[i++] =data.getWorkName();
					obj[i++] =data.getWorkLevel();
					obj[i++] =data.getBeginTime();
					obj[i++] =data.getEndTime();
					obj[i++] =data.getSummary();
					obj[i++] =data.getExaminer();
					obj[i++] =data.getLearnFlag();
					obj[i++] =data.getChecker();
					obj[i++] =data.getDirector();
					obj[i++] =data.getExecuteState();
					obj[i++] =data.getNotes();
					
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
