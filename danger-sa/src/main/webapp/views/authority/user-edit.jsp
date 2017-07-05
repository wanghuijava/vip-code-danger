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
<link rel="stylesheet" href="${adminUrl}/css/zTreeStyle.css"
	type="text/css" />
<link rel="stylesheet"
	href="${adminUrl}/lib/jquery-ui-1.9.0/themes/base/jquery.ui.all.css" />
<link rel="stylesheet" href="${adminUrl}/css/resourcetype.css" />



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
					<div id="sysUserEdit">
						<form id="editSysUserForm" class="form-horizontal"
							action="${adminUrl}/admin/user/userEdit" method="post">
							<div class="control-group">
								<input type="hidden" id=userId name="userId" value="${user.id}"
									maxlength="20" />
							</div>
							<div class="control-group">
								<label class="control-label">机构名称:</label>
								<div class="controls">
									<input type="text" id=orgName name="orgName"
										value="${user.orgName }" maxlength="20" readonly="readonly" /> <input
										type="hidden" id="orgCode" name="orgCode"
										value="${user.orgCode }" />
									<div id="showOrgDialog" style="width: 400px; height: 600px;">
										<ul>
											<li id="treeOrgDemo" class="ztree"></li>
										</ul>
									</div>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">用户名:</label>
								<div class="controls">
									<input type="text" id=username name="username"
										value="${user.username}" maxlength="20" readonly/>
										<font color="red"><span id="userNameValidate">*</span></font>
								</div>
							</div>

							<div class="control-group">
								<label class="control-label">用户状态:</label>
								<div class="controls">
									<select  id="status" name="status">
										<option value="1"
											<c:if test="${user.status=='1'}">selected</c:if>>正常</option>
										<option value="0"
											<c:if test="${user.status=='0'}">selected</c:if>>注销</option>
									</select>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">用户类型:</label>
								<div class="controls">
									<select name="typeCode" id="typeCode">
										<option value="1" <c:if test="${user.typeCode=='1'}">selected</c:if>>上级用户</option>
										<option value="0" <c:if test="${user.typeCode=='0'}">selected</c:if>>下级用户</option>
										<option value="9" <c:if test="${user.typeCode=='9'}">selected</c:if>>超级用户</option>
									</select>
									<font color="red"><span id="typeCodeValidate">*</span></font>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">用户角色:</label>
								<div class="controls">
									<input type="text" readonly="readonly" id="userRole"
										name="userRole" maxlength="200" value="${user.roleNames }"/> <font color="red"><span
										id="userRoleValidate">*</span></font> <input type="hidden"
										id="roleIds" name="roleIds" value="${user.roles }"/>
								</div>
							</div>

							<div class="saveInfo">
								<button id="submitBtn" class="btn btn-primary" type="button">
									<i class="icon-check icon-white"></i> 保存
								</button>
								&nbsp;&nbsp;&nbsp;
								<button id="modifyPassWord" class="btn btn-primary" type="button">
									<i class="icon-check icon-white"></i>修改密码
								</button>
								&nbsp;&nbsp;&nbsp;
								<button id="backBtn" class="btn btn-primary" type="button">
									<i class="icon-check icon-white"></i> 返回
								</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="showRoleDialog" style="width: 200px; height: 400px;">
		<ul>
			<li id="treeRoleDemo" class="ztree"></li>
		</ul>
	</div>
	
	<div id="modifyPasswordDialog" style="width: 400px; height: 300px;">
		<ul>
			<li>
				<form action="" id="modifyUser">
					<table>
						<tr>
							<td>用户名:</td>
							<td><input type="text" value="" id="userNameTemp" maxlength="20" disabled/>
							</td>
						</tr>
						<tr>
							<td>原密码:</td>
							<td><input type="password" id="oldPassword" name="oldPassword"
								value="" maxlength="20" />
								<font color="red"><span id="oldPasswordValidate">*</span></font></td>
						</tr>
						<tr>
							<td>新密码:</td>
							<td><input type="password" id=password name="password"
								value="" maxlength="20" />
								<font color="red"><span id="passwordValidate">*</span></font></td>
						</tr>
						<tr>
							<td>确认密码:</td>
							<td><input type="password" id="confirmPassword"
								name="confirmPassword" value="" maxlength="20" />
								<font color="red"><span id="confirmPasswordValidate">*</span></font></td>
						</tr>
					</table>
				</form>
			</li>
			
		</ul>
	</div>
	<script type="text/javascript" src="${adminUrl}/lib/jquery.min.js"></script>
	<script type="text/javascript"
		src="${adminUrl}/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript"
		src="${adminUrl}/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript"
		src="${adminUrl}/lib/jquery-ui-1.9.0/ui/jquery-ui.js"></script>
	<script type="text/javascript" src="${adminUrl}/js/authority/user-edit.js"></script>
	
	<script type="text/javascript">
		var adminUrl = "${adminUrl}";
	</script>

</body>
</html>
