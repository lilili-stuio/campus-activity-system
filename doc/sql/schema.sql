-- ============================================
-- 校园活动管理系统 - 数据库建表脚本
-- ============================================

CREATE DATABASE IF NOT EXISTS campus_activity DEFAULT CHARACTER SET utf8mb4;
USE campus_activity;

-- 用户表
DROP TABLE IF EXISTS t_user;
CREATE TABLE t_user (
    id          BIGINT        AUTO_INCREMENT PRIMARY KEY,
    student_id  VARCHAR(20)   NOT NULL UNIQUE COMMENT '学号',
    name        VARCHAR(50)   NOT NULL COMMENT '姓名',
    password    VARCHAR(128)  NOT NULL COMMENT '密码',
    role        VARCHAR(10)   NOT NULL DEFAULT 'student' COMMENT '角色: admin/student'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 活动表
DROP TABLE IF EXISTS t_activity;
CREATE TABLE t_activity (
    id           BIGINT        AUTO_INCREMENT PRIMARY KEY,
    title        VARCHAR(128)  NOT NULL COMMENT '活动名称',
    description  TEXT          COMMENT '活动描述',
    location     VARCHAR(100)  COMMENT '活动地点',
    start_time   DATETIME      NOT NULL COMMENT '开始时间',
    end_time     DATETIME      NOT NULL COMMENT '结束时间',
    max_capacity INT           NOT NULL COMMENT '最大报名人数',
    current_num  INT           NOT NULL DEFAULT 0 COMMENT '当前已报名人数',
    status       TINYINT       NOT NULL DEFAULT 0 COMMENT '0-未开始, 1-报名中, 2-已结束',
    qr_code_key  VARCHAR(64)   UNIQUE COMMENT '签到二维码密钥',
    created_by   BIGINT        NOT NULL COMMENT '创建者ID',
    created_at   DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动表';

-- 报名表
DROP TABLE IF EXISTS t_registration;
CREATE TABLE t_registration (
    id           BIGINT   AUTO_INCREMENT PRIMARY KEY,
    user_id      BIGINT   NOT NULL COMMENT '用户ID',
    activity_id  BIGINT   NOT NULL COMMENT '活动ID',
    registered_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '报名时间',
    UNIQUE KEY uk_user_activity (user_id, activity_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='报名表';

-- 签到表
DROP TABLE IF EXISTS t_checkin;
CREATE TABLE t_checkin (
    id           BIGINT   AUTO_INCREMENT PRIMARY KEY,
    user_id      BIGINT   NOT NULL COMMENT '用户ID',
    activity_id  BIGINT   NOT NULL COMMENT '活动ID',
    checkin_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '签到时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='签到表';
