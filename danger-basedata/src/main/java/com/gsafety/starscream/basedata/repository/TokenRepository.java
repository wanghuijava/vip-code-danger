package com.gsafety.starscream.basedata.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.gsafety.starscream.basedata.model.Token;
import com.gsafety.starscream.basedata.model.TokenPK;
/**
 * 系统用户登录token Repository层
 * @author chenwenlong
 *
 */
public interface TokenRepository extends CrudRepository<Token,TokenPK>{
	
	/**
	 * 根据用户登录名和设备类型查询token
	 * @param loginName
	 * @param loginType
	 * @return
	 */
	@Query("select t from Token t where t.loginName = :loginName and t.loginType = :loginType and t.expireTime >= CURRENT_TIMESTAMP")
	public Token findToken(@Param("loginName")String loginName, @Param("loginType")int loginType);
	
	/**
	 * 根据token编码查询token
	 * @param tokenCode
	 * @return
	 */
	public Token findBytokenCode(String tokenCode);

	/**
	 * 根据tokenCode删除token
	 * @param tokenCode
	 */
	public void deleteByTokenCode(String tokenCode);
}
