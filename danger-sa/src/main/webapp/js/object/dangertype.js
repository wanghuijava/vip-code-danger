//常量定义
var baseUrl = "";
//初始化
$(document).ready(function() {
	baseUrl = adminUrl + "/admin/object/dangerType";
	
	//危险源分类树初始化
	$.ajax({
		type : "GET",
		url : baseUrl + "/findTree",
		dataType : "json",
		cache : false,
		success : function(data) {
			var result = eval("(" + data.response + ")");
			
			var setting = {
				callback : {
					onClick : function(event, treeId, treeNode) {
						editDangerType(treeNode.id);
					}
				}
			};
			$.fn.zTree.init($("#treeDangerType"), setting, result);
			searchTreeNode("");
		}
	});
}).on("click","#btnSearch",function(){
	searchTreeNode();
}).on("keydown",function(){
	if(event.keyCode == 13)
	searchTreeNode();
}).on("click",".content-menu a",function() {
	//var index = $(this).index();
	//$(".content-menu").find("a").removeClass("on").eq(index).addClass("on");
	$(this).addClass("on").siblings().removeClass("on");
	var tab = $(this).attr("data");
	if (tab == "delete") {
		var id = $("#selectId").val();
		if (null == id || "" == id) {
			alert("请选中删除项！");
			return;
		} else {
			alert("确认删除该栏目及其所有子栏目？",2,"删除栏目",function() {
					window.location.href = baseUrl + "/delete/" + id;
				});
		}
	} else if(tab == 'add') {
		//进入新增初始页面
		var id = $('#selectId').val();
		$.ajax({
			type:"GET",
			url:baseUrl+"/load",
			data:{'id':id},
			success:function(result){
				$(".main-content").html(result);
			}
		});
	}else if(tab == 'edit'){
		editDangerType($('#selectId').val());
	}
}).on("click","#submitBtn", function() {
	var tab = $(".content-menu a").find(".on").attr("data");
	if (tab=='add' && $("#dangerTypeId").val() == "") {
		alert("危险源分类编码不能为空！");
		return;
	} 
	if ($("#dName").val() == "") {
		alert("危险源分类名称不能为空！");
		return;
	} 
	//更新或者保存
	$("#dangerTypeForm").attr('action',baseUrl + '/save');
	$("#dangerTypeForm").submit();
})

//查询
function searchTreeNode(){
	var key = $("#dangerTypeName").val();
	var zTree = $.fn.zTree.getZTreeObj("treeDangerType");
	var node;
	if(key==''||key==null){
		node = zTree.getNodes()[0].children[0];
	}else {
		var nodes = zTree.getNodesByParamFuzzy("name",key, null);
		node = nodes[0];
	}
	
	zTree.selectNode(node);
	editDangerType(node.id);
}

//进入编辑页面
function editDangerType(id){
	$('#selectId').val(id);
	$.ajax({
		type:"POST",
		url:baseUrl + "/update/"+id,
		cache : false,
		success:function(result){
			$(".main-content").html(result);
			//编辑页签选中
			$(".content-menu a").removeClass("on").eq(0).addClass("on");
		}
	});
	
}
