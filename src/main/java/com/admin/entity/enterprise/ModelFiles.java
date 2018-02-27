package com.admin.entity.enterprise;

/**
 * 合同模板管理文档
 */
public class ModelFiles {
	/** 模型文件ID*/
	private Integer modelFileId;
	/** 模型ID*/
	private Integer modelId;
	/** 文件名*/
	private String fileName;
	/** 链接*/
	private String url;

    public Integer getModelFileId() {
        return modelFileId;
    }

    public void setModelFileId(Integer modelFileId) {
        this.modelFileId = modelFileId;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
