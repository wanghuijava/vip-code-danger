package com.gsafety.starscream.project.repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.gsafety.starscream.project.model.ProducePlan;

/**
 * 试运投产项目-计划
 * 
 * @author wanghui
 * @date 2016-1-1
 */
public interface ProducePlanRepository extends CrudRepository<ProducePlan,String>,JpaSpecificationExecutor<ProducePlan>{

}
