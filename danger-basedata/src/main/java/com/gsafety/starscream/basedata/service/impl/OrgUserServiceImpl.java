package com.gsafety.starscream.basedata.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsafety.common.utils.PinyinUtils;
import com.gsafety.starscream.basedata.model.Org;
import com.gsafety.starscream.basedata.model.OrgUser;
import com.gsafety.starscream.basedata.repository.OrgUserRepository;
import com.gsafety.starscream.basedata.service.OrgService;
import com.gsafety.starscream.basedata.service.OrgUserService;
import com.gsafety.starscream.basedata.service.UserService;
import com.gsafety.starscream.constant.BasedataConstant;
import com.gsafety.starscream.utils.db.PrimaryKeyUtils;
import com.gsafety.starscream.utils.page.ScrollPage;

/**
 * 组织机构用户（通讯录用户）Service
 * @author chenwenlong
 *
 */
@Service("orgUserService")
public class OrgUserServiceImpl implements OrgUserService {

	@Resource
	private OrgUserRepository orgUserRepository;
	
	@Resource
	private UserService userService;
	@Autowired
	private OrgService orgService;

	public List<OrgUser> findByIds(String [] userIds){
		return orgUserRepository.findByIds(userIds);
	}
	
	public List<OrgUser> findOrgUserByOrgCode(String orgCode){
		return orgUserRepository.findByOrgs(orgCode);
	}
	
	/**
	 * 查找 该部门最重要的人物  1. 常用联系人 2。 排在第一的人物 
	 */
	@Override
	public OrgUser findFirstPersonByOrgCode(String orgCode) {
		List<OrgUser> list = null  ; 
		list=orgUserRepository.findByOrgs(orgCode);
		if(list.size()>1){
			return list.get(1);
		}else{
			return null ; 
		}
	}
	
	/**
	 * 根据ID查询用户
	 */
	public OrgUser findById(String id) {
		return orgUserRepository.findOne(id);
	}
	
	/**
	 * 根据用户名查询
	 */
	@Override
	public OrgUser findByUsername(String username) {
		return orgUserRepository.findByUsername(username);
	}
	
	/**
	 * 用户添加
	 */
	@Transactional
	public OrgUser save(OrgUser orgUser) {
		if (StringUtils.isEmpty(orgUser.getId())){
			orgUser.setId(PrimaryKeyUtils.getStringTimeKey());
			orgUser.setCreateTime(new Date());
			//用户状态，默认正常
			orgUser.setStatus(BasedataConstant.USER_STATUS_NOR);
		}
		
		orgUser.setFullSpelling(PinyinUtils.getFullSpelling(orgUser.getUsername()));
		orgUser.setShortSpelling(PinyinUtils.getShortSpelling(orgUser.getUsername()));
		
//		当用户名为全英文时，取首字母 会出错 zzj
//		orgUser.setFirstSpelling(PinyinUtils.getShortSpellingFirst(orgUser.getUsername()));

		if(StringUtils.isEmpty(orgUser.getSex())) {
			orgUser.setSex("0");
		}
		if(orgUser.getStatus()==null) {
			orgUser.setStatus(BasedataConstant.USER_STATUS_NOR);
		}
		orgUser.setUpdateTime(new Date());
		return orgUserRepository.save(orgUser);
	}
	
	public OrgUser createOrgUser(OrgUser orgUser){
		if (StringUtils.isEmpty(orgUser.getId())){
			orgUser.setId(PrimaryKeyUtils.getStringTimeKey());
			orgUser.setCreateTime(new Date());
			//用户状态，默认正常
			orgUser.setStatus(BasedataConstant.USER_STATUS_NOR);
		}
		orgUser.setFullSpelling(PinyinUtils.getFullSpelling("组织机构"));
		orgUser.setShortSpelling(PinyinUtils.getShortSpelling("组织机构")+"_org");
		orgUser.setFirstSpelling(PinyinUtils.getShortSpellingFirst("组织机构"));

		if(StringUtils.isEmpty(orgUser.getSex())) {
			orgUser.setSex("0");
		}
		if(orgUser.getStatus()==null) {
			orgUser.setStatus(BasedataConstant.USER_STATUS_NOR);
		}
		orgUser.setUpdateTime(new Date());
		return orgUserRepository.save(orgUser);
	}
	
