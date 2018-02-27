package com.admin.entity.enterprise;

/**
 * 合同
 */
public class ContractArchive {
	/** 合同ID*/
	private Integer contractId;
	/** 归档合同状态 1-发行中 2-下架中*/
	private Integer type;
	/** 下架理由*/
	private String reason;
	/** 操作人Id*/
	private Long userId;
	/** 操作时间*/
	private String opTime;

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOpTime() {
		return opTime;
	}

	public void setOpTime(String opTime) {
		this.opTime = opTime;
	}
}
