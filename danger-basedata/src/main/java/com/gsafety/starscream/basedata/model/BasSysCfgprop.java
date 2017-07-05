package com.gsafety.starscream.basedata.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 系统属性配置
 * @author dzg
 * 
 */
@Entity
@Table(name="BAS_SYS_CFGPROP")
public class BasSysCfgprop implements Serializable{

	private static final long serialVersionUID = -1267191597040799245L;
	//属性编号
	@Id
	@Column(name="PROPID")
	private String propId;
	//属性名称
	@Column(name="PROPNAME")
	private String propName;
	//属性值
	@Column(name="PROPVALUE")
	private String propValue;
	//属性描述
	@Column(name="PROPDESC")
	private String propDesc;
	public String getPropId() {
		return propId;
	}
	public void setPropId(String propId) {
		this.propId = propId;
	}
	public String getPropName() {
		return propName;
	}
	public void setPropName(String propName) {
		this.propName = propName;
	}
	public String getPropValue() {
		return propValue;
	}
	public void setPropValue(String propValue) {
		this.propValue = propValue;
	}
	public String getPropDesc() {
		return propDesc;
	}
	public void setPropDesc(String propDesc) {
		this.propDesc = propDesc;
	}
	public BasSysCfgprop() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
