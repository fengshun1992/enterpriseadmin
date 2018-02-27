package com.admin.entity.enterprise;

import java.util.List;

/**
 * word文档表格
 */
public class WordTable {
	/** 参数所在表格名称*/
	private String tableName;
	/** 表格行数*/
	private int rows;
	/** 表格参数集合*/
	private List<TableCell> columnList;

	public String getTableName() {
		return tableName;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<TableCell> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<TableCell> columnList) {
		this.columnList = columnList;
	}
}
