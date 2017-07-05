package com.gsafety.starscream.basedata.service;

import java.util.List;
import java.util.Map;

import com.gsafety.starscream.basedata.model.BasSysCfgprop;

public interface BasSysCfgpropService {
	
	public BasSysCfgprop save(BasSysCfgprop model);
	
	public BasSysCfgprop findByPropName(String propName);
	
	public Map<String, String> getSysCfgPropMap();
	
	public List<BasSysCfgprop> findAll();
	
	public BasSysCfgprop findByPropId(String propId);
	
	public void delete(String propId);

}
