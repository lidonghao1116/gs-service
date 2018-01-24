package com.fbee.modules.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.fbee.modules.core.utils.DateUtils;
import com.fbee.modules.mybatis.dao.*;
import com.fbee.modules.mybatis.model.*;
import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.utils.JsonUtils;
import com.fbee.modules.wechat.message.config.WechatConfig;
import com.fbee.modules.wechat.message.model.NewOrderModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fbee.modules.bean.MemberBean;
import com.fbee.modules.core.Log;
import com.fbee.modules.form.ReserveOrdersForm;
import com.fbee.modules.form.ServiceInfoForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.jsonData.json.IndexJsonData;
import com.fbee.modules.mybatis.entity.ReserveOrdersCustomerInfoEntity;
import com.fbee.modules.mybatis.entity.ReserveOrdersEntity;
import com.fbee.modules.mybatis.entity.TenantsBannersEntity;
import com.fbee.modules.mybatis.entity.TenantsStaffSerItemsEntity;
import com.fbee.modules.operation.TenantsServiceOpt;
import com.fbee.modules.service.CommonService;
import com.fbee.modules.service.TenantService;
import com.fbee.modules.service.basic.BaseService;
import com.fbee.modules.utils.DictionarysCacheUtils;

/**
 * @author fry
 * @Description：租户站点信息实现类
 * @date 2017年1月19日 下午2:44:00
 */
@Service
public class TenantServiceImpl extends BaseService implements TenantService {
    @Autowired
    MembersInfoMapper membersInfoDao;

    @Autowired
    TenantsAppsMapper tenantsAppsMapper;

    @Autowired
    TenantsBannersMapper tenantsBannersMapper;

    @Autowired
    TenantsContactBarMapper tenantsContactBarMapper;

    @Autowired
    TenantsServiceItemsMapper tenantsServiceItemsMapper;

    @Autowired
    TenantsFlowMapper tenantsFlowDao; //存储租户充值时支付流水和租户id对应关系

    @Autowired
    CommonService commonService;

    @Autowired
    TenantsStaffSerItemsMapper tenantsStaffSerItemsMapper;

    @Autowired
    BankMapper bankMapper;

    @Autowired
    TenantsStaffJobInfoMapper tenantsStaffJobInfoMapper;

    private JedisTemplate mq = JedisUtils.getJedisMessage();

    @Override
    @Transactional
    public IndexJsonData getIndexInfo(Integer tenantId) {
        IndexJsonData indexJsonData = new IndexJsonData();
        // 查询租户站点信息
        TenantsApps tenantsAppsEntity = tenantsAppsMapper.getById(tenantId);
        if (tenantsAppsEntity != null) {
            indexJsonData.setWesiteName(tenantsAppsEntity.getWebsiteName());
            indexJsonData.setContactAddress(tenantsAppsEntity.getContactAddress());

            if (null != tenantsAppsEntity.getArea() && !"".equals(tenantsAppsEntity.getArea())) {
                indexJsonData.setContactAddress(DictionarysCacheUtils.getCountyName(tenantsAppsEntity.getArea()) + indexJsonData.getContactAddress());
            }

            if (null != tenantsAppsEntity.getCity() && !"".equals(tenantsAppsEntity.getCity())) {
                indexJsonData.setContactAddress(DictionarysCacheUtils.getCityName(tenantsAppsEntity.getCity()) + indexJsonData.getContactAddress());
            }

            if (null != tenantsAppsEntity.getPrivince() && !"".equals(tenantsAppsEntity.getPrivince())) {
                indexJsonData.setContactAddress(DictionarysCacheUtils.getProvinceName(tenantsAppsEntity.getPrivince()) + indexJsonData.getContactAddress());
            }
            //门店联系方式
            indexJsonData.setContactPhone(tenantsAppsEntity.getTenantsPhone());

        }
        // 查询站点banner图
        TenantsBannersEntity tenantsBannersEntity = tenantsBannersMapper.getBannerByTenantId(tenantId);
        if (tenantsBannersEntity != null) {
            if ("0".equals(tenantsBannersEntity.getIsDefault())) {
                indexJsonData.setBannerUrl("http://image-jiacer.oss-cn-shanghai.aliyuncs.com/fbee/default/banner.png");
            } else {
                if (tenantsBannersEntity.getBannerUrl() == null || "".equals(tenantsBannersEntity.getBannerUrl())) {
                    indexJsonData.setBannerUrl("http://image-jiacer.oss-cn-shanghai.aliyuncs.com/fbee/default/banner.png");
                } else {
                    if (tenantsBannersEntity.getBannerUrl() != null && tenantsBannersEntity.getBannerUrl().startsWith("http")) {
                        indexJsonData.setBannerUrl(tenantsBannersEntity.getBannerUrl());
                    } else {
                        indexJsonData.setBannerUrl("/images" + tenantsBannersEntity.getBannerUrl());
                    }
                }
            }
        } else {
            indexJsonData.setBannerUrl("http://image-jiacer.oss-cn-shanghai.aliyuncs.com/fbee/default/banner.png");
        }
        // 查询站点联系方式
        TenantsContactBar tenantsContactBarEntity = tenantsContactBarMapper.getById(tenantId);
        if (tenantsContactBarEntity != null) {

            if ("1".equals(tenantsContactBarEntity.getIsOpenMobile())) {
                indexJsonData.setContactPhone1(tenantsContactBarEntity.getContactPhone());
            }
            if ("1".equals(tenantsContactBarEntity.getIsOpenQq())) {

                if ("1".equals(tenantsContactBarEntity.getIsOpenQqOne())) {
                    indexJsonData.setQqOne(tenantsContactBarEntity.getQqOne());
                }
                if ("1".equals(tenantsContactBarEntity.getIsOpenQqTwo())) {
                    indexJsonData.setQqTwo(tenantsContactBarEntity.getQqTwo());

                }
                if ("1".equals(tenantsContactBarEntity.getIsOpenQqThree())) {
                    indexJsonData.setQqThree(tenantsContactBarEntity.getQqThree());
                }
            }
            if ("1".equals(tenantsContactBarEntity.getIsOpenQrCode())) {
                indexJsonData.setQrCode(tenantsContactBarEntity.getQrCode());
            }
        }

        return indexJsonData;
    }

