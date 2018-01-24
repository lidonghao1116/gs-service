package com.fbee.modules.mybatis.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TenantsJobs implements Serializable{
    
	private static final long serialVersionUID = 1L;

	/**
     * 表：tenants_jobs
     * 字段：id
     * 注释：主键ID
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 表：tenants_jobs
     * 字段：TENANT_ID
     * 注释：租户id
     *
     * @mbggenerated
     */
    private Integer tenantId;
    private Integer tenantUserId;

    /**
     * 表：tenants_jobs
     * 字段：SERVICE_TYPE
     * 注释：服务工种
     *
     * @mbggenerated
     */
    private String serviceType;

    /**
     * 表：tenants_jobs
     * 字段：POSITION_NAME
     * 注释：岗位名称
     *
     * @mbggenerated
     */
    private String positionName;

    /**
     * 表：tenants_jobs
     * 字段：AGE
     * 注释：年龄
     *
     * @mbggenerated
     */
    private String age;

    /**
     * 表：tenants_jobs
     * 字段：SALARY
     * 注释：工资
     *
     * @mbggenerated
     */
    private String salary;

    /**
     * 表：tenants_jobs
     * 字段：DEMAND
     * 注释：要求
     *
     * @mbggenerated
     */
    private String demand;

    /**
     * 表：tenants_jobs
     * 注释：是否上架
     *
     * @mbggenerated
     */
    private String status;

    /**
     * 表：tenants_jobs
     * 字段：add_time
     * 注释：添加时间
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * 表：tenants_jobs
     * 字段：add_account
     * 注释：添加人
     *
     * @mbggenerated
     */
    private String addAccount;

    /**
     * 表：tenants_jobs
     * 字段：modify_time
     * 注释：修改时间
     *
     * @mbggenerated
     */
    private Date modifyTime;

    /**
     * 表：tenants_jobs
     * 字段：modify_account
     * 注释：修改人
     *
     * @mbggenerated
     */
    private String modifyAccount;
    /**
     * @author xiehui
     * @return
     * @param省
     */
	private String serviceProvince;
	/**
	 * @author xiehui
	 * @return
	 * @param市
	 */
	private String serviceCity;

	private BigDecimal deposit;

	private String orderStatus;

    private String cancelReason;

    private String orderNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getTenantUserId() {
        return tenantUserId;
    }

    public void setTenantUserId(Integer tenantUserId) {
        this.tenantUserId = tenantUserId;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public String getServiceProvince() {
        return serviceProvince;
    }

    public void setServiceProvince(String serviceProvince) {
        this.serviceProvince = serviceProvince;
    }

    public String getServiceCity() {
		return serviceCity;
	}

	public void setServiceCity(String serviceCity) {
		this.serviceCity = serviceCity;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType == null ? null : serviceType.trim();
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName == null ? null : positionName.trim();
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary == null ? null : salary.trim();
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand == null ? null : demand.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}