package com.gsafety.starscream.utils.tree;

import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.gsafety.starscream.utils.file.PropertiesUtils;

public class TreeFactory {
	/* 构造一个singleton模式的factory类 */
	private static TreeFactory instance = null;

	private static Properties properties = null;

	/* 弹出窗口的配置文件路径 */
	private static final String TREE_PROP_FILE = "tree.properties";

	/* 装载弹出窗口的配置项对象OpenWinPropCfg */
	private static HashMap<String, TreePropCfg> map = new HashMap<String, TreePropCfg>();

	private TreeFactory() {
		loadProperties();
	}

	/**
	 * Description: 获取工厂类的实例
	 * 
	 * @return 工厂类的实例 2015-2-12
	 */
	public static TreeFactory getInstance() {
		if (instance == null){
			instance = new TreeFactory();
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
	 * Description: 通过一个键获取配置文件中的一组对应值
	 * 
	 * @param id
	 *            键
	 * @return 一个OpenWinPropCfg对象 2015-2-12
	 */
	public TreePropCfg getTreeById(String id) {
		TreePropCfg rtn = (TreePropCfg) map.get(id);
		if (rtn == null) {
			rtn = loadTreePropCfgById(id);
		}
		return rtn;
	}

	/**
	 * Description: 通过一个键获取配置文件中的一组对应值并装载到hashmap中
	 * 
	 * @param id
	 *            键
	 * @return 一个OpenWinPropCfg对象 2015-2-12
	 */
	private TreePropCfg loadTreePropCfgById(String id) {
		String type = getPropValue(id + ".type");
		String root = getPropValue(id + ".root");
		String parentName = getPropValue(id + ".parentName");
		String select = getPropValue(id + ".select");
		String table = getPropValue(id + ".table");
		String results = getPropValue(id + ".results");
		String filter = getPropValue(id + ".filter");
		String order = getPropValue(id + ".order");
		if (StringUtils.isEmpty(id) || StringUtils.isEmpty(table) || StringUtils.isEmpty(results)) {
			// 配置不正确
			System.out.println(id+"配置不正确");
			return null;
		}
		TreePropCfg rtn = new TreePropCfg(id, type, root, parentName, select, table,
				results, filter, order);
		map.put(id, rtn);
		return rtn;
	}
}
