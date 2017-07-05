<%@ page pageEncoding= "UTF-8" %>
<%@ page contentType= "text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>登录跳转</title>
</head>
<body>
<%@include file="./inc/footer.jsp" %>
<script type="text/javascript">
window.top.location.href='<%=request.getContextPath()%>/login';
</script>
</body>
</html>
