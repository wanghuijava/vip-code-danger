
var tableform=null;

$(document).ready(function(){
	
	tableform=new former($("#list-form"),{
		tid:"list-tmpl"
		,content:"#list-tb"
	},function(){
		//成功回调
	},function(){
		//失败回调
	})
	
	tableform.submit();
	
	$("#exportData").on("click",function(){
		exportExcel();
	})
	
}).on("click","#js-search-param .changeTime",function(){
	$this =$(this);
	var type= $this.data("type");
	changeTime(type);
	tableform.submit();
}).on("click","[name='thisMonth']",function(){
	if($(this).attr("checked")=='checked'){
		$("[name='param.thisMonth']").val("0");
		if($("[name='nextMonth']").attr("checked")=='checked'){
			$("[name='nextMonth']")[0].click();
		}
	}else{
		$("[name='param.thisMonth']").val("");
	}
	tableform.submit();
}).on("click","[name='nextMonth']",function(){
	if($(this).attr("checked")=='checked'){
		$("[name='param.nextMonth']").val("1");
		if($("[name='thisMonth']").attr("checked")=='checked'){
			$("[name='thisMonth']")[0].click();
		}
	}else{
		$("[name='param.nextMonth']").val("");
	}
	tableform.submit();
}).on("click","#doExport",function(){
	window.open(siteUrl+'/project/workPlan/exportData2?startTime='+$("[x-time-start]").val()+'&endTime='+$("[x-time-end]").val(),
			'_self','width=1,height=1,toolbar=no,menubar=no,location=no');
})

function exportExcel(){
	startTime = $("input[name='input_startTime']").val();
	endTime = $("input[name='input_endTime']").val();
	window.open(siteUrl+'/project/workPlan/exportData','_self','width=1,height=1,toolbar=no,menubar=no,location=no');
}