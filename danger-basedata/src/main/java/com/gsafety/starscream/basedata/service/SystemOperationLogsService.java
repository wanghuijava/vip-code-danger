package com.gsafety.starscream.basedata.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.paoding.rose.web.Invocation;

import com.gsafety.starscream.basedata.model.SystemOperationLogs;
import com.gsafety.starscream.basedata.view.SystemOperationLogsDTO;

/**
 *  @author Zzj
 * @date 2015-10-27 下午3:58:33
 * @history V1.0
 */
public interface SystemOperationLogsService {

	/**
	 * 
	 *  @author Zzj
	 * @date 2015-10-27 下午4:08:37
	 * @declare 
	 * @param opeationMap
	 * opeationMap's key operationType and funName not null
	 * opeationMap's key detail and operationUrl 
	 * eg.
	 * Map<String, String> map = new HashMap<String,String>
	 * map.put("operationType","");
	 * map.put("funName","");
	 * map.put("operationUrl","");
	 * map.put("detail","");
	 * @param ivn
	 */
	public String addLogs(Map<String, String> opeationMap,Invocation inv);
	
	
	public Page<SystemOperationLogs> findListByTime(SystemOperationLogsDTO dto,Pageable page); 
}

