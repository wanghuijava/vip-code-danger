package com.gsafety.starscream.basedata.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsafety.starscream.basedata.model.Org;
import com.gsafety.starscream.basedata.model.Role;
import com.gsafety.starscream.basedata.repository.OrgRepository;
import com.gsafety.starscream.basedata.service.OrgService;
import com.gsafety.starscream.constant.BasedataConstant;
import com.gsafety.starscream.exception.SequenceNoValException;
import com.gsafety.starscream.utils.db.PrimaryKeyUtils;
import com.gsafety.starscream.utils.page.ScrollPage;

/**
 * 组织机构Service实现
 * @author chenwenlong
 *
 */
@Service("orgService")
public class OrgServiceImpl implements OrgService {

	@Resource
	private OrgRepository orgRepository;     //组织机构service注入
	
	public List<String> getSearchList(Long maxRows, String keyword){
		return orgRepository.findByKeyword(maxRows, keyword);
	}
	
	/**
	 * 保存组织机构
	 * @param org
	 * @param distCode 行政区划编码
	 * @return
	 * @throws SequenceNoValException 
	 */
	@Override
	@Transactional
	public Org save(Org org) throws SequenceNoValException {
		//生成orgCode
		if (StringUtils.isEmpty(org.getOrgCode())) {
//			组织机构orgCode 生成 策略:行政区划代码+四位编码
			org.setOrgCode(PrimaryKeyUtils.getSequenceKey(org.getDistrictCode(), 4));
			org.setCreateTime(new Date());
			org.setOrderNum(PrimaryKeyUtils.getIntSequenceKey("org_orderNum"));
			org.setIsLeaf(BasedataConstant.IS_LEAF);
		}
//		org.setFullSpelling(PinyinUtils.getFullSpelling(org.getOrgName()));
//		org.setShortSpelling(PinyinUtils.getShortSpelling(org.getOrgName()));
		org.setUpdateTime(new Date());
		
		if(org.getTypeCode() == null){
			org.setTypeCode(BasedataConstant.TYPE_CODE_XJ);
		}
		if(org.getOrgShortName() == null){
			org.setOrgShortName(org.getOrgName());
		}
		
		org = orgRepository.save(org);
		//更新父级机构是否是叶子节点
		if (StringUtils.isNotEmpty(org.getParentCode())) {
			Org parent = orgRepository.findOne(org.getParentCode());
			parent.setIsLeaf(BasedataConstant.NOT_LEAF);
		}
		return org;
	}

	/**
	 * 删除组织机构
	 * @param orgCode
	 */
	@Override
	@Transactional
	public void delete(String orgCode) {
		Org org = orgRepository.findOne(orgCode);
		//如果传递机构编码不正确，直接返回
		if(org == null) {
			return;
		}else{
			List<Org> list = orgRepository.findByParentCodeOrderByOrgCodeAsc(orgCode);
			if(list.size()==0){
				orgRepository.delete(orgCode);  
			}else{
				for(Org orgs:list){
					delete(orgs.getOrgCode());
				}
				orgRepository.delete(orgCode);        //删除组织机构
			}
		}
		
		if (StringUtils.isEmpty(org.getParentCode())||findCountByParentCode(org.getParentCode())>0) {
			return ;
		}
		//修改父级组织机构是否是叶子节点
		Org parent = orgRepository.findOne(org.getParentCode());
		parent.setIsLeaf(BasedataConstant.IS_LEAF);
	}

	/**
	 * 更新组织机构
	 * @param org
	 * @return
	 */
	@Override
	@Transactional
	public Org update(Org org) {
		return orgRepository.save(org);
	}

	/**
	 * 查询组织机构数量，根据父级机构编码
	 * @param parentCode
	 * @return
	 */
	@Override
	public int findCountByParentCode(String parentCode) {
		//当传递父级CODE为空时，查询子级机构数量
		if(StringUtils.isEmpty(parentCode)) {
			return orgRepository.findCountByParentCodeIsNull();
		}
		return orgRepository.findCountByParentCode(parentCode);
	}
	
	/**
	 * 查询组织机构，根据组织机构编码
	 * @param orgCode
	 * @return
	 */
	@Override
	public Org findByOrgCode(String orgCode) {
		return orgRepository.findOne(orgCode);
	}

	/**
	 * 查询组织机构，根据父级机构编码
	 * @param parentCode
	 * @return
	 */
	@Override
	public List<Org> findByParentCode(String parentCode) {
		return orgRepository.findByParentCodeOrderByOrgCodeAsc(parentCode);
	}
	
	/**
	 * 查询组织机构，根据父级机构模糊查询
	 * @param parentCode
	 * @return
	 */
	@Override
	public List<Org> findLikeParentCode(String parentCode) {
		return orgRepository.findLikeParentCode(parentCode);
	}

	/**
	 * 查询组织机构，根据行政区划编码
	 * @param distCode
	 * @return
	 */
	@Override
	public List<Org> findByDistCode(String distCode) {
		return orgRepository.findByDistrictCode(distCode);
	}

