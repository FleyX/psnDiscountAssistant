-- 创建消息推送id表
CREATE TABLE `psn`.`form_key`  (
  `formKeyId` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `userId` int(0) NOT NULL COMMENT '用户id',
  `value` varchar(255) NOT NULL COMMENT 'formId值',
  `expireTime` bigint(0) NOT NULL COMMENT '过期时间',
  PRIMARY KEY (`formKeyId`)
) COMMENT = '消息推送id表，用于给用户推送模板消息';