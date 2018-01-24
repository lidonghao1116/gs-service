package com.fbee.modules.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;

public class Orders {
    /**
     * 表：orders
     * 字段：ORDER_NO
     * 注释：订单流水号
     *
     * @mbggenerated
     */
    private String orderNo;

    /**
     * 表：orders
     * 字段：TENANT_ID
     * 注释：租户id
     *
     * @mbggenerated
     */
    private Integer tenantId;

    /**
     * 表：orders
     * 字段：MEMBER_ID
     * 注释：客户ID
     *
     * @mbggenerated
     */
    private Integer memberId;

    /**
     * 表：orders
     * 字段：AMOUNT
     * 注释：订单金额
     *
     * @mbggenerated
     */
    private BigDecimal amount;

    /**
     * 表：orders
     * 字段：ORDER_TIME
     * 注释：提交时间
     *
     * @mbggenerated
     */
    private Date orderTime;

    /**
     * 表：orders
     * 字段：ORDER_STATUS
     * 注释：订单状态 01 待支付定金 02 待面试 03 待支付尾款 04 已完成 05 已取消 06 待完成 07已结单 08结单后阿姨更换处理
     *
     * @mbggenerated
     */
    private String orderStatus;

    /**
     * 表：orders
     * 字段：SERVICE_ITEM_CODE
     * 注释：服务工种
     *
     * @mbggenerated
     */
    private String serviceItemCode;

    /**
     * 表：orders
     * 字段：STAFF_ID
     * 注释：阿姨id
     *
     * @mbggenerated
     */
    private Integer staffId;

    /**
     * 表：orders
     * 字段：ORDER_SOURCE
     * 注释：订单来源  01 本地订单 02 网络订单 03 淘蜂享
     *
     * @mbggenerated
     */
    private String orderSource;

    /**
     * 表：orders
     * 字段：ORDER_DEPOSIT
     * 注释：支付定金
     *
     * @mbggenerated
     */
    private BigDecimal orderDeposit;

    /**
     * 表：orders
     * 字段：ORDER_BALANCE
     * 注释：支付尾款
     *
     * @mbggenerated
     */
    private BigDecimal orderBalance;

    /**
     * 表：orders
     * 字段：DEPOSIT_DATE
     * 注释：定金支付时间
     *
     * @mbggenerated
     */
    private Date depositDate;

    /**
     * 表：orders
     * 字段：IDEPOSIT_OVER
     * 注释：定金是否支付 01未支付 02已支付
     *
     * @mbggenerated
     */
    private String idepositOver;

    /**
     * 表：orders
     * 字段：IS_INTERVIEW
     * 注释：是否已面试 01未面试 02已面试
     *
     * @mbggenerated
     */
    private String isInterview;

    /**
     * 表：orders
     * 字段：PASS_VIEW_DATE
     * 注释：通过面试时间
     *
     * @mbggenerated
     */
    private Date passViewDate;

    /**
     * 表：orders
     * 字段：BALANCE_DATE
     * 注释：尾款支付时间
     *
     * @mbggenerated
     */
    private Date balanceDate;

    /**
     * 表：orders
     * 字段：IS_LOCK
     * 注释：是否锁定(预留字段)
     *
     * @mbggenerated
     */
    private String isLock;

    /**
     * 表：orders
     * 字段：add_time
     * 注释：添加时间
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * 表：orders
     * 字段：add_account
     * 注释：添加人
     *
     * @mbggenerated
     */
    private String addAccount;

    /**
     * 表：orders
     * 字段：modify_time
     * 注释：修改时间
     *
     * @mbggenerated
     */
    private Date modifyTime;

    /**
     * 表：orders
     * 字段：modify_account
     * 注释：修改人
     *
     * @mbggenerated
     */
    private String modifyAccount;

    /**
     * 表：orders
     * 字段：RESERVE_ORDER_NO
     * 注释：关联预约编号
     *
     * @mbggenerated
     */
    private String reserveOrderNo;

    /**
     * 表：orders
     * 字段：ORDER_TYPE
     * 注释：订单类型
     *
     * @mbggenerated
     */
    private String orderType;

    /**
     * 表：orders
     * 字段：MEMBER_MOBILE
     * 注释：手机号码
     *
     * @mbggenerated
     */
    private String memberMobile;

    /**
     * 表：orders
     * 字段：REMARK
     * 注释：
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 表：orders
     * 字段：SHARE_ORDER_NO
     * 注释：分享订单号
     *
     * @mbggenerated
     */
    private String shareOrderNo;

