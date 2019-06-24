<template>
  <div class="page-main">
    <van-cell :title="str"></van-cell>
    <van-cell title="微信: f96123456" value="点击复制" @click="copyToBoard('f96123456')"></van-cell>
    <van-cell v-if="showPrivate" title="qq群: " value="点击复制" @click="copyToBoard('')"></van-cell>
    <van-cell>
      <div slot="title">
        <div class="title">版本历史</div>
        <div class="item" v-for="(item,index) in versionList" :key="index">
          <span class="item-title">{{item.title}}:&nbsp;&nbsp;</span>
          <span class="item-content">{{item.content}}</span>
        </div>
      </div>
    </van-cell>
  </div>
</template>

<script>
import * as feedBackUtil from "../../../utils/feedBackUtil.js";
export default {
  name: "about",
  data() {
    return {
      str:
        "本小程序是个人业余时间开发的，时间，精力有限，做的不好的地方希望能够指出，有什么建议也可联系我,我会尽量满足的。另外：有兴趣一起开发的也可以联系我!",
      showPrivate: false,
      versionList: [
        {
          title: "V1.0",
          content: "实现基本功能：价格查询，折扣推送(邮件，微信提醒)"
        }
      ]
    };
  },
  methods: {
    copyToBoard(str) {
      wx.setClipboardData({
        data: str,
        success: function() {
          feedBackUtil.showToast("toast", "success", "复制成功", false, false);
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
  padding-bottom: 0.5em;
}
.title {
  font-weight: 600;
  font-size: 1.2em;
}
.item-title {
  font-weight: 600;
}
</style>
