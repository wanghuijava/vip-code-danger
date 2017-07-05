$(document).ready(function() {
		
	$("#showDialog").dialog({
		height : 500,
		width : 400,
		title : "组织机构",
		autoOpen : false,
		model : true,
		buttons : {
			"取消" : function() {
				$("#showDialog").dialog("close");
			}
		}
	});
	
	$("#showUserDialog").dialog({
		height : 400,
		width : 450,
		title : "转登录用户",
		autoOpen : false,
		model : true,
		buttons : {
			"确定" : function() {
				orgUserChangeSysUser();
			},
			"取消" : function() {
				$("#showUserDialog").dialog("close");
			}
		}
	});
	
	var roleSetting = {
			check : {
				enable : true
			},
			callback : {
				onCheck : zTreeOnCheck
			//节点选择事件
			}
	};
	
	$("#sysUserRole").bind("click", function() {
		$("#showRoleDialog").dialog("open");
		$.ajax({
			type : "GET",
			url : adminUrl+"/admin/role/findRoleTree",
			dataType : "json",
			cache : false,
			success : function(data) {
				json = eval("(" + data.response + ")");
				$.fn.zTree.init($("#roleTree"), roleSetting, json);
				var treeObj = $.fn.zTree.getZTreeObj("roleTree");
				treeObj.expandAll(true);
			}
		});
	});

	$("#showRoleDialog").dialog({
		height : 350,
		width : 270,
		title : "用户角色",
		autoOpen : false,
		model : true,
		buttons : {
			"确定" : function() {
				getCheckNodesValue();
			},
			"取消" : function() {
				$("#showRoleDialog").dialog("close");
			}
		}
	});

	$("#orgName").bind("click", function() {
		$("#showDialog").dialog("open");
	});

	// 处理树的属性
	var setting = {
		data : {
			key : {
				name : "orgName"
			}
		},
		callback : {
			onClick : function(event, treeId, treeNode) {
				$("#orgCode").val(treeNode.orgCode);
				$("#orgName").val(treeNode.orgName);
				$("#sysOrgCode").val(treeNode.orgCode);
				$("#sysOrgName").val(treeNode.orgName);
				$("#showDialog").dialog("close");
			}
		}
	};

	$.ajax({
		type : "POST",
		url : "/starscream-sa/admin/orguser/findtree",
		dataType : "json",
		cache : false,
		success : function(data) {
			$.fn.zTree.init($("#treeOrgUser"), setting, data.response);
			var treeObj=$.fn.zTree.getZTreeObj("treeOrgUser");
			treeObj.expandAll(true);
		}
	});
	
	$("#backBtn").bind("click", function() {
		var orgCode=$("#orgCode").val();
		window.location.href=adminUrl+"/admin/orguser/backlist?orgCode="+orgCode;
		//history.back();
	});
	//判断通讯录用户是否转入到了登录用户
	validateUserIfExist();
})

$("#submitBtn").live("click", function() {
	var username=$.trim($("#username").val());
	if(username==null || username==""){
		$("#usernameValidate").html("请输入用户姓名");
		$("#username").focus();
		return false;
	}
	
	var position=$.trim($("#position").val());
	if(position==null || position==""){
		$("#positionValidate").html("请输入用户职位");
		$("#position").focus();
		return false;
	}
	
	var mobileTel=$.trim($("#mobileTel").val());
	if(mobileTel==null || mobileTel==""){
		$("#mobileTelValidate").html("请输入手机号码");
		$("#mobileTel").focus();
		return false;
	}
	
	var reg=new RegExp("^[0-9]*$");
	if(!reg.test(mobileTel) || mobileTel.length!=11){
		$("#mobileTelValidate").html("请正确输入手机号");
		$("#mobileTel").focus();
		return false;
	}
	
	var officeTel=$.trim($("#officeTel").val());
	if(officeTel==null || officeTel==""){
		$("#officeTelValidate").html("请输入办公电话");
		$("#officeTel").focus();
		return false;
	}
	
	if(!reg.test(officeTel)){
		$("#officeTelValidate").html("办公电话请输入数字");
		$("#officeTel").focus();
		return false;
	}

	$("#addUserForm").submit();
});

$("#username").change(function(){
	var username=$.trim($("#username").val());
	if(username!=null && username!=""){
		$("#usernameValidate").html("*");
	}
});

$("#position").change(function(){
	var position=$.trim($("#position").val());
	if(position!=null && position!=""){
		$("#positionValidate").html("*");
	}
});

$("#mobileTel").change(function(){
	var mobileTel=$.trim($("#mobileTel").val());
	if(mobileTel!=null && mobileTel!=""){
		$("#mobileTelValidate").html("*");
	}
});

$("#officeTel").change(function(){
	var officeTel=$.trim($("#officeTel").val());
	if(officeTel!=null && officeTel!=""){
		$("#officeTelValidate").html("*");
	}
});

$("#syssubmitBtn").live("click", function() {
	$("#addsysUserForm").submit();
});

function zTreeOnCheck(e, treeId, treeNode) {
	//alert(treeNode.id);
}

