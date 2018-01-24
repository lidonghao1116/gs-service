package com.fbee.modules.controller;

import com.fbee.modules.basic.RequestMappingURL;
import com.fbee.modules.consts.ErrorCode;
import com.fbee.modules.core.Log;
import com.fbee.modules.interceptor.anno.Guest;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.service.AboutUsService;
import com.fbee.modules.service.TenantService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: AboutUsController
 * @Description: 关于我们控制器
 * @author 赵利壮
 * @date 2017年2月20日 下午12:04:07
 * 
 */
@Controller
@RequestMapping(RequestMappingURL.AboutUs_BASE_URL)
public class AboutUsController {

	@Autowired
	AboutUsService aboutUsService;

	@Autowired
	TenantService tenantService;

	@Guest
	@RequestMapping(value = RequestMappingURL.getUsInfo_URL, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult getStaffServiceItemList(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("domain") String domain) {
		try {
			
			// 根据二级域名获取租户id
			if (domain == null) {
				return JsonResult.failure(ErrorCode.SYS_ERROR);
			}
			Integer tenantId = tenantService.getTenantIdByDomain(domain);

			return JsonResult.success(aboutUsService.getUsInfo(tenantId));
		} catch (Exception e) {
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}

}
