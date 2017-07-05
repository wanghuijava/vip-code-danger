<%@page pageEncoding="UTF-8" %>
<!doctype html>
<html>
	<head>
		<%@include file="../../inc/header.jsp" %>
	</head>
	<body>
		<form id="list-form" action="/project/workExecute/find">
			<div class="layout layout-full">
			    <div class="layout layout-search">
			    
			    	<div class="search-widget">
			    		<input type="hidden"  name="param.thisDay" value="1">
			    		<input type="hidden"  name="param.thisMonth" value="">
			    		<label><input type="checkbox" name="thisDay" checked="checked"/>今日</label>
			    		<label><input type="checkbox" name="thisMonth" />本月</label>
				        <div class="search-filter ibm">
			                <div class="search-case">
			                    <input type="text" class="ipt" name="param.workName" value="" placeholder="可按公司名、项目名等过滤">
			                    <button class="btn" type="submit"></button>
			                </div>
				        </div>
				        <a href="javascript:;" class="ibm expander">高级搜索<i></i></a>
			        </div>
	                <div class="btn-grp ibm">
	                    <button type="button" class="btn btn-normal" data-href="${siteUrl }/project/workExecute/addPage">
	                        <span class="btn-otr"><span class="btn-inr"><i class="icon icon-add"></i><span class="ibm">新增</span></span></span>
	                    </button>
	                </div>
	                <c:if test="${user.superior}" var ="superior">
	                <div class="btn-grp ibm">
	                    <button type="button" class="btn btn-normal" id="exportData">
	                        <span class='btn-otr'><span class='btn-inr'>今日导出</span></span>
	                    </button>
	                </div>
	                </c:if>
	                <div class="layout search-filter-content" id="js-search-param">
			            <dl>
			                <dt style="width:200px">日期：</dt>
			                <dd>
			                
				                	<input type="hidden" name="param.searchStartTimeStr" data-time-start value="" id="search_startTime"/>
				                	<input type="hidden" name="param.searchEndTimeStr" data-time-end value="" id="search_endTime"/>
				                	<label class="active changeTime"  data-value="all">
				                		<a href="javascript:;" >全部</a>
				                	</label>
				                    <span x-time-group="main">
				                    	<span class="ipt-case ibm">
				                    		<input class="ipt" type="text" x-time-start/>
				                    	</span>
			                        		至
			                        	<span class="ipt-case ibm">
			                        		<input class="ipt" type="text"  x-time-end>
			                        	</span>
				                        <button class="btn changeTime" data-value="" type="button">确定</button>
				                        <button class="btn" data-value="" id="doExport" type="button">导出</button>
				                    </span>
			                </dd>
			            </dl>
			        </div>

			    </div>
			    <div class="layout layout-search-result">
			        <div class="layout layout-table-header">
			            <table class="table cliptb">
			                <colgroup >
			                    <col width="110">
			                    <c:if test="${superior}">
			                    <col width="100">
			                    </c:if>
			                    <col width="250">
			                    <col width="90">
			                    <col width="90">
			                    <col width="90">
			                    <col />
			                    <col width="100">
			                    <col width="100">
			                    <col width="100">
			                    <col width="100">
			                    <c:if test="${!superior}">
			                    <col width="220">
			                    </c:if>
			                </colgroup>
			                <thead>
			                <tr>
			                    <td>日期</td>
			                    <c:if test="${superior}">
			                    <td>公司名称</td>
			                    </c:if>
			                    <td>危险作业项目名称</td>
			                    <td>作业级别</td>
			                    <td>开始时间</td>
			                    <td>关闭时间</td>
			                    <td>工程概况</td>
			                    <td>作业审批人</td>
			                    <td>作业复核人</td>
			                    <td>现场指挥</td>
			                    <td>作业进展</td>
			                    <c:if test="${!superior}">
			                    <td class="txt-ac">操作</td>
			                    </c:if>
			                </tr>
			                </thead>
			            </table>
			        </div>
			        <div x-scrollable class="layout layout-table-body cliptb">
			            <table class="table cliptb" x-checkgrp x-sorttable >
			                <colgroup >
			                    <col width="110">
			                    <c:if test="${superior}">
			                    <col width="100">
			                    </c:if>
			                    <col width="250">
			                    <col width="90">
			                    <col width="90">
			                    <col width="90">
			                    <col/>
			                    <col width="100">
			                    <col width="100">
			                    <col width="100">
			                    <col width="100">
			                    <c:if test="${!superior}">
			                    <col width="220">
			                    </c:if>
			                </colgroup>
			                <tbody id="list-tb">
			                
			                </tbody>
			            </table>
			            <script id="list-tmpl" type="text/html">
						{{each result as item index}}
						<tr class="{{if item.executeState=='已完成'}}row-green{{else }}row-red {{/if}}">
							<td>{{item.executeDateStr}}</td>
			                <c:if test="${superior}">
							<td title="{{item.orgName}}">{{item.orgName}}</td>
							</c:if>
		                    <td class="anchor" title="{{item.workName}}"><a href="${siteUrl }/project/workExecute/viewPage/{{item.id}}">{{item.workName}}</a></td>
							<td title="{{item.workLevel}}">{{item.workLevel}}</td>
							<td title="{{item.beginTime}}">{{item.beginTime}}</td>
							<td title="{{item.endTime}}">{{item.endTime}}</td>
							<td title="{{item.summary}}">{{item.summary}}</td>
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
							<c:if test="${!superior}">
		                    <td class="txt-ac  expe">
								{{if '${todayDate}' == item.execDay}}
								<button type="button" class="btn btn-green" data-href="${siteUrl }/project/workExecute/editPage/{{item.id}}">
									<span class="btn-otr">
										<span class="btn-inr"><i class="icon icon18 icon-edit"></i>编辑</span>
									</span>
								</button>
								<button type="button" class="btn btn-red" data-post-url="/project/workExecute/delete" data-confirm="" data-post-param='{"id":"{{item.id}}"}'>
									<span class="btn-otr">
										<span class="btn-inr"><i class="icon icon18 icon-delete"></i>删除</span>
									</span>
								</button>
								{{/if}}
		                    </td>
			                </c:if>
		                </tr>
						{{/each}}
						{{each unExecute as item index}}
						<tr class="project-unexecute">
							<td>{{item.executeDateStr}}</td>
			                <c:if test="${superior}">
							<td title="{{item.orgName}}">{{item.orgName}}</td>
							</c:if>
		                    <td title="{{item.workName}}">{{item.workName}}</td>
							<td title="{{item.workLevel}}">{{item.workLevel}}</td>
							<td title="{{item.beginTime}}">{{item.beginTime}}</td>
							<td title="{{item.endTime}}">{{item.endTime}}</td>
							<td title="{{item.summary}}">{{item.summary}}</td>
		                    <td title="{{item.examiner}}">{{item.examiner}}</td>
							<td title="{{item.checker}}">{{item.checker}}</td>
		                    <td title="{{item.director}}">{{item.director}}</td>
		                    <td title="{{if item.notes}}{{item.notes}}{{/if}}">
									<label  class="label label-red" >
										尚未实施
									</label>
							</td>
							<c:if test="${!superior}">
		                    <td class="txt-ac  expe">
								<button type="button" class="btn btn-green" data-href="${siteUrl }/project/workExecute/addPage?workPlanId={{item.id}}">
									<span class="btn-otr">
										<span class="btn-inr"><i class="icon icon18 icon-edit"></i>实施</span>
									</span>
								</button>
		                    </td>
			                </c:if>
		                </tr>
						{{/each}}
						{{if result.length==0 && unExecute.length == 0}}
							<tr>
								<td colspan="11">
									<%@include file="../../inc/no-data.jsp"%>
								</td>
							</tr>
						{{/if}}
					</script>
			        </div>
					<div class="layout layout-table-footer">
						<div x-pager class="flr" data-name="page.count" data-count="6"></div>
					</div>
			    </div>
			</div>
		</form>
	</body>
	
	<%@include file="../../inc/footer.jsp" %>
	<script src="${siteUrl }/js/project/workExecute/workExecute-list.js"></script>
	<script src="${siteUrl }/js/mysearch.js"></script>
</html>