<template>
  <div>
    <van-dialog id="dialog"></van-dialog>
    <div v-if="isSuccess" class="page-main container main">
      <van-toast id="toast"/>
      <!-- 顶部，图标，基础信息 -->
      <div class="h-between" style="margin-bottom:20rpx">
        <div class="left">
          <div v-if="detail.zhName.length>0" class="title">{{detail.zhName}}</div>
          <div v-else class="title">{{detail.name}}</div>
          <div v-if="detail.zhName.length>0" style="font-size:0.7em">商店名称：{{detail.name}}</div>
          <div class="language">{{language}} &nbsp;{{detail.type}} &nbsp;{{publishTime}}</div>
          <div class="v-center">
            <van-rate style="display:inline-block" readonly :size="15" :value="detail.intPsnScore"></van-rate>
            <span class="score">&nbsp;{{ detail.psnScore / 100 }}&nbsp;</span>
            <span class="score-num">&nbsp;{{ detail.psnScoreNum }}人评</span>
          </div>
          <!-- 史地提示 -->
          <vant-tag v-if="detail.isLowest==1" class="lowest">史低！</vant-tag>
          <vant-tag v-if="detail.isLowest==2" class="lowest">持平史低！</vant-tag>
        </div>
        <img class="icon" :src="detail.icon+'?w=300&h=300'">
      </div>
      <div class="line"></div>
      <!-- 价格 -->
      <div v-if="detail.originPrice>0" style="margin-bottom:30rpx">
        <div>
          <span>原价: {{param.moneySymbol}}{{detail.originPrice/100}}</span>
          &emsp;
          史低: {{param.moneySymbol}}
          <span style="color:red">{{minPrice}}</span>
        </div>
        <!-- 当前折扣价格 -->
        <div class="v-center" v-if="detail.cutPercent>0">
          当前:
          <span>{{param.moneySymbol}}{{detail.currentPrice/100}}</span>
          &nbsp;
          <span>￥{{detail.rmbPrice/100}}</span>
          &nbsp;
          <van-tag>- {{detail.cutPercent}}%</van-tag>&nbsp;
          <span class="over-time">{{overTime}}</span>
        </div>
        <!-- plus折扣信息 -->
        <div class="v-center" v-if="detail.isPlus && detail.plusCutPercent>0">
          plus:
          <span>{{param.moneySymbol}}{{detail.plusPrice/100}}</span>
          &nbsp;
          <span>￥{{detail.rmbPlusPrice/100}}</span>
          <van-tag color="yellow" text-color="black">- {{ detail.plusCutPercent }}%</van-tag>
        </div>
        <div v-if="attention" class="attention">
          <div v-if="attention.type=='once_cut'">一旦本游戏打折，将通过微信/邮件通知你</div>
          <div v-if="attention.type=='lowest'">一旦本游戏史低/持平史低，将通过微信/邮件通知你</div>
          <div
            v-if="attention.type=='lower'"
          >一旦本游戏价格低于:{{param.moneySymbol}}{{attention.value/100}},将通过微信/邮件通知你</div>
        </div>
      </div>
      <div v-else style="margin-bottom:30rpx">免费--free</div>

      <div class="line"></div>


      <!-- 历史价格 -->
      <priceHistory v-if="!isShow" :history="history" :moneySymbol="param.moneySymbol"></priceHistory>

      <!-- 广告 -->
      <ad unit-id="adunit-27e3072f321dd8b2"></ad>

      <div class="line"></div>
      <!-- 详细介绍 -->
      <div class="detail" style>详情</div>
      <wxParse :content="detail.description"></wxParse>

      <div class="line" style="margin-top:30rpx"></div>
      <!-- 相关游戏 -->
      <div v-if="children.length>0" class="detail" style>相关游戏</div>
      <scroll-view scroll-x="true" style="display:flex;align-items:flex-start">
        <childrenGameItem v-for="item in children" :key="item.id" :data="item"></childrenGameItem>
      </scroll-view>
    </div>
    <!-- 底部导航栏 -->
    <form @submit="formSubmit" :report-submit="true" class="bottom">
      <button style="border:0;display:inline-block" plain="true" form-type="submit" @click="back">
        <van-button round type="primary" size="small" @click="back">返回</van-button>
      </button>
      <button
        style="border:0;display:inline-block"
        plain="true"
        form-type="submit"
        @click="watchGame"
      >
        <van-button round type="danger" size="small">{{watchText}}</van-button>
      </button>
      <button
        style="border:0;display:inline-block"
        plain="true"
        form-type="submit"
        open-type="share"
      >
        <van-button round type="info" size="small">分享</van-button>
      </button>
    </form>

    <!-- 关注详情 -->
    <van-popup :show="isShow">
      <chooseRemindType
        :currentPrice="detail.originPrice"
        :moneySymbol="param.moneySymbol"
        @close="isShow=false"
        @change="dataChange"
      ></chooseRemindType>
    </van-popup>
  </div>
