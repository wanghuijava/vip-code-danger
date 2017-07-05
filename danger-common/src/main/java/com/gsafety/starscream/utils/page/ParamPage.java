package com.gsafety.starscream.utils.page;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.gsafety.starscream.constant.PageConstant;

public class ParamPage {

private int start = PageConstant.DEFAULT_START;    //分页起始页
	
	private int count = PageConstant.DEFAULT_COUNT;    //分页页数
	
	private String orderBy;                            //排序字段
	
	private String dir = PageConstant.DIR_DESC;        //排序方向
	
	private long total = 0;
	
	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
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

	/**
	 * 获取排序对象
	 * @return
	 */
	public Sort getSort(){
		if (StringUtils.isEmpty(orderBy)) {
			return null;
		}
		Sort sort = new Sort(Direction.DESC,orderBy);
		if(PageConstant.DIR_ASC.equalsIgnoreCase(dir)) {
			dir = PageConstant.DIR_ASC;
			sort = new Sort(Direction.ASC,orderBy);
		}
		
		return sort;
	}
	
	/**
	 * 获取pageable对象
	 * @return
	 */
	public Pageable getPageable(){
		Pageable pageable = new PageRequest(start, count, getSort());
		return pageable;
	}
	
}
