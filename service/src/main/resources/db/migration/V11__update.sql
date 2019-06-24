ALTER TABLE `game`
ADD COLUMN `isPlus`  tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否plus会员独享折扣' AFTER `plusCutPercent`;