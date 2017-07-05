<!DOCTYPE html>
<%@page pageEncoding="UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
    <head>
    	<base href="<%=basePath%>">
		<meta charset="utf-8"/>
		<meta http-equiv = "X-UA-Compatible" content = "IE=edge,chrome=1"/>
		<!-- Set render engine for 360 browser -->
		<meta name="renderer" content="webkit">
		<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
		<title>危险作业管理系统</title>
		<link rel="shortcut icon" href="favicon.ico"/>
		<link rel="stylesheet" href="css/normalize.css"/>
		<link rel="stylesheet" href="css/layout.css"/>
		<link rel="stylesheet" href="css/icon.css"/>
		<link rel="stylesheet" href="css/main.css"/>
		<link rel="stylesheet" href="css/control.css"/>
		<link rel="stylesheet" href="css/animate.min.css"/>
		<link rel="stylesheet" href="css/animation-custom.css"/>
		
		<!--[if lte IE 8]>
		<link rel="stylesheet" href="css/hack.css"/>
		<![endif]-->
    </head>
    <body >
    	<div class="layout layout-full">
    		<div class="layout layout-exception exception-404 txt-ac">
    			<div class="exception-inr">
    				<h1>404</h1>
    				<h3>COMPONENT NOT FOUND</h3>
    				<p>抱歉，你请求的页面暂时无法打开，请稍后再试！</p>
    				<button class="btn" data-top-href="<%=path%>">
    					<span class="btn-otr">
    						<span class="btn-inr">返回首页</span>
    					</span>
    				</button>
    			</div>
    		</div>
    	</div>
    </body>
    <script type="text/javascript">
	// 网站上下文
	var siteUrl='<%=path%>';
	//doc转office后服务地址前缀
	</script>
    <script src="lib/jquery-1.7.2.min.js"></script>
    <script src="lib/artDialog4.1.6/artDialog.js?skin=default"></script>
    <script src="lib/malihu-custom-scrollbar-plugin-master/jquery.mCustomScrollbar.concat.min.js"></script>
    <script src="service/commonService.js"></script>
    <script src="js/common.js"></script>
</html>