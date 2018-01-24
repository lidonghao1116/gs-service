package com.fbee.modules.form;

import com.fbee.modules.core.persistence.ModelSerializable;

/** 
* @ClassName: TenantsJobsForm 
* @Description: 招聘信息新增修改
* @author 张杰
*/
public class TenantsJobsForm implements ModelSerializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;//主键
	
	private Integer tenantId;//租户id
	
	private String positionName;//岗位名称
	
	private String serverType;//服务工种
	
	private String age;//年龄
	
	private String salary ;//工资
	
	private String demand;//要求
	
	private String isUsable;//是否上架
	
	private String serviceProvince;//省
	
	private String serviceCity;//市
	
	private String salaryType;
	

	public String getSalaryType() {
		return salaryType;
	}

	public void setSalaryType(String salaryType) {
		this.salaryType = salaryType;
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

	public Integer getTenantId() {
		return tenantId;
	}

	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getServerType() {
		return serverType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
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
		this.salary = salary;
	}

	public String getDemand() {
		return demand;
	}

	public void setDemand(String demand) {
		this.demand = demand;
	}

	public String getIsUsable() {
		return isUsable;
	}

	public void setIsUsable(String isUsable) {
		this.isUsable = isUsable;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "TenantsJobsForm [id=" + id + ", tenantId=" + tenantId + ", positionName=" + positionName
				+ ", serverType=" + serverType + ", age=" + age + ", salary=" + salary + ", demand=" + demand
				+ ", isUsable=" + isUsable + "]";
	}
}
