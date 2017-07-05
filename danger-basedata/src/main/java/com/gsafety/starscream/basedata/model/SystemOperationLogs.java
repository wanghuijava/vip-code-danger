package com.gsafety.starscream.basedata.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Zzj
 * @date 2015-10-27 下午3:37:41
 * @history V1.0
 */
@Entity
@Table(name = "BAS_SYS_OPERATIONLOG")
public class SystemOperationLogs implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private String id;
	@Column(name = "OPERATIONTYPE")
	private String operationType; // 操作类型[登录/登出/模块名]
	@Column(name = "OPERATIONTIME")
	private Date createTime = new Date(); // 操作时间
	@Column(name = "OPERATOR")
	private String operator; // 操作人
	@Column(name = "OPERATIONDETAIL")
	private String detail; // 详细内容
	@Column(name = "OPERATIONURL")
	private String url;
	@Column(name = "OPERATORFUNNAME")
	private String funName; // 操作功能 名称
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
	 
}
