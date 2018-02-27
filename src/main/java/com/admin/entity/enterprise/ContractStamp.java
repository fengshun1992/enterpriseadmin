package com.admin.entity.enterprise;

/**
 * 合同公章
 */
public class ContractStamp {
	/** id*/
	private Integer Id;
	/** 模型文件id*/
	private Integer modelFileId;
	/** 第几页*/
	private Integer pageNo;
	/** 1-发行公司 2-发行公司法人 3-受托公司 4受托公司法人*/
	private Integer imgType;
	/** 公章组*/
	private String imgGroup;
	/** 公章url*/
	private String imgUrl;
	/** x坐标*/
	private Integer absoluteX;
	/** y坐标*/
	private Integer absoluteY;
	/** 旋转角度*/
	private Integer rotate;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Integer getModelFileId() {
		return modelFileId;
	}

	public void setModelFileId(Integer modelFileId) {
		this.modelFileId = modelFileId;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getImgType() {
		return imgType;
	}

	public void setImgType(Integer imgType) {
		this.imgType = imgType;
	}

	public String getImgGroup() {
		return imgGroup;
	}

	public void setImgGroup(String imgGroup) {
		this.imgGroup = imgGroup;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getAbsoluteX() {
		return absoluteX;
	}

	public void setAbsoluteX(Integer absoluteX) {
		this.absoluteX = absoluteX;
	}

	public Integer getAbsoluteY() {
		return absoluteY;
	}

	public void setAbsoluteY(Integer absoluteY) {
		this.absoluteY = absoluteY;
	}

	public Integer getRotate() {
		return rotate;
	}

	public void setRotate(Integer rotate) {
		this.rotate = rotate;
	}
}
