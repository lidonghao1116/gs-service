package com.fbee.modules.form;

import com.fbee.modules.core.persistence.ModelSerializable;

/** 
* @ClassName: StaffBaseInfoForm 
* @Description: 员工（阿姨）基础信息新增
* @author 贺章鹏
* @date 2016年12月30日 上午11:46:23 
*  
*/
public class StaffBaseInfoForm implements ModelSerializable{

	private static final long serialVersionUID = 1L;

	private String certNo;//身份证
	
	private String certImage;//证件头像
	
	private String staffName;//员工姓名
	
	private Integer age;//年龄
	
	private String nation;//民族
	
	private String sex;//性别
	
	private String zodiac;//属相
	
	private String nativePlace;//籍贯
	
	private String constellation;//星座
	
	private String education;//学历
	
	private String specialty;//专业
	
	private String maritalStatus;//婚姻状况
	
	private String fertilitySituation;//生育状况
	
	private String bloodType;//血型
	
	private String mobile;//手机号码
	
	private String houseAddress;//户籍地址
	
	private String contactPhone;//联系电话
	
	private String contactAddress;//联系地址
	
	private String liveAddress;//现居地
	
	private String workStatus;//工作状态
	
	private Integer expectedSalary;//期望工资

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getCertImage() {
		return certImage;
	}

	public void setCertImage(String certImage) {
		this.certImage = certImage;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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

	public String getConstellation() {
		return constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
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

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getFertilitySituation() {
		return fertilitySituation;
	}

	public void setFertilitySituation(String fertilitySituation) {
		this.fertilitySituation = fertilitySituation;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getHouseAddress() {
		return houseAddress;
	}

	public void setHouseAddress(String houseAddress) {
		this.houseAddress = houseAddress;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getLiveAddress() {
		return liveAddress;
	}

	public void setLiveAddress(String liveAddress) {
		this.liveAddress = liveAddress;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public Integer getExpectedSalary() {
		return expectedSalary;
	}

	public void setExpectedSalary(Integer expectedSalary) {
		this.expectedSalary = expectedSalary;
	}
	
	@Override
	public String toString(){
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("staff baseInfo,{staffName:").append(staffName)
			.append(",certNo:").append(certNo).append(",mobile:").append(mobile);
		return stringBuilder.toString();
	}
}
