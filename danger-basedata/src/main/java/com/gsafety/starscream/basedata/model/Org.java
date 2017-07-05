package com.gsafety.starscream.basedata.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * 组织机构model
 * @author chenwenlong
 *
 */
@Entity
@Table(name="bas_org")
public class Org implements Serializable{

	private static final long serialVersionUID = 2741311459619742512L;
	
	@Id
	@Column(name="ORG_CODE")
	private String orgCode;         //机构编码【主键】
	
	@Column(name="ORG_TYPE_CODE")
	private Integer typeCode;       //机构类型代码（上级:1，下级:0）
	
	@Column(name="ORG_NAME")
	private String orgName;         //机构名称
	
	@Column(name="ORG_ICON")
	private String orgIcon;         //机构图标
	
	@Column(name="ORG_NAME_SHORT")
	private String orgShortName;    //机构简称
	
	@Column(name="ORG_SPELLING_FULL")
	private String fullSpelling;    //机构全拼
	
	@Column(name="ORG_SPELLING_SHORT")
	private String shortSpelling;   //机构简拼
	
	@Column(name="ORG_ADDRESS")
	private String address;         //机构地址
	
	@Column(name="ORG_INFO")
	private String orgInfo;         //机构描述
	
	@Column(name="ORG_PRINCIPAL")
	private String principal;       //负责人
	
	@Column(name="ORG_CONTACT_TEL")
	private String contactTel;      //联系电话
	
	@Column(name="ORG_CONTACT_INFO")
	private String contactInfo;     //其他联系方式
	
	@Column(name="ORG_FAX")
	private String fax;             //传真
	
	@Column(name="ORG_ORDERNUM")
	private int orderNum;           //排序
	
	@Column(name="ORG_ISLEAF")
	private int isLeaf;             //是否叶子节点；（默认：0，是叶子节点：1）
	
	@Column(name="CREATE_TIME")
	private Date createTime;        //创建时间
	
	@Column(name="UPDATE_TIME")
	private Date updateTime;        //更新时间
	
	@Column(name="ORG_PARENT_CODE")
	private String parentCode;      //父级机构
	
	@Column(name="ORG_DIST_CODE")
	private String districtCode;    //所在行政区划
	
	@Column(name="CHARGEORG")
	private String chargeOrg;      //维护机构 by wanghui 0417
	
	@Column(name="ORG_LEVEL")
	private String orgLevel;      //维护机构 by wanghui 0420

	@Transient
	private List<Org> children;     //子级组织机构
	
	@Transient
	private String ignoreOrgCode;   //排除该机构编码，用于查询（为“true”排除当前用户机构）
	
	@ManyToMany(targetEntity=Role.class,fetch=FetchType.EAGER)
	@JoinTable(
			name = "BAS_SYS_ORG_ROLE",
			joinColumns ={@JoinColumn(name="ORGCODE")},
			inverseJoinColumns={@JoinColumn(name="ROLE_ID")}
	)
	private Set<Role> roles;  //权限列表
	
	
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Integer getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(Integer typeCode) {
		this.typeCode = typeCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgIcon() {
		return orgIcon;
	}

	public void setOrgIcon(String orgIcon) {
		this.orgIcon = orgIcon;
	}

	public String getOrgShortName() {
		return orgShortName;
	}

	public void setOrgShortName(String orgShortName) {
		this.orgShortName = orgShortName;
	}

	public String getFullSpelling() {
		return fullSpelling;
	}

	public void setFullSpelling(String fullSpelling) {
		this.fullSpelling = fullSpelling;
	}

	public String getShortSpelling() {
		return shortSpelling;
	}

	public void setShortSpelling(String shortSpelling) {
		this.shortSpelling = shortSpelling;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOrgInfo() {
		return orgInfo;
	}

	public void setOrgInfo(String orgInfo) {
		this.orgInfo = orgInfo;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public int getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(int isLeaf) {
		this.isLeaf = isLeaf;
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

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public List<Org> getChildren() {
		return children;
	}

	public void setChildren(List<Org> children) {
		this.children = children;
	}

	public String getIgnoreOrgCode() {
		return ignoreOrgCode;
	}

	public void setIgnoreOrgCode(String ignoreOrgCode) {
		this.ignoreOrgCode = ignoreOrgCode;
	}
	
	public String getChargeOrg() {
		return chargeOrg;
	}

	public void setChargeOrg(String chargeOrg) {
		this.chargeOrg = chargeOrg;
	}

	public String getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}

	public Org(){}
	
	public Org(String orgCode){
		this.orgCode = orgCode;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public String[] getRoleIdAndName(){
		String[] roleIdAndName = new String[2];
		if(getRoles().size()>0){
			String ids= "";
			String names= "";
			int i = 0;
			for (Role r : getRoles()) {
				if(i == 0 ){
					ids = r.getId();
					names = r.getName();
				}else {
					ids += ","+ r.getId();
					names += ","+ r.getName();
				}
				i++;
			}
			roleIdAndName[0]= ids;
			roleIdAndName[1]= names;
		}
		return roleIdAndName;
	}
}
