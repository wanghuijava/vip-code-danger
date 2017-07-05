package com.gsafety.starscream.project.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gsafety.common.utils.AjaxUtils;
import com.gsafety.starscream.basedata.model.Attach;
import com.gsafety.starscream.basedata.model.User;
import com.gsafety.starscream.basedata.service.AttachService;
import com.gsafety.starscream.constant.CodeConstant;
import com.gsafety.starscream.project.model.ProducePlan;
import com.gsafety.starscream.project.service.ProducePlanService;
import com.gsafety.starscream.utils.AttachUtils;
import com.gsafety.starscream.utils.CodesUtils;
import com.gsafety.starscream.utils.SysUserUtils;
import com.gsafety.starscream.utils.ValidExcelUtil;
import com.gsafety.starscream.utils.format.DateUtil;

/**
 * 试运投产项目-计划
 * 
 * @author wanghui
 * @date 2016-2-23
 */
@Path("project")
public class ProducePlanImportController {
	@Autowired
	private Invocation inv;

	@Autowired
	private ProducePlanService producePlanService;

	@Autowired
	AttachService attachService;



	/**
	 * 数据导入
	 * @param resourceType
	 * @param attachId
	 * @return
	 */
	@Post("producePlan/import")
	public String excel_import(
			@Param("resourceType")String resourceType, 
			@Param("attachId")String attachId){
		
		if(StringUtils.isEmpty(attachId)){
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM, "请上传要导入的Excle文件！", inv);
		}
		//获取附件
		Attach attach = attachService.findById(attachId);
		if(attach==null) {
			inv.addModel("resultMsg","no such file!!!");
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM, "no such file!!!", inv);
		}
		
		// 解析数据
		try {
			String savePath = AttachUtils.fileRootDir;
			File template = new File(savePath+attach.getAttachPath());
			Workbook wb = Workbook.getWorkbook(template);
			Sheet sheet = wb.getSheet(0);
			// 进行批量导入
			String msg = saveDatas(sheet);
			if(msg == null){
				return AjaxUtils.printSuccessJson(null, msg, inv);
			}
			
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM, msg, inv);
			
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxUtils.printErrorJson(CodeConstant.ERROR_PARAM, "操作失败！", inv);
		}
		
	}
	


	/**
	 * 进行批量导入的方法
	 * 
	 * @param sheet
	 * @param ctx
	 */
	private String saveDatas(Sheet sheet) {
		String clew = null;
		//验证规则 notnull:0；1:mobile；2:tel；3:fax；4:date；5:int；6:float；7:email；8:idcard;
		String[] columnNames = {"公司名称", "名称", "作业级别", "工程概况", "计划实施时间（格式：2016-01-01）", "作业审批人", "作业复核人", "现场指挥"};
		int[][] columnRules = {{0},       {0},            {},       {0},       {0,4},                        {},          {},       {}};
		User user = SysUserUtils.getCurrentUser(inv);
		boolean supFlag = user.isSuperior();
		//主管单位
		String orgCodeT = user.getOrgCode();
		String orgNameT = user.getOrgName();
		
		//下月
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH, 1);
		now.set(Calendar.DATE, 1);
		now.add(Calendar.DATE, -1);
		Date begin = now.getTime();
		now.add(Calendar.MONTH, 2);
		now.set(Calendar.DATE, 1);
		now.add(Calendar.DATE, -1);
		Date end = now.getTime();
		
		if(!supFlag){
			columnNames = new String[]{"试运投产项目名称", "作业级别", "工程概况", "计划实施时间（格式：2016-01-01）", "作业审批人", "作业复核人", "现场指挥"};
			columnRules = new int[][]{{0},            {},       {0},       {0,4},                        {},          {},       {}};
		}
		int col = 0;
		List<ProducePlan> list = new ArrayList<ProducePlan>();
		// 判断能否执行插入
		int rowsNum = sheet.getRows();
		for (int i = 1; i <= rowsNum; i++) {
			ProducePlan producePlan = new ProducePlan();
			if (StringUtils.isEmpty(sheet.getCell(0, i).getContents()) && StringUtils.isEmpty(sheet.getCell(0, i+1).getContents())) {
//				clew = "第" + (i + 1) + "行为空！";
//				state = "1";
				break;
			}
			
			if(supFlag){
				// 公司名称
				col = 0;
				String orgName = ValidExcelUtil.getCellString(sheet.getCell(col, i));
				clew = ValidExcelUtil.checkValue(i, columnNames[col], orgName, 10, columnRules[col]);
				if (clew != null) {
					break;
				}
				producePlan.setOrgName(orgName);
				producePlan.setOrgCode(CodesUtils.getOrgCodeByOrgName(orgName));
			}else{
				producePlan.setOrgName(orgNameT);
				producePlan.setOrgCode(orgCodeT);
			}

			// 试运投产项目名称
			col = 0;
			if(supFlag){
				col += 1;
			}
			String workName = ValidExcelUtil.getCellString(sheet.getCell(col, i));
			clew = ValidExcelUtil.checkValue(i, columnNames[col], workName, 20, columnRules[col]);
			if (clew != null) {
				break;
			}
			producePlan.setWorkName(workName);

			// 作业级别
			col += 1;
			String workLevel = ValidExcelUtil.getCellString(sheet.getCell(col, i));
			clew = ValidExcelUtil.checkValue(i, columnNames[col], workLevel, 0, columnRules[col]);
			if (clew != null) {
				break;
			} 
			if(StringUtils.isNotEmpty(workLevel)){
				producePlan.setWorkLevel(workLevel);
			}
			
			// 工程概况
			col += 1;
			String summary = ValidExcelUtil.getCellString(sheet.getCell(col, i));
			clew = ValidExcelUtil.checkValue(i, columnNames[col], summary, 500, columnRules[col]);
			if (clew != null) {
				break;
			}
			producePlan.setSummary(summary);

			// 计划实施时间（格式：2013-01-01）
			col += 1;
			String executeDate = ValidExcelUtil.getCellString(sheet.getCell(col, i));
			clew = ValidExcelUtil.checkValue(i, columnNames[col], executeDate, 0, columnRules[col]);
			if (clew != null) {
				break;
			}
			producePlan.setExecuteDate(DateUtil.getDate(executeDate));
			//下级要验证计划的时间
			if(!supFlag && (producePlan.getExecuteDate().before(begin) || producePlan.getExecuteDate().after(end))){
				clew = "第" + (i + 1) + "行 "+columnNames[col]+"必须是下个月的某一天！";
				break;
			}
			
			// 作业审批人
			col += 1;
			String examiner = ValidExcelUtil.getCellString(sheet.getCell(col, i));
			clew = ValidExcelUtil.checkValue(i, columnNames[col], examiner, 10, columnRules[col]);
			if (clew != null) {
				break;
			}
			producePlan.setExaminer(examiner);
			
			// 作业复核人
			col += 1;
			String checker = ValidExcelUtil.getCellString(sheet.getCell(col, i));
			clew = ValidExcelUtil.checkValue(i, columnNames[col], checker, 10, columnRules[col]);
			if (clew != null) {
				break;
			}
			producePlan.setChecker(checker);
			
			// 现场指挥
			col += 1;
			String director = ValidExcelUtil.getCellString(sheet.getCell(col, i));
			clew = ValidExcelUtil.checkValue(i, columnNames[col], director, 10, columnRules[col]);
			if (clew != null) {
				break;
			}
			producePlan.setDirector(director);
			
			//添加对象
			list.add(producePlan);
		}

		// 确定是否有可以全部添加
		if (clew == null) {
			for (ProducePlan model : list) {
				if(supFlag){
					model.setCheckFlag(1);
				}
				model.setCreateBy(user.getId());
				producePlanService.save(model);
			}
		}

		return clew;
	}
	
}
