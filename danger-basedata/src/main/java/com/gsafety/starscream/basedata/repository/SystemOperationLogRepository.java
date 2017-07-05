package com.gsafety.starscream.basedata.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.gsafety.starscream.basedata.model.SystemOperationLogs;

/**
 *  @author Zzj
 * @date 2015-10-27 下午4:38:27
 * @history V1.0
 */
public interface SystemOperationLogRepository extends CrudRepository<SystemOperationLogs, String>,
	JpaSpecificationExecutor<SystemOperationLogs> {

}

