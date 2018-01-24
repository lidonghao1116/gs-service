package com.fbee.modules.form;

import com.fbee.modules.core.persistence.ModelSerializable;

public class ServiceInfoForm implements ModelSerializable {

	private static final long serialVersionUID = 1L;
	
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
     * 表：reserve_orders_customer_info
     * 字段：IS_BABY_BORN
     * 注释：宝宝是否已出生
     *
     * @mbggenerated
     */
    private String isBabyBorn;
    
    /**
     * 表：reserve_orders_customer_info
     * 字段：EXPECTED_BIRTH
     * 注释：预产期
     *
     * @mbggenerated
     */
    private String expectedBirth;
    
    /**
     * 表：reserve_orders_customer_info
     * 字段：PET_RAISING
     * 注释：饲养宠物
     *
     * @mbggenerated
     */
    private String petRaising;
    
    /**
     * 表：reserve_orders_customer_info
     * 字段：SALARY_SKILLS
     * 注释：服务内容
     *
     * @mbggenerated
     */
    private String[] salarySkills;
    
    /**
     * 表：reserve_orders_customer_info
     * 字段：OTHER_REQUIREMENTS
     * 注释：其他需求
     *
     * @mbggenerated
     */
    private String[] otherRequirements;
    
    /**
     * 表：reserve_orders_customer_info
     * 字段：BABY_COUNT
     * 注释：宝宝数
     *
     * @mbggenerated
     */
    private Integer babyCount;
    
    /**
     * 表：reserve_orders_customer_info
     * 字段：BABY_AGE
     * 注释：宝宝月龄
     *
     * @mbggenerated
     */
    private Integer babyAge;
    
    /**
     * 表：reserve_orders_customer_info
     * 字段：SERVICE_TYPE
     * 注释：服务类型
     *
     * @mbggenerated
     */
    private String serviceType;
    
    /**
     * 表：reserve_orders_customer_info
     * 字段：CHILDREN_COUNT
     * 注释：儿童数
     *
     * @mbggenerated
     */
    private Integer childrenCount;
    
    /**
     * 表：reserve_orders_customer_info
     * 字段：FAMILY_COUNT
     * 注释：家庭人数
     *
     * @mbggenerated
     */
    private Integer familyCount;
    
    /**
     * 表：reserve_orders_customer_info
     * 字段：CHILDREN_AGE_RANGE
     * 注释：儿童年龄段
     *
     * @mbggenerated
     */
    private String childrenAgeRange;
    
    /**
     * 表：reserve_orders_customer_info
     * 字段：COOKING_REQIREMENTS
     * 注释：烹饪要求
     *
     * @mbggenerated
     */
    private String[] cookingReqirements;
    
    /**
     * 表：reserve_orders_customer_info
     * 字段：OLDER_COUNT
     * 注释：老人数
     *
     * @mbggenerated
     */
    private Integer olderCount;

	/**
     * 表：reserve_orders_customer_info
     * 字段：OLDER_AGE_RANGE
     * 注释：老人年龄段
     *
     * @mbggenerated
     */
    private String olderAgeRange;
    
    /**
     * 表：reserve_orders_customer_info
     * 字段：SELF_CARES
     * 注释：老人能都自理
     *
     * @mbggenerated
     */
    private String selfCares;
    
    /**
     * 表：reserve_orders_customer_info
     * 字段：SPECIFIED_TIME
     * 注释：指定时间
     *
     * @mbggenerated
     */
    private String specifiedTime;
    
    /**
     * 表：reserve_orders_customer_info
     * 字段：LANGUAGE_REQUIREMENTS
     * 注释：语言要求
     *
     * @mbggenerated
     */
    private String[] languageRequirements;
    
    /**
     * 表：reserve_orders_customer_info
     * 字段：SERVICE_PROVICE
     * 注释：服务区域省
     *
     * @mbggenerated
     */
    private String serviceProvice;

    /**
     * 表：reserve_orders_customer_info
     * 字段：SERVICE_CITY
     * 注释：服务区域市
     *
     * @mbggenerated
     */
    private String serviceCity;

    /**
     * 表：reserve_orders_customer_info
     * 字段：SERVICE_COUNTY
     * 注释：服务区域区
     *
     * @mbggenerated
     */
    private String serviceCounty;
    
    /**
     * 表：reserve_orders_customer_info
     * 字段：SERVICE_ADDRESS
     * 注释：服务地址
     *
     * @mbggenerated
     */
    private String serviceAddress;
    
    /**
     * 表：reserve_orders_customer_info
     * 字段：SPECIAL_NEEDS
     * 注释：特殊需求
     *
     * @mbggenerated
     */
    private String specialNeeds;
    
    /**
     * 表：reserve_orders_customer_info
     * 字段：REMARKS
     * 注释：备注（特殊要求）
     *
     * @mbggenerated
     */
    private String remarks;
    
