$(document).ready(function(){
	//Update by DX (根据外层隐藏字段判断界面定位界面)
	var actionPage = $(".layout-main").data("action");
	//检查是否有影藏
	if(actionPage && $.trim(actionPage) != "" && $(".v-menu li[data-id='" + actionPage +"']")){
		if($(".v-menu .active")){
			$(".v-menu .active").removeClass("active");
		}
		$(".v-menu li[data-id='" + actionPage +"'] a").trigger("click");
		$(".v-menu li[data-id='" + actionPage +"']").addClass("active");
	} else if($(".menu .active").size()>0) {
		$(".v-menu .active a").trigger("click");
	} else {
		$(".v-menu li:first a").trigger("click");
	}
}).on("click",".v-menu li a",function(e){
	e.preventDefault();
	var $this=$(this);
	$this.parent().siblings().removeClass("active");
	$this.parent().addClass("active");
	var href=$this.attr("href");
	if($this.data("target")){
		location=href;
		return false;
	}
	var iframe=$("[data-rel=sub]").get(0);
	if(iframe){
		iframe.src=href;
	}
})