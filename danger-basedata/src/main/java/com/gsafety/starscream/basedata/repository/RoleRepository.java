package com.gsafety.starscream.basedata.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.gsafety.starscream.basedata.model.Role;


/**
 * 角色
 * @author chenwenlong
 *
 */
public interface RoleRepository extends CrudRepository<Role,String>,JpaSpecificationExecutor<Role>{
	
}

