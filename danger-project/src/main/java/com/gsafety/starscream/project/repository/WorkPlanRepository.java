package com.gsafety.starscream.project.repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.gsafety.starscream.project.model.WorkPlan;

/**
 * 危险作业项目-计划
 * 
 * @author wanghui
 * @date 2016-1-1
 */
public interface WorkPlanRepository extends CrudRepository<WorkPlan,String>,JpaSpecificationExecutor<WorkPlan>{

}
