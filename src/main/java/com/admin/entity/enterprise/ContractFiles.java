package com.admin.entity.enterprise;

public class ContractFiles {
    /** 生成的最新合同文件id*/
    private Integer targetFileId ;
    /** 合同ID*/
    private Integer contractId;
    /** 采用的模板文件id，若是附件文件为空*/
    private Integer modelFileId;
    /** 文件名称*/
    private String fileName;
    /** 文件存放路径*/
    private String url;


    public Integer getTargetFileId() {
        return targetFileId;
    }

    public void setTargetFileId(Integer targetFileId) {
        this.targetFileId = targetFileId;
    }

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public Integer getModelFileId() {
        return modelFileId;
    }

    public void setModelFileId(Integer modelFileId) {
        this.modelFileId = modelFileId;
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
