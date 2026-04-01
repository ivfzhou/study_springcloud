-- ==============================================================================
-- 酒店管理系统数据库初始化脚本
-- 本脚本包含所有业务模块的数据库和表创建语句
-- ==============================================================================

-- ----------------------------
-- 城市模块：创建数据库和城市表
-- ----------------------------
create database if not exists db_city;
-- 如果表已存在则先删除
drop table if exists db_city.t_city;
-- 城市信息表：存储城市基本信息
create table db_city.t_city
(
    id           int unsigned primary key auto_increment,   -- 城市主键ID
    name         varchar(32)      not null comment '城市名',
    pinyin       varchar(64) comment '城市名拼音',
    hotel_number int unsigned default 0 comment '城市的酒店数量',
    create_time  timestamp        not null comment '创建时间',
    status       tinyint unsigned not null comment '状态，0=正常、1=删除'
) engine = InnoDB
  default charset = utf8mb4 comment '城市信息';

-- ----------------------------
-- 优惠券模块：创建数据库和优惠券相关表
-- ----------------------------
create database db_coupon;
-- 优惠券信息表：存储优惠券的基本信息（名称、类型、优惠规则等）
drop table if exists db_coupon.t_coupon;
create table db_coupon.t_coupon
(
    id          int unsigned primary key auto_increment,  -- 优惠券主键ID
    subject     varchar(256) comment '主题',                -- 优惠券主题名称
    type        tinyint unsigned comment '类型',            -- 优惠券类型
    priority    tinyint comment '优先级',                   -- 优惠券显示优先级
    content     text comment '内容',                       -- 优惠券详细描述内容
    rule_info   text comment '优惠规则',                   -- JSON格式的优惠规则（如满减金额等）
    limit_info  text comment '优惠限制',                   -- JSON格式的使用限制条件
    create_time timestamp comment '创建时间',              -- 优惠券创建时间
    status      tinyint unsigned comment '状态',           -- 状态标记
    sub_title   varchar(64) comment '副标题'               -- 优惠券副标题
) comment '优惠卷信息表';

-- 优惠券发行表：记录优惠券的发行策略（领券中心、定时抢购等）
drop table if exists db_coupon.t_coupon_issue;
-- 优惠券发行表：记录优惠券的发行策略（领券中心、定时抢购等）
drop table if exists db_coupon.t_coupon_issue;
create table db_coupon.t_coupon_issue
(
    id            int unsigned primary key auto_increment,  -- 发行记录主键ID
    coupon_id     int unsigned not null comment '优惠卷 Id',  -- 关联的优惠券ID
    type          tinyint unsigned comment '类型，0=时间范围有效、1=领取天数有效', -- 有效期类型
    begin_time    timestamp comment '开抢时间',                -- 领券开始时间
    end_time      timestamp comment '停抢时间',                -- 领券结束时间
    days          int unsigned comment '领取多少天之后有效',     -- 领取后有效天数
    number        int unsigned comment '发行的数量，0=无上限',   -- 发行总数量
    go_start_time timestamp comment '定时抢购开始的时间',        -- 定时抢购场次开始时间
    go_stop_time  timestamp comment '定时抢购结束的时间',        -- 定时抢购场次结束时间
    method        tinyint unsigned comment '优惠卷发布形式，0=领劵中心、1=定时抢购' -- 发布方式
) comment '优惠卷发行表';

-- 优惠券领取记录表：记录用户领取优惠券的记录
drop table if exists db_coupon.t_coupon_receive;
-- 优惠券领取记录表：记录用户领取优惠券的记录
drop table if exists db_coupon.t_coupon_receive;
create table db_coupon.t_coupon_receive
(
    id        int unsigned primary key auto_increment,  -- 记录主键ID
    user_id   int unsigned not null comment '用户 Id',    -- 领取用户ID
    coupon_id int unsigned not null comment '优惠卷 Id',  -- 领取的优惠券ID
    get_time  timestamp comment '获得时间'                -- 领取时间
) comment '优惠卷接收记录表';

