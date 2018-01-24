package com.fbee.modules.mybatis.dao;

import java.util.List;
import java.util.Map;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.TenantsStaffsInfoEntity;
import com.fbee.modules.mybatis.model.TenantsStaffsInfo;

@MyBatisDao
public interface TenantsStaffsInfoMapper extends CrudDao<TenantsStaffsInfoEntity>{

	TenantsStaffsInfoEntity selectByPrimaryKey2(Integer staffId);
	
	void updateByPrimaryKey(TenantsStaffsInfo tenantsStaffsInfo);
	/**
	 * @param map
	 * @return
	 */
	Integer getStaffQueryCount(Map<Object, Object> map);

	/**
	 * @param map
	 * @return
	 */
	List<TenantsStaffsInfoEntity> getStaffQueryList(Map<Object, Object> map);

	/**
	 * @param param
	 * @return
	 */
	TenantsStaffsInfoEntity getStaffInfo(TenantsStaffsInfoEntity param);
	
	/**
	 * 获取推荐阿姨列表
	 * @param tenantId
	 * @return
	 */
	List<Map<String, String>> selectRecommendList(Integer tenantId);
	
	/**
	 * 获取阿姨列表
	 * @param tenantId
	 * @return
	 */
	List<Map<String, String>> selectStaffInfoList(Integer tenantId, Integer value, Integer pageSize);
	
	/**
	 * 获取阿姨个人信息
	 * @param tenantId
	 * @param staffId
	 * @return
	 */
	Map<String, String> getStaffInfoByStaffId(Integer tenantId, Integer staffId);
	
	/**
	 * 模糊查询获取阿姨列表
	 * @param tenantId
	 * @param staffName
	 * @return
	 */
	List<Map<String, String>> getStaffInfoLikeStaffName(Integer tenantId, String staffName);
	/**
	 * 获取所有阿姨的个人信息
	 * @param map
	 * @return
	 */
	List<Map> getStaffPersonInfo(Map map);
	/**
	 * 获取某个租户某个服务工种的阿姨总数
	 * @param map
	 * @return
	 */
	int getStaffCountByTenantId(Map map);
	
	/**
	 * 根据id获取阿姨信息
	 * @param staffId
	 * @return
	 */
	Map<String, Object> getStaffsInfo(Integer staffId);
	
	/**
	 * 获取阿姨详情
	 * @param staffid
	 * @return
	 */
	Map<String, Object> reserveOrderDetails(Integer staffid);

	TenantsStaffsInfoEntity selectByPrimaryKey(Integer staffId);
}