package com.gsafety.starscream.basedata.view;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.gsafety.starscream.basedata.model.SystemOperationLogs;

/**
 * @author Zzj
 * @date 2015-10-27 下午3:37:41
 * @history V1.0
 */
public class SystemOperationLogsDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String operationType; // 操作类型[登录/登出/模块名]
	private Date createTime; // 操作时间
	private String operator; // 操作人
	private String detail; // 详细内容
	private String url; //操作路径 
	private String funName; // 操作功能 名称
	
	private Date startTime;
	private String startTimeStr;
	private Date endTime;
	private String endTimeStr;
	
	
	public SystemOperationLogsDTO() {
		// zzj Auto-generated constructor stub
	}
	
	
	public SystemOperationLogsDTO(SystemOperationLogs model) {
		BeanUtils.copyProperties(model, this);
	}
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFunName() {
		return funName;
	}

	public void setFunName(String funName) {
		this.funName = funName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	
}
