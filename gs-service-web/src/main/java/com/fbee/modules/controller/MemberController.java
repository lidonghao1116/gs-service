package com.fbee.modules.controller;

import com.fbee.modules.basic.RequestMappingURL;
import com.fbee.modules.bean.Constants;
import com.fbee.modules.bean.MemberBean;
import com.fbee.modules.consts.ErrorCode;
import com.fbee.modules.consts.RedisKey;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.bean.CaptchaCode;
import com.fbee.modules.core.bean.SmsCode;
import com.fbee.modules.core.config.Global;
import com.fbee.modules.core.utils.GenerateCaptcha;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.form.LoginForm;
import com.fbee.modules.form.MemberInfoForm;
import com.fbee.modules.interceptor.anno.Auth;
import com.fbee.modules.interceptor.anno.Guest;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.jsonData.json.CaptchaJsonData;
import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.service.MembersInfoService;
import com.fbee.modules.service.SmsService;
import com.fbee.modules.service.TenantService;
import com.fbee.modules.utils.JsonUtils;
import com.fbee.modules.validate.LoginFormValidate;
import net.sf.json.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @ClassName: UserController
 * @Description: 用户登陆、登出、图形验证码
 */
@Controller
@RequestMapping(RequestMappingURL.MEMBERS_BASE_URL)
public class MemberController {

    private final static Logger log = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    TenantService tenantService;
    @Autowired
    MembersInfoService membersInfoService;

    @Autowired
    SmsService smsService;

    //private JedisTemplate redis = JedisUtils.getJedisTemplate();

    @Auth
    @RequestMapping(value = RequestMappingURL.CAPTCHA_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getCaptcha(HttpServletRequest request, HttpServletResponse response) {
        CaptchaJsonData jsonData = new CaptchaJsonData();
        try {
            GenerateCaptcha randomCode = new GenerateCaptcha();
            String captcha = randomCode.getRandFourcodeBase64(request, response, 4, null);

            if (StringUtils.isNotBlank(captcha)) {
                jsonData.setCaptcha(captcha);
                return JsonResult.success(jsonData);
            } else {
                return JsonResult.failure(ResultCode.User.CAPTCHA_FAILURE);
            }
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    @Auth
    @RequestMapping(value = RequestMappingURL.SENDSMS, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult sendRegisterSmsCode(HttpServletRequest request, HttpServletResponse response, String mobile,
                                          String captcha) {
        if(!"prod".equalsIgnoreCase(Global.getConfig("env"))){
            return JsonResult.success("test");
        }
        HttpSession session = request.getSession();
        // 验证图形验证码
        CaptchaCode captchaObj = (CaptchaCode) session.getAttribute(GenerateCaptcha.RANDOMCODEKEY);
        session.removeAttribute(GenerateCaptcha.RANDOMCODEKEY);

        if (captchaObj == null || captchaObj.isExpired()) { // 验证码过期
            return JsonResult.failure(ResultCode.User.CAPTCHA_TIMEOUT);
        } else if (!captchaObj.getCode().toUpperCase().equals(captcha.toUpperCase())) {// 验证码不正确
            return JsonResult.failure(ResultCode.User.CAPTCHA_ERROR);
        } else {

            // 获取当前日期
            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String addTime = formatter.format(currentTime);

            // 获取当日验证短信条数
            int count = smsService.getCount(mobile, addTime);

            // 验证当日注册短信次数
            if (count > 10) {
                return JsonResult.failure(ResultCode.User.OVER_ERROR);
            }

            // 清空session
            session.removeAttribute(SmsCode.REG_SMS_CODE_KEY);

            // 获取session中短信验证码对象
            SmsCode smsCode = smsService.sendRegisterSmsCode(mobile);
            session.setAttribute("mobile", mobile);
            // 短信发送成功
            if (smsCode != null) {
                session.setAttribute(SmsCode.REG_SMS_CODE_KEY, smsCode);
                return JsonResult.success(smsCode.getCode());
            }

            // 短信发送失败
            return JsonResult.failure(ResultCode.User.SMSSEND_FAILURE);
        }
    }

    @Auth
    @RequestMapping(value = RequestMappingURL.LOGIN_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult login(HttpServletRequest request, HttpServletResponse response,
                            @PathVariable("domain") String domain, LoginForm loginForm) {

        if(!"prod".equalsIgnoreCase(Global.getConfig("env"))){
            return membersInfoService.login(loginForm, request);
        }

        // 根据二级域名获取租户id
        if (domain == null) {
            return JsonResult.failure(ErrorCode.SYS_ERROR);
        }
        Integer tenantId = tenantService.getTenantIdByDomain(domain);
        // 验证表单数据
        JsonResult jsonResult = LoginFormValidate.validLogin(loginForm);
        if (!jsonResult.isSuccess()) {
            return jsonResult;
        }

        HttpSession session = SessionUtils.getSession(request);

        // 验证短信验证码
        SmsCode smsCode = (SmsCode) session.getAttribute(SmsCode.REG_SMS_CODE_KEY);

        // session是否过期
        if (smsCode == null) {
            return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
        }

        // 验证码是否过期
        if (smsCode.isExpired()) {
            return JsonResult.failure(ResultCode.User.SMSSEND_TIMEOUT);
        }

        // 验证短信验证码
        String sessionCode = smsCode.getCode();
        if (loginForm.getCode().toUpperCase().equals(sessionCode.toUpperCase())) {
            jsonResult = membersInfoService.login(loginForm, request);
            return jsonResult;
        }

        return JsonResult.failure(ResultCode.User.CODE_ERROR);
    }

    /**
     * 我的资料查询
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = RequestMappingURL.GETMEMBERINFO, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getMemberInfo(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            MemberBean memeberbean = (MemberBean) session.getAttribute(Constants.MEMBER_SESSION);
            if (memeberbean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            JsonResult js = membersInfoService.getMemberInfo(memeberbean);
            return js;
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 我的资料修改
     *
     * @param request
     * @param response
     * @param memberInfoForm
     * @return
     */
    @RequestMapping(value = RequestMappingURL.UPDATEMEMBERINFO, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult updateMemberInfo(HttpServletRequest request, HttpServletResponse response,
                                       MemberInfoForm memberInfoForm) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            MemberBean memeberbean = (MemberBean) session.getAttribute(Constants.MEMBER_SESSION);
            if (memeberbean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return JsonResult.success(membersInfoService.updateMemberInfo(memeberbean, memberInfoForm));
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 退出登录
     *
     * @param request
     * @param response
     * @return
     */
    @Guest
    @RequestMapping(value = RequestMappingURL.LOGOUT_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult userLogout(HttpServletRequest request, HttpServletResponse response,
                                 @PathVariable("domain") String domain) {
        try {
            if (domain == null) {
                return JsonResult.failure(ErrorCode.SYS_ERROR);
            }
            Integer tenantId = tenantService.getTenantIdByDomain(domain);
            JsonResult jsonResult = membersInfoService.logout(request, tenantId);
            //redis.del(RedisKey.User.WECHAT_INFO.getKey(SessionUtils.getHeaderValue(Constants.AUTH_KEY.TOKEN)));
            //redis.del(RedisKey.User..getKey(SessionUtils.getHeaderValue(Constants.AUTH_KEY.TOKEN)));
            return jsonResult;
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

}
