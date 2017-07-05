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

import com.gsafety.starscream.project.model.ProduceExecute;
import com.gsafety.starscream.project.model.ProducePlan;
import com.gsafety.starscream.project.repository.ProduceExecuteRepository;
import com.gsafety.starscream.project.repository.ProducePlanRepository;
import com.gsafety.starscream.project.service.ProduceExecuteService;
import com.gsafety.starscream.utils.db.PrimaryKeyUtils;
import com.gsafety.starscream.utils.format.DateUtil;

/**
 * 试运投产项目-实施（今日录入）
 * 
 * @author wanghui
 * @date 2016-1-1
 */
@Service
public class ProduceExecuteServiceImpl implements ProduceExecuteService {

	@Autowired
	private ProduceExecuteRepository produceExecuteRepository; // Repository注入
	@Autowired
	private ProducePlanRepository producePlanRepository; // Repository注入
	
	@PersistenceContext
	private EntityManager em; // Repository注入
	

	public ProduceExecute save(ProduceExecute produceExecute) {
		if (StringUtils.isEmpty(produceExecute.getId())) {
			produceExecute.setId(PrimaryKeyUtils.getStringTimeKey());
			produceExecute.setCreateTime(new Date()); // 创建时间
			produceExecute.setUpdateTime(produceExecute.getCreateTime()); // 更新时间
			produceExecute = produceExecuteRepository.save(produceExecute);

			ProducePlan producePlan = producePlanRepository.findOne(produceExecute.getWorkPlanId());
			if(StringUtils.isNotEmpty(produceExecute.getEndTime())){
				if(producePlan != null){
					producePlan.setCloseFlag(2);//如果执行且关闭时间不为空，则更新计划的此字段为已完成
					producePlanRepository.save(producePlan);
				}
			}else{
				if(producePlan != null){
					producePlan.setCloseFlag(1);//如果执行且关闭时间不为空，则更新计划的此字段为实施中
					producePlanRepository.save(producePlan);
				}
			}
			
			return produceExecute;
		} else{
			ProduceExecute model = this.findById(produceExecute.getId());
			if(model == null){
				return null;
			}

			BeanUtils.copyProperties(produceExecute, model, new String[]{"createTime", "createBy"});
			model.setUpdateTime(new Date()); // 更新时间
			model = produceExecuteRepository.save(model);
			
			ProducePlan producePlan = producePlanRepository.findOne(produceExecute.getWorkPlanId());
			if(StringUtils.isNotEmpty(produceExecute.getEndTime())){
				if(producePlan != null){
					producePlan.setCloseFlag(2);//如果执行且关闭时间不为空，则更新计划的此字段为已完成
					producePlanRepository.save(producePlan);
				}
			}else{
				if(producePlan != null){
					producePlan.setCloseFlag(1);//如果执行且关闭时间不为空，则更新计划的此字段为实施中
					producePlanRepository.save(producePlan);
				}
			}
			
			return model;
		}
	}

	public void delete(String produceExecuteId) {
		ProduceExecute model = this.findById(produceExecuteId);
		if(model == null){
			return ;
		}
		
		String workPlanId = model.getWorkPlanId();
		List<ProduceExecute> list = produceExecuteRepository.findByWorkPlanId(workPlanId);
		ProducePlan producePlan = producePlanRepository.findOne(model.getWorkPlanId());
		if(list.size() > 1){
			producePlan.setCloseFlag(1);//如果还有其他记录，则更新计划的此字段为实施中
			producePlanRepository.save(producePlan);
		}else{
			producePlan.setCloseFlag(0);//如果只有这一条实施记录，则更新计划的此字段为尚未实施
			producePlanRepository.save(producePlan);
		}
		produceExecuteRepository.delete(produceExecuteId);
	}

	public ProduceExecute update(ProduceExecute produceExecute) {
		ProduceExecute model = this.findById(produceExecute.getId());
		if(model == null){
			return null;
		}

		BeanUtils.copyProperties(produceExecute, model, new String[]{"createTime", "createBy"});
		model.setUpdateTime(new Date()); // 更新时间
		model = produceExecuteRepository.save(model);

		return model;
	}

	public ProduceExecute findById(String produceExecuteId) {
		return produceExecuteRepository.findOne(produceExecuteId);
	}

	public Page<ProduceExecute> find(ProduceExecute produceExecute, Pageable page) {
		if(page.getSort() != null){
			page.getSort().and(new Sort(Direction.DESC,"createTime"));
		}
		return produceExecuteRepository.findAll(new ProduceExecuteSpecification(produceExecute), page);
	}

	/**
	 * 查询条件过滤类
	 * 
	 * @author wanghui
	 * 
	 */
	class ProduceExecuteSpecification implements Specification<ProduceExecute> {

		private ProduceExecute produceExecute;

		public ProduceExecuteSpecification(ProduceExecute produceExecute) {
			this.produceExecute = produceExecute;
		}

		// 查询条件拼接
		public Predicate toPredicate(Root<ProduceExecute> root, CriteriaQuery<?> query,
				CriteriaBuilder cb) {
			return getPredicate(produceExecute, root, query, cb);
		}
	}


	public Predicate getPredicate(ProduceExecute produceExecute, Root<ProduceExecute> root,
			CriteriaQuery<?> query, CriteriaBuilder cb) {
		if (produceExecute == null) {
			return null;
		}

		List<Predicate> list = new ArrayList<Predicate>();
		//按某一天
		if (produceExecute.getExecuteDate() != null) {
			list.add(cb.equal(root.get("executeDate").as(Date.class), produceExecute.getExecuteDate()));
		}
		
		// 通过关键字查询
		if (StringUtils.isNotEmpty(produceExecute.getWorkName())) {
			list.add(cb.or(
					cb.like(root.get("orgName").as(String.class), "%"+ produceExecute.getWorkName() + "%"),
					cb.like(root.get("workName").as(String.class), "%"+ produceExecute.getWorkName() + "%")));
		}
		
		//按执行单位检索
		if (StringUtils.isNotEmpty(produceExecute.getOrgCode())) {
			list.add(cb.equal(root.get("orgCode").as(String.class), produceExecute.getOrgCode()));
		}
		
		//时间段查询
		if(StringUtils.isNotEmpty(produceExecute.getSearchStartTimeStr())){
			list.add(cb.greaterThanOrEqualTo(root.get("executeDate").as(Date.class), DateUtil.getDate(produceExecute.getSearchStartTimeStr())));
		}
		if(StringUtils.isNotEmpty(produceExecute.getSearchEndTimeStr())){
			list.add(cb.lessThanOrEqualTo(root.get("executeDate").as(Date.class), DateUtil.getDate(produceExecute.getSearchEndTimeStr()+" 23:59:59")));
		}
		
		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
	
	
}