</template>

<script>
import wxParse from "mpvue-wxparse";
import moment from "moment";
import chooseRemindType from "@/components/game/chooseRemindType";
import childrenGameItem from "@/components/game/childrenGameItem";
import priceHistory from "@/components/game/priceHistory";
import * as FeedBackUtil from "../../../utils/feedBackUtil.js";
import utils from "../../../utils";

export default {
  components: {
    chooseRemindType,
    childrenGameItem,
    wxParse,
    priceHistory
  },
  data() {
    return {
      isSuccess: false,
      detail: {},
      history: [],
      children: [],
      //server info
      param: {},
      //gameId
      id: "",
      //是否已经关注本游戏
      attention: null,
      isShow: false
    };
  },
  computed: {
    language() {
      if (this.detail.language != undefined)
        return this.detail.language.replace(/,/g, "");
      else return "";
    },
    publishTime() {
      return moment(this.detail.publishTime).format("YYYY-MM-DD");
    },
    minPrice() {
      let min = 100000000;
      this.history.forEach(
        item => (item.price < min ? (min = item.price) : null)
      );
      return min / 100;
    },
    overTime() {
      let overTime = this.detail.cutOverTime;
      if (overTime != null && overTime > 0) {
        return moment(overTime).format("MM-DD HH:mm") + "截止";
      } else {
        return "";
      }
    },
    watchText() {
      return this.attention ? "取消提醒" : "折扣提醒";
    }
  },
  onShow() {
    let pages = getCurrentPages();
    let param = pages[pages.length - 1].options;
    this.attention = null;
    this.param = this.globalData.serverList.find(
      item => item.serverId == param.serverId
    );
    this.id = param.id;
    this.$http.get("/game/getGameDetail?gameId=" + param.id).then(res => {
      res.detail.intPsnScore = Math.round(res.detail.psnScore / 100);
      this.children = res.children;
      this.detail = res.detail;
      this.history = res.history;
      this.attention = res.attention;
      this.isSuccess = true;
    });
  },
  onUnload() {
    this.detail = {};
    this.history = [];
    this.isSuccess = false;
  },
  //分享页面
  onShareAppMessage() {
    let redirect = `/pages/index/detail/main?id=${this.id}&serverId=${
      this.param.serverId
    }`;
    let path = "/pages/init/main?redirect=" + utils.base64_encode(redirect);
    console.log(path);
    return {
      title: this.detail.name,
      desc: "游戏详情，来自--psn折扣小助手",
      path
    };
  },
  methods: {
    formSubmit(e) {
      let item = {
        value: e.mp.detail.formId,
        expireTime: Date.now() + 7 * 24 * 60 * 60 * 1000
      };
      this.globalData.formIdList.push(item);
    },
    watchGame() {
      if (this.attention) {
        this.$http.delete(`/attention_price/${this.id}`).then(() => {
          FeedBackUtil.showToast("toast", "success", "取消成功", false);
          this.attention = null;
        });
      } else {
        this.isShow = true;
      }
    },
    dataChange(data) {
      data.gameId = this.id;
      this.$http.put("/attention_price", data).then(res => {
        let userInfo = this.globalData.userInfo;
        FeedBackUtil.showToast("toast", "success", "关注成功", false);
        this.attention = {
          type: data.type,
          value: data.value
        };
        this.isShow = false;
      });
    },
    back() {
      if (getCurrentPages().length <= 1) {
        wx.reLaunch({ url: "/pages/index/main" });
      } else {
        wx.navigateBack({ delta: 1 });
      }
    }
  }
};
</script>
<style scoped>
.main {
  margin-bottom: 140rpx;
}
.title {
  font-size: 1.1em;
  font-weight: 600;
}
.language {
  font-weight: 400;
  font-size: 0.8em;
}
.left {
  width: 500rpx;
}
.icon {
  width: 230rpx;
  height: 230rpx;
  border-radius: 7%;
}
.over-time {
  font-size: 0.8em;
  color: #dbd4d4;
}
.detail {
  font-weight: 600;
  margin-bottom: 10rpx;
  font-size: 1.1em;
}
.bottom {
  position: fixed;
  bottom: 0;
  width: 100%;
  background: white;
  padding-left: 0;
  padding-right: 0;
  padding-bottom: 20rpx;
  display: flex;
  justify-content: space-around;
}
.attention {
  font-size: 0.9em;
  color: #acacac;
}
.lowest {
  font-size: 1.1em;
  color: red;
  font-weight: 600;
}
</style>
