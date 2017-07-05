package com.gsafety.starscream.basedata.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gsafety.starscream.basedata.model.OrgUser;
import com.gsafety.starscream.utils.page.ScrollPage;

public interface OrgUserService {

	/**
	 * 根据用户ID数组查询组织机构用户 
	 * @param userIds
	 * @return
	 */
	public List<OrgUser> findByIds(String [] userIds);
	
	/**
	 * 根据组织机构ID查询组织机构用户
	 * @param orgCode
	 * @return
	 */
	public List<OrgUser> findOrgUserByOrgCode(String orgCode);
	
	/**
	 * 查找 该部门最重要的人物  1. 常用联系人 2。 排在第一的人物 
	 */
	public OrgUser findFirstPersonByOrgCode(String orgCode);
	public OrgUser getOrgPrincipal(String orgCode);
	/**
	 * 根据ID查询组织机构用户
	 * @param id
	 * @return
	 */
	public OrgUser findById(String id);
	
	/**
	 * 根据username查询用户
	 * @param username
	 * @return
	 */
	public OrgUser findByUsername(String username);
	
	/**
	 * 用户添加
	 * @param user
	 * @return
	 */
	public OrgUser save(OrgUser user);
	
	/**
	 * 自动创建通讯录用户(根据机构创建)
	 * @param user
	 * @return
	 */
	public OrgUser createOrgUser(OrgUser user);
	
	/**
	 * 常用组添加用户
	 * @param userId
	 * @param groupIds
	 */
	public void addToGroup(String groupId,String[] userIds);
	
	/**
	 * 用户注销
	 * @param id
	 */
	public void delete(String id);
	
	/**
	 * 用户删除
	 * @param id
	 */
	public void realdelete(String id);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public int delete(String[] ids);
	
	/**
	 * 常用组删除用户
	 * @param userId
	 * @param groupIds
	 */
	public void deleteFromGroup(String groupId,String[] userIds);
	
	/**
	 * 用户更新
	 * @param user
	 */
	public OrgUser update(OrgUser user);
	
	/**
	 * 带条件的分页、排序查询
	 * @param start
	 * @param count
	 * @param sort
	 * @param user
	 * @return
	 */
	public Page<OrgUser> find(OrgUser user, Pageable page);
	
	/**
	 * 滚动查询通讯录用户
	 * @param scrollPage
	 * @param orgUser
	 * @return
	 */
	public List<OrgUser> find(ScrollPage scrollPage, OrgUser orgUser);
	
	public List<OrgUser> findByComGroupIdsLike(String groupId);
	
	/**
	 * 查询下级机构并且为叶子节点的机构的用户
	 * @param shortName
	 * @return
	 */
	public List<OrgUser> findByLikeUserShort(String shortName);
}
