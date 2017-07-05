package com.gsafety.starscream.basedata.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 行政区划model
 * @author chenwenlong
 *
 */
@Entity
@Table(name="bas_district")
public class District implements Serializable{

	private static final long serialVersionUID = -4005980350536998048L;
	
	@Id
	@Column(name="DIST_CODE")
	private String distCode;         //行政区划编码【主键】
	
	@Column(name="DIST_NAME")
	private String distName;         //行政区划名称
	
	@Column(name="DIST_SHORTNAME")
	private String distShortName;    //行政区划简称
	
	@Column(name="DIST_SPELLING_FULL")
	private String fullSpelling;     //名称全称
	
	@Column(name="DIST_SPELLING_SHORT")
	private String shortSpelling;    //名称简称
	
	@Column(name = "DIST_LONGITUDE")
	private BigDecimal longitude;         //行政区划经度
	
	@Column(name = "DIST_LATITUDE")
	private BigDecimal latitude;          //行政区划纬度
		
	@Column(name="DIST_NOTES")
	private String distNotes;        //行政区划备注
	
	@Column(name="DIST_PARENT_CODE")
	private String parentCode;       //上级行政区划
	
	@Column(name="DIST_SORTNUM")
	private Integer sortNum;       //行政区划排序

	@Transient
	private List<District> children; //子级行政区划
	
	public String getDistCode() {
		return distCode;
	}

	public void setDistCode(String distCode) {
		this.distCode = distCode;
	}

	public String getDistName() {
		return distName;
	}

	public void setDistName(String distName) {
		this.distName = distName;
	}

	public String getDistShortName() {
		return distShortName;
	}

	public void setDistShortName(String distShortName) {
		this.distShortName = distShortName;
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

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public void setShortSpelling(String shortSpelling) {
		this.shortSpelling = shortSpelling;
	}

	public String getDistNotes() {
		return distNotes;
	}

	public void setDistNotes(String distNotes) {
		this.distNotes = distNotes;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public List<District> getChildren() {
		return children;
	}

	public void setChildren(List<District> children) {
		this.children = children;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
 
}