	public List<String> findByRole(String roleId){
		List<String> orgList = new ArrayList<String>();
		
		List<Object[]> objList = orgRepository.findOrgCodeByRoleId(roleId);
		if(objList.size()==0) return new ArrayList<String>();
		for (Object object : objList) {
			orgList.add((String) object);
		}
		
		return orgList;
	}
	
	
	/**
	 * 分页查询组织机构
	 * @param start
	 * @param count
	 * @param sort
	 * @param org
	 * @return
	 */
	public Page<Org> find(int start,int count,Sort sort,Org org){
		Pageable pageable = new PageRequest(start, count, sort);
		Page<Org> orgs = orgRepository.findAll(new OrgSpecification(org,sort),pageable);
		return orgs;
	}

	/**
	 * 滚动查询组织机构
	 * @param scrollPage
	 * @param org
	 * @return
	 */
	public List<Org> find(ScrollPage scrollPage, Org org){
		//id为空则查询所有数据
		if(StringUtils.isEmpty(scrollPage.getId())) {
			return (List<Org>)orgRepository.findAll(new OrgSpecification(org,null));
		}
		//分页ID不为空则表示滚动查询
		org.setOrgCode(scrollPage.getId());
		Page<Org> orgs = find(0,scrollPage.getCount(),scrollPage.getSort("orgCode"),org);
		return orgs.getContent();
	}
	
	/**
	 * 查询树形机构
	 */
	public List<Org> findTree(String code){
		List<Org> orgs = findByParentCode(code);
		for(Org org : orgs) {
			if(org.getIsLeaf()==0) {
				org.setChildren(findTree(org.getOrgCode()));
			}
		}
		return orgs;
	}
	
	/**
	 * 添加查询条件类
	 * @author Administrator
	 *
	 */
	class OrgSpecification implements Specification<Org>{

		private Org org;
		private Sort sort;
		
		public OrgSpecification(Org org,Sort sort){
			this.org = org;
			this.sort = sort;
		}
		
		//查询条件拼接
		public Predicate toPredicate(Root<Org> root, CriteriaQuery<?> query,
				CriteriaBuilder cb) {
			if(org==null) {
				return null;
			}
			List<Predicate> list = new ArrayList<Predicate>();
			//如果ID不为空，则进行滚动查询
			if(StringUtils.isNotEmpty(org.getOrgCode())) {
				if(sort.getOrderFor("orgCode").getDirection()==Direction.DESC) {
					list.add(cb.lessThan(root.get("orgCode").as(String.class), org.getOrgCode()));
				} else {
					list.add(cb.greaterThan(root.get("orgCode").as(String.class), org.getOrgCode()));
				}
			}
			if(!StringUtils.isEmpty(org.getOrgName())) {
				list.add(cb.like(root.get("orgName").as(String.class), "%"+org.getOrgName()+"%"));
			}
			//排除机构
			if(!StringUtils.isEmpty(org.getIgnoreOrgCode())) {
				list.add(cb.notEqual(root.get("orgCode").as(String.class), org.getIgnoreOrgCode()));
			}
			
			Predicate[] p = new Predicate[list.size()];
		    return cb.and(list.toArray(p));
		}
	}
	
	public void moveUp(String orgCode) {
		Org org = findByOrgCode(orgCode);
		Sort sort = new Sort(Direction.DESC,"orderNum");
		Pageable page = new PageRequest(0,1, sort);
		List<Org> orgs = orgRepository.findByParentCodeAndOrderNumLessThan(org.getParentCode(), org.getOrderNum(), page);
		if(orgs!=null&&orgs.size()!=0) {
			Org orgTemp = orgs.get(0);
			Integer tempOrderNum = orgTemp.getOrderNum();
			orgTemp.setOrderNum(org.getOrderNum());
			org.setOrderNum(tempOrderNum);
		}
	}

	public void moveDown(String orgCode) {
		Org org = findByOrgCode(orgCode);
		Sort sort = new Sort(Direction.ASC,"orderNum");
		Pageable page = new PageRequest(0,1, sort);
		List<Org> orgs = orgRepository.findByParentCodeAndOrderNumGreaterThan(org.getParentCode(), org.getOrderNum(), page);
		if(orgs!=null&&orgs.size()!=0) {
			Org orgTemp = orgs.get(0);
			Integer tempOrderNum = orgTemp.getOrderNum();
			orgTemp.setOrderNum(org.getOrderNum());
			org.setOrderNum(tempOrderNum);
		}
	}
	
	public List<String> findOrgByOrgTypeCode(){
		return orgRepository.findOrgByOrgTypeCode();
	}
	
	public String findRolesByOrgCode(String orgCode){
		Org org = orgRepository.findOne(orgCode);
		Set<Role> roles = org.getRoles();
		String str = "";
		boolean first = true;
		for (Role role : roles) {
			if(first){
				str = role.getId();
				first = false;
			}else {
				str += ","+role.getId();
			}
		}
		return str;
	}
}
