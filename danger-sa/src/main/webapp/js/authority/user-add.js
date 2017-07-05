$(document).ready(function() {
			var setting = {
				callback : {
					onClick : function(event, treeId, treeNode) {
						var orgName = treeNode.name;
						var orgCode = treeNode.id;
						$("#orgCode").val(orgCode);
						$("#orgName").val(orgName);
						queryUserByOrgCode(orgCode);
					}
				}
			};

			$.ajax({
				type : "GET",
				url : adminUrl+"/admin/orguser/findOrgTree",
				dataType : "json",
				cache : false,
				success : function(data) {
					json = eval("(" + data.response + ")");
					$.fn.zTree.init($("#orgTree"), setting, json);
					var treeObj = $.fn.zTree.getZTreeObj("orgTree");
					var node = treeObj.getNodes()[0].children[0];
					if(node){
						treeObj.selectNode(node);
					}
					//treeObj.expandAll(true);
				}
			});

			$("#showUserDialog").dialog({
				height : 400,
				width : 450,
				title : "添加系统用户",
				autoOpen : false,
				model : true,
				buttons : {
					"确定" : function() {
						sysUserIfExist();
					},
					"取消" : function() {
						$("#showUserDialog").dialog("close");
					}
				}
			});

			$("#addUser").bind("click", function() {
				var orgCode = $("#orgCode").val();
				if (orgCode != null && orgCode != "") {
					$("#showUserDialog").dialog("open");
				} else {
					alert("请选择组织机构");
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
			$("#userRole").bind("click", function() {
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
		})
		
		
		function sysUserIfExist(){
			var sysUserName = $.trim($("#username").val());
			if (sysUserName == null || sysUserName == "") {
				$("#userNameValidate").html("请输入用户名");
				$("#username").focus();
				return false;
			}
			//验证系统用户是否存在
			$.ajax({
				type : "POST",
				url : adminUrl+"/admin/user/sysUserIfExist",
				data : {
					'username' : sysUserName
				},
				dataType : "json",
				cache : false,
				success : function(data) {
					var flag=data.flag;
					if(flag=='0'){
						//系统用户中存在该账号，请重新输入账号
						alert("系统用户中存在该账号，请重新输入账号");
						return false;
					}else{
						saveUserMessage();
					}
				}
			});
		}

		function saveUserMessage() {
			var username = $.trim($("#username").val());
			var password = $.trim($("#password").val());
			var confirmPassword = $.trim($("#confirmPassword").val());
			var userRole = $.trim($("#userRole").val());
			var roleIds = $.trim($("#roleIds").val());
			var typeCode = $.trim($("#typeCode").val());
			var orgName = $.trim($("#orgName").val());
			var orgCode = $.trim($("#orgCode").val());

			if (username == null || username == "") {
				$("#userNameValidate").html("用户名不能为空");
				$("#username").focus();
				return false;
			}

			if (password == null || password == "") {
				$("#passwordValidate").html("密码不能为空");
				$("#password").focus();
				return false;
			}

			if (confirmPassword == null || confirmPassword == "") {
				$("#confirmPasswordValidate").html("请输入确认密码");
				$("#confirmPassword").focus();
				return false;
			}

			if (password != confirmPassword) {
				$("#confirmPasswordValidate").html("两次输入的密码不一致");
				return false
			}

			if (typeCode == null || typeCode == "") {
				$("#typeCodeValidate").html("用户类型不能为空");
				$("#typeCode").focus();
				return false;
			}

			//去掉用户中用户角色的验证，如果角色为空。那么就继承机构的角色
/*			if (userRole == null || userRole == "") {
				$("#userRoleValidate").html("请选择用户角色");
				$("#userRole").focus();
				return false;
			}*/

			if (orgName == null || orgName == "") {
				$("#orgNameValidate").html("机构名称不能为空");
				$("#orgName").focus();
				return false;
			}
			
			
			$.ajax({
				type : "POST",
				url : adminUrl+"/admin/user/userAdd",
				data : {
					'username' : username,
					'password' : password,
					'userRole' : userRole,
					'roleIds' : roleIds,
					'typeCode' : typeCode,
					'orgCode' : orgCode,
					'orgName' : orgName
				},
				dataType : "json",
				cache : false,
				success : function(data) {
					json = eval("(" + data.response + ")");
					if (json != null && json[0].success == true) {
						$("#showUserDialog").dialog("close");
						var orgCode = $("#orgCode").val();
						queryUserByOrgCode(orgCode);
						$("#username").val("");
						$("#password").val("");
						$("#confirmPassword").val("");
						$("#userRole").val("");
						$("#roleIds").val("");
					}
				}
			});
		}

		function queryUserByOrgCode(orgCode) {
			$.ajax({
						type : "GET",
						data : {
							'orgCode' : orgCode
						},
						url : adminUrl+"/admin/user/userList",
						cache : false,
						success : function(data) {
							$("#mytbody").html(data);
						}
					});
		}

		$("#username").change(function() {
			var username = $.trim($("#username").val());
			if (username != null && username != "") {
				$("#userNameValidate").html("*");
			}
		});

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

		$("#typeCode").change(function() {
			var typeCode = $.trim($("#typeCode").val());
			if (typeCode != null && typeCode != "") {
				$("#typeCodeValidate").html("*");
			}
		});

		$("#userRole").change(function() {
			var typeCode = $.trim($("#userRole").val());
			if (typeCode != null && typeCode != "") {
				$("#userRoleValidate").html("*");
			}
		});

		$("#orgName").change(function() {
			var orgName = $.trim($("#orgName").val());
			if (orgName != null && orgName != "") {
				$("#orgNameValidate").html("*");
			}
		});

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

			$("#userRole").val(nodeNameTemp);
			$("#roleIds").val(nodeIdsTemp);
			$("#showRoleDialog").dialog("close");
		}

		//注销系统用户
		function deleteUser(userId) {
			if (confirm("确定注销用户吗?")) {
				$.ajax({
					type : "GET",
					url : adminUrl+"/admin/user/delete/"+userId,
					dataType : "json",
					cache : false,
					success : function(data) {
						json = eval("(" + data.response + ")");
						var orgCode = $("#orgCode").val();
						$("#mytbody").html("");
						//根据组织机构code查询用户
						queryUserByOrgCode(orgCode);
					}
				});
			}
		}

		function zTreeOnCheck(e, treeId, treeNode) {
			//alert(treeNode.id);
		}