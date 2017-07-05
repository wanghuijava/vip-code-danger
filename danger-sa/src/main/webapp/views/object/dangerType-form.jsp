<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<form id="dangerTypeForm" class="form-horizontal" method="post">
	<div class="control-group">
		<label class="control-label">
			危险源分类编码:
		</label>
		<div class="controls">
			<input type="text" id="dangerTypeId" name="dangerType.id" value="${dangerType.id}" maxlength="20" />
			<input type="hidden" id="hidParentId" name="dangerType.parentId" value="${dangerType.parentId}" maxlength="20" />
		</div>
	</div>

	<div class="control-group">
		<label class="control-label">
			危险源分类名称:
		</label>
		<div class="controls">
			<input type="text" id="dName" name="dangerType.name"
				value="${dangerType.name}" maxlength="20" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">
			上级危险源分类名称:
		</label>
		<div class="controls">
			<span class="input-largeName uneditable-input">
				${parentName}
			</span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">
			备注:
		</label>
		<div class="controls">
			<textarea id="addnotes" data-num-id="user-txt-num"
				name="dangerType.notes" maxlength="200">${dangerType.notes}</textarea>
		</div>
	</div>
	
	<div class="saveInfo">
		<button id="submitBtn" class="btn btn-primary" type="button">
			<i class="icon-check icon-white"></i> 保存
		</button>
	</div>
	
</form>