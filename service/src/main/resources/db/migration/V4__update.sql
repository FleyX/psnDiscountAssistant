ALTER TABLE `psn`.`user`
CHANGE COLUMN `wxId` `openId` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '微信id' ,
CHANGE COLUMN `username` `nickName` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '昵称' ,
CHANGE COLUMN `password` `icon` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'icon';