<%@page pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<div class="shortcut-user flr" >
     <!-- 用户资料 -->
    <span id="user-info"></span>
	<a href="javascript:;" id="modify-password" ><i class="ibm uicon uicon-pwd" title="修改密码"></i></a>
	<a href="javascript:;" class="ibm logout" title="注销用户" data-role='logout'>
		<i class="ibm"></i>
	</a>
</div>
<div class="hidden" id="modify-panel">
	<form class="modifypwd-form">
		<table class="tb" style="min-width:0;margin-right:50px">
			<colgroup>
				<col width="120">
			</colgroup>
			<tr>
				<th>原密码：</th><td><input id="oldPwd" type="password" data-label="原密码" data-rule="notnull"/><span class="required">*</span></td>
			</tr>
			<tr>
				<th>新密码：</th><td><input id="password"  type="password"  data-rule="notnull"/><span class="required">*</span></td>
			</tr>
			<tr>
				<th>确认密码：</th><td><input id="password2" type="password"  data-rule="notnull"/><span class="required">*</span></td>
			</tr>
			<tr>
				<th></th>
				<td class="txt-ar">
					<button class="modifypwd-cancel-btn" type="button">关闭</button>
					<button class="modifypwd-save-btn" type="button">确定 </button>
				</td>
			</tr>
		</table>
	</form>
</div>

<script type="text/html" id="user-tmpl">
	<strong>
		{{if orgUser!=undefined}}
		{{orgUser.username}}
		{{else}}
		{{
			username
		}}
		{{/if}}
	</strong>
</script>
	<!-- <em>
	{{if orgUser!=undefined}}
		{{orgUser.org.orgName}}
	{{else}}
	{{
		orgName
	}}
	{{/if}}
	</em> -->