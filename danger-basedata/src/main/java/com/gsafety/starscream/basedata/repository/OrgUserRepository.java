package com.gsafety.starscream.basedata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.gsafety.starscream.basedata.model.OrgUser;

public interface OrgUserRepository extends CrudRepository<OrgUser,String>,JpaSpecificationExecutor<OrgUser>{

	/**
	 * 根据组织机构ID查询组织机构下所有用户
	 * @param orgCode
	 * @return
	 */
	@Query("select user from OrgUser user where user.org.orgCode=:orgCode")
	public List<OrgUser> findByOrgs(@Param("orgCode")String orgCode);
	
	/**
	 * 根据用户名称查询用户
	 * @param username
	 * @return
	 */
	public OrgUser findByUsername(String username);

	/**
	 * 查询用户
	 * @param userIds
	 * @return
	 */
	@Query("select user from OrgUser user where user.id in ?1")
	public List<OrgUser> findByIds(String[] userIds);
	
	/**
	 * 根据组id查询组下的用户
	 * @param groupId
	 * @return
	 */
	@Query("select user from OrgUser user where user.comGroupIds like %:groupId%")
	public List<OrgUser> findByComGroupIdsLike(@Param("groupId") String groupId);
	
	@Modifying
	@Query("update OrgUser user set user.status=0 where user.id in ?1")
	public Integer delete(String[] userIds);
	
	@Query("select user.firstSpelling from OrgUser user")
	public String[] findFirstSpell();
	
	@Query("select user from OrgUser user where user.shortSpelling like %:shortName%")
	public List<OrgUser> findByLikeUserShort(@Param("shortName") String shortName);
	
}
