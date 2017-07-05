
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
		if($("[name='thisDay']").attr("checked")=='checked'){
			$("[name='thisDay']")[0].click();
		}
	}else{
		$("[name='param.thisMonth']").val("");
	}
	tableform.submit();
}).on("click","[name='thisDay']",function(){
	if($(this).attr("checked")=='checked'){
		$("[name='param.thisDay']").val("1");
		if($("[name='thisMonth']").attr("checked")=='checked'){
			$("[name='thisMonth']")[0].click();
		}
	}else{
		$("[name='param.thisDay']").val("");
	}
	tableform.submit();
}).on("click","#doExport",function(){
	window.open(siteUrl+'/project/produceExecute/exportData2?startTime='+$("[x-time-start]").val()+'&endTime='+$("[x-time-end]").val(),
			'_self','width=1,height=1,toolbar=no,menubar=no,location=no');
})

function exportExcel(){
	window.open(siteUrl+'/project/produceExecute/exportData','_self','width=1,height=1,toolbar=no,menubar=no,location=no');
}