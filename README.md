# Spring Cloud 酒店预订微服务系统

一个基于 Spring Cloud 构建的**酒店预订平台**微服务系统，涵盖用户管理、酒店搜索、客房预订、优惠券发放/抢购、支付宝支付等完整业务流程。

## 技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Java | 21 | |
| Spring Boot | 3.5.0 | 应用框架 |
| Spring Cloud | 2025.0.0 | 微服务治理 |
| MyBatis-Plus | 3.5.12 | ORM 框架 |
| MySQL | 9.3.0 | 关系型数据库 |
| Redis | - | 布隆过滤器 / 分布式锁 / 令牌桶限流 / 缓存 |
| RabbitMQ | - | 事件驱动，服务间异步解耦 |
| ElasticSearch | 9.0.2 | 酒店搜索（中文分词 + 拼音搜索） |
| Eureka | - | 服务注册与发现 |
| Spring Cloud Gateway | - | API 网关（路由 / 限流 / 过滤） |
| Spring Cloud Config | - | 配置中心（Native 模式） |
| OpenFeign | - | 服务间声明式调用 |
| Zipkin | - | 链路追踪（采样率 50%） |
| Alipay SDK | 4.40.237 | 支付宝沙箱支付 |
| JWT (jjwt) | 0.12.6 | 用户认证 |
| Pinyin4j | 2.5.1 | 中文转拼音 |
| Thymeleaf | - | 后台管理页面模板 |
| Log4j2 | - | 日志框架 |

## 项目结构

```
springcloud/
├── configserver/       # 配置中心 (:8080)
├── eurekaserver/       # 服务注册中心 (:8761)
├── gatewayserver/      # API 网关 (:9001)
├── userserver/         # 用户服务 (:9009)
├── cityserver/         # 城市服务 (:9002)
├── hotelserver/        # 酒店服务 (:9004)
├── orderserver/        # 订单服务 (:9005)
├── couponserver/       # 优惠券服务 (:9003)
├── purchaseserver/     # 优惠券抢购 (:9006)
├── searchserver/       # 搜索服务 (:9007)
├── systemserver/       # 后台管理 (:9008)
├── entity/             # 实体类 + 优惠券规则引擎
├── common/             # 公共工具（JWT / 布隆过滤器 / 分布式锁 / 限流）
├── feign/              # Feign 远程调用接口
├── rabbitmq/           # RabbitMQ 事件驱动基础设施
├── exception/          # 全局异常处理
├── log4j2/             # 日志配置
└── db.sql              # 数据库初始化脚本
```

## 服务清单

| 模块 | 服务名 | 端口 | 角色 |
|------|--------|------|------|
| eurekaserver | eureka-server | 8761 | 服务注册中心 |
| configserver | configserver | 8080 | 配置中心（Native 模式） |
| gatewayserver | gateway-server | 9001 | API 网关 |
| cityserver | micro-city | 9002 | 城市管理 |
| couponserver | micro-coupon | 9003 | 优惠券管理 |
| hotelserver | micro-hotel | 9004 | 酒店与客房管理 |
| orderserver | micro-order | 9005 | 订单与支付 |
| purchaseserver | micro-purchase | 9006 | 优惠券限时抢购 |
| searchserver | micro-search | 9007 | ES 酒店搜索 |
| systemserver | micro-system | 9008 | 后台管理系统 |
| userserver | micro-user | 9009 | 用户认证 |

## 业务功能

### 用户服务
- 用户注册、登录（JWT 令牌认证，有效期 7 天）

### 城市服务
- 城市列表查询、新增城市（自动生成拼音）
- 监听酒店创建事件，自动维护城市酒店数量

### 酒店服务
- 酒店与客房的增删查改
- 客房价格管理（按日期定价）
- 查看酒店详情时使用 Redis 布隆过滤器防止重复点击率统计
- 监听下单事件，自动更新客房剩余库存

