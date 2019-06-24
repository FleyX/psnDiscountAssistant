<template>
  <div class="page-main">
    <van-notify id="van-notify"></van-notify>
    <van-toast id="toast"></van-toast>
    <van-tabs active="current" @change="onChange">
      <van-tab title="邮箱"></van-tab>
      <van-tab title="手机号"></van-tab>
    </van-tabs>
    <van-cell-group>
      <van-field
        :value="inputValue"
        clearable
        @change="inputChange"
        :label="current"
        :placeholder="'请输入新'+current"
      />
      <van-field
        :value="verifyCode"
        center
        clearable
        label="验证码"
        placeholder="输入6位验证码"
        @change="verifyCodeChange"
        :border="false"
        use-button-slot
      >
        <van-button
          slot="button"
          :disabled="isCountDown"
          size="small"
          type="primary"
          @click="getCode"
        >{{countDown}}</van-button>
      </van-field>
    </van-cell-group>
    <div v-if="current=='邮箱'">注意：邮件有可能会被加入到垃圾箱，请检查垃圾箱，并将邮件地址加入白名单</div>
    <div class="h-center" style="padding-top:50rpx">
      <van-button type="primary" size="small" @click="submit">提交</van-button>
    </div>
  </div>
</template>

<script>
import * as feedBackUtil from "../../../../utils/feedBackUtil.js";
export default {
  data() {
    return {
      current: "邮箱",
      type: "email",
      countDown: "获取验证码",
      isCountDown: false,
      timer: null,
      inputValue: "",
      verifyCode: ""
    };
  },
  methods: {
    inputChange(event) {
      this.inputValue = event.mp.detail;
    },
    verifyCodeChange(event) {
      this.verifyCode = event.mp.detail;
    },
    onChange(event) {
      this.current = event.mp.detail.title;
      this.type = this.current == "邮箱" ? "email" : "phone";
      this.inputValue = "";
      this.verifyCode = "";
    },
    //获取验证码
    getCode() {
      if (this.isCountDown) {
        return;
      }
      let isOk;
      if (this.current == "邮箱") {
        isOk = /^.+@.+$/.test(this.inputValue);
      } else {
        isOk = /^\d{11}$/.test(this.inputValue);
      }
      if (!isOk) {
        feedBackUtil.showErrorNotify("输入不合法，请检查");
        return;
      }
      let url = `/user/sendVerifyCode?type=${
        this.type
      }&value=${this.inputValue.trim()}`;
      //开始倒计时
      let count = 60;
      this.countDown = count + "秒后重试";
      this.isCountDown = true;
      this.timer = setInterval(() => {
        count--;
        if (count == 0) {
          this.clear();
        } else {
          this.countDown = count + "秒后重试";
        }
      }, 1000);
      this.$http
        .get(url)
        .then(() => {
          feedBackUtil.showToast("toast", "success", "发送成功", false);
        })
        .catch(() => {
          this.clear();
        });
    },
    clear() {
      this.countDown = "获取验证码";
      this.isCountDown = false;
      clearInterval(this.timer);
    },
    // 提交数据
    submit() {
      if (!/^.{6}$/.test(this.verifyCode)) {
        feedBackUtil.showErrorNotify("请输入6位验证码");
        return;
      }
      let body = {
        value: this.inputValue.trim(),
        type: this.type,
        verifyCode: this.verifyCode.trim()
      };
      this.$http.post("/user/updateContactInfo", body).then(() => {
        feedBackUtil.showToast("toast", "success", "修改成功", false);
        this.globalData.userInfo[this.type] = this.inputValue.trim();
        setTimeout(() => {
          wx.navigateBack({
            delta: 1
          });
        }, 1000);
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
