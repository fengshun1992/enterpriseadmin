package com.admin.service.enterprise.contractTemplate;

import com.admin.dao.DaoSupport;
import com.admin.entity.Page;
import com.admin.entity.enterprise.ContractTemplate;
import com.admin.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("contractTemplateService")
public class ContractTemplateService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
     * 列表
     */
	public List<PageData> listPdPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("ContractTemplateMapper.listPage", page);
	}

	/**
     * 新增
     */
	public int save(ContractTemplate contractTemplate) throws Exception {
		return (int) dao.save("ContractTemplateMapper.save", contractTemplate);
	}

	/*
	 * 通过id获取数据
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ContractTemplateMapper.findById", pd);
	}

	/**
	 * 更新
	 */
	public void update(ContractTemplate contractTemplate) throws Exception {
		dao.update("ContractTemplateMapper.update", contractTemplate);
	}

	/*
	 * 删除
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("ContractTemplateMapper.delete", pd);
	}

	/**
	 *查询出当前登录的发行人可看到的合同模板
	 */
	public List<PageData> listPubTemplate(Page page) throws Exception{
		return (List<PageData>) dao.findForList("ContractTemplateMapper.pubTemplatelistPage", page);
	}

	/**
	 *根据模板id查询出模板数据
	 */
	public ContractTemplate findByTemplateId(int templateId) throws Exception{
		return (ContractTemplate) dao.findForObject("ContractTemplateMapper.findByTemplateId", templateId);
	}

	/**
	 *通过creatorId获取数据
	 */
	public List<ContractTemplate> findByCreatorId(long creatorId) throws Exception{
		return (List<ContractTemplate>) dao.findForList("ContractTemplateMapper.findByCreatorId", creatorId);
	}
}
