<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="header.jsp"%>
</head>
<body>
	<form id="import-form" class="show-body">
		<input type="hidden" name="resourceType" value="${resourceType}">
		<div class="layout layout-full">
			<div x-scrollable
				class="layout layout-search-result-t scroll-no-margin">
                <table class="tb fixedtb">
                    <colgroup>
                        <col width="100">
                    </colgroup>
					<tr>
						<th>模板下载：</th>
						<td>
							<a href="${siteUrl}/resource/downloadTemplate/${resourceType}">${fileName}</a>
						</td>
					</tr>
					<tr>
						<th>文件导入：</th>
						<td><input x-upload data-name="attachId" data-maxlength="1" data-accept="*.xls;" /></td>
					</tr>
					<tr>
						<td colspan="2" id="showMessage" class="required"></td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</body>

<%@include file="footer.jsp"%>

</html>