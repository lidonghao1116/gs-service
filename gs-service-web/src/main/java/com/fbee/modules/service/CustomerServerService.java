package com.fbee.modules.service;

import com.fbee.modules.core.bean.SmsCode;
import com.fbee.modules.form.FindServiceForm;
import com.fbee.modules.form.TenantsRegistForm;
import com.fbee.modules.jsonData.basic.JsonResult;

public interface CustomerServerService {
	/**
	 * 附近家政公司列表查询
	 * @param tenantId
	 * @param customerqueryform
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	JsonResult queryNearbyCompany(FindServiceForm findServiceForm,int pageNumber,int pageSize);
	/**
	 * 找服务(工作)列表查询接口
	 * @param tenantId
	 * @param pageNumber
	 * @param pageSize
	 * @param serverType 
	 * @return
	 */
	JsonResult queryJob(int pageNumber,int pageSize,String showMore, String serviceType,String privince,String city,String ageQu,String salaryTypeQu,String salaryQu,String area);
	/**
	 * 找服务(工作详情)列表查询接口
	 * @param tenantId
	 * @param customerqueryform
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	JsonResult queryJobDetail(Integer id);
	/**
	 * 商户注册
	 * @param tenantId
	 * @param customerqueryform
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	JsonResult tenantsRegist(TenantsRegistForm tenantsRegistForm);
	
	/**
	 * 商户注册信息写入
	 * @param tenantId
	 * @param customerqueryform
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	JsonResult tenantsRegistInfo(TenantsRegistForm tenantsRegistForm,String mobile);
	
	/**
	 * 获取短信验证码
	 * @param telNum
	 * @return
	 */
	SmsCode getSmsCode(String telNum);
	
	/**
	 * 查询是否有相同门店
	 * @param storeName
	 * @return
	 */
	JsonResult getUniqueName(String storeName);
	
	/**
	 * 查看手机号是否已被注册
	 * @param mobile
	 * @return
	 */
	JsonResult getUniquePhone(String mobile);
	
}
