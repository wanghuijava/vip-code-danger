<%@ page pageEncoding= "UTF-8" %>
<%@ page contentType= "text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>添加行政区划</title>
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
		<a href="${adminUrl}/admin/basedata/district/list"> <em>管理行政区划</em></a>
		<span>|</span>
		<a href="javascript:;" class="on"> <em>添加行政区划</em></a>
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
					<form id="addDistrictForm" class="form-horizontal" action="${adminUrl}/admin/basedata/district/save" method="post">
						<div class="control-group">
							<label class="control-label">上级行政区划</label>
							<div class="controls">
								<select class="span2" name="parentDistrictCode" id="parentDistrict">
									<option value="" <c:if test="${empty districtCode}">selected</c:if>>≡ 作为一级节点 ≡</option>		
									<!-- 一级节点 -->		
									<c:forEach var="select" items="${districtList}">
										<option value="${select.distCode}" <c:if test="${districtCode == select.distCode}">selected</c:if>>${select.distName}</option>
										
										<!-- 二级节点 -->
										<c:if test="${!empty select.children}">
											<c:forEach var="selectChild1" items="${select.children}" varStatus="childIndex1">
												<c:choose>
												<c:when test="${childIndex1.index == fn:length(select.children) - 1}">
													<option value="${selectChild1.distCode}" <c:if test="${districtCode == selectChild1.distCode}">selected</c:if>>&nbsp;└ ${selectChild1.distName}</option>
												</c:when>
												<c:otherwise>
													<option value="${selectChild1.distCode}" <c:if test="${districtCode == selectChild1.distCode}">selected</c:if>>&nbsp;├ ${selectChild1.distName}</option>
												</c:otherwise>
												</c:choose>
												<!-- 三级节点 -->
												<c:if test="${!empty selectChild1.children}">
													<c:forEach var="selectChild2" items="${selectChild1.children}" varStatus="childIndex2">
														<c:choose>
														<c:when test="${childIndex2.index == fn:length(selectChild1.children) - 1}">
															<option value="${selectChild2.distCode}" <c:if test="${districtCode == selectChild2.distCode}">selected</c:if>>&nbsp;&nbsp;&nbsp;&nbsp;└ ${selectChild2.distName}</option>
														</c:when>
														<c:otherwise>
															<option value="${selectChild2.distCode}" <c:if test="${districtCode == selectChild2.distCode}">selected</c:if>>&nbsp;&nbsp;&nbsp;&nbsp;├ ${selectChild2.distName}</option>
														</c:otherwise>
														</c:choose>
													</c:forEach>
												</c:if>
											</c:forEach>
										</c:if>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">行政区划编码:</label>
							<div class="controls">
								<input type="text" id="distCode" name="distCode" value="" maxlength="20" />
								<font color="red"><span id="distCodeValidate">*</span></font>
								<input type="hidden" id="distCodeCheck" value="1" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">行政区划全称:</label>
							<div class="controls">
								<input type="text" id="distName" name="distName" value="" maxlength="20" />
								<font color="red"><span id="distNameValidate">*</span></font>
								<input type="hidden" id="distNameCheck" value="1" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">行政区划简称:</label>
							<div class="controls">
								<input type="text" id="distShortName" name="distShortName" value="" maxlength="20" />
								<font color="red"><span id="distShortNameValidate">*</span></font>
								<!-- span class="help-inline"><div id="distShortNameTip" class="onShow">请输入行政区划简称（没有可以不用输入）</div></span>
								<input type="hidden" id="distShortNameCheck" value="1" /-->
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">经度坐标:</label>
							<div class="controls">
								<input type="text" id="distLongitude" name="distLongitude" value="" maxlength="20" />
								<font color="red"><span id="distLongitudeValidate">*</span></font>
								<!-- span class="help-inline"><div id="distLongitudeTip" class="onShow">请输入行政区划经度坐标</div></span>
								<input type="hidden" id="distLongitudeCheck" value="1" / -->
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">纬度坐标:</label>
							<div class="controls">
								<input type="text" id="distLatitude" name="distLatitude" value="" maxlength="20" />
								<font color="red"><span id="distLatitudeValidate">*</span></font>
								<!-- span class="help-inline"><div id="distLatitudeTip" class="onShow">请输入行政区划纬度坐标</div></span>
								<input type="hidden" id="distLatitudeCheck" value="1" / -->
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">备注:</label>
							<div class="controls">
								<input type="text" id="distNotes" name="distNotes" value="" maxlength="50" />
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
	
	var distCode=$.trim($("#distCode").val());
	var distName=$.trim($("#distName").val());
	var distShortName=$.trim($("#distShortName").val());
	var distLongitude=$.trim($("#distLongitude").val());
	var distLatitude=$.trim($("#distLatitude").val());
	var distNotes=$.trim($("#distNotes").val());
	
	if(distCode==null || distCode==""){
		$("#distCodeValidate").html("请输入行政区划编码");
		$("#distCode").focus();
		return false;
	}else if(isNaN(distCode)){
		$("#distCodeValidate").html("行政区划编码为数字,请重新输入");
		$("#distCode").focus();
		return false;
	}
	
	if(distName==null || distName==""){
		$("#distNameValidate").html("请输入行政区划全称");
		$("#distName").focus();
		return false;
	}
	
	if(distShortName==null || distShortName==""){
		$("#distShortNameValidate").html("请输入行政区划简称");
		$("#distShortName").focus();
		return false;
	}
	
	if(distLongitude==null || distLongitude==""){
		$("#distLongitudeValidate").html("请输入行政区划精度");
		$("#distLongitude").focus();
		return false;
	}
	
	if(distLatitude==null || distLatitude==""){
		$("#distLatitudeValidate").html("请输入行政区划纬度");
		$("#distLatitude").focus();
		return false;
	}
	
	$("#addDistrictForm").submit();
}