	/**
	 * 常用组添加用户
	 * @param groupId
	 * @param userIds
	 */
	@Override
	public void addToGroup(String groupId,String[] userIds){
		if(userIds==null||userIds.length==0) {
			return ;
		}
		List<OrgUser> orgUsers = orgUserRepository.findByIds(userIds);
		for(OrgUser orgUser : orgUsers) {
			String oldGroupIds = orgUser.getComGroupIds();
			if(oldGroupIds==null) {
				oldGroupIds = StringUtils.EMPTY;
			}
			//添加不在组中的用户
			if(oldGroupIds.indexOf(groupId+",")==-1){
				oldGroupIds += groupId+",";
			}
			orgUser.setComGroupIds(oldGroupIds);
			
			orgUserRepository.save(orgUser);
		}
	}
	
	/**
	 * 用户注销
	 */
	@Transactional
	public void delete(String id) {
		OrgUser orgUser = orgUserRepository.findOne(id);
		//修改状态，假删除
		orgUser.setStatus(0);
		
		String[] ids = {id};
		userService.deleteByIds(ids);
	}
	
	/**
	 * 用户删除
	 */
	public void realdelete(String id){
		String[] ids = {id};
		userService.deleteByIds(ids);
		
		orgUserRepository.delete(id);
	}

	/**
	 * 批量用户删除
	 */
	@Transactional
	public int delete(String[] ids) {
		//修改状态，假删除
		return orgUserRepository.delete(ids);
	}
	
	/**
	 * 常用组删除用户
	 * @param userId
	 * @param groupIds
	 */
	@Override
	public void deleteFromGroup(String groupId,String[] userIds){
		List<OrgUser> orgUsers ;
		if(userIds==null||userIds.length==0) {
			orgUsers = orgUserRepository.findByComGroupIdsLike("%"+groupId+"%");
		} else {
			orgUsers = orgUserRepository.findByIds(userIds);
		}
		for(OrgUser orgUser : orgUsers) {
			String oldGroupIds = orgUser.getComGroupIds();
			if(oldGroupIds==null) {
				oldGroupIds = StringUtils.EMPTY;
			}
			//删除组中的用户
			oldGroupIds = oldGroupIds.replaceAll(groupId+",", "");
			orgUser.setComGroupIds(oldGroupIds);
		}
	}
	
	/**
	 * 用户更新
	 */
	@Transactional
	public OrgUser update(OrgUser user) {
		user.setUpdateTime(new Date());
		return orgUserRepository.save(user);
	}

	/**
	 * 查询用户，带分页、排序、查询条件
	 */
	public Page<OrgUser> find(OrgUser user, Pageable page) {
		return orgUserRepository.findAll(new OrgUserSpecification(user, null), page);
	}

	/**
	 * 滚动查询通讯录用户
	 * @param scrollPage
	 * @param orgUser
	 * @return
	 */
	public List<OrgUser> find(ScrollPage scrollPage, OrgUser orgUser) {
		//分页ID为空则查询所有数据
		if(scrollPage==null||StringUtils.isEmpty(scrollPage.getId())){
			List<OrgUser> orgUsers = (List<OrgUser>)orgUserRepository.findAll(new OrgUserSpecification(orgUser,null));
			return filterUser(orgUsers,orgUser.getPhoneNum());
		}
		String id = scrollPage.getId();
		orgUser.setId(id);
		Pageable pageable = new PageRequest(0,scrollPage.getCount(),scrollPage.getSort("id"));
		Page<OrgUser> orgUsers = orgUserRepository.findAll(new OrgUserSpecification(orgUser, scrollPage.getSort("id")), pageable);
		return orgUsers.getContent();
	}
	
	/**
	 * 添加查询条件类
	 * @author Administrator
	 *
	 */
	class OrgUserSpecification implements Specification<OrgUser>{

		private OrgUser user;
		private Sort sort;
		
		public OrgUserSpecification(OrgUser user,Sort sort){
			this.user = user;
			this.sort = sort;
		}
		
