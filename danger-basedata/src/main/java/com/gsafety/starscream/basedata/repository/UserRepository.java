package com.gsafety.starscream.basedata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.gsafety.starscream.basedata.model.User;

/**
 * 系统用户 Repository层
 * <p>
 * 继承CrudRepository的基本增删改查功能方法,泛型<model类型,主键类型>
 * </p>
 * <p>
 * 继承JpaSpecificationExecutor的过滤查询条件
 * </p>
 * 
 * @author chenwenlong
 * 
 */
public interface UserRepository extends CrudRepository<User, String>,
		JpaSpecificationExecutor<User> {

	/**
	 * 根据用户ID查询系统用户
	 * 
	 * @param id
	 * @return
	 */
	public User findById(String id);

	/**
	 * 根据用户ID查询系统用户
	 * 
	 * @param ids
	 * @return
	 */
	public List<User> findByIdIn(String[] ids);

	/**
	 * 删除组织机构下的系统用户
	 * 
	 * @param orgCode
	 */
	public void deleteByOrgCode(String orgCode);

	/**
	 * 根据用户名和密码查询用户
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public User findByUsernameAndPassword(String username, String password);

	/**
	 * 根据用户名和密码查询用户
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public List<User> findByOrgCode(String orgCode);

	@Modifying
	@Query(nativeQuery = true,value = "update  bas_org_user u set u.user_comgroup_ids=:userGroupId where u.user_id=:userId")
	public void updateUserToGroup(@Param("userGroupId") String userGroupId,@Param("userId") String userId);

	@Modifying
	@Query(nativeQuery = true,value = "update  bas_sys_user u set u.USER_PWD=:password where u.USER_LOGIN_NAME=:userName")
	public void modifyPassword(@Param("userName") String userName,@Param("password") String password);
	
	public List<User> findByRolesLike(String roleId);
}
