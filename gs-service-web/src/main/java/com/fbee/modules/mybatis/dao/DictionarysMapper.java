package com.fbee.modules.mybatis.dao;

import java.util.List;
import java.util.Map;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.DictionarysEntity;

@MyBatisDao
public interface DictionarysMapper extends CrudDao<DictionarysEntity>{
	/**
	 * 获取服务工种
	 * @return
	 */
	List<Map<String, String>> getServiceType();
	/**
	 * 获取某个typeCode的value
	 * @param typeCode
	 * @param code
	 * @return
	 */
	String getValue(String typeCode,String code);

	/**
	 * 根据级别获取字典型参数值
	 * @return
	 */
	List<DictionarysEntity> getServiceTypeByLevel(String level);
}