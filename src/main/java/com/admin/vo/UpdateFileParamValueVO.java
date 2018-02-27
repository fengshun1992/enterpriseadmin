package com.admin.vo;

import com.admin.entity.enterprise.FileParamsValues;

import java.util.List;

public class UpdateFileParamValueVO {
    private List<FileParamsValues> paramValueList;

    public List<FileParamsValues> getParamValueList() {
        return paramValueList;
    }

    public void setParamValueList(List<FileParamsValues> paramValueList) {
        this.paramValueList = paramValueList;
    }
}