    /**
     * 表：orders
     * 字段：BALANCE_OVER
     * 注释：尾款是否支付 01未支付 02已支付
     *
     * @mbggenerated
     */
    private String balanceOver;

    /**
     * 表：orders
     * 字段：is_usable
     * 注释：
     *
     * @mbggenerated
     */
    private String isUsable;


	private BigDecimal serviceCharge;

	/**
	 * 表：orders
	 * 字段：totalPrice
	 * 注释：总价
	 *
	 * @mbggenerated
	 */
	private String totalPrice;

	private Integer userId;

	private Integer stafffSerItemId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
     * 表：orders
     * 字段：CANCLE_REASON
     * 注释：订单取消原因
     *
     * @mbggenerated
     */
    private String cancleReason;

    private Integer isPublishedJob;
    private Integer isLocalStaff;

	public BigDecimal getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public Integer getIsPublishedJob() {
		return isPublishedJob;
	}

	public void setIsPublishedJob(Integer isPublishedJob) {
		this.isPublishedJob = isPublishedJob;
	}

	public Integer getIsLocalStaff() {
		return isLocalStaff;
	}

	public void setIsLocalStaff(Integer isLocalStaff) {
		this.isLocalStaff = isLocalStaff;
	}

	public String getOrderNo() {
        return orderNo;
    }
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

