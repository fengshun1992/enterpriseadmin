package com.admin.service.enterprise.publishContract;

import com.admin.dao.DaoSupport;
import com.admin.entity.Page;
import com.admin.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("publishContractService")
public class PublishContractService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /**
     * 合同书参数列表
     */
    public List<PageData> listOontractParams(Page page) throws Exception {
        return (List<PageData>) dao.findForList("OrgnationMapper.allList", page);
    }



}
