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
import com.gsafety.starscream.project.model.ProduceExecute;
import com.gsafety.starscream.project.model.ProducePlan;
import com.gsafety.starscream.project.service.ProduceExecuteService;
import com.gsafety.starscream.project.service.ProducePlanService;
import com.gsafety.starscream.project.util.ExportExcel4Project;
import com.gsafety.starscream.utils.SysUserUtils;
import com.gsafety.starscream.utils.format.DateUtil;
import com.gsafety.starscream.utils.page.ParamPage;
import com.gsafety.util.json.JsonUtils;

/**
 * 试运投产项目-实施（今日录入）
 * 
 * @author wanghui
 * @date 2016-1-1
 */
@Path("project")
public class ProduceExecuteController {
	@Autowired
	private Invocation inv;

	@Autowired
	private ProduceExecuteService produceExecuteService;

	@Autowired
	private ProducePlanService producePlanService;

	@Autowired
	AttachService attachService;


	String viewUrl = "project/produceExecute/";
	
	
	/**
	 * 添加页面
	 * @return
	 */
	@Get("produceExecute/addPage")
	public String produceExecute_addPage(@Param("workPlanId")String workPlanId){

		Calendar now = Calendar.getInstance();
		inv.addModel("todayDate", DateUtil.formatDate(now.getTime()));
		
		if(StringUtils.isNotEmpty(workPlanId)){
			ProducePlan producePlanValue = producePlanService.findById(workPlanId);
			inv.addModel("producePlan", producePlanValue);
			return viewUrl + "produceExecute-add4";
		}
		
		ProducePlan producePlan = new ProducePlan();
		// 如果是下级机构，则显示本机构数据......此处ing
		User user = SysUserUtils.getCurrentUser(inv);
		if(!user.isSuperior()){
			producePlan.setOrgCode(user.getOrgCode());
		}

		//本月的未关闭的计划
		
		now.set(Calendar.DAY_OF_MONTH, 1);
		producePlan.setSearchStartTimeStr(DateUtil.formatDate(now.getTime()));
		
		now.add(Calendar.MONTH, 1);
		now.set(Calendar.DAY_OF_MONTH, -1);
		producePlan.setSearchEndTimeStr(DateUtil.formatDate(now.getTime()));
		
		ParamPage page = new ParamPage();
		page.setCount(100);
		if (StringUtils.isEmpty(page.getOrderBy())) {
			page.setDir(PageConstant.DIR_ASC);
			page.setOrderBy("executeDate");
		}

		producePlan.setCloseFlag(2);
		producePlan.setCheckFlag(1);
		Page<ProducePlan> producePlanPages = producePlanService.find(producePlan, page.getPageable());

		inv.addModel("producePlans", producePlanPages.getContent());
		
		return viewUrl + "produceExecute-add";
	}

	/**
	 * 编辑页面
	 * @param id
	 * @return
	 */
	@Get("produceExecute/editPage/{id:[0-9]+}")
	public String produceExecute_editPage(@Param("id")String id){
		ProducePlan producePlan = new ProducePlan();
		// 如果是下级机构，则显示本机构数据......此处ing
		User user = SysUserUtils.getCurrentUser(inv);
		if(!user.isSuperior()){
			producePlan.setOrgCode(user.getOrgCode());
		}

		//本月的未关闭的计划
		Calendar now = Calendar.getInstance();
		inv.addModel("todayDate", DateUtil.formatDate(now.getTime()));
		
		now.set(Calendar.DAY_OF_MONTH, 1);
		producePlan.setSearchStartTimeStr(DateUtil.formatDate(now.getTime()));
		
		now.add(Calendar.MONTH, 1);
		now.set(Calendar.DAY_OF_MONTH, -1);
		producePlan.setSearchEndTimeStr(DateUtil.formatDate(now.getTime()));
		
		ParamPage page = new ParamPage();
		if (StringUtils.isEmpty(page.getOrderBy())) {
			page.setDir(PageConstant.DIR_ASC);
			page.setOrderBy("executeDate");
		}

		producePlan.setCloseFlag(2);
		producePlan.setCheckFlag(1);
		Page<ProducePlan> producePlanPages = producePlanService.find(producePlan, page.getPageable());

		inv.addModel("producePlans", producePlanPages.getContent());
		return viewUrl + "produceExecute-edit";
	}

