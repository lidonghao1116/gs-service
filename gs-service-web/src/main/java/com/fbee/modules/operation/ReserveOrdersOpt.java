package com.fbee.modules.operation;

import com.fbee.modules.core.utils.DateUtils;
import com.fbee.modules.jsonData.extend.ReserveOrdersJson;
import com.fbee.modules.mybatis.model.OrderCustomersInfo;
import com.fbee.modules.mybatis.model.ReserveOrders;
import com.fbee.modules.mybatis.model.ReserveOrdersCustomerInfo;
import com.fbee.modules.utils.DictionarysCacheUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

public class ReserveOrdersOpt {

    public ReserveOrdersJson bulidReserveOrdersJson(String OrderNo, String orderStatus, String serviceItemCode,
                                                    String serviceStart, String websiteName, String serviceEnd,
                                                    BigDecimal salary, String staffName, String age,
                                                    String zodiac, String sex, String nativePlace, String remarks,
                                                    String domain, String dateTime, String headImage, String salaryType) {
        ReserveOrdersJson reserveOrdersJson = new ReserveOrdersJson();
        reserveOrdersJson.setDateTime(dateTime);//下单时间
        reserveOrdersJson.setOrderNo(OrderNo);
        if (orderStatus.equals("01")) {//订单状态
            reserveOrdersJson.setOrderStatus("01,待处理");
        } else if (orderStatus.equals("02")) {
            reserveOrdersJson.setOrderStatus("02,已处理");
        } else if (orderStatus.equals("03")) {
            reserveOrdersJson.setOrderStatus("03,已取消");
        }
        if (StringUtils.isNotBlank(serviceItemCode)) {
            reserveOrdersJson.setServiceItemCode(serviceItemCode + "," + DictionarysCacheUtils.getServiceTypeName(serviceItemCode));
        }
        reserveOrdersJson.setStaffName(staffName);
        reserveOrdersJson.setAge(age);
        if (StringUtils.isNotBlank(zodiac)) {
            reserveOrdersJson.setZodiac(DictionarysCacheUtils.getZodiacName(zodiac));//属相中文
        }
        if (StringUtils.isNotBlank(nativePlace)) {
            reserveOrdersJson.setNativePlace(DictionarysCacheUtils.getNativePlaceName(nativePlace));//籍贯中文
        }
        reserveOrdersJson.setServiceStart(serviceStart);
        reserveOrdersJson.setServiceEnd(serviceEnd);
        reserveOrdersJson.setWebsiteName(websiteName);
        reserveOrdersJson.setRemarks(remarks);
        reserveOrdersJson.setDomain(domain);
        reserveOrdersJson.setHeadImage(headImage);
        reserveOrdersJson.setSex(sex);

        reserveOrdersJson.setServicePrice(salary);
        reserveOrdersJson.setUnit(salaryType);
        reserveOrdersJson.setUnitValue(DictionarysCacheUtils.getServicePriceUnit(salaryType));
        return reserveOrdersJson;
    }


    public OrderCustomersInfo buildOrderCustomersInfoEntity(ReserveOrdersCustomerInfo ordersCustomerInfo, ReserveOrders roOrders) {
        OrderCustomersInfo orderCustomersInfoEntity = new OrderCustomersInfo();

        orderCustomersInfoEntity.setMemberMobile(roOrders.getMemberMobile());
        orderCustomersInfoEntity.setMemberSex(roOrders.getMemberSex());
        orderCustomersInfoEntity.setMemberName(roOrders.getMemberName());

        orderCustomersInfoEntity.setServiceItemCode(roOrders.getServiceItemCode());

        orderCustomersInfoEntity.setChildrenCount(ordersCustomerInfo.getChildrenCount());
        orderCustomersInfoEntity.setChildrenAgeRange(ordersCustomerInfo.getChildrenAgeRange());

        orderCustomersInfoEntity.setFamilyCount(ordersCustomerInfo.getFamilyCount());
        orderCustomersInfoEntity.setServiceStart(ordersCustomerInfo.getServiceStart());
        orderCustomersInfoEntity.setServiceEnd(ordersCustomerInfo.getServiceEnd());

        orderCustomersInfoEntity.setHouseType(ordersCustomerInfo.getHouseType());
        orderCustomersInfoEntity.setHouseArea(ordersCustomerInfo.getHouseArea());

        orderCustomersInfoEntity.setExpectedBirth(DateUtils.parseDate(ordersCustomerInfo.getExpectedBirth()));
        orderCustomersInfoEntity.setIsBabyBorn(ordersCustomerInfo.getIsBabyBorn());

        orderCustomersInfoEntity.setOlderCount(ordersCustomerInfo.getOlderCount());
        orderCustomersInfoEntity.setOlderAgeRange(ordersCustomerInfo.getOlderAgeRange());
        orderCustomersInfoEntity.setSelfCares(ordersCustomerInfo.getSelfCares());

        orderCustomersInfoEntity.setSalary(ordersCustomerInfo.getSalary());
        orderCustomersInfoEntity.setSalaryType(orderCustomersInfoEntity.getSalaryType());

        orderCustomersInfoEntity.setServiceProvice(ordersCustomerInfo.getServiceProvice());
        orderCustomersInfoEntity.setServiceCity(ordersCustomerInfo.getServiceCity());
        orderCustomersInfoEntity.setServiceCounty(ordersCustomerInfo.getServiceCounty());
        orderCustomersInfoEntity.setServiceAddress(ordersCustomerInfo.getServiceAddress());


        orderCustomersInfoEntity.setCookingReqirements(ordersCustomerInfo.getCookingReqirements());
        orderCustomersInfoEntity.setLanguageRequirements(ordersCustomerInfo.getLanguageRequirements());
        orderCustomersInfoEntity.setPersonalityRequirements(ordersCustomerInfo.getPersonalityRequirements());
        orderCustomersInfoEntity.setSalarySkills(ordersCustomerInfo.getSalarySkills());


        orderCustomersInfoEntity.setWageRequirements(ordersCustomerInfo.getWageRequirements());
        orderCustomersInfoEntity.setExperienceRequirements(ordersCustomerInfo.getExperienceRequirements());

        orderCustomersInfoEntity.setSpecialNeeds(ordersCustomerInfo.getRemarks());

        orderCustomersInfoEntity.setServiceType(ordersCustomerInfo.getServiceType());

        orderCustomersInfoEntity.setPetRaising(ordersCustomerInfo.getPetRaising());

        return orderCustomersInfoEntity;
    }
}
