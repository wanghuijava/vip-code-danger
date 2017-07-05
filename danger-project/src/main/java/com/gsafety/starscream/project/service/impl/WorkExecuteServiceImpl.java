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

import com.gsafety.starscream.project.model.WorkExecute;
import com.gsafety.starscream.project.model.WorkPlan;
import com.gsafety.starscream.project.repository.WorkExecuteRepository;
import com.gsafety.starscream.project.repository.WorkPlanRepository;
import com.gsafety.starscream.project.service.WorkExecuteService;
import com.gsafety.starscream.utils.db.PrimaryKeyUtils;
import com.gsafety.starscream.utils.format.DateUtil;

/**
 * 危险作业项目-实施（今日录入）
 * 
 * @author wanghui
 * @date 2016-1-1
 */
@Service
public class WorkExecuteServiceImpl implements WorkExecuteService {

	@Autowired
	private WorkExecuteRepository workExecuteRepository; // Repository注入
	@Autowired
	private WorkPlanRepository workPlanRepository; // Repository注入
	
	@PersistenceContext
	private EntityManager em; // Repository注入
	

	public WorkExecute save(WorkExecute workExecute) {
		if (StringUtils.isEmpty(workExecute.getId())) {
			workExecute.setId(PrimaryKeyUtils.getStringTimeKey());
			workExecute.setCreateTime(new Date()); // 创建时间
			workExecute.setUpdateTime(workExecute.getCreateTime()); // 更新时间
			workExecute = workExecuteRepository.save(workExecute);

			WorkPlan workPlan = workPlanRepository.findOne(workExecute.getWorkPlanId());
			if(StringUtils.isNotEmpty(workExecute.getEndTime())){
				if(workPlan != null){
					workPlan.setCloseFlag(2);//如果执行且关闭时间不为空，则更新计划的此字段为已完成
					workPlanRepository.save(workPlan);
				}
			}else{
				if(workPlan != null){
					workPlan.setCloseFlag(1);//如果执行且关闭时间不为空，则更新计划的此字段为实施中
					workPlanRepository.save(workPlan);
				}
			}
			
			return workExecute;
		} else{
			WorkExecute model = this.findById(workExecute.getId());
			if(model == null){
				return null;
			}

			BeanUtils.copyProperties(workExecute, model, new String[]{"createTime", "createBy"});
			model.setUpdateTime(new Date()); // 更新时间
			model = workExecuteRepository.save(model);
			
			WorkPlan workPlan = workPlanRepository.findOne(workExecute.getWorkPlanId());
			if(StringUtils.isNotEmpty(workExecute.getEndTime())){
				if(workPlan != null){
					workPlan.setCloseFlag(2);//如果执行且关闭时间不为空，则更新计划的此字段为已完成
					workPlanRepository.save(workPlan);
				}
			}else{
				if(workPlan != null){
					workPlan.setCloseFlag(1);//如果执行且关闭时间不为空，则更新计划的此字段为实施中
					workPlanRepository.save(workPlan);
				}
			}
			
			return model;
		}
	}

	public void delete(String workExecuteId) {
		WorkExecute model = this.findById(workExecuteId);
		if(model == null){
			return ;
		}
		
		String workPlanId = model.getWorkPlanId();
		List<WorkExecute> list = workExecuteRepository.findByWorkPlanId(workPlanId);
		WorkPlan workPlan = workPlanRepository.findOne(model.getWorkPlanId());
		if(list.size() > 1){
			workPlan.setCloseFlag(1);//如果还有其他记录，则更新计划的此字段为实施中
			workPlanRepository.save(workPlan);
		}else{
			workPlan.setCloseFlag(0);//如果只有这一条实施记录，则更新计划的此字段为尚未实施
			workPlanRepository.save(workPlan);
		}
		workExecuteRepository.delete(workExecuteId);
	}

	public WorkExecute update(WorkExecute workExecute) {
		WorkExecute model = this.findById(workExecute.getId());
		if(model == null){
			return null;
		}

		BeanUtils.copyProperties(workExecute, model, new String[]{"createTime", "createBy"});
		model.setUpdateTime(new Date()); // 更新时间
		model = workExecuteRepository.save(model);

		return model;
	}

	public WorkExecute findById(String workExecuteId) {
		return workExecuteRepository.findOne(workExecuteId);
	}

	public Page<WorkExecute> find(WorkExecute workExecute, Pageable page) {
		if(page.getSort() != null){
			page.getSort().and(new Sort(Direction.DESC,"createTime"));
		}
		return workExecuteRepository.findAll(new WorkExecuteSpecification(workExecute), page);
	}

	/**
	 * 查询条件过滤类
	 * 
	 * @author wanghui
	 * 
	 */
	class WorkExecuteSpecification implements Specification<WorkExecute> {

		private WorkExecute workExecute;

		public WorkExecuteSpecification(WorkExecute workExecute) {
			this.workExecute = workExecute;
		}

		// 查询条件拼接
		public Predicate toPredicate(Root<WorkExecute> root, CriteriaQuery<?> query,
				CriteriaBuilder cb) {
			return getPredicate(workExecute, root, query, cb);
		}
	}


	public Predicate getPredicate(WorkExecute workExecute, Root<WorkExecute> root,
			CriteriaQuery<?> query, CriteriaBuilder cb) {
		if (workExecute == null) {
			return null;
		}

		List<Predicate> list = new ArrayList<Predicate>();
		//按某一天
		if (workExecute.getExecuteDate() != null) {
			list.add(cb.equal(root.get("executeDate").as(Date.class), workExecute.getExecuteDate()));
		}
		
		// 通过关键字查询
		if (StringUtils.isNotEmpty(workExecute.getWorkName())) {
			list.add(cb.or(
					cb.like(root.get("orgName").as(String.class), "%"+ workExecute.getWorkName() + "%"),
					cb.like(root.get("workName").as(String.class), "%"+ workExecute.getWorkName() + "%")));
		}
		
		//按执行单位检索
		if (StringUtils.isNotEmpty(workExecute.getOrgCode())) {
			list.add(cb.equal(root.get("orgCode").as(String.class), workExecute.getOrgCode()));
		}
		
		//时间段查询
		if(StringUtils.isNotEmpty(workExecute.getSearchStartTimeStr())){
			list.add(cb.greaterThanOrEqualTo(root.get("executeDate").as(Date.class), DateUtil.getDate(workExecute.getSearchStartTimeStr())));
		}
		if(StringUtils.isNotEmpty(workExecute.getSearchEndTimeStr())){
			list.add(cb.lessThanOrEqualTo(root.get("executeDate").as(Date.class), DateUtil.getDate(workExecute.getSearchEndTimeStr()+" 23:59:59")));
		}
		
		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
	
	
}
