<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>防护目标管理</title>
<link rel="stylesheet"
	href="${adminUrl}/lib/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${adminUrl}/css/table_form.css" />
<link href="${adminUrl}/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${adminUrl}/css/g-system.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${adminUrl}/css/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="${adminUrl}/css/resourcetype.css" type="text/css">
</head>
<body>
	<TABLE border=0 height=600px align=left>
		<TR>
			<TD width=260px align=left valign=top
				style="BORDER-RIGHT: #999999 1px dashed">
				<div>
					<span>
						<input type="text" id="DefObjTypeName" placeholder="请输入防护目标类型名称查询" style="margin-top: 10px;margin-bottom: 0;">
					</span>
					<span id="btnSearch" style="cursor: hand">
						<img src="${adminUrl}/images/search.png" style="width:32px;height: 32px;margin-top: 10px;margin-bottom: 0;" />
					</span>
				</div>
				<div class="zTreeDemoBackground left">
					<ul id="treeDefObjType" class="ztree"></ul>
				</div>
			</TD>
			<TD width=770px align=left valign=top>
				<div class="content-menu ib-a blue line-x">
					<span></span> <a href="javascript:;" class="on" data="edit"><em>编辑防护目标类型</em> </a>
					<span>|</span> <a href="javascript:;" data="add"> <em>添加子防护目标类型</em></a>
			 		<span>|</span> <a data="delete" href="javascript:;" >  <em>删除防护目标类型</em></a> 
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
								<form id="editDefObjTypeForm" class="form-horizontal"
									action="${adminUrl}/admin/object/defObj/save" method="post">
									<div class="control-group">
										<label class="control-label">防护目标类型编码:</label>
										<div class="controls">
											<span class="input-large uneditable-input">${defObjType.defObjTypeCode}</span>
											<input type="hidden" id="editid" name="defObjType.defObjTypeCode"
												value="" maxlength="20" />											
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">防护目标类型名称:</label>
										<div class="controls">
											<input type="text" id="editname" name="defObjType.defObjTypeName"
												value="" maxlength="20" />
											<span class="prompt_mt">*</span>
											<span id="editnameSpan" class="help-inline" style="display:none;">
												<div  class="onShow" >请输入防护目标类型名称</div>
											</span>													
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">上级防护目标名称:</label>
										<div class="controls">
											<span class="input-parentName uneditable-input">${parentName}</span>
											<input type="hidden" id="parentName" name="parentName"
												value="" maxlength="20" />
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">备注:</label>
										<div class="controls">
											<input type="text" id="editnotes" name="defObjType.notes"
												value="" maxlength="50" />
										</div>
									</div>
									<div class="parentid_value">
										<input type="text" id="editparentId" name="defObjType.parentCode"
											value="" maxlength="50" />
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
								<form id="addDefObjTypeForm" class="form-horizontal"
									action="${adminUrl}/admin/object/defObj/save" method="post">
									<div class="control-group">
										<label class="control-label">防护目标类型编码:</label>
										<div class="controls">
											<input type="text" id="addid" name="defObjType.defObjTypeCode"
												value="" maxlength="20" />
											<input type="hidden" id="hidParentId" name="defObjType.parentCode"
												value="" maxlength="20" />
											<span class="prompt_mt">*</span>
											<span id="addidSpan" class="help-inline" style="display:none;">
												<div  class="onShow" >请输入防护目标类型编码</div>
											</span>												
										</div>
									</div>
									
									<div class="control-group">
										<label class="control-label">防护目标类型名称:</label>
										<div class="controls">
											<input type="text" id="addname" name="defObjType.defObjTypeName"
												value="" maxlength="20" />
											<span class="prompt_mt">*</span>	
											<span id="addnameSpan" class="help-inline" style="display:none;">
												<div  class="onShow" >请输入防护目标类型名称</div>
											</span>												
										</div>									
									</div>
									<div class="control-group">
										<label class="control-label">上级防护目标名称:</label>
										<div class="controls">
											<span class="input-largeName uneditable-input">${defObjType.defObjTypeCode}</span>
											<input type="hidden" id="addparentId" name="addparentId"
												value="" maxlength="20" />
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">备注:</label>
										<div class="controls">
											<input type="text" id="addnotes" name="defObjType.notes"
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
			</TD>
		</TR>
	</TABLE>

	<script type="text/javascript" src="${adminUrl}/lib/jquery.min.js"></script>
	<script type="text/javascript" src="${adminUrl}/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${adminUrl}/lib/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${adminUrl}/js/starscream.utils.js"></script>
	<script type="text/javascript" src="${adminUrl}/js/object/defObjType.js"></script>
	<script type="text/javascript">
		var adminUrl = "${adminUrl}";
	</script>
</body>
</html>