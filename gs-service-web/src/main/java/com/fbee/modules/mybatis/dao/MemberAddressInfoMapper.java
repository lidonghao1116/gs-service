package com.fbee.modules.mybatis.dao;

import com.fbee.modules.bean.MemberAddressInfo;
import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface MemberAddressInfoMapper extends CrudDao<MemberAddressInfo> {

	/**
	 * 查询用户所有地址
	 * @param memberId 用户id
	 * @return
	 */
	List<MemberAddressInfo> findList(Integer memberId);

	/**
	 * 查询用户有效地址数量
	 * @param memberId 用户id
	 * @return
	 */
	Integer count(Integer memberId);

	/**
	 * 设置用户默认地址
	 * @param id
	 * @param memberId
	 */
	void updateDefault(@Param("id") Integer id, @Param("memberId") Integer memberId, @Param("modifyAccount")String modifyAccount);

	/**
	 * 查询用户单个地址详情
	 * @param id
	 * @param memberId
	 * @return
	 */
	MemberAddressInfo getById(@Param("id") Integer id, @Param("memberId") Integer memberId);

	/**
	 * 逻辑删除用户地址
	 * @param id
	 * @param memberId
	 */
	void delete(@Param("id") Integer id, @Param("memberId") Integer memberId, @Param("modifyAccount")String modifyAccount);
}