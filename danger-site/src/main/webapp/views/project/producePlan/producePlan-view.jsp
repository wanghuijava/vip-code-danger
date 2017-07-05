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
									<th>公司名称:</th>
									<td><div class="ipt-case">
										${dto.orgName }
										</div></td>	
									<th>试运投产项目名称:</th>
									<td>
										<div class="ipt-case">
											${dto.workName }
										</div>
									</td>
								</tr>
								<tr>
									<th>作业级别:</th>
									<td><div class="ipt-case">
											${dto.workLevel }
										</div></td>		
									<th>工程概况:</th>
									<td><div class="ipt-case">${dto.summary }
										</div></td>
								</tr>
								<tr>						
									<th>计划实施时间:</th>
									<td><div class="ipt-case">${dto.executeDateStr }</div></td>
									<th>作业审批人:</th>
									<td><div class="ipt-case">
											${dto.examiner }
										</div></td>
								</tr>
								<tr>
									<th>作业复核人:</th>
									<td><div class="ipt-case">
											${dto.checker }
										</div></td>		
									<th>现场指挥:</th>
									<td><div class="ipt-case">
											${dto.director }
										</div></td>									
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="layout layout-search-result-t-sub">
				<div class="btn-grp fieldset-sub txt-ac">
					<button class="btn btn-gray shim" type="button" data-href="${siteUrl }/project/producePlan/listPage"><span class="btn-otr"><span class="btn-inr">返回</span></span></button>
				</div>
			</div>
		</div>
</body>

<%@include file="../../inc/footer.jsp"%>
<script src="${siteUrl }/js/project/producePlan/producePlan-view.js"></script>
</html>