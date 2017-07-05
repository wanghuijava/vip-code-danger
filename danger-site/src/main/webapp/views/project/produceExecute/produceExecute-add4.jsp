<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<%@include file="../../inc/header.jsp"%>
</head>
<body>

	<form id="add-form" action="/project/produceExecute/save">
		<div class="layout layout-full">
			<div class="layout layout-search">
				<div class="g-tabs">
					<a href="javascript:;" class="active">今日作业录入</a>
				</div>
			</div>
			<div x-scrollable
				class="layout layout-search-result-t scroll-no-margin">
				<div class="form">
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
											<input type="hidden" name="param.orgCode" data-label="主管单位" data-rule="notnull" value="${producePlan.orgCode }"/>
											<input name="param.orgName" data-label="主管单位" data-rule="notnull" value="${producePlan.orgName }" readonly/>
										</div></td>	
								</tr>
								<tr>
									<th><span class="required">*</span>试运投产项目名称:</th>
									<td>
										<div class="ipt-case">
											<input name="param.workPlanId" value="${producePlan.id}" type="hidden"  data-rule="notnull" />
											<input name="param.workName" value="${producePlan.workName}" readonly  />
										</div>
									</td>
									<th>作业级别:</th>
									<td><div class="ipt-case">
											<input name="param.workLevel" data-label="作业级别" maxlength="10" data-rule="" value="${producePlan.workLevel }">
										</div></td>		
								</tr>
								<tr>
									<th>作业开始时间:</th>
									<td><div class="ipt-case">
											<input name="param.beginTime" data-label="作业开始时间" maxlength="20" data-rule="">
										</div></td>		
									<th>作业关闭时:</th>
									<td><div class="ipt-case">
											<input name="param.endTime" data-label="作业关闭时间" maxlength="20" data-rule="">
										</div></td>									
								</tr>
								<tr>
									<th><span class="required">*</span>工程概况:</th>
									<td><div class="ipt-case"><textarea name="param.summary" data-label="工程概况" maxlength="500">${producePlan.summary}</textarea>
										</div></td>
									<th>是否进行方案学习交底:</th>
									<td><div class="ipt-case">
											<input name="param.learnFlag" data-label="是否进行方案学习交底" maxlength="2" data-rule="">
										</div></td>
								</tr>
								<tr>
									<th>作业审批人:</th>
									<td><div class="ipt-case">
											<input name="param.examiner" data-label="作业审批人" maxlength="10" data-rule="" value="${producePlan.examiner }">
										</div></td>
									<th>作业复核人:</th>
									<td><div class="ipt-case">
											<input name="param.checker" data-label="作业复核人" maxlength="10" data-rule="" value="${producePlan.checker }">
										</div></td>
								</tr>
								<tr>		
									<th>现场指挥:</th>
									<td><div class="ipt-case">
											<input name="param.director" data-label="现场指挥" maxlength="10" data-rule="" value="${producePlan.director }">
										</div></td>		
									<th><span class="required">*</span>作业进展:</th>
									<td><div class="ipt-case">
											<select name="param.executeState" data-label="作业进展" data-rule="notnull">
												<option value="">--请选择--</option>
												<option value="未实施">未实施</option>
												<option value="实施中">实施中</option>
												<option value="已完成">已完成</option>
											</select>
										</div></td>									
								</tr>
								<tr>		
									<th>备注:</th>
									<td><div class="ipt-case">
											<textarea name="param.notes" data-label="备注" maxlength="500"></textarea>
										</div></td>		
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="layout layout-search-result-t-sub">
				<div class="btn-grp fieldset-sub txt-ac">
					<button class="btn btn-green"><span class="btn-otr"><span class="btn-inr">保存</span></span></button>
					<button class="btn btn-gray shim" type="button" data-href="${siteUrl }/project/produceExecute/listPage"><span class="btn-otr"><span class="btn-inr">返回</span></span></button>
				</div>
			</div>
		</div>
	</form>
</body>

<%@include file="../../inc/footer.jsp"%>
<script src="${siteUrl }/js/project/produceExecute/produceExecute-add.js"></script>
</html>