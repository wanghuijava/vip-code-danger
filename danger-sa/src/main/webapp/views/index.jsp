<%@ page pageEncoding= "UTF-8" %>
<%@ page contentType= "text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" class="off">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge;chrome=1;" />
<title>后台管理中心</title>
<link href="${adminUrl}/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${adminUrl}/css/g-system.css" rel="stylesheet" type="text/css" />
<link href="${adminUrl}/lib/artDialog/skins/dialog.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript" src="${adminUrl}/lib/jquery.min.js"></script>
<style type="text/css">
.objbody{overflow:hidden}

.btns{background-color:#666;}
.btns{position: absolute; top:116px; right:30px; z-index:1000; opacity:0.6;}
.btns2{background-color:rgba(0,0,0,0.5); color:#fff; padding:2px; border-radius:3px; box-shadow:0px 0px 2px #333; padding:0px 6px; border:1px solid #ddd;}
.btns:hover{opacity:1;}
.btns h6{padding:4px; border-bottom:1px solid #666; text-shadow: 0px 0px 2px #000;}
.btns .pd4{ padding-top:4px; border-top:1px solid #999;}
.pd4 li{border-radius:0px 6spx 0px 6px; margin-top:2px; margin-bottom:3px; padding:2px 0px;}
.btns .pd4 li span{padding:0px 6px;}
.pd{padding:4px;}
.ac{background-color:#333; color:#fff;}
.hvs{background-color:#555; cursor: pointer;}
.bg_btn{background: url(${adminUrl}/img/admin/icon2.jpg) no-repeat; width:32px; height:32px;}
</style>
</head>
<body scroll="no" class="objbody">
<div class="header">
	<div class="logo lf"><a href="#" target="_blank"><span class="invisible">辰安科技 - 后台管理系统</span></a></div>
    <div class="rt-col">
    </div>
    <div class="col-auto">
    	<div class="log white cut_line">
			您好！辰安  [超级管理员]<span>|</span>
			<a href="${adminUrl}/logout">[退出]</a><span>|</span>
    	</div>
        <ul class="nav white" id="top_menu">
			<!-- li id="_M1" class="on top_menu" data="mypanel"><a href="javascript:void(0);" hidefocus="true" style="outline:none;">我的面板</a></li -->
			<li id="_M1" class="top_menu" data="basedata-m"><a href="javascript:void(0);"  hidefocus="true" style="outline:none;">基础数据管理</a></li>
            <li id="_M2" class="top_menu" data="authority-m"><a href="javascript:void(0);"  hidefocus="true" style="outline:none;">系统权限管理</a></li>
			<li id="_M3" class="top_menu" data="setting"><a href="javascript:void(0);"  hidefocus="true" style="outline:none;">系统参数管理</a></li>
			<!-- <li id="_M5" class="top_menu" data="resourceType-m"><a href="javascript:void(0);"  hidefocus="true" style="outline:none;">应急资源类型</a></li>
			 -->
        </ul>
    </div>
</div>
<div id="content">
	<div class="col-left left_menu">
    	<div id="Scroll">
			<div id="leftMain">
			</div>
		</div>
        <a href="javascript:;" id="openClose" style="outline-style: none; outline-color: invert; outline-width: medium;" hideFocus="hidefocus" class="open" title="展开与关闭"><span class="hidden">展开</span></a>
    </div>
	<div class="col-1 lf cat-menu" id="display_center_id" style="display:none" height="100%">
	<div class="content">
        	<iframe name="center_frame" id="center_frame" src="about:blank" frameborder="false" scrolling="auto" style="border:none" width="100%" height="auto" allowtransparency="true"></iframe>
            </div>
        </div>
    <div class="col-auto mr8">
    <div class="crumbs">
    <div class="shortcut cu-span">
		<!-- a href="#" target="right"><span>快捷按钮1</span></a>
		<a href="#" target="right"><span>快捷按钮2</span></a>
		<a href="#"><span>快捷按钮3</span></a -->
	</div>
    	当前位置：<span id="current_pos">位置1 > 位置2 > 位置3 </span></div>
    	<div class="col-1">
        	<div class="content" style="position:relative; overflow:hidden">
                <iframe name="right" id="rightMain" src="${adminUrl}/empty" frameborder="false" scrolling="auto" style="border:none; margin-bottom:30px" width="100%" height="auto" allowtransparency="true"></iframe>
                <!-- div class="fav-nav">
					<div id="panellist">
											</div>
					<div id="paneladd"></div>
					<input type="hidden" id="menuid" value="">
					<input type="hidden" id="bigid" value="" />
                    <div id="help" class="fav-help"></div>
				</div   -->
        	</div>
        </div>
    </div>
</div>
<div class="tab-web-panel hidden" style="position:absolute; z-index:999; background:#fff">
<ul>
	<li style="margin:0"><a href="">其他</a></li>
</ul>
</div>
<div class="scroll"><a href="javascript:;" class="per" title="使用鼠标滚轴滚动侧栏" onclick="menuScroll(1);"></a><a href="javascript:;" class="next" title="使用鼠标滚轴滚动侧栏" onclick="menuScroll(2);"></a></div>
<script>
var adminUrl = "${adminUrl}";
</script>
<script src="${adminUrl}/js/starscream.home.js"></script>
<script src="${adminUrl}/lib/artDialog/dialog.js"></script>
</body>
</html>