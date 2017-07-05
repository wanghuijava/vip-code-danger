<%@ page pageEncoding= "UTF-8" %>
<%@ page contentType= "text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<h3 class="f14">
	<span class="switchs cu on" title="展开与收缩"></span>系统权限管理
</h3>
<ul>
	<li class="sub_menu"><a href="${adminUrl}/admin/orguser/list" hidefocus="true" style="outline: none;">通讯录管理</a></li>
	<li class="sub_menu"><a href="${adminUrl}/admin/user/list" hidefocus="true" style="outline: none;">登录用户管理</a></li>
	<li class="sub_menu"><a href="${adminUrl}/admin/role/list" hidefocus="true" style="outline: none;">角色管理</a></li>
	<li class="sub_menu"><a href="${adminUrl}/admin/authority/list" hidefocus="true" style="outline: none;">权限管理</a></li>
	<li class="sub_menu"><a href="${adminUrl}/admin/userGroup/list" hidefocus="true" style="outline: none;">分组管理</a></li>
 </ul>