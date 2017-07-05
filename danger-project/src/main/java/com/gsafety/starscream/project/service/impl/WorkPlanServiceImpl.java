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

import com.gsafety.starscream.project.model.WorkPlan;
import com.gsafety.starscream.project.repository.WorkPlanRepository;
import com.gsafety.starscream.project.service.WorkPlanService;
import com.gsafety.starscream.utils.db.PrimaryKeyUtils;
import com.gsafety.starscream.utils.format.DateUtil;

/**
 * 危险作业项目-计划
 * 
 * @author wanghui
 * @date 2016-1-1
 */
@Service
public class WorkPlanServiceImpl implements WorkPlanService {

	@Autowired
	private WorkPlanRepository workPlanRepository; // Repository注入
	
	@PersistenceContext
	private EntityManager em; // Repository注入
	

	public WorkPlan save(WorkPlan workPlan) {
		if (StringUtils.isEmpty(workPlan.getId())) {
			workPlan.setId(PrimaryKeyUtils.getStringTimeKey());
			workPlan.setCreateTime(new Date()); // 创建时间
			workPlan.setUpdateTime(workPlan.getCreateTime()); // 更新时间
			workPlan.setCloseFlag(0);
			workPlan = workPlanRepository.save(workPlan);
			return workPlan;
		} else{
			WorkPlan model = this.findById(workPlan.getId());
			if(model == null){
				return null;
			}

			BeanUtils.copyProperties(workPlan, model, new String[]{"createTime", "createBy","closeFlag"});
			model.setUpdateTime(new Date()); // 更新时间
			model = workPlanRepository.save(model);
			return model;
		}

	}

	public void delete(String workPlanId) {
		workPlanRepository.delete(workPlanId);
	}

	public WorkPlan update(WorkPlan workPlan) {
		workPlan = workPlanRepository.save(workPlan);
		return workPlan;
	}

	public WorkPlan findById(String workPlanId) {
		return workPlanRepository.findOne(workPlanId);
	}

	public Page<WorkPlan> find(WorkPlan workPlan, Pageable page) {
		if(page.getSort() != null){
			page.getSort().and(new Sort(Direction.DESC,"createTime"));
		}
		return workPlanRepository.findAll(new WorkPlanSpecification(workPlan), page);
	}

	/**
	 * 查询条件过滤类
	 * 
	 * @author wanghui
	 * 
	 */
	class WorkPlanSpecification implements Specification<WorkPlan> {

		private WorkPlan workPlan;

		public WorkPlanSpecification(WorkPlan workPlan) {
			this.workPlan = workPlan;
		}

		// 查询条件拼接
		public Predicate toPredicate(Root<WorkPlan> root, CriteriaQuery<?> query,
				CriteriaBuilder cb) {
			return getPredicate(workPlan, root, query, cb);
		}
	}


	public Predicate getPredicate(WorkPlan workPlan, Root<WorkPlan> root,
			CriteriaQuery<?> query, CriteriaBuilder cb) {
		if (workPlan == null) {
			return null;
		}

		List<Predicate> list = new ArrayList<Predicate>();
		//按某一天
		if (workPlan.getExecuteDate() != null) {
			list.add(cb.equal(root.get("executeDate").as(Date.class), workPlan.getExecuteDate()));
		}
		
		// 通过关键字查询
		if (StringUtils.isNotEmpty(workPlan.getWorkName())) {
			list.add(cb.or(
					cb.like(root.get("orgName").as(String.class), "%"+ workPlan.getWorkName() + "%"),
					cb.like(root.get("workName").as(String.class), "%"+ workPlan.getWorkName() + "%")));
		}
		
		//按执行单位检索
		if (StringUtils.isNotEmpty(workPlan.getOrgCode())) {
			list.add(cb.equal(root.get("orgCode").as(String.class), workPlan.getOrgCode()));
		}
		
		//按是否关闭
		if (workPlan.getCloseFlag() == 2) {
			list.add(cb.notEqual(root.get("closeFlag").as(Integer.class), 2));//未关闭的
		}
		else if (workPlan.getCloseFlag() == 3) {
			list.add(cb.equal(root.get("closeFlag").as(Integer.class), 0));//未实施关联的
		}
		
		//按是否上报
		if (workPlan.getCheckFlag() == 1) {
			list.add(cb.equal(root.get("checkFlag").as(Integer.class), 1));//已上报的
		}
		
		//时间段查询
		if(StringUtils.isNotEmpty(workPlan.getSearchStartTimeStr())){
			list.add(cb.greaterThanOrEqualTo(root.get("executeDate").as(Date.class), DateUtil.getDate(workPlan.getSearchStartTimeStr()+" 00:00:00")));
		}
		if(StringUtils.isNotEmpty(workPlan.getSearchEndTimeStr())){
			list.add(cb.lessThanOrEqualTo(root.get("executeDate").as(Date.class), DateUtil.getDate(workPlan.getSearchEndTimeStr()+" 23:59:59")));
		}
		
		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
	
	
}
