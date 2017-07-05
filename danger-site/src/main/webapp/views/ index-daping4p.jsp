<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
		<%@include file="inc/header.jsp" %>
		<style type="text/css">
		body{font-size:28px;}
		.index-daping-title{font-size:24px;font-weight:bold;color:red}
		.row-line{border-bottom:2px dashed #d4d4d4;margin:10px 2px}
		</style>
    </head>
    <body >
    <input type="hidden" name="nowDate" value="${nowDate}"/>
        <div class="layout layout-full" >
            <div class="layout layout-header">
            	<div class="header-group group">
				    <%@include file="inc/user-info.jsp" %>
				    <div class="main-anchor ibm logo">
			           	危险作业管理系统
				    </div>
				</div>
            </div>
            <div class="layout index-daping-main main-case">
            	<div class="layout layout-full">
            		<div class="index-daping left">
            			<div class="index-daping-title">危险作业-当月计划</div>
            			<div class="index-daping-divcontent">
						  	<div id="workplan-demo" style="overflow: hidden; width: 100%;height: 100%;">
						  	  <div id="workplan-demo1" class="js-workplan-hook">
						  	  	<p>&nbsp;</p>
						  	  </div>
						  	  <div id="workplan-demo2">&nbsp;</div>
						  	</div>   
            			</div>
            		</div>
            		<script id="js-workplan-tmpl" type="text/html">
							{{each result as item index}}
						  	  	<p>【{{item.executeDateStr}}】【{{item.orgName}}】{{item.workName}}{{if item.workLevel}}-{{item.workLevel}}{{/if}}（工程概况：{{item.summary}}）</p>
								<div class="row-line"></div>
							{{/each}}
            		</script>
            		<div class="index-daping right">
            			<div class="index-daping-title">危险作业-今日录入</div>
            			<div class="index-daping-divcontent">
						  	<div id="workexecute-demo" style="overflow: hidden; width: 100%;height: 100%;">
						  	  <div id="workexecute-demo1" class="js-workexecute-hook">
						  	  	<p>&nbsp;</p>
						  	  </div>
						  	  <div id="workexecute-demo2">&nbsp;</div>
						  	</div>   
            			</div>
            		</div>
            		<script id="js-workexecute-tmpl" type="text/html">
							{{each result as item index}}
						  	  	<p>【{{item.executeDateStr}}】【{{item.orgName}}】{{item.workName}}{{if item.workLevel}}-{{item.workLevel}}{{/if}}
								（作业时间：{{item.beginTime}}-{{item.endTime}}） （工程概况：{{item.summary}}）
								（方案交底：{{item.learnFlag}}）（审批：{{item.examiner}}）（复核：{{item.checker}}）（指挥：{{item.director}}）（进展：{{item.executeState}}）</p>
								<div class="row-line"></div>
							{{/each}}
            		</script>
            		<div class="index-daping left">
            			<div class="index-daping-title">试运投产-当月计划</div>
            			<div class="index-daping-divcontent">
						  	<div id="produceplan-demo" style="overflow: hidden; width: 100%;height: 100%;">
						  	  <div id="produceplan-demo1" class="js-produceplan-hook">
						  	  	<p>&nbsp;</p>
						  	  </div>
						  	  <div id="produceplan-demo2">&nbsp;</div>
						  	</div>   
            			</div>
            		</div>
            		<script id="js-produceplan-tmpl" type="text/html">
							{{each result as item index}}
						  	  	<p>【{{item.executeDateStr}}】【{{item.orgName}}】{{item.workName}}{{if item.workLevel}}-{{item.workLevel}}{{/if}}（工程概况：{{item.summary}}）</p>
								<div class="row-line"></div>
							{{/each}}
            		</script>
            		<div class="index-daping right">
            			<div class="index-daping-title">试运投产-今日录入</div>
            			<div class="index-daping-divcontent">
						  	<div id="produceexecute-demo" style="overflow: hidden; width: 100%;height: 100%;">
						  	  <div id="produceexecute-demo1" class="js-produceexecute-hook">
						  	  	<p>&nbsp;</p>
						  	  </div>
						  	  <div id="produceexecute-demo2">&nbsp;</div>
						  	</div>   
            			</div>
            		</div>
            		<script id="js-produceexecute-tmpl" type="text/html">
							{{each result as item index}}
						  	  	<p>【{{item.executeDateStr}}】【{{item.orgName}}】{{item.workName}}{{if item.workLevel}}-{{item.workLevel}}{{/if}}
								（作业时间：{{item.beginTime}}-{{item.endTime}}） （工程概况：{{item.summary}}）
								（方案交底：{{item.learnFlag}}）（审批：{{item.examiner}}）（复核：{{item.checker}}）（指挥：{{item.director}}）（进展：{{item.executeState}}）</p>
								<div class="row-line"></div>
							{{/each}}
            		</script>
            	</div>
            </div>
        </div>
    </body>
    <%@include file="inc/footer.jsp" %>
    <script src="js/daping.js"></script>
</html>