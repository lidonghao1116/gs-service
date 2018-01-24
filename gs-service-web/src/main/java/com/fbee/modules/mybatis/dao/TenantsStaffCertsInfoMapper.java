package com.fbee.modules.mybatis.dao;

import java.util.List;
import java.util.Map;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.mybatis.entity.TenantsStaffCertsInfoEntity;

@MyBatisDao
public interface TenantsStaffCertsInfoMapper extends CrudDao<TenantsStaffCertsInfoEntity>{
	List<Map> getStaffCertsType(int staffId);
	/**
	 * 获取阿姨的所有证书信息
	 * @param staffId
	 * @return
	 */
	List<TenantsStaffCertsInfoEntity> getSatffAllCerts(Integer staffId);
	
	/**
	 * 获取阿姨的通过的证书信息
	 * 
	 * @param staffId
	 * @return
	 * @author xiehui
	 */
	List<Map<String, Object>> getSatffCertsByStatus(Integer staffId);
	 /**
     * 员工的所有证书
     * @param integer
     * @return
     */
	List<Map<String, Object>> reserveOrderDetails(Map<String,Object> map);
}