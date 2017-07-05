package com.gsafety.starscream.basedata.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gsafety.starscream.basedata.model.ComGroup;
import com.gsafety.starscream.basedata.repository.ComGroupRepository;
import com.gsafety.starscream.basedata.service.ComGroupService;
import com.gsafety.starscream.utils.db.PrimaryKeyUtils;

/**
 * 常用组Service实现
 * @author chenwenlong
 *
 */
@Service("comGroupService")
@Transactional
public class ComGroupServiceImpl implements ComGroupService {

	@Resource
	private ComGroupRepository comGroupRepository;
	
	/**
	 * 常用组保存
	 * @param comGroup
	 * @return
	 */
	@Override
	public ComGroup save(ComGroup comGroup){
		if (StringUtils.isEmpty(comGroup.getId())) {
			comGroup.setId(PrimaryKeyUtils.getFormatTimeKey());
		}
		return comGroupRepository.save(comGroup);
	}
	
	/**
	 * 常用组删除
	 * @param id
	 */
	@Override
	public void delete(String id){
		comGroupRepository.delete(id);
	}
	
	/**
	 * 常用组更新
	 * @param comGroup
	 * @return
	 */
	@Override
	public ComGroup update(ComGroup comGroup){
		return comGroupRepository.save(comGroup);
	}
	
	/**
	 * 常用组查找
	 * @param id
	 * @return
	 */
	@Override
	public ComGroup findById(String id){
		return comGroupRepository.findOne(id);
	}

	/**
	 * 常用组查找
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	@Override
	public List<ComGroup> findByParentId(String id){
		return comGroupRepository.findByParentIdOrderByIdAsc(id);
	}
	
	public List<ComGroup> findByGroupName(String groupName){
		return comGroupRepository.findByGroupName(groupName);
	}
	
	
	/**
	 * 查询树形结构常用组
	 */
	@Transactional(readOnly=true)
	@Override
	public List<ComGroup> findTree(String id) {
		List<ComGroup> comGroups = findByParentId(id);
		if(comGroups==null||comGroups.size()==0) {
			return null;
		}
		for(ComGroup comGroup :comGroups) {
			comGroup.setChildren(findTree(comGroup.getId()));
		}
		return comGroups;
	}
	
	public ComGroup findByGroupInfo(String groupInfo){
		return comGroupRepository.findByGroupInfo(groupInfo);
	}
	
}
