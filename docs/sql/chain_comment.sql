-- 链上评价明细表（用于公开可查与可追溯）
-- 注意：本项目仓库未接入 Flyway/Liquibase，需要你在数据库手工执行

CREATE TABLE IF NOT EXISTS `chain_comment` (
  `id` BIGINT NOT NULL COMMENT '主键',
  `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',

  `chain_id` BIGINT NOT NULL COMMENT '链ID(如1/56等)',
  `contract_address` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '合约地址',
  `content_type` VARCHAR(32) NOT NULL DEFAULT 'course' COMMENT '内容类型(course等)',
  `content_id` BIGINT NOT NULL COMMENT '内容ID(课程ID等)',

  `user_address` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '评价者链上地址',
  `score` TINYINT NOT NULL DEFAULT 0 COMMENT '评分(1-5)，无评分为0',
  `comment_uri` VARCHAR(512) NOT NULL DEFAULT '' COMMENT '评论原文URI(如IPFS/OSS，可空)',
  `comment_hash` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '评论hash/承诺值，可空',

  `tx_hash` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '交易hash',
  `block_number` BIGINT NOT NULL DEFAULT 0 COMMENT '区块高度',
  `log_index` INT NOT NULL DEFAULT 0 COMMENT '日志序号(同tx内唯一)',
  `event_time` BIGINT NOT NULL DEFAULT 0 COMMENT '链上事件时间戳(秒)',

  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_chain_tx_log` (`chain_id`, `tx_hash`, `log_index`),
  KEY `idx_content` (`content_type`, `content_id`),
  KEY `idx_user` (`user_address`),
  KEY `idx_block` (`chain_id`, `block_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='链上评价明细';

