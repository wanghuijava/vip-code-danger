package com.gsafety.starscream.basedata.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 角色实体
 * @author chenwenlong
 *
 */
@Entity
@Table(name="bas_sys_role")
public class Role implements Serializable{

	private static final long serialVersionUID = -983097092222778985L;

	@Id
	@Column(name="ROLE_ID")
	private String id;                  //角色ID 【主键】
	
	@Column(name="ROLE_NAME")
	private String name;                 //角色名称
	
	@Column(name="ROLE_TYPE_CODE")
	private Integer typeCode;            //角色类型（上级：1，下级：0，系统管理员：7，超级管理员：9）
	
	@Column(name="ROLE_COMMENTS")
	private String comments;             //角色描述注释
	

	@ManyToMany(targetEntity=Authority.class,fetch=FetchType.EAGER)
	@JoinTable(
			name = "bas_sys_role_authority",
			joinColumns ={@JoinColumn(name="ROLE_ID")},
			inverseJoinColumns={@JoinColumn(name="AUTHORITY_ID")}
	)
	private List<Authority> authoritys;  //权限列表
	
	@Transient
	private String[] authorityIds;      //权限Id列表

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(Integer typeCode) {
		this.typeCode = typeCode;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<Authority> getAuthoritys() {
		return authoritys;
	}

	public void setAuthoritys(List<Authority> authoritys) {
		this.authoritys = authoritys;
	}

	public String[] getAuthorityIds() {
		return authorityIds;
	}

	public void setAuthorityIds(String[] authorityIds) {
		this.authorityIds = authorityIds;
	}
}
