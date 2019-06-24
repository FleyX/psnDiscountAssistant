-- 修改value值为数字
ALTER TABLE `psn`.`attention_price`
MODIFY COLUMN `value` mediumint(8) NULL DEFAULT NULL AFTER `type`;