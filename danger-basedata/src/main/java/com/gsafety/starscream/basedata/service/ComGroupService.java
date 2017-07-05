package com.gsafety.starscream.basedata.service;

import java.util.List;

import com.gsafety.starscream.basedata.model.ComGroup;

/**
 * 常用组service
 * @author chenwenlong
 *
 */
public interface ComGroupService {

	/**
	 * 常用组保存
	 * @param comGroup
	 * @return
	 */
	public ComGroup save(ComGroup comGroup);
	
	/**
	 * 常用组删除
	 * @param id
	 */
	public void delete(String id);
	
	/**
	 * 常用组更新
	 * @param comGroup
	 * @return
	 */
	public ComGroup update(ComGroup comGroup);
	
	/**
	 * 常用组查找
	 * @param id
	 * @return
	 */
	public ComGroup findById(String id);
	
	/**
	 * 常用组查找
	 * @param id
	 * @return
	 */
	public List<ComGroup> findByParentId(String id);
	public List<ComGroup> findByGroupName(String groupName);
	
	/**
	 * 常用组查找子级树
	 * @param id
	 * @return
	 */
	public List<ComGroup> findTree(String id);
	
	/**
	 * 根据唯一组名查找组对象
	 * @param id
	 * @return
	 */
	public ComGroup findByGroupInfo(String groupInfo);
}
