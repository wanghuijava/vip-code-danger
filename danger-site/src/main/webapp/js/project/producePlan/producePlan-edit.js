var tableform=null;

$(document).ready(function(){
	
	http.post({
		url:"/project/producePlan/load"
		,data:{id:$("[name='param.id']").val()}
		,success:function(res){
			tpl.render({
				tid:"form-tmpl"
				,hook:"#form-tb"
				,data:res.response
			});
			var ctrl=new customControll();
			ctrl.scan();
		}
	})
	
	
	 tableform=new former("#edit-form",{
		url:""
	},function(){
		//成功回调
		tips("保存成功");
		location=siteUrl + "/project/producePlan/listPage";
	},function(){
		//失败回调
		tips("操作失败");
	});
});
