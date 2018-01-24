package com.fbee.modules.controller;

import com.fbee.modules.basic.RequestMappingURL;
import com.fbee.modules.bean.Constants;
import com.fbee.modules.bean.GatewayPrepayInfo;
import com.fbee.modules.bean.MemberBean;
import com.fbee.modules.consts.ErrorCode;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.config.Global;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.service.*;
import com.fbee.modules.utils.HttpUtils;
import com.fbee.modules.utils.JsonUtils;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 赵利壮
 * @ClassName: OrdersController
 * @Description: 订单控制器
 * @date 2016年12月28日 下午12:04:07
 */

@Controller
@RequestMapping(RequestMappingURL.Orders_BASE_URL)
public class OrdersController {

    private final static Logger log = LoggerFactory.getLogger("paymentLogger");

    @Autowired
    OrdersService ordersService;

    @Autowired
    StaffService staffService;

    @Autowired
    CommonService commonService;

    @Autowired
    TenantService tenantService;


    @Autowired
    private GateWayService service;


    /**
     * 订单列表
     *
     * @param request
     * @param response
     * @param pageNumber
     * @param pageSize
     * @param orderstatus
     * @return
     */
    @RequestMapping(value = RequestMappingURL.ORDERS_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    @Deprecated
    public JsonResult getStaffServiceItemList(HttpServletRequest request, HttpServletResponse response,
                                              @RequestParam(value = "pageNumber", defaultValue = Constants.DEFAULT_PAGE_NO) int pageNumber,
                                              @RequestParam(value = "pageSize", defaultValue = Constants.ORDERS_PAGE_SIZE) int pageSize,

                                              @RequestParam(value = "orderstatus") String orderstatus) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            MemberBean memeberbean = (MemberBean) session.getAttribute(Constants.MEMBER_SESSION);
            if (memeberbean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return ordersService.getOrdersList(memeberbean.getId(), orderstatus, pageNumber, pageSize);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 订单列表
     * @return
     */
    @RequestMapping(value = RequestMappingURL.ORDER_LIST_URL, method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getOrderList(HttpServletRequest request, HttpServletResponse response,
                                              @RequestParam(value = "pageNumber", defaultValue = Constants.DEFAULT_PAGE_NO) int pageNumber,
                                              @RequestParam(value = "pageSize", defaultValue = Constants.ORDERS_PAGE_SIZE) int pageSize,

                                              @RequestParam(value = "orderstatus") String orderstatus) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            MemberBean memeberbean = (MemberBean) session.getAttribute(Constants.MEMBER_SESSION);
            if (memeberbean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return ordersService.getOrderWithStaffList(memeberbean.getId(), orderstatus, pageNumber, pageSize);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 阿姨详情
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = RequestMappingURL.STAFFINFO_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getStaffInfo(HttpServletRequest request, HttpServletResponse response,
                                   @RequestParam(value = "staffId") String staffId, @PathVariable("domain") String domain) {
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
            Integer tenantId = tenantService.getTenantIdByDomain(domain);
            return staffService.getStaffInfo(staffId, tenantId + "");
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 订单详情
     *
     * @param request
     * @param response
     * @param orderNo
     * @return
     */
    @Deprecated
    @RequestMapping(value = RequestMappingURL.ORDERSDETAILS_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult reserveOrderDetails(HttpServletRequest request, HttpServletResponse response,
                                          @RequestParam("orderNo") String orderNo) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            MemberBean memeberbean = (MemberBean) session.getAttribute(Constants.MEMBER_SESSION);
            if (memeberbean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return ordersService.orderDetails(orderNo);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }


    /**
     * 订单详情
     *
     * @param orderNo
     * @return
     */
    @RequestMapping(value = RequestMappingURL.ORDERS_DETAILS_URL, method = RequestMethod.GET)
    @ResponseBody
    public JsonResult reserveOrderDetail(@RequestParam("orderNo") String orderNo) {
        try {
            HttpSession session = SessionUtils.getSession();
            MemberBean memeberbean = (MemberBean) session.getAttribute(Constants.MEMBER_SESSION);
            if (memeberbean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return ordersService.getOrderDetails(orderNo);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 我的订单-取消订单（支付定金前后）
     *
     * @param request
     * @param response
     * @param orderNo
     * @param cancleReason
     * @param counterFee
     * @param tradeAmount
     * @return
     */
    @RequestMapping(value = RequestMappingURL.CANCELORDER_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult cancleOrder(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam("orderNo") String orderNo, @RequestParam("cancleReason") String cancleReason,
                                  @RequestParam("counterFee") String counterFee, @RequestParam("tradeAmount") String tradeAmount) {
        try {
            return JsonResult.success(ordersService.cancleOrder(orderNo, cancleReason, counterFee, tradeAmount));
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 获取支付流水号,用于充值业务
     *
     * @return
     */
    @Deprecated
    @RequestMapping(value = "before.do", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public JsonResult getTradeFlowNo(HttpServletRequest request, HttpServletResponse response, String orderNo, String payAmount) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            MemberBean memeberbean = (MemberBean) session.getAttribute(Constants.MEMBER_SESSION);
            if (memeberbean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            //生成payinfo no
            String tradeFlowNo = commonService.createOrderNo(Constants.ZF);//支付流水号 06
            log.info(String.format("create order no[%s] : flowNo[%s]", orderNo, tradeFlowNo));

            session.setAttribute("pay_order_no", orderNo);

            return JsonResult.success(tradeFlowNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送http请求
     *
     * @param request
     * @param response
     * @return
     */
    @Deprecated
    @RequestMapping(value = "sendPayRequest.do", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String sendPayRequest(HttpServletRequest request, HttpServletResponse response) {

        String data = request.getParameter("pay_data");
        if (StringUtils.isBlank(data)) {
            log.info("request data[QR_URL] is null");
            return "data is null";
        }

        return data;
    }

    /**
     * 查询支付结果
     *
     * @param request
     * @param response
     * @param flowNo
     * @return
     */
    @RequestMapping(value = "getPayResult.do", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public JsonResult getPayResult(HttpServletRequest request, HttpServletResponse response, String flowNo) {
        return tenantService.getPayResult(flowNo);
    }

    /**
     * 更换阿姨
     *
     * @param request
     * @param response
     * @param orderNo
     * @return
     */
    @RequestMapping(value = RequestMappingURL.CHANGESTAFF_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult changeStaff(HttpServletRequest request, HttpServletResponse response, String orderNo,
                                  String customerRemark) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            MemberBean memeberbean = (MemberBean) session.getAttribute(Constants.MEMBER_SESSION);
            if (memeberbean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return ordersService.changeStaff(orderNo, customerRemark);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 本地订单-更换阿姨
     *
     * @param orderNo author Baron
     *                date 2017-05-02
     * @return
     */
    @RequestMapping(value = RequestMappingURL.CHANGEHS_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult changehs(HttpServletRequest request, HttpServletResponse response, String orderNo, String type, String remark) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            MemberBean memeberbean = (MemberBean) session.getAttribute(Constants.MEMBER_SESSION);
            if (memeberbean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return JsonResult.success(ordersService.changehs(orderNo, type, remark));
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 获取支付流水号,用于充值业务
     *
     * @return
     */
    @RequestMapping(value = "beforeWechat.do", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public JsonResult getWechatTradeFlowNo(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            MemberBean memeberbean = (MemberBean) session.getAttribute(Constants.MEMBER_SESSION);
            if (memeberbean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }

            String code = String.valueOf(request.getParameter("code"));
            System.out.println("code为：" + code);

            StringBuilder url = new StringBuilder();
            //获取配置文件中的密钥
            String appid = Global.getConfig("wechat.appid");
            String secret = Global.getConfig("wechat.secret");

            url.append("https://api.weixin.qq.com/sns/oauth2/access_token?")
                    .append("appid=" + appid)
                    .append("&secret=" + secret)
                    .append("&code=" + code + "&grant_type=authorization_code");
            String wechatResult = HttpUtils.sendGet(url.toString());
            System.out.println("返回结果为：" + wechatResult);

            JSONObject json = JSONObject.fromObject(wechatResult);
            String openId = json.getString("openid");
            //----测试----
//			String openId = "ocj2FwZZeD8o3tVksHkLVnknMmYw";
            System.out.println("openId=" + openId);

            String state = request.getParameter("state");
            String stateArr[] = state.split(" ");
            String orderNo = stateArr[0];
            String payAmount = stateArr[1];
            System.out.println("orderNo:" + orderNo + "&payAmount:" + payAmount);
            String result = ordersService.verificationPayAmount(orderNo, payAmount);//判断金额,上生产环境解除
            if ("1".equals(result)) {
                JSONObject jsonResult = new JSONObject();
                String flowNo = commonService.createOrderNo("06");
                ordersService.payment(flowNo, orderNo);
                jsonResult.put("openId", openId);
                jsonResult.put("orderNo", flowNo);
                return JsonResult.success(jsonResult);
            } else {
                return JsonResult.failure(ResultCode.PAYAMOUNT);//金额不一致
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送http请求
     *
     * @return
     */
    @RequestMapping(value = "sendBeforePayRequest.do", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String sendBeforePayRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("[sendBeforePayRequest], request for CodeImgUrl");
            HttpSession session = SessionUtils.getSession(request);
            MemberBean memeberbean = (MemberBean) session.getAttribute(Constants.MEMBER_SESSION);
            if (memeberbean == null) {
                log.info("[sendBeforePayRequest] user session is null");
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT).toString();
            }

            String tradeFlowNo = request.getParameter("orderNo");
            String payAmount = request.getParameter("payAmount");
            String transDesc = request.getParameter("transDesc");
            String payType = request.getParameter("payType"); //P03支付宝，P02微信

            log.info(String.format("query orderNo from tenantflow by flowNo [%s]", tradeFlowNo));
            String orderNo = (String)session.getAttribute("pay_order_no");
            if(StringUtils.isBlank(orderNo)){
                log.info(String.format("orderNo is not exist [%s]", orderNo));
                return "orderNo is not exist";
            }


            log.info(String.format("verificationPayAmount[%s][%s]", orderNo ,payAmount));
            String amountCorrect = ordersService.verificationPayAmount(orderNo, payAmount);
            if (StringUtils.isBlank(amountCorrect)) {
                log.info("order status is not between in [01,03], can't pay");
                return "order status is not between in [01,03], can't pay";
            }
            if (amountCorrect.equals("0") && "prod".equalsIgnoreCase(Global.getConfig("env"))) {
                log.info("amount is error, please send request to pay again.");
                return "amount is error, please send request to pay again.";//金额不一致
            }

            log.info(String.format("create gateway [%s]", tradeFlowNo));
            GatewayPrepayInfo gw = null;
            if ("P03".equalsIgnoreCase(payType)) {
                gw = GatewayPrepayInfo.getAliQRPay(tradeFlowNo, Double.valueOf(payAmount), transDesc);
            } else if ("P02".equalsIgnoreCase(payType)) {
                gw = GatewayPrepayInfo.getWxQRPay(tradeFlowNo, Double.valueOf(payAmount), transDesc);
            } else {
                log.error(String.format("payType[%s] error. need between [P02,P03]", payType));
                return String.format("payType[%s] error. need between [P02,P03]", payType);
            }
            log.info("create orderNo[%s] prepay info [%s]", orderNo, JsonUtils.toJson(gw));
            Object rlt = service.prepay(gw, orderNo);
            log.info("prepay end .. " + JsonUtils.toJson(rlt));

            Map<String, String> str = new HashMap<String, String>();
            str.put("pay_data", JsonUtils.toJson(rlt));
            return JsonUtils.toJson(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 微信公众号支付校验
     *
     * @return
     */
    @RequestMapping(value = "sendBeforeWechatPayRequest.do", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String sendBeforeWechatPayRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            MemberBean memeberbean = (MemberBean) session.getAttribute(Constants.MEMBER_SESSION);
            if (memeberbean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT).toString();
            }
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = ordersService.sendBeforePayRequest();// 发送http请求
            List<BasicNameValuePair> param = new ArrayList<BasicNameValuePair>();
            param.add(new BasicNameValuePair("orderNo", request.getParameter("orderNo")));
            param.add(new BasicNameValuePair("payAmount", request.getParameter("payAmount")));
            param.add(new BasicNameValuePair("transDesc", request.getParameter("transDesc")));
            param.add(new BasicNameValuePair("payType", request.getParameter("payType")));
            param.add(new BasicNameValuePair("openId", request.getParameter("openId")));
            httpPost.setEntity(new UrlEncodedFormEntity(param, StandardCharsets.UTF_8));
            HttpResponse res = httpclient.execute(httpPost);
            HttpEntity entity = res.getEntity();
            String result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 微信公众号支付
     * 发送http请求
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "sendWechatPayRequest.do", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String sendWechatPayRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            MemberBean memeberbean = (MemberBean) session.getAttribute(Constants.MEMBER_SESSION);
            if (memeberbean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT).toString();
            }
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = ordersService.sendPcPayRequest();    //微信公众号支付
            List<BasicNameValuePair> param = new ArrayList<BasicNameValuePair>();
            param.add(new BasicNameValuePair("pay_data", request.getParameter("pay_data")));
            param.add(new BasicNameValuePair("sign_msg", request.getParameter("sign_msg")));
            param.add(new BasicNameValuePair("callbackUrl", request.getParameter("callbackUrl")));
            httpPost.setEntity(new UrlEncodedFormEntity(param, StandardCharsets.UTF_8));
            HttpResponse res = httpclient.execute(httpPost);
            HttpEntity entity = res.getEntity();
            String result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
