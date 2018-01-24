package com.fbee.modules.form;

import com.fbee.modules.core.persistence.ModelSerializable;
/**
 * 商户注册
 * @author ZhangJie
 *
 */
public class TenantsRegistForm implements ModelSerializable{

	private static final long serialVersionUID = 1L;
	private String mobile;//手机号
	private String captcha;//验证码
	private String smsCode;//短信验证码
	private String storeName;//门店名称
	private String privince;//省
	private String city;//市
	private String area;//区
	private Integer recommendId;//推荐人id
	private String address;//详细地址
	private String telephonenumber;//门店电话
	private String applicantName;//申请人姓名
	private String idCardNo;//身份证号码
	private String idPhotopositive;//身份证照片正面
	private String idPhotonegative;//身份证照片反面
	private String businessName;//工商名称
	private String socialInformationCode;//社会信息代码
	private String businessLicensePhoto;//营业执照照片
	private String accountProperties;//账号性质   01：个人账号	02：企业账号
	private String accountName; //账号名称（账号所属人名称）
	private String bankAccount;//银行账号
	private String openingBand;//开户行
	private String openingBandBranch;//开户支行
	

	
	public String getOpeningBandBranch() {
		return openingBandBranch;
	}

	public void setOpeningBandBranch(String openingBandBranch) {
		this.openingBandBranch = openingBandBranch;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephonenumber() {
		return telephonenumber;
	}

	public void setTelephonenumber(String telephonenumber) {
		this.telephonenumber = telephonenumber;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getIdPhotopositive() {
		return idPhotopositive;
	}

	public void setIdPhotopositive(String idPhotopositive) {
		this.idPhotopositive = idPhotopositive;
	}

	public String getIdPhotonegative() {
		return idPhotonegative;
	}

	public void setIdPhotonegative(String idPhotonegative) {
		this.idPhotonegative = idPhotonegative;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getSocialInformationCode() {
		return socialInformationCode;
	}

	public void setSocialInformationCode(String socialInformationCode) {
		this.socialInformationCode = socialInformationCode;
	}

	public String getBusinessLicensePhoto() {
		return businessLicensePhoto;
	}

	public void setBusinessLicensePhoto(String businessLicensePhoto) {
		this.businessLicensePhoto = businessLicensePhoto;
	}

	public String getAccountProperties() {
		return accountProperties;
	}

	public void setAccountProperties(String accountProperties) {
		this.accountProperties = accountProperties;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getOpeningBand() {
		return openingBand;
	}

	public void setOpeningBand(String openingBand) {
		this.openingBand = openingBand;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getRecommendId() {
		return recommendId;
	}

	public void setRecommendId(Integer recommendId) {
		this.recommendId = recommendId;
	}

	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCaptcha() {
		return captcha;
	}
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getPrivince() {
		return privince;
	}
	public void setPrivince(String privince) {
		this.privince = privince;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}

	@Override
	public String toString() {
		return "TenantsRegistForm [mobile=" + mobile + ", captcha=" + captcha + ", smsCode=" + smsCode + ", storeName="
				+ storeName + ", privince=" + privince + ", city=" + city + ", area=" + area + ", recommendId="
				+ recommendId + ", address=" + address + ", telephonenumber=" + telephonenumber + ", applicantName="
				+ applicantName + ", idCardNo=" + idCardNo + ", idPhotopositive=" + idPhotopositive
				+ ", idPhotonegative=" + idPhotonegative + ", businessName=" + businessName + ", socialInformationCode="
				+ socialInformationCode + ", businessLicensePhoto=" + businessLicensePhoto + ", accountProperties="
				+ accountProperties + ", accountName=" + accountName + ", bankAccount=" + bankAccount + ", openingBand="
				+ openingBand + ", openingBandBranch=" + openingBandBranch + "]";
	}
	
}
