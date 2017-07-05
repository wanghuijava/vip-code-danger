package com.gsafety.starscream.utils.db;



/**
 * 数据库序列工具类
 * @author chenwenlong
 *
 */
public class SequenceUtils {

	public static String getSequenceVal(String key){ 
		//String sql = "select GETSEQUENCEVAL('"+key+"') from dual";
		String sql = "select nextval('\\'"+key+"\\'') from dual";
		return JdbcBase.queryObjectBySQL(sql, String.class);
//		String sql = "{call GETSEQUENCEVAL(?,?)}";
//		return JdbcBase.queryStrByProcedure(sql,key);
	}
	
	/**
	 * 设置序列
	 * @param key
	 * @return    返回-1表示该序列已存在，返回1表示新建序列成功
	 */
	public static String setSequenceVal(String key){
		String sql = "call SETSEQUENCEVAL('"+key+"')";
		return JdbcBase.queryObjectBySQL(sql, String.class);
	}
}
