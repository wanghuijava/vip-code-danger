package com.gsafety.starscream.utils.tree;

import org.springframework.util.StringUtils;


public class TreePropCfg {
	private String id = null;

	private String type = null;

	private String root = null;

	private String parentName = null;

	private String select = null;

	private String table = null;

	private String results = null;

	private String filter = null;

	private String order = null;
	
	
	public TreePropCfg(String id,String type,String root,String parentName,String select,String table,String results,String filter,String order){
		this.id = id;
		this.type = type;
		this.root = root;
		this.parentName = parentName;
		this.select = select;
		this.table = table;
		this.results = results;
		this.filter = filter;
		this.order = order;
	}

	public boolean isTree(){
		if(!"list".equals(type) && !StringUtils.isEmpty(parentName)){
			return true;
		}
		return false;
	}
	
	public boolean isMultiSelect(){
		if("multi".equals(select)){
			return true;
		}
		return false;
	}
	
	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}
	
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((filter == null) ? 0 : filter.hashCode());
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((root == null) ? 0 : root.hashCode());
		result = PRIME * result + ((order == null) ? 0 : order.hashCode());
		result = PRIME * result + ((results == null) ? 0 : results.hashCode());
		result = PRIME * result + ((select == null) ? 0 : select.hashCode());
		result = PRIME * result + ((table == null) ? 0 : table.hashCode());
		result = PRIME * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TreePropCfg other = (TreePropCfg) obj;
		if (filter == null) {
			if (other.filter != null)
				return false;
		} else if (!filter.equals(other.filter))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (results == null) {
			if (other.results != null)
				return false;
		} else if (!results.equals(other.results))
			return false;
		if (select == null) {
			if (other.select != null)
				return false;
		} else if (!select.equals(other.select))
			return false;
		if (table == null) {
			if (other.table != null)
				return false;
		} else if (!table.equals(other.table))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
}
