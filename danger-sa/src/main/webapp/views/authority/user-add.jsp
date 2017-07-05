<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>添加行政区划</title>
<link rel="stylesheet"
	href="${adminUrl}/lib/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${adminUrl}/css/reset.css" />
<link rel="stylesheet" type="text/css"
	href="${adminUrl}/css/g-system.css" />
<link rel="stylesheet" type="text/css"
	href="${adminUrl}/css/table_form.css" />
<link rel="stylesheet" href="${adminUrl}/css/zTreeStyle.css" type="text/css" />
<link rel="stylesheet" href="${adminUrl}/lib/jquery-ui-1.9.0/themes/base/jquery.ui.all.css" />

</head>
<body>
	<div class="main-content">
		<div class="pad-10">
			<div class="explain-col" style="display: none">温馨提示:</div>
			<div class="bk10"></div>
			<div class="col-tab">
				<ul class="tabBut cu-li">
					<li id="tab_setting_1" class="on">基本选项</li>
				</ul>
				<div id="div_setting_1" class="contentList pad-10">
					<form id="addUserForm" class="form-horizontal" action="${adminUrl}/authority/user/userAdd" method="post">
						<div class="control-group">
							<label class="control-label">用户登录名:</label>
							<div class="controls">
								<input type="text" id=username name="username" value="" maxlength="20" />
								<font color="red"><span id="userNameValidate">*</span></font>
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label">密码:</label>
							<div class="controls">
								<input type="password" id="password" name="password" value="" maxlength="20" />
								<font color="red"><span id="passwordValidate">*</span></font>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">确认密码:</label>
							<div class="controls">
								<input type="password" id=confirmPassword name="confirmPassword" value="" maxlength="20" />
								<font color="red"><span id="confirmPasswordValidate">*</span></font>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">用户类型:</label>
							<div class="controls">
								<select name="typeCode" id="typeCode">
									<option value="1">上级用户</option>
									<option value="0">下级用户</option>
									<option value="9">超级用户</option>
								</select>
								<font color="red"><span id="typeCodeValidate">*</span></font>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">用户角色:</label>
							<div class="controls">
								<input type="text" readonly="readonly"  id="userRole" name="userRole" value="${user.roleNames }" maxlength="200"/>
								<font color="red"><span id="userRoleValidate">*</span></font>
								<input type="hidden" id="roleIds" name="roleIds" value="${user.roles }"/>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">机构名称:</label>
							<div class="controls">
								<input type="text" id=orgName name="orgName" value="" maxlength="20" />
								<font color="red"><span id="orgNameValidate">*</span></font>
							</div>
						</div>
						<div class="saveInfo"><button id="submitBtn" class="btn btn-primary" type="button"><i class="icon-check icon-white"></i> 保存</button></div>
						
						<div  id="showDialog" style="width: 200px;height: 400px;">
							<ul>
								<li><input type="text" id="searchParam" name="searchParam" style="width: 210px;height: 15px;"/> </li>
								<li id="treeDemo" class="ztree" ></li>
							</ul>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript" src="${adminUrl}/lib/jquery.min.js"></script>
	<script type="text/javascript" src="${adminUrl}/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${adminUrl}/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="${adminUrl}/lib/jquery-ui-1.9.0/ui/jquery-ui.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			
			$("#searchParam").keydown(function(e){
				if(e.keyCode==13){
					searchNode();
				}
			});
			
			$("#searchParam").change(function(e){
				searchNode();
			});
			
			var setting = {
					check:{
						enable:true
					},callback:{
						onCheck:zTreeOnCheck//节点选择事件
					}
			};
			

			$("#userRole").bind("click",function(){
				$.ajax({
					type : "GET",
					url : "${adminUrl}/authority/role/findRoleTree",
					dataType : "json",
					cache : false,
					success : function(data) {
						json = eval("(" + data.response + ")");
						$.fn.zTree.init($("#treeDemo"), setting, json);
						var treeObj=$.fn.zTree.getZTreeObj("treeDemo");
						treeObj.expandAll(true);
					}
				});
			});
			
			$("#showDialog").dialog({
				height:350,
				width:270,
				title:"用户角色",
				autoOpen:false,
				model:true,
				buttons:{
					"确定":function(){
						getCheckNodesValue();
					},"取消":function(){
						$("#showDialog").dialog("close");
					}
				}
			});

			$("#userRole").bind("click",function(){
				$("#showDialog").dialog("open");
			});
		});
		
		function searchNode(){
			var searchParam=$.trim($("#searchParam").val());
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var node;
			if(searchParam==''||searchParam==null){
				node = zTree.getNodes()[0].children[0];
			}else {
				var nodes = zTree.getNodesByParamFuzzy("name",searchParam, null);
				node = nodes[0];
			}
			zTree.selectNode(node);
		}
		
		
		function zTreeOnCheck(e,treeId,treeNode){
			//alert(treeNode.id);
		}
		
		function getCheckNodesValue(){
			var zTree=$.fn.zTree.getZTreeObj("treeDemo");
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
			$("#showDialog").dialog("close");
		}
		
		

		$("#submitBtn").bind("click",function(e){
			var username=$.trim($("#username").val());
			var password=$.trim($("#password").val());
			var confirmPassword=$.trim($("#confirmPassword").val());
			var userRole=$.trim($("#userRole").val());
			var typeCode=$.trim($("#typeCode").val());
			var orgName=$.trim($("#orgName").val());
			
			if(username==null || username==""){
				$("#userNameValidate").html("用户名不能为空");
				$("#username").focus();
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

			if(orgName==null || orgName==""){
				$("#orgNameValidate").html("机构名称不能为空");
				$("#orgName").focus();
				return false;
			}
			$("#addUserForm").submit();
		});
		
		$("#username").change(function(){
			var username=$.trim($("#username").val());
			if(username!=null && username!=""){
				$("#userNameValidate").html("*");
			}
		});
		
		$("#password").change(function(){
			var password=$.trim($("#password").val());
			if(password!=null && password!=""){
				$("#passwordValidate").html("*");
			}
		});
		
		$("#confirmPassword").change(function(){
			var confirmPassword=$.trim($("#confirmPassword").val());
			if(confirmPassword!=null && confirmPassword!=""){
				$("#confirmPasswordValidate").html("*");
			}
		});
		
		
		$("#typeCode").change(function(){
			var typeCode=$.trim($("#typeCode").val());
			if(typeCode!=null && typeCode!=""){
				$("#typeCodeValidate").html("*");
			}
		});
		
		$("#userRole").change(function(){
			var typeCode=$.trim($("#userRole").val());
			if(typeCode!=null && typeCode!=""){
				$("#userRoleValidate").html("*");
			}
		});
		
		$("#orgName").change(function(){
			var orgName=$.trim($("#orgName").val());
			if(orgName!=null && orgName!=""){
				$("#orgNameValidate").html("*");
			}
		});
	</script>
</body>
</html>
