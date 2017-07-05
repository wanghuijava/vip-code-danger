package com.gsafety.starscream.utils.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;

import com.gsafety.starscream.memcached.MCache;
import com.gsafety.starscream.utils.db.JdbcBase;

/**
 * 树结构通用编码
 * 
 * @author wanghui
 * 
 */
public class TreeUtils {

	/**
	 * 根据配置id和type、filter取得数据库的数据，见配置tree.properties
	 * filter过滤条件如果不为空，则不做memcache缓存。
	 * @param treeid
	 * @param type
	 * @param filter
	 *            
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public static List<Tree> findTreesByIdAndType(String treeid, String type, String filter)
			throws DataAccessException {

		List<Tree> trees = new ArrayList<Tree>();
		if (StringUtils.isEmpty(treeid)) {
			return trees;
		}

		TreePropCfg tcf = TreeFactory.getInstance().getTreeById(treeid);
		if (tcf == null) {
			return trees;
		}
		TreePropCfg tcfTemp = new TreePropCfg(tcf.getId(), tcf.getType(),
				tcf.getRoot(), tcf.getParentName(), tcf.getSelect(),
				tcf.getTable(), tcf.getResults(), tcf.getFilter(),
				tcf.getOrder());
		if (StringUtils.isNotEmpty(type)) {
			tcfTemp.setType(type);
		}
		//filter过滤条件如果不为空，则不做memcache缓存，动态查询数据库获取数据
		if (StringUtils.isNotEmpty(filter)) {
			if(filter.contains("select distinct")){
				tcfTemp.setFilter(filter);
				tcfTemp.setType("list");
			}else 
			tcfTemp.setFilter(tcfTemp.getFilter() + filter);
			
			return trees = TreeUtils.findOpentree(tcfTemp);
		}

		Object object = MCache.get(tcfTemp.getId() + "_" + tcfTemp.getType());
		if (object != null) {
			trees = (List<Tree>) object;
		} else {
			trees = TreeUtils.findOpentree(tcfTemp);
			if (trees != null && trees.size() > 0) {
				MCache.set(tcfTemp.getId() + "_" + tcfTemp.getType(), trees);
			}
		}

		return trees;
	}

	/**
	 * 根据配置id和type取得数据库的数据，见配置tree.properties
	 * 会做memcache缓存
	 * @param treeid
	 * @param type
	 *            :list;tree
	 * @return
	 * @throws DataAccessException
	 */
	public static List<Tree> findTreesByIdAndType(String treeid, String type)
			throws DataAccessException {

		return findTreesByIdAndType(treeid, type, null);
	}

	/**
	 * 根据code取得名称
	 * 
	 * @param treeid
	 *            配置在tree.properties中的前缀
	 * @param id
	 *            要取得的名称的id
	 * @return
	 */
	public static String getNameById(String treeid, String id) {
		if (StringUtils.isEmpty(treeid) || StringUtils.isEmpty(id)) {
			return "";
		}
		return findMapByIdAndType(treeid).get(id);
	}

	/**
	 * 根据配置id取得数据库的数据封装成map，用于取得名称，见配置tree.properties
	 * 
	 * @param treeid
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> findMapByIdAndType(String treeid)
			throws DataAccessException {

		Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isEmpty(treeid)) {
			return map;
		}

		TreePropCfg tcf = TreeFactory.getInstance().getTreeById(treeid);
		if (tcf == null) {
			return map;
		}
		tcf.setType("list");

		Object object = MCache.get(tcf.getId() + "_" + tcf.getType() + "_map");
		if (object != null) {
			map = (Map<String, String>) object;
		} else {
			List<Tree> trees = TreeUtils.findOpentree(tcf);
			for (Tree tree : trees) {
				map.put(tree.getKey(), tree.getName());
			}
			MCache.set(tcf.getId() + "_" + tcf.getType() + "_map", map);
		}

		return map;
	}

	private static List<Tree> findOpentree(TreePropCfg owpc)
			throws DataAccessException {
		List<Map<String, Object>> ls = null;
		List<Tree> rtn = new ArrayList<Tree>();
		String sql = buildSqlWithUserTreeData(owpc);
		ls = JdbcBase.queryMapListBySQL(sql);
//		System.out.println(ls.size() + "========" + sql);
		for (Map<String, Object> tmp : ls) {
			Tree openwindow = null;
			if (tmp.size() == 2) {
				openwindow = new Tree((String) tmp.get("id"),
						(String) tmp.get("name"));
			} else if (tmp.size() == 3) {
				openwindow = new Tree((String) tmp.get("id"),
						(String) tmp.get("name"), (String) tmp.get("pId"));
			} else if (tmp.size() >= 4) {
				openwindow = new Tree((String) tmp.get("id"),
						(String) tmp.get("name"), (String) tmp.get("pId"),
						(String) tmp.get("notes"));
			}

			if (owpc.isTree()) {
				owpc.setRoot((String) tmp.get("id"));
				openwindow.setChildren(findOpentree(owpc));
			}

			rtn.add(openwindow);
		}
		return rtn;
	}

	private static String buildSqlWithUserTreeData(TreePropCfg owpc) {
		String basesql = "SELECT " + owpc.getResults() + " FROM "
				+ owpc.getTable();
		String where = " WHERE 1 = 1 ";
		if (owpc.isTree()) {
			String isTree = "AND " + owpc.getParentName() + " = '"
					+ owpc.getRoot() + "' ";
			if (StringUtils.isEmpty(owpc.getRoot())) {
				isTree = "AND " + owpc.getParentName() + " is null ";
			}
			where += isTree;
		}

		if (!StringUtils.isEmpty(owpc.getFilter())) {
			where += "AND " + owpc.getFilter();
		}
		basesql += where;
		if (!StringUtils.isEmpty(owpc.getOrder())) {
			basesql += " " + owpc.getOrder();
		}
		return basesql;
	}

}
