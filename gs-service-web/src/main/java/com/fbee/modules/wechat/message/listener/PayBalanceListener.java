package com.fbee.modules.wechat.message.listener;

import com.fbee.modules.bean.Constants;
import com.fbee.modules.core.utils.DateUtils;
import com.fbee.modules.mybatis.dao.MembersInfoMapper;
import com.fbee.modules.mybatis.dao.OrderCustomersInfoMapper;
import com.fbee.modules.mybatis.dao.OrdersMapper;
import com.fbee.modules.mybatis.dao.TenantsAppsMapper;
import com.fbee.modules.mybatis.model.MembersInfo;
import com.fbee.modules.mybatis.model.OrderCustomersInfo;
import com.fbee.modules.mybatis.model.Orders;
import com.fbee.modules.mybatis.model.TenantsApps;
import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.utils.DictionarysCacheUtils;
import com.fbee.modules.wechat.message.config.WechatConfig;
import com.fbee.modules.wechat.message.model.PayBalanceModel;
import com.fbee.modules.wechat.message.model.PushModel;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayBalanceListener extends PushWechatListener {

    private final static Logger log = LoggerFactory.getLogger("messageLogger");

    private JedisTemplate mq = JedisUtils.getJedisMessage();

    @Autowired
    private MembersInfoMapper membersInfoMapper;

    @Autowired
    private TenantsAppsMapper tenantsAppsMapper;

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderCustomersInfoMapper orderCustomersInfoMapper;


    /**
     * 支付尾款提醒
     *
     * @param channel
     * @param message
     */
    @Override
    public void onMessage(String channel, String message) {
        log.info(String.format("[listener] pay balance: received messageKey [%s] on channel [%s]", message, channel));
        if (StringUtils.isBlank(message)) {
            log.info("null? "+ message);
            return;
        }
        try {
                String orderNo = message;//mq.brpop(60, WechatConfig.Queue.ORDER_PAY_BALANCE_C.getQueue());
                Orders order = ordersMapper.selectByPrimaryKey(orderNo);
                if(order == null){
                    return;
                }
                OrderCustomersInfo cust = orderCustomersInfoMapper.selectByPrimaryKey(orderNo);
                TenantsApps app = tenantsAppsMapper.selectByPrimaryKey(Integer.valueOf(order.getTenantId()));

                PayBalanceModel model = new PayBalanceModel();
                model.setTitle("尊敬的客户您好，您申请的服务已经确认。\n");
                model.setMemberId(order.getMemberId());
                model.setTenantUserId(order.getUserId());
                model.setOrderNo(orderNo);
                model.setServiceTime(String.format("%s至%s", DateUtils.formatDate(cust.getServiceStart()), DateUtils.formatDate(cust.getServiceEnd())));
                model.setOrderAmount(order.getOrderBalance().setScale(2).toString());
                model.setServiceAddress(String.format("%s%s%s%s",DictionarysCacheUtils.getProvinceName(cust.getServiceProvice()),DictionarysCacheUtils.getCityName(cust.getServiceCity()),DictionarysCacheUtils.getCountyName(cust.getServiceCounty()) ,cust.getServiceAddress()));
                model.setDescription(String.format("%s-%s", DictionarysCacheUtils.getServiceTypeName(order.getServiceItemCode()),DictionarysCacheUtils.getServiceNatureStr(order.getServiceItemCode(), cust.getServiceType())));
                model.setRemark("\n请点击查看服务详情。");

                MembersInfo mem = membersInfoMapper.selectByPrimaryKey(model.getMemberId());
                if(mem == null || StringUtils.isBlank(mem.getOpenid())){
                    return;
                }

                PushModel data = new PushModel();
                data.setTouser(mem.getOpenid());
                data.setTemplateId(WechatConfig.Template.ORDER_PAY_BALANCE_TEMPLATE.getId());
                data.setUrl(Constants.MOBILE_HOST_NAME + "/" + app.getDomain() + "/orderDetail.html?orderNo="+model.getOrderNo());
                data.addData("first", model.getTitle())
                        .addData("keyword1", model.getDescription(), "#173177")
                        .addData("keyword2", model.getServiceTime(), "#173177")
                        .addData("keyword3", model.getServiceAddress(), "#173177")
                        .addData("remark", model.getRemark());

                push(data);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }


}
