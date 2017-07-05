//组织机构js duxi
//初始化树结构
$(document).ready(function() {
	autoSelect("orgName",adminUrl+"/admin/basedata/org/loadSearchList");
	$.ajax({
		type : "POST",
		url : adminUrl+"/admin/basedata/org/findTree",
		dataType : "json",
		cache : false,
		success : function(data) {
			$.fn.zTree.init($("#treeOrg"), setting, data.response);
			searchTreeNode("");
		}
	});
}).on("click", "#btnSearch", function() {
	searchTreeNode();
}).on("keyup", "#orgName", function() {
	if (event.keyCode == 13) {
		searchTreeNode();
	}
}).on("click","#rolesName",function(){
	var _that = $(this);
	
	var setting = {
		check:{
			enable:true
		},callback:{
			//onCheck:zTreeOnCheck//节点选择事件
		}
	};
		
	//填充数据
	$.ajax({
		type : "GET",
		url : adminUrl+"/admin/role/findRoleTree",
		dataType : "json",
		cache : false,
		success : function(data) {
			json = eval("(" + data.response + ")");
			$.fn.zTree.init($("#treeRoleDemo"), setting, json);
			var treeObj=$.fn.zTree.getZTreeObj("treeRoleDemo");
			treeObj.expandAll(true);
			var roleIds=$.trim(_that.parent().find("input[type='hidden']").val());
			if(roleIds!=''){
				alreadyCheckNodes(_that);
			}
		}
	});
	
	
	$("#showRoleDialog").dialog({
		height:350,
		width:270,
		title:"用户角色",
		model:true,
		buttons:{
			"确定":function(){
				getCheckNodesValue(_that);
			},"取消":function(){
				$("#showRoleDialog").dialog("close");
			}
		}
	});
});

	//选中已有角色
	function alreadyCheckNodes(_that){
		var roleIds = $.trim(_that.parent().find("input[type='hidden']").val());
		var roleIdArray=roleIds.split(",");
		var treeObj=$.fn.zTree.getZTreeObj("treeRoleDemo");
		var nodes = treeObj.getNodes();
		
		if(nodes!=null && roleIdArray!=null){
			for(var i=0;i<nodes.length;i++){
				for(var j=0;j<roleIdArray.length;j++){
					if(nodes[i].id==roleIdArray[j]){
						var tId = nodes[i].tId;
						var node = treeObj.getNodeByTId(tId);
						treeObj.checkNode(node, true, true);
						break;
					}
				}
			}
		}
	}

	/**
	 *  确定后，把数据显示在文本框中
	 */
	function getCheckNodesValue(_that){
		var zTree=$.fn.zTree.getZTreeObj("treeRoleDemo");
		var nodes=zTree.getCheckedNodes();
		var nodeIds="";
		var nodeNames="";
		
		for(var i=0;i<nodes.length;i++){
			var nodeIdValue=nodes[i].id;
			var nodeNameValue=nodes[i].name;
			
			if(i == 0){
				nodeIds = nodeIdValue;
				nodeNames = nodeNameValue;
				
			}else {
				nodeIds +=","+ nodeIdValue;
				nodeNames +=","+ nodeNameValue;
			}
		}
		if(nodeIds==""){
			alert("请至少选择一项角色！")
		}
		
		_that.parent().find("input[type='text']").val(nodeNames);
		_that.parent().find("input[type='hidden']").val(nodeIds);
		
//		$("#rolesName").val(nodeNameTemp);
//		$("#roleIds").val(nodeIdsTemp);
		$("#showRoleDialog").dialog("close");
	}


//处理树的属性
var setting = {
	data : {
		key : {
			name : "orgName"
		}
	},
	callback : {
		onClick : function(event, treeId, treeNode) {
				editOrg(treeNode.orgCode);
			/*$.ajax({
				type : "GET",
				url : adminUrl+"/admin/basedata/org/update/"+treeNode.orgCode,
				dataType : "json",
				cache : false,
				success : function(data) {
					var json = eval("(" + data.response + ")");
					dataBind(json);
					$(".content-menu").find("a").first().click();
				}
			});*/
		}
	}
};

