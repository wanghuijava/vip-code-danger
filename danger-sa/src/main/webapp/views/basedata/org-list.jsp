<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>组织机构管理</title>
		<link rel="stylesheet" href="${adminUrl}/lib/bootstrap/css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" href="${adminUrl}/css/reset.css" />
		<link rel="stylesheet" type="text/css" href="${adminUrl}/css/g-system.css" />
		<link rel="stylesheet" type="text/css" href="${adminUrl}/css/table_form.css" />
		<link rel="stylesheet" type="text/css" href="${adminUrl}/css/zTreeStyle.css" />
		<link rel="stylesheet" type="text/css" href="${adminUrl}/css/resourcetype.css" />
		<link rel="stylesheet" type="text/css" href="${adminUrl}/lib/jquery-ui-1.9.0/themes/base/jquery.ui.all.css"/>
		<script type="text/javascript">
			var adminUrl= "${adminUrl}";
		</script>
		
		<script type="text/javascript" src="${adminUrl}/lib/jquery.min.js"></script>
		<script type="text/javascript" src="${adminUrl}/js/jquery.ztree.core-3.5.js"></script>
 		<script type="text/javascript" src="${adminUrl}/js/jquery.ztree.excheck-3.5.js"></script>
		<script type="text/javascript" src="${adminUrl}/lib/jquery-ui-1.9.0/ui/jquery-ui.js"></script>
		
		<script type="text/javascript" src="${adminUrl}/js/autoSelect.js"></script>
		<script type="text/javascript" src="${adminUrl}/js/basedata/org.js"></script>
	</head>
