package com.gsafety.starscream.basedata.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import com.gsafety.starscream.basedata.model.BasSysCfgprop;
import com.gsafety.starscream.basedata.repository.BasSysCfgpropRepository;
import com.gsafety.starscream.basedata.service.BasSysCfgpropService;
import com.gsafety.starscream.utils.db.PrimaryKeyUtils;

/**
 * 系统属性配置service
 * @author dzg
 *
 */
@Service("sysCfgpropService")
public class BasSysCfgpropServiceImpl implements BasSysCfgpropService{
	@Resource
	private BasSysCfgpropRepository basSysCfgpropRepository;
	
	public BasSysCfgprop save(BasSysCfgprop model){
		if (StringUtils.isEmpty(model.getPropId())) {
			model.setPropId(PrimaryKeyUtils.getFormatTimeKey());
		}
		return basSysCfgpropRepository.save(model);
	}
	
	public List<BasSysCfgprop> findAll(){
		List<BasSysCfgprop> list=(List<BasSysCfgprop>)basSysCfgpropRepository.findAll();
		return list;
	}
	
	public Map<String, String> getSysCfgPropMap(){
		Map<String, String> map =new HashMap<String, String>();
		List<BasSysCfgprop> list=(List<BasSysCfgprop>)basSysCfgpropRepository.findAll();
		for (BasSysCfgprop obj :list) {
			map.put(obj.getPropName(), obj.getPropValue());
		}
		return map;
	}
	
	public BasSysCfgprop findByPropName(String propName){
		BasSysCfgprop model=basSysCfgpropRepository.findByPropName(propName);
		return model;
	}
	
	public BasSysCfgprop findByPropId(String propId){
		BasSysCfgprop model=basSysCfgpropRepository.findOne(propId);
		return model;
	}
	
	public void delete(String propId){
		basSysCfgpropRepository.delete(propId);
	}
	
	

}
