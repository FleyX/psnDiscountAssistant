-- 给user表新增最后登录时间字段
ALTER TABLE `psn`.`user` ADD COLUMN `lastLoginTime` bigint(0) UNSIGNED NOT NULL DEFAULT 0 AFTER `phone`;