package com.fbee.modules.service.impl;

import com.fbee.modules.bean.ErrorMsg;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.page.Page;
import com.fbee.modules.core.utils.DateUtils;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.form.TenantsJobsForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.mybatis.dao.DictionarysMapper;
import com.fbee.modules.mybatis.dao.TenantsJobsMapper;
import com.fbee.modules.service.JobRecruitService;
import com.fbee.modules.service.basic.BaseService;
import com.fbee.modules.utils.DictionariesUtil;
import com.fbee.modules.utils.DictionarysCacheUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobRecruitImpl extends BaseService implements JobRecruitService {
	@Autowired
	TenantsJobsMapper tenantsJobsMapper;
	@Autowired
	DictionarysMapper dictionarysDao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public JsonResult getTenantsJobsInfoList(int tenantId, String serviceType, TenantsJobsForm tenantsJobsForm,
			int pageNumber, int pageSize) {
		
		try {
			// 获取总条数
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tenantId", tenantId);
			if (StringUtils.isNotBlank(serviceType)) {
				map.put("serviceType", serviceType);
			}
			if (StringUtils.isNotBlank(tenantsJobsForm.getServiceProvince())) {
				map.put("serviceProvince", tenantsJobsForm.getServiceProvince());
			} else {
				map.put("serviceProvince", "");
			}
			if (StringUtils.isNotBlank(tenantsJobsForm.getServiceCity())) {
				map.put("serviceCity", tenantsJobsForm.getServiceCity());
			} else {
				map.put("serviceCity", "");
			}
			String salaryQy = tenantsJobsForm.getSalary();
			if(StringUtils.isNotBlank(salaryQy)){
				System.out.println("StrMap:"+this.StrMap(map,salaryQy));
				map = this.StrMap(map,salaryQy);	
			}
			String salaryTypeQu = tenantsJobsForm.getSalaryType();
			if(StringUtils.isNotBlank(salaryTypeQu)){
				map.put("salaryType", salaryTypeQu);
			}else{
				map.put("salaryType", "");
			}
			String ageQu = tenantsJobsForm.getAge();
			if(ageQu != null){
				if(!ageQu.equals("00")){//
					map.put("age", ageQu);//
				}
			}
			Integer totalCount = this.tenantsJobsMapper.getTenantsJobsInfoCount(map);
			// 分页实体
			Page<Map> page = new Page<Map>();
			page.setPage(pageNumber);
			page.setRowNum(pageSize);
			// 最大页数判断
			int pageM = maxPage(totalCount, page.getRowNum(), page.getPage());
			if (pageM > 0) {
				page.setPage(pageM);
			}
			if (totalCount > 0) {
				map.put("offset", page.getOffset());
				map.put("pageSize", page.getRowNum());
				List<Map> resultList = tenantsJobsMapper.getTenantsJobsInfoList(map);
				for (Map result : resultList) {
					String str = "";
					// 省
					str = (String) result.get("serviceProvince");
					if (StringUtils.isNotBlank(str)) {
						result.put("serviceProvince", DictionarysCacheUtils.getProvinceName(str));
					} else {
						result.put("serviceProvince", "");
					}
					// 市
					str = (String) result.get("serviceCity");
					if (!StringUtils.isBlank(str)) {
						result.put("serviceCity", DictionarysCacheUtils.getCityName(str));
					} else {
						result.put("serviceCity", "");
					}
					// 区
					str = (String) result.get("serviceArea");
					if (!StringUtils.isBlank(str)) {
						result.put("serviceArea", DictionarysCacheUtils.getCountyName(str));
					} else {
						result.put("serviceArea", "");
					}
					String age = (String)result.get("age");
            		if (!StringUtils.isBlank(age)) {
            			if(age.equals("01") || age.equals("02") || age.equals("03") || age.equals("04")){
            				result.put("age",DictionariesUtil.getAgerange(age));
            			}
            			if(age.equals("00")){
            				result.put("age","不限");
            			}
            		}
            		String salaryType = (String)result.get("salaryType");
            		if (!StringUtils.isBlank(salaryType)) {
            			BigDecimal salary = new BigDecimal(result.get("salary").toString());
            			result.put("salary",salary.setScale(2).toString());
            			result.put("salaryTypeValue", DictionariesUtil.getSalaryRange(salaryType));
            		}
					result.put("addTime", DateUtils.formatDateTime((Date) result.get("addTime")));
				}
				page.setRows(resultList);
				page.setRecords(totalCount.longValue());
			}
			return JsonResult.success(page);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(ErrorMsg.STAFF_QUERY_ERR, e);
			return JsonResult.failure(ResultCode.DATA_ERROR);
		}
	}


	private Map StrMap(Map map,String salaryQu){
		if(salaryQu.equals("y_01")){
			map.put("cxbs", "1");
			map.put("beginSalary", 4000);
		}
		if(salaryQu.equals("y_02")){
			map.put("cxbs", "2");
			map.put("beginSalary", 4000);
			map.put("endSalary", 6999);
		}
		if(salaryQu.equals("y_03")){
			map.put("cxbs", "2");
			map.put("beginSalary", 7000);
			map.put("endSalary", 9999);
		}
		if(salaryQu.equals("y_04")){
			map.put("cxbs", "2");
			map.put("beginSalary", 10000);
			map.put("endSalary", 14999);
		}
		if(salaryQu.equals("y_05")){
			map.put("cxbs", "3");
			map.put("beginSalary", 15000);
		}
		
		if(salaryQu.equals("t_01")){
			map.put("cxbs", "1");
			map.put("beginSalary", 150);
		}
		if(salaryQu.equals("t_02")){
			map.put("cxbs", "2");
			map.put("beginSalary", 150);
			map.put("endSalary", 249);
		}
		if(salaryQu.equals("t_03")){
			map.put("cxbs", "2");
			map.put("beginSalary", "250");
			map.put("endSalary", "349");
		}
		if(salaryQu.equals("t_04")){
			map.put("cxbs", "2");
			map.put("beginSalary", 350);
			map.put("endSalary", 499);
		}
		if(salaryQu.equals("t_05")){
			map.put("cxbs", "3");
			map.put("beginSalary", 500);
		}
		
		if(salaryQu.equals("s_01")){
			map.put("cxbs", "1");
			map.put("beginSalary", 30);
		}
		if(salaryQu.equals("s_02")){
			map.put("cxbs", "2");
			map.put("beginSalary", 30);
			map.put("endSalary", 49);
		}
		if(salaryQu.equals("s_03")){
			map.put("cxbs", "2");
			map.put("beginSalary", 50);
			map.put("endSalary", 69);
		}
		if(salaryQu.equals("s_04")){
			map.put("cxbs", "2");
			map.put("beginSalary", 70);
			map.put("endSalary", 99);
		}
		if(salaryQu.equals("s_05")){
			map.put("cxbs", "3");
			map.put("beginSalary", 100);
		}
		return map;
	}
	
	@Override
	public JsonResult getServiceType() {
		return JsonResult.success(dictionarysDao.getServiceType());
	}

}