	/**
	 * 详情页面
	 * @param id
	 * @return
	 */
	@Get("produceExecute/viewPage/{id:[0-9]+}")
	public String produceExecute_viewPage(@Param("id")String id){
		ProduceExecute produceExecute = produceExecuteService.findById(id);
		produceExecute.setMore();
		
		inv.addModel("dto",produceExecute);
		
		return viewUrl + "produceExecute-view";
	}

	/**
	 * 列表页面
	 * @param id
	 * @return
	 */
	@Get("produceExecute/listPage")
	public String produceExecute_listPage(){
		String id = SysUserUtils.getCurrentUser(inv).getId();
		inv.addModel("id",id);
		
		Calendar now = Calendar.getInstance();
		inv.addModel("todayDate", DateUtil.formatDate(now.getTime(), "yyyy-MM-dd"));
		return viewUrl + "produceExecute-list";
	}

	/**
	 * 新增
	 * 
	 * @param produceExecuteJson
	 * @return
	 */
	@Post("produceExecute/save")
	public String produceExecute_save(@Param("param") String produceExecuteJson) {
		ProduceExecute produceExecute = JsonUtils.getObject(produceExecuteJson,
				ProduceExecute.class);
		produceExecute.setCreateBy(SysUserUtils.getCurrentUser(inv).getId());
		produceExecute.setWorkName(producePlanService.findById(produceExecute.getWorkPlanId()).getWorkName());
		
		produceExecute.setModel();
		produceExecute = produceExecuteService.save(produceExecute);

		return AjaxUtils.printSuccessJson(produceExecute, "操作成功", inv);
	}

	/**
	 * 修改
	 * 
	 * @param produceExecuteJson
	 * @return
	 */
//	@Post("produceExecute/update")
//	public String produceExecute_update(@Param("param") String produceExecuteJson) {
//		ProduceExecute produceExecute = JsonUtils.getObject(produceExecuteJson,
//				ProduceExecute.class);
//		produceExecute.setModel();
//		produceExecute = produceExecuteService.update(produceExecute);
//
//		return AjaxUtils.printSuccessJson(produceExecute, "操作成功", inv);
//	}

	/**
	 * 删除
	 * 
	 * @param event
	 * @return
	 */
	@Post("produceExecute/delete")
	public String produceExecute_delete(@Param("id") String id) {
		produceExecuteService.delete(id);
		return AjaxUtils.printSuccessJson(null, "删除成功", inv);
	}


	/**
	 * 查询
	 * 
	 * @param id
	 * @return produceExecute
	 */
	@Post("produceExecute/load")
	public String produceExecute_load(@Param("id") String id) {
		ProduceExecute produceExecute = produceExecuteService.findById(id);
		produceExecute.setMore();
		
		return AjaxUtils.printSuccessJson(produceExecute, inv);
	}

