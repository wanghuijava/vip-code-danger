package com.gsafety.starscream.basedata.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gsafety.starscream.basedata.model.Role;
import com.gsafety.starscream.basedata.model.User;
import com.gsafety.starscream.basedata.repository.RoleRepository;
import com.gsafety.starscream.basedata.service.RoleService;
import com.gsafety.starscream.basedata.service.UserService;
import com.gsafety.starscream.utils.SysRoleUtils;
import com.gsafety.starscream.utils.db.PrimaryKeyUtils;

/**
 * 角色Service
 * @author chenwenlong
 *
 */
@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService {

	@Resource
	private RoleRepository roleRepository;
	
	@Resource
	private UserService userService;
	
	/**
	 * 保存角色
	 * @param role
	 * @return
	 */
	@Override
	public Role save(Role role) {
		if (StringUtils.isEmpty(role.getId())) {
			role.setId(PrimaryKeyUtils.getStringTimeKey());
		}
		return roleRepository.save(role);
	}

	/**
	 * 删除角色
	 * @param id
	 */
	@Override
	public void delete(String id) {
		//删除角色
		roleRepository.delete(id);
		//刷新角色
		SysRoleUtils.refreshRoleMap();
		//修改登录用户角色，权限
		User user = new User();
		user.setRoles(id+"");
		List<User> users = userService.findAll();
		for(User u:users) {
			String roles = u.getRoles();
			if(roles.indexOf(id+",")!=-1) {
				roles = roles.replaceAll(id+",", "");
			}else {
				roles = roles.replaceAll(id+"", "");
			}
			u.setRoles(roles);
			u.setAuthority(SysRoleUtils.getRoleAuthority2Json(roles));
		}
	}

	/**
	 * 更新角色
	 * @param role
	 * @return
	 */
	@Override
	public Role update(Role role) {
		//修改角色
		role = roleRepository.save(role);
		//刷新角色
		SysRoleUtils.refreshRoleMap();
		//修改登录用户角色，权限
//		User user = new User();
//		user.setRoles(role.getId()+"");
//		List<User> users = userService.findAll();
//		for(User u:users) {
//			String roles = u.getRoles();
//			u.setAuthority(SysRoleUtils.getRoleAuthority2Json(roles));
//		}
		return role;
	}

	/**
	 * 根据ID查询角色
	 * @param id
	 * @return
	 */
	@Override
	public Role findById(String id) {
		return roleRepository.findOne(id);
	}

	/**
	 * 获取指定roleId的角色列表
	 * @param roles
	 * @return
	 */
	@Override
	public List<Role> findByIds(String roles) {
		String[] rs = roles.split(",");
		List<Integer> roleInts = new ArrayList<Integer>();
		for(String r:rs) {
			roleInts.add(Integer.parseInt(r));
		}
		return (List<Role>)roleRepository.findAll();
	}
	
	/**
	 * 查询所有角色
	 * @return
	 */
	@Override
	public List<Role> findAll() {
		return (List<Role>)roleRepository.findAll();
	}
	
	public int findCount(){
		int count=(int)roleRepository.count();
		return count;
	}

	public Page<Role> find(Role role,Pageable page){
		Page<Role> roles= roleRepository.findAll(new RoleSpecification(role), page);
		return roles;
	}
	
	
	class RoleSpecification implements Specification<Role> {

		private Role role;

		public RoleSpecification(Role role) {
			this.role = role;
		}

		// 查询条件拼接
		public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query,
				CriteriaBuilder cb) {
			if (role == null) {
				return null;
			}
			List<Predicate> list = new ArrayList<Predicate>();
			// 条件查询
			if (StringUtils.isNotEmpty(role.getName())) {
				list.add(cb.like(root.get("name").as(String.class), "%" + role.getName() + "%"));
			}
			Predicate[] p = new Predicate[list.size()];
			System.out.println(list.toArray(p));
			return cb.and(list.toArray(p));
		}
	}
	
	
}
