package com.admin.entity.system;

import com.admin.entity.Page;

import java.math.BigDecimal;
import java.util.Date;

public class Template {
    private Long id;

    private String smsType;

    private BigDecimal smsTimes;

    private Object smsTemplate;

    private String smsKey;

    private String smsSign;

    private Date addTimd;

    private Date updateTime;

    private String creater;

    private String changer;

    private Page page;			//分页对象

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType == null ? null : smsType.trim();
    }

    public BigDecimal getSmsTimes() {
        return smsTimes;
    }

    public void setSmsTimes(BigDecimal smsTimes) {
        this.smsTimes = smsTimes;
    }

    public Object getSmsTemplate() {
        return smsTemplate;
    }

    public void setSmsTemplate(Object smsTemplate) {
        this.smsTemplate = smsTemplate;
    }

    public String getSmsKey() {
        return smsKey;
    }

    public void setSmsKey(String smsKey) {
        this.smsKey = smsKey == null ? null : smsKey.trim();
    }

    public String getSmsSign() {
        return smsSign;
    }

    public void setSmsSign(String smsSign) {
        this.smsSign = smsSign == null ? null : smsSign.trim();
    }

    public Date getAddTimd() {
        return addTimd;
    }

    public void setAddTimd(Date addTimd) {
        this.addTimd = addTimd;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    public String getChanger() {
        return changer;
    }

    public void setChanger(String changer) {
        this.changer = changer == null ? null : changer.trim();
    }
}