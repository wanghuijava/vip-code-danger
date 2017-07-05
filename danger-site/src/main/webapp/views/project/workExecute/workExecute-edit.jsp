<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<%@include file="../../inc/header.jsp"%>
</head>
<body>

	<form id="edit-form" action="/project/workExecute/save">
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
									<th><span class="required">*</span>日期:</th>
									<td><div class="ipt-case"><input name="param.executeDateStr" value="${todayDate}" readonly  data-rule="notnull" />
										</div></td>
									<th><span class="required">*</span>公司名称:</th>
									<td><div class="ipt-case">
										<c:if test="${user.superior}" var="superior">
											<input name="param.orgCode" data-name="param.orgName" data-label="公司名称" data-rule="notnull" value="{{param.orgCode}}" 
												type="hidden" data-source-type="tree" data-source-id="bas_org" data-source-show="{{param.orgName}}"/>
										</c:if>
										<c:if test="${!superior}">
											<input type="hidden" name="param.orgCode" data-label="主管单位" data-rule="notnull" value="${user.orgCode }"/>
											<input name="param.orgName" data-label="主管单位" data-rule="notnull" value="${user.orgName }" readonly/>
										</c:if>
										</div></td>	
								</tr>
								<tr>
									<th><span class="required">*</span>危险作业项目名称:</th>
									<td>
										<div class="ipt-case">
											<input name="param.workPlanId" value="{{workPlanId}}" type="hidden"  data-rule="notnull" />
											<input name="param.workName" value="{{workName}}" readonly  />
										</div>
									</td>
									<th>作业级别:</th>
									<td><div class="ipt-case">
											<input name="param.workLevel" data-label="作业级别" maxlength="10" data-rule="" value="{{workLevel}}" />
										</div></td>		
								</tr>
								<tr>
									<th>作业开始时间:</th>
									<td><div class="ipt-case">
											<input name="param.beginTime" data-label="作业开始时间" maxlength="20" data-rule="" value="{{beginTime}}">
										</div></td>		
									<th>作业关闭时:</th>
									<td><div class="ipt-case">
											<input name="param.endTime" data-label="作业关闭时间" maxlength="20" data-rule="" value="{{endTime}}">
										</div></td>									
								</tr>
								<tr>
									<th><span class="required">*</span>工程概况:</th>
									<td><div class="ipt-case"><textarea name="param.summary" data-label="工程概况" maxlength="500">{{summary}}</textarea>
										</div></td>
									<th>是否进行方案学习交底:</th>
									<td><div class="ipt-case">
											<input name="param.learnFlag" data-label="是否进行方案学习交底" maxlength="2" data-rule="" value="{{learnFlag}}">
										</div></td>
								</tr>
								<tr>
									<th>作业审批人:</th>
									<td><div class="ipt-case">
											<input name="param.examiner" data-label="作业审批人" maxlength="10" data-rule="" value="{{examiner}}">
										</div></td>
									<th>作业复核人:</th>
									<td><div class="ipt-case">
											<input name="param.checker" data-label="作业复核人" maxlength="10" data-rule="" value="{{checker}}">
										</div></td>
								</tr>
								<tr>		
									<th>现场指挥:</th>
									<td><div class="ipt-case">
											<input name="param.director" data-label="现场指挥" maxlength="10" data-rule="" value="{{director}}">
										</div></td>		
									<th><span class="required">*</span>作业进展:</th>
									<td><div class="ipt-case">
											<select name="param.executeState" data-label="作业进展" data-rule="notnull">
												<option value="">--请选择--</option>
												<option value="未实施" {{if executeState == '未实施'}}selected{{/if}}>未实施</option>
												<option value="实施中" {{if executeState == '实施中'}}selected{{/if}}>实施中</option>
												<option value="已完成" {{if executeState == '已完成'}}selected{{/if}}>已完成</option>
											</select>
										</div></td>									
								</tr>
								<tr>		
									<th>备注:</th>
									<td><div class="ipt-case">
											<textarea name="param.notes" data-label="备注" maxlength="500">{{notes}}</textarea>
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
					<button class="btn btn-gray shim" type="button" data-href="${siteUrl }/project/workExecute/listPage"><span class="btn-otr"><span class="btn-inr">返回</span></span></button>
				</div>
			</div>
		</div>
	</form>
</body>

<%@include file="../../inc/footer.jsp"%>
<script src="${siteUrl }/js/project/workExecute/workExecute-edit.js"></script>
</html>