package com.gsafety.starscream.basedata.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 附件model
 * @author chenwenlong
 *
 */
@Entity
@Table(name="bas_attach")
public class Attach implements Serializable{

	private static final long serialVersionUID = -2122931549839194841L;

	@Id
	@Column(name="ATTACH_ID")
	private String id;            //附件ID 【主键】
	
	@Column(name="ATTACH_REFER_ID")
	private String referId;       //关联ID
	
	@Column(name="ATTACH_NAME")
	private String name;          //附件名称
	
	@Column(name="ATTACH_SUFFIX")
	private String suffix;        //附件后缀
	
	@Column(name="ATTACH_PATH")
	private String attachPath;    //附件路径
	
	@Column(name="CREATE_TIME")
	private Date createTime;      //附件创建时间
	@Column(name="ATTACH_NOTES")
	private String notes;      //附件创建时间
	
	@Transient
	private String thumbnailPath;    //如果是图片，则有缩略图路径
	

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Attach(){}
	
	public Attach(String id) {
		this.id = id;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getThumbnailPath() {
		return thumbnailPath;
	}

	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}
	
	
}
