package com.gsafety.starscream.basedata.model;

import java.io.Serializable;

/**
 * 联合组建
 * @author chenwenlong
 *
 */
public class TokenPK implements Serializable {

	private static final long serialVersionUID = -3397199953078696774L;
	
	private String loginName;  //用户登录名 【主键】
	
	private int loginType;     //登录类型 （PC：0，手机：1）

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
	
	public TokenPK(){}
	
	public TokenPK(String loginName,int loginType){
		this.loginName = loginName;
		this.loginType = loginType;
	}
}
