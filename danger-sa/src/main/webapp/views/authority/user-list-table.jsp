<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:forEach var="user" items="${userList}" varStatus="index">
<tr>
	<td>${user.username}</td>
	<td>
		<c:choose>
			<c:when test="${user.typeCode == 1}">上级用户</c:when>
			<c:when test="${user.typeCode == 0}">下级用户</c:when>
			<c:otherwise >超级用户</c:otherwise>
		</c:choose>
	</td>
	<td>${user.orgName}</td>
	<td>
		<c:choose>
			<c:when test="${user.status == 1}">正常</c:when>
			<c:when test="${user.status == 0}">注销</c:when>
		</c:choose>
	</td>
	<td><fmt:formatDate value="${user.createTime}" type="date"/></td>
	<td>
		<a class='btn btn-small btn-info' href="${adminUrl}/admin/user/userEditPage/${user.id}"><i class='icon-edit icon-white'></i> 编辑</a>&nbsp;&nbsp;&nbsp;
		<a class='btn btn-small btn-danger delete' onclick="deleteUser(${user.id})"  href="javascript:void(0);">注销</a>
	</td>
</tr>
</c:forEach>