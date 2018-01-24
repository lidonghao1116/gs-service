package com.fbee.modules.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.mybatis.dao.TenantsStaffCertsInfoMapper;
import com.fbee.modules.mybatis.dao.TenantsStaffSerItemsMapper;
import com.fbee.modules.mybatis.dao.TenantsStaffsInfoMapper;
import com.fbee.modules.service.StaffService;
import com.fbee.modules.service.basic.BaseService;

public class StaffServiceImpl extends BaseService implements StaffService {

	@Autowired
	TenantsStaffCertsInfoMapper tenantsStaffCertsInfoDao;
	
	@Autowired
	TenantsStaffSerItemsMapper tenantsStaffSerItemsDao;
	
	@Autowired
	TenantsStaffsInfoMapper tenantsStaffsInfoDao;
	
	/**
	 * 阿姨详情
	 */
	@Override
	public JsonResult getStaffInfo(String staffId,String tenantId) {
		Map<String, Object> resultmap = new HashMap<String, Object>();
		Map<String, Object> staffmap = tenantsStaffsInfoDao.reserveOrderDetails(Integer.parseInt(staffId));//阿姨基本信息
		resultmap.put("staffmap", staffmap);
		Map<String, Object> parammap = new HashMap<String, Object>();
		parammap.put("staffid", Integer.parseInt(staffId));
		parammap.put("tenantid", Integer.parseInt(tenantId));
		List<Map<String, Object>> itemsmap = tenantsStaffSerItemsDao.reserveOrderDetails(parammap);//阿姨服务信息
		resultmap.put("itemsmap", itemsmap);
		List<Map<String, Object>> sertlist = tenantsStaffCertsInfoDao.reserveOrderDetails(parammap);//阿姨证件信息
		resultmap.put("sertlist", sertlist);
		return JsonResult.success(resultmap);
	}

}
