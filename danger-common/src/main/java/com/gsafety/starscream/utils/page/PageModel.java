package com.gsafety.starscream.utils.page;


public class PageModel {
	
	//查询记录总条数
	private int totalRecords;
	
	//每页多少条数
	private int pageSize=5;
	
	//当前页
	private int currentPage=0;
	
	//总页数
	public int getTotalPages(){
		return ((totalRecords+pageSize-1)/pageSize)-1;
	}
	
	//取得首页
	public int getTopcurrentPage(){
		return 0;
	}
	
	public void setTopcurrentPage(){
		this.setcurrentPage(0);
	}
	
	//取得尾页
	public int getBottomcurrentPage(){
		return getTotalPages();
	}
	
	public void setBottomcurrentPage(){
		currentPage=getTotalPages();
	}
	
	//上一页
	public int getPreviouscurrentPage(){
		if (currentPage<=1) {
			return 1;
		}
		return currentPage-1;
	}
	
	public void setPreviouscurrentPage(){
		if (currentPage<1) {
			currentPage=0;
		}else{
			currentPage=currentPage-1;
		}
	}
	
	//下一页
	public int getNextcurrentPage(){
		if (currentPage>=getTotalPages()) {
			return getTotalPages();
		}
		return currentPage+1;
	}
	
	public void setNextcurrentPage(){
		if (currentPage>=getTotalPages()) {
			currentPage=getTotalPages();
		}else{
			currentPage=currentPage+1;
		}
		
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getcurrentPage() {
		return currentPage;
	}

	public void setcurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}
