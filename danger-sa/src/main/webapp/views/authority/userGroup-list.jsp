<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>用户分组管理</title>
<link rel="stylesheet"
	href="${adminUrl}/lib/bootstrap/css/bootstrap.min.css" />
<link href="${adminUrl}/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${adminUrl}/css/g-system.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${adminUrl}/css/table_form.css" />
<link href="${adminUrl}/css/zTreeStyle.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${adminUrl}/css/resourcetype.css" />
<script type="text/javascript" src="${adminUrl}/lib/jquery.min.js"></script>
<script type="text/javascript"
	src="${adminUrl}/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript"
	src="${adminUrl}/js/authority/userGroup-list.js"></script>
<script type="text/javascript">
	var adminUrl = "${adminUrl}";
</script>
</head>
<body>
	<table border=0 align=left>
		<tr>
			<td width=280px align=left valign=top
				style="BORDER-RIGHT: #999999 1px dashed">
				<div>
					<span> <input type="text" id="userGroupName"
						placeholder="请输入组名" style="margin-top: 10px; margin-bottom: 0;" />
					</span> <span id="btnSearch" style="cursor: hand;"> <img
						src="${adminUrl}/images/search.png"
						style="width: 32px; height: 32px; margin-top: 10px; margin-bottom: 0;" />
					</span>
				</div>
				<div class="zTreeDemoBackground left">
					<ul id="userGroupTree" class="ztree"
						style="overflow: auto; height: 500px"></ul>
				</div>
			</td>
			<td width=770px align=left valign=top>
				<div class="content-menu ib-a blue line-x">
					<span></span> <a href="javascript:;" class="on" data="edit"><em>编辑用户组</em>
					</a> <span>|</span> <a href="javascript:;" data="add"> <em>添加用户组</em></a>
					<span>|</span> <a href="javascript:;" data="delete"> <em>删除用户组</em></a>
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
								<form id="" class="form-horizontal" action="" method="post">
									<div class="control-group">
										<label class="control-label">组名:</label>
										<div class="controls">
											<input type="text" id="groupName" name="groupName" value=""
												maxlength="20" /><font color="red"><span
												id="groupNameValidate">*</span></font> <input type="hidden"
												id="userGroupId" /> <input type="hidden" id="parentId" />
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">组说明:</label>
										<div class="controls">
											<input type="text" id="groupInfo" name="groupInfo" value=""
												maxlength="20" /><font color="red"><span
												id="groupInfoValidate">*</span></font>
										</div>
									</div>
									<div class="saveInfo" align="center">
										<button id="editSubmitBtn" class="btn btn-primary"
											type="button">
											<i class="icon-check icon-white"></i> 保存
										</button>
									</div>
								</form>
							</div>
							<div class="col-tab">
								<div id="div_setting_2" class="contentList pad-10">
									<form id="" class="form-horizontal" action="" method="post">
										<table class="table table-hover table-condensed">
											<thead>
												<tr>
													<th width="130px">用户名称</th>
													<th width="130px">职务</th>
													<th width="130px">手机号码</th>
													<th width="130px">办公电话</th>
													<th width="150px">状态</th>
													<th width="250px">操作</th>
												</tr>
											</thead>
											<tbody id="mytbody" style="width: 1000px; height: 800px;">

											</tbody>
										</table>
									</form>
								</div>
							</div>
						</div>
					</div>
					<div class="pad-10" id="add" style="display: none">
						<div class="explain-col" style="display: none">温馨提示:</div>
						<div class="bk10"></div>
						<div class="col-tab">
							<ul class="tabBut cu-li">
								<li id="tab_setting_2" class="on">添加子节点</li>
							</ul>
							<div id="div_setting_3" class="contentList pad-10">
								<form id="" class="form-horizontal" action="" method="post">
									<div class="control-group">
										<label class="control-label">组名:</label>
										<div class="controls">
											<input type="text" id="addgroupName" name="addgroupName"
												value="" maxlength="20" /><font color="red"><span
												id="addgroupNameValidate">*</span></font> <input type="hidden"
												id="adduserGroupId" />
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">组说明:</label>
										<div class="controls">
											<input type="text" id="addgroupInfo" name="addgroupInfo"
												value="" maxlength="20" /><font color="red"><span
												id="addgroupInfoValidate">*</span></font>
										</div>
									</div>
								</form>
							</div>
							<div class="saveInfo">
								<button id="addSubmitBtn" class="btn btn-primary" type="button">
									<i class="icon-check icon-white"></i> 保存
								</button>
							</div>
						</div>
					</div>
				</div>
			</td>
		</tr>
	</table>

	<script type="text/javascript"
		src="${adminUrl}/lib/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${adminUrl}/js/starscream.utils.js"></script>
</body>
</html>