<%@ page pageEncoding= "UTF-8" %>
<%@ page contentType= "text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>添加系统属性</title>
	<link rel="stylesheet" href="${adminUrl}/lib/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="${adminUrl}/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="${adminUrl}/css/g-system.css"/>
	<link rel="stylesheet" type="text/css" href="${adminUrl}/css/table_form.css"/>
	<style type="text/css">
		body{ padding-left: 10px; }
		
		.btn-group>.btn, .btn-group>.dropdown-menu, .btn-group>.popover { font-size: 12px; }
		.table th, .table td { vertical-align: middle; }
		.page-title{ margin: 15px 0 10px 25px; }
		
		blockquote { border-left: 8px solid #006dcc; }
		blockquote { padding: 0 0 0 15px; margin: 0 0 20px; }
		h3 { font-size: 22px; }
		label, input, button, select, textarea { font-size: 12px; font-weight: normal; line-height: 20px; }
		select, input[type="file"] { height: 26px; line-height: 30px; }
		.form-horizontal .control-label { padding: 5px 0 5px 0; color: #777}
		.form-horizontal .control-group { margin-bottom: 10px; border-bottom: 1px solid #eee; }
		.saveInfo{ margin: 10px 0 0 97px; }
		
		input[type="text"] { height: 18px; font-size: 12px; }
		.uneditable-input { height: 18px; font-size: 12px; }
	</style>
</head>
<body>
	<div class="content-menu ib-a blue line-x">
		<a href="${adminUrl}/basedata/basSysCfgProp/list"> <em>属性配置管理</em></a>
		<span>|</span>
		<a href="javascript:;" class="on"> <em>添加系统属性</em></a>
	</div>
	<div class="main-content">
		<div class="pad-10">
			<div class="explain-col" style="display:none">温馨提示:</div>
			<div class="bk10"></div>
			<div class="col-tab">
				<ul class="tabBut cu-li">
					<li id="tab_setting_1" class="on">基本选项</li>
				</ul>
				<div id="div_setting_1" class="contentList pad-10">
					<form id="addForm" class="form-horizontal" action="${adminUrl}/basedata/basSysCfgProp/save" method="post">
						<div class="control-group">
							<label class="control-label">属性名称:</label>
							<div class="controls">
								<input type="text" id="propName" name="propName" value="" maxlength="100" />
								<font color="red"><span id="propNameValidate">*</span></font>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">属性值:</label>
							<div class="controls">
								<input type="text" id="propValue" name="propValue" value="" maxlength="200" />
								<font color="red"><span id="propValueValidate">*</span></font>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">属性描述:</label>
							<div class="controls">
								<textarea id="propDesc" name="propDesc" cols="5" rows="3"></textarea>
								<font color="red"><span id="propDescValidate">*</span></font>
							</div>
						</div>
					</form>
				</div>
				<div class="saveInfo">
					<button id="submitBtn" class="btn btn-primary" type="button"><i class="icon-check icon-white"></i> 保存</button>
					<button id="backBtn" class="btn btn-primary" type="button"><i class="icon-check icon-white"></i> 返回</button>
				</div>
				<input type="hidden" id="submitFlag" value="0" />
			</div>
		</div>
<%@include file="../inc/footer.jsp"%>
<script type="text/javascript">
var adminUrl = "${adminUrl}";
var CheckAndSubmit = function(){
	var propName=$.trim($("#propName").val());
	var propValue=$.trim($("#propValue").val());
	var propDesc=$.trim($("#propDesc").val());
	if(propName==null || propName==""){
		$("#propNameValidate").html("请输入属性名称");
		$("#propName").focus();
		return false;
	}
	
	if(propValue==null || propValue==""){
		$("#propValueValidate").html("请输入属性值");
		$("#propValue").focus();
		return false;
	}
	
	if(propDesc==null || propDesc==""){
		$("#propDescValidate").html("请输入属性描述");
		$("#propDesc").focus();
		return false;
	}
	
	$.ajax({
		type : "POST",
		url : adminUrl+"/basedata/basSysCfgProp/findByPropName",
		data : {
			'propName' : propName
		},
		dataType : "json",
		cache : false,
		success : function(data) {
			var flag=data.response.flag;
			if(flag!=null && flag=='1'){
				$("#addForm").submit();
			}else{
				alert("属性名称已经存在,请重新输入");
			}
		}
	});
}


$("#propName").change(function(){
	var propName=$.trim($("#propName").val());
	if(propName!=null && propName!=""){
		$("#propNameValidate").html("*");
	}
});

$("#propValue").change(function(){
	var propValue=$.trim($("#propValue").val());
	if(propValue!=null && propValue!=""){
		$("#propValueValidate").html("*");
	}
});

$("#propDesc").change(function(){
	var propDesc=$.trim($("#propDesc").val());
	if(propDesc!=null && propDesc!=""){
		$("#propDescValidate").html("*");
	}
});


$("#submitBtn").live("click", function(){
	CheckAndSubmit();
});


$("#backBtn").on("click",function(){
	window.location.href=adminUrl+"/basedata/basSysCfgProp/list";
});



</script>
</body>
	</html>