//查询树节点
function searchTreeNode() {
	var key = $("#orgName").val();
	var zTree = $.fn.zTree.getZTreeObj("treeOrg");
	var note;
	if ("" == key || null == key) {
		node = zTree.getNodes()[0];
	} else {
		var nodes = zTree.getNodesByParamFuzzy("orgName", key, null);
		node = nodes[0];
	}
	zTree.selectNode(node);
	zTree.expandAll(true);
	if (null == node || "" == node) {
		alert("没有找到！！！");
	} else {
		editOrg(node.orgCode);
	}
}
//根据选中的节点编辑树
function editOrg(code) {
	$.ajax({
		type : "GET",
		url : adminUrl+"/admin/basedata/org/edit/"+code,
		dataType : "json",
		cache : false,
		success : function(data) {
			var json = data.response;
			dataBind(json);
			$(".content-menu").find("a").first().click();
		}
	});
}
//将公共的部分提出来. zzj
function dataBind(json){
		$("#editOrgForm #parentDistrict").val(json.org.districtCode);
		$("#editOrgForm #orgEditName").val(json.org.orgName);
		$("#editOrgForm #orgEditShortName").val(json.org.orgShortName);
		$("#editOrgForm #editTypeCode").val(json.org.typeCode);
		$("#editOrgForm #orgEditCode").val(json.org.orgCode);
		$("#addOrgForm #addparentCode").val(json.org.orgCode);
		if(json.org.parentCode){
			$("#editOrgForm #editparentCode").val(json.org.parentCode);
		}

		$("#editOrgForm #principal").val(json.org.principal);
		$("#editOrgForm #contactInfo").val(json.org.contactInfo);
		$("#editOrgForm #contactTel").val(json.org.contactTel);
		$("#editOrgForm #fax").val(json.org.fax);
		$("#editOrgForm #address").val(json.org.address);
		$("#editOrgForm #orgInfo").val(json.org.orgInfo);
		if(json.roleIdAndName){
			$("#editOrgForm #roleIds").val(json.roleIdAndName[0]);
			$("#editOrgForm #rolesName").val(json.roleIdAndName[1]);
		}
		
		$(".input-add").html(json.org.orgName);
		$(".input-edit").html(json.parentName);
		
		if(json.org.chargeOrg!=null&&json.org.chargeOrg!=""){			
			$.ajax({
				type : "GET",
				url : adminUrl+"/admin/basedata/org/edit/"+json.org.chargeOrg,
				dataType : "json",
				cache : false,
				success : function(data) {
					var myjson = data.response;
					$("#chargeOrg").val(myjson.org.orgCode);
					$("#chargeOrgName").val(myjson.org.orgName);
				}
			});				
		}else{
				$("#chargeOrg").val(json.org.chargeOrg);
				$("#chargeOrgName").val(json.org.orgName);			
		}
}

