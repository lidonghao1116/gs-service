package com.fbee.modules.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;

public class TenantsTradeRecords {
    /**
     * 表：tenants_trade_records
     * 字段：TRADE_NO
     * 注释：账户流水号
     * 注释：账户流水
     *
     * @mbggenerated
     */
    private String tradeNo;

    /**
     * 关联订单号
     */
    private String orderNo;

    /**
     * 表：tenants_trade_records
     * 字段：TENANT_ID
     * 注释：租户id
     *
     * @mbggenerated
     */
    private Integer tenantId;

    /**
     * 表：tenants_trade_records
     * 字段：TRADE_TIME
     * 注释：交易时间
     *
     * @mbggenerated
     */
    private Date tradeTime;

    /**
     * 表：tenants_trade_records
     * 字段：FINANCE_TYPE
     * 注释：财务类型 01收入 02支出
     *
     * @mbggenerated
     */
    private String financeType;

    /**
     * 表：tenants_trade_records
     * 字段：TRADE_AMOUNT
     * 注释：交易金额
     *
     * @mbggenerated
     */
    private BigDecimal tradeAmount;

    /**
     * 表：tenants_trade_records
     * 字段：STATUS
     * 注释：交易状态 01处理中 02交易成功 03交易失败
     *
     * @mbggenerated
     */
    private String status;

    /**
     * 表：tenants_trade_records
     * 字段：COUNTER_FEE
     * 注释：手续费
     *
     * @mbggenerated
     */
    private BigDecimal counterFee;

    /**
     * 表：tenants_trade_records
     * 字段：IN_OUT_NO
     * 注释：交易流水号
     *
     * @mbggenerated
     */
    private String inOutNo;

    /**
     * 表：tenants_trade_records
     * 字段：TRADE_TYPE
<<<<<<< HEAD
     * 注释：交易类型 01：充值 02：提现 03:冻结 04:解冻 05：手续费 06：支付定金 07：支付尾款 08成单奖励  09:退款
=======
     * 注释：交易类型 01：充值 02：提现 03:冻结
>>>>>>> origin/fbeeWebConsole_server-1.0-zlz
     *
     * @mbggenerated
     */
    private String tradeType;

    /**
     * 表：tenants_trade_records
     * 字段：REMARKS
     * 注释：备注
     *
     * @mbggenerated
     */
    private String remarks;

    /**
     * 表：tenants_trade_records
     * 字段：add_time
     * 注释：添加时间
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * 表：tenants_trade_records
     * 字段：add_account
     * 注释：添加人
     *
     * @mbggenerated
     */
    private String addAccount;

    /**
     * 表：tenants_trade_records
     * 字段：modify_time
     * 注释：修改时间
     *
     * @mbggenerated
     */
    private Date modifyTime;

    /**
     * 表：tenants_trade_records
     * 字段：modify_account
     * 注释：修改人
     *
     * @mbggenerated
     */
    private String modifyAccount;

    /**
     * 表：tenants_trade_records
     * 字段：is_usable
     * 注释：
     *
     * @mbggenerated
     */
    private String isUsable;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo == null ? null : tradeNo.trim();
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getFinanceType() {
        return financeType;
    }

    public void setFinanceType(String financeType) {
        this.financeType = financeType == null ? null : financeType.trim();
    }

    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public BigDecimal getCounterFee() {
        return counterFee;
    }

    public void setCounterFee(BigDecimal counterFee) {
        this.counterFee = counterFee;
    }

    public String getInOutNo() {
        return inOutNo;
    }

    public void setInOutNo(String inOutNo) {
        this.inOutNo = inOutNo == null ? null : inOutNo.trim();
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType == null ? null : tradeType.trim();
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getIsUsable() {
        return isUsable;
    }

    public void setIsUsable(String isUsable) {
        this.isUsable = isUsable == null ? null : isUsable.trim();
    }
}