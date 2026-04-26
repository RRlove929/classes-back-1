-- 内容评分聚合表（用于快速查询：均分、人数、分布）
-- 注意：本项目仓库未接入 Flyway/Liquibase，需要你在数据库手工执行

CREATE TABLE IF NOT EXISTS `content_rating_agg` (
  `id` BIGINT NOT NULL COMMENT '主键',
  `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',

  `content_type` VARCHAR(32) NOT NULL DEFAULT 'course' COMMENT '内容类型(course等)',
  `content_id` BIGINT NOT NULL COMMENT '内容ID(课程ID等)',

  `score_count` INT NOT NULL DEFAULT 0 COMMENT '评分人数',
  `score_sum` INT NOT NULL DEFAULT 0 COMMENT '评分总和(1-5累计)',
  `score_1_count` INT NOT NULL DEFAULT 0 COMMENT '1分数量',
  `score_2_count` INT NOT NULL DEFAULT 0 COMMENT '2分数量',
  `score_3_count` INT NOT NULL DEFAULT 0 COMMENT '3分数量',
  `score_4_count` INT NOT NULL DEFAULT 0 COMMENT '4分数量',
  `score_5_count` INT NOT NULL DEFAULT 0 COMMENT '5分数量',

  `last_block_number` BIGINT NOT NULL DEFAULT 0 COMMENT '聚合到的最新区块高度',

  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_content` (`content_type`, `content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='内容评分聚合';

