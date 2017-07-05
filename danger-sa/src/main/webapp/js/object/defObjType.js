//常量定义
var baseUrl = "";

var setting = {
		data : {
			key : {
				name : "defObjTypeName"
			}
		},
		callback : {
		onClick : function(event, treeId, treeNode) {
			editDefObjtype(treeNode.defObjTypeCode);
		}
	}
};

//初始化
$(document).ready(function() {
	baseUrl = adminUrl + "/admin/object/defObj";
	$.ajax({
		type : "GET",
		url : baseUrl+"/findTree",
		dataType : "json",
		cache : false,
		success : function(data) {
			json = eval("(" + data.response + ")")
			$.fn.zTree.init($("#treeDefObjType"), setting, json);
			searchTreeNode("");
		}
	});
}).on("click","#btnSearch",function(){
	searchTreeNode();
}).on("keydown",function(){
	if(event.keyCode == 13)
	searchTreeNode();
});

function searchTreeNode(){
	var key = $("#DefObjTypeName").val();
	var zTree = $.fn.zTree.getZTreeObj("treeDefObjType");
	var node;
	if(key==''||key==null){
		node = zTree.getNodes()[0].children[0];
	}else {
		var nodes = zTree.getNodesByParamFuzzy("defObjTypeName",key, null);
		node = nodes[0];
	}
	if(node==null||node==''){
		alert("没有找到数据！");
		return;
	}else{
		zTree.selectNode(node);
		editDefObjtype(node.defObjTypeCode);
	}			
}

function editDefObjtype(id){
	debugger
	$.ajax({
		type:"GET",
		url:baseUrl+"/load/"+id,
		dataType : "json",
		cache : false,
		success:function(data){
			var json = eval("(" + data.response + ")");
			$(".input-large").html(json.defObjType.defObjTypeCode);
			$(".input-largeName").html(json.defObjType.defObjTypeName);
			$(".input-parentName").html(json.parentName);
			$("#editid").val(json.defObjType.defObjTypeCode);
			$("#editname").val(json.defObjType.defObjTypeName);
			$("#editparentId").val(json.defObjType.parentCode);
			$("#editnotes").val(json.defObjType.notes);
			$("#hidParentId").val(json.defObjType.defObjTypeCode);
			$("#parentName").val(json.parentName);
			$(".content-menu").find("a").first().click();
		}
	});
}

$(function() {
	$(".content-menu").on("click", "a", function() {
		$(this).addClass("on").siblings().removeClass("on");
		var tab = $(this).attr("data");
		if(tab=="delete"){
			var id = $("#editid").val();
			if(null==id||""==id){
				alert("请选中删除项！");
				return;
			}else{
				var parentCode = $("#editparentId").val();
				if(parentCode == "00000"){
					alert("不能删除根节点",1);
				}else {
					alert("确认删除该栏目及其所有子栏目？", 2, "删除栏目", function(){
						window.location.href=baseUrl+"/delete/"+id;
					});
				}
			}
			
		}else{
			$("#" + tab).show().siblings().hide();
		}
	})
});

$("#editSubmitBtn").live("click", function() {
	if($("#editname").val()==""){
		$("#editnameSpan").prev(".prompt_mt").css("display","none");
		$("#editnameSpan").css("display","inline");
		return false;
	}else{
		$("#submitFlag").val(1);
		$("#editDefObjTypeForm").submit();
	}
});

$("#addSubmitBtn").live("click", function() {
	if($("#addid").val()==""){
		$("#addidSpan").prev(".prompt_mt").css("display","none");
		$("#addidSpan").css("display","inline");
		return false;
	}else if($("#addname").val()==""){
		$("#addnameSpan").prev(".prompt_mt").css("display","none");
		$("#addnameSpan").css("display","inline");
		return false;
	}else{
		$("#submitFlag").val(1);
		$("#addDefObjTypeForm").submit();
	}
});

$(document).on("focus","input",function(){
	$("#"+$(this).attr("id")+"Span").css("display","none");
	$("#"+$(this).attr("id")+"Span").prev(".prompt_mt").css("display","inline");
});
