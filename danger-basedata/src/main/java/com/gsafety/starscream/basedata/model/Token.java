package com.gsafety.starscream.basedata.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * 标记是否登录
 * @author chenwenlong
 *
 */
@Entity
@IdClass(TokenPK.class)
@Table(name="bas_sys_token")
public class Token implements Serializable{

	private static final long serialVersionUID = 2080923631484256838L;

	@Id
	@Column(name="USER_LOGIN_NAME")
	private String loginName;  //用户登录名 【主键】
	
	@Id
	@Column(name="USER_LOGIN_TYPE")
	private int loginType;     //登录类型 （PC：0，手机：1）
	
	@Column(name="USER_TOKEN")
	private String tokenCode;  //登录token值 （sessionId的md5加密）
	
	@Column(name="CREATE_TIME")
	private Date createTime;   //创建时间
	
	@Column(name="EXPIRE_TIME")
	private Date expireTime;   //失效时间

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public int getLoginType() {
		return loginType;
	}

	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}

	public String getTokenCode() {
		return tokenCode;
	}

	public void setTokenCode(String tokenCode) {
		this.tokenCode = tokenCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	
	public Token(){}
	
	public Token(String loginName,int loginType,String tokenCode,Date createTime,Date expireTime){
		this.loginName = loginName;
		this.loginType = loginType;
		this.tokenCode = tokenCode;
		this.createTime = createTime;
		this.expireTime = expireTime;
	}
}
