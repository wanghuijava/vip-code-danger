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
		<a href="javascript:;" class="on"> <em>管理行政区划</em>
		</a>
		<span>|</span>
		<a href="${adminUrl}/admin/basedata/district/add/300000"> <em>添加行政区划</em>
		</a>
	</div>
	<div class="main-content">
		<div class="pad-10">
			<div class="explain-col" style="display:none">
				温馨提示：当前模板菜单项需要选择6个栏目进行填充。
			</div>
			<div class="bk10"></div>
			<table class="table table-hover table-condensed">
              <thead>
                <tr>
                  <th width="200px">行政区划全称</th>
                  <th width="100px">行政区划编码</th>
                  <th width="100px">行政区划简称</th>
                  <th width="90px">经度坐标</th>
                  <th width="90px">纬度坐标</th>
                  <th width="150px">排序</th>
                  <th>管理操作</th>
                </tr>
              </thead>
              <tbody>
              	<!-- 一级节点 -->
              	<c:forEach var="district" items="${districtList}" varStatus="index">
              		<tr>
						<td>${district.distName}</td>
						<td>${district.distCode}</td>
						<td>${district.distShortName}</td>
						<td>${district.longitude}</td>
						<td>${district.latitude}</td>
						<td>
							<div class="btn-group">
								<a class="btn btn-small <c:if test="${index.index == 0}">disabled</c:if>" href="/content/basedata/district/moveUp/${district.distCode}"> <i class="icon-arrow-up"></i> 上移</a>
								<a class="btn btn-small <c:if test="${index.index == fn:length(districtList) - 1}">disabled</c:if>" href="/content/basedata/district/moveDown/${district.distCode}"> <i class="icon-arrow-down"></i> 下移</a>
							</div>
						</td>
						<td>
							<a class="btn btn-small btn-success" href="${adminUrl}/admin/basedata/district/add/${district.distCode}"><i class="icon-plus-sign icon-white"></i> 添加子节点</a>
							<a class="btn btn-small btn-info" href="${adminUrl}/admin/basedata/district/edit/${district.distCode}"><i class="icon-edit icon-white"></i> 编辑</a>
							<a class="btn btn-small btn-danger delete" href="javascript:void(0);" data="${adminUrl}/admin/basedata/district/delete/${district.distCode}"><i class="icon-trash icon-white"></i> 删除</a>
						</td>
					</tr>
					
					<!-- 二级节点 -->
					<c:if test="${!empty district.children}">
						<c:forEach var="dist1" items="${district.children}" varStatus="childrenIndex1">
		              		<tr>
								<td>
									<c:choose>
									<c:when test="${childrenIndex1.index == fn:length(district.children) - 1}"><span class="nextLevel">└─ ${dist1.distName}</span></c:when>
									<c:otherwise><span class="nextLevel">├─ ${dist1.distName}</span></c:otherwise>
									</c:choose>
								</td>
								<td>${dist1.distCode}</td>
								<td>${dist1.distShortName}</td>
								<td>${dist1.longitude}</td>
								<td>${dist1.latitude}</td>
								<td>
									<div class="btn-group">
										<a class="btn btn-small <c:if test="${childrenIndex1.index == 0}">disabled</c:if>" href="${adminUrl}/admin/basedata/district/moveUp/${dist1.distCode}"> <i class="icon-arrow-up"></i> 上移</a>
										<a class="btn btn-small <c:if test="${childrenIndex1.index == fn:length(district.children) - 1}">disabled</c:if>" href="${adminUrl}/admin/basedata/district/moveDown/${dist1.distCode}"> <i class="icon-arrow-down"></i> 下移</a>
									</div>
								</td>
								<td>
									<a class="btn btn-small btn-success" href="${adminUrl}/admin/basedata/district/add/${dist1.distCode}"><i class="icon-plus-sign icon-white"></i> 添加子节点</a>
									<a class="btn btn-small btn-info" href="${adminUrl}/admin/basedata/district/edit/${dist1.distCode}"><i class="icon-edit icon-white"></i> 编辑</a>
									<a class="btn btn-small btn-danger delete" href="javascript:void(0);" data="${adminUrl}/admin/basedata/district/delete/${dist1.distCode}"><i class="icon-trash icon-white"></i> 删除</a>
								</td>
							</tr>
							
							<!-- 三级节点 -->
							<c:if test="${!empty dist1.children}">
								<c:forEach var="dist2" items="${dist1.children}" varStatus="childrenIndex2">
				              		<tr>
										<td>
											<c:choose>
											<c:when test="${childrenIndex2.index == fn:length(dist1.children) - 1}"><span class="nextLevel">│</span><span class="nextLevel">└─ ${dist2.distName}</span></c:when>
											<c:otherwise><span class="nextLevel">│</span><span class="nextLevel">├─ ${dist2.distName}</span></c:otherwise>
											</c:choose>
										</td>
										<td>${dist2.distCode}</td>
										<td>${dist2.distShortName}</td>
										<td>${dist2.longitude}</td>
										<td>${dist2.latitude}</td>
										<td>
											<div class="btn-group">
												<a class="btn btn-small <c:if test="${childrenIndex2.index == 0}">disabled</c:if>" href="/content/category/moveup/${dist2.distCode}"> <i class="icon-arrow-up"></i> 上移</a>
												<a class="btn btn-small <c:if test="${childrenIndex2.index == fn:length(dist1.children) - 1}">disabled</c:if>" href="/content/category/movedown/${dist2.distCode}"> <i class="icon-arrow-down"></i> 下移</a>
											</div>
										</td>
										<td>
											<a class="btn btn-small btn-success disabled" href="${adminUrl}/admin/basedata/district/add/${dist2.distCode}"><i class="icon-plus-sign icon-white"></i> 添加子节点</a>
											<a class="btn btn-small btn-info" href="${adminUrl}/admin/basedata/district/edit/${dist2.distCode}"><i class="icon-edit icon-white"></i> 编辑</a>
											<a class="btn btn-small btn-danger delete" href="javascript:void(0);" data="${adminUrl}/admin/basedata/district/delete/${dist2.distCode}"><i class="icon-trash icon-white"></i> 删除</a>
										</td>
									</tr>									
								</c:forEach>
							</c:if>
						</c:forEach>
					</c:if>
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
	alert("确认删除该栏目及其所有子栏目？", 2, "删除栏目", function(){window.location.href = href;});
});

$(".disabled").bind("click",function(e){e.preventDefault();});
</script>
</body>
	</html>