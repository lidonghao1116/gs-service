package com.fbee.modules.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fbee.modules.mybatis.entity.TenantsStaffJobInfoEntity;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbee.modules.core.page.Page;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.form.extend.StaffInfoForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.mybatis.dao.TenantsServiceItemsMapper;
import com.fbee.modules.mybatis.dao.TenantsStaffCertsInfoMapper;
import com.fbee.modules.mybatis.dao.TenantsStaffJobInfoMapper;
import com.fbee.modules.mybatis.dao.TenantsStaffSerItemsMapper;
import com.fbee.modules.mybatis.dao.TenantsStaffsInfoMapper;
import com.fbee.modules.mybatis.dao.TenantsStaffsMediaMapper;
import com.fbee.modules.mybatis.entity.TenantsStaffsInfoEntity;
import com.fbee.modules.service.HouseKeepingService;
import com.fbee.modules.service.basic.BaseService;
import com.fbee.modules.utils.DictionarysCacheUtils;

@Service
public class HouseKeepingImpl extends BaseService implements HouseKeepingService {
    @Autowired
    TenantsServiceItemsMapper tenantsServiceItemsDao;//服务工种
    @Autowired
    TenantsStaffsInfoMapper tenantsStaffsInfoDao;//员工信息
    @Autowired
    TenantsStaffsMediaMapper tenantsStaffsMediaDao;//员工视频图片信息
    @Autowired
    TenantsStaffCertsInfoMapper tenantsStaffCertsDao;//证书认证
    @Autowired
    TenantsStaffSerItemsMapper tenantsStaffSerItemsDao;//服务信息
    @Autowired
    TenantsStaffJobInfoMapper tenantsStaffJobDao;//求职信息

    @Override
    public JsonResult getStaffServiceItemList(Integer tenantId) {
        //服务工种列表获取
        return JsonResult.success(tenantsServiceItemsDao.getStaffServiceItemList(tenantId));
    }

    @Override
    public JsonResult getObjectContent(Map<Object, Object> map) {
        //服务对象与内容获取
        return JsonResult.success(tenantsServiceItemsDao.getObjectContent(map));
    }

    @SuppressWarnings("rawtypes")
    @Override
    public JsonResult getStaffPersonInfo(StaffInfoForm staffInfoForm, int pageNumber, int pageSize) {
        //服务员列表信息获取
        Map<String, Object> map = new HashMap<String, Object>();
        if (staffInfoForm.getServicePrice() != null && !"".equals(staffInfoForm.getServicePrice())) {
            String valuePrice = null;
            if ("01".equalsIgnoreCase(staffInfoForm.getUnit()) || "03".equalsIgnoreCase(staffInfoForm.getUnit())) {
                //按月 & 26天
                valuePrice = DictionarysCacheUtils.getServicePirceForMonthValue(staffInfoForm.getServicePrice());
            } else if ("02".equalsIgnoreCase(staffInfoForm.getUnit())) {
                //按小时
                valuePrice = DictionarysCacheUtils.getServicePirceForHourValue(staffInfoForm.getServicePrice());
            } else if ("04".equalsIgnoreCase(staffInfoForm.getUnit())) {
                //按天
                valuePrice = DictionarysCacheUtils.getServicePirceForDayValue(staffInfoForm.getServicePrice());
            }
            if(StringUtils.isNotBlank(valuePrice)){
                String[] arrTemp = valuePrice.split(",");
                double minServicePrice = Double.parseDouble(arrTemp[0]);
                map.put("minServicePrice", minServicePrice);
                if (arrTemp.length == 2) {
                    double maxServicePrice = Double.parseDouble(arrTemp[1]);
                    map.put("maxServicePrice", maxServicePrice);
                }
            }
        }
        if ("01".equalsIgnoreCase(staffInfoForm.getUnit())) {
            //查询 按月 & 按26天
            map.put("unitList", Lists.newArrayList("01", "03"));
        } else {
            map.put("unit", staffInfoForm.getUnit());
        }

        map.put("tenantId", staffInfoForm.getTenantId());
        map.put("experience", staffInfoForm.getExperence());
        if (staffInfoForm.getAge() != null && !"".equals(staffInfoForm.getAge())) {
            String valueAge = DictionarysCacheUtils.getAgeIntervalName(staffInfoForm.getAge());
            String[] arrAge = valueAge.split(",");
            double minAge = Integer.parseInt(arrAge[0]);
            map.put("minAge", minAge);
            if (arrAge.length == 2) {
                double maxAge = Integer.parseInt(arrAge[1]);
                map.put("maxAge", maxAge);
            }
        }
        map.put("age", staffInfoForm.getAge());
        map.put("zodiac", staffInfoForm.getZodiac());
        map.put("education", staffInfoForm.getEducation());
        map.put("nativePlace", staffInfoForm.getNativePlace());
        map.put("sex", staffInfoForm.getSex());
        map.put("workStatus", staffInfoForm.getWorkStatus());
        map.put("itemCode", staffInfoForm.getItemCode());
        map.put("serviceNature", staffInfoForm.getServiceNature());
        map.put("ageBy", staffInfoForm.getAgeBy());
        map.put("priceBy", staffInfoForm.getPriceBy());
        map.put("experienceBy", staffInfoForm.getExperienceBy());
        Integer totalCount = tenantsStaffsInfoDao.getStaffCountByTenantId(map);
        // 分页实体
        Page<Map> page = new Page<Map>();
        page.setRecords(totalCount);
        page.setPage(pageNumber);
        page.setRowNum(pageSize);
        if (totalCount > 0) {
            map.put("pageNumber", page.getOffset());
            map.put("pageSize", page.getRowNum());
            List<Map> resultList = tenantsStaffsInfoDao.getStaffPersonInfo(map);
            for(Map m : resultList){
                String str = (String)m.get("headImage");
                if(StringUtils.isNotBlank(str) && !str.startsWith("http") && !str.startsWith("data")){
                    m.put("headImage", "/images"+str);
                }
            }
            page.setRows(resultList);
        }
        return JsonResult.success(page);
    }


