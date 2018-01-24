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
import com.fbee.modules.utils.JsonUtils;
import com.fbee.modules.wechat.message.config.WechatConfig;
import com.fbee.modules.wechat.message.model.PushModel;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CancelOrderListener extends PushWechatListener {

    private final static Logger log = LoggerFactory.getLogger("messageLogger");

    private JedisTemplate mq = JedisUtils.getJedisMessage();

    @Autowired
    private TenantsAppsMapper tenantsAppsMapper;
    @Autowired
    private MembersInfoMapper membersInfoMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderCustomersInfoMapper orderCustomersInfoMapper;

    /**
     * 取消订单
     *
     * @param channel
     * @param message
     */
    @Override
    public void onMessage(String channel, String message) {
        log.info(String.format("[listener] cancel order: received messageKey [%s] on channel [%s]", message, channel));
        if (StringUtils.isBlank(message)) {
            return;
        }
        try {
            //等待业务处理事务提交
            Thread.sleep(1000);
            String orderNo = message;
            Orders order = ordersMapper.selectByPrimaryKey(orderNo);
            OrderCustomersInfo orderCustomersInfo = orderCustomersInfoMapper.selectByPrimaryKey(orderNo);
            MembersInfo mem = membersInfoMapper.selectByPrimaryKey(order.getMemberId());

            String serviceType = String.format("%s-%s", DictionarysCacheUtils.getServiceTypeName(order.getServiceItemCode()), DictionarysCacheUtils.getServiceNatureStr(order.getServiceItemCode(), orderCustomersInfo.getServiceType()));
            TenantsApps app = tenantsAppsMapper.selectByPrimaryKey(Integer.valueOf(order.getTenantId()));

            PushModel data = new PushModel();
            data.setTouser(mem.getOpenid());
            data.setTemplateId(WechatConfig.Template.BUSINESS_TEMPLATE.getId());
            data.setUrl(Constants.MOBILE_HOST_NAME + "/" + app.getDomain() + "/orderDetail.html?orderNo=" + orderNo);
            data.addData("first", "您的订单已取消通知\n")
                    .addData("keyword1", serviceType, "#173177")
                    .addData("keyword2", "已取消", "#173177")
                    .addData("keyword3", order.getCancleReason(), "#173177")
                    .addData("remark", "\n如有疑问，可点击查看详情。");

            push(data);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }


}
