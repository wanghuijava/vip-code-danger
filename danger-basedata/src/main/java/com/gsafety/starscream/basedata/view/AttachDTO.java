package com.gsafety.starscream.basedata.view;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.gsafety.starscream.basedata.model.Attach;
import com.gsafety.starscream.utils.AttachUtils;

/**
 * 附件DTO
 * @author wanghui
 *
 */
public class AttachDTO {

	private String id;            //附件ID 【主键】
	
	private String referId;       //关联ID
	
	private String name;          //附件名称
	
	private String suffix;        //附件后缀
	
	private String attachPath;    //附件路径
	
	private String thumbnailPath;    //如果是图片，则有缩略图路径
	
	private Date createTime;      //附件创建时间

	private String notes;    //备注
	
	private String state;   //富文本框用到的
	
	public AttachDTO (Attach model){
		BeanUtils.copyProperties(model, this);
		this.thumbnailPath = AttachUtils.getThumbnailPath(model.getSuffix(), model.getAttachPath());
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReferId() {
		return referId;
	}

	public void setReferId(String referId) {
		this.referId = referId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getAttachPath() {
		return attachPath;
	}

	public void setAttachPath(String attachPath) {
		this.attachPath = attachPath;
	}
	
	public String getThumbnailPath() {
		return thumbnailPath;
	}

	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public AttachDTO(){}
	
	public AttachDTO(String id) {
		this.id = id;
	}


	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