		//查询条件拼接
		public Predicate toPredicate(Root<OrgUser> root, CriteriaQuery<?> query,
				CriteriaBuilder cb) {
			if(user==null) {
				return cb.notEqual(root.get("status").as(Integer.class), BasedataConstant.USER_STATUS_OFF);
			}
			List<Predicate> list = new ArrayList<Predicate>();
			//如果ID不为空，则进行滚动查询
			if(user.getId()!=null) {
				if(sort.getOrderFor("id").getDirection()==Direction.DESC) {
					list.add(cb.lessThan(root.get("id").as(String.class), user.getId()));
				} else {
					list.add(cb.greaterThan(root.get("id").as(String.class), user.getId()));
				}
			}
			//用户所在机构
			if(StringUtils.isNotEmpty(user.getOrgCode())) {
				Join<OrgUser,Org> join = root.join(root.getModel().getSingularAttribute("org",Org.class),JoinType.INNER);
				list.add(cb.equal(join.get("orgCode").as(String.class), user.getOrgCode()));
			}
			//用户名
			if(StringUtils.isNotEmpty(user.getUsername())) {
				List<Predicate> orPredicate = new ArrayList<Predicate>();
				Predicate userName = cb.like(root.get("username").as(String.class), "%"+user.getUsername()+"%");
				Predicate officeTel = cb.like(root.get("officeTel").as(String.class), "%"+user.getUsername()+"%");
				Predicate mobileTel = cb.like(root.get("mobileTel").as(String.class), "%"+user.getUsername()+"%");
//				list.add(cb.like(root.get("username").as(String.class), "%"+user.getUsername()+"%"));
//				list.add(cb.like(root.get("officeTel").as(String.class), "%"+user.getUsername()+"%"));
				orPredicate.add(userName);
				orPredicate.add(officeTel);
				orPredicate.add(mobileTel);
				list.add(cb.or(orPredicate.toArray(new Predicate[orPredicate.size()])));
			}
			/*//拨号盘查询
			if(StringUtils.isNotEmpty(user.getPhoneNum())) {
				List<Predicate> orPredicate = new ArrayList<Predicate>();
				//匹配用户手机号或者办公电话
				orPredicate.add(cb.like(root.get("mobileTel").as(String.class), user.getPhoneNum()+"%"));
				orPredicate.add(cb.like(root.get("officeTel").as(String.class), user.getPhoneNum()+"%"));
				//匹配用户姓名全拼或者简拼
				String[] nums = getDialLetterGroup(user.getPhoneNum());
				for(String num : nums) {
					Predicate fullPre = cb.like(root.get("fullSpelling").as(String.class), "%"+num+"%");
					Predicate shortPre = cb.like(root.get("shortSpelling").as(String.class), "%"+num+"%");
					orPredicate.add(fullPre);
					orPredicate.add(shortPre);
				}
				list.add(cb.or(orPredicate.toArray(new Predicate[orPredicate.size()])));
			}*/
			/*//用户全拼
			if(StringUtils.isNotEmpty(user.getFullSpelling())) {
				list.add(cb.like(root.get("fullSpelling").as(String.class), "%"+user.getFullSpelling()+"%"));
			}
			//用户简拼
			if(StringUtils.isNotEmpty(user.getShortSpelling())) {
				list.add(cb.like(root.get("shortSpelling").as(String.class), "%"+user.getShortSpelling()+"%"));
			}
			//用户首字母
			if(StringUtils.isNotEmpty(user.getFirstSpelling())) {
				list.add(cb.equal(root.get("firstSpelling").as(String.class), user.getFirstSpelling()));
			}
			//用户性别
			if(StringUtils.isNotEmpty(user.getSex())) {
				list.add(cb.equal(root.get("sex").as(String.class), user.getSex()));
			}*/
			//办公电话
			if(StringUtils.isNotEmpty(user.getOfficeTel())) {
				list.add(cb.like(root.get("officeTel").as(String.class), "%"+user.getUsername()+"%"));
			}
			//用户手机
			if(StringUtils.isNotEmpty(user.getMobileTel())) {
				list.add(cb.like(root.get("mobileTel").as(String.class), "%"+user.getUsername()+"%"));
			}
			/*//用户职位
			if(StringUtils.isNotEmpty(user.getPosition())) {
				list.add(cb.like(root.get("position").as(String.class), "%"+user.getPosition()+"%"));
			}
			//用户所在常用组
			if(StringUtils.isNotEmpty(user.getComGroupIds())) {
				list.add(cb.like(root.get("comGroupIds").as(String.class), "%"+user.getComGroupIds()+"%"));
			}
			//用户状态
			if(user.getStatus()==null || user.getStatus()==BasedataConstant.USER_STATUS_NOR) { 
				//查询正常用户（包括系统用户）
				list.add(cb.notEqual(root.get("status").as(Integer.class),BasedataConstant.USER_STATUS_OFF));
			} else { 
				//查询注销状态用户或者系统用户
				list.add(cb.equal(root.get("status").as(Integer.class),user.getStatus()));
			}*/
			
			Predicate[] p = new Predicate[list.size()];
		    return cb.and(list.toArray(p));
		}
	}

	private static String[][] num = {
			{"0"},{"1"},{"a","b","c"},{"d","e","f"},{"g","h","i"},
	        {"j","k","l"},{"m","n","o"},{"p","q","r","s"},
	        {"t","u","v"},{"w","x","y","z"} };
	
	private static String[] numStr = {"0","1","abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"};
	
