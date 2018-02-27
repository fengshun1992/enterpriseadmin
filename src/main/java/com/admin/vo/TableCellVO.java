package com.admin.vo;

/**
 * word文档表格
 */
public class TableCellVO {
	/** 文件id*/
	private Integer modelFileId;
	/** 参数*/
	private String param;
	/** 参数值*/
	private String value;
	/** 参数所在表格第几行*/
	private int rowIndex;
	/** 所在表格列数*/
	private int columnIndex;

	/** 参数Id*/
	private Integer paramId;

	/** 所在表格名称*/
	private String tableName;

	public Integer getModelFileId() {
		return modelFileId;
	}

	public void setModelFileId(Integer modelFileId) {
		this.modelFileId = modelFileId;
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

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	public Integer getParamId() {
		return paramId;
	}

	public void setParamId(Integer paramId) {
		this.paramId = paramId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
