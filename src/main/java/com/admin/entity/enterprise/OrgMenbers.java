package com.admin.entity.enterprise;

/**
 * 合同
 */
public class OrgMenbers {
	/** 机构id*/
	private Integer orgId;
	/** 用户id*/
	private Long userId;

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
