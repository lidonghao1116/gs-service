package com.fbee.modules.service;

import com.fbee.modules.bean.MemberAddressInfo;

import java.util.List;

/**
 * 用户地址信息
 */
public interface MemberAddressService {


    MemberAddressInfo add(MemberAddressInfo address, Integer memberId);

    List<MemberAddressInfo> find(Integer memberId);

    MemberAddressInfo get(Integer id, Integer memberId);

    void update(MemberAddressInfo address, Integer memberId);

    void updateDefault(Integer id, Integer memberId, String modifyAccount);

    void delete(Integer id, Integer memberId, String modifyAccount);

    Integer count(Integer memberId);
    
    MemberAddressInfo findNewOne(Integer memberId);
}
