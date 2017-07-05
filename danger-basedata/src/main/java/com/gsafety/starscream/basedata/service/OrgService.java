package com.gsafety.starscream.basedata.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.gsafety.starscream.basedata.model.Org;
import com.gsafety.starscream.exception.SequenceNoValException;
import com.gsafety.starscream.utils.page.ScrollPage;

/**
 * 组织机构Service
 * @author chenwenlong
 *
 */
public interface OrgService {

	/**
	 * 保存组织机构
	 * @param org
	 * @param distCode 行政区划编码
	 * @return
	 */
	public Org save(Org org) throws SequenceNoValException;
	
	/**
	 * 删除组织机构
	 * @param orgCode
	 */
	public void delete(String orgCode);
	
	/**
	 * 更新组织机构
	 * @param org
	 * @return
	 */
	public Org update(Org org);
	
	/**
	 * 查询组织机构数量，根据父级机构编码
	 * @param parentCode
	 * @return
	 */
	public int findCountByParentCode(String parentCode);
	
	/**
	 * 查询组织机构，根据组织机构编码
	 * @param orgCode
	 * @return
	 */
	public Org findByOrgCode(String orgCode);
	
	/**
	 * 查询组织机构，根据父级机构编码
	 * @param parentCode
	 * @return
	 */
	public List<Org> findByParentCode(String parentCode);
	
	/**
	 * 查询组织机构，根据父级机构模糊查询
	 * @param parentCode
	 * @return
	 */
	public List<Org> findLikeParentCode(String parentCode);
	
	/**
	 * 查询组织机构，根据行政区划编码
	 * @param distCode
	 * @return
	 */
	public List<Org> findByDistCode(String distCode);
	/**
	 * 根据roleid 查询属于此角色的所有机构
	 * @param role
	 * @return
	 */
	public List<String> findByRole(String roleId);
	public String findRolesByOrgCode(String orgCode);
	
	/**
	 * 分页查询组织机构
	 * @param start
	 * @param count
	 * @param sort
	 * @param org
	 * @return
	 */
	public Page<Org> find(int start,int count,Sort sort,Org org);

	/**
	 * 滚动查询组织机构
	 * @param scrollPage
	 * @param org
	 * @return
	 */
	public List<Org> find(ScrollPage scrollPage, Org org);
	
	/**
	 * 查询树形机构
	 * @return
	 */
	public List<Org> findTree(String code);
	
	/**
	 * 根据关键字查询模糊匹配组织机构名称
	 * @param keyword
	 * @return
	 */
	public List<String> getSearchList(Long maxRows, String keyword);
	
	/**
	 * 向上移动
	 * @param orgCode
	 */
	public void moveUp(String orgCode);

	/**
	 * 向下移动
	 * @param orgCode
	 */
	public void moveDown(String orgCode);

	/**
	 * 查询下级机构并且为叶子节点的机构
	 * @param orgName
	 * @return
	 */
	public List<String> findOrgByOrgTypeCode();
	
	
}
