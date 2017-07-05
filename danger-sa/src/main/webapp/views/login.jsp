<%@ page pageEncoding= "UTF-8" %>
<%@ page contentType= "text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>辰安科技 - 后台管理中心</title>
<link rel="stylesheet" href="${adminUrl}/css/login/template.css" type="text/css" />
<link rel="stylesheet" href="${adminUrl}/lib/bootstrap/css/bootstrap.min.css" />
<style>
.btn { border-color: #c5c5c5; border-color: rgba(0,0,0,0.15) rgba(0,0,0,0.15) rgba(0,0,0,0.25); }
.btn-primary { color: #fff; text-shadow: 0 -1px 0 rgba(0,0,0,0.25); background-color: #1d6cb0; background-image: -moz-linear-gradient(top,#2384d3,#15497c); background-image: -webkit-gradient(linear,0 0,0 100%,from(#2384d3),to(#15497c)); background-image: -webkit-linear-gradient(top,#2384d3,#15497c); background-image: -o-linear-gradient(top,#2384d3,#15497c); background-image: linear-gradient(to bottom,#2384d3,#15497c); background-repeat: repeat-x; filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ff2384d3', endColorstr='#ff15497c', GradientType=0); border-color: #15497c #15497c #0a223b; border-color: rgba(0,0,0,0.1) rgba(0,0,0,0.1) rgba(0,0,0,0.25); *background-color: #15497c; filter: progid:DXImageTransform.Microsoft.gradient(enabled = false); }
.btn-primary:hover,
.btn-primary:active,
.btn-primary.active,
.btn-primary.disabled,
.btn-primary[disabled] { color: #fff; background-color: #15497c; *background-color: #113c66; }
.btn-large { padding: 9px 14px; font-size: 17.5px; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; }
</style>
</head>
<body class="site com_login view-login layout-default task- itemid- ">
<c:if test="${!empty topWindowLogin}">
	<script type="text/javascript">
		window.top.location.href='${adminUrl}/login';
	</script>
</c:if>
<!-- Container -->
<div class="container">
  <div id="content">
    <!-- Begin Content -->
    <div id="element-box" class="login well"> <img src="${adminUrl}/images/admin/login-logo.png" alt="北京辰安科技LOGO" />
      <hr />
      <div id="system-message-container"> </div>
      <form action="${adminUrl}/login" method="post" id="login-form" class="form-inline">
        <fieldset class="loginform">
        <div class="control-group">
          <div class="controls">
            <div class="input-prepend input-append"> <span class="add-on"> <i class="icon-user" title="User Name"></i>
              <label for="mod-login-username" class="element-invisible"> User Name </label>
              </span>
              <input name="username" tabindex="1" value="gsafety" id="mod-login-username" type="text" class="input-medium" placeholder="User Name" size="15"/>
            </div>
          </div>
        </div>
        <div class="control-group">
          <div class="controls">
            <div class="input-prepend input-append"> <span class="add-on"> <i class="icon-lock" title="Password"></i>
              <label for="mod-login-password" class="element-invisible"> Password </label>
              </span>
              <input name="password" tabindex="2" id="mod-login-password" value="Gsafety@321#" type="password" class="input-medium" placeholder="Password" size="15"/>
       </div>
          </div>
        </div>
        <div class="control-group">
          <div class="controls">
            <div class="btn-group pull-left">
              <button id="login-submit" tabindex="3" class="btn btn-primary btn-large">登 录</button>
            </div>
          </div>
        </div>
        </fieldset>
      </form>
    </div>
    <noscript>
    Warning! JavaScript must be enabled for proper operation of the Administrator backend.
    </noscript>
    <!-- End Content -->
  </div>
</div>
<div class="navbar navbar-fixed-bottom hidden-phone">
  <p class="pull-right"> Copyright&copy;2015 北京辰安科技股份有限公司 版权所有</p>
  <a href="http://www.gsafety.com" class="pull-left"><i class="icon-share icon-white"></i> 北京辰安科技股份有限公司首页</a>
</div>
<%@include file="./inc/footer.jsp" %>
	<script>
		$(document).on("click","#login-submit",function(){
		  $("#login-form").submit();
		}).on("keydown",function(EventCode){
				if(EventCode.keyCode == 13){
					 $("#login-submit").click();
				}
			
		});
	</script>
</body>
</html>
