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
					<div id="orguseradd">
						<form id="addUserForm" class="form-horizontal"
							action="${adminUrl}/admin/orguser/orgEdit" method="post">
							<div class="control-group">
								<input type="hidden" id=userId name="userId"
									value="${orgUser.id}" maxlength="20" />
							</div>
							<div class="control-group">
								<label class="control-label">机构名称:</label>
								<div class="controls">
									<input type="text" id=orgName name="orgName"
										value="${orgUser.org.orgName }" maxlength="20" /> <input
										type="hidden" id="orgCode" name="orgCode"
										value="${orgUser.org.orgCode }" /> <font color="red"><span
										id="orgNameValidate">*</span></font>
								</div>

								<div id="showDialog" style="width: 400px; height: 500px;">
									<ul>
										<li id="treeOrgUser" class="ztree"></li>
									</ul>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">用户姓名:</label>
								<div class="controls">
									<input type="text" id=username name="username"
										value="${orgUser.username}" maxlength="20" />
										<font color="red"><span id="usernameValidate">*</span></font>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">性别:</label>
								<div class="controls">
									<input type="radio" id="orgUserSex0" name="orgUserSex"
										value="0" <c:if test="${orgUser.sex=='0'}">checked</c:if>
										style="padding-left: 40px" />男 <input type="radio"
										id="orgUserSex1" name="orgUserSex" value="1"
										<c:if test="${orgUser.sex=='1'}">checked</c:if>
										style="padding-left: 60px" />女 <font color="red"><span
										id="passwordValidate" style="padding-left: 65px">*</span></font>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">用户职位:</label>
								<div class="controls">
									<input type="text" id=position name="position"
										value="${orgUser.position}" maxlength="20" />
										<font color="red"><span id="positionValidate">*</span></font>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">手机号码:</label>
								<div class="controls">
									<input type="text" id=mobileTel name="mobileTel"
										value="${orgUser.mobileTel}" maxlength="20" />
										<font color="red"><span id="mobileTelValidate">*</span></font>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">办公电话:</label>
								<div class="controls">
									<input type="text" id=officeTel name="officeTel"
										value="${orgUser.officeTel}" maxlength="20" />
										<font color="red"><span id="officeTelValidate">*</span></font>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">用户状态:</label>
								<div class="controls">
									<select class="span2" id="status" name="status">
										<option value="1"
											<c:if test="${orgUser.status=='1'}">selected</c:if>>正常</option>
										<option value="0"
											<c:if test="${orgUser.status=='0'}">selected</c:if>>注销</option>
									</select> <font color="red"><span id="passwordValidate"
										style="padding-left: 80px">*</span></font>
								</div>
							</div>
							<div class="saveInfo">
								<button id="submitBtn" class="btn btn-primary" type="button">
									<i class="icon-check icon-white"></i> 保存
								</button>
								&nbsp;&nbsp;&nbsp;
								<button id="changeBtn"  class="btn btn-primary" type="button">
									<i class="icon-check icon-white"></i> 转为登录用户
								</button>
								&nbsp;&nbsp;&nbsp;
								<button id="backBtn" class="btn btn-primary" type="button">
									<i class="icon-check icon-white"></i> 返回
								</button>
							</div>
						</form>
					</div>

						<div id="showUserDialog" style="width: 200px; height: 400px;">
							<table>
							<tr>
								<td>机构名称:</td>
								<td><input type="text" id=sysOrgName name="sysOrgName" disabled value=""
									maxlength="20" /> <input type="hidden" id="sysOrgCode" value="" />
								</td>
							</tr>
							<tr>
								<td>用户名:</td>
								<td><input type="text" id=sysTrueName name="sysTrueName" disabled value="" maxlength="20" /></td>
							</tr>
							<tr>
								<td>账号:</td>
								<td><input type="text" id=sysUserName name="sysUserName" value=""
									maxlength="20" /> <font color="red"><span
										id="sysUserNameValidate">*</span></font></td>
							</tr>
							<tr>
								<td>密码:</td>
								<td><input type="password" id="sysPassword" name="sysPassword"
									value="" maxlength="20" /> <font color="red"><span
										id="sysPasswordValidate">*</span></font></td>
							</tr>
							<tr>
								<td>确认密码:</td>
								<td><input type="password" id="confirmSysPassword"
									name="confirmSysPassword" value="" maxlength="20" /> <font
									color="red"><span id="confirmSysPasswordValidate">*</span></font></td>
							</tr>
							<tr>
								<td>用户类型:</td>
								<td><select name="sysTypeCode" id="sysTypeCode">
										<option value="1">上级用户</option>
										<option value="0">下级用户</option>
										<option value="9">超级用户</option>
								</select> <font color="red"><span id="sysTypeCodeValidate">*</span></font></td>
							</tr>
							<tr>
								<td>用户角色:</td>
								<td><input type="text" readonly="readonly" id="sysUserRole" name="sysUserRole" maxlength="200" />
									<input type="hidden" id="sysRoleIds" name="sysRoleIds" /></td>
							</tr>
						</table>
					</div>
					<div id="showRoleDialog" style="width: 200px; height: 400px;">
						<ul>
							<li id="roleTree" class="ztree"></li>
						</ul>
					</div>
				</div>
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
	<script type="text/javascript"
		src="${adminUrl}/js/authority/orguser-edit.js"></script>
	<script type="text/javascript">
		var adminUrl = "${adminUrl}";
	</script>
</body>
</html>
