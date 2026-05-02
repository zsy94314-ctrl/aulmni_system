-- 校友联络与捐赠管理系统数据库初始化脚本
-- 数据库: alumni_db
-- 字符集: utf8mb4

CREATE DATABASE IF NOT EXISTS alumni_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE alumni_db;

-- 系统用户表
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(50),
    email VARCHAR(100),
    phone VARCHAR(20),
    avatar VARCHAR(200),
    role VARCHAR(20) DEFAULT 'ALUMNI',
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT NOW(),
    update_time DATETIME DEFAULT NOW(),
    deleted TINYINT DEFAULT 0
);

-- 学院表
CREATE TABLE IF NOT EXISTS college (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(20),
    description TEXT,
    create_time DATETIME DEFAULT NOW(),
    deleted TINYINT DEFAULT 0
);

-- 班级表
CREATE TABLE IF NOT EXISTS class (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    college_id BIGINT,
    grade INT,
    major VARCHAR(50),
    create_time DATETIME DEFAULT NOW(),
    deleted TINYINT DEFAULT 0,
    FOREIGN KEY (college_id) REFERENCES college(id)
);

-- 校友信息表
CREATE TABLE IF NOT EXISTS alumni (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_no VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL,
    class_id BIGINT,
    phone VARCHAR(20),
    email VARCHAR(100),
    industry VARCHAR(50),
    region VARCHAR(50),
    company VARCHAR(100),
    position VARCHAR(50),
    avatar VARCHAR(200),
    privacy_level TINYINT DEFAULT 0,
    education_bg TEXT,
    work_experience TEXT,
    user_id BIGINT,
    create_time DATETIME DEFAULT NOW(),
    update_time DATETIME DEFAULT NOW(),
    deleted TINYINT DEFAULT 0,
    FOREIGN KEY (class_id) REFERENCES class(id),
    FOREIGN KEY (user_id) REFERENCES sys_user(id)
);

-- 活动表
CREATE TABLE IF NOT EXISTS activity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    type VARCHAR(50),
    location VARCHAR(200),
    start_time DATETIME,
    end_time DATETIME,
    registration_start DATETIME,
    registration_end DATETIME,
    max_participants INT DEFAULT 0,
    cover_image VARCHAR(200),
    description TEXT,
    status TINYINT DEFAULT 0,
    organizer_id BIGINT,
    registered_count INT DEFAULT 0,
    create_time DATETIME DEFAULT NOW(),
    deleted TINYINT DEFAULT 0
);

-- 活动报名表
CREATE TABLE IF NOT EXISTS activity_registration (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    activity_id BIGINT NOT NULL,
    alumni_id BIGINT NOT NULL,
    status TINYINT DEFAULT 1,
    remark VARCHAR(200),
    checked_in TINYINT DEFAULT 0,
    check_in_time DATETIME,
    create_time DATETIME DEFAULT NOW()
);

-- 捐赠项目表
CREATE TABLE IF NOT EXISTS donation_project (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    target_amount DECIMAL(12,2) NOT NULL,
    current_amount DECIMAL(12,2) DEFAULT 0,
    start_date DATE,
    end_date DATE,
    cover_image VARCHAR(200),
    status TINYINT DEFAULT 0,
    donor_count INT DEFAULT 0,
    create_time DATETIME DEFAULT NOW(),
    deleted TINYINT DEFAULT 0
);

-- 捐赠记录表
CREATE TABLE IF NOT EXISTS donation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT NOT NULL,
    alumni_id BIGINT NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    pay_method VARCHAR(20),
    certificate_no VARCHAR(50),
    order_no VARCHAR(50),
    status TINYINT DEFAULT 0,
    remark VARCHAR(200),
    create_time DATETIME DEFAULT NOW()
);

-- 新闻资讯表
CREATE TABLE IF NOT EXISTS news (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    category VARCHAR(50),
    summary TEXT,
    content LONGTEXT,
    cover_image VARCHAR(200),
    author VARCHAR(50),
    view_count INT DEFAULT 0,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT NOW(),
    deleted TINYINT DEFAULT 0
);

-- 审计日志表
CREATE TABLE IF NOT EXISTS audit_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    username VARCHAR(50),
    action VARCHAR(50),
    resource_type VARCHAR(50),
    resource_id BIGINT,
    detail TEXT,
    ip_address VARCHAR(50),
    create_time DATETIME DEFAULT NOW()
);

-- 疑似重复记录表
CREATE TABLE IF NOT EXISTS duplicate_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    alumni_id1 BIGINT,
    alumni_id2 BIGINT,
    alumni_name1 VARCHAR(50),
    alumni_name2 VARCHAR(50),
    similarity DOUBLE,
    status TINYINT DEFAULT 0,
    action VARCHAR(50),
    create_time DATETIME DEFAULT NOW()
);

-- 初始化数据
INSERT INTO sys_user (username, password, name, role, status) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EO', '管理员', 'ADMIN', 1),
('alumni', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EO', '校友用户', 'ALUMNI', 1);

INSERT INTO college (name, code, description) VALUES
('机械工程学院', 'ME', '机械工程'),
('信息科学与工程学院', 'ISE', '信息科学'),
('经济管理学院', 'EM', '经济管理'),
('材料科学与工程学院', 'MSE', '材料科学');

INSERT INTO class (name, college_id, grade, major) VALUES
('机械2001班', 1, 2020, '机械设计'),
('计算机2001班', 2, 2020, '计算机科学'),
('工商2001班', 3, 2020, '工商管理'),
('材料2001班', 4, 2020, '材料工程');

INSERT INTO alumni (student_no, name, class_id, phone, email, industry, region, company, position) VALUES
('2020001', '张三', 1, '13800138001', 'zhangsan@ysu.edu.cn', 'IT互联网', '北京', '某科技公司', '工程师'),
('2020002', '李四', 2, '13800138002', 'lisi@ysu.edu.cn', '金融', '上海', '某银行', '经理'),
('2020003', '王五', 3, '13800138003', 'wangwu@ysu.edu.cn', '教育', '广州', '某大学', '教授');

INSERT INTO activity (title, type, location, start_time, max_participants, description, status) VALUES
('校友返校日', '聚会', '燕山大学西校区', NOW() + INTERVAL 7 DAY, 200, '欢迎校友返校参加活动', 1),
('创业经验分享会', '讲座', '图书馆报告厅', NOW() + INTERVAL 14 DAY, 100, '邀请成功校友分享创业经验', 1);

INSERT INTO donation_project (title, description, target_amount, end_date, status) VALUES
('教学楼修缮基金', '为改善教学环境募集资金', 500000.00, NOW() + INTERVAL 90 DAY, 0),
('贫困生助学金', '资助家庭经济困难学生', 200000.00, NOW() + INTERVAL 180 DAY, 0);

INSERT INTO news (title, category, summary, content, author, status) VALUES
('燕山大学百年校庆公告', '学校新闻', '庆祝燕山大学建校100周年', '详细内容...', '校办', 1),
('优秀校友事迹展', '校友动态', '展示杰出校友成就', '详细内容...', '校友办', 1);

-- 创建索引
CREATE INDEX idx_alumni_student_no ON alumni(student_no);
CREATE INDEX idx_alumni_class_id ON alumni(class_id);
CREATE INDEX idx_activity_status_start ON activity(status, start_time);
CREATE INDEX idx_donation_project_id ON donation(project_id);
CREATE INDEX idx_donation_alumni_id ON donation(alumni_id);
