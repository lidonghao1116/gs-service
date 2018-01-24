package com.fbee.modules.service;

import com.fbee.modules.form.TenantsJobsForm;
import com.fbee.modules.jsonData.basic.JsonResult;

public interface JobRecruitService {
	/**
	 * 岗位招聘<br/>
	 * 获取招聘列表信息
	 * @param serviceType
	 * @param pageSize 
	 * @param pageNumber 
	 * @return
	 */
	JsonResult getTenantsJobsInfoList(int tenantId, String serviceType, TenantsJobsForm tenantsJobsForm, int pageNumber, int pageSize);
	
	JsonResult getServiceType();
	
}
