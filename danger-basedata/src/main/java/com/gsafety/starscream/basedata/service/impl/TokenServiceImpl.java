package com.gsafety.starscream.basedata.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsafety.starscream.basedata.model.Org;
import com.gsafety.starscream.basedata.model.OrgUser;
import com.gsafety.starscream.basedata.model.Token;
import com.gsafety.starscream.basedata.model.TokenPK;
import com.gsafety.starscream.basedata.model.User;
import com.gsafety.starscream.basedata.repository.TokenRepository;
import com.gsafety.starscream.basedata.service.OrgService;
import com.gsafety.starscream.basedata.service.OrgUserService;
import com.gsafety.starscream.basedata.service.TokenService;
import com.gsafety.starscream.basedata.service.UserService;
import com.gsafety.starscream.memcached.MCache;

/**
 * 系统用户登录token Service实现
 * 
 * @author chenwenlong
 *
 */
@Service("tokenService")
@Transactional
public class TokenServiceImpl implements TokenService {

	@Resource
	private TokenRepository tokenRepository;

	@Resource
	private UserService userService;
	
	@Resource
	private OrgService orgService;
	
	@Resource
	private OrgUserService orgUserService;
	
	/**
	 * 根据用户名和设备类型查询token
	 * @param loginName
	 * @param loginType
	 * @return
	 */
	@Override
	public Token findByLoginNameType(String loginName, int loginType) {
		return tokenRepository.findOne(new TokenPK(loginName,loginType));
	}
	
	/**
	 * 根据tokenCode取得token信息
	 * @param tokenCode
	 * @return
	 */
	@Override
	public Token findTokenByTokenCode(String tokenCode) {
		return tokenRepository.findBytokenCode(tokenCode);
	}

	/**
	 * 保存token
	 * @param token
	 */
	@Override
	public void save(Token token) {
		Token tokenTemp = tokenRepository.findToken(token.getLoginName(), token.getLoginType());
		//新增的token与数据库中token比对，不同则删除memcached中数据
		if(tokenTemp!=null&&!tokenTemp.getTokenCode().equals(token.getTokenCode())){
			MCache.delete(tokenTemp.getTokenCode());
			tokenTemp.setTokenCode(token.getTokenCode());
			tokenTemp.setCreateTime(token.getCreateTime());
			tokenTemp.setExpireTime(token.getExpireTime());
			return ;
		}
		tokenRepository.save(token);
	}

	/**
	 * 根据用户名和设备类型删除token
	 * @param loginName
	 * @param loginType
	 */
	@Override
	public void delete(String loginName, int loginType) {
		tokenRepository.delete(new TokenPK(loginName,loginType));
	}
	
	/**
	 * 根据用户名和设备类型删除token
	 * @param loginName
	 * @param loginType
	 */
	@Override
	public void delete(String tokenCode) {
		tokenRepository.deleteByTokenCode(tokenCode);
	}
	
	@Override
	public User findByTokenCode(String tokenCode) {
		//获取用户token数据
		Token token = tokenRepository.findBytokenCode(tokenCode);
		
		if(null == token){
			return null;
		}
		
		//刷新MCache缓存
		User user = userService.findByUsername(token.getLoginName());
		
		//Delete by DX
		//OrgUser中已经包含org信息，所以不需要另外设置进去
		//Org userOrg = orgService.findByOrgCode(user.getOrgCode());
		
		OrgUser orgUser = orgUserService.findById(user.getId());
		//user.setOrg(userOrg);
		user.setOrgUser(orgUser);
		MCache.set(tokenCode, user);
		return user;
	}
}

