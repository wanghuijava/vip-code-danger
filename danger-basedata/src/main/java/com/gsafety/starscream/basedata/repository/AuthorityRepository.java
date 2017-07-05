package com.gsafety.starscream.basedata.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import com.gsafety.starscream.basedata.model.Authority;


/**
 * 权限
 * @author chenwenlong
 *
 */
public interface AuthorityRepository extends CrudRepository<Authority,String>,JpaSpecificationExecutor<Authority>{

}
