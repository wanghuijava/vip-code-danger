package com.gsafety.starscream.basedata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.gsafety.starscream.basedata.model.Attach;

/**
 * 附件Repository
 * @author chenwenlong
 *
 */
public interface AttachRepository extends Repository<Attach,String>{

	/**
	 * 添加附件
	 * @param attach
	 * @return
	 */
	public Attach save(Attach attach);

	/**
	 * 删除附件
	 * @param id
	 */
	public void delete(String id);

	/**
	 * 查询附件
	 * @param id
	 * @return
	 */
	public Attach findById(String id);

	/**
	 * 根据关联ID 查询附件
	 * @param referId
	 * @return
	 */
	public List<Attach> findByReferIdOrderByCreateTimeAsc(String referId);

	/**
	 * 删除已删除的附件ID
	 * @title deleteAttachReferId
	 * @description TODO
	 * @param @param attachIds
	 * @param @param referId
	 * @return void
	 * @author raomengwen
	 * @Date 2015-6-4/下午6:03:41
	 */
	@Modifying
	@Query("delete Attach a where a.referId=:referId and  a.id not in :attachIds")
	public void deleteAttachReferId(@Param("attachIds")String[] attachIds, @Param("referId")String referId);
	
	/**
	 * 删除关联ID的所有附件
	 * @title deleteAttachReferId
	 * @param @param referId
	 * @return void
	 * @author wanghui
	 * @Date 2015-8-28/下午5:03:41
	 */
	@Modifying
	@Query("delete Attach a where a.referId=:referId ")
	public void deleteAllByReferId(@Param("referId")String referId);
	
	/**
	 * 修改附件关联ID
	 * @param attachIds
	 * @param referId
	 */
	@Modifying
	@Query("update Attach a set a.referId=:referId where a.id in :attachIds")
	public void updateReferId(@Param("attachIds")String[] attachIds,@Param("referId")String referId);

	
	/**
	 * 根据IDs查找附件
	 * @param ids
	 * @return
	 */
	@Query("from Attach attach where attach.id in :ids")
	public List<Attach> findByIds(@Param("ids")String[] ids);

	

	
}
