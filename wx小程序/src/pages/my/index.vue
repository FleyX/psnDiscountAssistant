<template>
  <div class="page-main">
    <!-- <van-toast id="toast"></van-toast> -->
    <van-cell title="个人信息" @click="goTo('/pages/my/personInfo/main')" is-link></van-cell>
    <van-cell title="我的关注" @click="goTo('/pages/my/myAttention/main')" is-link></van-cell>
    <van-cell title="关于" is-link @click="goTo('/pages/my/about/main')"></van-cell>
    <van-cell title="建议/吐槽" is-link @click="feedBack"></van-cell>
    <!-- <van-cell title="交流Q群:809862940" value="点击复制" @click="copyToBoard('809862940')"></van-cell> -->
  </div>
</template>

<script>
import * as feedBackUtil from "../../utils/feedBackUtil.js";
export default {
  data() {
    return {};
  },
  methods: {
    copyToBoard(str) {
      wx.setClipboardData({
        data: str,
        success: function () {
          feedBackUtil.showToast("toast", "success", "复制成功", false, false);
        }
      });
    },
    goTo(url) {
      wx.navigateTo({
        url
      });
    },
    feedBack() {
      wx.navigateToMiniProgram({
        appId: "wx8abaf00ee8c3202e",
        extraData: {
          id: "60315"
        }
      });
    }
  }
};
</script>

<style scoped>
.description {
  background-color: white;
  margin-bottom: 30rpx;
  padding-left: 1.5%;
  padding-right: 1.5%;
}

.item {
  line-height: 2em;
  width: 100%;
  vertical-align: middle;
  background-color: white;
  margin-bottom: 30rpx;
  padding-left: 1.5%;
  padding-right: 1.5%;
}
</style>
