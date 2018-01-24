package com.fbee.modules.jsonData.extend;

import com.fbee.modules.core.persistence.ModelSerializable;

/** 
* @ClassName: StaffQueryJson 
* @Description: 用于阿姨查询返回实体--用于返回json
* @author 贺章鹏
* @date 2017年1月4日 下午4:15:21 
*  
*/
public class StaffQueryJson implements ModelSerializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer staffId;//阿姨id
	
	private String staffName;//阿姨姓名
	
	private String headImage;//图像
	
	private String serviceItems;//服务工种
	
	private String mobile;//手机号码
	
	private String education;//学历，如：本科(营养学)
	
	private String specialty;//专业
	
    private Integer age;//学历
    
    private String zodiac;//属相
    
    private String nativePlace;//籍贯
	
	private String workStatus;//工作状态，如：服务中
	
	private String onOffShelf;//上下架

	private String baseInfo;//基本信息，如：26/羊/上海人
	
	private String serviceType;//服务工种，如：保姆,陪护,日常保洁
	
	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getServiceItems() {
		return serviceItems;
	}

	public void setServiceItems(String serviceItems) {
		this.serviceItems = serviceItems;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
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

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public String getOnOffShelf() {
		return onOffShelf;
	}

	public void setOnOffShelf(String onOffShelf) {
		this.onOffShelf = onOffShelf;
	}

	public String getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(String baseInfo) {
		this.baseInfo = baseInfo;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
}
