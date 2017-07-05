<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>添加角色</title>
<link rel="stylesheet"
	href="${adminUrl}/lib/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${adminUrl}/css/reset.css" />
<link rel="stylesheet" type="text/css"
	href="${adminUrl}/css/g-system.css" />
<link rel="stylesheet" type="text/css"
	href="${adminUrl}/css/table_form.css" />
<link rel="stylesheet" href="${adminUrl}/css/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="${adminUrl}/lib/jquery-ui-1.9.0/themes/base/jquery.ui.all.css">
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

.page-title {
	margin: 15px 0 10px 25px;
}

blockquote {
	border-left: 8px solid #006dcc;
}

blockquote {
	padding: 0 0 0 15px;
	margin: 0 0 20px;
}

h3 {
	font-size: 22px;
}

label,input,button,select,textarea {
	font-size: 12px;
	font-weight: normal;
	line-height: 20px;
}

select,input[type="file"] {
	height: 26px;
	line-height: 30px;
}

.form-horizontal .control-label {
	padding: 5px 0 5px 0;
	color: #777
}

.form-horizontal .control-group {
	margin-bottom: 10px;
	border-bottom: 1px solid #eee;
}

.saveInfo {
	margin: 10px 0 0 97px;
}

input[type="text"] {
	height: 18px;
	font-size: 12px;
}

.uneditable-input {
	height: 18px;
	font-size: 12px;
}
</style>
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
					<form id="addroleForm" class="form-horizontal" action="${adminUrl}/admin/role/roleAdd" method="post">
						<div class="control-group">
							<label class="control-label">角色名称:</label>
							<div class="controls">
								<input type="text" id=rolename name="rolename" value="" maxlength="20" />
								<font color="red"><span id="rolenameValidate">*</span></font>
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label">角色类型:</label>
							<div class="controls">
								<select id="typeCode" name="typeCode">
									<option value="0">上级</option>
									<option value="1">下级</option>
									<option value="7">系统管理员</option>
									<option value="9">超级管理员</option>
								</select>
								<font color="red"><span id="typeCodeValidate">*</span></font>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">角色描述:</label>
							<div class="controls">
								<input type="text" id=comments name="comments" value="" maxlength="20" />
								<font color="red"><span id="commentsValidate">*</span></font>
							</div>
						</div>
						<div class="saveInfo">
							<button id="submitBtn" class="btn btn-primary" type="button"><i class="icon-check icon-white"></i> 保存</button>
							<button id="backBtn" class="btn btn-primary" type="button"><i class="icon-check icon-white"></i> 返回</button>
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
	
		$("#submitBtn").live("click", function(){
			var rolename=$.trim($("#rolename").val());
			var typeCode=$.trim($("#typeCode").val());
			var comments=$.trim($("#comments").val());
			
			if(rolename=null || rolename==""){
				$("#rolenameValidate").html("角色名称不能为空");
				$("#rolename").focus();
				return false;
			}
			if(typeCode=null || typeCode==""){
				$("#typeCodeValidate").html("角色类型不能为空");
				$("#typeCode").focus();
				return false;
			}
			if(comments=null || comments==""){
				$("#commentsValidate").html("角色描述不能为空");
				$("#comments").focus();
				return false;
			}
			$("#addroleForm").submit();
		});
		$("#backBtn").on("click",function(){
			window.location.href="${adminUrl}/admin/role/list";
		});
		
		$("#rolename").change(function(){
			var rolename=$.trim($("#rolename").val());
			if(rolename!=null && rolename!=""){
				$("#rolenameValidate").html("*");
			}
		});
		
		$("#typeCode").change(function(){
			var typeCode=$.trim($("#typeCode").val());
			if(typeCode!=null && typeCode!=""){
				$("#typeCodeValidate").html("*");
			}
		});
		
		$("#comments").change(function(){
			var comments=$.trim($("#comments").val());
			if(comments!=null && comments!=""){
				$("#commentsValidate").html("*");
			}
		});
	</script>
</body>
</html>
