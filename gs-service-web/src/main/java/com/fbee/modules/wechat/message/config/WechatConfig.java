package com.fbee.modules.wechat.message.config;

import com.fbee.modules.bean.Constants;

public interface WechatConfig {

    enum Channel {
        JOB_PUBLISH("jiacer.channel.job.publish",  "职位发布"),

        RESUME_APPLY("jiacer.channel.resume.apply",  "简历投递"),
        RESUME_CHECK("jiacer.channel.resume.check",  "简历审核"),
        RESERVE_ORDER("jiacer.channel.reserve.order", "预约新订单"),

        NEW_PAY_ORDER("jiacer.channel.new.pay.order", "新支付订单"),
        ORDER_PAY_DEPOSIT("jiacer.channel.order.pay.deposit", "订单支付定金"),
        ORDER_WAIT_PAY_BALANCE("jiacer.channel.order.wait.pay.balance", "订单待支付尾款"),

        ORDER_PAY_BALANCE("jiacer.channel.order.pay.balance",  "订单支付尾款"),

        FINISH_JOB("jiacer.channel.finish.job",  "合作抢单完成"),
        CANCEL_JOB("jiacer.channel.cancel.job",  "合作抢单取消"),

        CANCEL_ORDER("jiacer.channel.cancel.order", "取消订单");

        private String channel;
        private String description;

        Channel(String channel, String description) {
            this.channel = channel;
            this.description = description;
        }

        public String getChannel() {
            return channel;
        }

    }
    enum Queue {
        JOB_PUBLISH_B( "jiacer.queue.b.job.recommend", "职位发布"),
        JOB_PUBLISH_MS( "jiacer.queue.edu.job.recommend", "职位发布"),

        RESUME_APPLY_B( "jiacer.queue.b.resume.apply", "简历投递"),
        RESUME_CHECK_B( "jiacer.queue.b.resume.check", "简历审核"),
        RESERVE_ORDER_B( "jiacer.queue.b.reserve.order", "预约新订单"),

        NEW_PAY_ORDER_C( "jiacer.queue.c.new.pay.order", "创建新订单"),

        ORDER_PAY_DEPOSIT_B( "jiacer.queue.b.order.pay.deposit", "订单支付定金"),
        ORDER_PAY_DEPOSIT_C( "jiacer.queue.c.order.pay.deposit", "订单支付定金"),

        ORDER_PAY_BALANCE_B("jiacer.queue.b.order.pay.balance", "订单支付尾款"),
        ORDER_PAY_BALANCE_C( "jiacer.queue.c.order.pay.balance", "订单支付尾款"),

        CANCEL_ORDER_B( "jiacer.queue.b.cancel.order", "取消订单"),
        CANCEL_ORDER_C( "jiacer.queue.c.cancel.order", "取消订单");

        private String queue;
        private String description;

        Queue( String queue, String description) {
            this.queue = queue;
            this.description = description;
        }

        public String getQueue() {
            return queue;
        }

    }
    enum Api {
        GET_TOKEN("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s"),
        POST_MESSAGE("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s"),
        POST_INDUSTRY("https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=%s");

        private String value;

        Api(String value) {
            this.value = value;
        }

        public String getValue(String... param) {
            return String.format(value, param);
        }
    }

    enum Template{

        //prod
        ORDER_WAIT_DEPOSIT_TEMPLATE("4KS0MKaBGiDaANKKOgkJhWpzOkQDhqyZkggc83hAbJA",Constants.MOBILE_HOST_NAME+"/",  "生成订单/待支付定金提醒"),
        ORDER_PAY_DEPOSIT_TEMPLATE("4KS0MKaBGiDaANKKOgkJhWpzOkQDhqyZkggc83hAbJA",Constants.MOBILE_HOST_NAME+"/",  "客户支付定金完成提醒"),
        ORDER_PAY_BALANCE_TEMPLATE("kfo6dz8a6oI4gP5lifCZHgoa68cSDAh-9rRdh8CgGwc",Constants.MOBILE_HOST_NAME+"/",  "客户支付尾款完成提醒"),
        BUSINESS_TEMPLATE("WNY0iQFd61SCZRD0YrIvpr4bSmG0RE5DgiezD9w3DS0",Constants.MOBILE_HOST_NAME+"/",  "业务模版：合作抢单完成/取消，订单取消");

        //test
//        ORDER_WAIT_DEPOSIT_TEMPLATE("-XqmZkK3LPaFLuzgK3oIZIO-DiDw76a-U4NAGIQYZzI",Constants.MOBILE_HOST_NAME+"/",  "生成订单/待支付定金提醒"),
//        ORDER_PAY_DEPOSIT_TEMPLATE("-XqmZkK3LPaFLuzgK3oIZIO-DiDw76a-U4NAGIQYZzI",Constants.MOBILE_HOST_NAME+"/",  "客户支付定金完成提醒"),
//        ORDER_PAY_BALANCE_TEMPLATE("VrH9Q_vESrklbZ8TLyEXUx2K8k5cbwqm-O4q53Q9WhM",Constants.MOBILE_HOST_NAME+"/",  "客户支付尾款完成提醒"),
//        BUSINESS_TEMPLATE("jPumFtNi-jNYz44XsUHOqRV1sNFAPfQnPF5EALfiPiQ",Constants.MOBILE_HOST_NAME+"/",  "业务模版：合作抢单完成/取消，订单取消");

    	//uat
//	      ORDER_WAIT_DEPOSIT_TEMPLATE("lVqx8uJpl9WAC6zY4UCRvXaoEiuJtbF9yDVsDbGyttQ",Constants.MOBILE_HOST_NAME+"/",  "生成订单/待支付定金提醒"),
//	      ORDER_PAY_DEPOSIT_TEMPLATE("lVqx8uJpl9WAC6zY4UCRvXaoEiuJtbF9yDVsDbGyttQ",Constants.MOBILE_HOST_NAME+"/",  "客户支付定金完成提醒"),
//	      ORDER_PAY_BALANCE_TEMPLATE("StfJ7nziel8REvFjENzRmt6blC0BgJAAut0q4AxJqf8",Constants.MOBILE_HOST_NAME+"/",  "客户支付尾款完成提醒"),
//	      BUSINESS_TEMPLATE("7prTrsJGE77nz6sW5j3egdTnT0IJR1J1FitP9CvSoDE",Constants.MOBILE_HOST_NAME+"/",  "业务模版：合作抢单完成/取消，订单取消");

        private String id;
        private String url;
        private String description;

        Template(String id, String url, String description) {
            this.id = id;
            this.url = url;
            this.description = description;
        }

        public String getId() {
            return id;
        }
        public String getUrl(String ... param) {
            return String.format(url, param);
        }
    }

}
