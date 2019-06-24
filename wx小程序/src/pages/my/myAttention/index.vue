<template>
  <div class="page-main">
    <filter :param="param" :count="count" isFixed showShort showCount @dataChange="dataChange"></filter>
    <div class="main"></div>
    <item v-for="item in list" :key="item.id" :item="item" :serverInfo="serverInfo"></item>
    <noMore v-if="isNoMore"></noMore>
    <button v-if="isNoMore==false&&isLoading==false" @click="getList">加载更多</button>
    <van-toast id="van-toast"></van-toast>
  </div>
</template>

<script>
import filter from "../../../components/game/filter";
import item from "../../../components/game/gameItem";
import noMore from "../../../components/game/noMore";
import * as feedBackUtil from "../../../utils/feedBackUtil.js";
import util from "../../../utils/index.js";
export default {
  data() {
    return {
      count: 0,
      isNoMore: false,
      toast: null,
      isLoading: true,
      list: [],
      serverInfo: {},
      param: {
        serverId: 1,
        platform: "ps4",
        orderKey: "cutPercent",
        isAsc: 0,
        language: "全部语言",
        start: 0,
        size: 10
      }
    };
  },
  components: {
    filter,
    item,
    noMore
  },
  mounted() {
    this.getServerInfo();
    setTimeout(this.refresh, 500);
  },
  methods: {
    dataChange(data) {
      let keys = Object.keys(data);
      let isRefresh = false;
      keys.forEach(item => {
        if (this.param[item] != data[item]) {
          this.param[item] = data[item];
          isRefresh = true;
        }
      });
      if (isRefresh) {
        this.getServerInfo();
        this.refresh();
      }
    },
    refresh() {
      this.isNoMore = false;
      this.isLoading = false;
      this.param.start = 0;
      this.list = [];
      this.getCount();
      this.getList();
    },
    getCount() {
      let url =
        `/game/getAttentionGameNum?serverId=${this.param.serverId}&platform=${
        this.param.platform
        }` + `&language=${this.param.language}`;
      this.$http.get(url).then(res => (this.count = res));
    },
    getList() {
      if (this.isLoading || this.isNoMore) {
        return;
      }
      this.isLoading = true;
      this.toast = feedBackUtil.showToast(
        "van-toast",
        "loading",
        "请稍后",
        false
      );
      this.$nextTick(function () {
        let str = util.objToUrlParam(this.param);
        this.$http
          .get(`/game/getAttentionGameList?${str}`)
          .then(res => {
            console.log(res);
            res.forEach(item => {
              item.intPsnScore = Math.round(item.psnScore / 100);
              this.list.push(item);
            });
            this.param.start += res.length;
            setTimeout(() => {
              if (res.length < this.param.size) {
                this.isNoMore = true;
              }
              this.clearToast();
            }, 100);
          })
          .catch(this.clearToast);
      });
    },
    getServerInfo() {
      let serverList = this.globalData.serverList;
      this.serverInfo = serverList.find(
        item => item.serverId == this.param.serverId
      );
    },
    clearToast() {
      this.isLoading = false;
      if (this.toast != null) {
        this.toast.clear();
      }
    }
  }
};
</script>

<style scoped>
.main {
  height: 55rpx;
}
</style>
