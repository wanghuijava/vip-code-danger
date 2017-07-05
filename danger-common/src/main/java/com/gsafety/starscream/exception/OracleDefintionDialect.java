package com.gsafety.starscream.exception;

import java.sql.Types;

import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.type.StandardBasicTypes;

/**
 * 定义数据库的方言
 * 由于oracle的方言不识别Nvarchar，
 * 所以自己定义方言集成oracle的方言，
 * @author raomengwen
 * @date 2015-5-15
 */ 
public class OracleDefintionDialect extends Oracle10gDialect {
	
	public OracleDefintionDialect(){
		super() ; 
		registerHibernateType(Types.NVARCHAR,StandardBasicTypes.STRING.getName());
		
	}

}
