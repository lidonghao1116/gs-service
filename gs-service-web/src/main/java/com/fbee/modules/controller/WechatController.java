package com.fbee.modules.controller;

import com.fbee.modules.basic.RequestMappingURL;
import com.fbee.modules.bean.Constants;
import com.fbee.modules.bean.MemberBean;
import com.fbee.modules.consts.ErrorCode;
import com.fbee.modules.consts.RedisKey;
import com.fbee.modules.core.config.Global;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.core.utils.TokenSingleton;
import com.fbee.modules.form.MemberInfoForm;
import com.fbee.modules.interceptor.anno.Auth;
import com.fbee.modules.interceptor.anno.Guest;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.mybatis.model.MembersInfo;
import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.service.APIUserService;
import com.fbee.modules.service.MembersInfoService;
import com.fbee.modules.utils.JsonUtils;
import com.fbee.modules.wechat.bean.OAuthTokenBean;
import com.fbee.modules.wechat.bean.WechatUserinfoBean;
import com.fbee.modules.wechat.config.WechatConfig;
import com.fbee.modules.wechat.core.WechatAuthrize;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Controller
@RequestMapping({RequestMappingURL.WECHAT_BASE_URL})
public class WechatController {

    private final static Logger log = LoggerFactory.getLogger(WechatController.class);
    private final static String UTF8 = "UTF8";

    @Autowired
    MembersInfoService membersInfoService;

    @Autowired
    APIUserService apiUserService;

    private JedisTemplate redis = JedisUtils.getJedisTemplate();