//切换按钮
$(function() {
	$(".content-menu")
			.on(
					"click",
					"a",
					function() {
						$(this).addClass("on").siblings().removeClass("on");
						var tab = $(this).attr("data");
						if (tab == "delete") {
							var id = $("#orgEditCode").val();
							if (null == id || "" == id) {
								alert("请选中删除项！");
								return;
							} else {
								
								var parentCode = $("#editparentCode").val();
								if(parentCode==""){
									alert("不能删除根节点",1);
								}else {
									var r=confirm("确认删除吗");
									if(r==true){
										$.ajax({
											type : "GET",
											url : adminUrl+"/admin/basedata/org/delete/"+id,
											dataType : "json",
											cache : false,
											success : function(data) {
												var flag=data.flag;
												if(flag=='0'){
													alert("该机构下有通讯录用户不能删除");
												}else{
													window.location.reload();
												}
											}
										});	
										
									}
									/*alert(
											"确认删除该节点及其所有子节点？",
											2,
											"删除节点",
											function() {
												
												$.ajax({
													type : "GET",
													url : adminUrl+"/admin/basedata/org/delete/"+id,
													dataType : "json",
													cache : false,
													success : function(data) {
														var flag=data.flag;
														if(flag=='0'){
															alert("该机构下有通讯录用户不能删除");
														}else{
															
														}
													}
												});	
											});*/
									
									
								}
							}
						} else {
							$("#" + tab).show().siblings().hide();
						}
					})
});
//提交编辑表单	
$("#editSubmitBtn").live("click", function() {
	$("#submitFlag").val(1);
	if($("#parentDistrict").val()=="0"){
		$("#parentDistrictSpan").css("display","inline");
		return false;
	}
	if($("#orgEditName").val()==""){
		$("#orgEditNameSpan").css("display","inline");
		return false;
	}
	$("#editOrgForm").submit();
});
//提交添加表单
$("#addSubmitBtn").live("click", function() {
	$("#submitFlag").val(1);
	if($("#districtCode").val()=="0"){
		$("#districtCodeSpan").css("display","inline");
		return false;
	}
	if($("#orgAddName").val()==""){
		$("#orgAddNameSpan").css("display","inline");
		return false;
	}
	$("#addOrgForm").submit();
});

//获得焦点隐藏提示span
//$("#parentDistrict").live("change",function(){
//	$("#editdistrictSpan").css("display","none");
//});
//$("#addTypeCode").live("focus",function(){
//	$("#addTypeCodeSpan").css("display","none");
//});
//$("#addTypeName").live("focus",function(){
//	$("#addTypeNameSpan").css("display","none");
//});
$(document).on("focus","input",function(){
	$("#"+$(this).attr("id")+"Span").css("display","none");
});
$(document).on("change","select",function(){
	$("#"+$(this).attr("id")+"Span").css("display","none");
});

$("#chargeOrgName").live("focus",function(){
	showMenu();
});

$("#chargeOrgNameadd").live("focus",function(){
	showMenuadd();
});

function showMenu(){
	$.ajax({
		type : "POST",
		url : adminUrl+"/admin/basedata/org/findTree",
		dataType : "json",
		cache : false,
		success : function(data) {
			$.fn.zTree.init($("#treeDemo"), setting2, data.response);
		}
	});
	var orgObj = $("#chargeOrgName");
	var orgOffset = $("#chargeOrgName").offset();
	$("#menuContent").css({left:orgOffset.left + "px", top:orgOffset.top + orgObj.outerHeight() + "px"}).slideDown("fast");

	$("body").bind("mousedown", onBodyDown);
}
function showMenuadd(){
	$.ajax({
		type : "POST",
		url : adminUrl+"/admin/basedata/org/findTree",
		dataType : "json",
		cache : false,
		success : function(data) {
			$.fn.zTree.init($("#treeDemoadd"), setting2, data.response);
		}
	});
	var orgObj = $("#chargeOrgNameadd");
	var orgOffset = $("#chargeOrgNameadd").offset();
	$("#menuContentadd").css({left:orgOffset.left + "px", top:orgOffset.top + orgObj.outerHeight() + "px"}).slideDown("fast");

	$("body").bind("mousedown", onBodyDown);
}

var setting2 = {
		view: {
			dblClickExpand: false
		},
		data : {
			key : {
				name : "orgName"
			}
		},
		callback: {
			onClick: onClick2
		}
	};
	
	function onClick2(e, treeId, treeNode) {
		$.ajax({
			type : "GET",
			url : adminUrl+"/admin/basedata/org/edit/"+treeNode.orgCode,
			dataType : "json",
			cache : false,
			success : function(data) {
				var json = data.response;			
				$("#chargeOrg").val(treeNode.orgCode);
				var orgObj = $("#chargeOrgName");
				orgObj.attr("value", json.org.orgName);
				$("#chargeOrgadd").val(treeNode.orgCode);
				$("#chargeOrgNameadd").attr("value", json.org.orgName);				
				hideMenu();
				hideMenuadd();
			}
		});
	}

	function hideMenuadd() {
		$("#menuContentadd").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	
	function hideMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
			hideMenu();
		}
	}
