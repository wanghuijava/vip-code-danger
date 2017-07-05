$(document).ready(function(){

	if($.browser.webkit){
		setTimeout(function(){
			$("input:-webkit-autofill").each(function(){
				var $this=$(this);
				var val=$this.val();
				var name=$this.attr("name");
				$this.before(this.outerHTML);
				$("[name="+name+"]").val(val).eq(1).hide().attr("disabled",true);
			});
		},100)
	}
	
	if ($.browser.msie) {
		var width = $(this).width();
		if(width<=1900){
			$(".login-main-bg").addClass("small");
		}else{
			$(".login-main-bg").removeClass("small");
		}
	}
	
}).on("submit","form",function(e){
	e.preventDefault();
	var $this=$(this);
	var flag=$this.fval("validate");
	if(flag){
		var param=$this.serialize();
		userFactory.login(param)
		.then(function(result){
			if(result.code==200){
				userFactory.setUser(result.response);
				location=siteUrl+"/index"
			}else{
				showIptTip($("[name='username']").eq(0),"",{content:result.message})
			}
		})
	}
}).on("focus",".ipt",function(){
	$(this).parent().addClass("focus")
}).on("blur",".ipt",function(){
	$(this).parent().removeClass("focus")
})
if ($.browser.msie) {
	$(window).resize(function(){
		var width = $(this).width();
		if(width<=1900){
			$(".login-main-bg").addClass("small");
		}else{
			$(".login-main-bg").removeClass("small");
		}
	})
}