### 优惠券服务
- **动态规则引擎**：通过反射 + JSON 动态创建优惠规则（满减 / 递减）和限制条件（指定酒店）
- 优惠券发行：领券中心 / 定时抢购两种模式
- 优惠券领取、用户可用券查询
- 优惠券模板管理、数据字典管理
- 使用 Spring Cache 缓存按时间查询的优惠券列表

### 订单服务
- 订单价格计算（按日期累加房价 * 房间数 - 优惠金额）
- 下单（Redis 分布式锁保证库存安全）
- 支付宝沙箱支付（WAP 手机网页支付 + 异步回调验签）

### 优惠券抢购
- 整点场次抢购，定时任务更新场次时间
- **Redis Lua 脚本原子扣减库存**，保证高并发一致性
- 网关层场次校验 + 令牌桶限流双重防护
- 抢购成功后 MQ 异步处理领取记录

### 搜索服务
- 基于 ElasticSearch 的复杂酒店搜索
- 中文分词（ik_max_word）+ 拼音搜索
- 多字段匹配（酒店名权重 5 > 品牌 4 > 关键词/区域 3）
- Nested 查询排除满房房间
- Painless 脚本计算最低均价和地理距离
- 点击率加权排序
- 启动时自动创建索引并同步数据库

### 后台管理
- Thymeleaf 模板渲染的管理界面
- 城市管理、酒店管理（含图片上传）、客房管理
- 优惠券管理、发行管理、模板管理、字典管理
- 房价修改

## 架构设计

### 事件驱动

服务间通过 RabbitMQ 实现异步解耦，基于 Direct Exchange（`event-exchange`）：

| 事件 | 发布方 | 消费方 | 说明 |
|------|--------|--------|------|
| `hotel_insert` | HotelServer | CityServer | 酒店创建时更新城市酒店数量 |
| `hotel_room_update` | OrderServer | HotelServer / SearchServer | 下单后更新客房库存 |
| `hotel_click` | HotelServer | SearchServer | 更新酒店点击率 |
| `coupon_purchase` | PurchaseServer | CouponServer | 抢购成功后生成领取记录 |

### 服务调用

通过 OpenFeign 实现服务间同步调用：

| Feign 接口 | 目标服务 | 调用方 |
|------------|---------|--------|
| CityFeign | micro-city | HotelServer / SystemServer |
| HotelFeign | micro-hotel | OrderServer / SystemServer |
| CouponFeign | micro-coupon | OrderServer / SystemServer |
| OrderFeign | micro-order | CouponServer |

### 网关层

- 路由转发：基于路径前缀路由到对应微服务（`lb://` 负载均衡）
- 全局过滤器：请求日志记录
- PurchaseFilter：防止提前抢购（Redis Set 校验场次）
- RedisLimiterFilter：基于 Redis 令牌桶的分布式限流（每 URL 独立桶，500 容量 / 500 tokens/秒）

### 配置中心

使用 Spring Cloud Config Native 模式，所有共享配置统一管理：

- `eureka-local.yml` — Eureka 注册地址
- `mysql-local.yml` — MySQL 数据源 + MyBatis-Plus（每个服务独立数据库 `db_${spring.application.name}`）
- `redis-local.yml` — Redis 连接
- `rabbitmq-local.yml` — RabbitMQ 连接
- `elasticsearch.yml` — ElasticSearch 连接
- `log4j2-local.yml` — 日志 + Zipkin 链路追踪
- `common-local.yml` — JWT 密钥和超时时间

## 快速开始

### 环境依赖

- JDK 21
- Maven
- MySQL 9.x
- Redis
- RabbitMQ
- ElasticSearch 9.x（需安装 ik 中文分词和拼音分词插件）

### 启动步骤

1. 执行 `db.sql` 初始化数据库
2. 按顺序启动基础设施服务：Eureka -> Config Server -> Gateway
3. 启动业务服务：User -> City -> Hotel -> Coupon -> Order -> Purchase -> Search -> System
