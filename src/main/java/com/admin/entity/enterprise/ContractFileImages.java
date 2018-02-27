package com.admin.entity.enterprise;

/**
 * 合同文件图片集
 */
public class ContractFileImages {
	/** ID*/
	private Integer Id;
	/** 对应的合同文件id*/
	private Integer targetFileId;
	/** 当前页码*/
	private Integer pageNo;
	/** url*/
	private String imageUrl;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Integer getTargetFileId() {
		return targetFileId;
	}

	public void setTargetFileId(Integer targetFileId) {
		this.targetFileId = targetFileId;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
