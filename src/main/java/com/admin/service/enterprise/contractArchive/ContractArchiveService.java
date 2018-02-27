package com.admin.service.enterprise.contractArchive;

import com.admin.dao.DaoSupport;
import com.admin.entity.Page;
import com.admin.entity.enterprise.ContractArchive;
import com.admin.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("contractArchiveService")
public class ContractArchiveService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
     * 归档列表
     */
	public List<PageData> gdListPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("ContractArchiveMapper.gdlistPage", page);
	}

	/**
     * 新增
     */
	public int save(ContractArchive contractArchive) throws Exception {
		return (int)dao.save("ContractArchiveMapper.save", contractArchive);
	}

	/*
	 * 通过id获取数据
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ContractArchiveMapper.findById", pd);
	}

	/**
	 * 更新
	 */
	public void update(ContractArchive contractArchive) throws Exception {
		dao.update("ContractArchiveMapper.update", contractArchive);
	}

	/*
	 * 删除
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("ContractArchiveMapper.delete", pd);
	}

}
