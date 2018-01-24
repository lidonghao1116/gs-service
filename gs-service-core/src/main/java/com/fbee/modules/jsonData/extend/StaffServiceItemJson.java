package com.fbee.modules.jsonData.extend;

import java.math.BigDecimal;

import com.fbee.modules.core.persistence.ModelSerializable;

/** 
* @ClassName: StaffServiceItemJson 
* @Description: TODO
* @author 贺章鹏
* @date 2017年1月5日 下午5:35:00 
*  
*/
public class StaffServiceItemJson implements ModelSerializable{
	private static final long serialVersionUID = 1L;
	
	private String serviceItemCode;//服务工种
	
	private String experience;//从业经验
	
	private BigDecimal price;//服务价格
	
	private String skills;//技能特点
	
	private String serviceNature;//服务类型

	public String getServiceItemCode() {
		return serviceItemCode;
	}

	public void setServiceItemCode(String serviceItemCode) {
		this.serviceItemCode = serviceItemCode;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getServiceNature() {
		return serviceNature;
	}

	public void setServiceNature(String serviceNature) {
		this.serviceNature = serviceNature;
	}

}