	/**
	 * 查询
	 * 
	 * @param produceExecuteJson
	 *            ,pageJson
	 * @return List<ProduceExecute>
	 */
	@Post("produceExecute/find")
	public String produceExecute_find(@Param("param") String produceExecuteJson,
			@Param("page") String pageJson) {
		ProduceExecute produceExecute = JsonUtils.getObject(produceExecuteJson,
				ProduceExecute.class);
		// 如果是下级机构，则显示本机构数据......此处ing
		User user = SysUserUtils.getCurrentUser(inv);
		if(!user.isSuperior()){
			produceExecute.setOrgCode(user.getOrgCode());
		}
		
		if(StringUtils.isNotEmpty(produceExecute.getThisDay())){
			Calendar now = Calendar.getInstance();
			produceExecute.setSearchStartTimeStr(DateUtil.formatDate(now.getTime()));
			
			produceExecute.setSearchEndTimeStr(DateUtil.formatDate(now.getTime()));
		}else if(StringUtils.isNotEmpty(produceExecute.getThisMonth())){
			Calendar now = Calendar.getInstance();
			now.set(Calendar.DAY_OF_MONTH, 1);
			produceExecute.setSearchStartTimeStr(DateUtil.formatDate(now.getTime()));
			
			now.add(Calendar.MONTH, 1);
			now.set(Calendar.DAY_OF_MONTH, -1);
			produceExecute.setSearchEndTimeStr(DateUtil.formatDate(now.getTime()));
		}
		
		ParamPage page = JsonUtils.getObject(pageJson, ParamPage.class);
		if (StringUtils.isEmpty(page.getOrderBy())) {
			page.setDir(PageConstant.DIR_DESC);
			page.setOrderBy("executeDate");
		}

		Page<ProduceExecute> produceExecutePages = produceExecuteService.find(produceExecute, page.getPageable());
		page.setTotal(produceExecutePages.getTotalElements());
		List<ProduceExecute> produceExecutes = produceExecutePages.getContent();
		List<ProduceExecute> list = new ArrayList<ProduceExecute>();
		for (ProduceExecute model : produceExecutes) {
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
		ProducePlan producePlan = new ProducePlan();
		producePlan.setCloseFlag(3);
		Calendar now = Calendar.getInstance();
		producePlan.setSearchStartTimeStr(DateUtil.formatDate(now.getTime()));
		producePlan.setSearchEndTimeStr(DateUtil.formatDate(now.getTime()));
		
		Page<ProducePlan> producePlanPages = producePlanService.find(producePlan, page2.getPageable());
		List<ProducePlan> producePlans = producePlanPages.getContent();
		List<ProducePlan> listPlan = new ArrayList<ProducePlan>();
		for (ProducePlan model : producePlans) {
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
	@Get("produceExecute/exportData")
	public String produceExecute_exportData(@Param("param") String produceExecuteJson){

		ProduceExecute produceExecute = JsonUtils.getObject(produceExecuteJson,
				ProduceExecute.class);
		// 如果是下级机构，则显示本机构数据......此处ing
		User user = SysUserUtils.getCurrentUser(inv);
		if(!user.isSuperior()){
			produceExecute.setOrgCode(user.getOrgCode());
		}

		Calendar now = Calendar.getInstance();
		produceExecute.setSearchStartTimeStr(DateUtil.formatDate(now.getTime()));
		produceExecute.setSearchEndTimeStr(DateUtil.formatDate(now.getTime()));
		
		String pageJson="{count:100000}";
		ParamPage page = JsonUtils.getObject(pageJson, ParamPage.class);
		page.setDir(PageConstant.DIR_ASC);
		page.setOrderBy("executeDate");
		Page<ProduceExecute> expPages = produceExecuteService.find(produceExecute, page.getPageable());

		Calendar now2 = Calendar.getInstance();
		int currMonth = now2.get(Calendar.MONTH)+1;
		int currDay = now2.get(Calendar.DAY_OF_MONTH);
		String exprotName="湖北分公司" + currMonth + "月" + currDay + "日试运投产项目";
		List<ProduceExecute> produceExecuteList = expPages.getContent();
		exportProduceExecute(exprotName, produceExecuteList);
		return null;
	}
	
	/**
	 * 时间段导出Excel
	 * @param id
	 * @return
	 */
	@Get("produceExecute/exportData2")
	public String produceExecute_exportData2(@Param("startTime") String startTime,@Param("endTime") String endTime){

		ProduceExecute produceExecute = new ProduceExecute();
		produceExecute.setSearchStartTimeStr(startTime);
		produceExecute.setSearchEndTimeStr(endTime);
		// 如果是下级机构，则显示本机构数据......此处ing
		User user = SysUserUtils.getCurrentUser(inv);
		if(!user.isSuperior()){
			produceExecute.setOrgCode(user.getOrgCode());
		}
		
		String pageJson="{count:100000}";
		ParamPage page = JsonUtils.getObject(pageJson, ParamPage.class);
		page.setDir(PageConstant.DIR_ASC);
		page.setOrderBy("executeDate");
		Page<ProduceExecute> expPages = produceExecuteService.find(produceExecute, page.getPageable());

		String exprotName="湖北分公司" + startTime + "至" + endTime + "试运投产项目";
		List<ProduceExecute> produceExecuteList = expPages.getContent();
		exportProduceExecute(exprotName, produceExecuteList);
		return null;
	}
	
	
	public String exportProduceExecute(String exprotName, List<ProduceExecute> produceExecuteList){
		PrintWriter out = null;
		HttpServletResponse response=inv.getResponse();
		response.setCharacterEncoding("UTF-8");
		try {
			String[] title = new String []{"序号","日期","公司名称","试运投产项目名称","作业级别","作业开始时间","作业关闭时间","工程概况","是否进行方案学习交底","作业审批人","作业复核人","现场指挥","作业进展","备注"};
			String[] width = new String []{"5","12","12","12","12","12","12","12","12","12","12","12","12","12"};
			out=response.getWriter();
			List<Object[]> listObj=new ArrayList<Object[]>();
			
			if (produceExecuteList!=null && produceExecuteList.size()>0) {
				for (ProduceExecute data : produceExecuteList) {
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
