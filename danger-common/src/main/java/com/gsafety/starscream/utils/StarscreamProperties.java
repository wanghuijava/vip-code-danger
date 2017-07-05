package com.gsafety.starscream.utils;

import java.util.Properties;

import com.gsafety.starscream.utils.file.PropertiesUtils;

public class StarscreamProperties {
	/* 构造一个singleton模式的factory类 */
	private static StarscreamProperties instance = null;

	private static Properties properties = null;

	/* 弹出窗口的配置文件路径 */
	private static final String TREE_PROP_FILE = "config.properties";


	private StarscreamProperties() {
		loadProperties();
	}

	/**
	 * Description: 获取工厂类的实例
	 * 
	 * @return 工厂类的实例 2015-2-12
	 */
	public static StarscreamProperties getInstance() {
		if (instance == null){
			instance = new StarscreamProperties();
		}
		return instance;
	}

	/**
	 * Description: 初始化配置
	 * 
	 * @return 2015-2-12
	 */
	private void loadProperties() {
		if (properties == null) {
			properties = PropertiesUtils.getProperties(TREE_PROP_FILE, null);
		}
	}

	/**
	 * Description: 通过一个键获取配置文件中的一个对应值
	 * 
	 * @param key
	 *            键
	 * @return 对应值 2015-2-12
	 */
	public String getPropValue(String key) {
		return properties.getProperty(key);
	}
	
	/**
	 * 文档上传url
	 *  @author Zzj
	 * @date 2015-4-23 下午1:47:33
	 * @declare 
	 * @return
	 */
	public String getOfficeAnalysisUploadUrl(){
		StarscreamProperties p = StarscreamProperties.getInstance();
		return p.getPropValue("OFFICE_ANALYSIS_UPLOAD_URL");
	}
	public String getOfficeAnalysisViewUrl(){
		StarscreamProperties p = StarscreamProperties.getInstance();
		return p.getPropValue("OFFICE_ANALYSIS_VIEW_URL");
	}
}
