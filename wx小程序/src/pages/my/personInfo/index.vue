<template>
  <div class="page-main">
    <!-- <div class="title">基本信息</div>
    <van-cell-group>
      <van-cell
        title="微信名"
        :value="personInfo.nickName"
      ></van-cell>
      <van-cell title="头像">
        <view slot="value">
          <van-icon :src="personInfo.icon"></van-icon>
        </view>
      </van-cell>
    </van-cell-group> -->
    <div class="container h-between title">
      <div>联系方式</div>
      <div @click="changeInfo">
        编辑
        <van-icon
          name="edit"
          class="icon-center"
        ></van-icon>
      </div>
    </div>
    <van-cell-group>
      <van-cell
        title="邮箱"
        :value="personInfo.email"
      ></van-cell>
      <van-cell
        title="手机号"
        :value="personInfo.phone"
      ></van-cell>
      <div class="send-info">默认会通过微信提醒来发送打折信息，但是微信提醒有一个限制是用户如果超过7天不打开小程序，就不能向这个用户发送消息提醒了.所以使用邮箱做一个补充(手机号还不支持)</div>

    </van-cell-group>
  </div>
</template>

<script>
export default {
  data() {
    return {
      personInfo: null
    };
  },
  onShow() {
    this.personInfo = this.globalData.userInfo;
  },
  methods: {
    changeInfo() {
      wx.navigateTo({ url: "/pages/my/personInfo/editInfo/main" });
    }
  }
};
</script>

<style scoped>
.icon-img {
  width: 250rpx;
  height: 250rpx;
}
.title {
  margin-bottom: 10rpx;
  margin-top: 30rpx;
  font-size: 1em;
}
.send-info {
  font-size: 0.9em;
  color: #bec1b4;
  text-align: center;
}
</style>