-- 优惠券模板表：存储优惠券的模板定义（用于后台管理创建优惠券规则模板）
drop table if exists db_coupon.t_coupon_template;
-- 优惠券模板表：存储优惠券的模板定义（用于后台管理创建优惠券规则模板）
drop table if exists db_coupon.t_coupon_template;
create table db_coupon.t_coupon_template
(
    id          int unsigned primary key auto_increment,  -- 模板主键ID
    name        varchar(256) comment '名称',                -- 模板名称
    create_time timestamp comment '创建时间',              -- 创建时间
    status      tinyint comment '状态',                    -- 状态标记
    class       varchar(256) comment '优惠卷模板类路径',     -- 实现类的全限定类名
    dynamic     varchar(256) comment '优惠卷模板类字段数据',  -- 动态字段JSON数据
    type        tinyint comment '类型'                     -- 模板类型
) comment '优惠卷模板表';

-- 字典内容表：存储字典项下的具体内容
drop table if exists db_coupon.t_dictionary_content;
-- 字典内容表：存储字典项下的具体内容
drop table if exists db_coupon.t_dictionary_content;
create table db_coupon.t_dictionary_content
(
    id            int unsigned primary key auto_increment,  -- 内容主键ID
    name          varchar(32) comment '名',                   -- 字典项显示名称
    value         text comment '值',                          -- 字典项实际值
    dictionary_id int unsigned comment '对应字典 Id'          -- 关联的字典ID
) comment '字典内容表';

-- 字典表：存储系统数据字典（如酒店类型、品牌等下拉选项）
drop table if exists db_coupon.t_dictionary;
-- 字典表：存储系统数据字典（如酒店类型、品牌等下拉选项）
drop table if exists db_coupon.t_dictionary;
create table db_coupon.t_dictionary
(
    id     int unsigned primary key auto_increment,  -- 字典主键ID
    name   varchar(256) comment '字典名',              -- 字典名称
    status tinyint comment '状态'                     -- 状态标记
) comment '字典表';

