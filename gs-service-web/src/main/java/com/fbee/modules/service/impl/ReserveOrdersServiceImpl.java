package com.fbee.modules.service.impl;

import com.fbee.modules.bean.Constants;
import com.fbee.modules.bean.ErrorMsg;
import com.fbee.modules.bean.ServiceKillsCacheBean;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.page.Page;
import com.fbee.modules.core.utils.DateUtils;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.jsonData.extend.ReserveOrdersJson;
import com.fbee.modules.mybatis.dao.*;
import com.fbee.modules.mybatis.entity.ReserveOrdersEntity;
import com.fbee.modules.mybatis.entity.TenantsStaffJobInfoEntity;
import com.fbee.modules.mybatis.model.OrderCustomersInfo;
import com.fbee.modules.mybatis.model.Orders;
import com.fbee.modules.mybatis.model.ReserveOrders;
import com.fbee.modules.mybatis.model.ReserveOrdersCustomerInfo;
import com.fbee.modules.operation.ReserveOrdersOpt;
import com.fbee.modules.service.ReserveOrdersService;
import com.fbee.modules.service.basic.BaseService;
import com.fbee.modules.util.WebUtils;
import com.fbee.modules.utils.DictionariesUtil;
import com.fbee.modules.utils.DictionarysCacheUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;

public class ReserveOrdersServiceImpl extends BaseService implements ReserveOrdersService {

    @Autowired
    ReserveOrdersMapper reserveOrdersDao;

    @Autowired
    ReserveOrdersCustomerInfoMapper reserveOrdersCustomerInfoDao;

    @Autowired
    TenantsStaffsInfoMapper tenantsStaffsInfoDao;

    @Autowired
    TenantsStaffJobInfoMapper tenantsStaffJobInfoDao;

    @Autowired
    TenantsStaffSerItemsMapper tenantsStaffSerItemsDao;

    @Autowired
    OrdersMapper ordersMapper;

    @Autowired
    OrderCustomersInfoMapper orderCustomersInfoMapper;

    @Autowired
    TenantsAppsMapper TenantsAppsDao;

    @Autowired
    TenantsStaffCertsInfoMapper tenantsStaffCertsInfoDao;

    ReserveOrdersOpt reserveOrdersOpt = new ReserveOrdersOpt();


