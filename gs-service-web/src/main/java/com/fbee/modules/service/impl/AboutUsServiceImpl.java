package com.fbee.modules.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.fbee.modules.mybatis.dao.TenantsAboutUsMapper;
import com.fbee.modules.service.AboutUsService;
import com.fbee.modules.service.basic.BaseService;

public class AboutUsServiceImpl extends BaseService implements AboutUsService {

	@Autowired
	TenantsAboutUsMapper tenantsAboutUsMapper;
	
	/**
	 * 关于我们详细信息
	 */
	@Override
	public Map<String, Object> getUsInfo(int tenantId) {
		return tenantsAboutUsMapper.getAboutUsInfo(tenantId);
	}

}
