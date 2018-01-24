package com.fbee.modules.mybatis.model;

import java.io.Serializable;
import java.util.Date;

public class TenantsInfo implements Serializable{
    
	private static final long serialVersionUID = 1L;

	/**
     * 表：tenants_info
     * 字段：TENANT_ID
     * 注释：租户id
     *
     * @mbggenerated
     */
    private Integer tenantId;

    /**
     * 表：tenants_info
     * 字段：TENANT_NAME
     * 注释：租户姓名
     *
     * @mbggenerated
     */
    private String tenantName;

    /**
     * 表：tenants_info
     * 字段：CONTACT_PHONE
     * 注释：联系电话
     *
     * @mbggenerated
     */
    private String contactPhone;

    /**
     * 表：tenants_info
     * 字段：CONTACT_ADDRESS
     * 注释：联系地址
     *
     * @mbggenerated
     */
    private String contactAddress;

    /**
     * 表：tenants_info
     * 字段：TENANT_ACCOUNT
     * 注释：系统生成账号
     *
     * @mbggenerated
     */
    private String tenantAccount;

    /**
     * 表：tenants_info
     * 字段：CERT_NO
     * 注释：证件号码
     *
     * @mbggenerated
     */
    private String certNo;

    /**
     * 表：tenants_info
     * 字段：CERT_TYPE
     * 注释：证件类型
     *
     * @mbggenerated
     */
    private String certType;

    /**
     * 表：tenants_info
     * 字段：remarks
     * 注释：备注
     *
     * @mbggenerated
     */
    private String remarks;

    /**
     * 表：tenants_info
     * 字段：is_usable
     * 注释：是否可用
     *
     * @mbggenerated
     */
    private String isUsable;

    /**
     * 表：tenants_info
     * 字段：add_time
     * 注释：添加时间
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * 表：tenants_info
     * 字段：add_account
     * 注释：添加人
     *
     * @mbggenerated
     */
    private String addAccount;

    /**
     * 表：tenants_info
     * 字段：modify_time
     * 注释：修改时间
     *
     * @mbggenerated
     */
    private Date modifyTime;

    /**
     * 表：tenants_info
     * 字段：modify_account
     * 注释：修改人
     *
     * @mbggenerated
     */
    private String modifyAccount;

    /**
     * 表：tenants_info
     * 字段：TENANT_CODE
     * 注释：租户编码
     *
     * @mbggenerated
     */
    private String tenantCode;

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName == null ? null : tenantName.trim();
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress == null ? null : contactAddress.trim();
    }

    public String getTenantAccount() {
        return tenantAccount;
    }

    public void setTenantAccount(String tenantAccount) {
        this.tenantAccount = tenantAccount == null ? null : tenantAccount.trim();
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo == null ? null : certNo.trim();
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType == null ? null : certType.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getIsUsable() {
        return isUsable;
    }

    public void setIsUsable(String isUsable) {
        this.isUsable = isUsable == null ? null : isUsable.trim();
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
        this.addAccount = addAccount == null ? null : addAccount.trim();
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
        this.modifyAccount = modifyAccount == null ? null : modifyAccount.trim();
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode == null ? null : tenantCode.trim();
    }
}