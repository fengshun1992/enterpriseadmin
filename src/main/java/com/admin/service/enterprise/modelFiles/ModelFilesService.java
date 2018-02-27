package com.admin.service.enterprise.modelFiles;

import com.admin.dao.DaoSupport;
import com.admin.entity.enterprise.ModelFiles;
import com.admin.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("modelFilesService")
public class ModelFilesService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /**
     * 列表
     */
    public List<PageData> allList(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ModelFilesMapper.allList", pd);
    }

    /**
     * 新增
     */
    public void save(List<ModelFiles> modelFiles) throws Exception {
        dao.batchSave("ModelFilesMapper.batchSave", modelFiles);
    }

    /*
     * 获取未关联数据
     */
    public List<ModelFiles> getList() throws Exception {
        return (List<ModelFiles>) dao.findForList("ModelFilesMapper.getList", null);
    }

    /*
     * 通过id获取数据
     */
    public List<PageData> findById(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ModelFilesMapper.findById", pd);
    }

    /**
     * 更新
     */
    public void update(ModelFiles modelFiles) throws Exception {
        dao.update("ModelFilesMapper.update", modelFiles);
    }

    /*
     * 删除
     */
    public void delete(Integer templateId) throws Exception {
        dao.delete("ModelFilesMapper.batchDelete", templateId);
    }

    /*
     * 根据模板id获取模型文件
     */
    public List<ModelFiles> listByTemplateId(Integer templateId) throws Exception {
        return (List<ModelFiles>) dao.findForList("ModelFilesMapper.listByTemplateId", templateId);
    }


    /*
     * 根据modelFileId获取模型文件
     */
    public ModelFiles findByModelId(Integer modelFileId) throws Exception{
        return (ModelFiles) dao.findForObject("ModelFilesMapper.findByModelId", modelFileId);
    }
}
