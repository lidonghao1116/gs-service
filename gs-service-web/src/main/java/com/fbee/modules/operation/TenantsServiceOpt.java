package com.fbee.modules.operation;

import java.util.Date;

import com.fbee.modules.core.utils.DateUtils;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.form.ReserveOrdersForm;
import com.fbee.modules.form.ServiceInfoForm;
import com.fbee.modules.mybatis.entity.ReserveOrdersCustomerInfoEntity;
import com.fbee.modules.mybatis.entity.ReserveOrdersEntity;
import com.fbee.modules.utils.DictionarysCacheUtils;

public class TenantsServiceOpt {

	StringBuffer sb = new StringBuffer();
	
	public ReserveOrdersEntity buildReserveOrdersEntity(String orderno,Integer tenantId, Integer id, ReserveOrdersForm reserveordersform){
		ReserveOrdersEntity reserveOrdersEntity = new ReserveOrdersEntity();
		reserveOrdersEntity.setOrderNo(orderno);//订单号
		reserveOrdersEntity.setTenantId(tenantId); 
		reserveOrdersEntity.setMemberId(id);//客户号
		reserveOrdersEntity.setMemberMobile(reserveordersform.getCustomerMobile());
		reserveOrdersEntity.setMemberName(reserveordersform.getCustomerName());
		reserveOrdersEntity.setStaffId(reserveordersform.getStaffId());
		reserveOrdersEntity.setServiceItemCode(reserveordersform.getServiceItemCode());
		reserveOrdersEntity.setOrderTime(new Date());
		reserveOrdersEntity.setOrderStatus("01");//订单状态
		return reserveOrdersEntity;
	}
	
	public ReserveOrdersCustomerInfoEntity buildReserveOrdersCustomerInfoEntity(String orderno,ReserveOrdersForm reserveordersform,ServiceInfoForm serviceinfoform){
		ReserveOrdersCustomerInfoEntity reserveOrdersCustomerInfoEntity = new ReserveOrdersCustomerInfoEntity();
		reserveOrdersCustomerInfoEntity.setOrderNo(orderno);
		reserveOrdersCustomerInfoEntity.setServiceProvice(serviceinfoform.getServiceProvice());
		reserveOrdersCustomerInfoEntity.setServiceCity(serviceinfoform.getServiceCity());
		reserveOrdersCustomerInfoEntity.setServiceCounty(serviceinfoform.getServiceCounty());
		reserveOrdersCustomerInfoEntity.setServiceAddress(serviceinfoform.getServiceAddress());
		reserveOrdersCustomerInfoEntity.setServiceStart(DateUtils.parseDate(serviceinfoform.getServiceStart()));
		reserveOrdersCustomerInfoEntity.setServiceEnd(DateUtils.parseDate(serviceinfoform.getServiceEnd()));
		reserveOrdersCustomerInfoEntity.setRemarks(serviceinfoform.getRemarks());

		String[] SalarySkills = serviceinfoform.getSalarySkills();
		if(SalarySkills != null){
			sb.setLength(0);
			for(int i=0;i<SalarySkills.length;i++){
				if(i == SalarySkills.length-1){
					sb.append(SalarySkills[i]);
				}else{
					sb.append(SalarySkills[i]+",");
				}
			}
			reserveOrdersCustomerInfoEntity.setSalarySkills(sb.toString());
		}
		
		reserveOrdersCustomerInfoEntity.setFamilyCount(serviceinfoform.getFamilyCount());
		reserveOrdersCustomerInfoEntity.setChildrenCount(serviceinfoform.getChildrenCount());
		reserveOrdersCustomerInfoEntity.setOlderCount(serviceinfoform.getOlderCount());
		reserveOrdersCustomerInfoEntity.setChildrenAgeRange(serviceinfoform.getChildrenAgeRange());
		reserveOrdersCustomerInfoEntity.setOlderAgeRange(serviceinfoform.getOlderAgeRange());
		reserveOrdersCustomerInfoEntity.setSelfCares(serviceinfoform.getSelfCares());
		reserveOrdersCustomerInfoEntity.setIsBabyBorn(serviceinfoform.getIsBabyBorn());
		reserveOrdersCustomerInfoEntity.setExpectedBirth(DateUtils.parseDate(serviceinfoform.getExpectedBirth()));
		reserveOrdersCustomerInfoEntity.setBabyCount(serviceinfoform.getBabyCount());
		reserveOrdersCustomerInfoEntity.setBabyAge(serviceinfoform.getBabyAge());
		reserveOrdersCustomerInfoEntity.setPetRaising(serviceinfoform.getPetRaising());
		
		String[] OtherRequirements = serviceinfoform.getOtherRequirements();
		if(OtherRequirements != null){
			sb.setLength(0);
			for(int i=0;i<OtherRequirements.length;i++){
				if(i == OtherRequirements.length-1){
					sb.append(OtherRequirements[i]);
				}else{
					sb.append(OtherRequirements[i]+",");
				}
			}
			reserveOrdersCustomerInfoEntity.setOtherRequirements(sb.toString());
		}
		
		reserveOrdersCustomerInfoEntity.setServiceType(serviceinfoform.getServiceType());
		
		String[] CookingReqirements = serviceinfoform.getCookingReqirements();
		if(CookingReqirements != null){
			sb.setLength(0);
			for(int i=0;i<CookingReqirements.length;i++){
				if(i == CookingReqirements.length-1){
					sb.append(CookingReqirements[i]);
				}else{
					sb.append(CookingReqirements[i]+",");
				}
			}
			reserveOrdersCustomerInfoEntity.setCookingReqirements(sb.toString());
		}
		
		reserveOrdersCustomerInfoEntity.setSpecifiedTime(DateUtils.parseDate(serviceinfoform.getSpecifiedTime()));
		
		String[] LanguageRequirements = serviceinfoform.getLanguageRequirements();
		if(LanguageRequirements != null){
			sb.setLength(0);
			for(int i=0;i<LanguageRequirements.length;i++){
				if(i == LanguageRequirements.length-1){
					sb.append(LanguageRequirements[i]);
				}else{
					sb.append(LanguageRequirements[i]+",");
				}
			}
			reserveOrdersCustomerInfoEntity.setLanguageRequirements(sb.toString());
		}
		reserveOrdersCustomerInfoEntity.setWageRequirements(serviceinfoform.getWageRequirements());
		reserveOrdersCustomerInfoEntity.setExperienceRequirements(serviceinfoform.getExperienceRequirements());
		reserveOrdersCustomerInfoEntity.setSpecialNeeds(serviceinfoform.getSpecialNeeds());
		return reserveOrdersCustomerInfoEntity;
	}
	
}
