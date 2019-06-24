-- 创建url表
CREATE TABLE `psn`.`url`  (
  `urlId` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `method` enum('GET','POST','PUT','DELETE') NOT NULL DEFAULT 'GET',
  `url` varchar(255) NOT NULL,
  `type` enum('public','private') NOT NULL DEFAULT 'public',
  PRIMARY KEY (`urlId`)
);