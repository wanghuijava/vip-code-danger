package com.gsafety.starscream.project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gsafety.starscream.project.model.ProducePlan;

/**
 * 试运投产项目-计划
 * 
 * @author wanghui
 * @date 2016-1-1
 */
public interface ProducePlanService {
	
	/**
	 * 新增
	 * @param producePlan
	 * @return
	 */
	public ProducePlan save(ProducePlan producePlan) ;
	
	/**
	 * 删除
	 * @param workPlanId 
	 * @return
	 */
	public void delete(String workPlanId);
	
	/**
	 * 更新
	 * @param producePlan
	 * @return
	 */
	public ProducePlan update(ProducePlan producePlan);
	
	/**
	 * 加载
	 * @param workPlanId
	 * @return producePlan
	 */
	public ProducePlan findById(String workPlanId);
	
	/**
	 * 查询
	 * @param producePlan
	 * @param page
	 * @return
	 */
	public Page<ProducePlan> find(ProducePlan producePlan ,Pageable page);
	
}
