package com.fbee.modules.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.mybatis.dao.TenantsRecommendMapper;
import com.fbee.modules.service.StaffsService;
import com.fbee.modules.service.basic.BaseService;
import com.fbee.modules.utils.DictionarysCacheUtils;

/**
* @Description：员工信息实现类
* @author fry
* @date 2017年1月20日 下午2:54:58
* 
*/
public class StaffsServiceImpl extends BaseService implements StaffsService {
	@Autowired
	TenantsRecommendMapper tenantsRecommendMapper;
	
	/**
	 * 根据租户id获取推荐阿姨信息
	 * @param tenantId
	 * @return
	 */
	@Override
	@Transactional
	public List<Map<String, String>> getRecomMendListByTenantId(Integer tenantId) {
		// TODO Auto-generated method stub
		List<Map<String, String>> list = tenantsRecommendMapper.getRecomMendListByTenantId(tenantId);
		
		for(Map<String, String> map:list){
			if (!StringUtils.isBlank((String)map.get("zodiac"))) {
				map.put("zodiac", DictionarysCacheUtils.getZodiacName((String)map.get("zodiac")));
			}
			if (!StringUtils.isBlank((String)map.get("nativePlace"))) {
				map.put("nativePlace", DictionarysCacheUtils.getNativePlaceName((String)map.get("nativePlace")));
			}
			if (!StringUtils.isBlank((String)map.get("educarion"))) {
				map.put("educarion", DictionarysCacheUtils.getEducationName((String)map.get("educarion")));
			}
			if (!StringUtils.isBlank((String)map.get("constellation"))) {
				map.put("constellation", DictionarysCacheUtils.getConstellationName((String)map.get("constellation")));
			}
			if (!StringUtils.isBlank((String)map.get("workStatus"))) {
				map.put("workStatus", DictionarysCacheUtils.getWorkStatusName((String)map.get("workStatus")));
			}
			if (!StringUtils.isBlank((String)map.get("experience"))) {
				map.put("experience", DictionarysCacheUtils.getExperienceName((String)map.get("experience")));
			}
			if (!StringUtils.isBlank((String)map.get("characerFeature"))) {
				map.put("characerFeature", DictionarysCacheUtils.getFeaturesStr("03", (String)map.get("characerFeature")));
			}
			if (!StringUtils.isBlank((String)map.get("unit"))) {
				map.put("unitValue", DictionarysCacheUtils.getServicePriceUnit(map.get("unit")));
			}
			//if (!StringUtils.isBlank((String)map.get("sex"))) {
//				map.put("sex", DictionarysCacheUtils.getZodiacName((String)map.get("sex")));
//			}
		}
		//Collections.reverse(list);
		//System.out.println("***********************"+list+"************************");
		if(list.size()>0){                                      
			return list;
		}
		return list;
	}

}

