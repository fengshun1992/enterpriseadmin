package com.admin.vo;

import java.io.Serializable;
import java.util.List;

public class FileParamValueVO implements Serializable{

    private List<FileParamsValuesVO> paramValueList;//普通参数

    private List<TableCellVO> tableParamList;       //表格参数


    public List<FileParamsValuesVO> getParamValueList() {
        return paramValueList;
    }

    public void setParamValueList(List<FileParamsValuesVO> paramValueList) {
        this.paramValueList = paramValueList;
    }

    public List<TableCellVO> getTableParamList() {
        return tableParamList;
    }

    public void setTableParamList(List<TableCellVO> tableParamList) {
        this.tableParamList = tableParamList;
    }
}
