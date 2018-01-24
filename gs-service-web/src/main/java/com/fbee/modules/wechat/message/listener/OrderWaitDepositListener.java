package com.fbee.modules.wechat.message.listener;

import com.fbee.modules.bean.Constants;
import com.fbee.modules.mybatis.dao.MembersInfoMapper;
import com.fbee.modules.mybatis.dao.TenantsAppsMapper;
import com.fbee.modules.mybatis.model.MembersInfo;
import com.fbee.modules.mybatis.model.TenantsApps;
import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.utils.JsonUtils;
import com.fbee.modules.wechat.message.config.WechatConfig;
import com.fbee.modules.wechat.message.model.CreatePayOrderModel;
import com.fbee.modules.wechat.message.model.PushModel;
import net.sf.json.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderWaitDepositListener extends PushWechatListener {

    private final static Logger log = LoggerFactory.getLogger("messageLogger");

    private JedisTemplate mq = JedisUtils.getJedisMessage();

    @Autowired
    private MembersInfoMapper membersInfoMapper;

    @Autowired
    private TenantsAppsMapper tenantsAppsMapper;

    /**
     * 新支付订单/待支付定金  提醒
     *
     * @param channel
     * @param messageKey
     */
    @Override
    public void onMessage(String channel, String messageKey) {
        log.info(String.format("[listener] order wait deposit: received messageKey [%s] on channel [%s]", messageKey, channel));
        if (StringUtils.isBlank(messageKey)) {
            log.info("messagekey == null");
            return;
        }
        try {
            while (true) {
                String message = mq.brpop(60, WechatConfig.Queue.NEW_PAY_ORDER_C.getQueue());
                log.info("message:"+ JsonUtils.toJson(message));
                CreatePayOrderModel model = JsonUtils.fromJson(message, CreatePayOrderModel.class);
                log.info("model "+ JsonUtils.toJson(model));
                if (StringUtils.isBlank(message) || model == null) {
                    log.info("model is null");
                    return;
                }
                MembersInfo mem = membersInfoMapper.selectByPrimaryKey(model.getMemberId());
                log.info("mem:"+JsonUtils.toJson(mem));
                if(mem == null || StringUtils.isBlank(mem.getOpenid())){
                    continue;
                }

                TenantsApps app = tenantsAppsMapper.selectByPrimaryKey(Integer.valueOf(model.getTenantId()));
                PushModel data = new PushModel();
                data.setTouser(mem.getOpenid());
                data.setTemplateId(WechatConfig.Template.ORDER_PAY_DEPOSIT_TEMPLATE.getId());
                data.setUrl(Constants.MOBILE_HOST_NAME + "/" + app.getDomain() + "/orderDetail.html?orderNo="+model.getOrderNo());
                data.addData("first", model.getTitle()+"\n", null)
                        .addData("keyword1", app.getWebsiteName(), "#173177")
                        .addData("keyword2", model.getOrderNo(), "#173177")
                        .addData("keyword3", model.getOrderType(), "#173177")
                        .addData("keyword4", model.getDepositAmount(), "#173177")
                        .addData("keyword5", model.getStatus(), "#173177")
                        .addData("remark", "\n"+model.getRemark(), null);

                push(data);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }


}
