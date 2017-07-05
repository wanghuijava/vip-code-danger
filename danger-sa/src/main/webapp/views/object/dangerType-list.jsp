<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>危险源分类</title>
	<link rel="stylesheet" href="${adminUrl}/lib/bootstrap/css/bootstrap.min.css" />
	<link href="${adminUrl}/css/reset.css" rel="stylesheet" type="text/css" />
	<link href="${adminUrl}/css/g-system.css" rel="stylesheet" type="text/css" />
	<link href="${adminUrl}/css/zTreeStyle.css" rel="stylesheet" type="text/css">
</head>
<body>
	<TABLE border=0 height=600px align=left>
		<input type="hidden" id="selectId"/>
		<TR>
			<TD width=260px align=left valign=top
				style="BORDER-RIGHT: #999999 1px dashed">
				<div>
					<span> <input type="text" id="dangerTypeName"
							placeholder="请输入危险源分类名称查询"
							style="margin-top: 10px; margin-bottom: 0;">
					</span>
					<span id="btnSearch" style="cursor: hand"> <img
							src="${adminUrl}/images/search.png"
							style="width: 32px; height: 32px; margin-top: 10px; margin-bottom: 0;" />
					</span>
				</div>
				<div class="zTreeDemoBackground left">
					<ul id="treeDangerType" class="ztree"></ul>
				</div>
			</TD>
			<TD width=770px align=left valign=top>
				<div class="content-menu ib-a blue line-x">
					<span></span>
					<a href="javascript:;" class="on" data="edit"><em>编辑危险源分类</em>
					</a>
					<span>|</span>
					<a href="javascript:;" data="add"> <em>添加子危险源分类</em>
					</a>
					<span>|</span>
					<a href="javascript:;" data="delete"> <em>删除危险源分类</em>
					</a>
				</div>
				<div class="main-content">
					<%@ include file="dangerType-form.jsp"%>
				</div> 
			</TD>
		</TR>
	</TABLE>
	
	<!-- javascript -->
	<script type="text/javascript" src="${adminUrl}/lib/jquery.min.js"></script>
	<script type="text/javascript" src="${adminUrl}/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${adminUrl}/lib/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${adminUrl}/js/starscream.utils.js"></script>
	<script type="text/javascript" src="${adminUrl}/js/object/dangertype.js"></script>
	<script type="text/javascript">
		var adminUrl = "${adminUrl}";
	</script>
</body>
</html>
