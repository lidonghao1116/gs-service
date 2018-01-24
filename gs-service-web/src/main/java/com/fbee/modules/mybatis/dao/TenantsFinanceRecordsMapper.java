package com.fbee.modules.mybatis.dao;

import java.util.Map;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.TenantsFinanceRecordsEntity;
import com.fbee.modules.mybatis.model.TenantsFinanceRecords;

@MyBatisDao
public interface TenantsFinanceRecordsMapper extends CrudDao<TenantsFinanceRecordsEntity>{

	int deleteByPrimaryKey(String inOutNo);

    int insert(TenantsFinanceRecords record);

    int insertSelective(TenantsFinanceRecords record);
	    int updateByPrimaryKey(TenantsFinanceRecords record);
	    /**
	     * 根据tenantFinanceRecords 中的payNo查询出inOutNo
	     * @param payNo
	     * @return
	     */
	    Map<String , Object> getInOutNo(String payNo);
	    /**
	     * 根据支付流水号更新该记录状态为 “不可用”
	     * @param payNo
	     * @return
	     */
	    int updateByPayNo(String payNo);
	    
	   
    TenantsFinanceRecords selectByPrimaryKey(String inOutNo);

    int updateByPrimaryKeySelective(TenantsFinanceRecords record);

	
}