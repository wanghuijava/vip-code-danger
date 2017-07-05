package com.gsafety.starscream.project.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.gsafety.starscream.project.model.WorkExecute;

/**
 * 危险作业项目-实施（今日录入）
 * 
 * @author wanghui
 * @date 2016-1-1
 */
public interface WorkExecuteRepository extends CrudRepository<WorkExecute,String>,JpaSpecificationExecutor<WorkExecute>{

	List<WorkExecute> findByWorkPlanId(String workPlanId);
}
