package com.gsafety.starscream.basedata.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.gsafety.starscream.basedata.model.ComGroup;

/**
 * 常用组repository
 * @author chenwenlong
 *
 */
public interface ComGroupRepository extends CrudRepository<ComGroup,String>{

	public List<ComGroup> findByParentIdOrderByIdAsc(String id);
	
	public ComGroup findByGroupInfo(String groupInfo);
	
	public List<ComGroup> findByGroupName(String groupName);

}
