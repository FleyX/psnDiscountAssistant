-- game表增加一个字段表示本游戏信息是否有效
ALTER TABLE `psn`.`game`
ADD COLUMN `valid` tinyint(0) NOT NULL DEFAULT 1 COMMENT '是否有效' AFTER `parentStoreId`;