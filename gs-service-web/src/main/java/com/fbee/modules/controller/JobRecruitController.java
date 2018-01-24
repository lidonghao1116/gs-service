package com.fbee.modules.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fbee.modules.interceptor.anno.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbee.modules.basic.RequestMappingURL;
import com.fbee.modules.bean.Constants;
import com.fbee.modules.core.Log;
import com.fbee.modules.form.TenantsJobsForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.service.JobRecruitService;
import com.fbee.modules.service.TenantService;

@Controller
@RequestMapping(value = RequestMappingURL.JOB_RECRUIT)
public class JobRecruitController {

	@Autowired
	JobRecruitService jobRecruitService;

	@Autowired
	TenantService tenantService;


	/**
	 * 岗位招聘-获取招聘列表信息集合
	 * 
	 * @param request
	 * @param response
	 * @param pageNumber
	 * @param pageSize
	 * @param serviceType
	 * @return
	 */
	@Guest
	@RequestMapping(value = RequestMappingURL.GET_TENANTS_JOBS_LIST_INFO, method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public JsonResult getTenantsJobsInfoList(TenantsJobsForm tenantsJobsForm, HttpServletRequest request,
			HttpServletResponse response, @PathVariable("domain") String domain,
			@RequestParam(value = "pageNumber", defaultValue = Constants.DEFAULT_PAGE_NO) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize,
			@RequestParam(value = "serviceType", defaultValue = "") String serviceType) {

		try {
			// 调用业务层查询
			// 根据二级域名获取租户id
			if (domain == null) {
				return JsonResult.failure(0);
			}
			Integer tenantId = tenantService.getTenantIdByDomain(domain);
			return jobRecruitService.getTenantsJobsInfoList(tenantId, serviceType, tenantsJobsForm, pageNumber,
					pageSize);
		} catch (Exception e) {
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}

	@Guest
	@RequestMapping(value = "getServiceType", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public JsonResult getServiceType(TenantsJobsForm tenantsJobsForm, HttpServletRequest request,
			HttpServletResponse response) {

		try{
			return jobRecruitService.getServiceType();
		} catch (Exception e) {
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}

	
}
