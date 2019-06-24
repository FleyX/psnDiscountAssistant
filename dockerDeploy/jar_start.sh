#!/bin/bash

# 配置spring boot
cmd="
java -jar 
-Dspring.profiles.active=prod 
-Dmybatis.configuration.log-impl=org.apache.ibatis.logging.nologging.NoLoggingImpl 
-Dspring.mail.host=
-Dspring.mail.username=
-Dspring.mail.password=
-Dspring.mail.properties.mail.smtp.port=
-Dspring.datasource.druid.password=${MYSQL_PASS} 
-Dspring.datasource.druid.url=jdbc:mysql://psn-mysql:3306/psn?useUnicode=true&characterEncoding=utf-8&useSSL=false 
-Dwx.appId=
-Dwx.secret=
-Dspring.redis.host=psn-redis 
service.jar
"
#cmd="$(echo $cmd)"
$cmd
