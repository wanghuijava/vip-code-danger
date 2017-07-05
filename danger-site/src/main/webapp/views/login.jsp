<%@page pageEncoding="UTF-8" %>
<!doctype html>
<html>
	<head>
	    <%@include file="inc/header.jsp" %>
	    <link rel="stylesheet" href="${siteUrl }/css/login.css"/>
	</head>
	<body>
		<div class="login-main-bg"></div>
	        <form action="">
	        <div class="txt-ac">
	        	<div class="login-case txt-ac" style="border-left:2px solid #fff;border-right:2px solid #fff;width:600px;MARGIN-RIGHT: auto; MARGIN-LEFT: auto; ">
	        		<div class="login-logo " style="line-height:120px;color:#ffffff;font-size:45px;font-weight:bold;">危险作业管理系统
	        		</div>
	        		<div class="login-group ibt">
	        			<table>
	        				<tr>
	        					<td>
	        						<label class="login-ipt">
	        							<i class="login-icon icon-user"></i>
	        							<input data-rule="notnull" tabindex="1" class="ipt" autofocus data-label="用户名" name="username" type="text"/>
	        						</label>
	        					</td>
	        					<td rowspan="2">
	        						<label>
		        						<button class="btn btn-login" tabindex="-1" type="submit"></button>
		        					</label>
	        					</td>
	        				</tr>
	        				<tr>
	        					<td colspan="2">
	        						<label class="login-ipt">
	        							<i class="login-icon icon-pawd"></i>
	        							<input data-rule="notnull" tabindex="2" class="ipt" data-label="密码" name="password" type="password"/>
	        						</label>
	        					</td>
	        				</tr>
	        			</table>
	        		</div>
	        	</div>
	        </div>
	        </form>
	
	</body>

	<%@include file="inc/footer.jsp" %>
	
	<script src="${siteUrl }/js/login.js"></script>

</html>