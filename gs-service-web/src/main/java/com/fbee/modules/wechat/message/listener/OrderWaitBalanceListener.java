package com.fbee.modules.wechat.message.listener;

import com.fbee.modules.bean.Constants;
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
import com.fbee.modules.wechat.message.model.PayDepositModel;
import com.fbee.modules.wechat.message.model.PushModel;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderWaitBalanceListener extends PushWechatListener {

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
     * 支付定金/带支付尾款 通知
     *
     * @param channel
     * @param messageKey
     */
    @Override
    public void onMessage(String channel, String messageKey) {
        log.info(String.format("[listener] order wait balance - onMessage: received messageKey [%s] on channel [%s]", messageKey, channel));
        if (StringUtils.isBlank(messageKey)) {
            return;
        }
        try {
            String orderNo = messageKey;//mq.brpop(60, WechatConfig.Queue.ORDER_PAY_DEPOSIT_C.getQueue());

            Orders order = ordersMapper.selectByPrimaryKey(orderNo);
            if (order == null) {
                return;
            }
            OrderCustomersInfo cust = orderCustomersInfoMapper.selectByPrimaryKey(orderNo);

            PayDepositModel model = new PayDepositModel();
            model.setMemberId(order.getMemberId());
            model.setTenantUserId(order.getUserId());
            model.setOrderNo(orderNo);
            model.setOrderAmount(order.getOrderBalance().setScale(2).toString());
            model.setTitle("您有一笔订单待完成支付，请及时处理");
            model.setOrderContent(String.format("%s-%s", DictionarysCacheUtils.getServiceTypeName(order.getServiceItemCode()), DictionarysCacheUtils.getServiceNatureStr(order.getServiceItemCode(), cust.getServiceType())));
            model.setRemark("请在48小时内完成支付，逾期将自动取消，点击查看详情。");

            MembersInfo mem = membersInfoMapper.selectByPrimaryKey(model.getMemberId());
            if (mem == null || StringUtils.isBlank(mem.getOpenid())) {
                return;
            }

            TenantsApps app = tenantsAppsMapper.selectByPrimaryKey(Integer.valueOf(order.getTenantId()));

            PushModel data = new PushModel();
            data.setTouser(mem.getOpenid());
            data.setTemplateId(WechatConfig.Template.ORDER_PAY_DEPOSIT_TEMPLATE.getId());
            data.setUrl(Constants.MOBILE_HOST_NAME + "/" + app.getDomain() + "/orderDetail.html?orderNo=" + orderNo);
            data.addData("first", model.getTitle() + "\n", null)
                    .addData("keyword1", app.getWebsiteName(), "#173177")
                    .addData("keyword2", model.getOrderNo(), "#173177")
                    .addData("keyword3", model.getOrderContent(), "#173177")
                    .addData("keyword4", model.getOrderAmount(), "#173177")
                    .addData("keyword5", "待支付尾款", "#173177")
                    .addData("remark", "\n" + model.getRemark(), null);

            push(data);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }


}