    /**
     * 预约列表查询
     * rewrite: getReserveWithStaffList
     */
    @Override
    @Deprecated
    public JsonResult getReserveOrdersList(Integer memberid, String orderstatus, int pageNumber, int pageSize) {
        try {
            //获取总条数
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("memberid", memberid);
            if (orderstatus.equals("01") || orderstatus.equals("02") || orderstatus.equals("03")) {
                map.put("orderstatus", orderstatus);
            } else {
                map.put("orderstatus", null);
            }
            Integer totalCount = reserveOrdersDao.getReserveOrdersCount(map);
            //分页实体
            Page<ReserveOrdersJson> page = new Page<ReserveOrdersJson>();
            page.setPage(pageNumber);
            page.setRowNum(pageSize);
            if (totalCount == null) {
                return JsonResult.success(page);
            }
            //最大页数判断
            int pageM = maxPage(totalCount, page.getRowNum(), page.getPage());
            if (pageM > 0) {
                page.setPage(pageM);
            }
            map.put("pageNumber", page.getOffset());
            map.put("pageSize", page.getRowNum());
            if (totalCount > 0) {
                List<ReserveOrdersEntity> list = reserveOrdersDao.getReserveOrdersList(map);
                List<ReserveOrdersJson> resultlist = new ArrayList<ReserveOrdersJson>();
                for (ReserveOrdersEntity entity : list) {
                    String OrderNo = entity.getOrderNo();//订单流水号
                    String OrderStatus = entity.getOrderStatus();//订单状态
                    Integer TenantId = entity.getTenantId();//租户id
                    Date date = entity.getOrderTime();
                    String dateTime = DateUtils.formatDateTime(date);
                    String Domain = this.TenantsAppsDao.getDomainByTenantId(String.valueOf(entity.getTenantId()));//二级域名

                    String ServiceItemCode = entity.getServiceItemCode();//服务工种
                    Integer StaffSerItemId = entity.getStaffSerItemId();//服务工种id
                    Map<String, Object> customermap = reserveOrdersCustomerInfoDao.reserveOrderDetails(OrderNo);
                    String serviceStart = ((String) customermap.get("SERVICE_START")).split(" ")[0];//服务开始时间

                    String serviceEnd = ((String) customermap.get("SERVICE_END")).split(" ")[0];//服务开始时间

                    String remarks = (String) customermap.get("REMARKS");
                    String websiteName = TenantsAppsDao.getWebsiteName(TenantId);//站点名称
                    Integer staffId = entity.getStaffId();//员工id
                    String staffName = null; //阿姨姓名
                    String age = null;//年龄
                    String zodiac = null;//属相
                    String nativePlace = null;//籍贯
                    String headImage = null;//阿姨头像
                    String sex = null;//性别
                    BigDecimal salary = (BigDecimal)customermap.get("salary");
                    String salaryType = (String)customermap.get("salary_type");

                    if (StringUtils.isNotBlank(StringUtils.IsNull(staffId))) {
                        Map<String, Object> StaffsInfo = null;
                        StaffsInfo = tenantsStaffsInfoDao.getStaffsInfo(staffId);
                        headImage = (String)StaffsInfo.get("HEAD_IMAGE");
                        if(StringUtils.isNotBlank(headImage)&&!headImage.startsWith("http")&&!headImage.startsWith("data")){
                            headImage = Constants.IMAGE_URL + (String)StaffsInfo.get("HEAD_IMAGE");
                        }else{
                            headImage = (String)StaffsInfo.get("HEAD_IMAGE");
                        }
                        staffName = (String) StaffsInfo.get("staffName");
                        age = Integer.toString((Integer)StaffsInfo.get("age"));
                        zodiac = (String) StaffsInfo.get("zodiac");
                        nativePlace = (String) StaffsInfo.get("nativePlace");
                        sex = (String) StaffsInfo.get("sex");
                        Map<String,Object> map2 = new HashMap<String,Object>();
                        map2.put("StaffId", staffId);
                        map2.put("TenantId", TenantId);
                        map2.put("ServiceItemCode", ServiceItemCode);
                        map2.put("StaffSerItemId", StaffSerItemId);
                    }
                    resultlist.add(reserveOrdersOpt.bulidReserveOrdersJson(OrderNo, OrderStatus, ServiceItemCode, serviceStart, websiteName, serviceEnd, salary, staffName, age, zodiac, sex, nativePlace, remarks, Domain, dateTime, headImage, salaryType));
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
     * 预约列表查询
     */
    @Override
    public JsonResult getReserveWithStaffList(Integer memberid, int pageNumber, int pageSize) {
        try {
            //获取总条数
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("memberid", memberid);
            map.put("orderstatus", "01");
            Integer totalCount = reserveOrdersDao.getReserveOrdersCount(map);


            //分页实体
            Page page = new Page();
            page.setPage(pageNumber);
            page.setRowNum(pageSize);
            if (totalCount == null) {
                return JsonResult.success(page);
            }

            //最大页数判断
            int pageM = maxPage(totalCount, page.getRowNum(), page.getPage());
            if (pageM > 0) {
                page.setPage(pageM);
            }
            map.put("pageNumber", page.getOffset());
            map.put("pageSize", page.getRowNum());
            if (totalCount > 0) {
                List<ReserveOrdersEntity> list = reserveOrdersDao.getReserveWithStaffList(map);
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
     * 取消订单
     */
    @Override
    public JsonResult cancellationOfReserveOrder(String orderNo, String cancelReason) {
        try {
            reserveOrdersDao.cancelReserveOrder(orderNo, cancelReason);
            //生成支付订单(orders)，状态已取消.
            // 向订单表插入订单
            Orders ordersEntity = new Orders();
            ReserveOrders roOrders = reserveOrdersDao.getById(orderNo);
            ordersEntity.setTenantId(roOrders.getTenantId());
            ordersEntity.setStaffId(roOrders.getStaffId());
            ordersEntity.setMemberId(roOrders.getMemberId());
            ordersEntity.setOrderSource("02");//02网络订单
            ordersEntity.setOrderNo(roOrders.getOrderNo());
            ordersEntity.setOrderStatus("05"); //已取消
            ordersEntity.setServiceItemCode(roOrders.getServiceItemCode());
            ordersEntity.setAmount(new BigDecimal(0));
            ordersEntity.setOrderTime(roOrders.getOrderTime());
            ordersEntity.setIsLock("1");
            ordersEntity.setOrderBalance(new BigDecimal(0));
            ordersEntity.setIdepositOver("01"); //定金未支付
            ordersEntity.setIsInterview("01");//未面试
            ordersEntity.setBalanceOver("01");//尾款未支付
            ordersEntity.setOrderDeposit(new BigDecimal(0));
            ordersEntity.setAddAccount(WebUtils.getMember().getName());
            ordersEntity.setAddTime(roOrders.getOrderTime());
            ordersEntity.setModifyTime(new Date());
            ordersEntity.setCancleReason(cancelReason);
            ordersEntity.setStafffSerItemId(roOrders.getStaffSerItemId());
            ordersMapper.insertSelective(ordersEntity);

            ReserveOrdersCustomerInfo ordersCustomerInfo = reserveOrdersCustomerInfoDao.selectByPrimaryKey(roOrders.getOrderNo());
            OrderCustomersInfo orderCustomersInfo = reserveOrdersOpt.buildOrderCustomersInfoEntity(ordersCustomerInfo, roOrders);
            orderCustomersInfo.setOrderNo(roOrders.getOrderNo());
            if (orderCustomersInfo.getExpectedBirth() != null) {
                orderCustomersInfo.setIsBabyBorn("0");
            } else {
                orderCustomersInfo.setIsBabyBorn("1");
            }
            orderCustomersInfoMapper.insertSelective(orderCustomersInfo);


            return JsonResult.success(ResultCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.ORDERS_QUERY_ERR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }

    /**
     * 预约订单详情
     * rewrite to reserveOrderDetail
     */
    @Override
    @Deprecated
    public JsonResult reserveOrderDetails(String orderNo) {
        try {
            Map<String, Object> resultmap = new HashMap<String, Object>();
            Map<String, Object> ordersmap = reserveOrdersDao.reserveOrderDetails(orderNo);//订单信息
            String serviceItemCode = (String) ordersmap.get("serviceItemCode");//服务工种
            Integer staffSerItemId = (Integer) ordersmap.get("staffSerItemId");//服务工种id
            ordersmap.put("serviceItemCode_NAME", DictionarysCacheUtils.getServiceTypeName(serviceItemCode));
            String orderStatus = (String) ordersmap.get("orderStatus");
            if (orderStatus.equals("01")) {
                ordersmap.put("orderStatus", "待处理");
            } else if (orderStatus.equals("02")) {
                ordersmap.put("orderStatus", "已处理");
            } else if (orderStatus.equals("03")) {
                ordersmap.put("orderStatus", "已取消");
            }
            resultmap.put("ordersmap", ordersmap);
            Map<String, Object> customermap = reserveOrdersCustomerInfoDao.reserveOrderDetails(orderNo);//预约订单客户信息
            if (StringUtils.isNotBlank((String) customermap.get("SALARY_SKILLS"))) {
                customermap.put("SALARY_SKILLS_NAME", DictionarysCacheUtils.getSkillsStr(serviceItemCode, (String) customermap.get("SALARY_SKILLS")));
            }
            if (StringUtils.isNotBlank((String) customermap.get("COOKING_REQIREMENTS"))) {
                customermap.put("COOKING_REQIREMENTS_NAME", DictionarysCacheUtils.getFeaturesStr("02", (String) customermap.get("COOKING_REQIREMENTS")));
            }
            if (StringUtils.isNotBlank((String) customermap.get("LANGUAGE_REQUIREMENTS"))) {
                customermap.put("LANGUAGE_REQUIREMENTS_NAME", DictionarysCacheUtils.getFeaturesStr("01", (String) customermap.get("LANGUAGE_REQUIREMENTS")));
            }
            if (StringUtils.isNotBlank((String) customermap.get("PERSONALITY_REQUIREMENTS"))) {
                customermap.put("PERSONALITY_REQUIREMENTS_NAME", DictionarysCacheUtils.getFeaturesStr("03", (String) customermap.get("PERSONALITY_REQUIREMENTS")));
            }
            if (StringUtils.isNotBlank((String) customermap.get("SALARY_TYPE"))) {
                customermap.put("salaryTypeValue", DictionarysCacheUtils.getServicePriceUnit((String) customermap.get("SALARY_TYPE")));
            }
            if (StringUtils.isNotBlank((String) customermap.get("WAGE_REQUIREMENTS"))) {
                String age = DictionarysCacheUtils.getAgeIntervalName((String) customermap.get("WAGE_REQUIREMENTS"));
                String[] ages = age.split(",");
                if (ages.length == 2) {
                    if (ages[0].equals("0")) {
                        customermap.put("WAGE_REQUIREMENTS", ages[1] + "岁以下");
                    } else {
                        customermap.put("WAGE_REQUIREMENTS", ages[0] + "-" + ages[1] + "岁");
                    }
                } else if (ages.length == 1) {
                    customermap.put("WAGE_REQUIREMENTS", ages[0] + "岁以上");
                }
            } else {
                customermap.put("WAGE_REQUIREMENTS", "不限");//年龄要求
            }

            if (null != customermap.get("OLDER_AGE_RANGE") && StringUtils.isNotBlank((String) customermap.get("OLDER_AGE_RANGE"))) {
                DictionarysCacheUtils.getOldAge(customermap.get("OLDER_AGE_RANGE") + "");
                customermap.put("OLDER_AGE_RANGE", (String) DictionarysCacheUtils.getOldAge(customermap.get("OLDER_AGE_RANGE") + ""));
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

            customermap.put("SERVICE_PROVICE_NAME", StringUtils.isBlank((String) customermap.get("SERVICE_PROVICE")) ? "" : DictionarysCacheUtils.getProvinceName((String) customermap.get("SERVICE_PROVICE")));
            customermap.put("SERVICE_COUNTY_NAME", StringUtils.isBlank((String) customermap.get("SERVICE_COUNTY")) ? "" : DictionarysCacheUtils.getCountyName((String) customermap.get("SERVICE_COUNTY")));
            customermap.put("SERVICE_CITY_NAME", StringUtils.isBlank((String) customermap.get("SERVICE_CITY")) ? "" : DictionarysCacheUtils.getCityName((String) customermap.get("SERVICE_CITY")));
            customermap.put("SERVICE_TYPE_NAME", "不限");//服务类型
            if (StringUtils.isNotBlank((String) customermap.get("SERVICE_TYPE"))) {
                customermap.put("SERVICE_TYPE_NAME", DictionarysCacheUtils.getServiceNatureStr(serviceItemCode, (String) customermap.get("SERVICE_TYPE")));
            }
            resultmap.put("customermap", customermap);
            String servicePrice = null;//服务价格
            if (ordersmap.get("staffId") != null) {
                Map<String, Object> staffmap = tenantsStaffsInfoDao.reserveOrderDetails((Integer) ordersmap.get("staffId"));//阿姨基本信息
                staffmap.put("MARITAL_STATUS_NAME", StringUtils.isBlank((String) staffmap.get("MARITAL_STATUS")) ? "" : DictionarysCacheUtils.getMaritalStatus((String) staffmap.get("MARITAL_STATUS")));//婚姻状况中文
                staffmap.put("EDUCARION_NAME", StringUtils.isBlank((String) staffmap.get("EDUCARION")) ? "" : DictionarysCacheUtils.getEducationName((String) staffmap.get("EDUCARION")));//学历中文
                staffmap.put("BLOOD_TYPE_NAME", StringUtils.isBlank((String) staffmap.get("BLOOD_TYPE")) ? "" : DictionarysCacheUtils.getBloodType((String) staffmap.get("BLOOD_TYPE")));//学历中文
                staffmap.put("SEX_NAME", StringUtils.isBlank((String) staffmap.get("SEX")) ? "" : DictionarysCacheUtils.getMemberSex((String) staffmap.get("SEX")));//性别中文
                staffmap.put("NATION_NAME", StringUtils.isBlank((String) staffmap.get("NATION")) ? "" : DictionarysCacheUtils.getNationName((String) staffmap.get("NATION")));//性别中文
                staffmap.put("ZODIAC_NAME", StringUtils.isBlank((String) staffmap.get("ZODIAC")) ? "" : DictionarysCacheUtils.getZodiacName((String) staffmap.get("ZODIAC")));//属相中文
                staffmap.put("NATIVE_PLACE_NAME", StringUtils.isBlank((String) staffmap.get("NATIVE_PLACE")) ? "" : DictionarysCacheUtils.getNativePlaceName((String) staffmap.get("NATIVE_PLACE")));//籍贯中文
                staffmap.put("CONSTELLATION_NAME", StringUtils.isBlank((String) staffmap.get("CONSTELLATION")) ? "" : DictionarysCacheUtils.getConstellationName((String) staffmap.get("CONSTELLATION")));//星座中文
                staffmap.put("FERTILITY_SITUATION_NAME", StringUtils.isBlank((String) staffmap.get("FERTILITY_SITUATION")) ? "" : DictionarysCacheUtils.getFertilityStatus((String) staffmap.get("FERTILITY_SITUATION")));//生育状况中文
                staffmap.put("WORK_STATUS_NAME", StringUtils.isBlank((String) staffmap.get("WORK_STATUS")) ? "" : DictionarysCacheUtils.getWorkStatusName((String) staffmap.get("WORK_STATUS")));//工作状况中文
                String img = (String)staffmap.get("HEAD_IMAGE");
                if(StringUtils.isNotBlank(img)&&!img.startsWith("http")&&!img.startsWith("data")){
                    staffmap.put("HEAD_IMAGE", Constants.IMAGE_URL + img);
                }
                resultmap.put("staffmap", staffmap);
                Map<String, Object> parammap = new HashMap<String, Object>();
                parammap.put("staffid", (Integer) ordersmap.get("staffId"));
                parammap.put("tenantid", (Integer) ordersmap.get("tenantId"));
                parammap.put("serviceItemCode", serviceItemCode);
                parammap.put("staffSerItemId", staffSerItemId);
                Map<String, Object> itemsmap = tenantsStaffSerItemsDao.reserveOrderDetail(parammap);//阿姨服务信息
                itemsmap.put("SERVICE_ITEM_CODE_NAME", DictionarysCacheUtils.getServiceTypeName(serviceItemCode));
                itemsmap.put("SKILLS_NAME", DictionarysCacheUtils.getSkillsStr(serviceItemCode, (String) itemsmap.get("SKILLS")));//服务技能
                itemsmap.put("add_time", DateUtils.dateToStr((Date) itemsmap.get("add_time"), "yyyy-MM-dd HH:mm:ss"));//添加时间

                TenantsStaffJobInfoEntity job = tenantsStaffJobInfoDao.getById((Integer)ordersmap.get("staffId"));

                itemsmap.put("experience", job.getExperience());
                itemsmap.put("EXPERIENCE_NAME", DictionarysCacheUtils.getExperienceName(job.getExperience()));//服务经验
                servicePrice = job.getPrice().toString();//服务价格
//                resultmap.put("unit", job.getUnit());
//                resultmap.put("unitValue", DictionarysCacheUtils.getServicePriceUnit(job.getUnit()));
                resultmap.put("itemsmap", itemsmap);
            }
            resultmap.put("servicePrice", servicePrice);

            return JsonResult.success(resultmap);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.ORDERS_QUERY_ERR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }


    /**
     * 预约订单详情
     * rewrite to reserveOrderDetail
     */
    @Override
    public JsonResult reserveOrderDetail(String orderNo) {
        try {
            ReserveOrdersEntity entity = reserveOrdersDao.getWithStaffByOrderNo(orderNo);//订单信息
            if(entity == null){
                return JsonResult.success(entity);
            }
            return JsonResult.success(entity);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ErrorMsg.ORDERS_QUERY_ERR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }
}
