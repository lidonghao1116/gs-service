package com.fbee.modules.service.impl;

import com.fbee.modules.bean.MemberAddressInfo;
import com.fbee.modules.mybatis.dao.MemberAddressInfoMapper;
import com.fbee.modules.service.MemberAddressService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 用户地址信息
 */
public class MemberAddressServiceImpl implements MemberAddressService {

    @Autowired
    private MemberAddressInfoMapper memberAddressInfoMapper;

    @Override
    public MemberAddressInfo add(MemberAddressInfo address, Integer memberId) {

        address.setMemberId(memberId);
        memberAddressInfoMapper.insert(address);

        return address;
    }

    @Override
    public List<MemberAddressInfo> find(Integer memberId) {
        return memberAddressInfoMapper.findList(memberId);
    }

    @Override
    public MemberAddressInfo get(Integer id, Integer memberId) {
        return memberAddressInfoMapper.getById(id, memberId);
    }

    @Override
    public void update(MemberAddressInfo address, Integer memberId) {
        address.setMemberId(memberId);
        memberAddressInfoMapper.update(address);
    }

    @Override
    public void updateDefault(Integer id, Integer memberId, String modifyAccount) {
        memberAddressInfoMapper.updateDefault(id, memberId, modifyAccount);
    }

    @Override
    public void delete(Integer id, Integer memberId, String modifyAccount) {
        memberAddressInfoMapper.delete(id, memberId, modifyAccount);
    }

    @Override
    public Integer count(Integer memberId) {
        return memberAddressInfoMapper.count(memberId);
    }

	@Override
	public MemberAddressInfo findNewOne(Integer memberId) {
		List<MemberAddressInfo> list =  memberAddressInfoMapper.findList(memberId);
		if(list.isEmpty()||list.size() == 0){
			return null;
		}
		return list.get(0);
	}
}
