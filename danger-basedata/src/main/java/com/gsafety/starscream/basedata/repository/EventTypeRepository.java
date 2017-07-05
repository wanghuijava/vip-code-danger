package com.gsafety.starscream.basedata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.gsafety.starscream.basedata.model.EventType;

/**
 * 事件类型repository
 * @author duxi 
 *
 */
public interface EventTypeRepository extends CrudRepository<EventType,String>,JpaSpecificationExecutor<EventType>{
	
	/**
	 * 根据父级code查询子级组织机构列表
	 * @param parentCode
	 * @return
	 */
	public List<EventType> findByParentCodeOrderByEventTypeCodeAsc(String parentCode);
	
	
	/**
	 * 父级code为空查询子级数量
	 * @return
	 */
	@Query("select count(e) from EventType e where e.parentCode is null")
	public int findCountByParentCodeIsNull();
	
	/**
	 * 根据父级code查询子级事件类型数量
	 * @param parentCode
	 * @return
	 */
	@Query("select count(e) from EventType e where e.parentCode=:parentCode")
	public int findCountByParentCode(@Param("parentCode")String parentCode);

	@Query(nativeQuery=true,value="select b.eventtypename from bas_event_type b where b.eventtypecode=:typeCode")
	public Object[] findByEventTypeCode(@Param("typeCode")String typeCode);

	@Query("select b.eventTypeName from EventType b where b.eventTypeCode in :typeCodes")
	public Object[] findNameByEventTypeCodes(@Param("typeCodes")String[] typeCode);
	

}
