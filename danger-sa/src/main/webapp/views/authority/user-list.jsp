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
		type="text/css" />
	<link rel="stylesheet" 	href="${adminUrl}/lib/jquery-ui-1.9.0/themes/base/jquery.ui.all.css" />
	<style type="text/css">
	.table th,.table td {
		vertical-align: middle;
		text-align: center;
	}
	</style>

	</head>
<body>
	<form action="">
		<table border=0 align=left>
			<tr>
				<td align=left valign=top
					style="BORDER-RIGHT: #999999 1px dashed; width: 280px; height: 500px;">
					<div>
						<span> <input type="text" id="orgNameParamParam"
							style="margin-top: 10px; margin-bottom: 0;" />
						</span> <span id="btnSearch" style="cursor: hand;"> <img
							src="${adminUrl}/images/search.png"
							style="width: 32px; height: 32px; margin-top: 10px; margin-bottom: 0;" />
						</span>
					</div>
					<div class="zTreeDemoBackground left">
						<ul id="orgTree" class="ztree" style="height: 520px;overflow: auto;"></ul>
					</div>
				</td>
				<td align=left valign=top>
					<table class="table table-hover table-condensed">
							<thead>
								<tr>
									<th width="130px">用户登录名</th>
									<th width="130px">用户类型</th>
									<th width="130px">机构名称</th>
									<th width="130px">状态</th>
									<th width="200px">创建时间</th>
									<th width="300px">管理操作</th>
									<th width="200px"><a class="btn btn-small btn-success"
										id="addUser" href="javaScript:void(0)"><i
											class="icon-plus-sign icon-white"></i> 添加用户</a></th>
								</tr>
							</thead>
							<tbody id="mytbody">

							</tbody>
						</table>
				</td>
			</tr>
		</table>
		<div id="showUserDialog" style="width: 400px; height: 200px;">
			<ul>
				<li>
						<table>
							<tr>
								<td>机构名称:</td>
								<td>
									<input type="text" id=orgName name="orgName" value="" maxlength="20" readOnly="readonly" />
								 	<input type="hidden" id="orgCode" value="" />
								</td>
							</tr>
							<tr>
								<td>用户名:</td>
								<td>
									<input type="text" id=username name="username" value="" maxlength="20" /> 
										<font color="red"><span id="userNameValidate">*</span></font>
								</td>
							</tr>
							<tr>
								<td>密码:</td>
								<td><input type="password" id="password" name="password"
									value="" maxlength="20" /> <font color="red"><span
										id="passwordValidate">*</span></font></td>
							</tr>
							<tr>
								<td>确认密码:</td>
								<td><input type="password" id=confirmPassword
									name="confirmPassword" value="" maxlength="20" /> <font
									color="red"><span id="confirmPasswordValidate">*</span></font></td>
							</tr>
							<tr>
								<td>用户类型:</td>
								<td><select name="typeCode" id="typeCode">
										<option value="1">上级用户</option>
										<option value="0">下级用户</option>
										<option value="9">超级用户</option>
								</select> <font color="red"><span id="typeCodeValidate">*</span></font></td>
							</tr>
							
							<tr>
								<td>用户角色:</td>
								<td>
								<input type="text" readonly="readonly" id="userRole" name="userRole" maxlength="200" /> 
								<input type="hidden" id="roleIds" name="roleIds" /></td>
							</tr>
						</table>
				</li>
			</ul>
		</div>
		<div id="showRoleDialog" style="width: 200px; height: 400px;">
			<ul>
				<li id="roleTree" class="ztree"></li>
			</ul>
		</div>
	</form>
	<script type="text/javascript" src="${adminUrl}/lib/jquery.min.js"></script>
	<script type="text/javascript"
		src="${adminUrl}/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript"
		src="${adminUrl}/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript"
		src="${adminUrl}/lib/jquery-ui-1.9.0/ui/jquery-ui.js"></script>
	<script type="text/javascript" src="${adminUrl}/js/authority/user-add.js"></script>
	
	<script type="text/javascript">
		var adminUrl = "${adminUrl}";
	</script>

</body>
</html>