    @Guest
    @RequestMapping(value = {RequestMappingURL.GET_WECHAT_INFO}, method = {org.springframework.web.bind.annotation.RequestMethod.GET, org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public JsonResult getIndexInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam("urlpath") String urlpath) {
        return JsonResult.success(getWxConfig(request, urlpath));
    }

    /**
     * 获取微信授权用户信息
     * @return
     */
    @Auth
    @RequestMapping(value = "/info", method = org.springframework.web.bind.annotation.RequestMethod.GET)
    @ResponseBody
    public JsonResult getWechatInfo() {
        String json = redis.get(RedisKey.User.WECHAT_INFO.getKey(SessionUtils.getHeaderValue(Constants.AUTH_KEY.TOKEN)));
        if(StringUtils.isNotBlank(json)){
            return JsonResult.success(JsonUtils.fromJson(json,WechatUserinfoBean.class));
        }
        return JsonResult.failure(ErrorCode.WX_GET_USER_INFO_ERROR);
    }

    /**
     * @param request
     * @param code
     * @return
     */
    @Guest
    @RequestMapping(value = "/auth/login", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView wechatAuthLogin(ModelAndView mav, HttpServletRequest request, String code, String state,
                                        String redirect) throws UnsupportedEncodingException {
        String rwurl = "/error.html";
        if (StringUtils.isBlank(code)) {
            log.warn("请求需要code参数，需微信授权后拿到code访问");
            mav.addObject("e", ErrorCode.WX_AUTH_ERROR);
            mav.setView(new RedirectView(rwurl, false));
            return mav;
        }
        //获取授权用户信息
        OAuthTokenBean token = WechatAuthrize.getAccessToken(code);
        if (token == null) {
            log.warn("获取token失败");
            mav.addObject("e", ResultCode.AUTHORIZE_ERROR);
            mav.setView(new RedirectView(rwurl, false));
            return mav;

        }
        String openId = token.getOpenId();
        if (StringUtils.isEmpty(openId)) {
            log.warn("获取openid为空");
            mav.addObject("e", ErrorCode.WX_OPENID_AUTH_ERROR);
            mav.setView(new RedirectView(rwurl, false));
            return mav;
        }

        /**
         * 获取微信用户信息
         */
        WechatUserinfoBean userinfo = WechatAuthrize.getWechatUserinfo(token.getAccessToken(), openId, WechatConfig.LANG_ZH_CN);

        if (userinfo == null) {
            log.warn("用户微信信息获取失败··");
            mav.addObject("e", ErrorCode.WX_GET_USER_INFO_ERROR);
            mav.setView(new RedirectView(rwurl, false));
            return mav;
        }
        redis.set(RedisKey.User.WECHAT_INFO.getKey(token.getAccessToken()), JsonUtils.toJson(userinfo),30 * 24 * 60 * 60);

        /**
         * 将用户信息存储到redis, session
         */
        //根据手机号获取会员信息
        MembersInfo membersInfo = membersInfoService.getByOpenId(openId);
        MemberBean memberBean = new MemberBean();
        if (membersInfo != null) {
            //手机已注册过会员
            memberBean.setMobile(membersInfo.getMobile());
            memberBean.setRegisterTime(membersInfo.getRegisterTime());
            memberBean.setUserStatus(membersInfo.getUserStatus());
            memberBean.setIsLocked(membersInfo.getIsLocked());
            memberBean.setId(membersInfo.getId());
            memberBean.setName(membersInfo.getName());

            //如果没有昵称，则更新微信昵称
            if(StringUtils.isBlank(membersInfo.getNickname()) || StringUtils.isBlank(membersInfo.getHeadImage())){
                MemberInfoForm form = new MemberInfoForm();
                form.setHeadImage(userinfo.getHeadImgUrl());
                form.setNickname(userinfo.getNickname());
                membersInfoService.updateMemberInfo(memberBean, form);
            }

            //用户信息存储到redis
            redis.set(RedisKey.User.USER_INFO.getKey(token.getAccessToken()), JsonUtils.toJson(memberBean), 30 * 24 * 60 * 60);
        }
        //跳转首页
        log.info("跳转到首页?=" + redirect);
        StringBuilder redirectUrl = new StringBuilder();
        redirectUrl.append("/index.html?token=").append(token.getAccessToken());
        redirectUrl.append("&uid=" + memberBean.getId());
        redirectUrl.append("&to=");
        if (StringUtils.isNotBlank(redirect)) {
            redirectUrl.append(URLEncoder.encode(redirect, "UTF8"));
        }
        log.info("redirect to " + redirectUrl.toString());
        System.out.println("redirect to " + redirectUrl.toString());

        mav.setView(new RedirectView(redirectUrl.toString(), false));
        return mav;
    }


    @Guest
    @RequestMapping(value = "/auth/url", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getAuthURL() throws UnsupportedEncodingException {
        String authpath = "/fbeeWebConsole_web/api/WechatInfo/auth/login?redirect=";
        String suf = "&response_type=code&scope=snsapi_userinfo&state=@STATE@#wechat_redirect";
        StringBuffer sb = new StringBuffer();
        sb.append("https://open.weixin.qq.com/connect/oauth2/authorize?")
                .append("appid=").append(Constants.APPID)
                .append("&redirect_uri=")
                .append(URLEncoder.encode(Constants.MOBILE_HOST_NAME, UTF8))
                .append(URLEncoder.encode(authpath, UTF8))
                .append("@REDIRECT@")
                .append(suf);
        return JsonResult.success(sb.toString());
    }

    /**
     * 接收微信平台的GET事件提交,主要用于微信接入服务器TOKEN验证
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @param response
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    @Guest
    @RequestMapping(value = RequestMappingURL.PROCESS_URL, method = RequestMethod.GET)
    public void signature(@RequestParam(value = "signature", required = true) String signature,
                          @RequestParam(value = "timestamp", required = true) String timestamp,
                          @RequestParam(value = "nonce", required = true) String nonce,
                          @RequestParam(value = "echostr", required = true) String echostr, HttpServletResponse response)
            throws IOException {
        String[] values = {Constants.TOKEN, timestamp, nonce};
        Arrays.sort(values); // 字典序排序
        String value = values[0] + values[1] + values[2];
        String sign = DigestUtils.shaHex(value);
        PrintWriter writer = response.getWriter();
        if (signature.equals(sign)) {// 验证成功返回ehcostr
            writer.print(echostr);
        } else {
            writer.print("error");
        }
        writer.flush();
        writer.close();
    }

    public static Map<String, Object> getWxConfig(HttpServletRequest request, String pathurl) {
        Map<String, Object> ret = new HashMap();
        String appId = Global.getConfig("wechat.appid");
        String secret = Global.getConfig("wechat.secret");
        String requestUrl = pathurl;
        System.out.println(requestUrl + "*************************");
        String access_token = "";
        String jsapi_ticket = "";
        String timestamp = Long.toString(System.currentTimeMillis() / 1000L);
        String nonceStr = UUID.randomUUID().toString();


        Map tokenMap = TokenSingleton.getInstance().getMap();
        if (tokenMap.get("jsapi_token") != null) {
            jsapi_ticket = (String) tokenMap.get("jsapi_token");
            System.out.println(jsapi_ticket + "===========jsapi_ticket");
        }
        String signature = "";

        String sign = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url=" + requestUrl;
        System.out.println(sign + "-------------------");
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(sign.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ret.put("appId", appId);
        ret.put("timestamp", timestamp);
        ret.put("nonceStr", nonceStr);
        ret.put("signature", signature);
        System.out.println("signature=============" + signature);
        return ret;
    }

    private static String byteToHex(byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", new Object[]{Byte.valueOf(b)});
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}