    @SuppressWarnings("static-access")
    @Override
    public JsonResult getStaffPersonInfoByPrimaryKey(int staffId) {
        //获取服务员员工个人信息
        TenantsStaffsInfoEntity tenantsStaffsInfoEntity = new TenantsStaffsInfoEntity();
        tenantsStaffsInfoEntity.setStaffId(staffId);
        tenantsStaffsInfoEntity = tenantsStaffsInfoDao.getStaffInfo(tenantsStaffsInfoEntity);
        if(tenantsStaffsInfoEntity == null){
            return null;
        }
        tenantsStaffsInfoEntity.setJobInfo(getJobInfo(staffId));

        if(StringUtils.isNotBlank(tenantsStaffsInfoEntity.getHeadImage())
                && !tenantsStaffsInfoEntity.getHeadImage().startsWith("http")
                && !tenantsStaffsInfoEntity.getHeadImage().startsWith("data")){
            tenantsStaffsInfoEntity.setHeadImage("/images"+tenantsStaffsInfoEntity.getHeadImage());
        }
        DictionarysCacheUtils dictionarysCacheUtils = new DictionarysCacheUtils();
        if (StringUtils.isNotBlank(tenantsStaffsInfoEntity.getZodiac())) {
            tenantsStaffsInfoEntity.setZodiac(dictionarysCacheUtils.getZodiacName(tenantsStaffsInfoEntity.getZodiac()));
        }
        if (StringUtils.isNotBlank(tenantsStaffsInfoEntity.getNativePlace())) {
            tenantsStaffsInfoEntity
                    .setNativePlace(dictionarysCacheUtils.getNativePlaceName(tenantsStaffsInfoEntity.getNativePlace()));
        }
        if (StringUtils.isNotBlank(tenantsStaffsInfoEntity.getNation())) {
            tenantsStaffsInfoEntity.setNation(dictionarysCacheUtils.getNationName(tenantsStaffsInfoEntity.getNation()));
        }
        if (StringUtils.isNotBlank(tenantsStaffsInfoEntity.getEducarion())) {
            tenantsStaffsInfoEntity
                    .setEducarion(dictionarysCacheUtils.getEducationName(tenantsStaffsInfoEntity.getEducarion()));
        }
        if (StringUtils.isNotBlank(tenantsStaffsInfoEntity.getConstellation())) {
            tenantsStaffsInfoEntity.setConstellation(
                    dictionarysCacheUtils.getConstellationName(tenantsStaffsInfoEntity.getConstellation()));
        }
        if (StringUtils.isNotBlank(tenantsStaffsInfoEntity.getBloodType())) {
            tenantsStaffsInfoEntity
                    .setBloodType(dictionarysCacheUtils.getBloodType(tenantsStaffsInfoEntity.getBloodType()));
        }
        if (StringUtils.isNotBlank(tenantsStaffsInfoEntity.getWorkStatus())) {
            tenantsStaffsInfoEntity
                    .setWorkStatus(dictionarysCacheUtils.getWorkStatusName(tenantsStaffsInfoEntity.getWorkStatus()));
        }
        if (StringUtils.isNotBlank(tenantsStaffsInfoEntity.getMaritalStatus())) {
            tenantsStaffsInfoEntity
                    .setMaritalStatus(dictionarysCacheUtils.getMaritalStatus(tenantsStaffsInfoEntity.getMaritalStatus()));
        }
        return JsonResult.success(tenantsStaffsInfoEntity);
    }

