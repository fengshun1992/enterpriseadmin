package com.admin.entity.enterprise;

/**
 * 操作记录表
 */
public class OprationRecord {
	/** 操作记录id*/
	private Integer recordId;
	/** 合同ID*/
	private Integer contractId;
	/** 操作类型 1-发行 2-审核通过 3-驳回 4-归档发行  5-下架 6-重新发行*/
	private Integer opType;
	/** 如果驳回，此处记录原因*/
	private String reason;
	/** 操作人id*/
	private Long opId;
	/** 操作时间*/
	private String opTime;

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public Long getOpId() {
		return opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public String getOpTime() {
		return opTime;
	}

	public void setOpTime(String opTime) {
		this.opTime = opTime;
	}

	public Integer getOpType() {
		return opType;
	}

	public void setOpType(Integer opType) {
		this.opType = opType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
