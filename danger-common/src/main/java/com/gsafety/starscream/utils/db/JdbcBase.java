package com.gsafety.starscream.utils.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import oracle.jdbc.internal.OracleTypes;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.gsafety.starscream.utils.RoseContextUtils;

/**
 * Jdbc SQL操作
 * @author chenwenlong
 *
 */
@Component
public class JdbcBase {

	private static JdbcTemplate jdbcTemplate ;
	
	/**
	 * 获取JdbcTemplate实例
	 * @return
	 */
	protected static JdbcTemplate getJdbcTemplate(){
		if(jdbcTemplate==null) {
			jdbcTemplate = RoseContextUtils.getBean("jdbcTemplate");
		}
		return jdbcTemplate;
	}
	
	/**
	 * 通过SQL查询，返回指定类型对象
	 *   可以是Integer,String,Map...
	 * @param sql
	 * @param t
	 * @return
	 */
	public static <T>T queryObjectBySQL(String sql,Class<T> t){
		return getJdbcTemplate().queryForObject(sql, t);
	}
	
	/**
	 * 通过SQL查询，查询map列表
	 * @param sql
	 * @return
	 */
	public static List<Map<String,Object>> queryMapListBySQL(String sql){
		return getJdbcTemplate().queryForList(sql);
	}
	
	public static String queryStrByProcedure(final String call,final String key){
		
		return getJdbcTemplate().execute(new CallableStatementCreator() {
			
			@Override
			public CallableStatement createCallableStatement(Connection conn)
					throws SQLException {
				CallableStatement cs = conn.prepareCall(call);
				cs.setString(1, key);
				cs.registerOutParameter(2, OracleTypes.VARCHAR);
				
				return cs;
			}
		}, new CallableStatementCallback<String>() {

			public String doInCallableStatement(CallableStatement cs)
					throws SQLException, DataAccessException {
				cs.execute();
				return cs.getString(2);
			}
		});
		
	}
	
	
}
