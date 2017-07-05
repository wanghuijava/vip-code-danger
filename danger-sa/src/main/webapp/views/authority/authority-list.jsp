<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>权限管理</title>
<link rel="stylesheet"
	href="${adminUrl}/lib/bootstrap/css/bootstrap.min.css" />
<link href="${adminUrl}/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${adminUrl}/css/g-system.css" rel="stylesheet"
	type="text/css" />
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

.nextLevel {
	padding-left: 15px;
}
</style>

</head>
<body>
	<div class="content-menu ib-a blue line-x">
		<a href="javascript:;" class="on"> <em>权限管理</em>
		</a>
		<span>|</span>
		<a href="${adminUrl}/admin/authority/authorityAddPage"> <em>添加权限</em>
		</a>
	</div>
	<div class="main-content">
		<div class="pad-10">
			<div class="explain-col" style="display: none">
				温馨提示：当前模板菜单项需要选择6个栏目进行填充。</div>
			<div class="bk10"></div>
			<table class="table table-hover table-condensed">
				<thead>
					<tr>
						<th width="150px">权限名称</th>
						<th width="230px">权限路径</th>
						<th width="250px">管理操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="authority" items="${authorityList}" varStatus="index">
              		<tr>
						<td>${authority.name}</td>
						<td>${authority.url}</td>
						<td>
							<a class="btn btn-small btn-info" href="${adminUrl}/admin/authority/authorityEditPage/${authority.id}"><i class="icon-edit icon-white"></i> 编辑</a>
							<a class='btn btn-small btn-danger delete' onclick='deleteAuthority("${authority.id}")'  href='javascript:void(0);'>删除</a>
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	
	<%@include file="../inc/footer.jsp"%>
<script type="text/javascript">

//删除通讯录用户
function deleteAuthority(authorityId){
	if(confirm("确定删除权限吗?")){
		$.ajax({
			type : "GET",
			url : "${adminUrl}/admin/authority/delete/"+authorityId,
			dataType : "json",
			cache : false,
			success : function(data) {
				json = eval("(" + data.response + ")");
				window.location.href=location;
			}
		});
	}
}
</script>
</body>
</html>