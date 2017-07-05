<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>用户管理</title>
<link rel="stylesheet"
	href="${adminUrl}/lib/bootstrap/css/bootstrap.min.css" />
<link href="${adminUrl}/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${adminUrl}/css/g-system.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" href="${adminUrl}/css/zTreeStyle.css"
	type="text/css">
	<link rel="stylesheet"
		href="${adminUrl}/lib/jquery-ui-1.9.0/themes/base/jquery.ui.all.css">
<style type="text/css">
body {
	padding-left: 10px;
}

.btn-group>.btn,.btn-group>.dropdown-menu,.btn-group>.popover {
	font-size: 12px;
}

.table th,.table td {
	vertical-align: middle;
}

.nextLevel {
	padding-left: 15px;
}
</style>

</head>
<body>
	<div class="main-content">
		<div class="pad-10">
			<div class="explain-col" style="display: none">
				温馨提示：当前模板菜单项需要选择6个栏目进行填充。</div>
			<div class="bk10"></div>
			<table class="table table-hover table-condensed">
				<thead>
					<tr>
						<th width="130px">角色名称</th>
						<!-- <th width="130px">角色类型</th> -->
						<th width="130px">角色描述</th>
						<th width="250px">管理操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="role" items="${roleList}" varStatus="index">
						<tr>
							<td>${role.name}</td>
							<!-- <td>${role.typeCode}</td> -->
							<td>${role.comments}</td>
							<td><a class="btn btn-small btn-success"
								href="${adminUrl}/admin/role/roleAddPage"><i
									class="icon-plus-sign icon-white"></i> 添加角色</a> 
									<a class="btn btn-small btn-success addAuthority" href="javaScript:void(0);" id="${role.id}" onclick=""><i class="icon-plus-sign icon-white"></i>赋权限</a>
									 <a class="btn btn-small btn-info"
								href="${adminUrl}/admin/role/roleEditPage/${role.id}"><i
									class="icon-edit icon-white"></i> 编辑</a> 
									<a class='btn btn-small btn-danger delete' onclick='deleteRole("${role.id}")'  href='javascript:void(0);'>删除</a>
									</td>
						</tr>
					</c:forEach>
					<!--<tr>
						<td><a href="${adminUrl}/admin/role/pagelist?operator=start" style="text-decoration: underline;">首页</a></td>
						<td><a href="${adminUrl}/admin/role/pagelist?operator=up" style="text-decoration: underline;">上一页</a></td>
						<td><a href="${adminUrl}/admin/role/pagelist?operator=down" style="text-decoration: underline;">下一页</a></td>
						<td><a href="${adminUrl}/admin/role/pagelist?operator=end" style="text-decoration: underline;">尾页</a></td>
					</tr>  -->
					
				</tbody>
			</table>
			<div id="showDialog" style="width: 200px; height: 400px;">
				<ul>
					<li id="treeDemo" class="ztree"></li>
					<li><input type="hidden" id="roleId" value=""/> </li>
					<li><input type="hidden" id="authorityIds" value=""/> </li>
				</ul>
			</div>
		</div>
	</div>

	<script type="text/javascript" src="${adminUrl}/lib/jquery.min.js"></script>
	<script type="text/javascript"
		src="${adminUrl}/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript"
		src="${adminUrl}/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript"
		src="${adminUrl}/lib/jquery-ui-1.9.0/ui/jquery-ui.js"></script>
	<script type="text/javascript">

		$(document).ready(function() {
			$(".addAuthority").bind("click",function(e){
				e.preventDefault();
				var roleId = $(this).attr("id");
				$("#roleId").val(roleId);
				
				$("#showDialog").dialog("open");
				$.ajax({
					type : "GET",
					url : "${adminUrl}/admin/authority/findAuthorityTree/"+roleId,
					dataType : "json",
					cache : false,
					success : function(data) {
						json = eval("(" + data.response + ")");
						if(json!=null){
							$.fn.zTree.init($("#treeDemo"), setting, json);
							var treeObj=$.fn.zTree.getZTreeObj("treeDemo");
							var nodes = treeObj.getNodes();
							//将权限回显
							$.ajax({
								type : "GET",
								url : "${adminUrl}/admin/role/roleAuthorityByRoleId",
								data:{
									'roleId':roleId
								},
								dataType : "json",
								cache : false,
								success : function(data) {
									json = eval("(" + data.response + ")");
									//将权限回显
									if(nodes!=null && json!=null){
										for(var i=0;i<nodes.length;i++){
											for(var j=0;j<json.length;j++){
												if(nodes[i].id==json[j].id){
													var tId = nodes[i].tId;
													var node = treeObj.getNodeByTId(tId);
													treeObj.checkNode(node, true, true);
													break;
												}
											}
										}
									}
								}
							});
						}
					}
				});
			});
			
			var setting = {
					check:{
						enable:true
					},callback:{
						onCheck:zTreeOnCheck//节点选择事件
					}
			};

			$("#showDialog").dialog({
				height : 400,
				width : 300,
				title : "权限清单",
				autoOpen : false,
				model : true,
				buttons : {
					"确定" : function() {
						getCheckNodesValue();
					},
					"取消" : function() {
						$("#showDialog").dialog("close");
					}
				}
			});
		});
		


		function zTreeOnCheck(e, treeId, treeNode) {
			//alert(treeNode.id);
		}

		function getCheckNodesValue() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var nodes = zTree.getCheckedNodes();
			var nodeIds = "";
			var nodeNames = "";
			var nodeIdsTemp = "";
			var nodeNameTemp = "";
			if(nodes!=null && nodes.length>0){
				for ( var i = 0; i < nodes.length; i++) {
					var nodeIdValue = nodes[i].id;
					var nodeNameValue = nodes[i].name;
					nodeIds += nodeIdValue + ",";
					nodeNames += nodeNameValue + ",";
				}

				if (nodeIds != "") {
					nodeIdsTemp = nodeIds.substring(0, nodeIds.length - 1);
				}
				
				$("#authorityIds").val(nodeIdsTemp);
				var roleId=$("#roleId").val();
				var authorityIds=$("#authorityIds").val();
				$.ajax({
					type : "POST",
					url : "${adminUrl}/admin/role/roleAddAuthority",
					data:{
						'roleId':roleId,
						'authorityIds':authorityIds
					},
					dataType : "json",
					cache : false,
					success : function(data) {
						json = eval("(" + data.response + ")");
						if(json[0].success==true){
							alert("保存成功");
						}
					}
				});
				
				$("#showDialog").dialog("close");
			}else{
				alert("请选择权限", 2, "选择权限");
			}
			
		}
		
		
		//删除通讯录用户
		function deleteRole(roleId){
			if(confirm("确定删除角色吗?")){
				$.ajax({
					type : "GET",
					url : "${adminUrl}/admin/role/roleDelete/"+roleId,
					dataType : "json",
					cache : false,
					success : function(data) {
						json = eval("(" + data.response + ")");
						window.location.href=location;
					}
				});
			}
		}
		
		
	</script>
</body>
</html>