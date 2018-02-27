package com.admin.service.enterprise.fileParamsValues;

import com.admin.dao.DaoSupport;
import com.admin.entity.enterprise.FileParamsValues;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "fileParamsValuesService")
public class FileParamsValuesService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /**
     * 新增
     */
    public void save(List<FileParamsValues> list) throws Exception {
        dao.save("FileParamsValuesMapper.batchSave", list);
    }


    /**
     * 通过targetFileId获取普通参数
     */

    public List<FileParamsValues> findParamById(Integer targetFileId) throws Exception{
        return (List<FileParamsValues>) dao.findForList("FileParamsValuesMapper.findParamById",targetFileId);
    }

    /**
     * 通过targetFileId获取表格参数
     */
    public List<FileParamsValues> findTableParamById(Integer targetFileId) throws Exception{
        return (List<FileParamsValues>) dao.findForList("FileParamsValuesMapper.findTableParamById",targetFileId);
    }

    /**
     * 通过targetFileId获取全部参数
     */
    public List<FileParamsValues> listByTargetFileId(Integer targetFileId) throws Exception{
        return (List<FileParamsValues>) dao.findForList("FileParamsValuesMapper.listByTargetFileId",targetFileId);
    }

    /**
     * 通过paramId获取全部参数
     */
    public FileParamsValues findByParamId(Integer paramId) throws Exception{
        return (FileParamsValues) dao.findForList("FileParamsValuesMapper.findByParamId",paramId);
    }

    /**
     * 更新
     */
    public void update(FileParamsValues fileParamsValues) throws  Exception{
        dao.update("FileParamsValuesMapper.update",fileParamsValues);
    }

    /**
     * 批量更新
     */
    public void batchUpdate(List<FileParamsValues> fpvList) throws Exception{
        dao.update("FileParamsValuesMapper.batchUpdate",fpvList);
    }

    /**
     * 根据targetFileId删除参数值
     * @param targetFileId
     */
    public void deleteByTargetFileId(Integer targetFileId) throws Exception{
        dao.delete("FileParamsValuesMapper.deleteByTargetFileId",targetFileId);
    }
}
