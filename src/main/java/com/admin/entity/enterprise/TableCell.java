package com.admin.entity.enterprise;

/**
 * word文档表格
 */
public class TableCell {
	/** 参数*/
	private String param;
	/** 参数值*/
	private String value;
	/** 参数所在表格第几行*/
	private int rowIndex;
	/** 所在表格列数*/
	private int columnIndex;

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
}
