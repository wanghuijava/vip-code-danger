//package com.gsafety.bumblebee.logger;
//
//import java.util.Date;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import com.gsafety.bumblebee.utils.format.DateUtil;
//
//@Aspect
//@Component
//public class LoggerAop {
//
//	Logger logger = LoggerFactory.getLogger(this.getClass());
//	
//	@Pointcut("execution(public * com.gsafety.bumblebee.*.service.impl.*.save*(..))")
//	public void pc_service_save(){}
//	@Pointcut("execution(public * com.gsafety.bumblebee.*.service.impl.*.del*(..))")
//	public void pc_service_delete(){}
//	@Pointcut("execution(public * com.gsafety.bumblebee.*.service.impl.*.update*(..))")
//	public void pc_service_update(){}
//	@Pointcut("execution(public * com.gsafety.bumblebee.*.service.impl.*.find*(..))")
//	public void pc_service_find(){}
//	
//	@AfterReturning(value="pc_service_save()",returning="rtv")
//	public void after_service_save(JoinPoint jp,Object rtv) throws Throwable{
//		logger.info("service层："+getInfo(jp,"保存成功"));
//	}
//	@AfterReturning(value="pc_service_delete()",returning="rtv")
//	public void after_service_delete(JoinPoint jp,Object rtv) throws Throwable{
//		logger.info("service层："+getInfo(jp,"删除成功"));
//	}
//	@AfterReturning(value="pc_service_update()",returning="rtv")
//	public void after_service_update(JoinPoint jp,Object rtv) throws Throwable{
//		logger.info("service层："+getInfo(jp,"修改成功"));
//	}
//	@AfterReturning(value="pc_service_find()",returning="rtv")
//	public void after_service_find(JoinPoint jp,Object rtv) throws Throwable{
//		logger.info("service层："+getInfo(jp,"查询成功"));
//	}
//	
//	@AfterThrowing(pointcut="pc_service_save()",throwing="ex")
//	public void afterThrowing_service_save(JoinPoint jp,Throwable ex){
//		logger.info("service层："+getInfo(jp,"保存失败"+ex.toString()));
//	}
//	@AfterThrowing(pointcut="pc_service_delete()",throwing="ex")
//	public void afterThrowing_service_delete(JoinPoint jp,Throwable ex){
//		logger.info("service层："+getInfo(jp,"删除失败"+ex.toString()));
//	}
//	@AfterThrowing(pointcut="pc_service_update()",throwing="ex")
//	public void afterThrowing_service_update(JoinPoint jp,Throwable ex){
//		logger.info("service层："+getInfo(jp,"修改失败"+ex.toString()));
//	}
//	@AfterThrowing(pointcut="pc_service_find()",throwing="ex")
//	public void afterThrowing_service_find(JoinPoint jp,Throwable ex){
//		logger.info("service层："+getInfo(jp,"查询失败"+ex.toString()));
//	}
//	
//	/**
//	 * 日志信息
//	 * @param jp
//	 * @param statue
//	 * @return
//	 */
//	public String getInfo(JoinPoint jp,String statue){
//		String curDate = DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
//		Object target = jp.getTarget();
//		String targetClass = target.getClass().getName();
//		String targetMethod = jp.getSignature().getName();
//		String info = "["+curDate+"]"+targetClass+"."+targetMethod+statue;
//		return info;
//	}
//}
//
