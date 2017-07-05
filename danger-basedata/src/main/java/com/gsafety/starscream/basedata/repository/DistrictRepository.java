package com.gsafety.starscream.basedata.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.gsafety.starscream.basedata.model.District;

/**
 * 行政区划Repository层
 * 
 * @author chenwenlong
 *
 */
public interface DistrictRepository extends CrudRepository<District,String>{

	/**
	 * 根据父级行政区划编码查询子级行政区划列表
	 * @param parentCode
	 * @return
	 */
	public List<District> findByParentCodeOrderBySortNumAsc(String parentCode);
	
	public District findByDistName(String districtName);
	
	
}
