package com.gsafety.starscream.basedata.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsafety.common.utils.PinyinUtils;
import com.gsafety.starscream.basedata.model.OrgUser;
import com.gsafety.starscream.basedata.model.User;
import com.gsafety.starscream.basedata.repository.UserRepository;
import com.gsafety.starscream.basedata.service.OrgUserService;
import com.gsafety.starscream.basedata.service.RoleService;
import com.gsafety.starscream.basedata.service.UserService;
import com.gsafety.starscream.constant.BasedataConstant;
import com.gsafety.starscream.utils.SysRoleUtils;
import com.gsafety.starscream.utils.db.PrimaryKeyUtils;

/**
 * 系统用户Service实现
 * 
 * @author chenwenlong
 *
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Resource
	private UserRepository userRepository;

	@Resource
	private OrgUserService orgUserService;
	
	@Resource
	private RoleService roleService;
	
	/**
	 * 检查用户是否存在
	 */
	@Override
	public boolean exist(String username) {
		return findByUsername(username)==null;
	}
	
	/**
	 * 用户登录
	 */
	@Override
	public User login(String username, String password) {
		User user = userRepository.findByUsernameAndPassword(username, password);
		return user;
	}

	/**
	 * 根据ID查询用户
	 */
	@Override
	public User findById(String id) {
		return userRepository.findById(id);
	}
	
	/**
	 * 根据ID查询用户,【username是主鍵】
	 */
	@Override
	public User findByUsername(String username) {
		return userRepository.findOne(username);
	}
	
	/**
	 * 保存用户
	 * @param sysUser
	 * @return
	 */
	public User save(User sysUser){
		if (StringUtils.isEmpty(sysUser.getId())) {
			sysUser.setId(PrimaryKeyUtils.getStringTimeKey());
		}
		sysUser.setCreateTime(new Date());
		sysUser.setUpdateTime(new Date());
		return userRepository.save(sysUser);
	}

	/**
	 * 组织机构下的用户转换成系统用户
	 * @param orgUserId
	 * @param sysUser
	 * @return
	 */
	@Override
	public User save(String orgUserId,User sysUser) {
		OrgUser orgUser = orgUserService.findById(orgUserId);
		if(orgUser==null) {
			return null;
		}
		if(sysUser==null) {
			sysUser = new User();
		}
		//生成系统用户ID
		sysUser.setId(orgUser.getId());
		//系统用户登录名
		String loginName = PinyinUtils.getLoginName(orgUser.getUsername());
		if(StringUtils.isNotEmpty(sysUser.getUsername())) {
			loginName = sysUser.getUsername();
		}
		sysUser.setUsername(loginName);
		//用户类型
		sysUser.setTypeCode(orgUser.getOrg().getTypeCode());
		//组织机构代码、名称
		if(StringUtils.isEmpty(sysUser.getOrgCode())) {
			sysUser.setOrgCode(orgUser.getOrg().getOrgCode());
			sysUser.setOrgName(orgUser.getOrg().getOrgName());
		}
		//系统用户状态
		sysUser.setStatus(orgUser.getStatus());
		//用户创建时间
		sysUser.setCreateTime(new Date());
		//用户更新时间
		sysUser.setUpdateTime(new Date());
		//用户权限
		sysUser.setAuthority(SysRoleUtils.getRoleAuthority2Json(sysUser.getRoles()));
		
		orgUser.setStatus(BasedataConstant.USER_STATUS_SYS);  //已转换的用户
		return userRepository.save(sysUser);
	}
	
	/**
	 * 用户注销
	 */
	@Override
	public void deleteByIds(String[] ids) {
		List<User> users = userRepository.findByIdIn(ids);
		for(User user:users) {
			user.setStatus(0);
		}
		userRepository.save(users);
	}
	

	/**
	 * 根据username删除用户
	 * @param username
	 * @return
	 */
	@Override
	public void deleteByUsername(String username) {
		userRepository.delete(username);
	}

	/**
	 * 根据组织机构删除用户
	 * @param orgCode
	 * @return
	 */
	@Override
	public void deleteByOrgCode(String orgCode) {
		userRepository.deleteByOrgCode(orgCode);
	}
	
	/**
	 * 用户更新
	 */
	@Override
	public User update(User user) {
//		User oldUser = findByUsername(user.getUsername());
//		//用户角色、权限
//		if(StringUtils.isEmpty(user.getRoles())){
//			oldUser.setRoles(StringUtils.EMPTY);
//			oldUser.setAuthority(StringUtils.EMPTY);
//		}else if(!user.getRoles().equals(oldUser.getRoles())) {
//			oldUser.setRoles(user.getRoles());
//			oldUser.setAuthority(SysRoleUtils.getRoleAuthority2Json(user.getRoles()));
//		}
//		//用户所在机构
//		if(StringUtils.isNotEmpty(user.getOrgCode())) {
//			oldUser.setOrgCode(user.getOrgCode());
//			oldUser.setOrgName(user.getOrgName());
//		}
//		//修改密码
//		if(StringUtils.isNotEmpty(user.getPassword())){
//			oldUser.setPassword(user.getPassword());
//		}
//		oldUser.setUpdateTime(new Date());
		user.setUpdateTime(new Date());
		return userRepository.save(user);
	}
	
	public void modifyPassword(String userName,String password){
		userRepository.modifyPassword(userName, password);
	}
	

	/**
	 * 查询用户，带分页、排序、查询条件
	 */
	public List<User> findAll() {
		return (List<User>)userRepository.findAll();
	}

	/**
	 * 查询条件过滤类
	 * @author chenwenlong
	 *
	 */
	class UserSpecification implements Specification<User>{

		private User user;
		
		public UserSpecification(User user){
			this.user = user;
		}
		
		//查询条件拼接
		public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
				CriteriaBuilder cb) {
			if(user==null) {
				return null;
			}
			List<Predicate> list = new ArrayList<Predicate>();
			//用户名
			if(!StringUtils.isEmpty(user.getUsername())) {
				list.add(cb.like(root.get("username").as(String.class), "%"+user.getUsername()+"%"));
			}
			//组织机构
			if(!StringUtils.isEmpty(user.getOrgName())) {
				list.add(cb.like(root.get("orgName").as(String.class), "%"+user.getOrgName()+"%"));
			}
			//用户类型代码（超级用户：9，上级用户：1，下级用户：0）
			if(user.getTypeCode()!=null) {
				list.add(cb.equal(root.get("typeCode").as(Integer.class), user.getTypeCode()));
			}
			//用户状态（正常：0，注销：1）
			if(user.getStatus()!=null) {
				list.add(cb.equal(root.get("status").as(Integer.class), user.getStatus()));
			}
			//用户角色
			if(StringUtils.isNotEmpty(user.getRoles())) {
				list.add(cb.like(root.get("roles").as(String.class),"%"+user.getRoles()+"%"));
			}
			//用户权限
			if(StringUtils.isNotEmpty(user.getAuthority())) {
				list.add(cb.like(root.get("authority").as(String.class),"%"+user.getAuthority()+"%"));
			}
			Predicate[] p = new Predicate[list.size()];
		    return cb.and(list.toArray(p));
		}
	}
	
	/**
	 * 查询用户，带分页、排序、查询条件
	 */
	@Override
	public List<User> findByOrgCode(String orgCode) {
		return userRepository.findByOrgCode(orgCode);
	}
	
	public void updateUserToGroup(String userGroupId,String userId){
		userRepository.updateUserToGroup(userGroupId, userId);
	}
	public List<User> findUserByRoleId(String roleId){
		return userRepository.findByRolesLike(roleId);
	}
	
}

