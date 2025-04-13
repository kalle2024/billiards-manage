-- 创建数据库
drop database if exists bm;
create database bm;

-- 应用bm数据库到当前会话
use bm;

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