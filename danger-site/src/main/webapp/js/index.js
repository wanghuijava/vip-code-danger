$(document).ready(function(){
	//Update by DX (根据外层隐藏字段判断界面定位界面)
	var user=userFactory.getUser();
	tpl.render({
		tid:"user-tmpl"
		,hook:"#user-info"
		,data:user
	})
	
	var actionPage = $(".layout-main").data("action");
	//检查是否有影藏
	if($.trim(actionPage) != "" && $(".menu li[data-id='" + actionPage +"']")){
		if($(".menu .active")){
			$(".menu .active").removeClass("active");
		}
		$(".menu li[data-id='" + actionPage +"'] a").trigger("click");
		$(".menu li[data-id='" + actionPage +"']").addClass("active");
	} else if($(".menu .active").size()>0) {
		$(".menu .active a").trigger("click");
	} else {
		$(".menu li:first a").trigger("click");
	}

}).on("click",".menu li a",function(e){
	e.preventDefault();
	var $this=$(this);
	$this.parent().siblings().removeClass("active");
	$this.parent().addClass("active");
	var href=$this.attr("href");
	if($this.data("target")){
		location=href;
		return false;
	}
	var iframe=$("[data-rel=main]").get(0);
	if(iframe){
		iframe.src=href;
	}
})