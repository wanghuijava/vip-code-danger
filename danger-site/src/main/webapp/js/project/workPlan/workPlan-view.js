$(document).ready(function(){
	
	http.post({
		url:"/project/workPlan/load"
		,data:{id:$("[name='param.id']").val()}
		,success:function(res){
			tpl.render({
				tid:"view-tmpl"
				,hook:"#view-tb"
				,data:res.response
			});
		}
	})
	
})