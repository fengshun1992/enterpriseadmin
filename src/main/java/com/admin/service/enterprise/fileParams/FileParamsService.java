package com.admin.service.enterprise.fileParams;

import com.admin.dao.DaoSupport;
import com.admin.entity.Page;
import com.admin.entity.enterprise.ContractTemplate;
import com.admin.entity.enterprise.FileParams;
import com.admin.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("fileParamsService")
public class FileParamsService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
     * 列表
     */
	public List<PageData> listPdPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("FileParamsMapper.listPage", page);
	}

	/**
     * 新增
     */
	public void save(List<FileParams> list) throws Exception {
		dao.save("FileParamsMapper.batchSave", list);
	}

	/*
	 * 通过id获取数据
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("FileParamsMapper.findById", pd);
	}

	/**
	 * 更新
	 */
	public void update(ContractTemplate contractTemplate) throws Exception {
		dao.update("FileParamsMapper.update", contractTemplate);
	}

	/*
	 * 删除
	 */
	public void delete(int templateId) throws Exception {
		dao.delete("FileParamsMapper.batchDelete", templateId);
	}

	/*
	 *根据合同文件id查询出对应的合同参数
	 */
	public List<FileParams> listParam(Integer modelFileId) throws Exception {
		return (List<FileParams>) dao.findForList("FileParamsMapper.listParam", modelFileId);
	}



    public String listTableName(Integer modelFileId) throws Exception {
		return (String) dao.findForObject("FileParamsMapper.listTableName", modelFileId);
    }

    public FileParams findByParamId(Integer paramId) throws Exception{
		return (FileParams) dao.findForObject("FileParamsMapper.findByParamId", paramId);
    }
}
