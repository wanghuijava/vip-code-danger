package com.gsafety.starscream.basedata.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
 /**
 * @author 朱泽江
 * @since 2015-2-3 下午4:44:08
 * @version v0.0.1
 * @declare 应急资源   地区级别  实体
1	国家级
2	自治区/省级
3	地市级
4	区县级
5	乡镇级
6	其它
 */
@Entity
@Table(name="BAS_AREA_LEVEL")
public class AreaLevel implements Serializable {
	
	private static final long serialVersionUID = -4515494062862124001L;

	/**
	 * 级别 代码主键
	 */
	@Id
	@Column(name="LEVELCODE") 
	private String id;
	
	/**
	 * 名称 
	 */
	@Column(name="LEVELNAME")
	private String name;
	
	
	public AreaLevel() {
	}


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
	
	
}
