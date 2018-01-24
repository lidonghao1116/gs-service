package com.fbee.modules.form;

import java.util.List;

import com.fbee.modules.core.persistence.ModelSerializable;
import com.fbee.modules.form.extend.SelfFeatureForm;
import com.fbee.modules.form.extend.StaffServiceItemform;

/** 
* @ClassName: StaffJobForm 
* @Description: 员工（阿姨）求职信息信息新增修改
* @author 贺章鹏
* @date 2017年1月3日 下午4:11:29 
*  
*/
public class StaffJobForm implements ModelSerializable{

	private static final long serialVersionUID = 1L;
	
	private Integer staffId;//阿姨id
	
	private String manageWay;//管理方式
	
	private String serviceProvice;//省
	
	private String serviceCity;//市
	
	private String serviceCounty;//区
	
	private String workExperience;//工作经历
	
	private String selfEvaluation;//自我评价
	
	private String teacherEvaluation;//老师评价
	
	private List<StaffServiceItemform> serviceItems;//服务工种
	
	private List<SelfFeatureForm> features;//个人特点

	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

	public String getManageWay() {
		return manageWay;
	}

	public void setManageWay(String manageWay) {
		this.manageWay = manageWay;
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

	public String getWorkExperience() {
		return workExperience;
	}

	public void setWorkExperience(String workExperience) {
		this.workExperience = workExperience;
	}

	public String getSelfEvaluation() {
		return selfEvaluation;
	}

	public void setSelfEvaluation(String selfEvaluation) {
		this.selfEvaluation = selfEvaluation;
	}

	public String getTeacherEvaluation() {
		return teacherEvaluation;
	}

	public void setTeacherEvaluation(String teacherEvaluation) {
		this.teacherEvaluation = teacherEvaluation;
	}

	public List<StaffServiceItemform> getServiceItems() {
		return serviceItems;
	}

	public void setServiceItems(List<StaffServiceItemform> serviceItems) {
		this.serviceItems = serviceItems;
	}

	public List<SelfFeatureForm> getFeatures() {
		return features;
	}

	public void setFeatures(List<SelfFeatureForm> features) {
		this.features = features;
	}

}
