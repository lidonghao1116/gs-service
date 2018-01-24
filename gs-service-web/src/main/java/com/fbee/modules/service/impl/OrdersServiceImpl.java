package com.fbee.modules.service.impl;

import com.fbee.modules.bean.Constants;
import com.fbee.modules.bean.ErrorMsg;
import com.fbee.modules.consts.JobConst;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.config.Global;
import com.fbee.modules.core.page.Page;
import com.fbee.modules.core.page.form.TenantJobResumeForm;
import com.fbee.modules.core.utils.DateUtils;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.mybatis.dao.*;
import com.fbee.modules.mybatis.entity.OrderChangehsRecordsEntity;
import com.fbee.modules.mybatis.entity.OrdersEntity;
import com.fbee.modules.mybatis.entity.TenantsStaffJobInfoEntity;
import com.fbee.modules.mybatis.entity.TenantsTradeRecordsEntity;
import com.fbee.modules.mybatis.model.*;
import com.fbee.modules.operation.PayRecordesOpt;
import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.service.CommonService;
import com.fbee.modules.service.OrdersService;
import com.fbee.modules.service.basic.BaseService;
import com.fbee.modules.util.WebUtils;
import com.fbee.modules.utils.DictionarysCacheUtils;
import com.fbee.modules.wechat.message.config.WechatConfig;
import com.fbee.modules.wechat.message.model.CancelOrderModel;
import org.apache.http.client.methods.HttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

public class OrdersServiceImpl extends BaseService implements OrdersService {

    private final static Logger log = LoggerFactory.getLogger(OrdersServiceImpl.class);

    @Autowired
    OrdersMapper orderDao;

    @Autowired
    TenantsStaffsInfoMapper tenantsStaffsInfoDao;

    @Autowired
    TenantsStaffSerItemsMapper tenantsStaffSerItemsDao;

    @Autowired
    OrderCustomersInfoMapper orderCustomersInfoDao;

    @Autowired
    TenantsStaffJobInfoMapper tenantsStaffJobInfoDao;

    @Autowired
    TenantsAppsMapper TenantsAppsDao;

    @Autowired
    TenantsFinanceRecordsMapper tenantsFinanceRecordsMapper;

    @Autowired
    TenantsTradeRecordsMapper tenantsTradeRecordsMapper;

    @Autowired
    CommonService commonService;

    @Autowired
    TenantsFlowMapper tenantsFlowDao; // 存储租户充值时支付流水和租户id对应关系

    @Autowired
    OrderContractInfoMapper orderContractInfoDao;

    @Autowired
    OrderChangeStaffInfoMapper orderChangeStaffInfoDao;

    @Autowired
    DeliveryBoxInfoMapper deliveryBoxInfoMapper;

    @Autowired
    MembersInfoMapper membersInfoDao;

    @Autowired
    OrderChangehsRecordsMapper orderChangehsRecordsMapper;

    @Autowired
    TradeRecordsMapper tradeRecordsDao; // 支付总表
    @Autowired
    TradeRecordsMapper tradeRecordsMapper;

    @Autowired
    TenantsJobsMapper tenantsJobsMapper;

    @Autowired
    TenantsJobResumesMapper tenantsJobResumesMapper;

    private JedisTemplate mq = JedisUtils.getJedisMessage();


