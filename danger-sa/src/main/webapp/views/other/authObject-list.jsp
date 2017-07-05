<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>承灾体数据权限设置</title>
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
	<form id="myForm">
		<input type="hidden" id="orgCode" name="orgCode" value=""/>
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
									<th width="130px">用户名称</th>
									<th width="130px">用户类型</th>
									<th width="130px">机构名称</th>
									<th width="130px">状态</th>
									<th width="200px">更新时间</th>
									<th width="300px">管理操作</th>
								</tr>
							</thead>
							<tbody id="mytbody">

							</tbody>
						</table>
				</td>
			</tr>
		</table>
	</form>
	<script type="text/javascript" src="${adminUrl}/lib/jquery.min.js"></script>
	<script type="text/javascript"
		src="${adminUrl}/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript"
		src="${adminUrl}/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript"
		src="${adminUrl}/lib/jquery-ui-1.9.0/ui/jquery-ui.js"></script>
	<script type="text/javascript" src="${adminUrl}/js/other/authObject-set.js"></script>
	
	<script type="text/javascript">
		var adminUrl = "${adminUrl}";
	</script>

</body>
</html>