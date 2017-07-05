package com.gsafety.starscream.basedata.service;

import java.util.List;

import com.gsafety.starscream.basedata.model.User;

/**
 * 系统用户Service
 * 
 * @author chenwenlong
 *
 */
public interface UserService {
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	public boolean exist(String username);
	
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	public User login(String username,String password);
	
	/**
	 * 根据ID查询用户
	 * @param id
	 * @return
	 */
	public User findById(String id);
	
	/**
	 * 根据username查询用户
	 * @param username
	 * @return
	 */
	public User findByUsername(String username);
	
	/**
	 * 组织机构下的用户转换成系统用户
	 * @param orgUserId
	 * @param sysUser
	 * @return
	 */
	public User save(String orgUserId,User sysUser);
	
	/**
	 * 保存用户
	 * @param sysUser
	 * @return
	 */
	public User save(User sysUser);
	
	/**
	 * 用户删除，根据ids
	 * @param id
	 */
	public void deleteByIds(String[] ids);
	
	/**
	 * 用戶刪除，根据主键username
	 * @param username
	 */
	public void deleteByUsername(String username);
	
	
	/**
	 * 根据组织机构删除系统用户
	 * @param orgCode
	 */
	public void deleteByOrgCode(String orgCode);
	
	/**
	 * 用户更新
	 * @param user
	 */
	public User update(User user);
	
	/**
	 * 修改密码
	 * @param user
	 */
	public void modifyPassword(String userName,String password);
	
	/**
	 * 带条件查询
	 * @param user
	 * @return
	 */
	public List<User> findAll();
	/**
	 * 带条件查询
	 * @param user
	 * @return
	 */
	public List<User> findByOrgCode(String orgCode);
	
	/**
	 * 将用户添加到用户组
	 * @param userGroupId
	 * @param userId
	 * @return
	 */
	public void updateUserToGroup(String userGroupId,String userId);
	/**
	 * 根据角色id查询 有该角色的用户
	 * @param roleId
	 * @return
	 */
	public List<User> findUserByRoleId(String roleId);
}
