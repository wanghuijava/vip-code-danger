package com.gsafety.starscream.utils.page;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import com.gsafety.starscream.constant.PageConstant;

public class ScrollPage {

	private String id;                             //指定ID开始拖动
	
	private int count = PageConstant.DEFAULT_COUNT;//每次拖动加载10条数据
	
	private String orderBy;                        //排序字段
	
	private String dir = PageConstant.DIR_DESC;    //排序方向
	
	private String scrollDir;                      //滚动方向

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getScrollDir() {
		return scrollDir;
	}

	public void setScrollDir(String scrollDir) {
		this.scrollDir = scrollDir;
	}
	
	/**
	 * 获取滚动方向
	 * @return
	 */
	public Direction getDirection(){
		if(PageConstant.DIR_UP.equalsIgnoreCase(scrollDir)){
			return Direction.ASC;
		}else {
			return Direction.DESC;
		}
	}
	
	/**
	 * 数据库排序
	 * @param field  排序字段
	 * @return
	 */
	public Sort getSort(String field){
		Sort sort = new Sort(getDirection(),field);
		return sort;
	}
	
	/**
	 * java排序方法
	 * @param list     待排序对象
	 */
	public<T> void sortList(List<T> list) {
		sortList(list,orderBy,dir);
	}
	
	/**
	 * java排序方法
	 * @param list      待排序对象
	 * @param orderBy   排序字段
	 * @param dir       排序方向
	 */
	public<T> void sortList(List<T> list,String orderBy,String dir) {
		Collections.sort(list, new SortComparator<T>(orderBy,dir));
	}
}

/**
 * java排序比较类
 * @author chenwenlong
 *
 * @param <T>
 */
class SortComparator<T> implements Comparator<T>{
	//待排序字段
	private String orderBy;
	//排序方向
	private String dir;
	
	public SortComparator(){}
	
	public SortComparator(String orderBy,String dir){
		this.orderBy = orderBy;
		this.dir = dir;
	}
	
	@Override
	public int compare(Object t1, Object t2) {
		String methodStr = "get"+orderBy.substring(0, 1).toUpperCase()+orderBy.substring(1, orderBy.length());
		//比较大小
		if(PageConstant.DIR_DESC.equalsIgnoreCase(dir)) {
			return getResult(t2,methodStr).compareTo(getResult(t1,methodStr));
		} else {
			return getResult(t1,methodStr).compareTo(getResult(t2,methodStr));
		}
	}
	
	/**
	 * 反射获取待比较大小字段值
	 * @param t
	 * @param methodStr
	 * @return
	 */
	public String getResult(Object t,String methodStr){
		String result = StringUtils.EMPTY;
		Class clazz = t.getClass();
		try {
			Method method = clazz.getMethod(methodStr, null);
			result = method.invoke(t, null).toString();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
