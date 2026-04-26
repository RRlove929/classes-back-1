-- 用户表新增生日字段
-- 注意：本项目仓库未接入 Flyway/Liquibase，需要你在数据库手工执行

ALTER TABLE `users`
ADD COLUMN `birthday` DATE NULL COMMENT '生日' AFTER `user_age`;

