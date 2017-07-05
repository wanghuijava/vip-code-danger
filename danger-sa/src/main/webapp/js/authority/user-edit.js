$("#submitBtn").live("click", function(){
		var username=$.trim($("#username").val());
		var userRole=$.trim($("#userRole").val());
		var typeCode=$.trim($("#typeCode").val());
		var orgName=$.trim($("#orgName").val());
		
		if(orgName==null || orgName==""){
			$("#orgNameValidate").html("机构名称不能为空");
			$("#orgName").focus();
			return false;
		}
		
		if(username==null || username==""){
			$("#userNameValidate").html("用户名不能为空");
			$("#username").focus();
			return false;
		}
		
		if(typeCode==null || typeCode==""){
			$("#typeCodeValidate").html("用户类型不能为空");
			$("#typeCode").focus();
			return false;
		}
		
		if(userRole==null || userRole==""){
			$("#userRoleValidate").html("请选择用户角色");
			$("#userRole").focus();
			return false;
		}

		
		$("#editSysUserForm").submit();
	});
	$("#backBtn").on("click",function(){
		window.location.href=adminUrl+"/admin/user/list";
	});

	$(document).ready(function(){
		var setting = {
				check:{
					enable:true
				},callback:{
					onCheck:zTreeOnCheck//节点选择事件
				}
		};
		
		var orgSetting = {
				callback:{
					onClick:zTreeOrgOnCheck//节点选择事件
				}
		};
		
		$("#showOrgDialog").dialog({
			height:500,
			width:400,
			title:"组织机构",
			autoOpen:false,
			model:true,
			buttons:{
				"关闭":function(){
					$("#showOrgDialog").dialog("close");
				}
			}
		});
		
		$("#orgName").bind("click",function(){
			$("#showOrgDialog").dialog("open");
			$.ajax({
				type : "GET",
				url : "/starscream-sa/admin/orguser/findOrgTree",
				dataType : "json",
				cache : false,
				success : function(data) {
					json = eval("(" + data.response + ")");
					$.fn.zTree.init($("#treeOrgDemo"), orgSetting, json);
					var treeObj=$.fn.zTree.getZTreeObj("treeOrgDemo");
					treeObj.expandAll(true);
				}
			});
		});
		
		
		$("#showRoleDialog").dialog({
			height:350,
			width:270,
			title:"用户角色",
			autoOpen:false,
			model:true,
			buttons:{
				"确定":function(){
					getCheckNodesValue();
				},"取消":function(){
					$("#showRoleDialog").dialog("close");
				}
			}
		});
		
		$("#modifyPasswordDialog").dialog({
			height:300,
			width:400,
			title:"修改密码",
			autoOpen:false,
			model:true,
			buttons:{
				"确定":function(){
					modifyPassword();
				},"取消":function(){
					$("#modifyPasswordDialog").dialog("close");
				}
			}
		});
		
		
		
		
		
		
		$("#userRole").bind("click",function(){
			$("#showRoleDialog").dialog("open");
			$.ajax({
				type : "GET",
				url : adminUrl+"/admin/role/findRoleTree",
				dataType : "json",
				cache : false,
				success : function(data) {
					json = eval("(" + data.response + ")");
					$.fn.zTree.init($("#treeRoleDemo"), setting, json);
					var treeObj=$.fn.zTree.getZTreeObj("treeRoleDemo");
					treeObj.expandAll(true);
					alreadyCheckNodes();
				}
			});
			
		});
		
		$("#modifyPassWord").on("click",function(){
			$("#userNameTemp").val($("#username").val());
			$("#modifyPasswordDialog").dialog("open");
		})
		
		$("#password").change(function() {
			var password = $.trim($("#password").val());
			if (password != null && password != "") {
				$("#passwordValidate").html("*");
			}
		});
		
		$("#confirmPassword").change(function() {
			var confirmPassword = $.trim($("#confirmPassword").val());
			if (confirmPassword != null && confirmPassword != "") {
				$("#confirmPasswordValidate").html("*");
			}
		});
		
		$("#oldPassword").change(function() {
			var oldPassword = $.trim($("#oldPassword").val());
			if (oldPassword != null && oldPassword != "") {
				$("#oldPasswordValidate").html("*");
			}
		});
		
		
	});
	
	
	function zTreeOrgOnCheck(e,treeId,treeNode){
		$("#orgName").val(treeNode.name);
		$("#orgCode").val(treeNode.id);
		$("#showOrgDialog").dialog("close");
	}
	function zTreeOnCheck(e,treeId,treeNode){
		//alert(treeNode.id);
		//alert(treeNode);
	}
	
	//选中已有角色
	function alreadyCheckNodes(){
		var roleIds=$.trim($("#roleIds").val());
		var roleIdArray=roleIds.split(",");
		var treeObj=$.fn.zTree.getZTreeObj("treeRoleDemo");
		var nodes = treeObj.getNodes();
		
		if(nodes!=null && roleIdArray!=null){
			for(var i=0;i<nodes.length;i++){
				for(var j=0;j<roleIdArray.length;j++){
					if(nodes[i].id==roleIdArray[j]){
						var tId = nodes[i].tId;
						var node = treeObj.getNodeByTId(tId);
						treeObj.checkNode(node, true, true);
						break;
					}
				}
			}
		}
	}

	function getCheckNodesValue(){
		var zTree=$.fn.zTree.getZTreeObj("treeRoleDemo");
		var nodes=zTree.getCheckedNodes();
		var nodeIds="";
		var nodeNames="";
		var nodeIdsTemp="";
		var nodeNameTemp="";
		
		for(var i=0;i<nodes.length;i++){
			var nodeIdValue=nodes[i].id;
			var nodeNameValue=nodes[i].name;
			nodeIds+=nodeIdValue+",";
			nodeNames+=nodeNameValue+",";
		}
		if(nodeIds!=""){
			nodeIdsTemp=nodeIds.substring(0,nodeIds.length-1);
			nodeNameTemp=nodeNames.substring(0,nodeNames.length-1);
		}
		
		$("#userRole").val(nodeNameTemp);
		$("#roleIds").val(nodeIdsTemp);
		$("#showRoleDialog").dialog("close");
	}
	
	function modifyPassword(){
		var username=$.trim($("#username").val());
		var oldPassword=$.trim($("#oldPassword").val());
		var password=$.trim($("#password").val());
		var confirmPassword=$.trim($("#confirmPassword").val());
		if(username==null || username==""){
			$("#userNameValidate").html("用户名不能为空");
			$("#username").focus();
			return false;
		}
		
		if(oldPassword==null || oldPassword==""){
			$("#oldPasswordValidate").html("请输入原密码");
			$("#oldPassword").focus();
			return false;
		}
		
		if(password==null || password==""){
			$("#passwordValidate").html("密码不能为空");
			$("#password").focus();
			return false;
		}
		
		if(confirmPassword==null || confirmPassword==""){
			$("#confirmPasswordValidate").html("请输入确认密码");
			$("#confirmPassword").focus();
			return false;
		}
		
		if(password!=confirmPassword){
			$("#confirmPasswordValidate").html("两次输入的密码不一致");
			return false;
		}
		
		$.ajax({
			type : "POST",
			url : adminUrl+"/admin/user/modifyPassword",
			data : {
				'username' : username,
				'oldPassword':oldPassword,
				'password' : password
			},
			dataType : "json",
			cache : false,
			success : function(data) {
				var flag=data.response.flag
				if(flag!=null && flag=='1'){
					alert("修改成功");
					$("#modifyUser")[0].reset();
					$("#modifyPasswordDialog").dialog("close");
				}else{
					alert("原密码不正确,请重新输入");
				}
			}
		});
		
	}