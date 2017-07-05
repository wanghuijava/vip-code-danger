<%@page pageEncoding="UTF-8" %>
<!doctype html>
<html>
	<head>
		<%@include file="../../inc/header.jsp" %>
	</head>
	<body>
		<form id="list-form" action="/project/workPlan/find">
			<div class="layout layout-full">
			    <div class="layout layout-search">
			    
			    	<div class="search-widget">
			    		<input type="hidden"  name="param.nextMonth" value="1">
			    		<input type="hidden"  name="param.thisMonth" value="">
			    		<label><input type="checkbox" name="nextMonth" checked="checked"/>下月</label>
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
	                    <button type="button" class="btn btn-normal" data-href="${siteUrl }/project/workPlan/addPage">
	                        <span class="btn-otr"><span class="btn-inr"><i class="icon icon-add"></i><span class="ibm">新增</span></span></span>
	                    </button>
	                </div>
	                <c:if test="${user.superior}">
	                <div class="btn-grp ibm">
	                    <button type="button" class="btn btn-normal" id="exportData">
	                        <span class='btn-otr'><span class='btn-inr'>下月计划导出</span></span>
	                    </button>
	                </div>
	                </c:if>
	                <div class="layout search-filter-content" id="js-search-param">
			            <dl>
			                <dt style="width:200px">计划实施时间：</dt>
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
				                        <button class="btn changeTime" id="doSearch" data-value="" type="button">确定</button>
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
			                    <col width="120">
			                    <col width="250">
			                    <col width="100">
			                    <col>
			                    <col width="120">
			                    <col width="100">
			                    <col width="100">
			                    <col width="100">
			                    <c:if test="${!user.superior}" var="xiaji">
			                    <col width="80">
			                    </c:if>
			                    <col width="220">
			                </colgroup>
			                <thead>
			                <tr>
			                    <td>公司名称</td>
			                    <td>危险作业项目名称</td>
			                    <td>作业级别</td>
			                    <td>工程概况</td>
			                    <td>计划实施时间</td>
			                    <td>作业审批人</td>
			                    <td>作业复核人</td>
			                    <td>现场指挥</td>
			                    <c:if test="${!user.superior}">
			                    <td>审核</td>
			                    </c:if>
			                    <td class="txt-ac">操作</td>
			                </tr>
			                </thead>
			            </table>
			        </div>
			        <div x-scrollable class="layout layout-table-body cliptb">
			            <table class="table cliptb" x-checkgrp x-sorttable >
			                <colgroup >
			                    <col width="120">
			                    <col width="250">
			                    <col width="100">
			                    <col>
			                    <col width="120">
			                    <col width="100">
			                    <col width="100">
			                    <col width="100">
			                    <c:if test="${!user.superior}">
			                    <col width="80">
			                    </c:if>
			                    <col width="220">
			                </colgroup>
			                <tbody id="list-tb">
			                
			                </tbody>
			            </table>
			            <script id="list-tmpl" type="text/html">
						{{each result as item index}}
						<tr class="
							{{if index%2==1}}
							even
							{{/if}}
							">
							<td title="{{item.orgName}}">{{item.orgName}}</td>
		                    <td class="anchor" title="{{item.workName}}"><a href="${siteUrl }/project/workPlan/viewPage/{{item.id}}">{{item.workName}}</a></td>
							<td>{{item.workLevel}}</td>
							<td title="{{item.summary}}">{{item.summary}}</td>
							<td>{{item.executeDateStr}}</td>
		                    <td title="{{item.examiner}}">{{item.examiner}}</td>
							<td title="{{item.checker}}">{{item.checker}}</td>
		                    <td title="{{item.director}}">{{item.director}}</td>
							<c:if test="${!user.superior}">
			                <td class="txt-ac expe">
								{{if item.checkFlag==0}}
								<label class="label label-red <c:if test="${user.typeCode=='2'}">anchor</c:if>" <c:if test="${user.typeCode=='2'}"> title="点击上报" data-post-url="/project/workPlan/report" data-confirm="是否确认上报？" data-post-param='{"id":"{{item.id}}"}'</c:if> >{{item.checkFlagStr}}</label>
								{{else}}
								<label class="label label-blue">{{item.checkFlagStr}}</label>
								{{/if}}
							</td>
			                </c:if>
		                    <td class="txt-ac expe">
								{{if '${nextYearMonth}' == item.planMonth && item.checkFlag==0 || '${user.superior}'== 'true'}}
								<button type="button" class="btn btn-green" data-href="${siteUrl }/project/workPlan/editPage/{{item.id}}">
									<span class="btn-otr">
										<span class="btn-inr"><i class="icon icon18 icon-edit"></i>编辑</span>
									</span>
								</button>
								<button type="button" class="btn btn-red" data-post-url="/project/workPlan/delete" data-confirm="" data-post-param='{"id":"{{item.id}}"}'>
									<span class="btn-otr">
										<span class="btn-inr"><i class="icon icon18 icon-delete"></i>删除</span>
									</span>
								</button>
								{{else}}
								&nbsp;
								{{/if}}
		                    </td>
		                </tr>
						{{/each}}
						{{if result.length==0}}
							<tr><c:if test="${user.superior}">
								<td colspan="9">
									<%@include file="../../inc/no-data.jsp"%>
								</td>
								</c:if>
								<c:if test="${!user.superior}">
								<td colspan="10">
									<%@include file="../../inc/no-data.jsp"%>
								</td>
								</c:if>
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
	<script src="${siteUrl }/js/project/workPlan/workPlan-list.js"></script>
	<script src="${siteUrl }/js/mysearch.js"></script>
</html>