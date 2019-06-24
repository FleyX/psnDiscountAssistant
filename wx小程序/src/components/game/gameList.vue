<template>
  <div class="page-main">
    <filter isFixed showSort showType :param="param" :count="count" @dataChange="dataChange"></filter>
    <div style="height:1.5rem"></div>
    <cutListItem v-for="(item,index) in list" :key="index" :index="index" :item="item" :serverInfo="serverInfo"></cutListItem>
    <noMore v-if="isNoMore"></noMore>
    <button v-if="isNoMore==false && isLoading==false" @click="getGameList">加载更多</button>
  </div>
</template>

<script>
// 折扣游戏列表
import filter from "./filter";
import cutListItem from "./gameItem";
import noMore from "./noMore";
import util from "@/utils/index.js";
import * as feedBackUtil from "../../utils/feedBackUtil.js";
export default {
  name: "cutList",
  props: ["current"],
  components: {
    cutListItem,
    filter,
    noMore
  },
  data() {
    return {
      isLoading: true,
      toast: null,
      count: 0,
      isNoMore: false,
      param: {
        serverId: 1,
        platform: "PS4",
        isCut: 1, //0非折扣，1普通折扣，2plus专属
        orderKey: "psnScoreNum",
        isAsc: 0,
        type: "全部类别",
        language: "全部语言",
        start: 0,
        size: 15
      },
      list: [],
      serverInfo: {},
      inSearch: false
    };
  },
  mounted() {
    setTimeout(this.refresh,500);
  },
  watch: {
    current: function (newValue, oldValue) {
      this.globalData.eventBus.$emit("refreshFilter");
      this.$set(this.param, "orderKey", "psnScoreNum");
      this.$set(this.param, "language", "全部语言");
      this.$set(this.param, "type", "全部类别");
      this.$set(this.param, "platform", "PS4");
      this.$set(this.param, "serverId", 1);
      this.$set(this.param, "isAsc", 0);
      this.$set(
        this.param,
        "isCut",
        this.current == "折扣" ? 1 : this.current == "plus专享" ? 2 : 0
      );
      this.refresh();
    }
  },
  methods: {
    getServer(serverList) {
      serverList = this.globalData.serverList;
      this.serverInfo = serverList.find(
        item => item.serverId == this.param.serverId
      );
    },
    dataChange(data) {
      console.log(data);
      let keys = Object.keys(data);
      let isRefresh = false;
      keys.forEach(item => {
        if (this.param[item] != data[item]) {
          this.$set(this.param, item, data[item]);
          isRefresh = true;
        }
      });
      if (isRefresh) {
        this.refresh();
      }
    },
    refresh() {
      this.list = [];
      this.count = 0;
      this.$set(this.param, "start", 0);
      this.isNoMore = false;
      this.isLoading = false;
      this.getServer();
      this.getGameCount();
      this.getGameList();
    },
    getGameCount() {
      let url =
        `/game/getGameNum?serverId=${this.param.serverId}&platform=${
        this.param.platform
        }` +
        `&type=${this.param.type}&isCut=${this.param.isCut}&language=${
        this.param.language
        }`;
      this.$http.get(url).then(res => (this.count = res));
    },
    getGameList() {
      if (this.isLoading || this.isNoMore) {
        return;
      }
      this.isLoading = true;
      this.toast = feedBackUtil.showToast("van-toast", "loading", "请稍后", false);
      this.$nextTick(function () {
        let str = util.objToUrlParam(this.param);
        this.$http.get(`/game/getGameList?${str}`)
          .then(res => {
            res.forEach(item => {
              item.intPsnScore = Math.round(item.psnScore / 100);
              this.list.push(item);
            });
            this.param.start += res.length;
            if (res.length < this.param.size) {
              this.isNoMore = true;
            }
            this.clearToast();
          })
          .catch(err => {
            this.clearToast();
          });
      });
    },
    clearToast() {
      if (this.toast != null) {
        this.isLoading = false;
        this.toast.clear();
      }
    }
  }
};
</script>

<style scoped>
</style>
