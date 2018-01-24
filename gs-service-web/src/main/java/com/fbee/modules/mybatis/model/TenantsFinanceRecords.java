package com.fbee.modules.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;

public class TenantsFinanceRecords {
    /**
     * 表：tenants_finance_records
     * 字段：IN_OUT_NO
     * 注释：交易流水号
     *
     * @mbggenerated
     */
    private String inOutNo;

    /**
     * 表：tenants_finance_records
     * 字段：TENANT_ID
     * 注释：租户id
     *
     * @mbggenerated
     */
    private Integer tenantId;

    /**
     * 表：tenants_finance_records
     * 字段：STAFF_ID
     * 注释：员工id
     *
     * @mbggenerated
     */
    private Integer staffId;

    /**
     * 表：tenants_finance_records
     * 字段：PAY_NO
     * 注释：支付流水号
     *
     * @mbggenerated
     */
    private String payNo;

    /**
     * 表：tenants_finance_records
     * 字段：PAY_TYPE
     * 注释：交易类型 01 订单支付 02成单加价 03成单奖励 04账户充值 05账户提现 06会员续费
     *
     * @mbggenerated
     */
    private String payType;

    /**
     * 表：tenants_finance_records
     * 字段：RELATED_TRANS
     * 注释：关联流水号
     *
     * @mbggenerated
     */
    private String relatedTrans;

    /**
     * 表：tenants_finance_records
     * 字段：IN_OUT_CATEGORY
     * 注释：收支类目
     *
     * @mbggenerated
     */
    private String inOutCategory;

    /**
     * 表：tenants_finance_records
     * 字段：IN_OUT_TYPE
     * 注释：收支类型
     *
     * @mbggenerated
     */
    private String inOutType;

    /**
     * 表：tenants_finance_records
     * 字段：IN_OUT_MANTISSA
     * 注释：收支尾数
     *
     * @mbggenerated
     */
    private String inOutMantissa;

    /**
     * 表：tenants_finance_records
     * 字段：IN_OUT_OBJECT
     * 注释：收支对象
     *
     * @mbggenerated
     */
    private String inOutObject;

    /**
     * 表：tenants_finance_records
     * 字段：IN_OUT_AMOUNT
     * 注释：交易金额
     *
     * @mbggenerated
     */
    private BigDecimal inOutAmount;

    /**
     * 表：tenants_finance_records
     * 字段：TRANS_TYPE
     * 注释：交易方式 01线上 02线下
     *
     * @mbggenerated
     */
    private String transType;

    /**
     * 表：tenants_finance_records
     * 字段：remarks
     * 注释：备注
     *
     * @mbggenerated
     */
    private String remarks;

    /**
     * 表：tenants_finance_records
     * 字段：add_time
     * 注释：添加时间(收支时间)
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * 表：tenants_finance_records
     * 字段：add_account
     * 注释：添加人
     *
     * @mbggenerated
     */
    private String addAccount;

    /**
     * 表：tenants_finance_records
     * 字段：modify_time
     * 注释：修改时间
     *
     * @mbggenerated
     */
    private Date modifyTime;

    /**
     * 表：tenants_finance_records
     * 字段：modify_account
     * 注释：修改人
     *
     * @mbggenerated
     */
    private String modifyAccount;

    /**
     * 表：tenants_finance_records
     * 字段：is_usable
     * 注释：是否可用
     *
     * @mbggenerated
     */
    private String isUsable;

    /**
     * 表：tenants_finance_records
     * 字段：status
     * 注释：状态（01、待处理 02、处理中 03、已处理）
     *
     * @mbggenerated
     */
    private String status;

    /**
     * 表：tenants_finance_records
     * 字段：drawee_id
     * 注释：付款人id
     *
     * @mbggenerated
     */
    private Integer draweeId;

    /**
     * 表：tenants_finance_records
     * 字段：payee_id
     * 注释：收款人id
     *
     * @mbggenerated
     */
    private Integer payeeId;

    /**
     * 表：tenants_finance_records
     * 字段：drawee_type
     * 注释：付款方类型
     *
     * @mbggenerated
     */
    private String draweeType;

    /**
     * 表：tenants_finance_records
     * 字段：payee_type
     * 注释：收款方类型
     *
     * @mbggenerated
     */
    private String payeeType;

    public String getInOutNo() {
        return inOutNo;
    }

    public void setInOutNo(String inOutNo) {
        this.inOutNo = inOutNo == null ? null : inOutNo.trim();
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo == null ? null : payNo.trim();
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public String getRelatedTrans() {
        return relatedTrans;
    }

    public void setRelatedTrans(String relatedTrans) {
        this.relatedTrans = relatedTrans == null ? null : relatedTrans.trim();
    }

    public String getInOutCategory() {
        return inOutCategory;
    }

    public void setInOutCategory(String inOutCategory) {
        this.inOutCategory = inOutCategory == null ? null : inOutCategory.trim();
    }

    public String getInOutType() {
        return inOutType;
    }

    public void setInOutType(String inOutType) {
        this.inOutType = inOutType == null ? null : inOutType.trim();
    }

    public String getInOutMantissa() {
        return inOutMantissa;
    }

    public void setInOutMantissa(String inOutMantissa) {
        this.inOutMantissa = inOutMantissa == null ? null : inOutMantissa.trim();
    }

    public String getInOutObject() {
        return inOutObject;
    }

    public void setInOutObject(String inOutObject) {
        this.inOutObject = inOutObject == null ? null : inOutObject.trim();
    }

    public BigDecimal getInOutAmount() {
        return inOutAmount;
    }

    public void setInOutAmount(BigDecimal inOutAmount) {
        this.inOutAmount = inOutAmount;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
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

    public String getIsUsable() {
        return isUsable;
    }

    public void setIsUsable(String isUsable) {
        this.isUsable = isUsable == null ? null : isUsable.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getDraweeId() {
        return draweeId;
    }

    public void setDraweeId(Integer draweeId) {
        this.draweeId = draweeId;
    }

    public Integer getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(Integer payeeId) {
        this.payeeId = payeeId;
    }

    public String getDraweeType() {
        return draweeType;
    }

    public void setDraweeType(String draweeType) {
        this.draweeType = draweeType == null ? null : draweeType.trim();
    }

    public String getPayeeType() {
        return payeeType;
    }

    public void setPayeeType(String payeeType) {
        this.payeeType = payeeType == null ? null : payeeType.trim();
    }
}