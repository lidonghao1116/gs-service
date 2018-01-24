package com.fbee.modules.bean;

import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.utils.DictionarysCacheUtils;

import java.io.Serializable;

public class MemberAddressInfo implements Serializable{

    private Integer id;

    /**
     * 用户ID
     */
    private Integer memberId;
    /**
     * //地址类型：家／公司／自定义
     */
    private String  type ;
    /**
     * //用户姓名
     */
    private String  userName;
    /**
     * //电话
     */
    private String  phone;
    /**
     *  //邮政编码
     */
    private String  zipCode;
    /**
     * 国家
     */
    private String  country;
    /**
     * 省
     */
    private String  province;
    /**
     * 市
     */
    private String  city;
    /**
     * 区
     */
    private String  district ;

    /**
     * 地址信息
     */
    private String  address;

    /**
     * 区域
     */
    private String  area;
    /**
     * 是否可用
     */
    private String  isUsable;
    /**
     * 是否默认
     */
    private String  isDefault;
    private String  addTime;
    private String  modifyTime;
    private String  addAccount;
    private String  modifyAccount;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }
    public String getProvinceValue() {
        if(StringUtils.isNotBlank(province)){
            return DictionarysCacheUtils.getProvinceName(province);
        }
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }
    public String getCityValue() {
        if(StringUtils.isNotBlank(city)){
            return DictionarysCacheUtils.getCityName(city);
        }
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }
    public String getDistrictValue() {
        if(StringUtils.isNotBlank(district)){
            return DictionarysCacheUtils.getCountyName(district);
        }
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getIsUsable() {
        return isUsable;
    }

    public void setIsUsable(String isUsable) {
        this.isUsable = isUsable;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getAddAccount() {
        return addAccount;
    }

    public void setAddAccount(String addAccount) {
        this.addAccount = addAccount;
    }

    public String getModifyAccount() {
        return modifyAccount;
    }

    public void setModifyAccount(String modifyAccount) {
        this.modifyAccount = modifyAccount;
    }
}
