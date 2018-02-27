package com.admin.service.enterprise.model;

import com.admin.dao.DaoSupport;
import com.admin.entity.Page;
import com.admin.entity.enterprise.ContractTemplate;
import com.admin.entity.enterprise.Model;
import com.admin.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("modelService")
public class ModelService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
     * 分页列表
     */
	public List<PageData> listPdPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("ModelMapper.listPage", page);
	}

	/**
     * 列表
     */
	public List<PageData> listAll() throws Exception {
		return (List<PageData>) dao.findForList("ModelMapper.listAll", null);
	}

	/**
     * 新增
     */
	public int save(Model model) throws Exception {
		return (int) dao.save("ModelMapper.save", model);
	}

	/*
	 * 通过id获取数据
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ModelMapper.findById", pd);
	}

	/**
	 * 更新
	 */
	public void update(Model model) throws Exception {
		dao.update("ModelMapper.update", model);
	}

	/*
	 * 删除
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("ModelMapper.delete", pd);
	}

}
