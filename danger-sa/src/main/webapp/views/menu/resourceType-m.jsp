<%@ page pageEncoding= "UTF-8" %>
<%@ page contentType= "text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<link href="${adminUrl}/css/resourcetype.css" rel="stylesheet" type="text/css" />

<h3 class="f14">
	<span class="switchs cu on" title="展开与收缩"></span>资源类型管理
</h3>
<ul>
<li class="sub_menu"><a href="${adminUrl}/admin/object/defObj/list" hidefocus="true" style="outline: none;">防护目标管理</a></li>
<li class="sub_menu bottomLine"><a href="${adminUrl}/admin/object/dangerType/list" hidefocus="true" style="outline: none;">危险源分类</a></li>
</ul>