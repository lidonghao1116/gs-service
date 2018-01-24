package com.fbee.modules.controller;

import com.fbee.modules.basic.RequestMappingURL;
import com.fbee.modules.bean.Constants;
import com.fbee.modules.bean.MemberBean;
import com.fbee.modules.consts.ErrorCode;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.form.ReserveOrdersForm;
import com.fbee.modules.form.ServiceInfoForm;
import com.fbee.modules.interceptor.anno.Guest;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 贺章鹏
 * @ClassName: ServiceController
 * @Description: 服务定制控制器
 * @date 2016年12月28日 下午12:04:07
 */
@Controller
@RequestMapping(RequestMappingURL.Service_BASE_URL)
public class ServiceController {

    @Autowired
    TenantService staffsService;

    /**
     * 获取服务列表清单
     *
     * @param request
     * @param response
     * @return
     */
    @Guest
    @RequestMapping(value = RequestMappingURL.SERITEMS_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getStaffServiceItemList(HttpServletRequest request, HttpServletResponse response,
                                              @PathVariable("domain") String domain) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            MemberBean memeberbean = (MemberBean) session.getAttribute(Constants.MEMBER_SESSION);
            if (memeberbean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            // 根据二级域名获取租户id
            if (domain == null) {
                return JsonResult.failure(ErrorCode.SYS_ERROR);
            }
            Integer tenantId = staffsService.getTenantIdByDomain(domain);

            return staffsService.getTenantsServiceItemList(tenantId);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 需求提交
     *
     * @param request
     * @param response
     * @param serviceinfoform
     * @param reserveordersform
     * @return
     */
    @Guest
    @RequestMapping(value = RequestMappingURL.SUBREQ_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult submitRequirements(HttpServletRequest request, HttpServletResponse response,
                                         ServiceInfoForm serviceinfoform, ReserveOrdersForm reserveordersform,
                                         @PathVariable("domain") String domain) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            MemberBean memeberbean = (MemberBean) session.getAttribute(Constants.MEMBER_SESSION);
            if (memeberbean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            // 根据二级域名获取租户id
            if (domain == null) {
                return JsonResult.failure(ErrorCode.SYS_ERROR);
            }
            Integer tenantId = staffsService.getTenantIdByDomain(domain);
            return staffsService.subRequirementsQuery(tenantId, memeberbean, reserveordersform, serviceinfoform);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

}
