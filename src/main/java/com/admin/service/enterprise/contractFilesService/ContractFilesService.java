package com.admin.service.enterprise.contractFilesService;

import com.admin.dao.DaoSupport;
import com.admin.entity.Page;
import com.admin.entity.enterprise.ContractFiles;
import com.admin.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "contractFilesService")
public class ContractFilesService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /**
     * 列表
     */
    public List<PageData> listPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("ContractFilesMapper.listPage", page);
    }

    /**
     * 批量新增
     */
    public void save(List<ContractFiles> contractFiles) throws Exception {
        dao.batchSave("ContractFilesMapper.batchSave", contractFiles);
    }

    /*
     * 通过id获取数据
     */
    public List<ContractFiles> findById(PageData pd) throws Exception {
        return (List<ContractFiles>) dao.findForList("ContractFilesMapper.findById", pd);
    }

    /**
     * 更新
     */
    public void update(ContractFiles contractFiles) throws Exception {
        dao.update("ContractFilesMapper.update", contractFiles);
    }

    /*
     * 删除
     */
    public void delete(PageData pd) throws Exception {
        dao.delete("ContractFilesMapper.delete", pd);
    }

    /*
     * 保存合同文件
     */
    public void saveOne(ContractFiles contractFiles) throws Exception{
        dao.save("ContractFilesMapper.saveOne",contractFiles);
    }

    /*
     * 通过contractId获取数据
     */
    public List<ContractFiles> findWordByContractId(int contractId) throws Exception {
        return (List<ContractFiles>) dao.findForList("ContractFilesMapper.findWordByContractId", contractId);
    }

    /*
     * 通过targetFileId获取数据
     */
    public ContractFiles findByTargetFileId(Integer targetFileId) throws Exception{
        return (ContractFiles) dao.findForObject("ContractFilesMapper.findByTargetFileId", targetFileId);
    }


    public void deleteByTargetFileId(Integer targetFileId) throws Exception{
        dao.delete("ContractFilesMapper.deleteByTargetFileId", targetFileId);
    }

    /**
     * 更新url
     */
    public void updateUrl(ContractFiles contractFiles) throws Exception {
        dao.update("ContractFilesMapper.updateUrl", contractFiles);
    }

    /**
     * 查询合同附件
     */
    public List<ContractFiles> findContractFJ(int contractId) throws Exception{
        return (List<ContractFiles>) dao.findForList("ContractFilesMapper.findContractFJ", contractId);
    }

    /**
     * 删除合同附件
     */
    public void deleteContractFJ(int targetFileId) throws Exception{
        dao.delete("ContractFilesMapper.deleteContractFJ", targetFileId);
    }


    public ContractFiles findByContractIdAndModelFileId(PageData pd) throws Exception{
        return (ContractFiles) dao.findForObject("ContractFilesMapper.findByContractIdAndModelFileId", pd);
    }
}
