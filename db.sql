create database if not exists db_city;
drop table if exists db_city.t_city;
create table db_city.t_city
(
    id           int unsigned primary key auto_increment,
    name         varchar(32)      not null comment '城市名',
    pinyin       varchar(64) comment '城市名拼音',
    hotel_number int unsigned default 0 comment '城市的酒店数量',
    create_time  timestamp        not null comment '创建时间',
    status       tinyint unsigned not null comment '状态，0=正常、1=删除'
) engine = InnoDB
  default charset = utf8mb4 comment '城市信息';

create database db_coupon;
drop table if exists db_coupon.t_coupon;
create table db_coupon.t_coupon
(
    id          int unsigned primary key auto_increment,
    subject     varchar(256) comment '主题',
    type        tinyint unsigned comment '类型',
    priority    tinyint comment '优先级',
    content     text comment '内容',
    rule_info   text comment '优惠规则',
    limit_info  text comment '优惠限制',
    create_time timestamp comment '创建时间',
    status      tinyint unsigned comment '状态',
    sub_title   varchar(64) comment '副标题'
) comment '优惠卷信息表';
drop table if exists db_coupon.t_coupon_issue;
create table db_coupon.t_coupon_issue
(
    id            int unsigned primary key auto_increment,
    coupon_id     int unsigned not null comment '优惠卷 Id',
    type          tinyint unsigned comment '类型，0=时间范围有效、1=领取天数有效',
    begin_time    timestamp comment '开抢时间',
    end_time      timestamp comment '停抢时间',
    days          int unsigned comment '领取多少天之后有效',
    number        int unsigned comment '发行的数量，0=无上限',
    go_start_time timestamp comment '定时抢购开始的时间',
    go_stop_time  timestamp comment '定时抢购结束的时间',
    method        tinyint unsigned comment '优惠卷发布形式，0=领劵中心、1=定时抢购'
) comment '优惠卷发行表';
drop table if exists db_coupon.t_coupon_receive;
create table db_coupon.t_coupon_receive
(
    id        int unsigned primary key auto_increment,
    user_id   int unsigned not null comment '用户 Id',
    coupon_id int unsigned not null comment '优惠卷 Id',
    get_time  timestamp comment '获得时间'
) comment '优惠卷接收记录表';
drop table if exists db_coupon.t_coupon_template;
create table db_coupon.t_coupon_template
(
    id          int unsigned primary key auto_increment,
    name        varchar(256) comment '名称',
    create_time timestamp comment '创建时间',
    status      tinyint comment '状态',
    class       varchar(256) comment '优惠卷模板类路径',
    dynamic     varchar(256) comment '优惠卷模板类字段数据',
    type        tinyint comment '类型'
) comment '优惠卷模板表';
drop table if exists db_coupon.t_dictionary_content;
create table db_coupon.t_dictionary_content
(
    id            int unsigned primary key auto_increment,
    name          varchar(32) comment '名',
    value         text comment '值',
    dictionary_id int unsigned comment '对应字典 Id'
) comment '字典内容表';
drop table if exists db_coupon.t_dictionary;
create table db_coupon.t_dictionary
(
    id     int unsigned primary key auto_increment,
    name   varchar(256) comment '字典名',
    status tinyint comment '状态'
) comment '字典表';

CREATE TABLE `t_history_price`
(
    `id`     bigint unsigned NOT NULL AUTO_INCREMENT,
    `rid`    bigint unsigned      DEFAULT NULL,
    `date`   timestamp       NULL DEFAULT NULL,
    `price`  decimal(5, 2)        DEFAULT NULL,
    `number` int unsigned         DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `t_hotel`
(
    `id`         bigint unsigned NOT NULL AUTO_INCREMENT,
    `hotel_name` varchar(256)    DEFAULT NULL,
    `type`       tinyint         DEFAULT NULL,
    `hotel_info` text,
    `keyword`    varchar(256)    DEFAULT NULL,
    `lon`        double          DEFAULT NULL,
    `lat`        double          DEFAULT NULL,
    `brand`      varchar(256)    DEFAULT NULL,
    `address`    varchar(256)    DEFAULT NULL,
    `cid`        bigint unsigned DEFAULT NULL,
    `district`   varchar(256)    DEFAULT NULL,
    `status`     tinyint         DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `t_order`
(
    `oid`         varchar(256)    DEFAULT NULL,
    `uid`         bigint unsigned DEFAULT NULL,
    `hid`         bigint unsigned DEFAULT NULL,
    `number`      int             DEFAULT NULL,
    `name`        varchar(256)    DEFAULT NULL,
    `phone`       varchar(30)     DEFAULT NULL,
    `begin_time`  timestamp NULL  DEFAULT NULL,
    `end_time`    timestamp NULL  DEFAULT NULL,
    `days`        int             DEFAULT NULL,
    `all_price`   decimal(5, 2)   DEFAULT NULL,
    `create_time` timestamp NULL  DEFAULT NULL,
    `status`      tinyint         DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `t_order_price_detail`
(
    `id`          bigint unsigned NOT NULL AUTO_INCREMENT,
    `oid`         varchar(256)         DEFAULT NULL,
    `time`        timestamp       NULL DEFAULT NULL,
    `price`       decimal(5, 2)        DEFAULT NULL,
    `create_time` timestamp       NULL DEFAULT CURRENT_TIMESTAMP,
    `status`      tinyint              DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `t_order_price_param`
(
    `hid`        bigint unsigned DEFAULT NULL,
    `rid`        bigint unsigned DEFAULT NULL,
    `begin_time` timestamp NULL  DEFAULT NULL,
    `end_time`   timestamp NULL  DEFAULT NULL,
    `rnumber`    int unsigned    DEFAULT NULL,
    `cid`        bigint unsigned DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `t_room_price`
(
    `id`         bigint unsigned NOT NULL AUTO_INCREMENT,
    `rid`        bigint unsigned      DEFAULT NULL,
    `date`       timestamp       NULL DEFAULT NULL,
    `price`      decimal(5, 2)        DEFAULT NULL,
    `type`       tinyint unsigned     DEFAULT NULL,
    `number`     int unsigned         DEFAULT NULL,
    `has_number` int unsigned         DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `t_user`
(
    `id`          int       NOT NULL AUTO_INCREMENT,
    `username`    varchar(32)    DEFAULT NULL,
    `password`    varchar(32)    DEFAULT NULL,
    `nickname`    varchar(32)    DEFAULT NULL,
    `create_time` timestamp NULL DEFAULT NULL,
    `email`       varchar(64)    DEFAULT NULL,
    `status`      int            DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `username` (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