function getCheckNodesValue() {
	var zTree = $.fn.zTree.getZTreeObj("roleTree");
	var nodes = zTree.getCheckedNodes();
	var nodeIds = "";
	var nodeNames = "";
	var nodeIdsTemp = "";
	var nodeNameTemp = "";

	for ( var i = 0; i < nodes.length; i++) {
		var nodeIdValue = nodes[i].id;
		var nodeNameValue = nodes[i].name;
		nodeIds += nodeIdValue + ",";
		nodeNames += nodeNameValue + ",";
	}
	if (nodeIds != "") {
		nodeIdsTemp = nodeIds.substring(0, nodeIds.length - 1);
		nodeNameTemp = nodeNames.substring(0, nodeNames.length - 1);
	}

	$("#sysUserRole").val(nodeNameTemp);
	$("#sysRoleIds").val(nodeIdsTemp);
	$("#showRoleDialog").dialog("close");
}
//通讯录用户转登录用户
$("#changeBtn").live("click", function() {
	
	
	var userId=$.trim($("#userId").val());
	var orgName=$.trim($("#orgName").val());
	var orgCode=$.trim($("#orgCode").val());
	var username=$.trim($("#username").val());
	
	$("#sysOrgName").val(orgName);
	$("#sysOrgCode").val(orgCode);
	$("#sysTrueName").val(username);

	
	$.ajax({
		type : "POST",
		url : adminUrl+"/admin/user/createAccount",
		data : {
			'username':username
		},
		dataType : "json",
		cache : false,
		success : function(data) {
			$("#sysUserName").val(data.loginName);
		}
	});
	
	$("#showUserDialog").dialog("open");
});

function orgUserChangeSysUser(){
	var userId=$.trim($("#userId").val());
	var orgName=$.trim($("#orgName").val());
	var orgCode=$.trim($("#orgCode").val());
	var sysUserName=$.trim($("#sysUserName").val());
	var sysPassword=$.trim($("#sysPassword").val());
	var confirmSysPassword=$.trim($("#confirmSysPassword").val());
	var sysTypeCode=$.trim($("#sysTypeCode").val());
	var sysUserRole=$.trim($("#sysUserRole").val());
	var sysRoleIds=$.trim($("#sysRoleIds").val());
	
	//验证系统用户是否存在
	$.ajax({
		type : "POST",
		url : adminUrl+"/admin/user/validateUserIfExist",
		data : {
			'userId' :userId,
			'username' : sysUserName
		},
		dataType : "json",
		cache : false,
		success : function(data) {
			var flag=data.flag;
			if(flag=='0'){
				//系统用户中不存在该用户可以转入
				if (sysUserName == null || sysUserName == "") {
					$("#sysUserNameValidate").html("账号不能为空");
					$("#sysUserName").focus();
					return false;
				}

				if (sysPassword == null || sysPassword == "") {
					$("#sysPasswordValidate").html("请输入密码");
					$("#sysPassword").focus();
					return false;
				}

				if (confirmSysPassword == null || confirmSysPassword == "") {
					$("#confirmSysPasswordValidate").html("请输入确认密码");
					$("#confirmSysPassword").focus();
					return false;
				}

				if (sysPassword != confirmSysPassword) {
					$("#confirmSysPasswordValidate").html("两次输入的密码不一致");
					$("#confirmSysPassword").focus();
					return false;
				}

				if (sysTypeCode == null || sysTypeCode == "") {
					$("#sysTypeCodeValidate").html("请选择用户类型");
					$("#sysTypeCode").focus();
					return false;
				}

//				if (sysUserRole == null || sysUserRole == "") {
//					$("#sysUserRoleValidate").html("请选择用户角色");
//					$("#sysUserRole").focus();
//					return false;
//				}
				
				$.ajax({
					type : "POST",
					url : adminUrl+"/admin/user/orgUserChangeSysUser",
					data : {
						'userId' : userId,
						'username' : sysUserName,
						'password' : sysPassword,
						'userRole' : sysUserRole,
						'roleIds' : sysRoleIds,
						'typeCode' : sysTypeCode,
						'orgCode' : orgCode,
						'orgName' : orgName
					},
					dataType : "json",
					cache : false,
					success : function(data) {
						json = eval("(" + data.response + ")");
						if (json != null && json[0].success == true) {
							alert("转入成功");
							history.back();
						}
					}
				});
			}else if(flag=='1'){
				//该通讯录用户已转入到系统用户中，不能再次转入
				alert("该通讯录用户已转入到系统用户中，不能再次转入");
			}else if(flag=='2'){
				//系统用户中存在该账号，请重新输入账号
				alert("系统用户中存在该账号，请重新输入账号");
			}else{
				//该通讯录用户已转入到系统用户中,且账号已存在不能转入
				alert("该通讯录用户已转入到系统用户中,且账号已存在不能转入");
			}
		}
	});
	
	
}

//判断通讯录用户是否转入到了登录用户
function validateUserIfExist(){
	var userId=$.trim($("#userId").val());
	var sysUserName=$.trim($("#sysUserName").val());
	$.ajax({
		type : "POST",
		url : adminUrl+"/admin/user/validateUserIfExist",
		data : {
			'userId' :userId,
			'username' : sysUserName
		},
		dataType : "json",
		cache : false,
		success : function(data) {
			var flag=data.flag;
			if(flag!='0'){
				$("#changeBtn").hide();
			}
		}
	});
}


