package com.fbee.modules.controller;

import com.fbee.modules.basic.RequestMappingURL;
import com.fbee.modules.bean.Constants;
import com.fbee.modules.consts.ErrorCode;
import com.fbee.modules.core.Log;
import com.fbee.modules.form.extend.StaffInfoForm;
import com.fbee.modules.interceptor.anno.Auth;
import com.fbee.modules.interceptor.anno.Guest;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.service.HouseKeepingService;
import com.fbee.modules.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(RequestMappingURL.HOUSE_KEEPING)
public class HouseKeepingController extends BaseController {
    @Autowired
    HouseKeepingService houseKeepingService;

    @Autowired
    TenantService tenantService;

    /**
     * 获取服务列表
     *
     * @return
     */
    @Guest
    @RequestMapping(value = RequestMappingURL.SERVICE_ITEMS, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getStaffServiceItemList(HttpServletRequest request, HttpServletResponse response,
                                              @PathVariable("domain") String domain) {
        try {
            // 根据二级域名获取租户id
            if (domain == null) {
                return JsonResult.failure(ErrorCode.SYS_ERROR);
            }
            Integer tenantId = tenantService.getTenantIdByDomain(domain);
            return houseKeepingService.getStaffServiceItemList(tenantId);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 获取服务内容与对象
     *
     * @param request
     * @param response
     * @param itemCode
     * @return
     */
    @Guest
    @RequestMapping(value = RequestMappingURL.OBJEC_CONTENT, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getObjectContent(HttpServletRequest request, HttpServletResponse response, String itemCode,
                                       @PathVariable("domain") String domain) {
        try {
            // 根据二级域名获取租户id
            if (domain == null) {
                return JsonResult.failure(ErrorCode.SYS_ERROR);
            }
            Integer tenantId = tenantService.getTenantIdByDomain(domain);
            Map<Object, Object> map = new HashMap<Object, Object>();
            map.put("itemCode", itemCode);
            map.put("tenantId", tenantId);
            return houseKeepingService.getObjectContent(map);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 获取员工信息列表
     *
     * @param staffInfoForm
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Guest
    @RequestMapping(value = RequestMappingURL.STAFF_PERSON_INFO_LIST, method = {RequestMethod.GET,
            RequestMethod.POST})
    @ResponseBody
    public JsonResult getStaffPersonInfo(StaffInfoForm staffInfoForm,
                                         @RequestParam(value = "pageNumber", defaultValue = Constants.DEFAULT_PAGE_NO) int pageNumber,
                                         @PathVariable("domain") String domain, @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize) {
        try {
            // 根据二级域名获取租户id
            if (domain == null) {
                return JsonResult.failure(ErrorCode.SYS_ERROR);
            }
            Integer tenantId = tenantService.getTenantIdByDomain(domain);
            staffInfoForm.setTenantId(tenantId);
            return houseKeepingService.getStaffPersonInfo(staffInfoForm, pageNumber, pageSize);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 获取员工个人信息
     *
     * @param staffId
     * @return
     */
    @Guest
    @RequestMapping(value = RequestMappingURL.STAFF_PERSON_INFO, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getStaffPersonInfo(int staffId) {
        try {
            return houseKeepingService.getStaffPersonInfoByPrimaryKey(staffId);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 获取员工个人的视频与照片
     *
     * @param staffId
     * @param type
     * @return
     */
    @Guest
    @RequestMapping(value = RequestMappingURL.STAFF_VIDEO_IMG, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getImgVedioList(int staffId, String type) {
        try {
            return houseKeepingService.getImgVedioList(staffId, type);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 获取员工已认证的证书
     *
     * @param staffId
     * @return
     */
    @Guest
    @RequestMapping(value = RequestMappingURL.STAFF_CERT_TYPE, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getStaffCertsType(int staffId) {
        try {

            return houseKeepingService.getStaffCertsType(staffId);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 获取员工个人的服务信息
     *
     * @param staffId
     * @return
     */
    @Guest
    @RequestMapping(value = RequestMappingURL.STAFF_SERVICE_INFO, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getStaffServiceInfo(int staffId) {
        try {

            return houseKeepingService.getStaffServiceInfo(staffId);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

}