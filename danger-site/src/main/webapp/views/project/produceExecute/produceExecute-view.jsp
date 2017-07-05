<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<%@include file="../../inc/header.jsp"%>
</head>
<body>

	<input type="hidden" name="param.id" value="${id}">
		<div class="layout layout-full">
			<div class="layout layout-search">
				<div class="g-tabs">
					<a href="javascript:;" class="active">查看信息</a>
				</div>
			</div>
			<div x-scrollable
				class="layout layout-search-result-t scroll-no-margin">
				<div class="form" id="view-tb">
					<div class="fieldset">
						<div class="legend">
							<ul class="form-need-handler">
								<li class="active">基本信息</li>
							</ul>
						</div>
						<div class="fieldset-content">
							<table class="tb fixedtb">
								<colgroup>
									<col width="140">
									<col>
									<col width="200">
								</colgroup>
								<tr>						
									<th>日期:</th>
									<td><div class="ipt-case">${dto.executeDateStr}
										</div></td>
									<th>公司名称:</th>
									<td><div class="ipt-case">
											${dto.orgName }
										</div></td>	
								</tr>
								<tr>
									<th>试运投产项目名称:</th>
									<td>
										<div class="ipt-case">
											${dto.workName}
										</div>
									</td>
									<th>作业级别:</th>
									<td><div class="ipt-case">
											${dto.workLevel}
										</div></td>		
								</tr>
								<tr>
									<th>作业开始时间:</th>
									<td><div class="ipt-case">
											${dto.beginTime}
										</div></td>		
									<th>作业关闭时:</th>
									<td><div class="ipt-case">
											${dto.endTime}
										</div></td>									
								</tr>
								<tr>
									<th>工程概况:</th>
									<td><div class="ipt-case">${dto.summary}
										</div></td>
									<th>是否进行方案学习交底:</th>
									<td><div class="ipt-case">
											${dto.learnFlag }
										</div></td>
								</tr>
								<tr>
									<th>作业审批人:</th>
									<td><div class="ipt-case">
											${dto.examiner }
										</div></td>
									<th>作业复核人:</th>
									<td><div class="ipt-case">
											${dto.checker }
										</div></td>
								</tr>
								<tr>		
									<th>现场指挥:</th>
									<td><div class="ipt-case">
											${dto.director }
										</div></td>		
									<th>作业进展:</th>
									<td><div class="ipt-case">
											${dto.executeState }
										</div></td>									
								</tr>
								<tr>		
									<th>备注:</th>
									<td><div class="ipt-case">
											${dto.notes }
										</div></td>		
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="layout layout-search-result-t-sub">
				<div class="btn-grp fieldset-sub txt-ac">
					<button class="btn btn-gray shim" type="button" data-href="${siteUrl }/project/produceExecute/listPage"><span class="btn-otr"><span class="btn-inr">返回</span></span></button>
				</div>
			</div>
		</div>
</body>

<%@include file="../../inc/footer.jsp"%>
</html>