    /**
     * 订单列表
     */
    @Override
    @Deprecated
    public JsonResult getOrdersList(Integer memberid, String orderstatus, int pageNumber, int pageSize) {
        try {
            // 获取总条数
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("memberid", memberid);
            map.put("orderstatus", orderstatus);// orderstatus默认为05(显示全部)
            // 01(待支付) 02(待面试) 03(已完成)
            // 04(已取消)
            Integer totalCount = orderDao.getOrdersCount(map);
            // 分页实体
            Page page = new Page();
            page.setPage(pageNumber);
            page.setRowNum(pageSize);
            if (totalCount == null) {
                return JsonResult.success(page);
            }
            // 最大页数判断
            int pageM = maxPage(totalCount, page.getRowNum(), page.getPage());
            if (pageM > 0) {
                page.setPage(pageM);
            }
            map.put("pageNumber", page.getOffset());
            map.put("pageSize", page.getRowNum());
            if (totalCount > 0) {
                List<Orders> list = orderDao.getOrdersList(map);
                List<Map<String, Object>> resultlist = new ArrayList<Map<String, Object>>();
                for (Orders entity : list) {

                    Map<String, Object> resultmap = new HashMap<String, Object>();
                    resultmap.put("orderTime", entity.getOrderTime());// 下单时间
                    resultmap.put("OrderNo", entity.getOrderNo());// 订单流水号
                    resultmap.put("OrderStatus", entity.getOrderStatus());// 订单状态
                    resultmap.put("TenantId", entity.getTenantId());// 租户id
                    resultmap.put("Domain", this.TenantsAppsDao.getDomainByTenantId(String.valueOf(entity.getTenantId())));
                    resultmap.put("ServiceItemCode", entity.getServiceItemCode());// 服务工种code
                    resultmap.put("ServiceItemNAME",
                            DictionarysCacheUtils.getServiceTypeName(entity.getServiceItemCode()));// 服务工种中文
                    resultmap.put("OrderDeposit", entity.getOrderDeposit());// 定金
                    resultmap.put("IdepositOver", entity.getIdepositOver());// 定金是否支付
                    resultmap.put("OrderBalance", entity.getOrderBalance());// 尾款
                    resultmap.put("BalanceOver", entity.getBalanceOver());// 尾款是否支付
                    resultmap.put("orderAmount", entity.getAmount());// 服务价格
                    resultmap.put("totalPrice", entity.getTotalPrice()); //总额
                    Map<String, Object> customermap = orderCustomersInfoDao.orderDetails(entity.getOrderNo());// 订单客户信息
                    resultmap.put("remarks", customermap == null ? "" : customermap.get("REMARKS"));// 备注
                    resultmap.put("websiteName", TenantsAppsDao.getWebsiteName(entity.getTenantId()));// 站点名称
                    Integer StaffId = entity.getStaffId();// 员工id
                    if (StaffId != null) {
                        Map<String, Object> staffsInfomap = tenantsStaffsInfoDao.getStaffsInfo(StaffId);
                        resultmap.put("zodiac", staffsInfomap.get("zodiac"));// 属相code
                        resultmap.put("zodiac_NAME",
                                DictionarysCacheUtils.getZodiacName((String) staffsInfomap.get("zodiac")));// 属相中文
                        resultmap.put("nativePlace", staffsInfomap.get("nativePlace"));// 籍贯code
                        resultmap.put("nativePlace_NAME",
                                DictionarysCacheUtils.getNativePlaceName((String) staffsInfomap.get("nativePlace")));// 籍贯中文
                        resultmap.put("staffName", staffsInfomap.get("staffName"));// 阿姨姓名
                        resultmap.put("age", staffsInfomap.get("age")); // 年龄
                        String img = (String) staffsInfomap.get("HEAD_IMAGE");
                        if (StringUtils.isNotBlank(img) && !img.startsWith("http") && !img.startsWith("data")) {
                            resultmap.put("HEAD_IMAGE", Constants.IMAGE_URL + img);
                        } else {
                            resultmap.put("HEAD_IMAGE", img);
                        }
                        resultmap.put("sex", staffsInfomap.get("sex"));
                    }
                    resultlist.add(resultmap);
                }
                page.setRows(resultlist);
                page.setRecords(totalCount.longValue());
            }
            return JsonResult.success(page);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.ORDERS_QUERY_ERR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }

    /**
     * 订单列表
     */
    @Override
    public JsonResult getOrderWithStaffList(Integer memberid, String orderstatus, int pageNumber, int pageSize) {
        try {
            // 获取总条数
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("memberid", memberid);
            map.put("orderstatus", orderstatus);// orderstatus默认为05(显示全部)
            // 01(待支付) 02(待面试) 03(已完成)
            // 04(已取消)
            Integer totalCount = orderDao.getOrdersCount(map);
            // 分页实体
            Page page = new Page();
            page.setPage(pageNumber);
            page.setRowNum(pageSize);
            if (totalCount == null) {
                return JsonResult.success(page);
            }
            // 最大页数判断
            int pageM = maxPage(totalCount, page.getRowNum(), page.getPage());
            if (pageM > 0) {
                page.setPage(pageM);
            }
            map.put("pageNumber", page.getOffset());
            map.put("pageSize", page.getRowNum());
            if (totalCount > 0) {
                List<OrdersEntity> list = orderDao.getOrdersWithStaffList(map);
                for(OrdersEntity entity : list){
                	if (!StringUtils.isBlank(entity.getServiceItemCode())) {
                        entity.setServiceItemCodeValue(DictionarysCacheUtils.getServiceTypeName(entity.getServiceItemCode()));
                    }
                }
                page.setRows(list);
                page.setRecords(totalCount.longValue());
            }
            return JsonResult.success(page);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.ORDERS_QUERY_ERR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }

    /**
     * 订单详情
     */
    @Override
    @Deprecated
    public JsonResult orderDetails(String orderNo) {
        try {
            Map<String, Object> resultmap = new HashMap<String, Object>();
            Map<String, Object> orderMap = orderDao.getOrder(orderNo);

            Date date = new Date();// 当前时间
            String orderTime = (String) orderMap.get("ORDER_TIME");// 订单提交时间
            Date orderTime1 = DateUtils.parseDate(orderTime);
            long surplusTime = date.getTime() - orderTime1.getTime();// 剩余时间(毫秒值)
            resultmap.put("surplusTime", surplusTime);

            String serviceItemCode = (String) orderMap.get("SERVICE_ITEM_CODE");// 服务工种
            Integer staffSerItemId = (Integer) orderMap.get("STAFFF_SER_ITEM_ID");// 服务工种id
            resultmap.put("orderMap", orderMap);
            Map<String, Object> customermap = orderCustomersInfoDao.orderDetails(orderNo);
            if (customermap != null) {

                if (null != serviceItemCode) {
                    customermap.put("SERVICE_ITEM_CODE_NAME", DictionarysCacheUtils.getServiceTypeName(serviceItemCode));
                }

                if (StringUtils.isNotBlank((String) customermap.get("SALARY_SKILLS"))) {
                    customermap.put("SALARY_SKILLS_NAME",
                            DictionarysCacheUtils.getSkillsStr(serviceItemCode, (String) customermap.get("SALARY_SKILLS")));
                }
                if (StringUtils.isNotBlank((String) customermap.get("COOKING_REQIREMENTS"))) {
                    customermap.put("COOKING_REQIREMENTS_NAME",
                            DictionarysCacheUtils.getFeaturesStr("02", (String) customermap.get("COOKING_REQIREMENTS")));
                }
                if (StringUtils.isNotBlank((String) customermap.get("LANGUAGE_REQUIREMENTS"))) {
                    customermap.put("LANGUAGE_REQUIREMENTS_NAME",
                            DictionarysCacheUtils.getFeaturesStr("01", (String) customermap.get("LANGUAGE_REQUIREMENTS")));
                }
                if (StringUtils.isNotBlank((String) customermap.get("PERSONALITY_REQUIREMENTS"))) {
                    customermap.put("PERSONALITY_REQUIREMENTS_NAME", DictionarysCacheUtils.getFeaturesStr("03",
                            (String) customermap.get("PERSONALITY_REQUIREMENTS")));
                }
                if (StringUtils.isNotBlank((String) customermap.get("SERVICE_PROVICE"))) {
                    customermap.put("SERVICE_PROVICE_NAME",
                            DictionarysCacheUtils.getProvinceName((String) customermap.get("SERVICE_PROVICE")));// 省
                }

                if (StringUtils.isNotBlank((String) customermap.get("SERVICE_COUNTY"))) {
                    customermap.put("SERVICE_COUNTY_NAME",
                            DictionarysCacheUtils.getCountyName((String) customermap.get("SERVICE_COUNTY")));// 市
                }

                if (StringUtils.isNotBlank((String) customermap.get("SERVICE_CITY"))) {
                    customermap.put("SERVICE_CITY_NAME",
                            DictionarysCacheUtils.getCityName((String) customermap.get("SERVICE_CITY")));// 区
                }

                customermap.put("SERVICE_TYPE_NAME", "不限");// 服务类型
                if (StringUtils.isNotBlank((String) customermap.get("SERVICE_TYPE"))) {
                    customermap.put("SERVICE_TYPE_NAME", DictionarysCacheUtils.getServiceNatureStr(serviceItemCode,
                            (String) customermap.get("SERVICE_TYPE")));
                }


                if (null != customermap.get("OLDER_AGE_RANGE") && StringUtils.isNotBlank((String) customermap.get("OLDER_AGE_RANGE"))) {
                    DictionarysCacheUtils.getOldAge(customermap.get("OLDER_AGE_RANGE") + "");
                    customermap.put("OLDER_AGE_RANGE", (String) DictionarysCacheUtils.getOldAge(customermap.get("OLDER_AGE_RANGE") + ""));
                }

                if (StringUtils.isNotBlank((String) customermap.get("WAGE_REQUIREMENTS"))) {
                    String age = DictionarysCacheUtils.getAgeIntervalName((String) customermap.get("WAGE_REQUIREMENTS"));
                    String[] ages = age.split(",");
                    if (ages.length == 2) {
                        if (ages[0].equals("0")) {
                            customermap.put("WAGE_REQUIREMENTS_NAME", ages[1] + "岁以下");
                        } else {
                            customermap.put("WAGE_REQUIREMENTS_NAME", ages[0] + "-" + ages[1] + "岁");
                        }
                    } else if (ages.length == 1) {
                        customermap.put("WAGE_REQUIREMENTS_NAME", ages[0] + "岁以上");
                    }
                } else {
                    customermap.put("WAGE_REQUIREMENTS_NAME", "不限");// 年龄要求
                }
                if (StringUtils.isNotBlank((String) customermap.get("EXPERIENCE_REQUIREMENTS"))) {
                    String str = "";
                    str = DictionarysCacheUtils.getExperienceName((String) customermap.get("EXPERIENCE_REQUIREMENTS"));
                    if (StringUtils.isNotBlank(str)) {
                        String[] experiences = str.split(",");
                        if (experiences.length == 2) {
                            if (experiences[0].equals("0")) {
                                customermap.put("EXPERIENCE_REQUIREMENTS", experiences[1] + "年以下");
                            } else {
                                customermap.put("EXPERIENCE_REQUIREMENTS", experiences[0] + "-" + experiences[1] + "年");
                            }
                        } else if (experiences.length == 1) {
                            customermap.put("EXPERIENCE_REQUIREMENTS", experiences[0] + "年以上");
                        }
                    }
                } else {
                    customermap.put("EXPERIENCE_REQUIREMENTS", "不限");//服务经验要求
                }
            }
            customermap.put("salaryTypeValue", DictionarysCacheUtils.getServicePriceUnit((String) customermap.get("salary_type")));
            resultmap.put("customermap", customermap);
            String servicePrice = null;// 薪资范围
            if (orderMap.get("STAFF_ID") != null) {
                Map<String, Object> staffmap = tenantsStaffsInfoDao.reserveOrderDetails((Integer) orderMap.get("STAFF_ID"));// 阿姨基本信息
                TenantsStaffJobInfoEntity job = tenantsStaffJobInfoDao.getById((Integer) orderMap.get("STAFF_ID"));

                if (null != staffmap.get("MARITAL_STATUS") && !"".equals(staffmap.get("MARITAL_STATUS"))) {
                    staffmap.put("MARITAL_STATUS_NAME",
                            DictionarysCacheUtils.getMaritalStatus((String) staffmap.get("MARITAL_STATUS")));// 婚姻状况中文
                }

                if (null != staffmap.get("EDUCARION") && !"".equals(staffmap.get("EDUCARION"))) {
                    staffmap.put("EDUCARION_NAME",
                            DictionarysCacheUtils.getEducationName((String) staffmap.get("EDUCARION")));// 学历中文
                }

                if (null != staffmap.get("BLOOD_TYPE") && !"".equals(staffmap.get("BLOOD_TYPE"))) {
                    staffmap.put("BLOOD_TYPE_NAME",
                            DictionarysCacheUtils.getBloodType((String) staffmap.get("BLOOD_TYPE")));// 学历中文
                }


                if (null != staffmap.get("SEX") && !"".equals(staffmap.get("SEX"))) {
                    staffmap.put("SEX_NAME", DictionarysCacheUtils.getMemberSex((String) staffmap.get("SEX")));// 性别中文
                }


                if (null != staffmap.get("NATION") && !"".equals(staffmap.get("NATION"))) {
                    staffmap.put("NATION_NAME", DictionarysCacheUtils.getNationName((String) staffmap.get("NATION")));// 性别中文
                }


                if (null != staffmap.get("ZODIAC") && !"".equals(staffmap.get("ZODIAC"))) {
                    staffmap.put("ZODIAC_NAME", DictionarysCacheUtils.getZodiacName((String) staffmap.get("ZODIAC")));// 属相中文
                }


                if (null != staffmap.get("NATIVE_PLACE") && !"".equals(staffmap.get("NATIVE_PLACE"))) {
                    staffmap.put("NATIVE_PLACE_NAME",
                            DictionarysCacheUtils.getNativePlaceName((String) staffmap.get("NATIVE_PLACE")));// 籍贯中文
                }

                if (null != staffmap.get("CONSTELLATION") && !"".equals(staffmap.get("CONSTELLATION"))) {
                    staffmap.put("CONSTELLATION_NAME",
                            DictionarysCacheUtils.getConstellationName((String) staffmap.get("CONSTELLATION")));// 星座中文
                }

                if (null != staffmap.get("FERTILITY_SITUATION") && !"".equals(staffmap.get("FERTILITY_SITUATION"))) {
                    staffmap.put("FERTILITY_SITUATION_NAME",
                            DictionarysCacheUtils.getFertilityStatus((String) staffmap.get("FERTILITY_SITUATION")));// 生育状况中文
                }

                if (null != staffmap.get("WORK_STATUS") && !"".equals(staffmap.get("WORK_STATUS"))) {
                    staffmap.put("FERTILITY_SITUATION_NAME",
                            DictionarysCacheUtils.getFertilityStatus((String) staffmap.get("WORK_STATUS")));// 生育状况中文
                }

                if (null != staffmap.get("WORK_STATUS") && !"".equals(staffmap.get("WORK_STATUS"))) {
                    staffmap.put("WORK_STATUS_NAME",
                            DictionarysCacheUtils.getWorkStatusName((String) staffmap.get("WORK_STATUS")));// 工作状况中文
                }
                String img = (String) staffmap.get("HEAD_IMAGE");
                if (StringUtils.isNotBlank(img) && !img.startsWith("http") && !img.startsWith("data")) {
                    staffmap.put("HEAD_IMAGE", Constants.IMAGE_URL + img);
                } else {
                    staffmap.put("HEAD_IMAGE", img);
                }

                resultmap.put("staffmap", staffmap);

                Map<String, Object> parammap = new HashMap<String, Object>();
                parammap.put("staffid", (Integer) orderMap.get("STAFF_ID"));
                DeliveryBoxInfoExample example = new DeliveryBoxInfoExample();
                List<String> values = new ArrayList<String>();
                values.add("05");
                values.add("06");
                example.createCriteria().andOrderNoEqualTo(orderNo).andHandleResultIn(values);
                List<DeliveryBoxInfo> deliveryBoxInfoList = deliveryBoxInfoMapper.selectByExample(example);
                if (deliveryBoxInfoList.size() != 0) { //如果是已经分享出去的订单,查到非分享方id
                    parammap.put("tenantid", deliveryBoxInfoList.get(0).getTenantId());
                } else {
                    parammap.put("tenantid", (Integer) orderMap.get("TENANT_ID"));
                }
                parammap.put("serviceItemCode", serviceItemCode);
                //@zsq 阿姨服务工种id
                parammap.put("staffSerItemId", staffSerItemId);
                log.info("+++++++++++++++++" + staffSerItemId);
                Map<String, Object> itemsmap = tenantsStaffSerItemsDao.reserveOrderDetail(parammap);// 阿姨服务信息

                if (itemsmap != null) {
                    if (StringUtils.isNotBlank(job.getExperience())) {
                        itemsmap.put("EXPERIENCE", job.getExperience());// 服务经验
                        itemsmap.put("EXPERIENCE_NAME", DictionarysCacheUtils.getExperienceName(job.getExperience()));// 服务经验
                    }

                    itemsmap.put("add_time", DateUtils.dateToStr((Date) itemsmap.get("add_time"), "yyyy-MM-dd HH:mm:ss"));// 添加时间

                    if (null != itemsmap.get("SKILLS") && !"".equals(itemsmap.get("SKILLS"))) {
                        itemsmap.put("SKILLS_NAME", DictionarysCacheUtils.getSkillsStr(serviceItemCode, (String) itemsmap.get("SKILLS")));// 服务技能
                    }
                }
                servicePrice = job.getPrice().toString();// 服务价格
                resultmap.put("itemsmap", itemsmap);
                resultmap.put("unit", job.getUnit());
                resultmap.put("unitValue", DictionarysCacheUtils.getServicePriceUnit(job.getUnit()));
            }
            resultmap.put("servicePrice", servicePrice);
            List<Map<String, String>> contracts = orderContractInfoDao.selectContractInfoByOrderNo(orderNo);
            if (contracts.size() == 0) {
                resultmap.put("contracts", "");
            } else {
                for (Map<String, String> contract : contracts) {
                    contract.put("contractUrl", Constants.IMAGE_URL + contract.get("contractUrl"));
                }
                resultmap.put("contracts", contracts);
            }
            return JsonResult.success(resultmap);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.ORDERS_QUERY_ERR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }

    /**
     * 订单详情
     */
    @Override
    public JsonResult getOrderDetails(String orderNo) {
        try {
            OrdersEntity orderMap = orderDao.getWithStaffByNo(orderNo);
            return JsonResult.success(orderMap);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.ORDERS_QUERY_ERR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }

    /**
     * 获取订单
     */
    @Override
    public Map<String, Object> getOrder(String orderNo) {
        return orderDao.getOrder(orderNo);
    }

    /**
     * 取消订单
     */
    @Override
    @Transactional
    public JsonResult cancleOrder(String orderNo, String cancleReason, String counterFee, String tradeAmount) {
        try {
            // 查询orders表 tenantId
            Map<String, Object> order = orderDao.getOrder(orderNo);
            // 判断取消订单是在定金支付前还是支付后 更改订单表
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("orderNo", orderNo);
            map.put("cancleReason", cancleReason);
            map.put("modifyAccount", WebUtils.getMember().getName());

            if ("01".equals(order.get("IDEPOSIT_OVER"))) { // 01待支付定金
                // 支付定金前更新订单状态
                map.put("orderStatus", "05");// 此时订单状态为 05已取消
                orderDao.cancelOrder(map);

            } else {
                if ("05".equals(order.get("SERVICE_ITEM_CODE")) && "0".equals(order.get("ORDER_DEPOSIT"))) {
                    map.put("orderStatus", "05");// 钟点工没有支付定金取消订单 ，此时订单状态为 05已取消
                    orderDao.cancelOrder(map);
                } else {
                    //@ZSQ 查询支付定金金额
                    Map<String, Object> tradeRecordsMap = tradeRecordsDao.selectByOrder(orderNo);
                    //获取支付定金金额
                    BigDecimal tradeAmountDJ = (BigDecimal) tradeRecordsMap.get("TRADE_AMOUNT");
                    //生成退款支付流水号  @zsq   手动生成支付流水号
                    String tradeFlowNo = commonService.createOrderNo(Constants.ZF);//支付流水号 06
                    TradeRecords tradeRecords = new TradeRecords();
                    tradeRecords.setTradeFlowNo(tradeFlowNo); //支付流水号
                    tradeRecords.setTradeType("03");//支付类型 01充值 02提现 03退款
                    //退款金额为支付定金金额
                    BigDecimal total_fee = tradeAmountDJ;

                    tradeRecords.setTradeAmount(total_fee);//支付金额(单位换算)
                    tradeRecords.setTradeTime(new Date());//支付时间
                    tradeRecords.setTradeStatis("01");//支付状态 01处理中 02支付成功 03支付失败
                    tradeRecords.setInitiatorType("01");//发起方类型 01客户 02租户
                    tradeRecords.setOrderNo(orderNo);//订单号
                    tradeRecords.setTradeChannel("04");//银行退款
                    tradeRecords.setAddTime(new Date());//添加时间
                    tradeRecords.setTradeChannelNo(null);//支付渠道流水号
                    tradeRecords.setIsUsable("1");//1代表可用 0代表逻辑删除
                    tradeRecordsDao.insertSelective(tradeRecords);// 插入支付总表z交易记录

                    TenantsFlow tenantsFlow = new TenantsFlow();
                    tenantsFlow.setTradeFlowNo(tradeFlowNo);//支付流水号
                    tenantsFlow.setOrderNo(orderNo);//订单编号
                    tenantsFlow.setTenantId((Integer) order.get("TENANT_ID"));
                    tenantsFlow.setPayresult("1");
                    tenantsFlow.setAddTime(new Date());
                    tenantsFlowDao.insertSelective(tenantsFlow);

                    // 支付定金后更新订单状态
                    map.put("orderStatus", "06"); // 此时订单状态为 06待完成
                    orderDao.cancelOrder(map);

                    // 根据订单号 查支付总表中的支付流水号
                    Map<String, Object> tradeRecord = tradeRecordsMapper.selectByOrderNo(orderNo);
                    String payNo = (String) tradeRecord.get("TRADE_FLOW_NO");

                    TenantsTradeRecordsEntity tenantsTradeRecordsEntity = new TenantsTradeRecordsEntity();
                    String orderNo1 = commonService.createOrderNo(Constants.ZH);

                    BigDecimal bd = new BigDecimal(counterFee);// 手续费
                    BigDecimal bd1 = tradeAmountDJ; // 退款金额

                    PayRecordesOpt payRecordesOpt = new PayRecordesOpt();
                    String inOutNowj = commonService.createOrderNo(Constants.CW);//财务流水号08
                    //取消订单支付定金退款流失
                    TenantsFinanceRecords tenantsFinanceRecords1 = payRecordesOpt.buildWithdjTenantsFinanceRecords(bd.add(bd1), (Integer) order.get("TENANT_ID"), inOutNowj);
                    if (null != order.get("MEMBER_ID") && !"".equals(order.get("MEMBER_ID"))) {
                        //订单支付，插入收支对象  客户
                        MembersInfo members = membersInfoDao.selectByPrimaryKey((Integer) order.get("MEMBER_ID"));
                        tenantsFinanceRecords1.setInOutObject(members.getName());
                    }
                    tenantsFinanceRecords1.setPayType("10");
                    tenantsFinanceRecords1.setRelatedTrans(orderNo);//关联号：订单编号orderNo
                    tenantsFinanceRecords1.setRemarks("退款");
                    //取消订单退定金身份类别
                    tenantsFinanceRecords1.setDraweeId((Integer) order.get("MEMBER_ID"));//付款方id
                    tenantsFinanceRecords1.setDraweeType("01");//付款方类型 01客户02家政机构03业务员04家政员05平台
                    tenantsFinanceRecords1.setPayeeId((Integer) order.get("TENANT_ID"));//收款方id
                    tenantsFinanceRecords1.setPayeeType("02");//收款方类型01客户02家政机构03业务员04家政员05平台
                    //@zsq
                    tenantsFinanceRecords1.setPayNo(payNo);
                    log.info("+++++++++++++++++++交易流水号+++++++++++++++++++++" + tenantsFinanceRecords1.getPayNo());
                    tenantsFinanceRecordsMapper.insertSelective(tenantsFinanceRecords1);


                    Map<String, Object> tenantsFinanceRecords = tenantsFinanceRecordsMapper.getInOutNo(payNo);

                    String inOutNo = (String) tenantsFinanceRecords.get("IN_OUT_NO");


                    tenantsTradeRecordsEntity.setTradeNo(orderNo1); // 账户流水号
                    log.info("+++++++++++++++++++++定金支付的交易流水号++++++++++++++++++++++++" + inOutNo);
                    tenantsTradeRecordsEntity.setInOutNo(inOutNowj);// 交易流水号
                    tenantsTradeRecordsEntity.setCounterFee(bd); // 手续费，一期没有手续费
                    tenantsTradeRecordsEntity.setTradeAmount(bd1); // 退款金额
                    tenantsTradeRecordsEntity.setStatus("01"); // 交易状态：01处理中
                    tenantsTradeRecordsEntity.setTradeType("09"); // 交易类型：09代表退款
                    tenantsTradeRecordsEntity.setTenantId((Integer) order.get("TENANT_ID"));
                    tenantsTradeRecordsEntity.setTradeTime(new Date());// 交易时间
                    tenantsTradeRecordsEntity.setAddTime(new Date());// 该条记录添加时间
                    tenantsTradeRecordsEntity.setFinanceType("02");// 财务类型：02支出
                    tenantsTradeRecordsEntity.setIsUsable("1"); // 表示该条记录可用
                    tenantsFinanceRecords1.setRelatedTrans(order.get("TENANT_ID") + "");
                    log.info("++++++++++++++++++++++退款的交易流水号+++++++++++++++++++++++++++++++++" + tenantsTradeRecordsEntity.getInOutNo());
                    tenantsTradeRecordsMapper.insertRecords(tenantsTradeRecordsEntity); // 轨迹表

                }
            }

            //query job
            TenantsJobs job = tenantsJobsMapper.getJobByOrderNo(orderNo);
            if (job != null) {
                // 修改职位状态
                TenantsJobs updateJob = new TenantsJobs();
                updateJob.setId(job.getId());
                updateJob.setStatus(JobConst.JobStatus.OFF.getCode());
                updateJob.setOrderStatus("05");
                updateJob.setCancelReason("用户取消订单");
                tenantsJobsMapper.update(updateJob);

                //取消职位发布
                mq.publish(WechatConfig.Channel.CANCEL_JOB.getChannel(), orderNo);

                //退保证金
                if(job.getDeposit() != null && job.getDeposit().compareTo(new BigDecimal(0)) > 0){
                    commonService.thawAmount(job.getTenantId(), job.getDeposit(), orderNo, "保证金解冻-招聘[用户取消订单]");
                }
                // 修改应聘状态
                TenantJobResumeForm pm = new TenantJobResumeForm();
                pm.setJobId(job.getId());
                pm.setRowNum(100);
                List<TenantsJobResume> resumes = tenantsJobResumesMapper.getTenantsJobsResumesList(pm);
                if (resumes != null && resumes.size() > 0) {
                    for (TenantsJobResume resume : resumes) {
                        if(resume.getStatus().equals(JobConst.ResumeStatus.REJECTS.getCode())){
                            continue;
                        }
                        TenantsJobResume updateResume = new TenantsJobResume();
                        //简历已取消
                        updateResume.setJobId(resume.getJobId());
                        updateResume.setId(resume.getId());
                        updateResume.setStatus(JobConst.ResumeStatus.CANCELED.getCode());
                        updateResume.setRemarks("用户已取消订单");
                        tenantsJobResumesMapper.update(updateResume);
                        //解冻保证金
                        if (resume.getDeposit() != null && resume.getDeposit().compareTo(new BigDecimal(0)) > 0) {
                            commonService.thawAmount(resume.getResumeTenantId(), resume.getDeposit(), resume.getOrderNo(), "保证金解冻-招聘[用户取消订单]");
                        }
                    }
                }
            }

            //mq.lpush(WechatConfig.Queue.CANCEL_ORDER_B.getQueue(),orderNo);
            //mq.lpush(WechatConfig.Queue.CANCEL_ORDER_C.getQueue(),orderNo);
            mq.publish(WechatConfig.Channel.CANCEL_ORDER.getChannel(), orderNo);

            return JsonResult.success(ResultCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.ORDERS_QUERY_ERR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }

    /**
     * 支付定金、尾款,插入支付流水和订单号对应关系
     */
    @Override
    public JsonResult payment(String flowNo, String orderNo) {
        TenantsFlow record = new TenantsFlow();
        record.setTradeFlowNo(flowNo);
        record.setOrderNo(orderNo);
        record.setAddTime(new Date());
        record.setPayresult("1");
        tenantsFlowDao.insertSelective(record);
        return null;
    }

    // 更换阿姨
    @Override
    public JsonResult changeStaff(String orderNo, String customerRemark) {
        // 插入更换阿姨处理一条记录
        OrderChangeStaffInfo orderChangeStaffInfo = new OrderChangeStaffInfo();
        orderChangeStaffInfo.setOrderNo(orderNo);
        orderChangeStaffInfo.setCustomerRemark(customerRemark);
        orderChangeStaffInfo.setAddTime(new Date());
        orderChangeStaffInfo.setDealStatus("01");
        orderChangeStaffInfoDao.insertSelective(orderChangeStaffInfo);
        // 更新订单状态
        Orders order = orderDao.selectByPrimaryKey(orderNo);
        order.setOrderStatus("08");
        orderDao.updateByPrimaryKeySelective(order);
        return JsonResult.success(null);
    }

    /**
     * 发送http请求
     */
    @Override
    public HttpPost sendBeforePayRequest() {
        return new HttpPost(Global.getConfig("buforeUrl"));
    }

    /**
     * 发送http请求
     */
    @Override
    public HttpPost sendPayRequest() {
        return new HttpPost(Global.getConfig("payRequestUrl"));
    }

    @Override
    public HttpPost sendPcPayRequest() {
        return new HttpPost(Global.getConfig("payWechatRequestUrl"));
    }

    /**
     * 验证金额
     *
     * @param orderNo
     * @param payAmount
     * @return
     */
    @Override
    public String verificationPayAmount(String orderNo, String payAmount) {
        Orders orders = orderDao.selectByPrimaryKey(orderNo);
        String status = orders.getOrderStatus();
        BigDecimal pa = new BigDecimal(Double.valueOf(payAmount));
        if (status.equals("01")) {
            BigDecimal orderDeposit = orders.getOrderDeposit();
            log.info(String.format("cpmpare to [%s]-[%s]=[%s]", orderDeposit.toString(), pa.toString(), orderDeposit.compareTo(pa)));
            if (orderDeposit.compareTo(pa) == 0) {
                return "1"; //金额一致
            } else {
                return "0"; //金额不一致
            }
        } else if (status.equals("03")) {
            BigDecimal orderBalance = orders.getOrderBalance();//订单尾款
            log.info(String.format("cpmpare to [%s]-[%s]=[%s]", orderBalance.toString(), pa.toString(), orderBalance.compareTo(pa)));
            if (orderBalance.compareTo(pa) == 0) {
                return "1"; //金额一致
            } else {
                return "0"; //金额不一致
            }
        }
        return null;
    }

    /**
     * 本地订单-更换阿姨
     *
     * @param orderNo
     * @param type
     * @param remark
     * @return
     */
    @Override
    public JsonResult changehs(String orderNo, String type, String remark) {
        OrderChangehsRecordsEntity orderChangehsRecordsEntitymxid = orderChangehsRecordsMapper.queryMxid(orderNo);
        if (null != type && "01".equals(type)) {
            //第一步，更换阿姨原因 修改orderstate=08，hs_application_time=sysdate
            OrderChangehsRecordsEntity orderChangehsRecordsEntity = new OrderChangehsRecordsEntity();
            orderChangehsRecordsEntity.setOrderNo(orderNo);
            orderChangehsRecordsEntity.setHsApplicationTime(new Date());
            orderChangehsRecordsEntity.setHsRemark(remark);
            orderChangehsRecordsEntity.setMxId(orderChangehsRecordsEntitymxid.getMxId());
            orderChangehsRecordsMapper.insertChangehs(orderChangehsRecordsEntity);
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("orderStatus", "08");
            params.put("orderNo", orderNo);
            orderDao.updateCommonStatus(params);
        }
        return JsonResult.success("");
    }

}
