package com.fbee.modules.controller;


import com.fbee.modules.basic.RequestMappingURL;
import com.fbee.modules.core.Log;
import com.fbee.modules.form.CertCheckForm;
import com.fbee.modules.interceptor.anno.Guest;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.service.InCertCkeckService;
import com.fbee.modules.utils.JsonUtils;
import com.fbee.modules.utils.ZSCXQuery;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.Collections;

@Controller
@RequestMapping(RequestMappingURL.ZSCX_BASE_URL)
public class InCertCheckController {

    @Autowired
    InCertCkeckService inCertCkeckService;

    private JedisTemplate redis = JedisUtils.getJedisTemplate();

    private static final String REDIS_KEY = "CERT.QUERY.UID.";

    //图形码验证
    @Guest
    @RequestMapping(value = RequestMappingURL.ZSCX_CAPTCHA_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getCaptcha(HttpServletRequest request, HttpServletResponse response) {
        try {

            String mapkey = ZSCXQuery.getImageRequest();
            String uuid = RandomStringUtils.random(32, true, true);
            redis.setex(REDIS_KEY + uuid, mapkey, 600);
            return JsonResult.success(Collections.singletonMap("uid", uuid));
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 证书数据爬取
     *
     * @param request
     * @param response
     * @param certCheckForm
     * @return
     */
    @Guest
    @RequestMapping(value = RequestMappingURL.ZSCX_CKECK_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult selectCertInfoById(HttpServletRequest request, HttpServletResponse response,
                                         CertCheckForm certCheckForm) {
        try {
            //中文解码
            String nameurl = URLDecoder.decode(certCheckForm.getName(), "UTF-8");
            certCheckForm.setName(nameurl);
            String clientkey = redis.get(REDIS_KEY + certCheckForm.getUid());
            if (clientkey == null) {
                JsonResult.failure(ResultCode.ERROR);
            }
            JsonResult success = inCertCkeckService.checkCertInfo(certCheckForm, clientkey);
            //查询有数据后放进缓存中
            if (success.getCode().equals(0) || success.getCode().equals(999015)) {
                redis.set(REDIS_KEY + certCheckForm.getUid() + ".form", JsonUtils.toJson(certCheckForm), 600);
            }
            return success;
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 证书列表查询
     *
     * @return
     */
    @Guest
    @RequestMapping(value = RequestMappingURL.QUERY_CERT_INFO, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult queryCertInfoById(String uid, String idcard, @RequestParam(value = "name") String name) {
        try {

            String capCode = redis.get(REDIS_KEY + uid + ".form");

            if (capCode == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            } else if (idcard == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            } else if (name == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            } else if (capCode != null) {
                CertCheckForm certCheckForm = JsonUtils.fromJson(capCode, CertCheckForm.class);
                if (!certCheckForm.getIdcard().equals(idcard)) {
                    return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
                } else if (!certCheckForm.getName().equals(name)) {
                    return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
                }
            }

            return inCertCkeckService.queryCertInfo(idcard, name);

        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }


}
