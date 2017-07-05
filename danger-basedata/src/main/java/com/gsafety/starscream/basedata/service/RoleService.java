package com.gsafety.starscream.basedata.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gsafety.starscream.basedata.model.Role;

public interface RoleService  {

	/**
	 * 保存角色
	 * @param role
	 * @return
	 */
	public Role save(Role role);
	
	/**
	 * 删除角色
	 * @param id
	 */
	public void delete(String id);
	
	/**
	 * 更新角色
	 * @param role
	 * @return
	 */
	public Role update(Role role);
	
	/**
	 * 根据ID查询角色
	 * @param id
	 * @return
	 */
	public Role findById(String id);
	
	/**
	 * 获取指定roleId的角色列表
	 * @param roles
	 * @return
	 */
	public List<Role> findByIds(String roles);
	
	/**
	 * 查询所有角色
	 * @return
	 */
	public List<Role> findAll();
	
	/**
	 * 查询数据总条数
	 * @return
	 */
	public int findCount();
	
	public Page<Role> find(Role role,Pageable page);
}
