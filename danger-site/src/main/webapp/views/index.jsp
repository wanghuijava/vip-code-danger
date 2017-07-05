<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<script type="text/javascript">
<!--
if("${user.username=='daping'}"=="true"){
	window.top.location.href="<%=request.getContextPath()%>/indexDapingPage";
}
//-->
</script>
    <head>
		<%@include file="inc/header.jsp" %>
    </head>
    <body >
        <div class="layout layout-full" >
            <div class="layout layout-header">
            	<div class="header-group group">
				    <%@include file="inc/user-info.jsp" %>
				    <div class="main-anchor ibm logo">
			           	危险作业管理系统
				    </div>
				</div>
            </div>
            <div class="layout layout-menu">
            	<div class="layout layout-full">
				    <ul class="menu">
				    	<li>
				            <a href="${siteUrl }/project/workPlan/listPage">
				                <span><i class="icon icon-danger"></i>危险作业-下月计划录入</span>
				            </a>
				        </li>
				        <li class="active" >
				            <a href="${siteUrl }/project/workExecute/listPage">
				                <span><i class="icon icon-risk"></i>危险作业-今日录入</span>
				            </a>
				        </li>
				    	<li>
				            <a href="${siteUrl }/project/producePlan/listPage">
				                <span><i class="icon icon-epp"></i>试运投产-下月计划录入</span>
				            </a>
				        </li>
				        <li>
				            <a href="${siteUrl }/project/produceExecute/listPage">
				                <span><i class="icon icon-epp-sub"></i>试运投产-今日实施录入</span>
				            </a>
				        </li>
				    </ul>
				</div>
            	
            </div>
            <div class="layout layout-main main-case">
            	<iframe data-rel="main" class="layout layout-full" frameborder="0" scrolling="no"></iframe>
            </div>
        </div>
    </body>
    <%@include file="inc/footer.jsp" %>
    <script src="js/index.js"></script>
</html>