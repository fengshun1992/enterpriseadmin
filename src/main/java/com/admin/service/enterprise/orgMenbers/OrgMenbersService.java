package com.admin.service.enterprise.orgMenbers;

import com.admin.dao.DaoSupport;
import com.admin.entity.Page;
import com.admin.entity.enterprise.ContractTemplate;
import com.admin.entity.enterprise.OrgMenbers;
import com.admin.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("orgMenbersService")
public class OrgMenbersService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
     * 列表
     */
	public List<PageData> listPdPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("OrgMenbersMapper.listPage", page);
	}

	/**
     * 新增
     */
	public int save(List<OrgMenbers> list) throws Exception {
		return (int) dao.batchSave("OrgMenbersMapper.batchSave", list);
	}

	/*
	 * 通过id获取数据
	 */
	public OrgMenbers findByUserId(PageData pd) throws Exception {
		return (OrgMenbers) dao.findForObject("OrgMenbersMapper.findByUserId", pd);
	}

	/**
	 * 更新
	 */
	public void update(ContractTemplate contractTemplate) throws Exception {
		dao.update("OrgMenbersMapper.update", contractTemplate);
	}

	/*
	 * 删除
	 */
	public void delete(int orgId) throws Exception {
		dao.delete("OrgMenbersMapper.delete", orgId);
	}

	/*
	 * 通过id获取数据
	 */
	public List<OrgMenbers> getAllUserId() throws Exception {
		return (List<OrgMenbers>) dao.findForList("OrgMenbersMapper.getAllUserId", null);
	}

}
