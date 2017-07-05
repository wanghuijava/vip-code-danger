package com.gsafety.starscream.project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gsafety.starscream.project.model.ProduceExecute;

/**
 * 试运投产项目-实施（今日录入）
 * 
 * @author wanghui
 * @date 2016-1-1
 */
public interface ProduceExecuteService {
	
	/**
	 * 新增
	 * @param produceExecute
	 * @return
	 */
	public ProduceExecute save(ProduceExecute produceExecute) ;
	
	/**
	 * 删除
	 * @param produceExecuteId 
	 * @return
	 */
	public void delete(String produceExecuteId);
	
	/**
	 * 更新
	 * @param produceExecute
	 * @return
	 */
	public ProduceExecute update(ProduceExecute produceExecute);
	
	/**
	 * 加载
	 * @param produceExecuteId
	 * @return produceExecute
	 */
	public ProduceExecute findById(String produceExecuteId);
	
	/**
	 * 查询
	 * @param produceExecute
	 * @param page
	 * @return
	 */
	public Page<ProduceExecute> find(ProduceExecute produceExecute ,Pageable page);
	
}
