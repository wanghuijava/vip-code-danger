package com.gsafety.starscream.project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gsafety.starscream.project.model.WorkExecute;

/**
 * 危险作业项目-实施（今日录入）
 * 
 * @author wanghui
 * @date 2016-1-1
 */
public interface WorkExecuteService {
	
	/**
	 * 新增
	 * @param workExecute
	 * @return
	 */
	public WorkExecute save(WorkExecute workExecute) ;
	
	/**
	 * 删除
	 * @param workExecuteId 
	 * @return
	 */
	public void delete(String workExecuteId);
	
	/**
	 * 更新
	 * @param workExecute
	 * @return
	 */
	public WorkExecute update(WorkExecute workExecute);
	
	/**
	 * 加载
	 * @param workExecuteId
	 * @return workExecute
	 */
	public WorkExecute findById(String workExecuteId);
	
	/**
	 * 查询
	 * @param workExecute
	 * @param page
	 * @return
	 */
	public Page<WorkExecute> find(WorkExecute workExecute ,Pageable page);
	
}
