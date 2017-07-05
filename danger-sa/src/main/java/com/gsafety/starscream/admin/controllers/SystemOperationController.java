package com.gsafety.starscream.admin.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.gsafety.common.utils.AjaxUtils;
import com.gsafety.starscream.basedata.model.SystemOperationLogs;
import com.gsafety.starscream.basedata.service.SystemOperationLogsService;
import com.gsafety.starscream.basedata.view.SystemOperationLogsDTO;
import com.gsafety.starscream.constant.BasedataConstant;
import com.gsafety.starscream.constant.PageConstant;
import com.gsafety.starscream.utils.format.DateUtil;
import com.gsafety.starscream.utils.page.ParamPage;
import com.gsafety.util.json.JsonUtils;
 /**
 * @author 朱泽江
 * @since 2015-11-9 上午10:41:33
 * @version v0.0.1
 * @declare 
 */
@Path("admin/systemOperation")
public class SystemOperationController {

	@Autowired
	private Invocation inv;
	
	@Autowired
	private SystemOperationLogsService service;
	
	@Get("operation/find")
	@Post("operation/find")
	public String operation_findByType(@Param("param")String jsonStr,
			@Param("page") String pageJson){

		
		SystemOperationLogsDTO dto = JsonUtils.getObject(jsonStr, SystemOperationLogsDTO.class);
		String operation_type = "login".equals(dto.getOperationType())?BasedataConstant.SYSTEM_LOG:BasedataConstant.OPERATION_LOG;
		
		if(StringUtils.isNotEmpty(dto.getStartTimeStr())){
			dto.setStartTime(DateUtil.getDate(dto.getStartTimeStr() +" 00:00:00"));
		}
		if(StringUtils.isNotEmpty(dto.getEndTimeStr())){
			dto.setEndTime(DateUtil.getDate(dto.getEndTimeStr() + " 23:59:59"));
		}
		
		 dto.setOperationType(operation_type);
//		 dto.setStartTime(startTime);
//		 dto.setEndTime(endTime);
		 
		ParamPage page = JsonUtils.getObject(pageJson, ParamPage.class);
		page.setOrderBy("createTime");
		page.setDir(PageConstant.DIR_DESC);
		
		
		Page<SystemOperationLogs> pageList = service.findListByTime(dto, page.getPageable());
		page.setTotal(pageList.getTotalElements());
		List<SystemOperationLogs> list = pageList.getContent();
		
		List<SystemOperationLogsDTO> dtoList = new ArrayList<SystemOperationLogsDTO>();
		for(SystemOperationLogs model : list){
			dtoList.add(new SystemOperationLogsDTO(model));
		}
		
//		inv.addModel("dtoList",dtoList);
//		inv.addModel("page",page);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("page", page);
		resultMap.put("dtoList", dtoList);
		
		return AjaxUtils.printSuccessJson(resultMap, inv);
	}
	
	@Get("operation/index")
	public String logs_index(@Param("operationType")String operationType){
		inv.addModel("operationType",operationType);
		return "authority/operationlogs-list";
	}
}
