-- 创建数据库
drop database if exists bm;
create database bm;

-- 应用bm数据库到当前会话
use bm;

-- 令牌表
drop table if exists bm_token;
create table bm_token
(
    id          int          not null auto_increment comment '主键',
    login_id    varchar(100) not null comment '登录id',
    token       text         not null comment '令牌',
    expire_time datetime     null comment '失效时间',
    create_time datetime     not null default current_timestamp comment '创建时间',
    update_time datetime     not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id)
) comment '令牌表';

create unique index uniq_login_id on bm_token (login_id);

-- 用户表
drop table if exists bm_user;
create table bm_user
(
    id          int          not null auto_increment comment '主键',
    username    varchar(20)  not null comment '用户名称',
    mobile      varchar(11)  null comment '手机号码',
    password    varchar(100) not null comment '密码',
    nickname    varchar(200) null comment '昵称',
    avatar_url  varchar(255) null comment '头像链接',
    balance     decimal(12, 2)        default 0.0 comment '余额',
    user_type   int          not null default 0 comment '用户类型(0:普通用户、1:会员、2:员工、-1:超级管理员)',
    create_time datetime     not null default current_timestamp comment '创建时间',
    update_time datetime     not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id)
) comment '用户表';

create unique index uniq_username on bm_user (username);
create unique index uniq_mobile on bm_user (mobile);

-- 初始化一个超管
insert into bm_user (id, username, password, nickname, balance, user_type)
values (1, 'admin', '123456', '超管', 99999999.00, -1);

-- 充值记录表
drop table if exists bm_recharge_record;
create table bm_recharge_record
(
    id          int      not null auto_increment comment '主键',
    user_id     int      not null comment '充值用户id',
    amount      decimal(12, 2)    default 0.0 comment '充值金额',
    operator_id int      not null comment '操作人id',
    create_time datetime not null default current_timestamp comment '创建时间',
    update_time datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id)
) comment '充值记录表';

-- 商品类型表
drop table if exists bm_goods_type;
create table bm_goods_type
(
    id          int         not null auto_increment comment '主键',
    type        varchar(50) not null comment '商品类型',
    create_time datetime    not null default current_timestamp comment '创建时间',
    update_time datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id)
) comment '商品类型表';

create unique index uniq_type on bm_goods_type (type);

-- 初始化商品类型
insert into bm_goods_type(id, type)
values (1, '食品'),
       (2, '中式台球'),
       (3, '斯诺克');

-- 商品表
drop table if exists bm_goods;
create table bm_goods
(
    id            int          not null auto_increment comment '主键',
    goods_type_id int          not null comment '商品类型id',
    name          varchar(200) not null comment '商品名称',
    price         decimal(12, 2)        default 0.0 comment '商品价格',
    create_time   datetime     not null default current_timestamp comment '创建时间',
    update_time   datetime     not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id)
);

-- 初始化商品
insert into bm_goods(id, goods_type_id, name, price)
values (1, 1, '可口可乐', 2.5),
       (2, 1, '薯片', 4.0),
       (3, 1, '泡面', 4.5),
       (4, 2, '[团购]中八闲时(周一至周日10:00~18:00)2小时', 19.9),
       (5, 2, '中八全天2小时', 39.9),
       (6, 2, '斯诺克2小时', 56);

-- 球桌表
drop table if exists bm_table;
create table bm_table
(
    id          int         not null auto_increment comment '主键',
    type        int         not null comment '球桌类型(1:中式八球、2:斯诺克)',
    name        varchar(50) not null comment '球桌名称',
    status      int         not null default 0 comment '球桌状态(0:未启用、1:已启用、2:营运中)',
    create_time datetime    not null default current_timestamp comment '创建时间',
    update_time datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id)
) comment '球桌表';

-- 初始化球桌
insert into bm_table(id, type, name, status)
values (1, 1, 'CB-1', 1),
       (2, 1, 'CB-2', 1),
       (3, 1, 'CB-3', 1),
       (4, 1, 'CB-4', 1),
       (5, 1, 'CB-5', 1),
       (6, 1, 'CB-6', 1),
       (7, 1, 'CB-7', 1),
       (8, 1, 'CB-8', 1),
       (9, 1, 'CB-9', 1),
       (10, 1, 'CB-10', 1),
       (11, 2, 'Snooker-1', 1),
       (12, 2, 'Snooker-2', 1);

-- 订单表
drop table if exists bm_order;
create table bm_order
(
    id                int          not null auto_increment comment '主键',
    user_id           int          not null comment '下单用户id',
    goods_id          int          not null comment '商品id',
    origin_goods_name varchar(200) not null comment '原商品名称',
    price             decimal(12, 2)        default 0.0 comment '支付价格',
    status            int          not null default 0 comment '订单状态(0:待使用、1:已使用、2:已取消)',
    create_time       datetime     not null default current_timestamp comment '创建时间',
    update_time       datetime     not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id)
) comment '订单表';

-- 开台记录表
drop table if exists bm_table_open_record;
create table bm_table_open_record
(
    id          int      not null auto_increment comment '主键',
    order_id    int      not null comment '订单id',
    table_id    int      not null comment '球桌id',
    expire_time datetime not null comment '到期时间',
    create_time datetime not null default current_timestamp comment '创建时间',
    update_time datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id)
) comment '开台记录表';