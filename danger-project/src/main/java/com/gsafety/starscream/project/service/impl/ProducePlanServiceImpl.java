package com.gsafety.starscream.project.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.gsafety.starscream.project.model.ProducePlan;
import com.gsafety.starscream.project.repository.ProducePlanRepository;
import com.gsafety.starscream.project.service.ProducePlanService;
import com.gsafety.starscream.utils.db.PrimaryKeyUtils;
import com.gsafety.starscream.utils.format.DateUtil;

/**
 * 试运投产项目-计划
 * 
 * @author wanghui
 * @date 2016-1-1
 */
@Service
public class ProducePlanServiceImpl implements ProducePlanService {

	@Autowired
	private ProducePlanRepository producePlanRepository; // Repository注入
	
	@PersistenceContext
	private EntityManager em; // Repository注入
	

	public ProducePlan save(ProducePlan producePlan) {
		if (StringUtils.isEmpty(producePlan.getId())) {
			producePlan.setId(PrimaryKeyUtils.getStringTimeKey());
			producePlan.setCreateTime(new Date()); // 创建时间
			producePlan.setUpdateTime(producePlan.getCreateTime()); // 更新时间
			producePlan.setCloseFlag(0);
			producePlan = producePlanRepository.save(producePlan);
			return producePlan;
		} else{
			ProducePlan model = this.findById(producePlan.getId());
			if(model == null){
				return null;
			}

			BeanUtils.copyProperties(producePlan, model, new String[]{"createTime", "createBy","closeFlag"});
			model.setUpdateTime(new Date()); // 更新时间
			model = producePlanRepository.save(model);
			return model;
		}

	}

	public void delete(String workPlanId) {
		producePlanRepository.delete(workPlanId);
	}

	public ProducePlan update(ProducePlan producePlan) {
		producePlan = producePlanRepository.save(producePlan);
		return producePlan;
	}

	public ProducePlan findById(String workPlanId) {
		return producePlanRepository.findOne(workPlanId);
	}

	public Page<ProducePlan> find(ProducePlan producePlan, Pageable page) {
		if(page.getSort() != null){
			page.getSort().and(new Sort(Direction.DESC,"createTime"));
		}
		return producePlanRepository.findAll(new ProducePlanSpecification(producePlan), page);
	}

	/**
	 * 查询条件过滤类
	 * 
	 * @author wanghui
	 * 
	 */
	class ProducePlanSpecification implements Specification<ProducePlan> {

		private ProducePlan producePlan;

		public ProducePlanSpecification(ProducePlan producePlan) {
			this.producePlan = producePlan;
		}

		// 查询条件拼接
		public Predicate toPredicate(Root<ProducePlan> root, CriteriaQuery<?> query,
				CriteriaBuilder cb) {
			return getPredicate(producePlan, root, query, cb);
		}
	}


	public Predicate getPredicate(ProducePlan producePlan, Root<ProducePlan> root,
			CriteriaQuery<?> query, CriteriaBuilder cb) {
		if (producePlan == null) {
			return null;
		}

		List<Predicate> list = new ArrayList<Predicate>();
		//按某一天
		if (producePlan.getExecuteDate() != null) {
			list.add(cb.equal(root.get("executeDate").as(Date.class), producePlan.getExecuteDate()));
		}
		
		// 通过关键字查询
		if (StringUtils.isNotEmpty(producePlan.getWorkName())) {
			list.add(cb.or(
					cb.like(root.get("orgName").as(String.class), "%"+ producePlan.getWorkName() + "%"),
					cb.like(root.get("workName").as(String.class), "%"+ producePlan.getWorkName() + "%")));
		}
		
		//按执行单位检索
		if (StringUtils.isNotEmpty(producePlan.getOrgCode())) {
			list.add(cb.equal(root.get("orgCode").as(String.class), producePlan.getOrgCode()));
		}
		
		//按是否关闭
		if (producePlan.getCloseFlag() == 2) {
			list.add(cb.notEqual(root.get("closeFlag").as(Integer.class), 2));//未关闭的
		}
		else if (producePlan.getCloseFlag() == 3) {
			list.add(cb.equal(root.get("closeFlag").as(Integer.class), 0));//未实施关联的
		}
		
		//按是否上报
		if (producePlan.getCheckFlag() == 1) {
			list.add(cb.equal(root.get("checkFlag").as(Integer.class), 1));//已上报的
		}
		
		//时间段查询
		if(StringUtils.isNotEmpty(producePlan.getSearchStartTimeStr())){
			list.add(cb.greaterThanOrEqualTo(root.get("executeDate").as(Date.class), DateUtil.getDate(producePlan.getSearchStartTimeStr()+" 00:00:00")));
		}
		if(StringUtils.isNotEmpty(producePlan.getSearchEndTimeStr())){
			list.add(cb.lessThanOrEqualTo(root.get("executeDate").as(Date.class), DateUtil.getDate(producePlan.getSearchEndTimeStr()+" 23:59:59")));
		}
		
		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
	
	
}
