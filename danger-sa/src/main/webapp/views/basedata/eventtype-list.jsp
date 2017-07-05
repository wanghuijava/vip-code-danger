<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>事件类型管理</title>
		<link rel="stylesheet" href="${adminUrl}/lib/bootstrap/css/bootstrap.min.css" />
		<link href="${adminUrl}/css/reset.css" rel="stylesheet" type="text/css" />
		<link href="${adminUrl}/css/g-system.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="${adminUrl}/css/table_form.css" />
		<link href="${adminUrl}/css/zTreeStyle.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="${adminUrl}/css/resourcetype.css" />
		<script type="text/javascript" src="${adminUrl}/lib/jquery.min.js"></script>
		<script type="text/javascript" src="${adminUrl}/js/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript" src="${adminUrl}/js/basedata/eventtype.js" ></script>
		<script type="text/javascript">
			var adminUrl= "${adminUrl}";
		</script>
	</head>
<body>
	<table border=0 align=left>
		<tr>
			<td width=280px align=left valign=top
				style="BORDER-RIGHT: #999999 1px dashed">
				<div>
					<span>
						<input type="text" id="eventTypeName" placeholder="请输入事件类型名称" style="margin-top: 10px;margin-bottom: 0;" />
					</span>
					<span id="btnSearch" style="cursor:hand;">
						<img src="${adminUrl}/images/search.png" style="width:32px;height: 32px;margin-top: 10px;margin-bottom: 0;" />
					</span>
				</div>
				<div class="zTreeDemoBackground left"  >
					<ul id="treeEventtype" class="ztree" style="overflow: auto;height: 500px"></ul>
				</div>
			</td>
			<td width=770px align=left valign=top>
				<div class="content-menu ib-a blue line-x">
					<span></span> <a href="javascript:;" class="on" data="edit"><em>编辑事件类型</em> </a>
					<span>|</span> <a href="javascript:;" data="add"> <em>添加子事件类型</em></a>
			 		<span>|</span> <a href="javascript:;" data="delete"  >  <em>删除事件类型</em></a> 
				</div>
				<div class="main-content">
					<div class="pad-10" id="edit">
						<div class="explain-col" style="display: none">温馨提示:</div>
						<div class="bk10"></div>
						<div class="col-tab">
							<ul class="tabBut cu-li">
								<li id="tab_setting_1" class="on">基本编辑选项</li>
							</ul>
							<div id="div_setting_1" class="contentList pad-10">
								<form id="editEventtypeForm" class="form-horizontal"
									action="${adminUrl}/admin/basedata/eventType/update" method="post">
									<div class="control-group">
										<label class="control-label">事件类型编码:</label>
										<div class="controls">
											<span class="input-large uneditable-input"></span>
											<input type="hidden" id="editid" name="editid"
												value="" maxlength="20" />
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">事件类型名称:</label>
										<div class="controls">
											<input type="text" id="editname" name="editname"
												value="" maxlength="20" />
											<span id="editnameSpan" class="help-inline" style="display:none;">
												<div  class="onShow" >请输入事件类型名称</div>
											</span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">上级事件名称:</label>
										<div class="controls">
											<span class="input-edit uneditable-input"></span>
											<input type="hidden" id="editparentCode" name="editparentCode"
												value="" maxlength="20" />
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">默认启动预案:</label>
										<div class="controls">
											<select class="span2"  name="eppManageSelect">
											</select>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">是否常用:</label>
										<div class="controls">
											<input type="radio" id="iscommon1" name="iscommon" style="padding-left:30px" value="1">是</input>
											<input type="radio" id="iscommon2" name="iscommon" style="padding-left:50px" checked="checked" value="0">不是</input>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">备注:</label>
										<div class="controls">
											<input type="text" id="editnotes" name="editnotes"
												value="" maxlength="50" />
										</div>
									</div>
								</form>
							</div>
							<div class="saveInfo">
								<button id="editSubmitBtn" class="btn btn-primary" type="button">
									<i class="icon-check icon-white"></i> 保存
								</button>
							</div>
							<input type="hidden" id="submitFlag" value="0" />
						</div>
						
					</div>
					<div class="pad-10" id="add" style="display: none">
							<div class="explain-col" style="display: none">温馨提示:</div>
						<div class="bk10"></div>
						<div class="col-tab">
							<ul class="tabBut cu-li">
								<li id="tab_setting_1" class="on">添加子节点</li>
							</ul>
							<div id="div_setting_1" class="contentList pad-10">
								<form id="addEventtypeForm" class="form-horizontal"
									action="${adminUrl}/admin/basedata/eventType/save" method="post">
									<div class="control-group">
										<label class="control-label">事件类型编码:</label>
										<div class="controls">
											<input type="text" id="addid" name="addid"
												value="" maxlength="20" />
											<span id="addidSpan" class="help-inline" style="display:none;">
												<div  class="onShow" >请输入事件类型编码</div>
											</span>
										</div>
									</div>
									
									<div class="control-group">
										<label class="control-label">事件类型名称:</label>
										<div class="controls">
											<input type="text" id="addname" name="addname"
												value="" maxlength="20" />
											<span id="addnameSpan" class="help-inline" style="display:none;">
												<div  class="onShow" >请输入事件类型名称</div>
											</span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">上级事件名称:</label>
										<div class="controls">
											<span  class="input-add uneditable-input"></span>
											<input type="hidden" id="hidParentCode" name="hidParentCode"
												value="" maxlength="20" />
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">默认启动预案:</label>
										<div class="controls">
											<input type="hidden" name="defaultEppId" value="" />
											<select class="span2"  name="eppManageSelect">
												<option value="0">下级</option>
											</select>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">是否常用:</label>
										<div class="controls">
											<input type="radio" id="iscommon1" name="iscommon" style="padding-left:30px" value="1">是</input>
											<input type="radio" id="iscommon2" name="iscommon" style="padding-left:50px" value="0" checked="checked">不是</input>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">备注:</label>
										<div class="controls">
											<input type="text" id="addnotes" name="addnotes"
												value="" maxlength="50" />
										</div>
									</div>
								</form>
							</div>
							<div class="saveInfo">
								<button id="addSubmitBtn" class="btn btn-primary" type="button">
									<i class="icon-check icon-white"></i> 保存
								</button>
							</div>
							<input type="hidden" id="submitFlag" value="0" />
						</div>
					</div>
				</div>
			</td>
		</tr>
	</table>

	<script type="text/javascript" src="${adminUrl}/lib/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${adminUrl}/js/starscream.utils.js"></script>
</body>
</html>