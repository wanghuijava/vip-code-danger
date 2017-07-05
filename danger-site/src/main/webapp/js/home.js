$(document).ready(function(){
	
	//获取当前登录用户
	var user=userFactory.getUser();
	
	//设置页面日历信息
	var pageCalendar = template("page-calendar", {'date':new Date()});
	$("#realTime").html(pageCalendar);
	
	setInterval(function(){
		var pageCalendar = template("page-calendar", {'date':new Date()});
		$("#realTime").html(pageCalendar);
	}, 1000);
}).on("submit","#search-form",function(){
	var $form=$(this);
	var keyword=$("[name=keyword]").val();
		keyword=$.trim(keyword);
	if(keyword==""){
		return false;
	}
})
