package com.admin.entity.enterprise;

/**
 * 模板文件参数
 */
public class FileParamsValues {
	/** 参数ID*/
	private Integer paramId;
	/** 目标文件id*/
	private Integer targetFileId;
	/** 参数所在表格名称*/
	private String tableName;
	/** 参数名称*/
	private String param;
	/** 参数值*/
	private String value;
	/** 参数所在表格第几行*/
	private Integer rowIndex;
	/** 参数所在表格第几列*/
	private Integer columnIndex;

	public Integer getParamId() {
		return paramId;
	}

	public void setParamId(Integer paramId) {
		this.paramId = paramId;
	}

	public Integer getTargetFileId() {
		return targetFileId;
	}

	public void setTargetFileId(Integer targetFileId) {
		this.targetFileId = targetFileId;
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

	public Integer getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(Integer rowIndex) {
		this.rowIndex = rowIndex;
	}

	public Integer getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(Integer columnIndex) {
		this.columnIndex = columnIndex;
	}

	@Override
	public String toString() {
		return "FileParamsValues{" +
				"targetFileId=" + targetFileId +
				", tableName='" + tableName + '\'' +
				", param='" + param + '\'' +
				", value='" + value + '\'' +
				", rowIndex=" + rowIndex +
				", columnIndex=" + columnIndex +
				'}';
	}
}
