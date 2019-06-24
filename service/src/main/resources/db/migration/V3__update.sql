ALTER TABLE `psn`.`language`
CHANGE COLUMN `gId` `gameId` int(10) UNSIGNED NOT NULL COMMENT 'game ID' FIRST,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`gameId`, `language`) USING BTREE;

ALTER TABLE `psn`.`price_history`
CHANGE COLUMN `gId` `gameId` int(10) UNSIGNED NOT NULL COMMENT '游戏id' AFTER `date`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`gameId`, `date`) USING BTREE;