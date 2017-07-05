package com.gsafety.starscream.constant;

/**
 * 编码常量
 * @author chenwenlong
 *
 */
public class CodeConstant {

	//正常编码
	public static final int SUCCESS_CODE = 200;
	//用户模块错误编码
	public static final int ERROR_NOT_LOGIN = 601;
	public static final String ERROR_NOT_LOGIN_STR = "用户未登陆！";
	
	//没有权限编码
	public static final int ERROR_NO_AUTHORITY = 602;
	public static final String ERROR_NO_AUTHORITY_STR = "用户访问权限受限，请联系系统管理员！";
	
	//参数错误编码
	public static final int ERROR_PARAM = 603;
	public static final String ERROR_PARAM_STR = "参数不正确！";
	
	//用户账号其他位置登录
	public static final int ERROR_KICK_OUT = 609;
	public static final String ERROR_KICK_OUT_STR = "用户账户在其他地方登录！";
	
	//统一删除标识
	public static final String DELETE_FLAG = "1";
	public static final String NOT_DELETE_FLAG = "0";
	
	//统一提示标识 
	public static final String FORM_SAVE_SUCCESS = "保存成功";
	public static final String FORM_SAVE_FAIL = "保存失败";
	public static final String FORM_DELETE_SUCCESS = "删除成功";
	public static final String FORM_DELETE_FAIL = "删除失败";
	public static final String FORM_UPDATE_SUCCESS = "更新成功";
	public static final String FORM_UPDATE_FAIL = "更新失败";
	public static final String OPERATE_SUCCESS = "操作成功";
	public static final String OPERATE_FAIL = "操作失败";
}
