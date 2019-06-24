ALTER TABLE `psn`.`game`
ADD COLUMN `rmbPrice` mediumint(10) UNSIGNED NOT NULL COMMENT 'rmb普通价格*100' AFTER `currentPrice`,
MODIFY COLUMN `cutPercent` tinyint(255) UNSIGNED NOT NULL DEFAULT 0 COMMENT '普通价格折扣*100' AFTER `currentPrice`,
MODIFY COLUMN `plusPrice` mediumint(9) NOT NULL DEFAULT -1 COMMENT 'plus价格*100' AFTER `cutPercent`,
ADD COLUMN `rmbPlusPrice` varchar(255) NULL COMMENT 'rmbPlus价格*100' AFTER `plusPrice`,
ADD COLUMN `plusCutPercent` tinyint(255) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'plus价格折扣*100' AFTER `rmbPlusPrice`;