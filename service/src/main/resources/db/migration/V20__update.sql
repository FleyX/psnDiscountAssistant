-- game表新增基本索引
ALTER TABLE `psn`.`game`
  DROP INDEX `asdf`,
  ADD INDEX `valid_serverId_platform_index`(`serverId`, `platform`, `valid`) USING BTREE;

-- language表新增索引
ALTER TABLE `psn`.`language`
  ADD INDEX `language_index`(`language`) USING BTREE;

-- 删除user表无用字段
ALTER TABLE `psn`.`user`
  DROP COLUMN `nickName`,
  DROP COLUMN `icon`;
