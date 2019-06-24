update game set rmbPlusPrice=0;
ALTER TABLE `psn`.`game`
MODIFY COLUMN `rmbPrice` mediumint(10) UNSIGNED NOT NULL COMMENT 'rmb普通价格*100' AFTER `currentPrice`,
MODIFY COLUMN `plusPrice` mediumint(9) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'plus价格*100' AFTER `cutPercent`,
MODIFY COLUMN `rmbPlusPrice` mediumint(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'rmbPlus价格*100' AFTER `plusPrice`;