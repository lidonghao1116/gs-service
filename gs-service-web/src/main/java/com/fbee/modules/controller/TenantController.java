package com.fbee.modules.controller;

import com.fbee.modules.basic.RequestMappingURL;
import com.fbee.modules.consts.ErrorCode;
import com.fbee.modules.core.Log;
import com.fbee.modules.interceptor.anno.Auth;
import com.fbee.modules.interceptor.anno.Guest;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.jsonData.json.IndexJsonData;
import com.fbee.modules.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description：站点首页控制器
 * @author fry
 * @date 2017年1月19日 下午3:00:37
 * 
 */
@Controller
@RequestMapping(RequestMappingURL.INDEX_BASE_URL)
public class TenantController {
	@Autowired
	TenantService tenantService;

	/**
	 * 通过租户id获取站点信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@Guest
	@RequestMapping(value = RequestMappingURL.INFO_URL, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult getIndexInfo(@PathVariable("domain") String domain, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 根据二级域名获取租户id
			if (domain == null) {
				return JsonResult.failure(ErrorCode.SYS_ERROR);
			}
			Integer tenantId = tenantService.getTenantIdByDomain(domain);
			IndexJsonData indexJsonData = tenantService.getIndexInfo(tenantId);
			return JsonResult.success(indexJsonData);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}

	/**
	 * 通过租户ID获取服务项目信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@Auth
	@RequestMapping(value = RequestMappingURL.SERITEMS_URL, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult getServiceItems(@PathVariable("domain") String domain, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 根据二级域名获取租户id
			if (domain == null) {
				return JsonResult.failure(ErrorCode.SYS_ERROR);
			}
			Integer tenantId = tenantService.getTenantIdByDomain(domain);
			return JsonResult.success(tenantService.getServiceItems(tenantId));
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}

	}


	/**
	 *
	 *通过租户ID获取logo地址
	 * @param request
	 * @param response
	 * @return
	 */
	@Auth
	@RequestMapping(value = RequestMappingURL.GET_APPS_LOGO, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult  getlogopath(@PathVariable("domain") String domain, HttpServletRequest request,
									  HttpServletResponse response) {
		try {
			// 根据二级域名获取租户id
			if (domain == null) {
				return JsonResult.failure(ErrorCode.SYS_ERROR);
			}
			//String tenantId = tenantService.getlogopath(domain);
			return JsonResult.success(tenantService.getlogopath(domain));
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}

	}


}
