package com.gsafety.starscream.constant;

/**
 * 基础数据basedata常量
 * @author chenwenlong
 *
 */
public class BasedataConstant {
	
	//项目路径
	public static String CONTEXT_PATH;
	
	//叶子节点 （叶子节点：1，非叶子节点：0）-----------------------------------
	public static final int IS_LEAF = 1;  //基础数据叶子节点数值
	
	public static final int NOT_LEAF = 0; //基础数据非叶子节点数值
	
	//类型代码 （超级用户：9，系统管理员：7，上级用户：1，下级用户：0）---------------
	public static final int TYPE_CODE_SA = 9;    //超级用户
	
	public static final int TYPE_CODE_ADMIN = 7; //系统管理员
	
	public static final int TYPE_CODE_SJ = 1;    //上级用户
	
	public static final int TYPE_CODE_XJ = 0;    //下级用户
	
	//用户状态（正常：1，注销：0）--------------------------------------------
	public static final int USER_STATUS_NOR = 1; //用户正常
	
	public static final int USER_STATUS_OFF = 0; //用户注销
	
	public static final int USER_STATUS_SYS = 2; //通讯录用户转换成系统用户状态
	
	//登录用户信息常量------------------------------------------------------
	public static final String PARAM_TOKEN = "token";     //请求token
	
	//常用分隔符------------------------------------------------------
	public static final String PARAM_SPLIT = ",";     //常用分隔符处理参数数组
	public static final String PARAM_JSONNULL = "{}";     //常用分隔符处理参数数组
	
	//实体转换，json过滤字段--------------------------------------------------
	public static final String[] TOJSON_IGNORE_FIELD = {"parent","children"};
	
	public static final String VO_IGNORE_FIELD = "parent,children";
	

//	 资源------------------------------------------------------------------------
	public static final String ORG = "bas_org";

	public static final String ORGLEVEL = "bas_org_level";

	public static final String DISTRICT = "bas_district";
	public static final String DISTRICT_ALL = "bas_district_all";

	public static final String EVENTTYPE = "bas_event_type";

	public static final String SEX = "dic_sexuality";
	
//	机构====================================================================================
//	行政区划根节点
	public static final String DISTRICT_ROOT_NAME	="杭州市";
	
	//用户组根节点(暂时留用,到时候会删掉)
	public static final String USERGROUP_ROOT="0000";
	
	
// sa事件类型根节点的parentCode================================================================
	public static final String EVENT_TYPE_PARENTCODE = "10000";
	
	
	//统一删除标志
	public static final String DELETE_YES="1";
	public static final String DELETE_NO="0";
	
	//统一阅读标志
	public static final String READ_YES="1";
	public static final String READ_NO="0";
	
//	记录操作日志类型
	public static final String SYSTEM_LOG = "系统日志";
	public static final String OPERATION_LOG = "操作日志";
	
	
	
	
}
