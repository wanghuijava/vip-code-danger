package com.gsafety.starscream.basedata.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 系统用户（可登陆的用户）
 * 系统用户分为两部分：
 *     特殊用户（管理员），这些用户不在组织用户OrgUser下
 *     非特殊用户（组织机构用户OrgUser的子集）
 * 
 * @author chenwenlong
 *
 */
@Entity
@Table(name="bas_sys_user")
public class User implements Serializable{

	private static final long serialVersionUID = 6850794724594014995L;
	@Column(name="USER_ID")
	private String id;       //用户编号（对应组织机构下用户的ID，没有对应的则为管理员）

	@Id
	@Column(name="USER_LOGIN_NAME")
	private String username;  //用户名【主键】
	
	@Column(name="USER_PWD")
	private String password;  //用户密码
	
	@Column(name="USER_TYPE_CODE")
	private Integer typeCode; //用户类型代码（超级用户：9，系统管理员：7，上级用户：1，下级用户：0）
	
	@Column(name="USER_ORG_CODE")
	private String orgCode;   //机构编码
	
	@Column(name="USER_ORG_NAME")
	private String orgName;   //机构名称
	
	@Column(name="USER_STATUS")
	private Integer status;   //用户状态（正常：1，注销：0）
	
	@Column(name="USER_ROLE")
	private String roles;     //角色（权限以“,”分割的字符串）
	
	@Column(name="USER_ROLENAME")
	private String roleNames;     //角色（权限以“,”分割的字符串）
	
	@Column(name="USER_AUTHORITY")
	private String authority; //权限列表（JSON格式字符串）
	
	@Column(name="CREATE_TIME")
	private Date createTime;  //用户创建时间
	
	@Column(name="UPDATE_TIME")
	private Date updateTime;  //用户更新时间
	
	@Transient
	private Org org;          //用户所在组织机构
	
	@Transient
	private OrgUser orgUser;          //用户所在组织机构


	public boolean isSuperior() {
		if(1==this.typeCode){
			return true;
		}
		return false;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(Integer typeCode) {
		this.typeCode = typeCode;
	}
	
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
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

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public OrgUser getOrgUser() {
		return orgUser;
	}

	public void setOrgUser(OrgUser orgUser) {
		this.orgUser = orgUser;
	}
	
	
}