-- ----------------------------
-- 酒店模块：历史价格表
-- ----------------------------
-- 历史价格表：记录酒店客房的历史每日价格（用于价格查询和展示）
CREATE TABLE `t_history_price`
(
    `id`     bigint unsigned NOT NULL AUTO_INCREMENT,  -- 主键ID
    `rid`    bigint unsigned      DEFAULT NULL,        -- 关联的客房ID
    `date`   timestamp       NULL DEFAULT NULL,        -- 价格日期
    `price`  decimal(5, 2)        DEFAULT NULL,        -- 当日价格
    `number` int unsigned         DEFAULT NULL,        -- 当日可用房间数量
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- 酒店信息表：存储酒店基本信息（名称、类型、位置、品牌等）
-- 酒店信息表：存储酒店基本信息（名称、类型、位置、品牌等）
CREATE TABLE `t_hotel`
(
    `id`         bigint unsigned NOT NULL AUTO_INCREMENT,  -- 酒店主键ID
    `hotel_name` varchar(256)    DEFAULT NULL,              -- 酒店名称
    `type`       tinyint         DEFAULT NULL,              -- 酒店类型
    `hotel_info` text,                                      -- 酒店详细介绍
    `keyword`    varchar(256)    DEFAULT NULL,              -- 搜索关键词
    `lon`        double          DEFAULT NULL,              -- 经度
    `lat`        double          DEFAULT NULL,              -- 纬度
    `brand`      varchar(256)    DEFAULT NULL,              -- 酒店品牌
    `address`    varchar(256)    DEFAULT NULL,              -- 酒店地址
    `cid`        bigint unsigned DEFAULT NULL,              -- 所属城市ID
    `district`   varchar(256)    DEFAULT NULL,              -- 所属区域/行政区
    `status`     tinyint         DEFAULT NULL,              -- 状态标记
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- 订单表：存储用户的酒店预订订单
-- 订单表：存储用户的酒店预订订单
CREATE TABLE `t_order`
(
    `oid`         varchar(256)    DEFAULT NULL,             -- 订单号（雪花算法生成）
    `uid`         bigint unsigned DEFAULT NULL,             -- 下单用户ID
    `hid`         bigint unsigned DEFAULT NULL,             -- 酒店ID
    `rid`         bigint unsigned DEFAULT NULL,             -- 客房类型ID
    `number`      int             DEFAULT NULL,             -- 预订房间数量
    `name`        varchar(256)    DEFAULT NULL,             -- 入住人姓名
    `phone`       varchar(30)     DEFAULT NULL,             -- 入住人手机号
    `begin_time`  timestamp NULL  DEFAULT NULL,             -- 入住时间
    `end_time`    timestamp NULL  DEFAULT NULL,             -- 离店时间
    `days`        int             DEFAULT NULL,             -- 入住天数
    `all_price`   decimal(5, 2)   DEFAULT NULL,             -- 订单总金额
    `create_time` timestamp NULL  DEFAULT NULL,             -- 下单时间
    `status`      tinyint         DEFAULT NULL              -- 订单状态（0=待支付、1=已支付等）
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- 订单价格明细表：记录订单中每天的房间价格
-- 订单价格明细表：记录订单中每天的房间价格
CREATE TABLE `t_order_price_detail`
(
    `id`          bigint unsigned NOT NULL AUTO_INCREMENT,  -- 明细主键ID
    `oid`         varchar(256)         DEFAULT NULL,         -- 关联的订单号
    `time`        timestamp       NULL DEFAULT NULL,         -- 价格对应的日期
    `price`       decimal(5, 2)        DEFAULT NULL,         -- 当日价格
    `create_time` timestamp       NULL DEFAULT CURRENT_TIMESTAMP, -- 创建时间
    `status`      tinyint              DEFAULT NULL,         -- 状态标记
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- 订单价格查询参数表：存储价格计算时的查询条件（临时表/参数表）
-- 订单价格查询参数表：存储价格计算时的查询条件（临时表/参数表）
CREATE TABLE `t_order_price_param`
(
    `hid`        bigint unsigned DEFAULT NULL,  -- 酒店ID
    `rid`        bigint unsigned DEFAULT NULL,  -- 客房类型ID
    `begin_time` timestamp NULL  DEFAULT NULL,  -- 入住时间
    `end_time`   timestamp NULL  DEFAULT NULL,  -- 离店时间
    `rnumber`    int unsigned    DEFAULT NULL,  -- 预订房间数量
    `cid`        bigint unsigned DEFAULT NULL   -- 使用的优惠券ID
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- 客房价格表：存储每个客房类型每天的详细价格和库存
-- 客房价格表：存储每个客房类型每天的详细价格和库存
CREATE TABLE `t_room_price`
(
    `id`         bigint unsigned NOT NULL AUTO_INCREMENT,  -- 价格记录主键ID
    `rid`        bigint unsigned      DEFAULT NULL,        -- 关联的客房类型ID
    `date`       timestamp       NULL DEFAULT NULL,        -- 价格生效日期
    `price`      decimal(5, 2)        DEFAULT NULL,        -- 当日价格
    `type`       tinyint unsigned     DEFAULT NULL,        -- 价格类型（周末/节假日/平日等）
    `number`     int unsigned         DEFAULT NULL,        -- 总房间数量
    `has_number` int unsigned         DEFAULT NULL,        -- 已预订/已售房间数量
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- 用户表：存储系统用户的基本信息
-- 用户表：存储系统用户的基本信息
CREATE TABLE `t_user`
(
    `id`          int       NOT NULL AUTO_INCREMENT,       -- 用户主键ID
    `username`    varchar(32)    DEFAULT NULL,              -- 用户名（登录账号，唯一）
    `password`    varchar(32)    DEFAULT NULL,              -- 密码
    `nickname`    varchar(32)    DEFAULT NULL,              -- 昵称
    `create_time` timestamp NULL DEFAULT NULL,              -- 注册时间
    `email`       varchar(64)    DEFAULT NULL,              -- 邮箱
    `status`      int            DEFAULT NULL,              -- 状态标记
    PRIMARY KEY (`id`),
    UNIQUE KEY `username` (`username`)                      -- 用户名唯一约束
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
