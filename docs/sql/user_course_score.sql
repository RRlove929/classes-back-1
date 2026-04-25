-- 课程评分表：一个用户对一个课程只能评分一次
-- 注意：本项目仓库未接入 Flyway/Liquibase，需要你在数据库手工执行

CREATE TABLE IF NOT EXISTS `user_course_score` (
  `id` BIGINT NOT NULL COMMENT '主键',
  `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `status_id` TINYINT NOT NULL DEFAULT 1 COMMENT '状态(1:正常，0:禁用)',
  `sort` INT NOT NULL DEFAULT 0 COMMENT '排序',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `course_id` BIGINT NOT NULL COMMENT '课程ID',
  `score` TINYINT NOT NULL COMMENT '评分(1-5)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_course` (`user_id`,`course_id`),
  KEY `idx_course_id` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户课程评分';

