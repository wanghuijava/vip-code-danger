<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>组织机构及通讯录管理</title>
	<script type="text/javascript">
		var adminUrl = "${adminUrl}";
	</script>
	<link rel="stylesheet" href="${adminUrl}/lib/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="${adminUrl}/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="${adminUrl}/css/g-system.css" />
	<link rel="stylesheet" type="text/css" href="${adminUrl}/css/table_form.css" />
	<link rel="stylesheet" href="${adminUrl}/css/zTreeStyle.css" type="text/css" />
	<link rel="stylesheet" href="${adminUrl}/lib/jquery-ui-1.9.0/themes/base/jquery.ui.all.css" />
	<style type="text/css">
		html,body{overflow: hidden;}
		.table th,.table td {
			vertical-align: middle;
			text-align: center;
		}
		.layout-left{overflow:auto;position: absolute;top:0;left:0;bottom:0;width:300px;}
		.layout-right{overflow:auto;position: absolute;right:0;top:0;bottom:0;left:300px;}
		.cliptxt{overflow: hidden;text-overflow:ellipsis;white-space: nowrap;}
		.cliptb{width: 100%;table-layout: fixed;}
	</style>

</head>
<body>
	<form action="">
		<div class="layout-left">
			<div>
				<input type="hidden" id="userId"/>
				<span> <input type="text" id="searchParam"
					style="width: 160px;height:15px;margin-top: 10px; margin-bottom: 0;" />
				</span> <span id="btnSearch" style="cursor: hand;"> <img
					src="${adminUrl}/images/search.png"
					style="width: 28px; height: 28px; margin-top: 10px; margin-bottom: 0;" />
				</span>
				<input type="hidden" id="backorgCode" value="${orgCode}">
			</div>
			<div class="zTreeDemoBackground left">
				<ul id="treeDemo" class="ztree" style="min-height: 520px;overflow: auto;"></ul>
			</div>
		</div>
		<div class="layout-right">
			<table class="table table-hover table-condensed cliptb">
				<colgroup>
					<col width="130">
					<col width="130">
					<col width="130">
					<col width="130">
					<col width="80">
					<col width="250">
				</colgroup>
				<thead>
					<tr>
						<th>用户姓名</th>
						<th>职务</th>
						<th>手机</th>
						<th>办公电话</th>
						<th>状态</th>
						<th>操作</th>
						<th><a class="btn btn-small btn-success"
								id="addUser" href="javaScript:void(0)"><i
									class="icon-plus-sign icon-white"></i></a></th>
					</tr>
				</thead>
				<tbody id="mytbody" >
					
				</tbody>
			</table>
		</div>
		<div  id="showDialog" style="width: 300px;height: 400px;">
			<ul>
				<li>
					<table class="table_form">
						<tr>
							<td width="80px"><label>机构名称:</label></td>
							<td>
								<input type="text" readonly="readonly" id="orgName"  class="input-text" />
								<input type="hidden" id="orgCode" name="orgCode" value=""/>
							</td>
						</tr>
						<tr>
							<td><label>用户姓名:</label></td>
							<td ><input type="text" id=username name="username" class="input-text" value="" />
							<font color="red"><span id="usernameValidate">*</span></font></td>
						</tr>
						<tr>
							<td><label>用戶性别:</label></td>
							<td><input type="radio" id=orgUserSex0 name="orgUserSex" value="0" checked="checked" style="padding-left: 30px" />男
							<input type="radio" id=orgUserSex1 name="orgUserSex"  value="1" style="padding-left: 50px" />女
							</td>
						</tr>
						<tr>
							<td><label>用户职位:</label></td>
							<td ><input type="text" id="position" name="position"  class="input-text" value="" />
							<font color="red"><span id="positionValidate">*</span></font></td>
						</tr>
						<tr>
							<td><label>手机号码:</label></td>
							<td>
								<input type="text" id="mobileTel" name="mobileTel"  class="input-text"/>
								<font color="red"><span id="mobileTelValidate">*</span></font>
							</td>
						</tr>
						<tr>
							<td><label>办公电话:</label></td>
							<td>
								<input type="text" id="officeTel"  name="officeTel"  class="input-text"/>
								<font color="red"><span id="officeTelValidate">*</span></font>
							</td>
						</tr>
					</table>
				</li>
			</ul>
		</div>
		
		<div id="userGroupTreeDialog" style="width: 200px; height: 300px;">
			<ul>
				<li><input type="text" id="userNameTxt" value="" disabled style="width: 150px;"/> </li>
				<li id="userGroupTree" class="ztree"></li>
			</ul>
		</div>
	</form>

	<script type="text/javascript" src="${adminUrl}/lib/jquery.min.js"></script>
	<script type="text/javascript" src="${adminUrl}/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${adminUrl}/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="${adminUrl}/lib/jquery-ui-1.9.0/ui/jquery-ui.js"></script>
	<script type="text/javascript" src="${adminUrl}/js/authority/orguser-list.js"></script>
	
</body>
</html>