$(document).ready(function(){
	
	var ctrl=new customControll();
	ctrl.scan();
	
	var _former=new former("#add-form",{
		url:""
	},function(){
		//成功回调
		tips("操作成功",function(){
			confirm("是否继续添加",function(){
				location=siteUrl + "/project/producePlan/addPage";
			},function(){
				location=siteUrl + "/project/producePlan/listPage";
			})
			
		});
	},function(){
		//失败回调
		tips("操作失败");
	});
})