package com.gsafety.starscream.basedata.view;

import java.util.List;

public class OrgUserStat {
	//机构名称
	private String orgName;
	//机构人数汇总
	private int orgTotal;
	//合并行
	private int mergeRow;
	
	private List<OrgUserStatDTO> orgStat;

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public int getOrgTotal() {
		return orgTotal;
	}

	public void setOrgTotal(int orgTotal) {
		this.orgTotal = orgTotal;
	}

	public List<OrgUserStatDTO> getOrgStat() {
		return orgStat;
	}

	public void setOrgStat(List<OrgUserStatDTO> orgStat) {
		this.orgStat = orgStat;
	}
	

	public int getMergeRow() {
		return mergeRow;
	}

	public void setMergeRow(int mergeRow) {
		this.mergeRow = mergeRow;
	}

	public OrgUserStat() {
		super();
		// TODO Auto-generated constructor stub
	}
}
