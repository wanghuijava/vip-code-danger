package com.gsafety.starscream.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * 对象转换成VO
 * @author chenwenlong
 *
 */
public class ValueObjectUtils {
	
	//记录当前正在处理的类
	private static Map<String,Integer> clazzMap = new HashMap<String,Integer>();
	
	/**
	 * 生成对应的VO对象
	 * @param object         传递带处理的对象（可以是集合）
	 * @param ignoreField    过滤不需要处理的字段
	 * @return
	 */
	public static<T> T getVO(T object,String ignoreField){
		if(object==null) {
			return null;
		}
		//集合数据处理
		if(object instanceof Collection) {
			if(((Collection)object).size()==0){
				return null;
			}
			Collection collectionResult = null;
			if(object instanceof List) {
				collectionResult = new ArrayList();
			}else if(object instanceof Set) {
				collectionResult = new HashSet();
			}
			Iterator iterator = ((Collection)object).iterator();
			while(iterator.hasNext()) {
				Object obj = iterator.next();
				if(obj!=null) {
					Object result = getVO(obj,ignoreField);
					collectionResult.add(result);
				}
			}
			return (T)collectionResult;
		}
		//非集合数据处理
		Class<T> clazz = null;
		T objResult = null;
		try{
			clazz = (Class<T>)object.getClass();
			clazzMap.put(clazz.getName(), 1);
			objResult = clazz.newInstance();
			
			Method[] methods = clazz.getDeclaredMethods();
			String methodNames = StringUtils.EMPTY;
			for(Method method:methods) {
				methodNames += method.getName()+"|";
			}
			Field[] fields = clazz.getDeclaredFields();
			for(Field field:fields) {
				String fieldName = field.getName();
				String getMethodStr = "get"+StringUtils.capitalize(fieldName);
				String setMethodStr = "set"+StringUtils.capitalize(fieldName);
				if(methodNames.contains(setMethodStr)&&methodNames.contains(getMethodStr)&&!ignoreField.contains(fieldName)){
					Method getMethod = clazz.getMethod(getMethodStr, null);
					Class<?> returnClass = getMethod.getReturnType();
					//如果返回类型与正在处理的类相同，不在处理该方法；避免双向关联死锁
					if(clazzMap.get(returnClass.getName())==null) {
						Method setMethod = clazz.getMethod(setMethodStr, returnClass);
						Object obj = getMethod.invoke(object, null);
						if(returnClass.getName().contains("com.gsafety.bumblebee")
								||returnClass.getName().contains("List")
								||returnClass.getName().contains("Set")) {
							setMethod.invoke(objResult, getVO(obj,ignoreField));
						} else {
							setMethod.invoke(objResult, obj);
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		//当前类执行完后，释放
		clazzMap.remove(clazz.getName());
		return (T)objResult;
	}
}
