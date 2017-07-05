package com.gsafety.starscream.basedata.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 权限实体
 * @author chenwenlong
 *
 */
@Entity
@Table(name="bas_sys_authority")
public class Authority implements Serializable{

	private static final long serialVersionUID = 3281625005941217574L;

	@Id
	@Column(name="AUTHORITY_ID")
	private String id;    //权限ID 【主键】
	
	@Column(name="AUTHORITY_NAME")
	private String name;   //权限名称
	
	@Column(name="AUTHORITY_URL")
	private String url;    //权限访问URL

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
