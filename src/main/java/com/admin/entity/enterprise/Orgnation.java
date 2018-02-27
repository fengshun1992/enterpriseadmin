package com.admin.entity.enterprise;

import java.util.List;

/**
 * 公司管理
 */
public class Orgnation {
	/** 公司ID*/
	private Integer orgId;
	/** 公司简称*/
	private String orgName;
	/** 公司全称*/
	private String orgFullName;
	/** 状态*/
	private Integer status;
	/** 公司类型(1-发行人 2-受托管理人 3-风控)*/
	private Integer orgType;
	/** 签名数据*/
	private String signData;
	/** 头像*/
	private String signImageUrl;
	/** 机构管理人员*/
	private List<OrgMenbers> orgUser;

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgFullName() {
        return orgFullName;
    }

    public void setOrgFullName(String orgFullName) {
        this.orgFullName = orgFullName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrgType() {
        return orgType;
    }

    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }

    public String getSignData() {
        return signData;
    }

    public void setSignData(String signData) {
        this.signData = signData;
    }

    public String getSignImageUrl() {
        return signImageUrl;
    }

    public void setSignImageUrl(String signImageUrl) {
        this.signImageUrl = signImageUrl;
    }

    public List<OrgMenbers> getOrgUser() {
        return orgUser;
    }

    public void setOrgUser(List<OrgMenbers> orgUser) {
        this.orgUser = orgUser;
    }
}
