ALTER TABLE `psn`.`cut_message`
  MODIFY COLUMN `isOver` tinyint(255) NOT NULL DEFAULT 0 AFTER `gameId`,
  MODIFY COLUMN `createTime` bigint(255) UNSIGNED NOT NULL DEFAULT 0 AFTER `isOver`,
  MODIFY COLUMN `overTime` bigint(20) UNSIGNED NOT NULL DEFAULT 0 AFTER `createTime`;