<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<%@include file="../../inc/header.jsp"%>
</head>
<body>

	<form id="edit-form" action="/project/producePlan/save">
	<input type="hidden" name="param.id" value="${id}">
		<div class="layout layout-full">
			<div class="layout layout-search">
				<div class="g-tabs">
					<a href="javascript:;" class="active">编辑信息</a>
				</div>
			</div>
			<div x-scrollable
				class="layout layout-search-result-t scroll-no-margin">
				<div class="form" id="form-tb">
				<script id="form-tmpl" type="text/html">
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
									<th><span class="required">*</span>公司名称:</th>
									<td><div class="ipt-case">
										<c:if test="${user.superior}" var="superior">
											<input name="param.orgCode" data-name="param.orgName" data-label="公司名称" data-rule="notnull" value="{{orgCode}}" 
												type="hidden" data-source-type="tree" data-source-id="bas_org" data-show-name="{{orgName}}"/>
										</c:if>
										<c:if test="${!superior}">
											<input type="hidden" name="param.orgCode" data-label="主管单位" data-rule="notnull" value="${user.orgCode }"/>
											<input name="param.orgName" data-label="主管单位" data-rule="notnull" value="${user.orgName }" readonly/>
										</c:if>
										</div></td>	
									<th><span class="required">*</span>试运投产项目名称:</th>
									<td>
										<div class="ipt-case">
											<input name="param.workName" data-label="试运投产项目名称" data-rule="notnull" maxlength="50" value="{{workName}}"/>
										</div>
									</td>
								</tr>
								<tr>
									<th>作业级别:</th>
									<td><div class="ipt-case">
											<input name="param.workLevel" data-label="作业级别" maxlength="10" data-rule="" value="{{workLevel}}">
										</div></td>		
									<th><span class="required">*</span>工程概况:</th>
									<td><div class="ipt-case"><textarea name="param.summary" data-label="工程概况"  data-rule="notnull" maxlength="500">{{summary}}</textarea>
										</div></td>
								</tr>
								<tr>						
									<th><span class="required">*</span>计划实施时间:</th>
									<td><div class="ipt-case"><input name="param.executeDateStr" value="{{executeDateStr}}" data-label="计划实施时间" data-rule="notnull" x-time <c:if test="${!superior}"> value="${nextMonthBegin }" data-min="${nextMonthBegin }" data-max="${nextMonthEnd }" </c:if> />
										</div></td>
									<th>作业审批人:</th>
									<td><div class="ipt-case">
											<input name="param.examiner" data-label="作业审批人" maxlength="10" data-rule="" value="{{examiner}}">
										</div></td>
								</tr>
								<tr>
									<th>作业复核人:</th>
									<td><div class="ipt-case">
											<input name="param.checker" data-label="作业复核人" maxlength="10" data-rule="" value="{{checker}}">
										</div></td>		
									<th>现场指挥:</th>
									<td><div class="ipt-case">
											<input name="param.director" data-label="现场指挥" maxlength="10" data-rule="" value="{{director}}">
										</div></td>									
								</tr>
							</table>
						</div>
					</div>
					</script>
				</div>
			</div>
			<div class="layout layout-search-result-t-sub">
				<div class="btn-grp fieldset-sub txt-ac">
					<button class="btn btn-green"><span class="btn-otr"><span class="btn-inr">保存</span></span></button>
					<button class="btn btn-gray shim" type="button" data-href="${siteUrl }/project/producePlan/listPage"><span class="btn-otr"><span class="btn-inr">返回</span></span></button>
				</div>
			</div>
		</div>
	</form>
</body>

<%@include file="../../inc/footer.jsp"%>
<script src="${siteUrl }/js/project/producePlan/producePlan-edit.js"></script>
</html>