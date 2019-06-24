<template>
  <form @submit="formSubmit" :report-submit="true">
    <button style="border:0;padding:0" plain="true" form-type="submit" @click="toDetail">
      <div style="overflow:hidden">
        <div class="main">
          <!-- 左边图像 -->
          <div class="left-icon">
            <img :src="item.icon+'?w=300&h=300'">
            <van-tag v-if="item.isLowest==1" style="position:absolute;left:0;bottom:5%" type="primary">史地</van-tag>
            <van-tag v-if="item.isLowest==2" style="position:absolute;left:0;bottom:5%" type="primary">持平史地</van-tag>
          </div>
          <!-- 右边内容区域 -->
          <div class="right">
            <p class="name" v-if="item.zhName.length>0">{{ item.zhName }}</p>
            <p class="name" v-else>{{ item.name }}</p>
            <!-- <div> -->
            <!-- 评分 -->
            <div class="v-center">
              <van-rate style="display:inline-block;" readonly :size="15" :value="item.intPsnScore"></van-rate>
              <span class="score">&nbsp;{{ item.psnScore / 100 }}&nbsp;</span>
              <span class="score-num">&nbsp;{{ item.psnScoreNum }}人评</span>
            </div>
            <!-- 类别，语言 -->
            <div>{{ item.type }}&emsp;{{ language }}</div>
            <!-- 展示折扣比例和价格 -->
            <div class="v-center">
              <span v-if="item.cutPercent>0">
                <van-tag>- {{ item.cutPercent }}%</van-tag>&nbsp;&nbsp;
              </span>
              <span v-if="item.isPlus==1 && item.plusCutPercent>0">
                <van-tag color="yellow" text-color="black">- {{ item.plusCutPercent }}%</van-tag>&nbsp;&nbsp;
              </span>
              <span :class="{'invalid-price':item.cutPercent>0}">{{serverInfo.moneySymbol}}{{ item.originPrice / 100 }}</span>
              &emsp;
              <span v-if="item.cutPercent>0" class="over-time">{{overTime}}</span>
            </div>
            <!-- 展示折扣价和会员价 -->
            <div style="font-size:0.9em" class="v-center">
              <span v-if="item.cutPercent>0">{{serverInfo.moneySymbol}}{{ item.currentPrice / 100 }}&emsp;</span>
              <span v-if="item.isPlus==1 && item.plusCutPercent>0" class="plus">{{serverInfo.moneySymbol}}{{ item.plusPrice / 100 }}</span>
            </div>
            <!-- </div> -->
          </div>
        </div>
        <div class="line"></div>
      </div>
    </button>
    <ad v-if="index >0 && index%8==0" unit-id="adunit-27e3072f321dd8b2"></ad>
  </form>
</template>

<script>
import moment from "moment";
export default {
  //折扣游戏单个项目
  name: "cutListItem",
  props: { item: Object, serverInfo: Object, index: Number },
  data() {
    return {};
  },
  computed: {
    language() {
      if (this.item != undefined && this.item != null) {
        return this.item.language.replace(/,/g, "");
      } else {
        return "";
      }
    },
    overTime() {
      if (this.item.cutOverTime == 0) {
        return "";
      } else {
        return moment(this.item.cutOverTime).format("MM-DD HH:mm") + "截止";
      }
    }
  },
  methods: {
    formSubmit(e) {
      let item = {
        value: e.mp.detail.formId,
        expireTime: Date.now() + 7 * 24 * 60 * 60 * 1000
      };
      this.globalData.formIdList.push(item);
    },
    toDetail() {
      wx.navigateTo({
        url: `/pages/index/detail/main?id=${this.item.id}&serverId=${
          this.serverInfo.serverId
          }`
      });
    }
  }
};
</script>

<style scoped>
.main {
  width: 98%;
  margin-left: 1%;
  margin-right: 1%;
  margin-bottom: 20rpx;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  font-size: 30rpx;
  line-height: 1.2em;
  text-align: left;
}
.left-icon {
  width: 200rpx;
  height: 200rpx;
  position: relative;
}

.left-icon > img {
  width: 100%;
  height: 100%;
  border-radius: 5%;
}

.right {
  width: 520rpx;
}
.name {
  font-size: 1.1em;
  line-height: 1.5em;
  font-weight: 600;
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
}
.score {
  font-size: 0.6em;
  color: #ddd7dc;
}
.score-num {
  font-size: 0.8em;
}
.invalid-price {
  font-size: 0.8em;
  text-decoration: line-through;
}
.plus {
  background: yellow;
}
.over-time {
  font-size: 0.7em;
}
</style>
