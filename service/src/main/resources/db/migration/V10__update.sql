ALTER TABLE `game`
MODIFY COLUMN `rmbPrice`  mediumint(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'rmb普通价格*100' AFTER `currentPrice`;