package com.fbee.modules.form;

import com.fbee.modules.core.persistence.ModelSerializable;

import java.math.BigDecimal;

public class ReserveOrdersForm implements ModelSerializable {

	private static final long serialVersionUID = 1L;
	
	/**
     * 表：tenants_customers_base
     * 字段：CUSTOMER_MOBILE
     * 注释：客户电话
     *
     * @mbggenerated
     */
    private String customerMobile;
    
    /**
     * 表：tenants_customers_base
     * 字段：CUSTOMER_NAME
     * 注释：客户姓名
     *
     * @mbggenerated
     */
    private String customerName;
    
    /**
     * 表：tenants_staffs_info
     * 字段：STAFF_ID
     * 注释：员工id
     *
     * @mbggenerated
     */
    private Integer staffId;
    
    /**
     * 表：reserve_orders
     * 字段：SERVICE_ITEM_CODE
     * 注释：服务工种
     *
     * @mbggenerated
     */
    private String serviceItemCode;
    
    /**
     * 性别
     */
    private String sex;

    private BigDecimal salary;
    private String salaryType;

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public String getSalaryType() {
		return salaryType;
	}

	public void setSalaryType(String salaryType) {
		this.salaryType = salaryType;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

	public String getServiceItemCode() {
		return serviceItemCode;
	}

	public void setServiceItemCode(String serviceItemCode) {
		this.serviceItemCode = serviceItemCode;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}