    public Integer getTenantId() {
        return tenantId;
    }
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.ORDER_TIME
	 * @return  the value of orders.ORDER_TIME
	 * @mbggenerated  Wed Mar 01 14:54:06 CST 2017
	 */
	public Date getOrderTime() {
		return orderTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.ORDER_TIME
	 * @param orderTime  the value for orders.ORDER_TIME
	 * @mbggenerated  Wed Mar 01 14:54:06 CST 2017
	 */
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.ORDER_STATUS
	 * @return  the value of orders.ORDER_STATUS
	 * @mbggenerated  Wed Mar 01 14:54:06 CST 2017
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.ORDER_STATUS
	 * @param orderStatus  the value for orders.ORDER_STATUS
	 * @mbggenerated  Wed Mar 01 14:54:06 CST 2017
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.SERVICE_ITEM_CODE
	 * @return  the value of orders.SERVICE_ITEM_CODE
	 * @mbggenerated  Wed Mar 01 14:54:06 CST 2017
	 */
	public String getServiceItemCode() {
		return serviceItemCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.SERVICE_ITEM_CODE
	 * @param serviceItemCode  the value for orders.SERVICE_ITEM_CODE
	 * @mbggenerated  Wed Mar 01 14:54:06 CST 2017
	 */
	public void setServiceItemCode(String serviceItemCode) {
		this.serviceItemCode = serviceItemCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.STAFF_ID
	 * @return  the value of orders.STAFF_ID
	 * @mbggenerated  Wed Mar 01 14:54:06 CST 2017
	 */
	public Integer getStaffId() {
		return staffId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.STAFF_ID
	 * @param staffId  the value for orders.STAFF_ID
	 * @mbggenerated  Wed Mar 01 14:54:06 CST 2017
	 */
	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.ORDER_SOURCE
	 * @return  the value of orders.ORDER_SOURCE
	 * @mbggenerated  Wed Mar 01 14:54:06 CST 2017
	 */
	public String getOrderSource() {
		return orderSource;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.ORDER_SOURCE
	 * @param orderSource  the value for orders.ORDER_SOURCE
	 * @mbggenerated  Wed Mar 01 14:54:06 CST 2017
	 */
	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.ORDER_DEPOSIT
	 * @return  the value of orders.ORDER_DEPOSIT
	 * @mbggenerated  Wed Mar 01 14:54:06 CST 2017
	 */
	public BigDecimal getOrderDeposit() {
		return orderDeposit;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.ORDER_DEPOSIT
	 * @param orderDeposit  the value for orders.ORDER_DEPOSIT
	 * @mbggenerated  Wed Mar 01 14:54:06 CST 2017
	 */
	public void setOrderDeposit(BigDecimal orderDeposit) {
		this.orderDeposit = orderDeposit;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.ORDER_BALANCE
	 * @return  the value of orders.ORDER_BALANCE
	 * @mbggenerated  Wed Mar 01 14:54:06 CST 2017
	 */
	public BigDecimal getOrderBalance() {
		return orderBalance;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.ORDER_BALANCE
	 * @param orderBalance  the value for orders.ORDER_BALANCE
	 * @mbggenerated  Wed Mar 01 14:54:06 CST 2017
	 */
	public void setOrderBalance(BigDecimal orderBalance) {
		this.orderBalance = orderBalance;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.DEPOSIT_DATE
	 * @return  the value of orders.DEPOSIT_DATE
	 * @mbggenerated  Wed Mar 01 14:54:06 CST 2017
	 */
	public Date getDepositDate() {
		return depositDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.DEPOSIT_DATE
	 * @param depositDate  the value for orders.DEPOSIT_DATE
	 * @mbggenerated  Wed Mar 01 14:54:06 CST 2017
	 */
	public void setDepositDate(Date depositDate) {
		this.depositDate = depositDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.IDEPOSIT_OVER
	 * @return  the value of orders.IDEPOSIT_OVER
	 * @mbggenerated  Wed Mar 01 14:54:06 CST 2017
	 */
	public String getIdepositOver() {
		return idepositOver;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.IDEPOSIT_OVER
	 * @param idepositOver  the value for orders.IDEPOSIT_OVER
	 * @mbggenerated  Wed Mar 01 14:54:06 CST 2017
	 */
	public void setIdepositOver(String idepositOver) {
		this.idepositOver = idepositOver;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.IS_INTERVIEW
	 * @return  the value of orders.IS_INTERVIEW
	 * @mbggenerated  Wed Mar 01 14:54:06 CST 2017
	 */
	public String getIsInterview() {
		return isInterview;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.IS_INTERVIEW
	 * @param isInterview  the value for orders.IS_INTERVIEW
	 * @mbggenerated  Wed Mar 01 14:54:06 CST 2017
	 */
	public void setIsInterview(String isInterview) {
		this.isInterview = isInterview;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.PASS_VIEW_DATE
	 * @return  the value of orders.PASS_VIEW_DATE
	 * @mbggenerated  Wed Mar 01 14:54:06 CST 2017
	 */
	public Date getPassViewDate() {
		return passViewDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.PASS_VIEW_DATE
	 * @param passViewDate  the value for orders.PASS_VIEW_DATE
	 * @mbggenerated  Wed Mar 01 14:54:06 CST 2017
	 */
	public void setPassViewDate(Date passViewDate) {
		this.passViewDate = passViewDate;
	}



	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.IS_LOCK
	 * @param isLock  the value for orders.IS_LOCK
	 * @mbggenerated  Wed Mar 01 14:54:06 CST 2017
	 */
	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.add_time
	 * @return  the value of orders.add_time
	 * @mbggenerated  Wed Mar 01 14:54:06 CST 2017
	 */
	public Date getAddTime() {
		return addTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.add_time
	 * @param addTime  the value for orders.add_time
	 * @mbggenerated  Wed Mar 01 14:54:06 CST 2017
	 */
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.add_account
	 * @return  the value of orders.add_account
	 * @mbggenerated  Wed Mar 01 14:54:06 CST 2017
	 */
	public String getAddAccount() {
		return addAccount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.add_account
	 * @param addAccount  the value for orders.add_account
	 * @mbggenerated  Wed Mar 01 14:54:06 CST 2017
	 */
	public void setAddAccount(String addAccount) {
		this.addAccount = addAccount;
	}

    public Date getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(Date balanceDate) {
        this.balanceDate = balanceDate;
    }

    public String getIsLock() {
        return isLock;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public String getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile == null ? null : memberMobile.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getShareOrderNo() {
        return shareOrderNo;
    }

    public void setShareOrderNo(String shareOrderNo) {
        this.shareOrderNo = shareOrderNo == null ? null : shareOrderNo.trim();
    }

    public String getBalanceOver() {
        return balanceOver;
    }

    public void setBalanceOver(String balanceOver) {
        this.balanceOver = balanceOver == null ? null : balanceOver.trim();
    }

    public String getIsUsable() {
        return isUsable;
    }

    public void setIsUsable(String isUsable) {
        this.isUsable = isUsable == null ? null : isUsable.trim();
    }

    public String getCancleReason() {
        return cancleReason;
    }

    public void setCancleReason(String cancleReason) {
        this.cancleReason = cancleReason == null ? null : cancleReason.trim();
    }
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
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
	public String getReserveOrderNo() {
		return reserveOrderNo;
	}
	public void setReserveOrderNo(String reserveOrderNo) {
		this.reserveOrderNo = reserveOrderNo;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}

	public void setStafffSerItemId(Integer stafffSerItemId) {
		this.stafffSerItemId = stafffSerItemId;
	}

	public Integer getStafffSerItemId() {
		return stafffSerItemId;
	}
}