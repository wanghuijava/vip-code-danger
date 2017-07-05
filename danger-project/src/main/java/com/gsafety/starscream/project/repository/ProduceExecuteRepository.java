package com.gsafety.starscream.project.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.gsafety.starscream.project.model.ProduceExecute;

/**
 * 试运投产项目-实施（今日录入）
 * 
 * @author wanghui
 * @date 2016-1-1
 */
public interface ProduceExecuteRepository extends CrudRepository<ProduceExecute,String>,JpaSpecificationExecutor<ProduceExecute>{

	List<ProduceExecute> findByWorkPlanId(String workPlanId);
}
