package com.fbee.modules.wechat.message;

import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.wechat.message.config.WechatConfig;
import com.fbee.modules.wechat.message.listener.*;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class MessageApplication implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(MessageApplication.class);

    private JedisTemplate redis = JedisUtils.getJedisTemplate();

    @Autowired
    private OrderWaitDepositListener orderWaitDepositListener;
    @Autowired
    private OrderWaitBalanceListener orderWaitBalanceListener;
    @Autowired
    private PayBalanceListener payBalanceListener;
    @Autowired
    private CancelOrderListener cancelOrderListener;

    private ExecutorService exePools = new ThreadPoolExecutor(10, 10, 0L,
            TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(),
            new ThreadFactoryBuilder().setNameFormat("thread-message-%d").build(), new ThreadPoolExecutor.AbortPolicy());

    /**
     * 监听广播
     */
    public void onListener() {

        log.info("message listener start ..");

        exePools.execute(new Runnable() {
            @Override
            public void run() {
                log.info("listener wait pay balance subscribe ..");
                redis.subscribe(orderWaitBalanceListener, WechatConfig.Channel.ORDER_WAIT_PAY_BALANCE.getChannel());
            }
        });
        exePools.execute(new Runnable() {
            @Override
            public void run() {
                log.info("listener new pay order subscribe ..");
                redis.subscribe(orderWaitDepositListener, WechatConfig.Channel.NEW_PAY_ORDER.getChannel());
            }
        });
        exePools.execute(new Runnable() {
            @Override
            public void run() {
                log.info("listener pay balance subscribe ..");
                redis.subscribe(payBalanceListener, WechatConfig.Channel.ORDER_PAY_BALANCE.getChannel());
            }
        });
        exePools.execute(new Runnable() {
            @Override
            public void run() {
                log.info("listener cancel order with deposit subscribe ..");
                redis.subscribe(cancelOrderListener, WechatConfig.Channel.CANCEL_ORDER.getChannel());
            }
        });

    }


    @Override
    public void afterPropertiesSet() throws Exception {
        onListener();
    }
}
