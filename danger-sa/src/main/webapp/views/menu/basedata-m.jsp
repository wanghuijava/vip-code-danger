<%@ page pageEncoding= "UTF-8" %>
<%@ page contentType= "text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<h3 class="f14">
	<span class="switchs cu on" title="展开与收缩"></span>基础数据管理
</h3>
<ul>
	<li class="sub_menu"><a href="${adminUrl}/admin/basedata/district/list" hidefocus="true" style="outline: none;">行政区划管理</a></li>
	<li class="sub_menu"><a href="${adminUrl}/admin/basedata/org/findAll" hidefocus="true" style="outline: none;">组织机构管理</a></li>
	<%-- <li class="sub_menu"><a href="${adminUrl}/admin/basedata/eventType/findAll" hidefocus="true" style="outline: none;">事件类型管理</a></li> --%>
</ul>