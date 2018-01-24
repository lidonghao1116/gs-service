package com.fbee.modules.form;

import java.util.List;

import com.fbee.modules.core.persistence.ModelSerializable;
import com.fbee.modules.form.extend.SelfFeatureForm;

/** 
* @ClassName: StaffQueryForm 
* @Description: 员工查询表单
* @author 贺章鹏
* @date 2016年12月29日 下午4:53:29 
*  
*/
public class StaffQueryForm implements ModelSerializable{

	private static final long serialVersionUID = 1L;
	
	private String serviceType;//服务工种
	
	private List<String> skillKeys;//技能点
	
	private List<Integer> prices;//价格区间[0,400)
	
	private String experience;//经验
	
	private List<Integer> ages;//年龄段[0,10)
	
	private String zodiac;//属相
	
	private String nativePlace;//籍贯
	
	private String workStatus;//工作状态
	
	private String onOffShelf;//上下架
	
	private List<SelfFeatureForm> features;//个人特点
	
	private String education;//学历
	
	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public List<String> getSkillKeys() {
		return skillKeys;
	}

	public void setSkillKeys(List<String> skillKeys) {
		this.skillKeys = skillKeys;
	}

	public List<Integer> getPrices() {
		return prices;
	}

	public void setPrices(List<Integer> prices) {
		this.prices = prices;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public List<Integer> getAges() {
		return ages;
	}

	public void setAges(List<Integer> ages) {
		this.ages = ages;
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

	public List<SelfFeatureForm> getFeatures() {
		return features;
	}

	public void setFeatures(List<SelfFeatureForm> features) {
		this.features = features;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}
}
