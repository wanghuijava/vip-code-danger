<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>添加权限</title>
<link rel="stylesheet"
	href="${adminUrl}/lib/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${adminUrl}/css/reset.css" />
<link rel="stylesheet" type="text/css"
	href="${adminUrl}/css/g-system.css" />
<link rel="stylesheet" type="text/css"
	href="${adminUrl}/css/table_form.css" />
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
					<form id="addauthorityForm" class="form-horizontal" action="${adminUrl}/admin/authority/authorityAdd" method="post">
						<div class="control-group">
							<label class="control-label">权限名称:</label>
							<div class="controls">
								<input type="text" id=authorityName name="authorityName" value="" maxlength="20" />
								<font color="red"><span id="authorityNameValidate">*</span></font>
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label">权限路径:</label>
							<div class="controls">
								<input type="text" id=authorityUrl name="authorityUrl" value="" maxlength="200" />
								<font color="red"><span id="authorityUrlValidate">*</span></font>
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
	<%@include file="../inc/footer.jsp"%>
	<script type="text/javascript">
		$("#submitBtn").live("click", function(){
			var authorityName=$.trim($("#authorityName").val());
			var authorityUrl=$.trim($("#authorityUrl").val());
			
			if(authorityName=null || authorityName==""){
				$("#authorityNameValidate").html("权限名称不能为空");
				$("#authorityName").focus();
				return false;
			}
			
			if(authorityUrl=null || authorityUrl==""){
				$("#authorityUrlValidate").html("权限路径不能为空");
				$("#authorityUrl").focus();
				return false;
			}
			$("#addauthorityForm").submit();
		});
		
		$("#authorityName").change(function(){
			var authorityName=$.trim($("#authorityName").val());
			if(authorityName!=null && authorityName!=""){
				$("#authorityNameValidate").html("*");
			}
		});
		
		$("#authorityUrl").change(function(){
			var authorityUrl=$.trim($("#authorityUrl").val());
			if(authorityUrl!=null && authorityUrl!=""){
				$("#authorityUrlValidate").html("*");
			}
		});
		
		$("#backBtn").on("click",function(){
			window.location.href="${adminUrl}/admin/authority/list";
		});
	</script>
</body>
</html>
