package com.gsafety.starscream.basedata.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import com.gsafety.starscream.basedata.model.BasSysCfgprop;

public interface BasSysCfgpropRepository extends CrudRepository<BasSysCfgprop,String>,JpaSpecificationExecutor<BasSysCfgprop>{
	
	public BasSysCfgprop findByPropName(String propName);

}
