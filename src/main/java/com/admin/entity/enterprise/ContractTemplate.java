package com.admin.entity.enterprise;

/**
 * 合同模板
 */
public class ContractTemplate {
	/** 合同模板ID*/
	private Integer templateId;
	/** 合同模型ID*/
	private Integer modelId;
	/** 合同模板名称*/
	private String name;
	/** 合同模板状态 0-未发行 1-已发行*/
	private Integer status;
	/** 创建者ID*/
	private Long creatorId;
	/** 创建时间*/
	private String createTime;
	/** 发行人id*/
	private Integer publisherOrgId;
	/** 受托管理人id*/
	private Integer delegatorOrgId;


	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
}
