package com.admin.service.enterprise.contractStamp;

import com.admin.dao.DaoSupport;
import com.admin.entity.Page;
import com.admin.entity.enterprise.Contract;
import com.admin.entity.enterprise.ContractStamp;
import com.admin.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("contractStampService")
public class ContractStampService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
     * 列表
     */
	public List<PageData> listPdPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("ContractStampMapper.listPage", page);
	}

	/**
     * 新增
     */
	public int save(ContractStamp contractStamp) throws Exception {
		return (int)dao.save("ContractStampMapper.save", contractStamp);
	}

	/*
	 * 通过id获取数据
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ContractStampMapper.findById", pd);
	}

	/**
	 * 更新
	 */
	public void update(ContractStamp contractStamp) throws Exception {
		dao.update("ContractStampMapper.update", contractStamp);
	}

	/*
	 * 删除
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("ContractStampMapper.delete", pd);
	}

	/*
	 * 发行盖章
	 */
	public List<ContractStamp> findFxByModelFileId(int modelFileId) throws Exception {
		return (List<ContractStamp>) dao.findForList("ContractStampMapper.findFxByModelFileId", modelFileId);
	}

	/*
	 * 受托盖章
	 */
	public List<ContractStamp> findStByModelFileId(int modelFileId) throws Exception {
		return (List<ContractStamp>) dao.findForList("ContractStampMapper.findStByModelFileId", modelFileId);
	}

}