    private TenantsStaffJobInfoEntity getJobInfo(int staffId){
        TenantsStaffJobInfoEntity job = tenantsStaffJobDao.getById(staffId);
        if(job == null){
            return null;
        }
        job.setUnitValue(DictionarysCacheUtils.getServicePriceUnit(job.getUnit()));
        job.setExperienceValue(DictionarysCacheUtils.getExperienceName(job.getExperience()));

        if (StringUtils.isNotBlank(job.getExperience())) {
            job.setExperienceValue(DictionarysCacheUtils.getExperienceName(job.getExperience()));
        }
        if (StringUtils.isNotBlank(job.getLanguageFeature())) {
            job.setLanguageFeatureValue(DictionarysCacheUtils.getFeaturesStr("01", job.getLanguageFeature()));
        }
        if (StringUtils.isNotBlank(job.getCookingFeature())) {
            job.setCookingFeatureValue(DictionarysCacheUtils.getFeaturesStr("02", job.getCookingFeature()));
        }
        if (StringUtils.isNotBlank(job.getCharacerFeature())) {
            job.setCharacerFeatureValue(DictionarysCacheUtils.getFeaturesStr("03", job.getCharacerFeature()));
        }
        if (StringUtils.isNotBlank(job.getServiceProvice())) {
            job.setServiceProviceValue(DictionarysCacheUtils.getProvinceName(job.getServiceProvice()));
        }
        if (StringUtils.isNotBlank(job.getServiceCity())) {
            job.setServiceCityValue(DictionarysCacheUtils.getCityName(job.getServiceCity()));
        }
        if (StringUtils.isNotBlank(job.getServiceCounty())) {
            job.setServiceCountyValue(DictionarysCacheUtils.getCountyName(job.getServiceCounty()));
        }
        if (StringUtils.isNotBlank(job.getServiceArea())) {//添加服务地域 Baron 20170527
            String[] serviceAreas = job.getServiceArea().split(",");
            StringBuffer serviceArea = new StringBuffer();
            for (int i = 0; i < serviceAreas.length; i++) {
                serviceArea.append(serviceAreas[i] + ":" + DictionarysCacheUtils.getProvinceName(serviceAreas[i]) + ",");
            }
            job.setServiceAreaValue(serviceArea.toString().substring(0, serviceArea.toString().length() - 1));
        }

        return job;
    }

    @Override
    public JsonResult getImgVedioList(int staffId, String type) {
        //获取员工个人的视频或者照片信息
        return JsonResult.success(tenantsStaffsMediaDao.getImgVedioList(staffId, type));
    }


    // xiehui 获取阿姨证书
    @Override
    public JsonResult getStaffCertsType(int staffId) {
        // 获取员工的证件信息
        List<Map<String, Object>> map = tenantsStaffCertsDao.getSatffCertsByStatus(staffId);
        for (Map<String, Object> map2 : map) {
            String str = "";
            str = (String) map2.get("authenticatGrade");
            if (!StringUtils.isBlank(str)) {
                map2.put("authenticatGrade", str + "," + DictionarysCacheUtils.getAuthenticatGrage(str));
            } else {
                map2.put("authenticatGrade", "");
            }
        }
        return JsonResult.success(map);
    }

    @SuppressWarnings("static-access")
    @Override
    public JsonResult getStaffServiceInfo(int staffId) {
        //获取员工的服务信息
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> maps = tenantsStaffSerItemsDao.getStaffServiceInfo(staffId);
        for(Map<String, Object> map : maps){
            if (StringUtils.isNotBlank((String) map.get("serviceItemCode"))) {
                map.put("serviceItemName", DictionarysCacheUtils.getServiceTypeName((String) map.get("serviceItemCode")));
            }
            if (StringUtils.isNotBlank((String) map.get("skills"))) {
                map.put("skills", DictionarysCacheUtils.getSkillsStr((String)map.get("serviceItemCode"), (String) map.get("skills")));
            }
            if (StringUtils.isNotBlank((String) map.get("serviceNature"))) {
                map.put("serviceNature", DictionarysCacheUtils.getServiceNatureStr((String)map.get("serviceItemCode"), (String) map.get("serviceNature")));
            }
        }

        return JsonResult.success(maps);
    }


}
