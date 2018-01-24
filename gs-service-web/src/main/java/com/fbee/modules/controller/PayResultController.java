package com.fbee.modules.controller;

import com.fbee.modules.basic.RequestMappingURL;
import com.fbee.modules.bean.SwiftpassCallbackInfo;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.utils.XmlUtils;
import com.fbee.modules.interceptor.anno.Guest;
import com.fbee.modules.service.CallbackService;
import com.fbee.modules.service.CommonService;
import com.fbee.modules.service.PayService;
import com.fbee.modules.utils.JsonUtils;
import net.sf.json.JSON;
import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;


/**
 * @author 赵利壮
 * @ClassName: PayResultController
 * @Description: TODO
 * @date 2017年2月20日 下午4:30:26
 */
@Controller
@RequestMapping(RequestMappingURL.PayResult_BASE_URL)
public class PayResultController {

    private static final Logger log = LoggerFactory.getLogger("callbackLogger");

    @Autowired()
    CallbackService callbackService;

    @Autowired
    CommonService commonService;

    /**
     * 回调接口-处理业务
     * tips: 使用以前老的sinopay支付网关。用于扫码支付
     *
     * @param request
     * @param response
     * @return
     * @throws DocumentException
     */
    @Guest
    @RequestMapping(value = "result.do", method = RequestMethod.POST)
    @ResponseBody
    public String getPayRequest(HttpServletRequest request, HttpServletResponse response) throws DocumentException {
        String returnCode = "{" + "\"returnCode" + "\":" + "\"fail" + "\"}";
        try {
            String resString = request.getParameter("notify_data");
            if (StringUtils.isBlank(resString)) {
                log.info("PayResult: 回调数据为空，return fail");
                return returnCode;
            }
            try {
                log.info("PayResult:回调数据为："+ resString);
                String str = callbackService.process(resString); //处理业务
                if ("success".equalsIgnoreCase(str)) {
                    returnCode = "{" + "\"returnCode" + "\":" + "\"success" + "\"}";
                }
            } catch (Exception e) {
                log.info("callback error : " + e.getMessage());
                return returnCode;
            }

            return returnCode;
        } catch (Exception e) {
            e.printStackTrace();
            return returnCode;
        }
    }

}
