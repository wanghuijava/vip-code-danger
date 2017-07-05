
$(document).ready(function() {
			$("#searchParam").keydown(function(e){
				if(e.keyCode==13){
					searchNode();
				}
			});
			
			$("#searchParam").change(function(e){
				searchNode();
			});
			
			var roleSetting = {
					check:{
						enable:true
					},callback:{
						onCheck:zTreeOnCheck//节点选择事件
					}
			};
			
			var setting = {
				callback : {
					onClick : function(event, treeId, treeNode) {
						var orgName=treeNode.name;
						var orgCode=treeNode.id;
						$("#orgCode").val(orgCode);
						$("#orgName").val(orgName);
						queryUserByOrgCode(orgCode);
					}
				}
			};

			$.ajax({
				type : "GET",
				url : adminUrl+"/admin/orguser/findOrgTree",
				dataType : "json",
				cache : false,
				success : function(data) {
					json = eval("(" + data.response + ")");
					$.fn.zTree.init($("#treeDemo"), setting, json);
					searchNode();
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
					/*var node = treeObj.getNodes()[0].children[0].children[0];
					if(node){
						treeObj.selectNode(node);
						queryUserByOrgCode(node.id);
					}*/
					//编辑返回时查询原有页面记录
					var backorgCode=$("#backorgCode").val();
					if(backorgCode!=null && backorgCode!=''){
						queryUserByOrgCode(backorgCode);
						var node=treeObj.getNodeByParam("id",backorgCode, null)
						if(node){
							treeObj.selectNode(node);
						}
					}
					treeObj.expandAll(true);
					
				}
			});
			
			
			
			$("#showDialog").dialog({
				height:400,
				width:400,
				title:"添加通讯录用户",
				autoOpen:false,
				model:true,
				resizable:false,
				buttons:{
					"确定":function(){
						addUserValidate();
					},"取消":function(){
						$("#showDialog").dialog("close");
					}//自动创建通讯录用户下级机构并且为叶子节点的机构
					/*,"创建通讯录":function(){
						createOrgUser();
					},"创建登陆用户":function(){
						createSysUser();
					}*/
				}
			});

			$("#addUser").bind("click",function(){
				var orgCode=$("#orgCode").val();
				if(orgCode!=null && orgCode!=""){
					$("#showDialog").dialog("open");
				}else{
					alert("请选择组织机构");
				}
			});
			
			
			$("#username").change(function(){
				var username=$.trim($("#username").val());
				if(username!=null && username!=""){
					$("#usernameValidate").html("*");
				}
			});

			$("#position").change(function(){
				var position=$.trim($("#position").val());
				if(position!=null && position!=""){
					$("#positionValidate").html("*");
				}
			});

			$("#mobileTel").change(function(){
				var mobileTel=$.trim($("#mobileTel").val());
				if(mobileTel!=null && mobileTel!=""){
					$("#mobileTelValidate").html("*");
				}
			});

			$("#officeTel").change(function(){
				var officeTel=$.trim($("#officeTel").val());
				if(officeTel!=null && officeTel!=""){
					$("#officeTelValidate").html("*");
				}
			});
			
			
		}).on("click","#btnSearch",function(){
			searchNode();
		});
		
		
		function searchNode(){
			var searchParam=$.trim($("#searchParam").val());
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var node;
			if(searchParam==''||searchParam==null){
				node = zTree.getNodes()[0].children[0];
			}else {
				var nodes = zTree.getNodesByParamFuzzy("name",searchParam, null);
				node = nodes[0];
			}
			//$("#orgName").val(node.name);
		}

		function zTreeOnCheck(e,treeId,treeNode){
			//alert(treeNode.id);
		}
		
		function addUserValidate(){
			var username=$.trim($("#username").val());
			var orgUserSex=$.trim($("input[name='orgUserSex']").val());
			var position=$.trim($("#position").val());
			var mobileTel=$.trim($("#mobileTel").val());
			var officeTel=$.trim($("#officeTel").val());
			var orgCode=$.trim($("#orgCode").val());
			
			
			
			var username=$.trim($("#username").val());
			var Regx = /^[A-Za-z0-9]+/;
			if(username==null || username=="" || Regx.test(username)){
				$("#usernameValidate").html("请输入有效的用户姓名(中文)");
				$("#username").focus();
				return false;
			}
			
			var position=$.trim($("#position").val());
			if(position==null || position==""){
				$("#positionValidate").html("请输入用户职位");
				$("#position").focus();
				return false;
			}
			
			
			
			var mobileTel=$.trim($("#mobileTel").val());
			if(mobileTel==null || mobileTel==""){
				$("#mobileTelValidate").html("请输入手机号码");
				$("#mobileTel").focus();
				return false;
			}
			
			var reg=new RegExp("^[0-9]*$");
			if(!reg.test(mobileTel) || mobileTel.length!=11){
				$("#mobileTelValidate").html("请正确输入手机号");
				$("#mobileTel").focus();
				return false;
			}
			
			var officeTel=$.trim($("#officeTel").val());
			if(officeTel==null || officeTel==""){
				$("#officeTelValidate").html("请输入办公电话");
				$("#officeTel").focus();
				return false;
			}
			
			if(!reg.test(officeTel)){
				$("#officeTelValidate").html("办公电话请输入数字");
				$("#officeTel").focus();
				return false;
			}
			
			$.ajax({
				type : "POST",
				url : adminUrl+"/admin/orguser/userAdd",
				data:{
					'username':username,
					'orgUserSex':orgUserSex,
					'position':position,
					'mobileTel':mobileTel,
					'officeTel':officeTel,
					'orgCode':orgCode
			    },
				dataType : "json",
				cache : false,
				success : function(data) {
					json = eval("(" + data.response + ")");
					if(json!=null && json[0].success==true){
						$("#showDialog").dialog("close");
						var orgCode=$("#orgCode").val();
						queryUserByOrgCode(orgCode);
						$("#username").val("");
						$("#position").val("");
						$("#mobileTel").val("");
						$("#officeTel").val("");
					}
				}
			});
		}
		
		function createOrgUser(){
			$.ajax({
				type : "POST",
				url : adminUrl+"/admin/orguser/createOrgUser",
				dataType : "json",
				cache : false,
				success : function(data) {
					json = eval("(" + data.response + ")");
					if(json!=null && json[0].success==true){
						alert("通讯录用户创建成功");
					}
				}
			});
		}
		
		function createSysUser(){
			$.ajax({
				type : "POST",
				url : adminUrl+"/admin/orguser/createSysUser",
				dataType : "json",
				cache : false,
				success : function(data) {
					json = eval("(" + data.response + ")");
					if(json!=null && json[0].success==true){
						alert("登陆用户创建成功");
					}
				}
			});
		}
		
		$("#userGroupTreeDialog").dialog({
			height:400,
			width:300,
			title:"用户分组",
			autoOpen:false,
			model:true,
			buttons:{
				"确定":function(){
					getCheckUserGroupValue();
				},"取消":function(){
					$("#userGroupTreeDialog").dialog("close");
				}
			}
		});
		
		function queryUserByOrgCode(orgCode){
			$.ajax({
				type : "GET",
				data:{
					'orgCode':orgCode
			    },
				url : adminUrl+"/admin/orguser/userList",
				dataType : "json",
				cache : false,
				success : function(data) {
					json = eval("(" + data.response + ")");
					var html="";
					if(json!=null && json.length>0){
						for(var i=0;i<json.length;i++){
							if(""==json[i].position||null==json[i].position){
								json[i].position="未填";
							}
							if(""==json[i].mobileTel||null==json[i].mobileTel){
								json[i].mobileTel="未填";
							}
							if(""==json[i].officeTel||null==json[i].officeTel){
								json[i].officeTel="未填";
							}
							if("1"==json[i].status){
								json[i].status="正常";
							}else{
								json[i].status="注销";
							}
							html+="<tr>"
							+"<td>"+json[i].username+"</td>"
							+"<td class='cliptxt' title="+json[i].position+">"+json[i].position+"</td>"
							+"<td>"+json[i].mobileTel+"</td>"
							+"<td>"+json[i].officeTel+"</td>"
							+"<td>"+json[i].status+"</td>"
							+"<td>"
							+"<a class='btn btn-small btn-info' href='"+adminUrl+"/admin/orguser/orgEditPage/"+json[i].id+"'><i class='icon-edit icon-white'></i> 编辑</a>&nbsp;&nbsp;&nbsp;"
							+"<a class='btn btn-small btn-danger delete' onclick='deleteUser("+json[i].id+")'  href='javascript:void(0);'>删除</a>&nbsp;&nbsp;&nbsp;"
							+"<a class='btn btn-small btn-info editUserGroup' href='javaScript:void(0);'  id="+json[i].id+" userName="+json[i].username+"><i class='icon-edit icon-white'></i> 用户分组</a>"
							+"</td><td></td>"
							+"</tr>"
						}
						$("#mytbody").html(html);
					}else{
						$("#mytbody").html("");
					}
					
					$(".editUserGroup").unbind("click").bind("click",function(e){
						e.preventDefault();
						var id = $(this).attr("id");
						var userName =$(this).attr("userName");
						
						$("#userId").val(id);
						//用户组回显
						findGroupByUserId(id);
						
						$("#userNameTxt").val(userName);
						$("#userGroupTreeDialog").dialog("open");
					});
				}
			});
		}
		
		//用户组回显示
		function findGroupByUserId(userId){
			var settingUserGroup = {
					check:{
						enable:true,
						chkboxType: {"Y":"","N":""}
					}
			};
			
			$.ajax({
				type : "GET",
				url : adminUrl+"/admin/dutyLeader/findUserGroup",
				dataType : "json",
				cache : false,
				success : function(data) {
					json = eval("(" + data.response + ")");
					$.fn.zTree.init($("#userGroupTree"), settingUserGroup, json);
					var treeObj = $.fn.zTree.getZTreeObj("userGroupTree");
					treeObj.expandAll(true);
					
					$.ajax({
						type : "POST",
						url : adminUrl+"/admin/orguser/findGroupByUserId",
						data:{"userId":userId},
						dataType : "json",
						cache : false,
						success : function(data) {
							json = eval("(" + data.response + ")");
							if(json!=null && json.length>0){
								var zTree = $.fn.zTree.getZTreeObj("userGroupTree");
								for(var i=0;i<json.length;i++){
									zTree.checkNode(zTree.getNodeByParam("id",json[i], null));
								}
							}
						}
					});
				}
			});
		}
		

		function getCheckUserGroupValue(){
			var zTree=$.fn.zTree.getZTreeObj("userGroupTree");
			var nodes=zTree.getCheckedNodes();
			var nodeIds="";
			var nodeNames="";
			var nodeIdsTemp="";
			var nodeNameTemp="";
			
			for(var i=0;i<nodes.length;i++){
				var nodeIdValue=nodes[i].id;
				var nodeNameValue=nodes[i].name;
				nodeIds+=nodeIdValue+",";
				nodeNames+=nodeNameValue+",";
			}
			
			if(nodeIds!=""){
				nodeIdsTemp=nodeIds.substring(0,nodeIds.length-1);
				nodeNameTemp=nodeNames.substring(0,nodeNames.length-1);
			}
			
			
			var userId=$("#userId").val();
			if(userId==null || userId==""){
				alert("请选择用户");
				return false;
			}
			$("#userGroupTreeDialog").dialog("close");
			$.ajax({
				type : "GET",
				data :{"userId":userId,"userGroupId":nodeIdsTemp},
				url : adminUrl+"/admin/orguser/saveGroupToUser",
				dataType : "json",
				cache : false,
				success : function(data) {
					if(data.code=='200'){
						alert(data.message);
						window.location.reload();
					}
				}
			});
		}

		//删除通讯录用户
		function deleteUser(userId){
			if(confirm("确定删除用户吗?")){
				$.ajax({
					type : "GET",
					url : adminUrl+"/admin/orguser/realDelete/"+userId,
					dataType : "json",
					cache : false,
					success : function(data) {
						json = eval("(" + data.response + ")");
						
						var orgCode=$("#orgCode").val();
						$("#mytbody").html("");
						queryUserByOrgCode(orgCode);
					}
				});
			}
		}