    /**
     * 服务价格
     */
    private String servicePrice;
    
//    /**
//     * 表：reserve_orders_customer_info
//     * 字段：SALARY_MIN
//     * 注释：薪酬范围-最小值
//     *
//     * @mbggenerated
//     */
//    private Integer salaryMin;
//
//    /**
//     * 表：reserve_orders_customer_info
//     * 字段：SALARY_MAX
//     * 注释：薪酬范围-最大值
//     *
//     * @mbggenerated
//     */
//    private Integer salaryMax;
    
    /**
     * 表：reserve_orders_customer_info
     * 字段：WAGE_REQUIREMENTS
     * 注释：年龄要求
     *
     * @mbggenerated
     */
    private String wageRequirements;
    
    /**
     * 表：reserve_orders_customer_info
     * 字段：EXPERIENCE_REQUIREMENTS
     * 注释：服务经验要求
     *
     * @mbggenerated
     */
    private String experienceRequirements;

	public String getPetRaising() {
		return petRaising;
	}

	public void setPetRaising(String petRaising) {
		this.petRaising = petRaising;
	}

	public String[] getSalarySkills() {
		return salarySkills;
	}

	public void setSalarySkills(String[] salarySkills) {
		this.salarySkills = salarySkills;
	}

	public String[] getOtherRequirements() {
		return otherRequirements;
	}

	public void setOtherRequirements(String[] otherRequirements) {
		this.otherRequirements = otherRequirements;
	}

	public String[] getCookingReqirements() {
		return cookingReqirements;
	}

	public void setCookingReqirements(String[] cookingReqirements) {
		this.cookingReqirements = cookingReqirements;
	}

	public String[] getLanguageRequirements() {
		return languageRequirements;
	}

	public void setLanguageRequirements(String[] languageRequirements) {
		this.languageRequirements = languageRequirements;
	}

	public Integer getOlderCount() {
		return olderCount;
	}

	public void setOlderCount(Integer olderCount) {
		this.olderCount = olderCount;
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

	public String getIsBabyBorn() {
		return isBabyBorn;
	}

	public void setIsBabyBorn(String isBabyBorn) {
		this.isBabyBorn = isBabyBorn;
	}

	public String getExpectedBirth() {
		return expectedBirth;
	}

	public void setExpectedBirth(String expectedBirth) {
		this.expectedBirth = expectedBirth;
	}

	public Integer getBabyCount() {
		return babyCount;
	}

	public void setBabyCount(Integer babyCount) {
		this.babyCount = babyCount;
	}

	public Integer getBabyAge() {
		return babyAge;
	}

	public void setBabyAge(Integer babyAge) {
		this.babyAge = babyAge;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public Integer getChildrenCount() {
		return childrenCount;
	}

	public void setChildrenCount(Integer childrenCount) {
		this.childrenCount = childrenCount;
	}

	public Integer getFamilyCount() {
		return familyCount;
	}

	public void setFamilyCount(Integer familyCount) {
		this.familyCount = familyCount;
	}

	public String getChildrenAgeRange() {
		return childrenAgeRange;
	}

	public void setChildrenAgeRange(String childrenAgeRange) {
		this.childrenAgeRange = childrenAgeRange;
	}

	public String getOlderAgeRange() {
		return olderAgeRange;
	}

	public void setOlderAgeRange(String olderAgeRange) {
		this.olderAgeRange = olderAgeRange;
	}

	public String getSelfCares() {
		return selfCares;
	}

	public void setSelfCares(String selfCares) {
		this.selfCares = selfCares;
	}

	public String getSpecifiedTime() {
		return specifiedTime;
	}

	public void setSpecifiedTime(String specifiedTime) {
		this.specifiedTime = specifiedTime;
	}

	public String getServiceProvice() {
		return serviceProvice;
	}

	public void setServiceProvice(String serviceProvice) {
		this.serviceProvice = serviceProvice;
	}

	public String getServiceCity() {
		return serviceCity;
	}

	public void setServiceCity(String serviceCity) {
		this.serviceCity = serviceCity;
	}

	public String getServiceCounty() {
		return serviceCounty;
	}

	public void setServiceCounty(String serviceCounty) {
		this.serviceCounty = serviceCounty;
	}

	public String getServiceAddress() {
		return serviceAddress;
	}

	public void setServiceAddress(String serviceAddress) {
		this.serviceAddress = serviceAddress;
	}

	public String getSpecialNeeds() {
		return specialNeeds;
	}

	public void setSpecialNeeds(String specialNeeds) {
		this.specialNeeds = specialNeeds;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(String servicePrice) {
		this.servicePrice = servicePrice;
	}

	public String getWageRequirements() {
		return wageRequirements;
	}

	public void setWageRequirements(String wageRequirements) {
		this.wageRequirements = wageRequirements;
	}

	public String getExperienceRequirements() {
		return experienceRequirements;
	}

	public void setExperienceRequirements(String experienceRequirements) {
		this.experienceRequirements = experienceRequirements;
	}
	
	
}
