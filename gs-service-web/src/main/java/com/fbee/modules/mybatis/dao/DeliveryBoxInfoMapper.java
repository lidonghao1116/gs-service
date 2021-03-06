package com.fbee.modules.mybatis.dao;

import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.model.DeliveryBoxInfo;
import com.fbee.modules.mybatis.model.DeliveryBoxInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
@MyBatisDao
public interface DeliveryBoxInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table delivery_box_info
     *
     * @mbggenerated Mon Mar 27 13:57:28 CST 2017
     */
    int countByExample(DeliveryBoxInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table delivery_box_info
     *
     * @mbggenerated Mon Mar 27 13:57:28 CST 2017
     */
    int deleteByExample(DeliveryBoxInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table delivery_box_info
     *
     * @mbggenerated Mon Mar 27 13:57:28 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table delivery_box_info
     *
     * @mbggenerated Mon Mar 27 13:57:28 CST 2017
     */
    int insert(DeliveryBoxInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table delivery_box_info
     *
     * @mbggenerated Mon Mar 27 13:57:28 CST 2017
     */
    int insertSelective(DeliveryBoxInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table delivery_box_info
     *
     * @mbggenerated Mon Mar 27 13:57:28 CST 2017
     */
    List<DeliveryBoxInfo> selectByExample(DeliveryBoxInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table delivery_box_info
     *
     * @mbggenerated Mon Mar 27 13:57:28 CST 2017
     */
    DeliveryBoxInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table delivery_box_info
     *
     * @mbggenerated Mon Mar 27 13:57:28 CST 2017
     */
    int updateByExampleSelective(@Param("record") DeliveryBoxInfo record, @Param("example") DeliveryBoxInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table delivery_box_info
     *
     * @mbggenerated Mon Mar 27 13:57:28 CST 2017
     */
    int updateByExample(@Param("record") DeliveryBoxInfo record, @Param("example") DeliveryBoxInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table delivery_box_info
     *
     * @mbggenerated Mon Mar 27 13:57:28 CST 2017
     */
    int updateByPrimaryKeySelective(DeliveryBoxInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table delivery_box_info
     *
     * @mbggenerated Mon Mar 27 13:57:28 CST 2017
     */
    int updateByPrimaryKey(DeliveryBoxInfo record);
}