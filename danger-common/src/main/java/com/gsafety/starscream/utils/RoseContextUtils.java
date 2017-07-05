package com.gsafety.starscream.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 获取spring管理的bean
 */
public class RoseContextUtils implements ApplicationContextAware{

	private static ApplicationContext roseApp = null;
	
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.roseApp = context;
	}
	
	/**
	 * 获取Rose Application context容器
	 */
	public static ApplicationContext getRoseApplicationContext(){
		return roseApp;
	}
	
	/**
	 * 根据类型获取bean
	 */
	public static <T>T getBean(Class<T> clazz) throws BeansException{
		return roseApp.getBean(clazz);
	}

	/**
	 * 根据名称获取bean
	 */
	@SuppressWarnings("unchecked")
	public static <T>T getBean(String name) throws BeansException{
		Object obj = null;
		obj = roseApp.getBean(name);
		return (T)obj;
	}
	
	/**
	 * 从指定application.xml配置找bean（可能会导致重新加载ApplicationContext）
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <T>T getBean(String name,String application) throws BeansException{
		Object obj = null;
		try{
			obj = roseApp.getBean(name);
		}catch(NoSuchBeanDefinitionException e) {
			//加载的bean不在已初始化的application.xml中
			if(obj==null&&StringUtils.isNotEmpty(application)) {
				//初始化application
				ApplicationContext applicationContext;
				applicationContext = new ClassPathXmlApplicationContext("classpath:"+application);
				obj = applicationContext.getBean(name);
			}
		}
		return (T)obj;
	}
}
