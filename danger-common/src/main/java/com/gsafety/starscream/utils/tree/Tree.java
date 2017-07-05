package com.gsafety.starscream.utils.tree;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Tree implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5235807989721408511L;
	/*标识*/
	private String id = null;
	/*名称*/
	private String name = null;
	/*父节点标识*/
	private String parentid = null;
	/*描述*/
	private String note = null;

	private List<Tree> children;
	
	public Tree(String id, String name){
		this.id = id;
		this.name = name;
	}
	
	public Tree(String id, String name, String parentid){
		this.id = id;
		this.name = name;
		this.parentid = parentid;
	}
	
	public Tree(String id, String name, String parentid, String note){
		this.id = id;
		this.name = name;
		this.parentid = parentid;
		this.note = note;
	}
	
	public String getKey() {
		return id;
	}
	public void setKey(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	
	public List<Tree> getChildren() {
		return children;
	}

	public void setChildren(List<Tree> children) {
		this.children = children;
	}

	/**
     * Generated using sunbor ModelDriver
     * @generated
     */
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)    
        		.append("id", this.id)
                .append("name", this.name)
                .append("parentid", this.parentid)
                .append("note", this.note)
				.toString();
    }
    
    public boolean equals(Object object) {
        if (!(object instanceof Tree)) {
            return false;
        }

        Tree openwindow = (Tree) object;

        return new EqualsBuilder()      
			.append("id", openwindow.id)
	        .append("name", openwindow.name)
	        .append("parentid", openwindow.parentid)
	        .append("note", openwindow.note)
			.isEquals();
	}
	
    public int hashCode() {
        return new HashCodeBuilder(-426830461, 631494429)     
			.append(this.id)
	        .append(this.name)
	        .append(this.parentid)
	        .append(this.note)
	        .toHashCode();
	}
}
