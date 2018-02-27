package com.admin.entity.enterprise;

/**
 * 合同
 */
public class Contract {
	/** 合同ID*/
	private Integer contractId;
	/** 合同编号*/
	private String contractNo;
	/** 合同模板ID*/
	private Integer templateId;
	/** 合同名称*/
	private String contractName;
	/** 合同状态 1-待受托管理人审核 2-待风控审核 3-驳回 4-已归档 5-下架 6-重新发行*/
	private Integer status;
	/** 驳回理由*/
	private String reason;
	/** 合同发起人所属单位id*/
	private Integer publisherOrgId;
	/** 合同受托管理人所属单位id*/
	private Integer delegatorOrgId;
	/** 合同管理单位id*/
	private Integer manageOrgId;
	/** 合同发行时间*/
	private String createTime;
	/** 合同修改时间*/
	private String updateTime;

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getPublisherOrgId() {
		return publisherOrgId;
	}

	public void setPublisherOrgId(Integer publisherOrgId) {
		this.publisherOrgId = publisherOrgId;
	}

	public Integer getDelegatorOrgId() {
		return delegatorOrgId;
	}

	public void setDelegatorOrgId(Integer delegatorOrgId) {
		this.delegatorOrgId = delegatorOrgId;
	}

	public Integer getManageOrgId() {
		return manageOrgId;
	}

	public void setManageOrgId(Integer manageOrgId) {
		this.manageOrgId = manageOrgId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
