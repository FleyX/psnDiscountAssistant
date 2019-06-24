# 扫码查看实际效果：

![二维码](https://raw.githubusercontent.com/FleyX/files/master/blogImg/20190430103928.png)

# 为什么要这个

PS4 商店的网络实在是太不友好了，速度极慢，再加上游戏检索、折扣查看等功能并不太方便所以就做了这么一个小程序。每天定时从 ps4 商店获取最新的游戏价格，供大家查看，同时也可以设置折扣推送（通过邮件和微信通知).

# 开发语言介绍：

## 小程序

没有使用原生开发，而是用一个 vue 的框架：[mpvue](http://mpvue.com/mpvue/).此框架性能相比原生会差一些，实际开发中偶尔会有列表页卡死，无法滑动的情况出现。

http 请求库使用的[flyio](https://www.npmjs.com/package/flyio)

UI 库使用的是：有赞的小程序库,[vant](https://youzan.github.io/vant-weapp/#/intro)

## 爬虫

爬虫比较简单，分析港服商店页面获取 api，然后再用代码请求获取游戏的价格数据，存储到 mysql 数据库中。使用 node.js+node-schedule 实现。注意代码使用的是：**typescript**

## java 后台服务

java 后台基于 springboot+mysql+redis+jwt 实现。用于实现游戏查询，打着提醒推送等一系列功能。

# 部署

已经集成 docker，部署很简单，配好参数就行了。

1. 修改`dockerDeploy/jar_start.sh`文件，配置邮件服务器、小程序 id、secrt 等参数
2. 设置环境变量`MYSQL_PASS`,配置 mysql root 密码。
3. 启动 docker-compose,进入 dockerDeploy 目录，运行：`docker-compose up -d`

**注意：**首次运行需要创建数据库 mysql，在 mysql 启动后连接数据库，手动创建数据库`psn`，然后重启`docker-compose restart`。
