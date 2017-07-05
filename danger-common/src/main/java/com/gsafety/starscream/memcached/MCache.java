package com.gsafety.starscream.memcached;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gsafety.starscream.utils.file.PropertiesUtils;

/**
 * Memcached客户端
 * @author chenwenlong
 *
 */
public class MCache {

	// 设置数据到缓存失效时间（秒），如：3600 表示1个小时
	// 0表示永久存储（默认期限1个月）
	public static int MEMCACHED_EXPIRE_TIME = 0;

	//启动Memcached功能
	private static String MEMCACHED_SWITCH_ON = "ON";
	private static Properties properties =  new Properties();
	/**
	 * 初始化数据
	 */
//	private static MemcachedClient mc = RoseContextUtils.getBean("memcachedClient");
	private static MemcachedClient mc = null;
	
	private static Logger logger = LoggerFactory.getLogger(MCache.class);

	static{
		properties = PropertiesUtils.getProperties("memcached.properties", null);
		
		if(MEMCACHED_SWITCH_ON.equalsIgnoreCase(properties.getProperty("MEMCACHED_SWITCH"))){
			String address = properties.getProperty("MEMCACHED_HOST") + ":" + properties.getProperty("MEMCACHED_PORT");
			MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(address));
			try {
				mc = builder.build();
				if(logger.isDebugEnabled()){
					logger.debug("======系统已启用Memcached功能======");
				}
			} catch (IOException e) {
				if(logger.isDebugEnabled()){
					logger.debug("无法启用Memcached功能，getClient：==IOException");
				}
				e.printStackTrace();
			} catch (Exception e) {
				if(logger.isDebugEnabled()){
					logger.debug("无法启用Memcached功能，getClient：==Exception");
				}
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 向memcached中添加数据
	 * @param key
	 * @param value
	 */
	public static void set(String key, Object value) {
		set(key, value, MEMCACHED_EXPIRE_TIME);
	}

	/**
	 * 向memcached中添加数据
	 * @param key
	 * @param value
	 * @param expireTime
	 */
	public static void set(String key, Object value,int expireTime) {
		try {
			if(mc!=null&&StringUtils.isNotEmpty(key)) {
				mc.set(key, expireTime, value);
			} else {
				if(logger.isDebugEnabled()){
					logger.debug("无法进行set操作，memcached可能没有启用，或key为空；参见memcached.properties配置");
				}
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			
			if(logger.isDebugEnabled()){
				logger.debug("无法进行set操作，memcached可能没有启用或连接不上，或key为空；参见memcached.properties配置");
			}
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取数据
	 * @param key
	 * @param expireTime
	 * @return
	 */
	public static Object get(String key) {
		Object result = null;
		try {
			if(mc!=null&&StringUtils.isNotEmpty(key)) {
				result = mc.get(key);
			} else {
				if(logger.isDebugEnabled()){
					logger.debug("无法进行get操作，memcached可能没有启用，或key为空；参见memcached.properties配置");
				}
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			if(logger.isDebugEnabled()){
				logger.debug("无法进行get操作，memcached可能没有启用或连接不上，或key为空；参见memcached.properties配置");
			}
			e.printStackTrace();
		} 
		return result;
	}

	/**
	 * 替换数据
	 * @param key
	 * @param value
	 * @param expireTime
	 * @return
	 */
	public static boolean replace(String key, Object value, int expireTime) {
		boolean flag = false;
		try {
			if(mc!=null&&StringUtils.isNotEmpty(key)) {
				flag = mc.replace(key, expireTime, value);
			} else {
				if(logger.isDebugEnabled()){
					logger.debug("无法进行replace操作，memcached可能没有启用，或key为空；参见memcached.properties配置");
				}
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			if(logger.isDebugEnabled()){
				logger.debug("无法进行replace操作，memcached可能没有启用或连接不上，或key为空；参见memcached.properties配置");
			}
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 删除数据
	 * @param key
	 */
	public static void delete(String key) {
		try {
			if(mc!=null&&StringUtils.isNotEmpty(key)) {
				mc.delete(key);
			} else {
				if(logger.isDebugEnabled()){
					logger.debug("无法进行delete操作，memcached可能没有启用，或key为空；参见memcached.properties配置");
				}
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			if(logger.isDebugEnabled()){
				logger.debug("无法进行delete操作，memcached可能没有启用，或key为空；参见memcached.properties配置");
			}
			e.printStackTrace();
		}
	}

	/**
	 * 清空数据
	 */
	public static void flush() {
		try {
			if(mc!=null) {
				mc.flushAll();
			} else {
				if(logger.isDebugEnabled()){
					logger.debug("无法进行flush操作，memcached可能没有启用，或key为空；参见memcached.properties配置");
				}
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			if(logger.isDebugEnabled()){
				logger.debug("无法进行flush操作，memcached可能没有启用，或key为空；参见memcached.properties配置");
			}
			e.printStackTrace();
		}
	}
}
