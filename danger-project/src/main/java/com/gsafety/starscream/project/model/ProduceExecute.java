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
 * 试运投产项目-实施（今日录入）
 * 
 * @author wanghui
 * @date 2016-1-1
 */
@Entity
@Table(name = "prj_produceexecute")
public class ProduceExecute implements Serializable {

	private static final long serialVersionUID = 6419715623776674913L;

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
	 * 试运投产项目计划ID
	 */
	@Column(name = "workPlanId")
	private String workPlanId;

	/**
	 * 试运投产项目名称
	 */
	@Column(name = "workName")
	private String workName;

	/**
	 * 作业级别
	 */
	@Column(name = "workLevel")
	private String workLevel;

	/**
	 * 作业开始时间
	 */
	@Column(name = "beginTime")
	private String beginTime;

	/**
	 * 作业关闭时间
	 */
	@Column(name = "endTime")
	private String endTime;

	/**
	 * 工程概况
	 */
	@Column(name = "summary")
	private String summary;

	/**
	 * 是否进行方案学习交底
	 */
	@Column(name = "learnFlag")
	private String learnFlag;

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
	 * 作业进展
	 */
	@Column(name = "executeState")
	private String executeState;

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

	@Transient
	private String thisMonth; // 是否是这个月的项目

	@Transient
	private String thisDay; // 是否是今天的计划

	@Transient
	private String execDay; // 执行的日期

	@Transient
	private String executeDateStr; // 是否是下个月的计划
	
	@Transient
	private String searchStartTimeStr; // 查询开始时间

	@Transient
	private String searchEndTimeStr; // 查询结束时间

	public ProduceExecute() {

	}

	public void setMore() {
		this.setExecuteDateStr(DateUtil.formatDate(this.getExecuteDate()));
		this.setExecDay(DateUtil.formatDate(this.getExecuteDate(), "yyyy-MM-dd"));
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

	public String getExecuteState() {
		return executeState;
	}

	public void setExecuteState(String executeState) {
		this.executeState = executeState;
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

	public String getWorkPlanId() {
		return workPlanId;
	}

	public void setWorkPlanId(String workPlanId) {
		this.workPlanId = workPlanId;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getLearnFlag() {
		return learnFlag;
	}

	public void setLearnFlag(String learnFlag) {
		this.learnFlag = learnFlag;
	}

	public String getExecDay() {
		return execDay;
	}

	public void setExecDay(String execDay) {
		this.execDay = execDay;
	}

	public String getThisMonth() {
		return thisMonth;
	}

	public void setThisMonth(String thisMonth) {
		this.thisMonth = thisMonth;
	}

	public String getThisDay() {
		return thisDay;
	}

	public void setThisDay(String thisDay) {
		this.thisDay = thisDay;
	}

	public String getExecuteDateStr() {
		return executeDateStr;
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
