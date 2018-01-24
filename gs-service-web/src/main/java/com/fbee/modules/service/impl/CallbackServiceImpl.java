package com.fbee.modules.service.impl;

import com.fbee.modules.bean.Constants;
import com.fbee.modules.bean.ErrorMsg;
import com.fbee.modules.bean.PayInfo;
import com.fbee.modules.bean.SwiftpassCallbackInfo;
import com.fbee.modules.consts.JobConst;
import com.fbee.modules.consts.OrderConst;
import com.fbee.modules.consts.SwiftpassConfig;
import com.fbee.modules.core.page.form.TenantJobResumeForm;
import com.fbee.modules.core.utils.DateUtils;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.mybatis.dao.*;
import com.fbee.modules.mybatis.entity.TenantsFundsEntity;
import com.fbee.modules.mybatis.model.*;
import com.fbee.modules.operation.PayRecordesOpt;
import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.service.CallbackService;
import com.fbee.modules.service.CommonService;
import com.fbee.modules.util.prepay.SignUtils;
import com.fbee.modules.util.prepay.XmlUtils;
import com.fbee.modules.utils.DictionarysCacheUtils;
import com.fbee.modules.utils.JsonUtils;
import com.fbee.modules.wechat.message.config.WechatConfig;
import com.fbee.modules.wechat.message.model.PayBalanceModel;
import com.fbee.modules.wechat.message.model.PayDepositModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class CallbackServiceImpl implements CallbackService {

    private static final Logger log = LoggerFactory.getLogger("callbackLogger");

    @Autowired
    TradeRecordsMapper tradeRecordsDao; // 支付总表

    @Autowired
    OrdersMapper ordersDao; // 订单表

    @Autowired
    TenantsFinanceRecordsMapper tenantsFinanceRecordsDao; // 租户交易流水表

    @Autowired
    TenantsFundsMapper tenantsFundsDao; // 租户资金表

    @Autowired
    TenantsTradeRecordsMapper tenantsTradeRecordsDao; // 租户余额表

    @Autowired
    OrderCustomersInfoMapper orderCustomersInfoMapper;

    @Autowired
    CommonService commonService;

    @Autowired
    TenantsFlowMapper tenantsFlowDao; // 存储租户充值时支付流水和租户id对应关系

    @Autowired
    TenantsAppsMapper tenantsAppsMapper;

    @Autowired
    TenantsJobsMapper tenantsJobsMapper;

    @Autowired
    TenantsJobResumesMapper tenantsJobResumesMapper;

    @Autowired
    PayInfoMapper payInfoMapper;

    PayRecordesOpt payRecordesOpt = new PayRecordesOpt();

    SmsServiceImpl SmsServiceImpl = new SmsServiceImpl();

    private JedisTemplate mq = JedisUtils.getJedisMessage();

    private static ConcurrentHashMap<String, AtomicBoolean> process = new ConcurrentHashMap<String, AtomicBoolean>();

    /**
     * 处理业务
     */
    @Override
    public Object handleService(HttpServletRequest req) throws Exception {
        SwiftpassCallbackInfo sp = null;
        log.info("收到通知...");
        try {
            req.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("req.setCharacterEncoding error." + e.getMessage());
        }
        String resString = XmlUtils.parseRequst(req);
        //String resString = "<xml><bank_type><![CDATA[CFT]]></bank_type><charset><![CDATA[UTF-8]]></charset><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[N]]></is_subscribe><mch_id><![CDATA[102520153627]]></mch_id><nonce_str><![CDATA[1511779083890]]></nonce_str><openid><![CDATA[oMJGHsyCnz0_awlD3PUlyFM-wTHI]]></openid><out_trade_no><![CDATA[062017112700014]]></out_trade_no><out_transaction_id><![CDATA[4200000021201711277442950741]]></out_transaction_id><pay_result><![CDATA[0]]></pay_result><result_code><![CDATA[0]]></result_code><sign><![CDATA[90E3FA4ADE3F02A2C7740A5796364A76]]></sign><sign_type><![CDATA[MD5]]></sign_type><status><![CDATA[0]]></status><sub_appid><![CDATA[wx3ba7ee60b26f2955]]></sub_appid><sub_is_subscribe><![CDATA[Y]]></sub_is_subscribe><sub_openid><![CDATA[ocj2FwTNvLmTYfOMQrtGSuT7NtBE]]></sub_openid><time_end><![CDATA[20171127183803]]></time_end><total_fee><![CDATA[1]]></total_fee><trade_type><![CDATA[pay.weixin.native]]></trade_type><transaction_id><![CDATA[102520153627201711277115686448]]></transaction_id><version><![CDATA[2.0]]></version></xml>";
        log.info("通知内容：" + resString);

        String respString = "error";
        if (resString == null || "".equals(resString)) {
            log.error("返回报文为空");
            return "fail";
        }
        return process(resString);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String process(String resString) throws Exception {
        Map<String, String> map = null;
        try {
            map = XmlUtils.toMap(resString.getBytes(), "utf-8");
        } catch (Exception e) {
            log.error("XmlUtils.toMap error." + e.getMessage());
        }
        String res = JsonUtils.toJson(map);
        SwiftpassCallbackInfo sp = JsonUtils.fromJson(res, SwiftpassCallbackInfo.class);
        if (StringUtils.isBlank(sp.getSign())) {
            log.error("返回报文没有签名信息");
            return "fail";
        }
        if (!sp.getSignType().equalsIgnoreCase("fix") && !SignUtils.checkParam(map, SwiftpassConfig.key)) {
            log.error("验证签名不通过");
            return "fail";
        }
        String status = map.get("status");
        if (status == null || !"0".equals(status)) {
            log.error(String.format("响应码错误[%s]", status));
            return "fail";
        }
        String result_code = map.get("result_code");
        if (result_code == null || !"0".equals(result_code)) {
            log.error(String.format("返回代码[%s]", result_code));
            return "fail";
        }
        sp.setResultXml(resString);

        if (!process.containsKey(sp.getOutTradeNo())) {
            process.put(sp.getOutTradeNo(), new AtomicBoolean());
        }

        try {
            //此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。
            //如果是false，则准备处理。true表示正在处理，跳过。
            if (process.get(sp.getOutTradeNo()).compareAndSet(false, true)) {
                log.info(String.format("====== callback process begin, pay_no=[%s]", sp.getOutTradeNo()));

                Map<String, Object> xmlDataMap = JsonUtils.fromJson(JsonUtils.toJson(sp), Map.class);
                // 业务结果
                String payresult = sp.getPayResult();
                String flowNo = sp.getOutTradeNo();

                PayInfo payInfo = payInfoMapper.getById(flowNo);

                if (payInfo == null) {
                    log.info(String.format("[%s]没有找到该条支付信息[payInfo] : %s ", "payinfo not exist", flowNo));
                    process.remove(sp.getOutTradeNo());
                    return "fail";
                }
                if (StringUtils.isNotBlank(payInfo.getCallbackInfo())) {
                    log.info(String.format("[%s]该支付请求已经处理，不可重复处理。[%s]", "duplication callback, it already processed", flowNo));
                    process.remove(sp.getOutTradeNo());
                    return "fail";
                }
                log.info(String.format("修改订单及支付单状态,order_no[%s],pay_No[%s]", payInfo.getOrderNo(), payInfo.getPayNo()));
                payInfo.setPayNo(flowNo);
                payInfo.setPayStatus(OrderConst.PayStatus.PAID.getCode());
                payInfo.setCallbackInfo(sp.getResultXml());
                payInfo.setCallbackTime(new Date());
                payInfo.setResultCode(sp.getResultCode());
                payInfo.setErrCode(sp.getErrCode());
                payInfo.setErrMsg(sp.getErrMsg());
                payInfoMapper.update(payInfo);

                log.info(String.format("更新支付流水状态 [ tenants_flow ] = [ %s ]", flowNo));
                TenantsFlow payresultRecord = new TenantsFlow();
                payresultRecord.setTradeFlowNo(flowNo);
                payresultRecord.setPayresult(payresult);
                tenantsFlowDao.updateByPrimaryKeySelective(payresultRecord);// 标记支付结果,用于界面上判断是否支付成功

                TenantsFlow tenantsFlow = tenantsFlowDao.selectByPrimaryKey(flowNo);// 对应关系
                String orderNo = tenantsFlow.getOrderNo();
                log.info(String.format("获取订单编号：[ %s ]", orderNo));

                if (payresult.equals("0")) {
                    //支付成功
                    try {
                        String result = null;
                        if (StringUtils.isNotBlank(orderNo)) {
                            // 订单支付,对应关系表中订单号不为空的就是订单支付
                            log.info("------  订单支付  -------");
                            result = pay(sp, tenantsFlow, xmlDataMap);
                        } else {
                            // 充值
                            log.info("------  充值  -------");
                            result = recharge(sp, tenantsFlow, xmlDataMap);
                        }
                        //7. 处理完成，返回成功
                        log.info(String.format("支付回调处理完成，pay_no [%s], result [%s]", sp.getOutTradeNo(), result));
                        process.remove(sp.getOutTradeNo());
                        return result;

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else { // 支付失败
                    log.info(String.format("支付回调>业务结果失败. pay_no [%s]", flowNo));
                    if (StringUtils.isNotBlank(orderNo)) { // 订单支付时失败
                        TradeRecords tradeRecords = payRecordesOpt.buildOrderFailTradeRecords(xmlDataMap, flowNo, orderNo);
                        tradeRecordsDao.insertSelective(tradeRecords);// 插入支付总表交易记录
                        log.info("fail: insert selective[pay]");
                    } else { // 充值时失败
                        TradeRecords tradeRecords = payRecordesOpt.buildFailTradeRecords(xmlDataMap, flowNo);
                        tradeRecordsDao.insertSelective(tradeRecords);// 插入支付总表交易记录
                        log.info("fail: insert selective[recharge]");
                    }
                }
            }
        } catch (Exception e) {
            log.info(String.format("exception : ", e.getMessage()));
            process.remove(sp.getOutTradeNo());
            throw e;
        }
        log.info("======== call back finished =======");
        return "fail";
    }

    public String pay(SwiftpassCallbackInfo sp, TenantsFlow tenantsFlow, Map<String, Object> xmlDataMap) throws ParseException {
        String flowNo = sp.getOutTradeNo();
        String orderNo = tenantsFlow.getOrderNo();

        Map<String, Object> orderMap = ordersDao.getOrder(orderNo);// 订单详情
        String orderStatus = (String) orderMap.get("ORDER_STATUS");// 订单状态,用于判断支付定金还是尾款
        if (OrderConst.OrderStatus.CANCELED.getCode().equals(orderStatus)) {
            log.info(String.format("该订单已取消[%s]：[%s]", orderStatus, orderMap.get("CANCLE_REASON")));
            return "fail";
        }
        if (!OrderConst.OrderStatus.WAIT_PAY_DEPOSIT.getCode().equals(orderStatus)
                && !OrderConst.OrderStatus.WAIT_PAY_BALANCE.getCode().equals(orderStatus)) {
            log.info(String.format("该订单不是支付状态[01,03]: [%s]", orderStatus));
            return "fail";
        }
        orderMap.put("SHARE_ORDER_STATUS", (String) orderMap.get("ORDER_STATUS"));
        TradeRecords tradeRecords = payRecordesOpt.buildTradeRecords(JsonUtils.fromJson(JsonUtils.toJson(sp), Map.class), flowNo, orderNo, orderStatus);
        log.info(String.format(" 插入支付总表：trade_records ...order_no [%s]", orderNo));
        tradeRecordsDao.insertSelective(tradeRecords);// 插入支付总表交易记录

        String inOutNo = commonService.createOrderNo("08");// 生成交易流水号
        TenantsFinanceRecords record = payRecordesOpt.buildTenantsFinanceRecords(JsonUtils.fromJson(JsonUtils.toJson(sp), Map.class), orderMap, flowNo, inOutNo);
        //订单支付，插入收支对象  客户
        log.info(String.format(" 插入收支对象：order_customers ...order_no [%s], inOutNo[%s]", orderNo, inOutNo));
        OrderCustomersInfo members = orderCustomersInfoMapper.selectByPrimaryKey((String) orderMap.get("ORDER_NO"));

        log.info(String.format("订单状态[%s], 01支付定金，03支付尾款", orderStatus));
        if (null != orderStatus && orderStatus.equals("03")) {
            log.info(String.format("订单状态[%s], 支付尾款[%s]]", orderStatus, orderNo));
            BigDecimal service_charge_fee = new BigDecimal(0);
            if (null != orderMap.get("SERVICE_CHARGE")) {
                service_charge_fee = (BigDecimal) orderMap.get("SERVICE_CHARGE");
            }
            BigDecimal amount_fee = new BigDecimal(0);
            if (null != orderMap.get("AMOUNT")) {
                amount_fee = (BigDecimal) orderMap.get("AMOUNT");
            }

            BigDecimal orderDeposit_fee = new BigDecimal(0);
            if (null != orderMap.get("ORDER_DEPOSIT")) {
                orderDeposit_fee = (BigDecimal) orderMap.get("ORDER_DEPOSIT");
            }
            log.info(String.format("订单[%s]金额：订单金额[%s], 服务费[%s], 定金[%s]", orderNo, amount_fee, service_charge_fee, orderDeposit_fee));

            String inOutNowj = commonService.createOrderNo("08");// 生成交易流水号
            TenantsFinanceRecords recordwj = payRecordesOpt.buildTenantsFinanceRecords(xmlDataMap, orderMap, flowNo, inOutNowj);
            recordwj.setRemarks("尾款");
            recordwj.setInOutAmount(amount_fee.subtract(orderDeposit_fee));//收支金额
            recordwj.setInOutObject(members.getMemberName());//收支对象
            recordwj.setDraweeId((Integer) orderMap.get("MEMBER_ID"));//付款方id
            recordwj.setDraweeType("01");//付款方类型01客户02家政机构03业务员04家政员05平台
            recordwj.setPayeeId((Integer) orderMap.get("TENANT_ID"));//收款方id
            recordwj.setPayeeType("02");//收款方类型01客户02家政机构03业务员04家政员05平台
            recordwj.setRelatedTrans(orderNo);//关联流水号：订单编号orderNo
            tenantsFinanceRecordsDao.insertSelective(recordwj);// 租户交易总表

            log.info(String.format("添加租户交易总表 tenants_finance_records[订单支付01], order[%s], amount[%s]", orderNo, recordwj.getInOutAmount()));

            record.setRemarks("服务费");
            record.setInOutAmount(service_charge_fee);//收支金额
            record.setInOutObject(members.getMemberName());
            record.setPayType("11");//交易类型 01 订单支付 02成单加价 03成单奖励 04账户充值 05账户提现 06会员续费 07报名费 08住宿费 09佣金费10取消订单退定金11:服务费
            record.setDraweeId((Integer) orderMap.get("MEMBER_ID"));//付款方id
            record.setDraweeType("01");//付款方类型01客户02家政机构03业务员04家政员05平台
            record.setPayeeId((Integer) orderMap.get("TENANT_ID"));//收款方id
            record.setPayeeType("02");//收款方类型01客户02家政机构03业务员04家政员05平台
            record.setRelatedTrans(orderNo);//关联流水号：订单编号orderNo
            tenantsFinanceRecordsDao.insertSelective(record);// 租户交易总表

            log.info(String.format("添加租户交易总表 tenants_finance_records[服务费11], order[%s], amount[%s]", orderNo, recordwj.getInOutAmount()));


        } else {
            log.info(String.format("订单状态[%s], 支付定金[%s]]", orderStatus, orderNo));
            record.setInOutObject(members.getMemberName());//收支对象
            record.setRelatedTrans(orderNo);//关联流水号：订单编号orderNo
            tenantsFinanceRecordsDao.insertSelective(record);// 租户交易总表
            log.info(String.format("添加租户交易总表 tenants_finance_records, order[%s]", orderNo));
        }

        log.info(String.format("[bulidTenantsTradeRecords] ordersStatus[%s]", (String) orderMap.get("SHARE_ORDER_STATUS")));
        String tradeNo = commonService.createOrderNo("07");// 生成账户流水号
        TenantsTradeRecords tenantsTradeRecords = payRecordesOpt.bulidTenantsTradeRecords(xmlDataMap, orderMap, tradeNo, inOutNo, orderNo);
        tenantsTradeRecordsDao.insertSelective(tenantsTradeRecords);// 插入租户资金变动轨迹表

        log.info(String.format("添加租户资金变动表[tenants_trade_records] order[%s], tradeNo[%s]", orderNo, tradeNo));

        Map<String, Object> orderparamMap = new HashMap<String, Object>();
        orderparamMap.put("outTradeNo", orderNo);
        orderparamMap.put("depositDate", new Date());
        orderparamMap.put("orderStatus", orderStatus);
        BigDecimal total_fee = new BigDecimal((String) xmlDataMap.get("total_fee"));// 金额
        BigDecimal danwei = new BigDecimal("0.01");
        if (orderStatus.equals("01")) {// 待支付定金订单
            log.info(String.format("待支付定金dealAccount：order[%s]", orderNo));

            dealAccount((Integer) orderMap.get("TENANT_ID"), "01", "06", "01", total_fee.multiply(danwei), orderNo, "[支付]支付定金");// 更新分享方账户资金总表(定金)

            OrderCustomersInfo orderCustomer = orderCustomersInfoMapper.selectByPrimaryKey(orderNo);
            String companyName = tenantsAppsMapper.getWebsiteName((Integer) orderMap.get("TENANT_ID"));
            log.info("companyName:" + companyName);

            String serviceName = DictionarysCacheUtils.getServiceTypeName((String) orderMap.get("SERVICE_ITEM_CODE"));
            log.info("serviceName:" + serviceName);

            if (ordersDao.getOrder(orderNo) != null) { // 判断表中是否有记录
                log.info(String.format("更改订单状态 [%s]", JsonUtils.toJson(orderparamMap)));
                ordersDao.updateStatus(orderparamMap); // 更改订单状态

                TenantsJobs job = tenantsJobsMapper.getJobByOrderNo(orderNo);
                if (job != null) {
                    TenantsJobs updateJob = new TenantsJobs();
                    updateJob.setId(job.getId());
                    updateJob.setOrderStatus("02");
                    tenantsJobsMapper.update(updateJob);
                }

                //支付订金提醒
                mq.lpush(WechatConfig.Queue.ORDER_PAY_DEPOSIT_B.getQueue(), orderNo);
                mq.publish(WechatConfig.Channel.ORDER_PAY_DEPOSIT.getChannel(), orderNo);

                //定金支付 发送短信
                try{
                    SmsServiceImpl.sendPaySuccessDing(orderCustomer.getMemberMobile(), companyName, serviceName);
                }catch (Exception e){
                    log.info("send sms"+e.getMessage());
                }

                return "success";
            } else {
                log.info(String.format("没有此订单[%s]", orderNo));
            }
            return "fail";
        } else if (orderStatus.equals("03")) { // 待支付尾款
            log.info(String.format("尾款支付 dealAccount：order[%s]", orderNo));
            JsonResult jr = dealAccount((Integer) orderMap.get("TENANT_ID"), "01", "07", "02", total_fee.multiply(danwei), orderNo, "[支付]支付尾款");
            log.info(String.format("尾款支付 dealAccount result: [%s]", JsonUtils.toJson(jr)));

            log.info(String.format("定金解冻 dealAccount：order[%s]", orderNo));
            Map<String, Object> tradeRecord = tradeRecordsDao.selectByOrder(orderNo);
            if (tradeRecord != null) {
                log.info(String.format("定金解冻 > 查询订单 : [%s]", JsonUtils.toJson(tradeRecord)));
                jr = dealAccount((Integer) orderMap.get("TENANT_ID"), "01", "04", "", (BigDecimal) tradeRecord.get("TRADE_AMOUNT"), orderNo, "[支付]定金解冻");
                log.info(String.format("定金解冻 dealAccount result: [%s]", JsonUtils.toJson(jr)));
            } else {
                log.info(String.format("若为门店助手中创建的订单，则不用支付定金，定金记录为空 dealAccount result: [%s]", JsonUtils.toJson(jr)));
            }
            OrderCustomersInfo orderCustomer = orderCustomersInfoMapper.selectByPrimaryKey(orderNo);
            String companyName = tenantsAppsMapper.getWebsiteName((Integer) orderMap.get("TENANT_ID"));
            String serviceName = DictionarysCacheUtils.getServiceTypeName((String) orderMap.get("SERVICE_ITEM_CODE"));
            String serviceStartTime = DateUtils.formatDate(orderCustomer.getServiceStart());

            Map<String, Object> existOrder = ordersDao.getOrder(orderNo);
            if (existOrder == null) { // 判断表中是否有记录
                log.info(String.format("没有此订单[%s]", orderNo));
                return "fail";
            }
            log.info(String.format("更改订单状态 - [%s]", JsonUtils.toJson(orderparamMap)));
            ordersDao.updateStatus(orderparamMap); // 更改订单状态

            //修改职位发布信息
            updateJobResume(existOrder.get("ORDER_NO").toString(), "04", existOrder.get("STAFF_ID")==null?"":existOrder.get("STAFF_ID").toString());


            //支付尾款提醒
            mq.lpush(WechatConfig.Queue.ORDER_PAY_BALANCE_B.getQueue(), orderNo);
            mq.lpush(WechatConfig.Queue.ORDER_PAY_BALANCE_C.getQueue(), orderNo);
            mq.publish(WechatConfig.Channel.ORDER_PAY_BALANCE.getChannel(), orderNo);
            mq.publish(WechatConfig.Channel.FINISH_JOB.getChannel(), orderNo);

            try{
                SmsServiceImpl.sendPaymentSuccess(orderCustomer.getMemberMobile(), companyName, serviceName, serviceStartTime);
            }catch (Exception e){
                log.info("send sms"+e.getMessage());
            }
            log.info(String.format("尾款发短信end - order:[%s]", orderCustomer.getOrderNo()));
            return "success";
        }
        return "fail";
    }

    public String recharge(SwiftpassCallbackInfo sp, TenantsFlow tenantsFlow, Map<String, Object> xmlDataMap) throws ParseException {

        String flowNo = tenantsFlow.getTradeFlowNo();

        Integer tenantId = tenantsFlow.getTenantId();// 租户id
        TradeRecords tradeRecords = payRecordesOpt.buildRechargeTradeRecords(xmlDataMap, flowNo);
        tradeRecordsDao.insertSelective(tradeRecords);// 插入支付总表交易记录

        String inOutNo = commonService.createOrderNo(Constants.CW);//财务流水号08
        TenantsFinanceRecords tenantsFinanceRecords = payRecordesOpt.buildRechargeTenantsFinanceRecords(xmlDataMap, flowNo, inOutNo, tenantId);
        //tenantsFinanceRecords.setInOutObject(inoutobject_userName);

        //充值，插入收支对象  业务员
        TenantsApps tenantApp = tenantsAppsMapper.selectByPrimaryKey(tenantId);
        tenantsFinanceRecords.setInOutObject(tenantApp.getWebsiteName());

        tenantsFinanceRecordsDao.insertSelective(tenantsFinanceRecords);// 插入租户交易总表

        String tradeNo = commonService.createOrderNo(Constants.ZH);// 生成账户流水号 07
        TenantsTradeRecords tenantsTradeRecords = payRecordesOpt.bulidRechargeTenantsTradeRecords(xmlDataMap, tradeNo, inOutNo, tenantId);
        tenantsTradeRecordsDao.insertSelective(tenantsTradeRecords);// 插入租户资金变动轨迹表

        BigDecimal total_fee = new BigDecimal((String) xmlDataMap.get("total_fee"));
        BigDecimal danwei = new BigDecimal("0.01");
        dealAccount(tenantId, "01", "01", "02", total_fee.multiply(danwei), "0", "账户充值");// 更新租户账户资金总表(账户充值)
        return "";
    }


    /**
     * 更新租户账户资金表
     *
     * @param tenantId
     * @param payType
     * @param bussType
     * @param state
     * @param money
     * @return
     */
    public JsonResult dealAccount(Integer tenantId, String payType, String bussType, String state, BigDecimal money, String orderNo, String remarks) {
        try {
            log.info(String.format("dealAccount: 查询租户租金 tenants_funds, tenantId:[%s], payType[%s], bussType[%s], state[%s], money[%s]", tenantId, payType, bussType, state, money));
            // 查询表中是否有该租户资金
            TenantsFunds tenantsFunds = tenantsFundsDao.selectByPrimaryKey(tenantId);
            // 如果没有创建租户资金
//            if (tenantsFunds == null) {
//                log.info("new  [tenantsFunds]");
//                TenantsFundsEntity tenantsFundsEntity = new TenantsFundsEntity();
//                BigDecimal m = BigDecimal.valueOf(0.00);
//                tenantsFundsEntity.setAvailableAmount(m);
//                tenantsFundsEntity.setFrozenAmount(m);
//                tenantsFundsEntity.setGrandTotalAmount(m);
//                tenantsFundsEntity.setTotalAmount(m);
//                tenantsFundsEntity.setTenantId(tenantId);
//                tenantsFundsEntity.setAddTime(new Date());
//                tenantsFundsEntity.setIsUsable("1");
//                if (payType.equals("01")) {// 收入
//                    if (bussType.equals("04")) {// 解冻
//                        log.info("租户为新建，无金额需要解冻");
//                        return JsonResult.failure(ResultCode.Funds.FROZEN_AMOUNT_NOT_ENOUGH);
//                    } else {// 其他收入
//                        log.info(String.format("新建租户：其他收入， state[%s]", state));
//                        if (state.equals("01")) {// 处理中
//                            log.info(String.format("新建租户[处理中]：冻结金额[0] and 总金额[0] add [%s]", money));
//                            // 冻结+
//                            tenantsFundsEntity.setFrozenAmount(m.add(money));
//                            // 租户总额+
//                            tenantsFundsEntity.setTotalAmount(m.add(money));
//                            tenantsFundsDao.insert(tenantsFundsEntity);
//                        }
//                        if (state.equals("02")) {// 已处理
//                            log.info(String.format("新建租户[已处理]：可用金额[0] and 总金额[0] add [%s]", money));
//                            // 可用+
//                            tenantsFundsEntity.setAvailableAmount(m.add(money));
//                            // 租户总额+
//                            tenantsFundsEntity.setTotalAmount(m.add(money));
//                            tenantsFundsDao.insert(tenantsFundsEntity);
//                        }
//                    }
//                }
//                if (payType.equals("02")) {// 支出
//                    log.info("新建租户[支出]：无金额支出交易");
//                    return JsonResult.failure(ResultCode.Funds.AVAILABLE_AMOUNT_NOT_ENOUGH);
//                }
//                return JsonResult.success(null);
//            }
            if (payType.equals("01")) {// 收入
                log.info(String.format("租户[%s]: payType[01]收入", tenantId));
                if (bussType.equals("04")) {// 解冻
                    log.info(String.format("租户[%s]: payType[01]收入 > bussType[04]解冻", tenantId));
                    log.info(String.format("租户[%s] > [解冻]: 冻结[%s]-， 可用[%s]+ [%s]", tenantId, tenantsFunds.getFrozenAmount(), tenantsFunds.getAvailableAmount(), money));
                    // 冻结-
                    tenantsFunds.setFrozenAmount(tenantsFunds.getFrozenAmount().subtract(money));
                    // 可用+
                    tenantsFunds.setAvailableAmount(tenantsFunds.getAvailableAmount().add(money));
                    tenantsFunds.setTenantId(tenantId);
                    tenantsFunds.setModifyTime(new Date());
                    tenantsFundsDao.updateByPrimaryKeySelective(tenantsFunds);
                } else {// 其他收入
                    log.info(String.format("租户[%s]: payType[01]收入 --> [%s]其他收入", tenantId, bussType));
                    if (state.equals("01")) {// 处理中
                        log.info(String.format("租户[%s] > state[01]处理中: 冻结[%s]+， 总额[%s]+ [%s]", tenantId, tenantsFunds.getFrozenAmount(), tenantsFunds.getTotalAmount(), money));
                        // 冻结+
                        tenantsFunds.setFrozenAmount(tenantsFunds.getFrozenAmount().add(money));
                        // 租户总额+
                        tenantsFunds.setTotalAmount(tenantsFunds.getTotalAmount().add(money));
                        tenantsFunds.setTenantId(tenantId);
                        tenantsFunds.setModifyTime(new Date());
                        tenantsFundsDao.updateByPrimaryKeySelective(tenantsFunds);
                    }
                    if (state.equals("02")) {// 已处理
                        log.info(String.format("租户[%s] > state[03]已处理: 可用[%s]+， 总额[%s]+ [%s]", tenantId, tenantsFunds.getAvailableAmount(), tenantsFunds.getTotalAmount(), money));
                        // 可用+
                        tenantsFunds.setAvailableAmount(tenantsFunds.getAvailableAmount().add(money));
                        // 租户总额+
                        tenantsFunds.setTotalAmount(tenantsFunds.getTotalAmount().add(money));
                        tenantsFunds.setTenantId(tenantId);
                        tenantsFunds.setModifyTime(new Date());
                        tenantsFundsDao.updateByPrimaryKeySelective(tenantsFunds);
                    }
                }
            }
            if (payType.equals("02")) {// 支出
                log.info(String.format("租户[%s]: payType[02]支出", tenantId));
                if (bussType.equals("03")) {// 冻结
                    log.info(String.format("租户[%s]: payType[01]收入 > bussType[03]解冻", tenantId));
                    log.info(String.format("租户[%s] > [解冻]: 冻结[%s]+， 可用[%s]- [%s]", tenantId, tenantsFunds.getFrozenAmount(), tenantsFunds.getAvailableAmount(), money));
                    // 冻结+
                    tenantsFunds.setFrozenAmount(tenantsFunds.getFrozenAmount().add(money));
                    // 可用-
                    tenantsFunds.setAvailableAmount(tenantsFunds.getAvailableAmount().subtract(money));
                    tenantsFunds.setTenantId(tenantId);
                    tenantsFunds.setModifyTime(new Date());
                    tenantsFundsDao.updateByPrimaryKeySelective(tenantsFunds);
                } else {// 其他收支出
                    log.info(String.format("租户[%s]: payType[02]支出 --> [%s]其他支出", tenantId, bussType));
                    if (state.equals("01")) {// 处理中
                        // 余额不足
                        if (tenantsFunds.getAvailableAmount().compareTo(money) == -1) {
                            log.info(String.format("租户[%s]: 余额不足[%s]", tenantId, tenantsFunds.getAvailableAmount()));
                            return JsonResult.failure(ResultCode.Funds.AVAILABLE_AMOUNT_NOT_ENOUGH);
                        }
                        log.info(String.format("租户[%s] > state[01]处理中: 冻结[%s]-， 总额[%s]- [%s]", tenantId, tenantsFunds.getFrozenAmount(), tenantsFunds.getTotalAmount(), money));
                        // 冻结-
                        tenantsFunds.setFrozenAmount(tenantsFunds.getFrozenAmount().subtract(money));
                        // 租户总额-
                        tenantsFunds.setTotalAmount(tenantsFunds.getTotalAmount().subtract(money));
                        tenantsFunds.setTenantId(tenantId);
                        tenantsFunds.setModifyTime(new Date());
                        tenantsFundsDao.updateByPrimaryKeySelective(tenantsFunds);
                    }
                    if (state.equals("02")) {// 已处理
                        // 余额不足
                        if (tenantsFunds.getAvailableAmount().compareTo(money) == -1) {
                            log.info(String.format("租户[%s]: 余额不足[%s]", tenantId, tenantsFunds.getAvailableAmount()));
                            return JsonResult.failure(ResultCode.Funds.AVAILABLE_AMOUNT_NOT_ENOUGH);
                        }
                        log.info(String.format("租户[%s] > state[02]已处理: 可用[%s]-， 总额[%s]- [%s]", tenantId, tenantsFunds.getAvailableAmount(), tenantsFunds.getTotalAmount(), money));
                        // 可用-
                        tenantsFunds.setAvailableAmount(tenantsFunds.getAvailableAmount().subtract(money));
                        // 租户总额-
                        tenantsFunds.setTotalAmount(tenantsFunds.getTotalAmount().subtract(money));
                        tenantsFunds.setTenantId(tenantId);
                        tenantsFunds.setModifyTime(new Date());
                        tenantsFundsDao.updateByPrimaryKeySelective(tenantsFunds);
                    }
                }
            }
            return JsonResult.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ErrorMsg.ORDERS_QUERY_ERROR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }


    /**
     * 修改职位信息
     */
    private void updateJobResume(String orderNo, String orderStatus, String staffIdString) {
        /**
         * 支付尾款，修改job状态，修改resume状态，取消冻结金额
         */
        Orders order = ordersDao.selectByPrimaryKey(orderNo);
        TenantsJobs job = tenantsJobsMapper.getJobByOrderNo(orderNo);
        if (job == null || order == null) {
            return;
        }
        TenantsJobs updateJob = new TenantsJobs();
        updateJob.setId(job.getId());
        updateJob.setOrderStatus(orderStatus);
        updateJob.setStatus(JobConst.JobStatus.OFF.getCode());
        tenantsJobsMapper.update(updateJob);

        if (!JobConst.JobStatus.OFF.getCode().equals(updateJob.getStatus())) {
            return;
        }
        //解冻保证金
        if (job.getDeposit() != null && job.getDeposit().compareTo(new BigDecimal(0)) > 0) {
            commonService.thawAmount(job.getTenantId(), job.getDeposit(), job.getOrderNo(), "解冻保证金[支付完成]");
        }

        /**
         * 应聘信息更新
         * 更新应聘表状态
         * 解冻保证金
         */
        if (StringUtils.isBlank(staffIdString)) {
            return;
        }
        Integer staffId = Integer.valueOf(staffIdString);
        TenantJobResumeForm pm = new TenantJobResumeForm();
        pm.setJobId(job.getId());
        pm.setRowNum(100);
        List<TenantsJobResume> resumes = tenantsJobResumesMapper.getMyResumesBoxList(pm);
        if (resumes == null || resumes.size() < 1) {
            return;
        }
        for (TenantsJobResume resume : resumes) {
            if(resume.getStatus().equals(JobConst.ResumeStatus.REJECTS.getCode())){
                continue;
            }
            TenantsJobResume update = new TenantsJobResume();
            String remarks = null;
            if (resume.getResumeTenantStaffId().equals(staffId) && Integer.valueOf(0).equals(order.getIsLocalStaff())) {
                //简历已完成
                update.setJobId(resume.getJobId());
                update.setId(resume.getId());
                update.setStatus(JobConst.ResumeStatus.FINISHED.getCode());
                remarks = "保证金解冻[招聘支付尾款完成]";
            } else {
                //其他简历已取消
                update.setJobId(resume.getJobId());
                update.setId(resume.getId());
                update.setStatus(JobConst.ResumeStatus.CANCELED.getCode());
                update.setRemarks("用户已选择其他阿姨");
                remarks = "保证金解冻[用户已选择其他阿姨]";
            }
            tenantsJobResumesMapper.update(update);
            //解冻保证金
            if (resume.getDeposit()!=null &&resume.getDeposit().compareTo(new BigDecimal(0)) > 0) {
                commonService.thawAmount(resume.getResumeTenantId(), resume.getDeposit(), resume.getOrderNo(), remarks);
            }
        }
    }

}
