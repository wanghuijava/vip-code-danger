package com.gsafety.starscream.project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gsafety.starscream.project.model.WorkPlan;

/**
 * 危险作业项目-计划
 * 
 * @author wanghui
 * @date 2016-1-1
 */
public interface WorkPlanService {
	
	/**
	 * 新增
	 * @param workPlan
	 * @return
	 */
	public WorkPlan save(WorkPlan workPlan) ;
	
	/**
	 * 删除
	 * @param workPlanId 
	 * @return
	 */
	public void delete(String workPlanId);
	
	/**
	 * 更新
	 * @param workPlan
	 * @return
	 */
	public WorkPlan update(WorkPlan workPlan);
	
	/**
	 * 加载
	 * @param workPlanId
	 * @return workPlan
	 */
	public WorkPlan findById(String workPlanId);
	
	/**
	 * 查询
	 * @param workPlan
	 * @param page
	 * @return
	 */
	public Page<WorkPlan> find(WorkPlan workPlan ,Pageable page);
	
}
