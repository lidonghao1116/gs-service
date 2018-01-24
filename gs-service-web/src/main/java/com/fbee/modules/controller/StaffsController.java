package com.fbee.modules.controller;

import com.fbee.modules.basic.RequestMappingURL;
import com.fbee.modules.consts.ErrorCode;
import com.fbee.modules.core.Log;
import com.fbee.modules.interceptor.anno.Guest;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.service.StaffsService;
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
 * @Description：员工信息管理控制器
 * @author fry
 * @date 2017年1月20日 下午2:50:26
 * 
 */
@Controller
@RequestMapping(RequestMappingURL.STAFF_BASE_URL)
public class StaffsController {

	@Autowired
	TenantService tenantService;
	@Autowired
	StaffsService staffsService;

	/**
	 * 根据租户id获取推荐阿姨信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@Guest
	@RequestMapping(value = RequestMappingURL.STAFFRECOMMEND_URL, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult getRecommendList(@PathVariable("domain") String domain, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// HttpSession session = SessionUtils.getSession(request);
			// MemberBean memeberbean=(MemberBean)
			// session.getAttribute(Constants.MEMBER_SESSION);
			// if(memeberbean == null){
			// return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			// }
			// 根据二级域名获取租户id
			if (domain == null) {
				return JsonResult.failure(ErrorCode.SYS_ERROR);
			}
			Integer tenantId = tenantService.getTenantIdByDomain(domain);
			return JsonResult.success(staffsService.getRecomMendListByTenantId(tenantId));
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.error(ResultCode.getMsg(ResultCode.ERROR), ex);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}
}
