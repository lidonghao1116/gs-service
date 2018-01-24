alter table orders CHANGE `ORDER_STATUS` `ORDER_STATUS` varchar(2) default null COMMENT '订单状态 01待定金 02待面试 03待尾款 04已完成 05已取消  10待商家确认取消  06待总控退款  07已结单 08结单后阿姨更换处理  09待完成(淘分享)';

ALTER TABLE `trade_records` CHANGE `TRADE_TYPE` `TRADE_TYPE` VARCHAR(2)  NOT NULL  DEFAULT ''  COMMENT '支付类型 01充值 02提现 03退款 04支付定金 05支付尾款';


CREATE TABLE `order_cancel_records` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_no` varchar(32) NOT NULL COMMENT '订单流水号',
  `tenant_id` varchar(32) NOT NULL COMMENT '用户id',
  `member_id` varchar(32) NOT NULL COMMENT '用户id',
  `order_status` varchar(2) DEFAULT NULL COMMENT '订单取消前状态',
  `cancel_reason` varchar(200) DEFAULT NULL COMMENT '取消原因',
  `cancel_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '取消时间',
  `cancel_status` varchar(2) DEFAULT NULL COMMENT '取消状态,1待处理，2待退款，3已取消, 4商家拒绝',
  `receive_content` varchar(200) DEFAULT NULL COMMENT '处理反馈',
  `receive_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '处理时间',
  `receive_tenant_user_id` int(11) DEFAULT NULL COMMENT '处理人ID',
  `receive_tenant_user_name` varchar(50) DEFAULT NULL COMMENT '处理人名称',
  `is_usable` varchar(1) DEFAULT '1' COMMENT '是否可用1是0否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单取消记录表';
