package com.admin.entity.enterprise;

/**
 * 模型
 */
public class Model {
	/** 模型ID*/
	private Integer modelId;
	/** 模型名称*/
	private String name;
	/** 创建者*/
	private Long creatorId;
	/** 修改者*/
	private Long editorId;
	/** 创建时间*/
	private String createTime;
	/** 修改时间*/
	private String updateTime;

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

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Long getEditorId() {
		return editorId;
	}

	public void setEditorId(Long editorId) {
		this.editorId = editorId;
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
