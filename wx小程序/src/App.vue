<script>
export default {
  created() {
    // 调用API从本地缓存中获取数据
    /*
     * 平台 api 差异的处理方式:  api 方法统一挂载到 mpvue 名称空间, 平台判断通过 mpvuePlatform 特征字符串
     * 微信：mpvue === wx, mpvuePlatform === 'wx'
     * 头条：mpvue === tt, mpvuePlatform === 'tt'
     * 百度：mpvue === swan, mpvuePlatform === 'swan'
     * 支付宝(蚂蚁)：mpvue === my, mpvuePlatform === 'my'
     */

    let logs;
    if (mpvuePlatform === "my") {
      logs = mpvue.getStorageSync({ key: "logs" }).data || [];
      logs.unshift(Date.now());
      mpvue.setStorageSync({
        key: "logs",
        data: logs
      });
    } else {
      logs = mpvue.getStorageSync("logs") || [];
      logs.unshift(Date.now());
      mpvue.setStorageSync("logs", logs);
    }
  },
  log() {
    console.log(`log at:${Date.now()}`);
  }
};
</script>

<style>
* {
  transition: width 2s;
  -moz-transition: width 2s;
  -webkit-transition: width 2s;
  -o-transition: width 2s;
}
page {
  background-color: #f1f4f8;
}

.h-center {
  display: flex;
  justify-content: center;
}
.h-between {
  display: flex;
  justify-content: space-between;
}
.v-center {
  display: flex;
  align-items: center;
}

.v-bottom {
  display: flex;
  align-items: flex-start;
}

.page-main {
  width: 100%;
  font-size: 30rpx;
  margin-bottom: 60rpx;
}
.container {
  width: 97%;
  padding-left: 1.5%;
  padding-right: 1.5%;
}

.line {
  width: 200%;
  position: relative;
  left: -10%;
  height: 7.5rpx;
  background-color: white;
  margin-bottom: 20rpx;
}
.icon-center {
  top: 5rpx;
  position: relative;
}
</style>
