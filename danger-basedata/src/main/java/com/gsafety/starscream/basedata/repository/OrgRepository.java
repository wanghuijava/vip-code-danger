package com.gsafety.starscream.basedata.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.gsafety.starscream.basedata.model.Org;

/**
 * 组织机构repository
 * @author chenwenlong
 *
 */
public interface OrgRepository extends CrudRepository<Org,String>,JpaSpecificationExecutor<Org>{

	
	/**
	 * 删除所有子节点
	 * @param orgCode
	 */
	public void deleteByParentCode(String parentCode);
	
	/**
	 * 父级code为空查询子级组织机构数量
	 * @return
	 */
	@Query("select count(o) from Org o where o.parentCode is null")
	public int findCountByParentCodeIsNull();
	
	/**
	 * 根据父级code查询子级组织机构数量
	 * @param parentCode
	 * @return
	 */
	@Query("select count(o) from Org o where o.parentCode=:parentCode")
	public int findCountByParentCode(@Param("parentCode")String parentCode);

	/**
	 * 根据父级code查询子级组织机构列表
	 * @param parentCode
	 * @return
	 */
	public List<Org> findByParentCodeOrderByOrgCodeAsc(String parentCode);
	
	/**
	 * 根据父级code模糊查询子级组织机构列表
	 * @param parentCode
	 * @return
	 */
	@Query("select org from Org org where org.parentCode like %:parentCode")
	public List<Org> findLikeParentCode(@Param("parentCode") String parentCode);
	
	/**
	 * 根据行政区划查询组织机构列表
	 * @param distCode
	 * @return
	 */
	public List<Org> findByDistrictCode(String distCode);
	
	/**
	 * 根据关键字查询组织机构表
	 * 需处理匹配组织机构的中文名、全拼、简拼
	 * @param keyword
	 * @return 组织机构中文名称
	 */
	@Query("select o.orgName from Org o where (o.orgName like %:keyword% or fullSpelling like %:keyword% or shortSpelling like %:keyword%) and rownum <=:maxRows")
	public List<String> findByKeyword(@Param("maxRows")Long maxRows,@Param("keyword") String keyword);


	/**
	 * 排序下移用：取得同级节点下orderNum大于本节点的机构
	 * @param parentCode
	 * @param orderNum
	 * @param page
	 * @return
	 */
	public List<Org> findByParentCodeAndOrderNumGreaterThan(String parentCode,
			int orderNum, Pageable page);

	/**
	 * 排序上移用：取得同级节点下orderNum小于本节点的机构
	 * @param parentCode
	 * @param orderNum
	 * @param page
	 * @return
	 */
	public List<Org> findByParentCodeAndOrderNumLessThan(String parentCode,
			int orderNum, Pageable page);
	/**
	 * 根据机构名称 取 机构Code
	 * @param orgName
	 * @return
	 */
	public Org findByOrgName(String orgName);
	
	/**
	 * 查询下级机构并且为叶子节点的机构
	 * 是否叶子节点 默认值：0；1为是叶子节点
	 * 机构类型代码 上级机构:1 下级机构为:0
	 * @param orgName
	 * @return
	 */
	@Query("select o.orgCode from Org o where o.isLeaf = '1' and o.typeCode = '0' ")
	public List<String> findOrgByOrgTypeCode();
	
	@Query(value="select t.orgcode from bas_sys_org_role t where t.role_id =:roleId ",nativeQuery=true)
	public List<Object[]> findOrgCodeByRoleId(@Param("roleId")String roleId);
	
	
}
