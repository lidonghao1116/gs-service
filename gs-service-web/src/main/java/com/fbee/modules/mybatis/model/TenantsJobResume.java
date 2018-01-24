package com.fbee.modules.mybatis.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 门店职位应聘信息
 */
public class TenantsJobResume implements Serializable{

    private Integer id;
    /**
     * 职位id
     */
    private Integer jobId;
    /**
     * 订单编号
     */
    private String  orderNo;
    /**
     * 招聘方门店id
     */
    private Integer jobTenantId;
    /**
     * 招聘方门店用户id
     */
    private Integer jobTenantUserId;
    /**
     * 应聘方门店ID
     */
    private Integer resumeTenantId;
    /**
     * 应聘方门店用户id
     */
    private Integer resumeTenantUserId;
    /**
     * 应聘方投递阿姨id
     */
    private Integer resumeTenantStaffId;

    /**
     * 阿姨详情
     */
    private TenantsStaffsInfo staffsInfo;

    /**
     * 投递日期
     */
    private Date    applyDate;
    /**
     * 审核日期
     */
    private Date    checkDate;
    /**
     * 状态1待处理，2已通过，3已拒绝
     */
    private String  status;
    /**
     * 备注
     */
    private String  remarks;
    private String  isUsable;
    private Date    addTime;
    private String  addAccount;
    private Date    modifyTime;
    private String  modifyAccount;

    /**
     * 是否使用保证金1是0否
     */
    private BigDecimal deposit;

    public TenantsStaffsInfo getStaffsInfo() {
        return staffsInfo;
    }

    public void setStaffsInfo(TenantsStaffsInfo staffsInfo) {
        this.staffsInfo = staffsInfo;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getJobTenantId() {
        return jobTenantId;
    }

    public void setJobTenantId(Integer jobTenantId) {
        this.jobTenantId = jobTenantId;
    }

    public Integer getJobTenantUserId() {
        return jobTenantUserId;
    }

    public void setJobTenantUserId(Integer jobTenantUserId) {
        this.jobTenantUserId = jobTenantUserId;
    }

    public Integer getResumeTenantId() {
        return resumeTenantId;
    }

    public void setResumeTenantId(Integer resumeTenantId) {
        this.resumeTenantId = resumeTenantId;
    }

    public Integer getResumeTenantUserId() {
        return resumeTenantUserId;
    }

    public void setResumeTenantUserId(Integer resumeTenantUserId) {
        this.resumeTenantUserId = resumeTenantUserId;
    }

    public Integer getResumeTenantStaffId() {
        return resumeTenantStaffId;
    }

    public void setResumeTenantStaffId(Integer resumeTenantStaffId) {
        this.resumeTenantStaffId = resumeTenantStaffId;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getIsUsable() {
        return isUsable;
    }

    public void setIsUsable(String isUsable) {
        this.isUsable = isUsable;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getAddAccount() {
        return addAccount;
    }

    public void setAddAccount(String addAccount) {
        this.addAccount = addAccount;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyAccount() {
        return modifyAccount;
    }

    public void setModifyAccount(String modifyAccount) {
        this.modifyAccount = modifyAccount;
    }
}
