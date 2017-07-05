package com.gsafety.starscream.basedata.view;

public class OrgUserStatDTO {
	//机构名称
	private String orgName;
	//二级机构人数
	private int orgCount;
	
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public int getOrgCount() {
		return orgCount;
	}
	public void setOrgCount(int orgCount) {
		this.orgCount = orgCount;
	}

	public OrgUserStatDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
}
