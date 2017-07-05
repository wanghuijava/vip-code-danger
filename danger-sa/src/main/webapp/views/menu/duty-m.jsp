<%@ page pageEncoding= "UTF-8" %>
<%@ page contentType= "text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<h3 class="f14">
	<span class="switchs cu on" title="展开与收缩"></span>值守工作
</h3>
<ul>
	<li class="sub_menu"><a href="${adminUrl}/admin/duty/dutyPlanPerson/find" hidefocus="true" style="outline: none;">值班人员维护</a></li>
	<li class="sub_menu"><a href="${adminUrl}/admin/dutySetting/dutyPersonSetting" hidefocus="true" style="outline: none;">值班设置</a></li>
	<li class="sub_menu"><a href="${adminUrl}/admin/duty/archiveType/findAll" hidefocus="true" style="outline: none;">档案类型配置</a></li>
	<li class="sub_menu"><a href="${adminUrl}/admin/dutyLeader/find" hidefocus="true" style="outline: none;">领导分工配置</a></li>
	<li class="sub_menu"><a href="${adminUrl}/admin/orgContact/orgContactList" hidefocus="true" style="outline: none;">联动机构</a></li>
</ul>