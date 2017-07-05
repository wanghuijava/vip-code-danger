package com.gsafety.starscream.basedata.service;

import java.util.List;

import com.gsafety.starscream.basedata.model.Attach;

/**
 * 附件service
 * @author chenwenlong
 *
 */
public interface AttachService {

	/**
	 * 附件保存操作
	 * @param attach
	 * @return
	 */
	public Attach save(Attach attach);
	
	/**
	 * 修改关联ID
	 * @param attachIds
	 * @param string
	 */
	public void updateReferId(String[] attachIds, String referId);
	
	/**
	 * 附件删除操作
	 * @param id
	 */
	public void delete(String id);
	
	/**
	 * 根据ID查找附件
	 * @param id
	 * @return
	 */
	public Attach findById(String id);
	
	/**
	 * 根据IDs查找附件
	 * @param ids
	 * @return
	 */
	public List<Attach> findByIds(String[] ids);
	
	/**
	 * 根据关联ID查找附件
	 * @param referId
	 * @return
	 */
	public List<Attach> findByReferId(String referId);

	/**
	 * 根据关联ID查找附件，并返回json string
	 * @param referId
	 * @return
	 */
	public String getJsonStrByReferId(String referId);

}