$("#distCode").change(function(){
	var distCode=$.trim($("#distCode").val());
	if(distCode!=null && distCode!=""){
		$("#distCodeValidate").html("*");
	}
});

$("#distName").change(function(){
	var distName=$.trim($("#distName").val());
	if(distName!=null && distName!=""){
		$("#distNameValidate").html("*");
	}
});

$("#distShortName").change(function(){
	var distShortName=$.trim($("#distShortName").val());
	if(distShortName!=null && distShortName!=""){
		$("#distShortNameValidate").html("*");
	}
});

$("#distLongitude").change(function(){
	var distLongitude=$.trim($("#distLongitude").val());
	if(distLongitude!=null && distLongitude!=""){
		$("#distLongitudeValidate").html("*");
	}
});

$("#distLatitude").change(function(){
	var distLatitude=$.trim($("#distLatitude").val());
	if(distLatitude!=null && distLatitude!=""){
		$("#distLatitudeValidate").html("*");
	}
});

$("#distNotes").change(function(){
	var distNotes=$.trim($("#distNotes").val());
	if(distNotes!=null && distNotes!=""){
		$("#distNotesValidate").html("*");
	}
});

$("#submitBtn").live("click", function(){
	$("#submitFlag").val(1);
	CheckAndSubmit();
});
$("#backBtn").on("click",function(){
	window.location.href=adminUrl+"/admin/basedata/district/list";
});

$("#catName").live("blur", function(){
	$("#catNameCheck").val(0);
	var catName = $(this).val();
	var cid = 0;
	$.ajax({
		url:adminUrl+"/admin/basedata/district/checkDistrictCode",
		type:"POST",
		data:{catName:catName,cid:cid},
		success:function(rsp){
			if(rsp.status > 0){
				$("#catNameTip").attr("class","onError");
				$("#catNameTip").html("该栏目名称已存在！");
			}
			else if(rsp.status == -1){
				$("#catNameTip").attr("class","onError");
				$("#catNameTip").html("栏目名称不能为空！");
			}
			else{
				$("#catNameTip").attr("class","onCorrect");
				$("#catNameTip").html("该栏目名称可用。");
				$("#catNameCheck").val(1);
				CheckAndSubmit();
			}
		}
	})
});
$("#catPath").live("blur", function(){
	$("#catPathCheck").val(0);
	var catPathName = $(this).val();
	var cid = 0;
	$.ajax({
		url:adminUrl+"/admin/basedata/district/checkDistrictName",
		type:"POST",
		data:{catPathName:catPathName,cid:cid},
		success:function(rsp){
			if(rsp.status > 0){
				$("#catPathTip").attr("class","onError");
				$("#catPathTip").html("该英文目录已存在！");
			}
			else if(rsp.status == -1){
				$("#catPathTip").attr("class","onError");
				$("#catPathTip").html("英文目录不能为空！");
			}
			else{
				$("#catPathTip").attr("class","onCorrect");
				$("#catPathTip").html("该英文目录可用。");
				$("#catPathCheck").val(1);
				CheckAndSubmit();
			}
		}
	});
});
</script>
</body>
	</html>