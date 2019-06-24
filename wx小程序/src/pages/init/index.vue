<template>
  <div>
    <!-- 初始化用 -->
    <div class="loading" v-if="isLoading">
      <van-loading type="spinner" color="#dddddd"></van-loading>
      <div>正在初始化，请稍候。。。</div>
    </div>
    <div class="tips" v-if="isFailed">
      <div>初始化失败</div>
      <van-button @click="init">重试</van-button>
    </div>
  </div>
</template>

<script>
import * as wxUtil from "../../utils/wxUtil.js";
import * as feedBackUtil from "../../utils/feedBackUtil.js";
import util from "../../utils";
export default {
  data() {
    return {
      isLoading: false,
      isFailed: false
    };
  },
  onLoad() {
    this.init();
    this.getConfig();
  },
  methods: {
    init() {
      this.isLoading = true;
      this.isFailed = false;
      let _this = this;
      let p1 = new Promise((resolve, reject) => {
        return _this.$http.get("/server/list")
          .then(res => resolve(res))
          .catch(err => reject(err));
      });
      let p2 = new Promise((resolve, reject) => {
        wx.login({
          success: function (res) {
            _this.$http
              .get("/user/login?code=" + res.code)
              .then(res => resolve(res))
              .catch(err => reject(err));
          },
          fail: function () {
            reject("登陆失败");
          }
        });
      });
      Promise.all([p1, p2])
        .then(res => {
          console.log(res);
          _this.globalData.serverList = res[0];
          _this.globalData["jwt-token"] = res[1]["jwt-token"];
          _this.globalData["userInfo"] = res[1]["userInfo"];
          let redirect = this.$root.$mp.query.redirect;
          let url;
          if (redirect) {
            url = util.base64_decode(redirect);
          } else {
            url = "/pages/index/main";
          }
          this.isLoading = false;
          wx.reLaunch({ url });
        })
        .catch(err => {
          this.isFailed = true;
          this.isLoading = false;
        });
    },
    //获取服务端所有配置
    getConfig(){

    }
  }
};
</script>

<style scoped>
.loading {
  text-align: center;
  margin-top: 30%;
}
.tips {
  margin-top: 30%;
  text-align: center;
}
</style>
