$(document).ready(function(){
	
	var ctrl=new customControll();
	ctrl.scan();
	
	var _former=new former("#add-form",{
		url:""
	},function(){
		//成功回调
		tips("操作成功",function(){
			confirm("是否继续添加",function(){
				location=siteUrl + "/project/workExecute/addPage";
			},function(){
				location=siteUrl + "/project/workExecute/listPage";
			})
			
		});
	},function(){
		//失败回调
		tips("操作失败");
	});
}).on("change","[name='param.workPlanId']",function(){
	$this =$(this);
	var workPlanId = $("[name='param.workPlanId']").val();
	if(workPlanId!=''){
		var optionSelet = $("select [value='"+workPlanId+"']");
		var title= optionSelet.data("title");
		var content = title.split("#@");
		$("[name='param.workLevel']").val(content[0]);
		$("[name='param.summary']").val(content[1]);
		$("[name='param.examiner']").val(content[2]);
		$("[name='param.checker']").val(content[3]);
		$("[name='param.director']").val(content[4]);
	}else{
		$("[name='param.workLevel']").val('');
		$("[name='param.summary']").val('');
		$("[name='param.examiner']").val('');
		$("[name='param.checker']").val('');
		$("[name='param.director']").val('');
	}
})