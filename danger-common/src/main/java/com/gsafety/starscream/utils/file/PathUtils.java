package com.gsafety.starscream.utils.file;

import org.apache.commons.lang.StringUtils;

/**
 * 获取系统路径
 * @author chenwenlong
 *
 */
public class PathUtils {

	/**
	 * 获取当前class文件所在系统路径
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getProjectPath(Class clazz){
		
		String path = clazz.getProtectionDomain().getCodeSource().getLocation().getPath();
		
		String sysName = System.getProperty("os.name").toUpperCase();
		
		if(StringUtils.isEmpty(path)){
			path = clazz.getResource("/").getPath();
		}
		
		int index = path.indexOf("WEB-INF")==-1?path.length():path.indexOf("WEB-INF");
		
		if(sysName.startsWith("WINDOWS")){
			return path.substring(1,index);
		} else {
			return path.substring(0,index);
		}
	}
	
	/**
	 * 获取当前类所在系统（bumblebee-common）路径
	 * 
	 */
	public static String getProjectPath(){
		return getProjectPath(PathUtils.class);
	}
}
