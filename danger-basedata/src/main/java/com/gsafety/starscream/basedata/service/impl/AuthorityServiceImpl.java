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

import com.gsafety.starscream.basedata.model.Authority;
import com.gsafety.starscream.basedata.model.Role;
import com.gsafety.starscream.basedata.model.User;
import com.gsafety.starscream.basedata.repository.AuthorityRepository;
import com.gsafety.starscream.basedata.service.AuthorityService;
import com.gsafety.starscream.basedata.service.RoleService;
import com.gsafety.starscream.basedata.service.UserService;
import com.gsafety.starscream.utils.SysRoleUtils;
import com.gsafety.starscream.utils.db.PrimaryKeyUtils;

/**
 * 权限Service
 * @author chenwenlong
 *
 */
@Service("authorityService")
@Transactional
public class AuthorityServiceImpl implements AuthorityService {

	@Resource
	private AuthorityRepository authorityRepository;
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private UserService userService;
	
	/**
	 * 保存权限
	 * @param authority
	 * @return
	 */
	@Override
	public Authority save(Authority authority) {
		if (StringUtils.isEmpty(authority.getId())) {
			authority.setId(PrimaryKeyUtils.getStringTimeKey());
		}
		return authorityRepository.save(authority);
	}

	/**
	 * 根据ID删除权限
	 * @param id
	 */
	@Override
	public void delete(String id) {
		if(id==null) {
			return ;
		}
		Authority oldAuthority = findById(id);
		//用户权限修改
		String oldUserAuthority = "\""+id+"\":\""+oldAuthority.getUrl()+"\"";
		User user = new User();
		user.setAuthority(oldUserAuthority);
		List<User> users = userService.findAll();
		for(User u:users) {
			String userAuthority = u.getAuthority();
			if(userAuthority.indexOf(oldUserAuthority+",")!=-1) {
				userAuthority = userAuthority.replaceAll(oldUserAuthority+",", "");
			} else {
				userAuthority = userAuthority.replaceAll(oldUserAuthority, "");
			}
			u.setAuthority(userAuthority);
		}
		//刷新角色
		SysRoleUtils.roleMap.clear();
		List<Role> roleList = roleService.findAll();
		for(Role role : roleList){
			List<Authority> aus = new ArrayList<Authority>();
			for(Authority authority:role.getAuthoritys()) {
				if(authority.getId()!=id) {
					aus.add(authority);
				}
			}
			role.setAuthoritys(aus);
			SysRoleUtils.roleMap.put(role.getId(), aus);
		}
		//删除权限
		authorityRepository.delete(id);
	}

	/**
	 * 更新权限
	 * @param authority
	 * @return
	 */
	@Override
	public Authority update(Authority authority) {
		Authority oldAuthority = findById(authority.getId());
		if(StringUtils.isNotEmpty(authority.getName())) {
			oldAuthority.setName(authority.getName());
		}
		if(!oldAuthority.getUrl().equals(authority.getUrl())) {
			String authorityId = oldAuthority.getId();
			String oldUserAuthority = "\""+authorityId+"\":\""+oldAuthority.getUrl()+"\"";
			String newUserAuthority = "\""+authorityId+"\":\""+authority.getUrl()+"\"";
			User user = new User();
			user.setAuthority(oldUserAuthority);
			List<User> users = userService.findAll();
			for(User u:users) {
				String userAuthority = u.getAuthority();
				userAuthority = userAuthority.replaceAll(oldUserAuthority, newUserAuthority);
				u.setAuthority(userAuthority);
			}
			oldAuthority.setUrl(authority.getUrl());
		}
		//刷新角色
		SysRoleUtils.refreshRoleMap();
		
		return authority;
	}

	/**
	 * 根据ID查询权限
	 * @param id
	 * @return
	 */
	@Override
	public Authority findById(String id) {
		return authorityRepository.findOne(id);
	}
	

	public int findCount(Authority authority){
		int count=(int)authorityRepository.count();
		return count;
	}

	/**
	 * 分页查询权限
	 * @return
	 */
	public Page<Authority> find(Authority authority,Pageable page){
		return authorityRepository.findAll(new AuthoritySpecification(authority), page);
	}
	
	class AuthoritySpecification implements Specification<Authority> {

		private Authority authority;

		public AuthoritySpecification(Authority authority) {
			this.authority = authority;
		}

		// 查询条件拼接
		public Predicate toPredicate(Root<Authority> root, CriteriaQuery<?> query,
				CriteriaBuilder cb) {
			if (authority == null) {
				return null;
			}
			List<Predicate> list = new ArrayList<Predicate>();
			// 条件查询
			if (StringUtils.isNotEmpty(authority.getName())) {
				list.add(cb.like(root.get("name").as(String.class), "%" + authority.getName() + "%"));
			}
			Predicate[] p = new Predicate[list.size()];
			System.out.println(list.toArray(p));
			return cb.and(list.toArray(p));
		}
	}
}
