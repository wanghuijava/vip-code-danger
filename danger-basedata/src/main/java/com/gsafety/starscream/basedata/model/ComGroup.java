package com.gsafety.starscream.basedata.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 常用分组
 * @author chenwenlong
 *
 */
@Entity
@Table(name="bas_comuse_group")
public class ComGroup implements Serializable{

	private static final long serialVersionUID = 1562659510324690562L;

	@Id
	@Column(name="GROUP_ID")
	private String id;               //常用分组ID 【主键】
	
	@Column(name="GROUP_PARENT_ID")
	private String parentId;         //常用分组ID 【主键】
	
	@Column(name="GROUP_NAME")
	private String groupName;         //分组名称
	
	@Column(name="GROUP_INFO")
	private String groupInfo;         //分组描述
	
	@Column(name="CREATE_TIME")
	private Date createTime;          //分组创建时间
	
	@Column(name="UPDATE_TIME")
	private Date updateTime;          //分组更新时间

	@Transient
	private List<ComGroup> children;  //子级常用组
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupInfo() {
		return groupInfo;
	}

	public void setGroupInfo(String groupInfo) {
		this.groupInfo = groupInfo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<ComGroup> getChildren() {
		return children;
	}

	public void setChildren(List<ComGroup> children) {
		this.children = children;
	}


	
}
