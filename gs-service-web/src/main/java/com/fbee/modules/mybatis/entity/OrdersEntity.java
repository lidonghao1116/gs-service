package com.fbee.modules.mybatis.entity;

import java.util.List;

import com.fbee.modules.bean.Constants;
import com.fbee.modules.bean.ServiceKillsCacheBean;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.mybatis.model.OrderCustomersInfo;
import com.fbee.modules.mybatis.model.Orders;
import com.fbee.modules.mybatis.model.StaffSnapShotInfo;
import com.fbee.modules.mybatis.model.TenantsSnapshotInfo;
import com.fbee.modules.utils.DictionariesUtil;
import com.fbee.modules.utils.DictionarysCacheUtils;

public class OrdersEntity extends Orders{
    
	private static final long serialVersionUID = 1L;


    private TenantsSnapshotInfo tenantInfo;
    private StaffSnapShotInfo staffInfo;
    private OrderCustomersInfo orderCustomer;
    
    private String serviceItemCodeValue;
    private String serviceTypeValue;
	private String salarySkillsValue;
	private String serviceCityValue;
	
	private String serviceCountryValue;
	private String serviceProviceValue;
	
	private String experienceRequirementsValue;
	private String wageRequirementsValue;

    public TenantsSnapshotInfo getTenantInfo() {
        return tenantInfo;
    }

    public void setTenantInfo(TenantsSnapshotInfo tenantInfo) {
        this.tenantInfo = tenantInfo;
    }

    public StaffSnapShotInfo getStaffInfo() {
        return staffInfo;
    }

    public void setStaffInfo(StaffSnapShotInfo staffInfo) {
        this.staffInfo = staffInfo;
    }

    public OrderCustomersInfo getOrderCustomer() {
        return orderCustomer;
    }

    public void setOrderCustomer(OrderCustomersInfo orderCustomer) {
        this.orderCustomer = orderCustomer;
    }

	public String getServiceItemCodeValue() {
		if(StringUtils.isNotBlank(this.getServiceItemCode())){
			return DictionarysCacheUtils.getServiceTypeName(this.getServiceItemCode());
		}
		return serviceItemCodeValue;
	}

	public void setServiceItemCodeValue(String serviceItemCodeValue) {
		this.serviceItemCodeValue = serviceItemCodeValue;
	}
	
	public String getServiceTypeValue() {
		if(StringUtils.isNotBlank(this.orderCustomer.getServiceType())){
			return DictionarysCacheUtils.getServiceType(this.orderCustomer.getServiceType());
		}
		return this.serviceTypeValue;
	}

	public void setServiceTypeValue(String serviceTypeValue) {
		this.serviceTypeValue = serviceTypeValue;
	}

	public String getSalarySkillsValue() {
		if(StringUtils.isNoneBlank(orderCustomer.getSalarySkills())){
        	List<ServiceKillsCacheBean> list = DictionarysCacheUtils.getSkillsList(this.getServiceItemCode(),this.orderCustomer.getSalarySkills());
        	StringBuffer sb = new StringBuffer();
        	for(int i = 0; i < list.size(); i++){
        		ServiceKillsCacheBean bean = list.get(i);
        		sb.append(bean.getSkillsValue());
        		if(i < (list.size() - 1)){
        			sb.append(Constants.COMMS);
        		}
        	}
        	return sb.toString();
		}
		return salarySkillsValue;
	}

	public void setSalarySkillsValue(String salarySkillsValue) {
		this.salarySkillsValue = salarySkillsValue;
	}

	public String getServiceCityValue() {
		if(StringUtils.isNotBlank(this.orderCustomer.getServiceCity())){
        	return DictionarysCacheUtils.getCityName(this.orderCustomer.getServiceCity());
        }
		return serviceCityValue;
	}

	public void setServiceCityValue(String serviceCityValue) {
		this.serviceCityValue = serviceCityValue;
	}

	public String getServiceCountryValue() {
		if(StringUtils.isNotBlank(this.orderCustomer.getServiceCounty())){
        	return DictionarysCacheUtils.getCountyName(this.orderCustomer.getServiceCounty());
        }
		return serviceCountryValue;
	}

	public void setServiceCountryValue(String serviceCountryValue) {
		this.serviceCountryValue = serviceCountryValue;
	}

	public String getServiceProviceValue() {
		if(StringUtils.isNotBlank(this.orderCustomer.getServiceProvice())){
        	return DictionarysCacheUtils.getProvinceName(this.orderCustomer.getServiceProvice());
        }
		return serviceProviceValue;
	}

	public void setServiceProviceValue(String serviceProviceValue) {
		this.serviceProviceValue = serviceProviceValue;
	}

	public String getExperienceRequirementsValue() {
		if(StringUtils.isNotBlank(this.orderCustomer.getExperienceRequirements())){
        	String str = "";
            str = DictionarysCacheUtils.getExperienceName(this.orderCustomer.getExperienceRequirements());
            if (StringUtils.isNotBlank(str)) {
                String[] experiences = str.split(",");
                if (experiences.length == 2) {
                    if (experiences[0].equals("0")) {
                    	return (experiences[1] + "年以下");
                    } else {
                    	return (experiences[0] + "-" + experiences[1] + "年");
                    }
                } else if (experiences.length == 1) {
                	return (experiences[0] + "年以上");
                }
            }
        }
		return experienceRequirementsValue;
	}

	public void setExperienceRequirementsValue(String experienceRequirementsValue) {
		this.experienceRequirementsValue = experienceRequirementsValue;
	}

	public String getWageRequirementsValue() {
		if(StringUtils.isNotBlank(this.orderCustomer.getWageRequirements())){
        	return DictionariesUtil.getAgerange(this.orderCustomer.getWageRequirements());
        }
		return wageRequirementsValue;
	}

	public void setWageRequirementsValue(String wageRequirementsValue) {
		this.wageRequirementsValue = wageRequirementsValue;
	}
    
}