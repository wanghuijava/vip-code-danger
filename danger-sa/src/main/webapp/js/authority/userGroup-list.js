$(document).ready(function() {
	
	var setting = {
			callback : {
				onClick : function(event, treeId, treeNode) {	
			        var id = treeNode.id;
			        //根据组id查询组对象
			        findUserGroupById(id);
					var parentNode=treeNode.getParentNode();
					if(parentNode){
						$("#parentId").val(parentNode.id);
					}else
						$("#parentId").val("0000");
					//根据组id查询组下面的用户
			        findUserByGroupId(id);
				}
			}
		};
	
	$.ajax({
		type : "GET",
		url : adminUrl+"/admin/userGroup/findTree",
		dataType : "json",
		cache : false,
		success : function(data) {
			json = eval("(" + data.response + ")");
			$.fn.zTree.init($("#userGroupTree"), setting, json);
			var treeObj = $.fn.zTree.getZTreeObj("userGroupTree");
			treeObj.expandAll(true);
		}
	});
	
	function findUserGroupById(id){
		$.ajax({
			type : "Post",
			url : adminUrl+"/admin/userGroup/findUserGroupById",
			data : {
				'id':id
			},
			dataType : "json",
			cache : false,
			success : function(data) {
				json = eval("(" + data.response + ")");
				if(json!=null && json!=""){
					$("#userGroupId").val(json.id);
					$("#groupName").val(json.groupName);
					$("#groupInfo").val(json.groupInfo);
					
				}
			}
		});
	}
	
	function findUserByGroupId(id){
		$.ajax({
			type : "Post",
			url : adminUrl+"/admin/orguser/findUserGroupById",
			data : {
				'groupId':id
			},
			dataType : "json",
			cache : false,
			success : function(data) {
				json = eval("(" + data.response + ")");
				var html="";
				if(json!=null && json.length>0){
					for(var i=0;i<json.length;i++){
						if("1"==json[i].status){
							json[i].status="正常";
						}else{
							json[i].status="注销";
						}
						html+="<tr>"
						+"<td>"+json[i].username+"</td>"
						+"<td>"+json[i].position+"</td>"
						+"<td>"+json[i].mobileTel+"</td>"
						+"<td>"+json[i].officeTel+"</td>"
						+"<td>"+json[i].status+"</td>"
						+"<td>"
						+"<a class='btn btn-small btn-danger editUserGroup' href='javaScript:void(0);'  id="+json[i].id+"><i class='icon-edit icon-white'></i>移除</a>"
						+"</td>"
						+"</tr>"
					}
					$("#mytbody").html(html);
				}else{
					$("#mytbody").html("");
				}
				
				$(".editUserGroup").unbind("click").bind("click",function(e){
					e.preventDefault();
					var userId = $(this).attr("id");
					var userGroupId=$("#userGroupId").val();
					var r=confirm("确定移除吗")
					 if (r==true)
				      {
						 //将用户从组里移除
						 $.ajax({
								type : "GET",
								data :{"userId":userId,"userGroupId":userGroupId},
								url : adminUrl+"/admin/orguser/removeUser",
								dataType : "json",
								cache : false,
								success : function(data) {
									if(data.code=='200'){
										alert(data.message);
										findUserByGroupId(userGroupId);
									}
								}
							});
				      }
				    else
				      {
				    	
				      }
				});
				
			}
		});
	}
	
	$("#editSubmitBtn").click(function(){
		var id=$("#userGroupId").val();
		var parentId=$("#parentId").val();
		var groupName=$("#groupName").val();
		var groupInfo=$("#groupInfo").val();
		if(groupName==null || groupName==""){
			$("#groupNameValidate").html("请输入组名");
			$("#groupName").focus();
			return false;
		}
		if(groupInfo==null || groupInfo==""){
			$("#groupInfoValidate").html("请输入组说明");
			$("#groupInfo").focus();
			return false;
		}
		
		$.ajax({
			type : "Post",
			url : adminUrl+"/admin/userGroup/save",
			data : {
				'id':id,
				'parentId':parentId,
				'groupName':groupName,
				'groupInfo':groupInfo
			},
			dataType : "json",
			cache : false,
			success : function(data) {
				json = eval("(" + data.response + ")");
				if(json.success=true){
					alert("操作成功");
					$("#userGroupId").val("");
					$("#parentId").val("");
					$("#groupName").val("");
					$("#groupInfo").val("");
					window.location.reload();
				}
			}
		});
	})
	
	$("#groupName").change(function() {
		var groupName = $.trim($("#groupName").val());
		if (groupName != null && groupName != "") {
			$("#groupNameValidate").html("*");
		}
   });
	
	$("#groupInfo").change(function() {
		var groupInfo = $.trim($("#groupInfo").val());
		if (groupInfo != null && groupInfo != "") {
			$("#groupInfoValidate").html("*");
		}
   });
	
	$("#addSubmitBtn").click(function(){
		var id=$("#adduserGroupId").val();
		var parentId=$("#userGroupId").val();
		var groupName=$("#addgroupName").val();
		var groupInfo=$("#addgroupInfo").val();
		
		if(groupName==null || groupName==""){
			$("#groupNameValidate").html("请输入组名");
			$("#groupName").focus();
			return false;
		}
		if(groupInfo==null || groupInfo==""){
			$("#groupInfoValidate").html("请输入组说明");
			$("#groupInfo").focus();
			return false;
		}
		
		$.ajax({
			type : "Post",
			url : adminUrl+"/admin/userGroup/save",
			data : {
				'id':id,
				'parentId':parentId,
				'groupName':groupName,
				'groupInfo':groupInfo
			},
			dataType : "json",
			cache : false,
			success : function(data) {
				json = eval("(" + data.response + ")");
				if(json.success=true){
					alert("操作成功");
					$("#userGroupId").val("");
					$("#addgroupName").val("");
					$("#addgroupInfo").val("");
					window.location.reload();
				}
			}
		});
	})
	
	$("#addgroupName").change(function() {
		var groupName = $.trim($("#addgroupName").val());
		if (groupName != null && groupName != "") {
			$("#addgroupNameValidate").html("*");
		}
   });
	
	$("#addgroupInfo").change(function() {
		var groupInfo = $.trim($("#addgroupInfo").val());
		if (groupInfo != null && groupInfo != "") {
			$("#addgroupInfoValidate").html("*");
		}
   });
	

	//切换按钮
	$(function() {
		$(".content-menu").on("click", "a", function() {
			$(this).addClass("on").siblings().removeClass("on");
			var tab = $(this).attr("data");
			if(tab=="delete"){
				var id = $("#userGroupId").val();
				if(null==id||""==id){
					alert("请选中删除项！");
					return;
				}else{
					$.ajax({
						type : "Post",
						url : adminUrl+"/admin/userGroup/findByParentId",
						data : {
							'id':id
						},
						dataType : "json",
						cache : false,
						success : function(data) {
							var flag=data.flag;
							if(flag=="1"){
								alert("该节点下面有子节点不能删除");
							}else{
								alert("确认删除该栏目及其所有子栏目？", 2, "删除栏目", function(){
									$.ajax({
										type : "GET",
										url : adminUrl+"/admin/userGroup/delete",
										data : {
											'id':id
										},
										dataType : "json",
										cache : false,
										success : function(data) {
											window.location.reload();
										}
									});
									//window.location.href=adminUrl+"/admin/userGroup/delete?id="+id;
								});
							}
						}
					});
				}
			}else{
				if(tab=="add"){
					var id = $("#userGroupId").val();
					if(null==id||""==id){
						alert("请选中上级事件类型！");
						return;
					}
				}
				$("#" + tab).show().siblings().hide();
			}
		})
	});
	
			
})