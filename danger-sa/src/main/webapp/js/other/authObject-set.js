$(document).ready(function() {
	var setting = {
		callback : {
			onClick : function(event, treeId, treeNode) {
				var orgName = treeNode.name;
				var orgCode = treeNode.id;
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
			json = JSON.parse(data.response);;
			$.fn.zTree.init($("#orgTree"), setting, json);
			var treeObj = $.fn.zTree.getZTreeObj("orgTree");
			var node = treeObj.getNodes()[0].children[0];
			if(node){
				treeObj.selectNode(node);
				treeObj.expandNode(node,true);
			}
		}
	});
})


function queryUserByOrgCode(orgCode) {
	$.ajax({
				type : "GET",
				data : {
					'orgCode' : orgCode
				},
				url : adminUrl+"/admin/other/authObject/userList",
				dataType : "json",
				cache : false,
				success : function(data) {
					json = data.response;
					var html = "";
					var actionName = "";
					if (json != null && json.length > 0) {
						for ( var i = 0; i < json.length; i++) {
							actionName = "打开";
							var sysUser = json[i].sysUser;
							if ("1" == sysUser.typeCode) {
								sysUser.typeCode = "上级用户";
							} else if ("0" == sysUser.typeCode) {
								sysUser.typeCode = "下级用户";
							} else {
								sysUser.typeCode = "超级用户";
							}

							if (1 == json[i].authority) {
								actionName = "关闭";
							}
							html += "<tr>"
									+ "<td>"
									+ sysUser.username
									+ "</td>"
									+ "<td>"
									+ sysUser.typeCode
									+ "</td>"
									+ "<td>"
									+ sysUser.orgName
									+ "</td>"
									+ "<td>"
									+ json[i].authorityName
									+ "</td>"
									+ "<td>"
									+ json[i].updateTime
									+ "</td>"
									+ "<td>"
									+ "<a class='btn btn-small btn-danger delete' onclick='changeAuthObject("
									+ "\""+sysUser.username+ "\","+ json[i].authority 
									+ ")'  href='javascript:void(0);'>"
									+actionName
									+"</a>"
									+ "</td>" + "</tr>"
						}
						$("#mytbody").html(html);
					} else {
						$("#mytbody").html("");
					}
				}
			});
}

//注销系统用户
function changeAuthObject(username, authority) {
	var actionName = "打开";
	if (1 == authority) {
		actionName = "关闭";
	}
	if (confirm("确定"+actionName+"用户承灾体数据权限吗?")) {
		$.ajax({
			type : "POST",
			data : {
				'username' : username,
				'authority' : authority
			},
			url : adminUrl+"/admin/other/authObject/save",
			dataType : "json",
			success : function(data) {
				var orgCode = $("#orgCode").val();
				$("#mytbody").html("");
				//根据组织机构code查询用户
				queryUserByOrgCode(orgCode);
			}
		});
	}
}
