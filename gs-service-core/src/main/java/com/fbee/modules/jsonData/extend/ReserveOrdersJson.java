package com.fbee.modules.jsonData.extend;

import com.fbee.modules.core.persistence.ModelSerializable;

import java.math.BigDecimal;

public class ReserveOrdersJson implements ModelSerializable {

	/*
	 * 预约订单查询
	 */
	
	private static final long serialVersionUID = 1L;
	
	/**
     * 表：reserve_orders
     * 字段：ORDER_NO
     * 注释：下单时间
     *
     * @mbggenerated
     */
    private String dateTime;
    
    /**
     * 表：reserve_orders
     * 字段：ORDER_NO
     * 注释：阿姨头像
     *
     * @mbggenerated
     */
    private String headImage;
	
	/**
     * 表：reserve_orders
     * 字段：ORDER_NO
     * 注释：订单流水号
     *
     * @mbggenerated
     */
    private String orderNo;
	
	/**
     * 表：reserve_orders
     * 字段：ORDER_STATUS
     * 注释：预约状态 01待处理 02 已处理 03 已取消
     *
     * @mbggenerated
     */
    private String orderStatus;
    
    /**
     * 表：reserve_orders
     * 字段：SERVICE_ITEM_CODE
     * 注释：服务工种
     *
     * @mbggenerated
     */
    private String serviceItemCode;
    
    /**
     * 表：tenants_staffs_info
     * 字段：STAFF_NAME
     * 注释：员工姓名
     *
     * @mbggenerated
     */
    private String staffName;
    
    /**
     * 表：tenants_staffs_info
     * 字段：AGE
     * 注释：年龄
     *
     * @mbggenerated
     */
    private String age;
    
    /**
     * 表：tenants_staffs_info
     * 字段：ZODIAC
     * 注释：属性
     *
     * @mbggenerated
     */
    private String zodiac;

    /**
     * 表：tenants_staffs_info
     * 字段：NATIVE_PLACE
     * 注释：籍贯
     *
     * @mbggenerated
     */
    private String nativePlace;
    
    /**
     * 表：tenants_service_items
     * 字段：SERVICE_PRICE
     * 注释：服务价格
     *
     * @mbggenerated
     */
    private BigDecimal servicePrice;
    
    /**
     * 表：reserve_orders_customer_info
     * 字段：SERVICE_START
     * 注释：服务开始时间
     *
     * @mbggenerated
     */
    private String serviceStart;

    /**
     * 表：reserve_orders_customer_info
     * 字段：SERVICE_END
     * 注释：服务结束时间
     *
     * @mbggenerated
     */
    private String serviceEnd;
    
    /**
     * 表：tenants_apps
     * 字段：WEBSITE_NAME
     * 注释：站点名称
     *
     * @mbggenerated
     */
    private String websiteName;
    
    /**
     * 表：remarks
     * 字段：REMARKS
     * 注释：备注
     *
     * @mbggenerated
     */
    private String remarks;
    /**
     * 二级域名
     */
    private String Domain;
    
	/**
	 * 表：tenants_staffs_info 字段：SEX 注释：性别
	 *
	 * @mbggenerated
	 */
	private String sex;

	private String unit;
	private String unitValue;

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUnitValue() {
		return unitValue;
	}

	public void setUnitValue(String unitValue) {
		this.unitValue = unitValue;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
	
	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getServiceItemCode() {
		return serviceItemCode;
	}

	public void setServiceItemCode(String serviceItemCode) {
		this.serviceItemCode = serviceItemCode;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getZodiac() {
		return zodiac;
	}

	public void setZodiac(String zodiac) {
		this.zodiac = zodiac;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}


	public BigDecimal getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(BigDecimal servicePrice) {
		this.servicePrice = servicePrice;
	}

	public String getServiceStart() {
		return serviceStart;
	}

	public void setServiceStart(String serviceStart) {
		this.serviceStart = serviceStart;
	}

	public String getServiceEnd() {
		return serviceEnd;
	}

	public void setServiceEnd(String serviceEnd) {
		this.serviceEnd = serviceEnd;
	}

	public String getWebsiteName() {
		return websiteName;
	}

	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}

	public String getDomain() {
		return Domain;
	}

	public void setDomain(String domain) {
		Domain = domain;
	}

	
	

}
