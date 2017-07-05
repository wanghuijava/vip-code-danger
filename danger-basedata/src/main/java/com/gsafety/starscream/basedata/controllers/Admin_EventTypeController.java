package com.gsafety.starscream.basedata.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.springframework.beans.factory.annotation.Autowired;

import com.gsafety.common.utils.AjaxUtils;
import com.gsafety.starscream.basedata.model.EventType;
import com.gsafety.starscream.basedata.service.EventTypeService;
import com.gsafety.starscream.constant.BasedataConstant;
import com.gsafety.starscream.exception.SequenceNoValException;
/**
 * 事件类型controller
 * @author duxi
 *	
 */
@Path("admin/basedata")
public class Admin_EventTypeController {

	@Autowired
	private Invocation inv;
	
	@Autowired
	private EventTypeService eventTypeService;

	/**
	 * 初始化树形结构
	 * @return
	 */
	@Get("eventType/findAll")
	public String eventType_findAll() {
		return "basedata/eventtype-list";
	}
	
	/**
	 * 异步加载树形结构
	 */
	
	@Get("eventType/findTree")
	public String eventType_findTree(){
		List<EventType> eventTypes = eventTypeService.findTree(BasedataConstant.EVENT_TYPE_PARENTCODE);
		return AjaxUtils.printSuccessJson(eventTypes, inv);
	}
	
	/**
	 * 异步编辑当前节点
	 */
	
	@Get("eventType/edit/{eventTypeCode:[0-9A-Z]+}")
	public String eventtype_edit(@Param("eventTypeCode") String eventTypeCode){
		EventType eventType = eventTypeService.findByEventTypeCode(eventTypeCode);
		String parentName="无上级事件";
		if(!eventType.getParentCode().equals(BasedataConstant.EVENT_TYPE_PARENTCODE)){
			parentName = eventTypeService.findByEventTypeCode(eventType.getParentCode()).getEventTypeName();
		}
		
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		jsonmap.put("eventType", eventType);
		jsonmap.put("parentName", parentName);
		return AjaxUtils.printSuccessJson(jsonmap, inv);
	}
	
	/**
	 * 保存事件类型
	 * @param 
	 * @return
	 * @throws SequenceNoValException 
	 */
	@Post("eventType/update")
	public String eventType_update(@Param("editid")String eventTypeCode,
			@Param("editname")String eventTypeName,@Param("editparentCode")String parentCode,
			@Param("editnotes")String notes,@Param("iscommon")String common) throws SequenceNoValException {
		EventType  eventtype= new EventType();
		eventtype.setEventTypeCode(eventTypeCode);
		eventtype.setEventTypeName(eventTypeName);
		eventtype.setNotes(notes);
		eventtype.setParentCode(parentCode);
		eventtype.setCommon(common);
		//数据保存
		eventtype = eventTypeService.save(eventtype);
		return "basedata/eventtype-list";
	}
	
	/**
	 * 添加子类事件类型
	 * @param 
	 * @return
	 * @throws SequenceNoValException 
	 */
	@Post("eventType/save")
	public String eventType_save(@Param("addid")String eventTypeCode,
			@Param("addname")String eventTypeName,@Param("hidParentCode")String hidParentCode,
			@Param("addnotes")String notes,@Param("iscommon")String common) throws SequenceNoValException {
		EventType  eventtype= new EventType();
		eventtype.setEventTypeCode(eventTypeCode);
		eventtype.setEventTypeName(eventTypeName);
		eventtype.setNotes(notes);
		eventtype.setParentCode(hidParentCode);
		eventtype.setCommon(common);
		//数据保存
		eventtype = eventTypeService.save(eventtype);
		return "basedata/eventtype-list";
	}
	
	
	/**
	 * 删除事件类型
	 * @param eventTypeCode
	 * @return delete/10032
	 */
	@Get("eventType/delete/{id:[0-9A-Z]+}")
	public String eventType_delete(@Param("id")String id){
		eventTypeService.delete(id);
		return "basedata/eventtype-list";
	}
	
}

