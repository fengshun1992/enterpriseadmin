package com.admin.entity.enterprise;

import java.io.Serializable;

/**
 * 模板文件参数
 */
public class FileParams implements Serializable{
	/** 参数ID*/
	private Integer paramId;
	/** 模板文件ID*/
	private Integer modelFileId;
	/** 参数所在表格名称*/
	private String tableName;
	/** 参数名称*/
	private String param;
	/** 参数值*/
	private String value;
	/** 参数类型 0-没有参数 1-普通参数 2-表格内部参数*/
	private Integer paramType;
	/** 参数所在表格第几列*/
	private Integer columnIndex;

	public Integer getParamId() {
		return paramId;
	}

	public void setParamId(Integer paramId) {
		this.paramId = paramId;
	}

	public Integer getModelFileId() {
		return modelFileId;
	}

	public void setModelFileId(Integer modelFileId) {
		this.modelFileId = modelFileId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getParamType() {
		return paramType;
	}

	public void setParamType(Integer paramType) {
		this.paramType = paramType;
	}

	public Integer getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(Integer columnIndex) {
		this.columnIndex = columnIndex;
	}
}
