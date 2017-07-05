package com.gsafety.starscream.basedata.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsafety.starscream.basedata.model.Attach;
import com.gsafety.starscream.basedata.repository.AttachRepository;
import com.gsafety.starscream.basedata.service.AttachService;
import com.gsafety.starscream.basedata.view.AttachDTO;
import com.gsafety.starscream.utils.AttachUtils;
import com.gsafety.starscream.utils.db.PrimaryKeyUtils;
import com.gsafety.util.json.JsonUtils;

/**
 * 附件Service实现
 * 
 * @author chenwenlong
 *
 */
@Service("attachService")
@Transactional
public class AttachServiceImpl implements AttachService {

	@Resource
	private AttachRepository attachRepository;

	/**
	 * 附件保存操作
	 * @param attach
	 * @return
	 */
	@Override
	public Attach save(Attach attach) {
		//id为空则有系统生成ID
		if(attach.getId()==null) {
			attach.setId(PrimaryKeyUtils.getFormatTimeKey());
		}
		return attachRepository.save(attach);
	}

	/**
	 * 修改关联ID
	 * 先删除已经删除的ID 再执行更新
	 * @param attachIds
	 * @param string
	 */
	@Override
	public void updateReferId(String[] attachIds, String referId){
		if(StringUtils.isNotEmpty(referId)){
			if(attachIds==null || attachIds.length == 0){
				attachRepository.deleteAllByReferId(referId);
			}else{
				attachRepository.deleteAttachReferId(attachIds, referId);
				attachRepository.updateReferId(attachIds, referId);
			}
		}
	}
	
	/**
	 * 附件删除操作
	 * @param id
	 */
	@Override
	public void delete(String id) {
		attachRepository.delete(id);
	}

	/**
	 * 根据ID查找附件
	 * @param id
	 * @return
	 */
	@Override
	public Attach findById(String id) {
		Attach attach = attachRepository.findById(id);
		if(attach!= null){
			attach.setThumbnailPath(AttachUtils.getThumbnailPath(attach.getSuffix(), attach.getAttachPath()));
		}
		return attach;
	}

	/**
	 * 根据IDs查找附件
	 * @param ids
	 * @return
	 */
	@Override
	public List<Attach> findByIds(String[] ids) {
		if(ids==null||ids.length==0) {
			return new ArrayList<Attach>();
		}
		return attachRepository.findByIds(ids);
	}
	
	/**
	 * 根据关联ID查找附件
	 * @param referId
	 * @return
	 */
	@Override
	public List<Attach> findByReferId(String referId) {
		return attachRepository.findByReferIdOrderByCreateTimeAsc(referId);
	}
	

	
	/**
	 * 根据关联ID查找附件，并返回json string
	 * @param referId
	 * @return
	 */
	@Override
	public String getJsonStrByReferId(String referId) {
		if(StringUtils.isEmpty(referId)){
			return null;
		}
		List<AttachDTO> attachDTOList = new ArrayList<AttachDTO>();
		List<Attach> attachList = this.findByReferId(referId);
		for (Attach attach : attachList){
			attachDTOList.add(new AttachDTO(attach));
		}
		
		return JsonUtils.getJsonStr(attachDTOList);
	}
}
