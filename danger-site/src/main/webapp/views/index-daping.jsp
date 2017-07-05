<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
		<%@include file="inc/header.jsp" %>
		<style type="text/css">
		body{font-size:36px;}
		.index-daping-title{font-size:30px;font-weight:bold;color:red}
		.row-line{border-bottom:2px dashed #d4d4d4;margin:10px 2px}
		.table td{padding:10px 10px;}
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
            		<div class="index-daping center">
            			<div class="index-daping-title">危险作业-今日录入</div>
            			<div class="index-daping-divcontent">
						  	<div id="workexecute-header " style="overflow: hidden;width: 100%;">
					            <table class="table ">
					                <colgroup >
					                    <col width="170">
					                    <col/>
					                    <col width="170">
					                    <col width="170">
					                    <col width="170">
					                    <col width="170">
					                    <col width="170">
					                    <col width="170">
					                    <col width="170">
					                </colgroup>
					                <thead>
					                <tr>
					                    <td>公司名称</td>
					                    <td>危险作业项目名称</td>
					                    <td>作业级别</td>
					                    <td>开始时间</td>
					                    <td>关闭时间</td>
					                    <td>审批人</td>
					                    <td>复核人</td>
					                    <td>现场指挥</td>
					                    <td>作业进展</td>
					                </tr>
					                </thead>
					            </table>
						  	</div> 
						  	<div id="workexecute-demo" style="overflow: hidden; width: 100%;height: 320px;">
						  	  <div id="workexecute-demo1">
					            <table class="table " >
					                <colgroup >
					                    <col width="170">
					                    <col/>
					                    <col width="170">
					                    <col width="170">
					                    <col width="170">
					                    <col width="170">
					                    <col width="170">
					                    <col width="170">
					                    <col width="170">
					                </colgroup>
					                <tbody class="js-workexecute-hook">
					                
					                </tbody>
					            </table>
					            <script id="js-workexecute-tmpl" type="text/html">
						{{each result as item index}}
						<tr class="{{if item.executeState=='已完成'}}row-green{{else }}row-red {{/if}}">
							<td title="{{item.orgName}}">{{item.orgName}}</td>
		                    <td class="anchor" title="{{item.workName}}"><a href="${siteUrl }/project/workExecute/viewPage/{{item.id}}">{{item.workName}}</a></td>
							<td title="{{item.workLevel}}">{{item.workLevel}}</td>
							<td title="{{item.beginTime}}">{{item.beginTime}}</td>
							<td title="{{item.endTime}}">{{item.endTime}}</td>
		                    <td title="{{item.examiner}}">{{item.examiner}}</td>
							<td title="{{item.checker}}">{{item.checker}}</td>
		                    <td title="{{item.director}}">{{item.director}}</td>
		                    <td title="{{if item.notes}}{{item.notes}}{{/if}}">
									<label  class="{{if item.executeState=='已完成'}}label label-blue 
										{{else if item.executeState=='实施中'}}label label-yellow 
										{{else if item.executeState=='未实施'}}label label-red{{/if}}" >
										{{item.executeState}}
									</label>
							</td>
		                </tr>
						{{/each}}
					</script>
						  	  </div>
						  	  <div id="workexecute-demo2">&nbsp;</div>
						  	</div>   
            			</div>
            		</div>
            		<div class="index-daping center">
            			<div class="index-daping-title">试运投产-今日录入</div>
            			<div class="index-daping-divcontent">
						  	<div id="produceexecute-header " style="width: 100%;">
					            <table class="table cliptb">
					                <colgroup >
					                    <col width="170">
					                    <col/>
					                    <col width="170">
					                    <col width="170">
					                    <col width="170">
					                    <col width="170">
					                    <col width="170">
					                    <col width="170">
					                    <col width="170">
					                </colgroup>
					                <thead>
					                <tr>
					                    <td>公司名称</td>
					                    <td>试运投产项目名称</td>
					                    <td>作业级别</td>
					                    <td>开始时间</td>
					                    <td>关闭时间</td>
					                    <td>审批人</td>
					                    <td>复核人</td>
					                    <td>现场指挥</td>
					                    <td>作业进展</td>
					                </tr>
					                </thead>
					            </table>
						  	</div> 
						  	<div id="produceexecute-demo" style="overflow: hidden; width: 100%;height: 320px;">
						  	  <div id="produceexecute-demo1">
					            <table class="table " >
					                <colgroup >
					                    <col width="170">
					                    <col/>
					                    <col width="170">
					                    <col width="170">
					                    <col width="170">
					                    <col width="170">
					                    <col width="170">
					                    <col width="170">
					                    <col width="170">
					                </colgroup>
					                <tbody class="js-produceexecute-hook">
					                
					                </tbody>
					            </table>
					            <script id="js-produceexecute-tmpl" type="text/html">
						{{each result as item index}}
						<tr class="{{if item.executeState=='已完成'}}row-green{{else }}row-red {{/if}}">
							<td title="{{item.orgName}}">{{item.orgName}}</td>
		                    <td class="anchor" title="{{item.workName}}"><a href="${siteUrl }/project/workExecute/viewPage/{{item.id}}">{{item.workName}}</a></td>
							<td title="{{item.workLevel}}">{{item.workLevel}}</td>
							<td title="{{item.beginTime}}">{{item.beginTime}}</td>
							<td title="{{item.endTime}}">{{item.endTime}}</td>
		                    <td title="{{item.examiner}}">{{item.examiner}}</td>
							<td title="{{item.checker}}">{{item.checker}}</td>
		                    <td title="{{item.director}}">{{item.director}}</td>
		                    <td title="{{if item.notes}}{{item.notes}}{{/if}}">
									<label  class="{{if item.executeState=='已完成'}}label label-blue 
										{{else if item.executeState=='实施中'}}label label-yellow 
										{{else if item.executeState=='未实施'}}label label-red{{/if}}" >
										{{item.executeState}}
									</label>
							</td>
		                </tr>
						{{/each}}
					</script>
						  	  </div>
						  	  <div id="produceexecute-demo2">&nbsp;</div>
						  	</div>   
            			</div>
            		</div>
            	</div>
            </div>
        </div>
    </body>
    <%@include file="inc/footer.jsp" %>
    <script src="js/daping.js"></script>
</html>