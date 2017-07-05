package com.gsafety.starscream.basedata.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.paoding.rose.web.Invocation;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.gsafety.starscream.basedata.model.SystemOperationLogs;
import com.gsafety.starscream.basedata.model.User;
import com.gsafety.starscream.basedata.repository.SystemOperationLogRepository;
import com.gsafety.starscream.basedata.service.SystemOperationLogsService;
import com.gsafety.starscream.basedata.view.SystemOperationLogsDTO;
import com.gsafety.starscream.utils.SysUserUtils;
import com.gsafety.starscream.utils.db.PrimaryKeyUtils;

/**
 *  @author Zzj
 * @date 2015-10-27 下午4:35:11
 * @history V1.0
 */
@Service
public class SystemOperationLogsServiceImpl implements SystemOperationLogsService {

	@Autowired
	private SystemOperationLogRepository repository;
	
	
/*	//添加操作日志 
	Map<String,String> opeationMap = new HashMap<String, String>();
	opeationMap.put("operationType",BasedataConstant.DATA_EXPORT);
	opeationMap.put("funName","危险源数据导出");
	opeationMap.put("detail","导出数据"+dangers.size()+"条。");
	logsService.addLogs(opeationMap, inv);
*/
	
	/*
	 * @author 朱泽江
	 * @date 2015-10-27 下午4:35:12
	 * @param opeationMap
	 * @param ivn
	 */
	@Override
	public String addLogs(Map<String, String> operationMap, Invocation inv) {
		SystemOperationLogs model = new SystemOperationLogs();
		model.setId(PrimaryKeyUtils.getStringTimeKey());
		if(!operationMap.containsKey("operationType")) return "操作日志类型不能为空!";
		model.setOperationType(operationMap.get("operationType"));
		if(!operationMap.containsKey("funName")) return "操作功能名不能为空!";
		model.setFunName(operationMap.get("funName"));
		
		if(operationMap.containsKey("detail")){
			model.setDetail(operationMap.get("detail"));
		}
		if(operationMap.containsKey("operator")){
			model.setOperator(operationMap.get("operator"));
		}else {
			User user = SysUserUtils.getCurrentUser(inv);
			model.setOperator(user.getUsername());
		}
		
		model.setUrl(inv.getRequest().getServletPath());
		repository.save(model);
		
		return model.getId();
	}

	/*
	 * @author 朱泽江
	 * @date 2015-10-27 下午4:35:12
	 * @param dto
	 * @return
	 */
	public Page<SystemOperationLogs> findListByTime(SystemOperationLogsDTO dto,Pageable page) {
		return repository.findAll(new OperationSpecification(dto), page);
	}

	
	class OperationSpecification implements Specification<SystemOperationLogs> {
		private SystemOperationLogsDTO sodto ;
		
		public OperationSpecification(SystemOperationLogsDTO dto) {
			sodto = dto;
		}

		@Override
		public Predicate toPredicate(Root<SystemOperationLogs> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
			if (sodto == null) {
				return null;
			}
			List<Predicate> list = new ArrayList<Predicate>();
			
			list.add(cb.equal(root.get("operationType").as(String.class), sodto.getOperationType()));
			
			//创建时间过滤
			if(sodto.getStartTime() != null && sodto.getEndTime()!= null){
				list.add(cb.between(root.get("createTime").as(Date.class), sodto.getStartTime(), sodto.getEndTime()));
			}else if(sodto.getStartTime() != null && sodto.getEndTime()== null){
				list.add(cb.greaterThanOrEqualTo(root.get("createTime").as(Date.class),sodto.getStartTime()));
			}else if(sodto.getStartTime() == null && sodto.getEndTime() != null){
				list.add(cb.lessThanOrEqualTo(root.get("createTime").as(Date.class),sodto.getEndTime()));
			}
			
//			Predicate[] p = new Predicate[list.size()];
//			query.where(cb.and(list.toArray(p)));
//			query.orderBy(cb.desc(root.get("createTime").as(Date.class)));
//			return query.getRestriction();
			Predicate[] p = new Predicate[list.size()];
			return cb.and(list.toArray(p));
		}
		
	}
}

