package com.fbee.modules.controller;

import com.fbee.modules.basic.RequestMappingURL;
import com.fbee.modules.bean.Constants;
import com.fbee.modules.bean.MemberBean;
import com.fbee.modules.bean.Status;
import com.fbee.modules.consts.ErrorCode;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.utils.DateUtils;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.interceptor.anno.Guest;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.service.DictionarysCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 贺章鹏
 * @ClassName: CommonController
 * @Description: 公用部分
 * 包含：【省市区】、【服务工种】、【个人特点】、【服务类型】、【技能特点】、【服务价格单位】、
 * 【阿姨查询条件：价格、经验、年龄段、属相、学历、籍贯】
 * @date 2017年1月20日 下午1:45:09
 */
@Controller
@RequestMapping(RequestMappingURL.COMMON_BASE_URL)
public class CommonController {

    @Autowired
    DictionarysCacheService dictionarysCacheService;

    @Guest
    @RequestMapping(value = "/now", method = RequestMethod.GET)
    @ResponseBody
    public String getNow(){
        return DateUtils.formatDateTime(new Date());
    }

    /**
     * 获取字典型参数
     * typeCode
     *
     * @param request
     * @param response
     * @return
     */
    @Guest
    @RequestMapping(value = RequestMappingURL.GET_DICTIONARY_DATA, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getDictionaryData(HttpServletRequest request, HttpServletResponse response, @PathVariable("typeCode") String typeCode) {
        try {
            if (StringUtils.isBlank(Status.getDesc(typeCode))) {
                JsonResult.failure(ResultCode.PARAMS_ERROR);
            }
            return dictionarysCacheService.getDictionaryData(Status.getDesc(typeCode));
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 获取区域值--省市区
     * pcode:父级code
     *
     * @param request
     * @param response
     * @param typeCode
     * @return
     */
    @Guest
    @RequestMapping(value = RequestMappingURL.GET_AREA_DATA, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getAreaData(HttpServletRequest request, HttpServletResponse response
            , @PathVariable("typeCode") String typeCode, String pcode) {
        try {
            if (StringUtils.isBlank(Status.getDesc(typeCode))) {
                JsonResult.failure(ResultCode.PARAMS_ERROR);
            }
            return dictionarysCacheService.getAreaData(Status.getDesc(typeCode), pcode);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 获取服务工种的技能特定、个人特点、服务工种的服务类型
     *
     * @param request
     * @param response
     * @param typeCode
     * @return
     */
    @Guest
    @RequestMapping(value = RequestMappingURL.GET_SKILLS_DATA, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getSKillsData(HttpServletRequest request, HttpServletResponse response
            , @PathVariable("typeCode") String typeCode, String itemCode, String level) {
        try {
            if (StringUtils.isBlank(Status.getDesc(typeCode))) {
                JsonResult.failure(ResultCode.PARAMS_ERROR);
            }
            return dictionarysCacheService.getSKillsData(Status.getDesc(typeCode), itemCode, level);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 判断用户是否登录
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = RequestMappingURL.IS_LOGIN, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult isLogin(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            MemberBean memeberbean=(MemberBean) session.getAttribute(Constants.MEMBER_SESSION);
            if(memeberbean == null){
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }else{
                return JsonResult.success(memeberbean);
            }
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }
}
