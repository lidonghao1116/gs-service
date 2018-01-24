package com.fbee.modules.controller;


import com.fbee.modules.bean.Constants;
import com.fbee.modules.bean.GatewayPrepayInfo;
import com.fbee.modules.bean.MemberBean;
import com.fbee.modules.consts.ErrorCode;
import com.fbee.modules.consts.OrderConst;
import com.fbee.modules.core.config.Global;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.service.CommonService;
import com.fbee.modules.service.GateWayService;
import com.fbee.modules.service.OrdersService;
import com.fbee.modules.util.WebUtils;
import com.fbee.modules.utils.HttpUtils;
import com.fbee.modules.utils.JsonUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 支付请求
 */
@RestController
@RequestMapping("/api/pay")
public class PaymentController {

    private static final Logger log = LoggerFactory.getLogger("paymentLogger");

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private GateWayService service;

    @Autowired
    private CommonService commonService;

    /**
     * 申请支付
     */
    @RequestMapping(value = "/prepay", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult prepay(String orderNo) {
        log.info("prepay == orderNo:" + orderNo);
//        if (StringUtils.isBlank(code)) {
//            log.warn("用户未授权");
//            return JsonResult.failure(ErrorCode.WX_AUTH_ERROR);
//        }
        //0. 判断用户登陆
        MemberBean user = WebUtils.getMember();
        if (user == null) {
            log.warn("获取不到用户信息");
            return JsonResult.failure(ErrorCode.USER_NOT_FOUND);
        }
        log.info("session user:" + JsonUtils.toJson(user));
//        if(StringUtils.isBlank(user.getOpenId())){
//            String openId = getOpenId(code);
//            if(StringUtils.isBlank(user.getOpenId())){
//                user.setOpenId(openId);
//            }
//        }
        if (user == null) {
            log.warn("未查询到用户登陆信息");
            return JsonResult.failure(ErrorCode.USER_NEED_LOGIN);
        }
        if (StringUtils.isBlank(user.getOpenId())) {
            log.warn("用户未绑定微信，请重新授权");
            return JsonResult.failure(ErrorCode.WX_AUTH_ERROR);
        }
        //1. 查询订单是否存在
        Map<String, Object> orderInfo = ordersService.getOrder(orderNo);
        if (orderInfo == null) {
            log.warn("未查询到订单信息");
            return JsonResult.failure(ErrorCode.ORDER_NOT_EXIST);
        }
        if (!user.getId().equals((Integer) orderInfo.get("MEMBER_ID"))) {
            log.warn("订单用户与当前用户不匹配");
            return JsonResult.failure(ErrorCode.ORDER_NOT_OWN);
        }
        String status = (String) orderInfo.get("ORDER_STATUS");
        if (!OrderConst.OrderStatus.WAIT_PAY_DEPOSIT.getCode().equals(status)
                && !OrderConst.OrderStatus.WAIT_PAY_BALANCE.getCode().equals(status)) {
            log.warn("当前订单不是待支付状态");
            return JsonResult.failure(ErrorCode.ORDER_CANT_PAY);
        }
        BigDecimal amount = null;
        String body = null;
        if (OrderConst.OrderStatus.WAIT_PAY_DEPOSIT.getCode().equals(status)) {
            amount = (BigDecimal) orderInfo.get("ORDER_DEPOSIT");
            body = String.format("[%s]支付定金", orderNo);
        } else if (OrderConst.OrderStatus.WAIT_PAY_BALANCE.getCode().equals(status)) {
            amount = (BigDecimal) orderInfo.get("ORDER_BALANCE");
            body = String.format("[%s]支付尾款", orderNo);
        }

        //3. 支付请求
        String tradeFlowNo = commonService.createOrderNo(Constants.ZF);//支付流水号 06
        GatewayPrepayInfo gw = GatewayPrepayInfo.getWxJsPayInfo(tradeFlowNo, amount.doubleValue(), user.getOpenId(), body);
        Object result = null;
        try {
            result = service.prepay(gw, orderNo);
            log.info("prepay end .. " + JsonUtils.toJson(result));
            return JsonResult.success(result);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JsonResult.failure(ErrorCode.WX_OPENID_AUTH_ERROR);

    }

    public String getOpenId(String code) {
        log.info("code为：" + code);

        StringBuilder url = new StringBuilder();
        // 获取配置文件中的密钥
        String appid = Global.getConfig("wechat.appid");
        String secret = Global.getConfig("wechat.secret");

        url.append("https://api.weixin.qq.com/sns/oauth2/access_token?")
                .append("appid=" + appid)
                .append("&secret=" + secret)
                .append("&code=" + code + "&grant_type=authorization_code");
        log.info(String.format("get openid [%s]", url.toString()));
        String wechatResult = HttpUtils.sendGet(url.toString());
        log.info("返回结果为：" + wechatResult);

        JSONObject json = JSONObject.fromObject(wechatResult);
        String openId = json.getString("openid");
        log.info(String.format("open id == [%s]", openId));
        return openId;
    }

}
