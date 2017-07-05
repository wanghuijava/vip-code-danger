package com.gsafety.starscream.basedata.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 事件类型model
* @author 朱泽江
* @date 2015-1-14 下午12:55:28
* @declare 
*/
@Entity
@Table(name="BAS_EVENT_TYPE")
public class EventType implements Serializable{

	private static final long serialVersionUID = -1506808400987057375L;
	
	@Id
	@Column(name="EVENTTYPECODE")
	private String eventTypeCode;         //事件类型ID 【主键】
	
	@Column(name="EVENTTYPENAME")
	private String eventTypeName;        //事件类型名称
	
	@Column(name="PARENTCODE")
	private String parentCode;   //事件类型父级ID
	
	@Column(name="SORTNUM")
	private String sortNum;        //事件类型排序
	
	@Column(name="COMMON")
	private String common;        //事件类型是否为常用 默认为0,常用为1

	@Column(name="DEFAULT_EPP_MANAGE")
	private String defaultEpp;  // 事件默认处理预案信息
	
	@Column(name="NOTES")
	private String notes;        //事件类型名称
	
	@Transient
	private List<EventType> children;     //子级事件类型
	
	
	public String getEventTypeCode() {
		return eventTypeCode;
	}

	public void setEventTypeCode(String eventTypeCode) {
		this.eventTypeCode = eventTypeCode;
	}
	
	public String getEventTypeName() {
		return eventTypeName;
	}

	public void setEventTypeName(String eventTypeName) {
		this.eventTypeName = eventTypeName;
	}
	
	
	public List<EventType> getChildren() {
		return children;
	}

	public void setChildren(List<EventType> children) {
		this.children = children;
	}
	
	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getSortNum() {
		return sortNum;
	}

	public void setSortNum(String sortNum) {
		this.sortNum = sortNum;
	}

	public String isCommon() {
		return common;
	}

	public void setCommon(String common) {
		this.common = common;
	}

	public String getDefaultEpp() {
		return defaultEpp;
	}

	public void setDefaultEpp(String defaultEpp) {
		this.defaultEpp = defaultEpp;
	}
 
	
}
