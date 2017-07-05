package com.gsafety.starscream.project.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.gsafety.starscream.utils.format.DateUtil;

/**
 * 危险作业项目-计划
 * 
 * @author wanghui
 * @date 2016-1-1
 */
@Entity
@Table(name = "prj_workplan")
public class WorkPlan implements Serializable {

	private static final long serialVersionUID = -4457102585104442118L;

	/**
	 * 主键id 编号
	 */
	@Id
	@Column(name = "ID")
	private String id;

	/**
	 * 公司Code
	 */
	@Column(name = "orgCode")
	private String orgCode;

	/**
	 * 公司名称
	 */
	@Column(name = "orgName")
	private String orgName;

	/**
	 * 危险作业项目名称
	 */
	@Column(name = "workName")
	private String workName;

	/**
	 * 作业级别
	 */
	@Column(name = "workLevel")
	private String workLevel;

	/**
	 * 工程概况
	 */
	@Column(name = "summary")
	private String summary;

	/**
	 * 计划实施时间
	 */
	@Column(name = "executeDate")
	private Date executeDate;

	/**
	 * 作业审批人
	 */
	@Column(name = "examiner")
	private String examiner;

	/**
	 * 作业复核人
	 */
	@Column(name = "checker")
	private String checker;

	/**
	 * 现场指挥
	 */
	@Column(name = "director")
	private String director;

	/**
	 * 备注
	 */
	@Column(name = "notes")
	private String notes;

	/**
	 * 创建人
	 */
	@Column(name = "createBy")
	private String createBy;

	/**
	 * 创建时间
	 */
	@Column(name = "createTime")
	private Date createTime;

	/**
	 * 更新时间
	 */
	@Column(name = "updateTime")
	private Date updateTime;
	
	/**
	 * 是否关闭
	 */
	@Column(name = "closeFlag")
	private int closeFlag;
	
	/**
	 * 是否已审核（0：待审核；1：已上报）
	 */
	@Column(name = "checkFlag")
	private int checkFlag;
	
	@Transient
	private String checkFlagStr;//（0：待审核；1：已上报）

	@Transient
	private String thisMonth; // 是否是下个月的计划

	@Transient
	private String nextMonth; // 是否是下个月的计划

	@Transient
	private String planMonth; // 计划的月份

	@Transient
	private String executeDateStr; // 是否是下个月的计划
	
	@Transient
	private String searchStartTimeStr; // 查询开始时间

	@Transient
	private String searchEndTimeStr; // 查询结束时间

	public WorkPlan() {

	}
	public WorkPlan(String id) {
		this.id = id;
	}

	public void setMore() {
		this.setExecuteDateStr(DateUtil.formatDate(this.getExecuteDate()));
		this.setPlanMonth(DateUtil.formatDate(this.getExecuteDate(), "yyyy-MM"));
		this.setCheckFlagStr(this.getCheckFlag()==1?"已上报":"待审核");
	}

	public void setModel() {
		this.setExecuteDate(DateUtil.getDate(this.getExecuteDateStr()));
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public String getWorkLevel() {
		return workLevel;
	}

	public void setWorkLevel(String workLevel) {
		this.workLevel = workLevel;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Date getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}

	public String getExaminer() {
		return examiner;
	}

	public void setExaminer(String examiner) {
		this.examiner = examiner;
	}

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getCloseFlag() {
		return closeFlag;
	}

	public void setCloseFlag(int closeFlag) {
		this.closeFlag = closeFlag;
	}

	public int getCheckFlag() {
		return checkFlag;
	}
	public void setCheckFlag(int checkFlag) {
		this.checkFlag = checkFlag;
	}
	
	public String getCheckFlagStr() {
		return checkFlagStr;
	}
	public void setCheckFlagStr(String checkFlagStr) {
		this.checkFlagStr = checkFlagStr;
	}
	public String getPlanMonth() {
		return planMonth;
	}

	public void setPlanMonth(String planMonth) {
		this.planMonth = planMonth;
	}

	public String getThisMonth() {
		return thisMonth;
	}

	public void setThisMonth(String thisMonth) {
		this.thisMonth = thisMonth;
	}

	public String getNextMonth() {
		return nextMonth;
	}

	public void setNextMonth(String nextMonth) {
		this.nextMonth = nextMonth;
	}

	public String getExecuteDateStr() {
		return executeDateStr;
	}

	public String getExecuteDateShow() {
		return DateUtil.formatDate(this.executeDate);
	}

	public void setExecuteDateStr(String executeDateStr) {
		this.executeDateStr = executeDateStr;
	}

	public String getSearchStartTimeStr() {
		return searchStartTimeStr;
	}

	public void setSearchStartTimeStr(String searchStartTimeStr) {
		this.searchStartTimeStr = searchStartTimeStr;
	}

	public String getSearchEndTimeStr() {
		return searchEndTimeStr;
	}

	public void setSearchEndTimeStr(String searchEndTimeStr) {
		this.searchEndTimeStr = searchEndTimeStr;
	}
	

}
