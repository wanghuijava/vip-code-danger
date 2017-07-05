package com.gsafety.starscream.basedata.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.gsafety.starscream.basedata.model.EventType;
import com.gsafety.starscream.exception.SequenceNoValException;
import com.gsafety.starscream.utils.page.ScrollPage;

/**
 * 事件类型Service
 * @author duxi
 *
 */
public interface EventTypeService {

	/**
	 * 保存事件类型
	 * @param eventType
	 * @return
	 */
	public EventType save(EventType eventType) throws SequenceNoValException;
	
	/**
	 * 删除事件类型
	 * @param eventTypeCode
	 */
	public void delete(String eventTypeCode);
	
	/**
	 * 更新事件类型
	 * @param eventType
	 * @return
	 */
	public EventType update(EventType eventType);
	
	/**
	 * 查询事件类型数量，根据父级机构编码
	 * @param parentCode
	 * @return
	 */
	public int findCountByParentCode(String parentCode);
	
	/**
	 * 查询事件类型，根据事件类型编码
	 * @param eventTypeCode
	 * @return
	 */
	public EventType findByEventTypeCode(String eventTypeCode);
	
	/**
	 * 查询事件类型，根据父级机构编码
	 * @param parentCode
	 * @return
	 */
	public List<EventType> findByParentCode(String parentCode);
	
	/**
	 * 分页查询事件类型
	 * @param start
	 * @param count
	 * @param sort
	 * @param eventType
	 * @return
	 */
	public Page<EventType> find(int start,int count,Sort sort,EventType eventType);

	/**
	 * 滚动查询事件类型
	 * @param scrollPage
	 * @param eventType
	 * @return
	 */
	public List<EventType> find(ScrollPage scrollPage, EventType eventType);
	
	/**
	 * 查询树形事件
	 * @return
	 */
	public List<EventType> findTree(String code);
	
	public String findNameByEventTypeCodes(String eventTypecodes);
}
