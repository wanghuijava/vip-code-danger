package com.gsafety.starscream.basedata.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsafety.starscream.basedata.model.EventType;
import com.gsafety.starscream.basedata.repository.EventTypeRepository;
import com.gsafety.starscream.basedata.service.EventTypeService;
import com.gsafety.starscream.constant.BasedataConstant;
import com.gsafety.starscream.exception.SequenceNoValException;
import com.gsafety.starscream.utils.page.ScrollPage;

/**
 * 事件类型Service实现
 * @author duxi
 *
 */
@Service("eventTypeService")
public class EventTypeServiceImpl implements EventTypeService {

	@Autowired
	private EventTypeRepository eventTypeRepository;     //事件类型Repository注入
	
	/**
	 * 保存事件类型
	 * @param EventType
	 * @return
	 * @throws SequenceNoValException 
	 */
	@Override
	@Transactional
	public EventType save(EventType eventType) throws SequenceNoValException {
		eventType = eventTypeRepository.save(eventType);
		return eventType;
	}

	/**
	 * 删除事件类型
	 * @param eventTypeCode
	 */
	@Override
	@Transactional
	public void delete(String eventTypeCode) {
		EventType eventType = eventTypeRepository.findOne(eventTypeCode);
		//如果传递类型编码不正确，直接返回
		if(eventType == null) {
			return;
		}else{
			List<EventType> list = eventTypeRepository.findByParentCodeOrderByEventTypeCodeAsc(eventType.getEventTypeCode());
			if(list.size()==0){
				eventTypeRepository.delete(eventTypeCode);        //删除事件类型
			}else{
				for(EventType eventTypes:list){
					if(null!=eventTypes){
						delete(eventTypes.getEventTypeCode());
					}
				}
				eventTypeRepository.delete(eventTypeCode);        //删除事件类型
			}
		}
		if (StringUtils.isEmpty(eventType.getParentCode())||findCountByParentCode(eventType.getParentCode())>0) {
			return ;
		}

	}

	/**
	 * 更新事件类型
	 * @param eventType
	 * @return
	 */
	@Override
	@Transactional
	public EventType update(EventType eventType) {
		return eventTypeRepository.save(eventType);
	}
	
	
	/**
	 * 查询事件类型，根据事件类型编码
	 * @param eventTypeCode
	 * @return
	 */
	@Override
	public EventType findByEventTypeCode(String eventTypeCode) {
		return eventTypeRepository.findOne(eventTypeCode);
	}

	/**
	 * 查询事件类型，根据父级事件编码
	 * @param parentCode
	 * @return
	 */
	@Override
	public List<EventType> findByParentCode(String parentCode) {
		return eventTypeRepository.findByParentCodeOrderByEventTypeCodeAsc(parentCode);
	}


	/**
	 * 分页查询事件类型
	 * @param start
	 * @param count
	 * @param sort
	 * @param eventType
	 * @return
	 */
	public Page<EventType> find(int start,int count,Sort sort,EventType eventType){
		Pageable pageable = new PageRequest(start, count, sort);
		Page<EventType> eventTypes = eventTypeRepository.findAll(new EventTypeSpecification(eventType,sort),pageable);
		return eventTypes;
	}

	/**
	 * 滚动查询事件类型
	 * @param scrollPage
	 * @param eventType
	 * @return
	 */
	public List<EventType> find(ScrollPage scrollPage, EventType eventType){
		//id为空则查询所有数据
		if(StringUtils.isEmpty(scrollPage.getId())) {
			return (List<EventType>)eventTypeRepository.findAll(new EventTypeSpecification(eventType,null));
		}
		//分页ID不为空则表示滚动查询
		eventType.setEventTypeCode(scrollPage.getId());
		Page<EventType> eventTypes = find(0,scrollPage.getCount(),scrollPage.getSort("orgCode"),eventType);
		return eventTypes.getContent();
	}
	
	/**
	 * 查询树形事件
	 * 默认根code为"-1"
	 */
	public List<EventType> findTree(String code){
		List<EventType> eventTypes = findByParentCode(code);
		for(EventType eventType : eventTypes) {
			eventType.setChildren(findTree(eventType.getEventTypeCode()));
		}
		return eventTypes;
	}
	
	/**
	 * 添加查询条件类
	 * @author Administrator
	 *
	 */
	class EventTypeSpecification implements Specification<EventType>{

		private EventType eventType;
		private Sort sort;
		
		public EventTypeSpecification(EventType eventType,Sort sort){
			this.eventType = eventType;
			this.sort = sort;
		}
		
		//查询条件拼接
		public Predicate toPredicate(Root<EventType> root, CriteriaQuery<?> query,
				CriteriaBuilder cb) {
			if(eventType==null) {
				return null;
			}
			List<Predicate> list = new ArrayList<Predicate>();
			if(!StringUtils.isEmpty(eventType.getEventTypeName())) {
				list.add(cb.like(root.get("eventTypeName").as(String.class), "%"+eventType.getEventTypeName()+"%"));
			}
			
			Predicate[] p = new Predicate[list.size()];
		    return cb.and(list.toArray(p));
		}
	}
	
	/**
	 * 查询事件类型数量，根据父级事件编码
	 * @param parentCode
	 * @return
	 */
	public int findCountByParentCode(String parentCode) {
		//当传递父级CODE为空时，查询子级机构数量
		if(StringUtils.isEmpty(parentCode)) {
			return eventTypeRepository.findCountByParentCodeIsNull();
		}
		return eventTypeRepository.findCountByParentCode(parentCode);
	}

	
	/**
	 * 查询事件类型数量，根据父级事件编码
	 * @param parentCode
	 * @return
	 */
	public String findNameByEventTypeCodes(String eventTypecodes) {
		//当传递父级CODE为空时，查询子级机构数量
		if(StringUtils.isNotEmpty(eventTypecodes)) {
			String[] codes = eventTypecodes.split(BasedataConstant.PARAM_SPLIT);
			Object[] names = eventTypeRepository.findNameByEventTypeCodes(codes);
			return StringUtils.join(names, BasedataConstant.PARAM_SPLIT);
		}
		return null;
	}
}
