package com.admin.service.enterprise.oprationRecord;

import com.admin.dao.DaoSupport;
import com.admin.entity.Page;
import com.admin.entity.enterprise.OprationRecord;
import com.admin.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("oprationRecordService")
public class OprationRecordService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
     * 列表
     */
	public List<PageData> listPdPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("OprationRecordMapper.listPage", page);
	}

	/**
     * 新增
     */
	public int save(OprationRecord oprationRecord) throws Exception {
		return (int) dao.save("OprationRecordMapper.save", oprationRecord);
	}

	/*
	 * 通过id获取数据
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("OprationRecordMapper.findById", pd);
	}

	/**
	 * 更新
	 */
	public void update(OprationRecord oprationRecord) throws Exception {
		dao.update("OprationRecordMapper.update", oprationRecord);
	}

	/*
	 * 删除
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("OprationRecordMapper.delete", pd);
	}
}