<body>
	<table border=0 align=left>
		<tr>
			<td width=280px align=left valign=top
				style="BORDER-RIGHT: #999999 1px dashed">
				<div>
					<span> <input type="text" id="orgName"
						style="margin-top: 10px; margin-bottom: 0;" />
					</span> <span id="btnSearch" style="cursor: hand;"> <img
						src="${adminUrl}/images/search.png"
						style="width: 32px; height: 32px; margin-top: 10px; margin-bottom: 0;" />
					</span>
				</div>
				<div class="zTreeDemoBackground left">
					<ul id="treeOrg" class="ztree"
						style="overflow: auto; height: 500px"></ul>
				</div>
			</td>
			<td width=770px align=left valign=top>
				<div class="content-menu ib-a blue line-x">
					<span></span><a href="javascript:;" class="on" data="edit"> <em>编辑组织机构</em></a>
					<span>|</span><a href="javascript:;" data="add"> <em>添加组织机构</em></a>
					<span>|</span><a href="javascript:;" data="delete" > <em>删除组织机构</em></a>
				</div>
				<div class="main-content">
					<div class="pad-10" id="edit">
						<div class="explain-col" style="display: none">温馨提示:</div>
						<div class="bk10"></div>
						<div class="col-tab">
							<ul class="tabBut cu-li">
								<li id="tab_setting_1" class="on">编辑选项</li>
							</ul>
							<div id="div_setting_1" class="contentList pad-10">
								<form id="editOrgForm" class="form-horizontal"
									action="${adminUrl}/admin/basedata/org/update"
									method="post">
									<div class="control-group">
										
										<label class="control-label">所属行政区划</label>
										<div class="controls">
											<select class="span2" name="parentDistrict"
												id="parentDistrict">
												<option value="0"
													<c:if test="${empty districtCode}">selected</c:if>>请选择所属行政区划</option>
												<!-- 一级节点 -->
												<c:forEach var="select" items="${districtList}">
													<option value="${select.distCode}"
														<c:if test="${districtCode == select.distCode}">selected</c:if>>${select.distName}</option>
													<!-- 二级节点 -->
													<c:if test="${!empty select.children}">
														<c:forEach var="selectChild1" items="${select.children}"
															varStatus="childIndex1">
															<c:choose>
																<c:when
																	test="${childIndex1.index == fn:length(select.children) - 1}">
																	<option value="${selectChild1.distCode}"
																		<c:if test="${districtCode == selectChild1.distCode}">selected</c:if>>&nbsp;└
																		${selectChild1.distName}</option>
																</c:when>
																<c:otherwise>
																	<option value="${selectChild1.distCode}"
																		<c:if test="${districtCode == selectChild1.distCode}">selected</c:if>>&nbsp;├
																		${selectChild1.distName}</option>
																</c:otherwise>
															</c:choose>
															<!-- 三级节点 -->
															<c:if test="${!empty selectChild1.children}">
																<c:forEach var="selectChild2"
																	items="${selectChild1.children}"
																	varStatus="childIndex2">
																	<c:choose>
																		<c:when
																			test="${childIndex2.index == fn:length(selectChild1.children) - 1}">
																			<option value="${selectChild2.distCode}"
																				<c:if test="${districtCode == selectChild2.distCode}">selected</c:if>>&nbsp;&nbsp;&nbsp;&nbsp;└
																				${selectChild2.distName}</option>
																		</c:when>
																		<c:otherwise>
																			<option value="${selectChild2.distCode}"
																				<c:if test="${districtCode == selectChild2.distCode}">selected</c:if>>&nbsp;&nbsp;&nbsp;&nbsp;├
																				${selectChild2.distName}</option>
																		</c:otherwise>
																	</c:choose>
																</c:forEach>
															</c:if>
														</c:forEach>
													</c:if>
												</c:forEach>
											</select>
											<span id="parentDistrictSpan" class="help-inline" style="display:none;">
												<div  class="onShow" >选择行政区划</div>
											</span>
										</div>
									</div>
									
									<div class="control-group">
										<label class="control-label">组织机构名称:</label>
										<div class="controls">
											<input type="text" id="orgEditName" name="orgName"
												value="" maxlength="20" /> 
											<span id="orgEditNameSpan" class="help-inline" style="display:none;">
												<div  class="onShow" >请输入组织机构名称</div>
											</span>
											<input type="hidden" id="orgEditCode" name="orgCode" value=""/>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">组织机构简称:</label>
										<div class="controls">
											<input type="text" id="orgEditShortName" name="orgShortName"
												value="" maxlength="20" /> 
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">上级组织机构名称:</label>
										<div class="controls">
											<span class="input-edit uneditable-input"></span>
											<input type="hidden" id="editparentCode" name="parentCode"
												value="" maxlength="20" />
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">组织机构类型:</label>
										<div class="controls">
											<select class="span2" id="editTypeCode" name="typeCode">
												<option value="0">下级</option>
												<option value="1">上级</option>
											</select>
										</div>
									</div>
									<!-- 
									<div class="control-group">
										<label class="control-label">选择机构图标:</label>
										<div class="controls">
											<input type="file" name="orgIcon"/>
										</div>
									</div>
									<div class="zTreeDemoBackground left">
											<ul id="treeOrg1" class="ztree"
												style="overflow: auto; height: 500px"></ul>
									</div>	
									-->
								
									<div class="control-group">
										<label class="control-label">组织机构维护:</label> 
										<div class="controls left">
											<input type="hidden" id="chargeOrg" name="chargeOrg" />
											<input type="text" id="chargeOrgName" name="chargeOrgName" value="" maxlength="20" />
										</div>							
									</div>
									<div id="menuContent" class="menuContent" style="display:none; position: absolute;background-color: white;">
											<ul id="treeDemo" class="ztree" style="margin-top:0; width:100%;"></ul>
									</div>									
									<div class="control-group">
										<label class="control-label">赋予角色:</label>
										<div class="controls">
											<input type="text" id="rolesName"  name="rolesName" maxlength="20" />
											<input type="hidden" id="roleIds"   name="roleIds" maxlength="20" />
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">机构负责人:</label>
										<div class="controls">
											<input type="text" id="principal" name="principal" value="" maxlength="20" />
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">负责人职位:</label>
										<div class="controls">
											<input type="text" id="contactInfo" name="contactInfo" value="" maxlength="20" />
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">负责人电话:</label>
										<div class="controls">
											<input type="text" id="contactTel" name="contactTel" value="" maxlength="20" />
										</div>
									</div>
								<!-- 
									<div class="control-group">
										<label class="control-label">机构传真号码:</label>
										<div class="controls">
											<input type="text" id="fax" name="fax" value="" maxlength="20" />
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">机构地址:</label>
										<div class="controls">
											<input type="text" id="address" name="address" value="" maxlength="20" />
										</div>
									</div>
								 -->
									<div class="control-group">
										<label class="control-label">机构职责描述:</label>
										<div class="controls">
											<textarea id="orgInfo" name="orgInfo" maxlength="100"></textarea>
										</div>
									</div>
								</form>
							</div>
							<div class="saveInfo">
								<button id="editSubmitBtn" class="btn btn-primary" type="button">
									<i class="icon-check icon-white"></i> 保存
								</button>
							</div>
							<input type="hidden" id="submitFlag" value="0" />
						</div>
					</div>


					<div class="pad-10" id="add" style="display: none">
						<div class="explain-col" style="display: none">温馨提示:</div>
						<div class="bk10"></div>
						<div class="col-tab">
							<ul class="tabBut cu-li">
								<li id="tab_setting_1" class="on">添加选项</li>
							</ul>
							<div id="div_setting_1" class="contentList pad-10">
								<form id="addOrgForm" class="form-horizontal"
									action="${adminUrl}/admin/basedata/org/save" method="post">
									<div class="control-group">
										<label class="control-label">所属行政区划</label>
										<div class="controls">
											<select class="span2" name="districtCode" id="districtCode">
												<option value="0"
													<c:if test="${empty districtCode}">selected</c:if>>请选择所属行政区划</option>
												<!-- 一级节点 -->
												<c:forEach var="select" items="${districtList}">
													<option value="${select.distCode}"
														<c:if test="${districtCode == select.distCode}">selected</c:if>>${select.distName}</option>

													<!-- 二级节点 -->
													<c:if test="${!empty select.children}">
														<c:forEach var="selectChild1" items="${select.children}"
															varStatus="childIndex1">
															<c:choose>
																<c:when
																	test="${childIndex1.index == fn:length(select.children) - 1}">
																	<option value="${selectChild1.distCode}"
																		<c:if test="${districtCode == selectChild1.distCode}">selected</c:if>>&nbsp;└
																		${selectChild1.distName}</option>
																</c:when>
																<c:otherwise>
																	<option value="${selectChild1.distCode}"
																		<c:if test="${districtCode == selectChild1.distCode}">selected</c:if>>&nbsp;├
																		${selectChild1.distName}</option>
																</c:otherwise>
															</c:choose>
															<!-- 三级节点 -->
															<c:if test="${!empty selectChild1.children}">
																<c:forEach var="selectChild2"
																	items="${selectChild1.children}"
																	varStatus="childIndex2">
																	<c:choose>
																		<c:when
																			test="${childIndex2.index == fn:length(selectChild1.children) - 1}">
																			<option value="${selectChild2.distCode}"
																				<c:if test="${districtCode == selectChild2.distCode}">selected</c:if>>&nbsp;&nbsp;&nbsp;&nbsp;└
																				${selectChild2.distName}</option>
																		</c:when>
																		<c:otherwise>
																			<option value="${selectChild2.distCode}"
																				<c:if test="${districtCode == selectChild2.distCode}">selected</c:if>>&nbsp;&nbsp;&nbsp;&nbsp;├
																				${selectChild2.distName}</option>
																		</c:otherwise>
																	</c:choose>
																</c:forEach>
															</c:if>
														</c:forEach>
													</c:if>
												</c:forEach>
											</select>
											<span id="districtCodeSpan" class="help-inline" style="display:none;">
												<div  class="onShow" >选择行政区划</div>
											</span>
										</div>
									</div>

									<div class="control-group">
										<label class="control-label">组织机构名称:</label>
										<div class="controls">
											<input type="text" id="orgAddName" name="orgName" value=""
												maxlength="20" /> 
											<span id="orgAddNameSpan" class="help-inline" style="display:none;">
												<div  class="onShow">选择行政区划</div>
											</span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">组织机构简称:</label>
										<div class="controls">
											<input type="text" id="orgAddShortName" name="orgShortName" value=""
												maxlength="20" /> 
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">上级组织机构名称:</label>
										<div class="controls">
											<span class="input-add uneditable-input"></span>
											<input type="hidden" id="addparentCode" name="parentCode"
												value="" maxlength="20" />
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">组织机构类型:</label>
										<div class="controls">
											<select class="span2" id="addTypeCode" name="typeCode">
												<option value="0">下级</option>
												<option value="1">上级</option>
											</select>
										</div>

									</div>
									<!-- 
									<div class="control-group">
										<label class="control-label">选择机构图标:</label>
										<div class="controls">
											<input type="file" name="orgIcon"/>
										</div>
									</div>
									 -->
									<div class="control-group">
										<label class="control-label">组织机构维护:</label> 
										<div class="controls left">
											<input type="hidden" id="chargeOrgadd" name="chargeOrg" />
											<input type="text" id="chargeOrgNameadd" name="chargeOrgName" value="" maxlength="20" />
										</div>							
									</div>
									<div id="menuContentadd" class="menuContent" style="display:none; position: absolute;">
											<ul id="treeDemoadd" class="ztree" style="margin-top:0; width:160px;"></ul>
									</div>
									<div class="control-group">
										<label class="control-label">赋予角色:</label>
										<div class="controls">
											<input type="text" id="rolesName"  name="rolesName" maxlength="20" />
											<input type="hidden" id="roleIds"   name="roleIds" maxlength="20" />
										</div>
									</div>								 
									<div class="control-group">
										<label class="control-label">机构负责人:</label>
										<div class="controls">
											<input type="text" id="principal" name="principal" value="" maxlength="20" />
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">负责人职位:</label>
										<div class="controls">
											<input type="text" id="contactInfo" name="contactInfo" value="" maxlength="20" />
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">负责人电话:</label>
										<div class="controls">
											<input type="text" id="contactTel" name="contactTel" value="" maxlength="20" />
										</div>
									</div>
									<!-- 
									<div class="control-group">
										<label class="control-label">机构传真号码:</label>
										<div class="controls">
											<input type="text" id="fax" name="fax" value="" maxlength="20" />
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">机构地址:</label>
										<div class="controls">
											<input type="text" id="address" name="address" value="" maxlength="20" />
										</div>
									</div>
									 -->
									
									<div class="control-group">
										<label class="control-label">机构职责描述:</label>
										<div class="controls">
											<textarea id="orgInfo" name="orgInfo" maxlength="100"></textarea>
										</div>
									</div>
								</form>
							</div>
							<div class="saveInfo">
								<button id="addSubmitBtn" class="btn btn-primary" type="button">
									<i class="icon-check icon-white"></i> 保存
								</button>
							</div>
							<input type="hidden" id="submitFlag" value="0" />
						</div>
					</div>

				</div>
			</td>
		</tr>
	</table>
	
	<div id="showRoleDialog" style="width: 200px; height: 400px;">
		<ul>
			<li id="treeRoleDemo" class="ztree"></li>
		</ul>
	</div>

	<script type="text/javascript" src="${adminUrl}/lib/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${adminUrl}/js/starscream.utils.js"></script>

</body>
</html>