	/**
	 * 拨号盘字母组合，超过3个字符只匹配前三个组合
	 * @param x
	 * @return
	 */
	public String[] getDialLetterGroup(String x){
		//判断是否为空和是否是数字
		if(StringUtils.isEmpty(x)||!StringUtils.isNumeric(x)){
			return new String[]{};
		}
		//判断输入是否包含0或者1；包含则直接查询电话
		if(x.indexOf("0")+x.indexOf("1")!=-2){
			return new String[]{x};
		}
		String separateStr = "|";
		int index = 0;
		//输入数字长度是1
		int I = x.charAt(0)-48;
		if(x.length()==1){
			I = x.charAt(0)-48;
			String[] nums = new String[num[I].length];
			for(int i=0;i<num[I].length;i++) {
				nums[index++] = separateStr+num[I][i];
			}
			return nums;
		}
		//输入数字长度是2
		int J = x.charAt(1)-48;
		if(x.length()==2){
			String[] nums = new String[num[I].length*num[J].length];
			for(int i=0;i<num[I].length;i++) {
				for(int j=0;j<num[J].length;j++){
					nums[index++] = separateStr+num[I][i]+num[J][j];
				}
			}
			return nums;
		}
		//输入数字长度大于2
		int K = x.charAt(2)-48;
		String[] nums = new String[num[I].length*num[J].length*num[K].length];
		for(int i=0;i<num[I].length;i++) {
			for(int j=0;j<num[J].length;j++){
				for(int k=0;k<num[K].length;k++){
					nums[index++] = separateStr+num[I][i]+num[J][j]+num[K][k];
				}
			}
		}
		return nums;
	}
	
	public List<OrgUser> filterUser(List<OrgUser> orgUsers,String phoneNum){
		if(StringUtils.isEmpty(phoneNum)||phoneNum.length()<4) {
			return orgUsers;
		}
		List<OrgUser> resultUser = new ArrayList<OrgUser>();
		for(OrgUser orgUser:orgUsers) {
			String mobileTel = orgUser.getMobileTel();
			String officeTel = orgUser.getOfficeTel();
			//输入号码没有匹配到手机或者工作电话，则继续匹配姓名全拼或者简拼
			if(mobileTel.indexOf(phoneNum)+officeTel.indexOf(phoneNum)==-2){
				String fullSpelling = orgUser.getFullSpelling();
				String shortSpelling = orgUser.getShortSpelling();
				//匹配全拼
				int index = 3;
				if(fullSpelling.length()<index+2){
					continue;
				}
				int indexInt = phoneNum.charAt(index)-48;
				String indexStr = fullSpelling.substring(index+1, index+2);
				do{
					indexInt = phoneNum.charAt(index)-48;
					indexStr = fullSpelling.substring(index+1, index+2);
					index++;
				}while(index<phoneNum.length()&&index+2<fullSpelling.length()&&-1!=numStr[indexInt].indexOf(indexStr));
				
				//全拼没有匹配，则继续匹配简拼
				if(index<phoneNum.length()){
					//匹配简拼
					index = 3;
					if(shortSpelling.length()<index+2){
						continue;
					}
					indexInt = phoneNum.charAt(index)-48;
					indexStr = shortSpelling.substring(index+1, index+2);
					do{
						indexInt = phoneNum.charAt(index)-48;
						indexStr = shortSpelling.substring(index+1, index+2);
						index++;
					}while(index<phoneNum.length()&&index+2<shortSpelling.length()&&-1!=numStr[indexInt].indexOf(indexStr));
					
					//简拼没有匹配，则跳过
					if(index<phoneNum.length()){
						continue;
					}
				}
			}
			resultUser.add(orgUser);
		}
		return resultUser;
	}
	
	@Override
	public List<OrgUser> findByComGroupIdsLike(String groupId){
		return (List<OrgUser>)orgUserRepository.findByComGroupIdsLike(groupId);
	}
	
	public List<OrgUser> findByLikeUserShort(String shortName){
		return (List<OrgUser>)orgUserRepository.findByLikeUserShort(shortName);
	}
	
	/**
	 * 权限机构id查询机构负责人
	 * @param orgCode
	 * @return 
	 * @author zhuzejiang 
	 * @date 
	 */
	public  OrgUser getOrgPrincipal(String orgCode){
		Org org = orgService.findByOrgCode(orgCode);
		OrgUser orgUser = null;
		if(org!= null && StringUtils.isNotEmpty(org.getPrincipal())){
			orgUser = new OrgUser();
			orgUser.setUsername(org.getPrincipal());
			orgUser.setMobileTel(org.getContactTel());
			orgUser.setPosition(org.getContactInfo());
		}
		return orgUser;
	}
	
}

