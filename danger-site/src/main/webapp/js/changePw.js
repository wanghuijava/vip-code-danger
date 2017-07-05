
var changePwd;
$(document).ready(function(){
	
}).on("click","#modify-password",function(){
	changePwd = art.dialog({
		id:"modiy"
		,title:"修改密码"
		,content:$("#modify-panel").html()
		,close:function(){$("[poshy-tip]").poshytip('destroy');}
	})
}).on("click",".modifypwd-cancel-btn",function(){
	changePwd.close();
}).on("click",".modifypwd-save-btn",function(){ 	
	
	var $form=$(this).closest("form");
	var flag=$form.fval("validate");
	if(!flag) return false;
	
	var oldPwd = $("#oldPwd").val();
	//验证旧密码是否正确 
	
	var password = $("#password").val();
	var password2 = $("#password2").val();
	if(password!=password2){
		alert("两次输入的密码不一致！");
		return ;
	}
    http.post({
		url:"/basedata/user/modifypwd"
		,data:"oldPwd="+oldPwd+"&password="+password
		,success:function(res){
			tips(res.message,function(){
				});
			if(res.code == 200){
				changePwd.close();
				//window.location.href=siteUrl+"/login";
			} 
		}
	 })
});

 