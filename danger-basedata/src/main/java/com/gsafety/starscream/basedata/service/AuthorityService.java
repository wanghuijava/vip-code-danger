package com.gsafety.starscream.basedata.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.gsafety.starscream.basedata.model.Authority;


public interface AuthorityService {

	/**
	 * 保存权限
	 * @param authority
	 * @return
	 */
	public Authority save(Authority authority);
	
	/**
	 * 根据ID删除权限
	 * @param id
	 */
	public void delete(String id);
	
	/**
	 * 更新权限
	 * @param authority
	 * @return
	 */
	public Authority update(Authority authority);
	
	/**
	 * 根据ID查询权限
	 * @param id
	 * @return
	 */
	public Authority findById(String id);
	
	/**
	 * 查询数据条数
	 * @return
	 */
	public int findCount(Authority authority);
	
	/**
	 * 查询全部权限
	 * @return
	 */
	public Page<Authority> find(Authority authority,Pageable page);
}
