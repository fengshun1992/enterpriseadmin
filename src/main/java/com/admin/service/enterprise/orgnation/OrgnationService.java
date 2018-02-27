package com.admin.service.enterprise.orgnation;

import com.admin.dao.DaoSupport;
import com.admin.entity.Page;
import com.admin.entity.enterprise.Orgnation;
import com.admin.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("orgnationService")
public class OrgnationService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 保存
	 */
	public int save(Orgnation orgnation) throws Exception {
		return (int) dao.save("OrgnationMapper.save", orgnation);
	}

	/*
	 * 更新用户
	 */
	public void update(Orgnation orgnation) throws Exception {
		dao.update("OrgnationMapper.edit", orgnation);
	}


	/*
	 * 删除
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("OrgnationMapper.delete", pd);
	}


	/*
	 * 通过orgId获取数据
	 */
	public Orgnation findByOId(PageData pd) throws Exception {
		return (Orgnation) dao.findForObject("OrgnationMapper.findByOId", pd);
	}



	/**
     * 分页列表
     */
	public List<PageData> listPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("OrgnationMapper.listPage", page);
	}


	/**
     * 列表
     */
	public List<PageData> allList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("OrgnationMapper.allList", pd);
	}

}
