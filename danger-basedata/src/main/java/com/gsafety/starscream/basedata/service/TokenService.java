package com.gsafety.starscream.basedata.service;

import com.gsafety.starscream.basedata.model.Token;
import com.gsafety.starscream.basedata.model.User;


/**
 * 系统用户登录token Service
 * 
 * @author chenwenlong
 *
 */
public interface TokenService {
	
	public static int LOGIN_TYPE_PC = 0;
	
	/**
	 * 根据用户名和设备类型查询token
	 * @param loginName
	 * @param loginType
	 * @return
	 */
	public Token findByLoginNameType(String loginName,int loginType);
	
	/**
	 * 根据tokenCode取得token信息
	 * @param tokenCode
	 * @return
	 */
	public Token findTokenByTokenCode(String tokenCode);
	
	/**
	 * 保存token
	 * @param token
	 */
	public void save(Token token);
	
	/**
	 * 根据用户名和设备类型删除token
	 * @param loginName
	 * @param loginType
	 */
	public void delete(String loginName,int loginType);

	/**
	 * 根据tokenCode删除token
	 * @param tokenCode
	 */
	public void delete(String tokenCode);
	
	/**
	 * 从数据库中查找匹配token，并刷新MCache
	 * @param tokenCode
	 * @return
	 */
	public User findByTokenCode(String tokenCode);
}
