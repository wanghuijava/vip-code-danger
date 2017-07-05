package com.gsafety.starscream.basedata.service;

import java.util.List;

import com.gsafety.starscream.basedata.model.District;

/**
 * 行政区划Service
 * 
 * @author chenwenlong
 *
 */
public interface DistrictService {

	/**
	 * 保存行政区划
	 * @param district
	 * @return
	 */
	public District save(District district);
	
	/**
	 * 删除行政区划，根据行政区划编码
	 * @param distCode
	 */
	public void delete(String distCode);
	
	/**
	 * 修改行政区划
	 * @param district
	 * @return
	 */
	public District update(District district);
	
	/**
	 * 查询行政区划，根据行政区划编码【主键】
	 * @param distCode
	 * @return
	 */
	public District findByDistCode(String distCode);
	
	/**
	 * 查询行政区划，根据父级行政区划编码查询子级行政区划
	 * @param parentCode
	 * @return
	 */
	public List<District> findByParentCode(String parentCode);
	
	/**
	 * 查询树形行政区划
	 * @param code
	 * @return
	 */
	public List<District> findTree(String code);
	
	/**
	 * 根据根节点名称查询树形行政区划
	 * @param code
	 * @return
	 */
	public List<District> getDistrictCondition();
}