    /**
     * 获取服务工种列表
     */
    @Override
    @Transactional
    public List<Map<String, String>> getServiceItems(Integer tenantId) {

        List<Map<String, String>> list = tenantsServiceItemsMapper.getServiceItemList(tenantId);

        for (Map<String, String> map : list) {
            if (!StringUtils.isBlank((String) map.get("servicePriceUnit"))) {
                map.put("servicePriceUnit",
                        DictionarysCacheUtils.getServicePriceUnit((String) map.get("servicePriceUnit")));
            }
        }

        if (list.size() > 0) {
            return list;
        }
        return list;
    }

    @Autowired
    TenantsServiceItemsMapper tenantsStaffItemsDao;

    @Autowired
    ReserveOrdersMapper reserveOrdersDao;

    @Autowired
    ReserveOrdersCustomerInfoMapper reserveOrdersCustomerInfoDao;

    TenantsServiceOpt tenantsServiceOpt = new TenantsServiceOpt();

    /**
     * 服务工种列表
     */
    @Override
    public JsonResult getTenantsServiceItemList(Integer tenantId) {
        return JsonResult.success(tenantsStaffItemsDao.getStaffServiceItemList(tenantId));
    }

    /**
     * 通过二级域名获取租户id
     */
    @Override
    public Integer getTenantIdByDomain(String domain) {
        return tenantsAppsMapper.getTenantIdByDomain(domain);
    }

    /**
     * 通过租户id获取二级域名
     */
    @Override
    public String getDomainByTenantId(String id) {
        return tenantsAppsMapper.getDomainByTenantId(id);
    }


    /**
     * 查询支付结果
     *
     * @param flowNo
     * @return
     */
    public JsonResult getPayResult(String flowNo) {
        String payResult = tenantsFlowDao.selectByPrimaryKey(flowNo).getPayresult();
        return JsonResult.success(payResult);
    }

    /**
     * 查询订单号
     *
     * @param flowNo
     * @return
     */
    public String getPayOrderNo(String flowNo) {
        return tenantsFlowDao.selectByPrimaryKey(flowNo).getOrderNo();
    }


    public JsonResult getlogopath(String domain) {
        String logopath = tenantsAppsMapper.getlogopath(domain);
        return JsonResult.success(logopath);
    }


