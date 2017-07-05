package com.gsafety.starscream.basedata.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 组织机构下的用户，通讯录用户
 * 
 * @author chenwenlong
 * @author wanghui 2015/4/17 增加备注
 *
 */
@Entity
@Table(name="bas_org_user")
public class OrgUser implements Serializable{

	private static final long serialVersionUID = -903444135372332892L;

	@Id
	@Column(name="USER_ID")
	private String id;          //用户编号【主键】
	
	@Column(name="USER_NAME")
	private String username;     //用户名
	
	@Column(name="USER_SPELLING_FULL")
	private String fullSpelling; //姓名全拼
	
	@Column(name="USER_SPELLING_SHORT")
	private String shortSpelling;//姓名简拼
	
	@Column(name="USER_SPELLING_FIRST")
	private String firstSpelling;//姓名首字母
	
	@Column(name="USER_SEX")
	private String sex;          //用户性别（默认：U，女：F，男：M）
	
	@Column(name="USER_OFFICETEL")
	private String officeTel;    //办公电话
	
	@Column(name="USER_MOBILEPHONE")
	private String mobileTel;    //用户手机
	
	@Column(name="USER_POSITION")
	private String position;     //用户职位
	
	@Column(name="USER_IMAGE")
	private String imageUrl;     //用户头像照片地址
	
	@Column(name="USER_STATUS")
	private Integer status;      //系统用户：2，正常：1，注销：0
	
	@Column(name="USER_ORDERNUM")
	private Integer orderNum;        //排序
	
	@Column(name="CREATE_TIME")
	private Date createTime;     //创建时间
	
	@Column(name="UPDATE_TIME")
	private Date updateTime;     //更新时间
	
	@ManyToOne(targetEntity=Org.class)
	@JoinColumn(name="USER_ORG_CODE")
	private Org org;             //组织机构（外键关联组织机构Org）
	
	@Column(name="USER_COMGROUP_IDS")
	private String comGroupIds;  //常用组ID
	
	@Transient
	private String orgCode;      //用户机构编码
	
	@Transient
	private String phoneNum;     //手机号码

	@Column(name="NOTES")
	private String notes;      //备注 by wanghui 0417
	
	@Column(name="USER_CONTACT_INFO")
	private String contactInfo;      //短号
	
	@Transient
	private String clickSearch; //是否点击了查询1是点击了  
	
	
	public OrgUser(){}
	public OrgUser(String id){
		this.id = id;
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

	public String getFirstSpelling() {
		return firstSpelling;
	}

	public void setFirstSpelling(String firstSpelling) {
		this.firstSpelling = firstSpelling;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getOfficeTel() {
		return officeTel;
	}

	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}
	
	public String getMobileTel() {
		return mobileTel;
	}

	public void setMobileTel(String mobileTel) {
		this.mobileTel = mobileTel;
	}
	
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
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

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getComGroupIds() {
		return comGroupIds;
	}

	public void setComGroupIds(String comGroupIds) {
		this.comGroupIds = comGroupIds;
	}
	
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}
	public String getClickSearch() {
		return clickSearch;
	}
	public void setClickSearch(String clickSearch) {
		this.clickSearch = clickSearch;
	}
	
	
}