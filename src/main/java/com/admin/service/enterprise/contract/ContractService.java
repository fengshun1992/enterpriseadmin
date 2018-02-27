package com.admin.service.enterprise.contract;

import com.admin.dao.DaoSupport;
import com.admin.entity.Page;
import com.admin.entity.enterprise.Contract;
import com.admin.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("contractService")
public class ContractService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
     * 列表
     */
	public List<PageData> listPdPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("ContractMapper.listPage", page);
	}

	/**
     * 受托审核列表
     */
	public List<PageData> stListPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("ContractMapper.stlistPage", page);
	}

	/**
     * 风控审核列表
     */
	public List<PageData> fkListPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("ContractMapper.fklistPage", page);
	}

	/**
     * 驳回列表
     */
	public List<PageData> bhListPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("ContractMapper.bhlistPage", page);
	}

	/**
     * 新增
     */
	public int save(Contract contract) throws Exception {
		return (int)dao.save("ContractMapper.save", contract);
	}

	/*
	 * 通过id获取数据
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ContractMapper.findById", pd);
	}

	/**
	 * 更新
	 */
	public void update(Contract contract) throws Exception {
		dao.update("ContractMapper.update", contract);
	}

	/*
	 * 删除
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("ContractMapper.delete", pd);
	}

	/*
	 * 按id集合获取列表
	 */
	public List<PageData> findByIdList(List list) throws Exception {
		return (List<PageData>) dao.findForList("ContractMapper.findByIdList", list);
	}

    public Contract findByContractId(Integer contractId) throws Exception{
		return (Contract) dao.findForObject("ContractMapper.findByContractId", contractId);
    }

	/*
     * 通过合同编号获取数据
     */
    public List<Contract> findByContractNo(Contract contract) throws Exception{
		return (List<Contract>) dao.findForList("ContractMapper.findByContractNo", contract);
    }

	/**
	 * 更新状态
	 */
	public void updateStatus(Contract contract) throws Exception {
		dao.update("ContractMapper.updateStatus", contract);
	}

	/**
	 * 检查合同编号是否存在
	 */
    public List<Contract> checkContractNo(PageData pd) throws Exception{
		return (List<Contract>) dao.findForList("ContractMapper.checkContractNo", pd);
    }
}
