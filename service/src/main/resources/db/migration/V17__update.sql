-- 新建配置表
CREATE TABLE `psn`.`config`  (
  `configId` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `key` varchar(255) NOT NULL COMMENT '配置键',
  `value` varchar(255) NOT NULL COMMENT '值',
  `type` varchar(255) NOT NULL COMMENT '值类型',
  PRIMARY KEY (`configId`)
)ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- 获取配置url加入到权限中
insert into url(method,url,type) value('GET','/config','public');