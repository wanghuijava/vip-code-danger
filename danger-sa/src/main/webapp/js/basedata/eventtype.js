//事件类型js duxi

//初始化树结构
$(document).ready(function() {
	$.ajax({
		type : "GET",
		url : adminUrl+"/admin/basedata/eventType/findTree",
		dataType : "json",
		cache : false,
		success : function(data) {
			var json = data.response;
			$.fn.zTree.init($("#treeEventtype"), setting, json);
			searchTreeNode("");
		}
	});
}).on("click","#btnSearch",function(){
	searchTreeNode();
}).on("keyup","#eventTypeName",function(){
	if(event.keyCode == 13){
		searchTreeNode();
	}
});
//处理树的属性
var setting = {
	data: {
		key: {
			name: "eventTypeName"
		}
	},
	callback : {
		onClick : function(event, treeId, treeNode) {
			
			initEppManage(treeNode.eventTypeCode);
			/*$.ajax({
				type : "GET",
				url : adminUrl+"/basedata/eventType/edit",
				data : {
					'eventTypeCode' : treeNode.eventTypeCode
				},
				dataType : "json",
				cache : false,
				success : function(data) {
					var json = eval("(" + data.response + ")");
					$(".input-large").html(json.eventType.eventTypeCode);
					$(".input-add").html(json.eventType.eventTypeName);
					$(".input-edit").html(json.parentName);
					$("#editid").val(json.eventType.eventTypeCode);
					$("#editname").val(json.eventType.eventTypeName);
					$("#editnotes").val(json.eventType.notes);
					$("#hidParentCode").val(json.eventType.eventTypeCode);
					$("#editparentCode").val(json.eventType.parentCode);
					$("#parentName").val(json.parentName);
					if(json.eventType.common=="1"){
						$("#iscommon1").prop("checked",true);
					}else{
						$("#iscommon2").prop("checked",true);
					}
					$(".content-menu").find("a").first().click();		
				}
			});*/
		}
	}
};
//查询某事件类型下的 预案 
function initEppManage(eventTypeCode){
	$.ajax({
		type : "POST",
		url : adminUrl+"/eppManage/eppMange/findByEventTypeCode/"+eventTypeCode,
		dataType : "json",
		cache : false,
		success : function(data) {
			var json = data.response;
			$("select[name='eppManageSelect']").html("<option value='0000'>请选择</option>");
			$(json).each(function(i){
				var option = "<option value='"+json[i].planId+"'>"+json[i].planName+"</option>";
				$("select[name='eppManageSelect']").append(option);
			});
			editEventtype(eventTypeCode);
		}
	})
}



//查询树节点		
function searchTreeNode(){
	var key = $("#eventTypeName").val();
	var zTree = $.fn.zTree.getZTreeObj("treeEventtype");
	var node;
	if(key==''||key==null){
		node = zTree.getNodes()[0].children[0];
	}else {
		var nodes = zTree.getNodesByParamFuzzy("eventTypeName",key, null);
		node = nodes[0];
	}
	zTree.selectNode(node);
	if(null==node||""==node){
		alert("没有找到！！！");
	}else{
		initEppManage(node.eventTypeCode);
//		editEventtype(node.eventTypeCode);	
	}
}
//根据选中的节点编辑树
function editEventtype(code){
	$.ajax({
		type:"GET",
		url:adminUrl+"/admin/basedata/eventType/edit/"+code,
		dataType : "json",
		cache : false,
		success:function(data){
			var json = data.response;
			
			$(".input-large").html(json.eventType.eventTypeCode);
			$(".input-add").html(json.eventType.eventTypeName);
			$(".input-edit").html(json.parentName);
			if(json.eventType.common=="1"){
				$("#iscommon1").prop("checked","true");
			}else{
				$("#iscommon2").prop("checked","true");
			}
			$("#editid").val(json.eventType.eventTypeCode);
			$("#editname").val(json.eventType.eventTypeName);
			$("#editnotes").val(json.eventType.notes);
			$("#hidParentCode").val(json.eventType.eventTypeCode);
			$("#parentName").val(json.parentName);
			$("#editparentCode").val(json.eventType.parentCode);
			$(".content-menu").find("a").first().click();
			
			//点击树之后，查询事件对应的默认预案 并回显启动预案
			$.ajax({
				type:"POST",
				url:adminUrl+"/eppManage/eventTypeDefaultEpp/load/"+code,
				dataType : "json",
				cache : false,
				success:function(data){
					var planId = data.response.planId;
					if(planId){
						$("#editEventtypeForm select[name='eppManageSelect']").attr("value",planId);
					}
				}
				
			});
		}
	});
	
	
	
}
		
//切换按钮
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
				
				var parentCode = $("#editparentCode").val();
				if(parentCode == -1){
					alert("不能删除根节点",1);
				}else {
					alert("确认删除该栏目及其所有子栏目？", 2, "删除栏目", function(){
						window.location.href=adminUrl+"/admin/basedata/eventType/delete/"+id;
					});
				}
			}
		}else{
			if(tab=="add"){
				var id = $("#hidParentCode").val();
				if(null==id||""==id){
					alert("请选中上级事件类型！");
					return;
				}
			}
			$("#" + tab).show().siblings().hide();
		}
	})
});
//提交编辑表单		
$("#editSubmitBtn").live("click", function() {
	$("#submitFlag").val(1);
	if($("#editname").val()==""){
		$("#editnameSpan").css("display","inline");
		return false;
	}
	
	var eventTypeCode = $("#editEventtypeForm input[name='editid']").val();
	var planId = $("#editEventtypeForm select[name='eppManageSelect']").val();
	
	debugger;
	update_eppManage(eventTypeCode,planId)
	
	$("#editEventtypeForm").submit();
});
//提交添加表单
$("#addSubmitBtn").live("click", function() {
	$("#submitFlag").val(1);
	if($("#addid").val()==""){
		$("#addidSpan").css("display","inline");
		return false;
	}
	if($("#addname").val()==""){
		$("#addnameSpan").css("display","inline");
		return false;
	}
		
	$("#addEventtypeForm").submit();
	
//	var eventTypeCode = $("#addEventtypeForm input[name='addid']").val();
//	var planId = $("#addEventtypeForm select[name='eppManageSelect']").val();
//	
//	debugger;
//	save_eppManage(eventTypeCode,planId)

});

function update_eppManage(eventTypeCode,planId){
	//点击树之后，查询事件对应的默认预案 并回显启动预案
	$.ajax({
		type:"POST",
		url:adminUrl+"/eppManage/eventTypeDefaultEpp/save/"+eventTypeCode+"/"+planId,
		dataType : "json",
		cache : false,
		success:function(data){
			debugger;
		}
	});
}




//获得焦点隐藏提示span
//$("#editname").live("focus",function(){
//	$("#editnameSpan").css("display","none");
//});
//$("#addid").live("focus",function(){
//	$("#addidSpan").css("display","none");
//});
//$("#addname").live("focus",function(){
//	$("#addnameSpan").css("display","none");
//});
$(document).on("focus","input",function(){
	$("#"+$(this).attr("id")+"Span").css("display","none");
});
		