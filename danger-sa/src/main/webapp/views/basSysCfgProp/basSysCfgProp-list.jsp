<%@ page pageEncoding= "UTF-8" %>
<%@ page contentType= "text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>行政区划管理</title>
	<link rel="stylesheet" href="${adminUrl}/lib/bootstrap/css/bootstrap.min.css" />
	<link href="${adminUrl}/css/reset.css" rel="stylesheet" type="text/css" />
	<link href="${adminUrl}/css/g-system.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
body{padding-left: 10px;}

.btn-group>.btn, .btn-group>.dropdown-menu, .btn-group>.popover { font-size: 12px; }
.table th, .table td { vertical-align: middle; }
.nextLevel{padding-left:15px;}
</style>
</head>
<body>
	<div class="content-menu ib-a blue line-x">
		<a href="${adminUrl}/basedata/basSysCfgProp/list" class="on"> <em>属性配置管理</em>
		</a>
		<span>|</span>
		<a href="${adminUrl}/basedata/basSysCfgProp/add"> <em>添加属性配置</em>
		</a>
	</div>
	<div class="main-content">
		<div class="pad-10">
			<div class="bk10"></div>
			<table class="table table-hover table-condensed">
              <thead>
                <tr>
                  <th width="200px">属性名称</th>
                  <th width="200px">属性值</th>
                  <th width="200px">属性描述</th>
                  <th>管理操作</th>
                </tr>
              </thead>
              <tbody>
	              	<c:forEach var="obj" items="${list}" varStatus="index">
							<tr>
								<td>${obj.propName}</td>
								<td>${obj.propValue}</td>
								<td>${obj.propDesc}</td>
								<td>
								<a class="btn btn-small btn-info edit" href="${adminUrl}/basedata/basSysCfgProp/edit/${obj.propId}">
									<i class="icon-edit icon-white"></i> 修改</a>
								<a class="btn btn-small btn-danger delete"
									href="javascript:void(0);"
									data="${adminUrl}/basedata/basSysCfgProp/delete/${obj.propId}"><i
										class="icon-trash icon-white"></i> 删除</a></td>
							</tr>
					</c:forEach>
              </tbody>
            </table>
		</div>
	</div>
<%@include file="../inc/footer.jsp"%>
<script type="text/javascript">
var delFunction = function(url){
	window.location.href = url;
}

$(".delete").bind("click",function(e){
	e.preventDefault();
	var href = $(this).attr("data");
	alert("确认删除该属性配置？", 2, "删除栏目", function(){window.location.href = href;});
});

$(".disabled").bind("click",function(e){e.preventDefault();});
</script>
</body>
	</html>