    /**
     * 预约
     *
     * @author xiehui
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult subRequirementsQuery(Integer tenantId, MemberBean memeberbean,
                                           ReserveOrdersForm reserveordersform, ServiceInfoForm serviceinfoform) {
        String roCount = reserveOrdersDao.getCount(memeberbean.getId());
        if (StringUtils.isNotBlank(roCount) && Integer.valueOf(roCount) > 4) {
            // 判断待处理订单数量 5个
            return JsonResult.failure(ResultCode.MORETHAN_TWO);
        }
        String orderNo = commonService.createOrderNo("02");
        ReserveOrdersEntity reserveOrdersEntity = tenantsServiceOpt.buildReserveOrdersEntity(orderNo, tenantId, memeberbean.getId(), reserveordersform);
        ReserveOrdersCustomerInfoEntity reserveOrdersCustomerInfoEntity = tenantsServiceOpt.buildReserveOrdersCustomerInfoEntity(orderNo, reserveordersform, serviceinfoform);
        //@zsq 增加预约阿姨服务工种id
        if (reserveOrdersEntity.getStaffId() != null) {
            TenantsStaffSerItemsEntity tenantsStaffSerItemsEntity = new TenantsStaffSerItemsEntity();
            tenantsStaffSerItemsEntity.setStaffId(reserveordersform.getStaffId());
            tenantsStaffSerItemsEntity.setTenantId(tenantId);
            tenantsStaffSerItemsEntity.setServiceItemCode(reserveordersform.getServiceItemCode());
            TenantsStaffSerItemsEntity tenantsStaffSerItems = tenantsStaffSerItemsMapper.getStaffServiceItemsByKey(tenantsStaffSerItemsEntity);
            reserveOrdersEntity.setStaffSerItemId(tenantsStaffSerItems.getId());
        }
        reserveOrdersDao.insert(reserveOrdersEntity);

        // 查询客户信息
        MembersInfoExample example = new MembersInfoExample();
        example.createCriteria().andIdEqualTo(memeberbean.getId());
        List<MembersInfo> list = membersInfoDao.selectByExample(example);
        for (MembersInfo member : list) {
            if (StringUtils.isBlank(member.getProvice())) {
                if (StringUtils.isBlank(member.getCity())) {
                    if (StringUtils.isBlank(member.getCounty())) {
                        member.setProvice(serviceinfoform.getServiceProvice());
                        member.setCity(serviceinfoform.getServiceCity());
                        member.setCounty(serviceinfoform.getServiceCounty());
                        if (serviceinfoform.getServiceAddress() != null) {
                            member.setAddress(serviceinfoform.getServiceAddress());
                        } else {
                            member.setAddress("");
                        }
                        member.setId(member.getId());
                        membersInfoDao.updateMemberByCity(member);
                    }
                }
            }
        }
        if (reserveordersform.getSalary() == null || StringUtils.isBlank(reserveordersform.getSalaryType())) {
            //价格为空，则默认家政员价格
            TenantsStaffJobInfo staffJob = tenantsStaffJobInfoMapper.getById(reserveordersform.getStaffId());
            if (staffJob != null) {
                reserveOrdersCustomerInfoEntity.setSalary(staffJob.getPrice() == null ? new BigDecimal(0) : staffJob.getPrice());
                reserveOrdersCustomerInfoEntity.setSalaryType(staffJob.getUnit() == null ? "01" : staffJob.getUnit());
            }
        } else {
            //自定义价格
            reserveOrdersCustomerInfoEntity.setSalary(reserveordersform.getSalary());
            reserveOrdersCustomerInfoEntity.setSalaryType(reserveordersform.getSalaryType());
        }

        reserveOrdersCustomerInfoDao.insertSelective(reserveOrdersCustomerInfoEntity);


        //预约新订单
        NewOrderModel model = new NewOrderModel();
        model.setOrderNo(orderNo);
        model.setTitle("您好，有一笔新的客户订单，请尽快处理。");
        model.setTenantId(tenantId);
        model.setOrderName(DictionarysCacheUtils.getServiceTypeName(reserveOrdersEntity.getServiceItemCode()) + "-" + DictionarysCacheUtils.getServiceNatureStr(reserveOrdersEntity.getServiceItemCode(), reserveOrdersCustomerInfoEntity.getServiceType()));
        model.setOrderTime(DateUtils.formatDateTime(reserveOrdersEntity.getOrderTime()));
        model.setOrderAmount(reserveOrdersCustomerInfoEntity.getSalary().setScale(2).toString() + DictionarysCacheUtils.getServicePriceUnit(reserveOrdersCustomerInfoEntity.getSalaryType()));
        model.setDescription(String.format("%s%s%s", DictionarysCacheUtils.getProvinceName(reserveOrdersCustomerInfoEntity.getServiceProvice()), DictionarysCacheUtils.getCityName(reserveOrdersCustomerInfoEntity.getServiceCity()), DictionarysCacheUtils.getCountyName(reserveOrdersCustomerInfoEntity.getServiceCounty())));
        model.setRemark("请在48小时内完成支付，逾期将自动取消，点击查看详情。");

        String msg = JsonUtils.toJson(model);
        mq.lpush(WechatConfig.Queue.RESERVE_ORDER_B.getQueue(), msg);
        mq.publish(WechatConfig.Channel.RESERVE_ORDER.getChannel(), msg);

        return JsonResult.success(ResultCode.getMsg(ResultCode.SUCCESS));

    }

    //银行列表
    @Override
    public JsonResult getBankCode() {
        try {
            List<Map<Object, String>> map = bankMapper.selectBankCode();


            return JsonResult.success(map);

        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }

    }

}
