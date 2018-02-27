package com.admin.service.enterprise.contractFileImages;

import com.admin.dao.DaoSupport;
import com.admin.entity.Page;
import com.admin.entity.enterprise.Contract;
import com.admin.entity.enterprise.ContractFileImages;
import com.admin.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("contractFileImagesService")
public class ContractFileImagesService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
     * 列表
     */
	public List<PageData> listPdPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("ContractFileImagesMapper.listPage", page);
	}

	/**
     * 新增
     */
	public int save(List<ContractFileImages> list) throws Exception {
		return (int) dao.save("ContractFileImagesMapper.batchSave", list);
	}

	/*
	 * 通过id获取数据
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ContractFileImagesMapper.findById", pd);
	}

	/**
	 * 更新
	 */
	public void update(Contract contract) throws Exception {
		dao.update("ContractFileImagesMapper.update", contract);
	}

	/*
	 * 删除
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("ContractFileImagesMapper.delete", pd);
	}

}
