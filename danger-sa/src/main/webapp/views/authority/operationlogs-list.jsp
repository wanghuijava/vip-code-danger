<%@ page pageEncoding= "UTF-8" %>
<%@ page contentType= "text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<!DOCTYPE html >
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>操作日志</title>
	<link rel="stylesheet" href="${adminUrl}/lib/bootstrap/css/bootstrap.min.css" />
	<link href="${adminUrl}/css/reset.css" rel="stylesheet" type="text/css" />
	<link href="${adminUrl}/css/g-system.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		body{padding-left: 10px;}
			
		.btn-group>.btn, .btn-group>.dropdown-menu, .btn-group>.popover { font-size: 12px; }
		.table th, .table td { vertical-align: middle;padding-left:10px;  }
		.nextLevel{padding-left:15px;}
		.queryTab input{
			width: 120px;
		}
	</style>
</head>
<body>
	<div class="content-menu ib-a">
	<!-- 
		<a href="javascript:;" class="on"> <em>操作日志</em></a>
	 -->
	 <form id="list-form" action="${adminUrl}/admin/systemOperation/operation/find"  class="form-inline" >
		<div class="form-group queryTab">
			<table>
				<tr>
					<td>
						<label>按时间查询:</label>
						<input name="param.operationType" type="hidden" value="${operationType }" />	
					</td>
					<td><input id="startTimeStr" type="text" readOnly="readOnly" name="param.startTimeStr" size="10" class="form-control Wdate" 
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${startTimeStr }" 
						placeholder="起始时间" x-time />-
					<input id="endTimeStr" type="text" readOnly name="param.endTimeStr" class="form-control Wdate"  size="10"
						onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startTimeStr\')}'})" value="${endTimeStr }"
					placeholder="终止时间" />
					</td>
					<td><label><button name="query" type="button" class="btn btn-primary" >查询</button></label></td>
				</tr>
			</table>
		</div>
		<div class="main-content">
			<div class="pad-10">
				<table class="table table-hover table-condensed">
	              <thead>
	                <tr>
						<th width="230px">操作名称</th>
						<th width="130px">操作人</th>
						<th width="130px">操作时间</th>
						<th width="230px">操作URL</th>
	                </tr>
	              </thead>
	              
	              <tbody id="list-tb">
		          </tbody>
					<script id="list-tmpl" type="text/html">
					{{each dtoList as item index}}
	              		<tr class="
							{{if index%2==1}}
							even
							{{/if}}
							">
							<td>{{item.funName}}</td>
							<td>{{item.operator}}</td>
							<td>{{item.createTime}}</td>
							<td>{{item.url}}</td>
						</tr>
					{{/each}}
				</script>
					</table>
	            <div>
					<div x-pager class="flr" data-name="page.count" data-count="10"></div>
				</div>
			</div>
			 
		</div>
	 </form>
	</div>
<%@include file="../inc/footer.jsp"%>
<script type="text/javascript" src="${adminUrl}/lib/mydate97/WdatePicker.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
	 
	var tableform=new former($("#list-form"),{
		tid:"list-tmpl"
		,content:"#list-tb"
	},function(){
		//成功回调
	},function(){
		//失败回调
	})
	
	tableform.submit();
	 $("button[name='query']").on("click",function(){
		 tableform.submit();
	 });
 });

</script>
</body>
	</html>
