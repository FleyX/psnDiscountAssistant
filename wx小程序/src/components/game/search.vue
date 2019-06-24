<template>
  <v>
    <div class="fixed">
      <van-search :value="searchStr" :focus="searchFocus" placeholder="请输入关键词(空格分隔)" use-action-slot @search="onSearch" @click="searchFocus=false" @change="searchChange">
        <view slot="action" @click="onSearch">搜索</view>
      </van-search>
      <filter :param="param" @dataChange="dataChange"></filter>
    </div>
    <div class="list">
      <ad v-if="result.length>0" unit-id="adunit-27e3072f321dd8b2"></ad>
      <gameItem v-for="item in result" :key="item.id" :item="item" :serverInfo="serverInfo"></gameItem>
    </div>
    <div v-if="isSearching" class="h-center">
      <van-loading></van-loading>
    </div>
    <div v-if="isEmpty">未搜索到结果</div>
  </v>
</template>

<script>
import filter from "@/components/game/filter";
import gameItem from "@/components/game/gameItem";
import Toast from "../../../static/vant/toast/toast";
export default {
  name: "search",
  props: ["current"],
  components: {
    filter,
    gameItem
  },
  mounted() {
    this.getServer();
  },
  data() {
    return {
      searchFocus: false,
      searchStr: "",
      isEmpty: false,
      isSearching: false,
      result: [],
      param: {
        serverId: 1,
        platform: "PS4",
        language: "全部语言"
      }
    };
  },
  methods: {
    getServer() {
      let serverList = this.globalData.serverList;
      this.serverInfo = serverList.find(
        item => item.serverId == this.param.serverId
      );
    },
    dataChange(data) {
      let keys = Object.keys(data);
      keys.forEach(item => (this.param[item] = data[item]));
      this.getServer();
    },
    searchChange(event) {
      this.searchStr = event.mp.detail;
    },
    onSearch() {
      if (this.searchStr.trim().length == 0) {
        //弹出提醒
        Toast("输入为空");
        return;
      }
      if (this.isSearching) {
        return;
      }
      this.isSearching = true;
      this.isEmpty = false;
      this.searchFocus = false;
      this.result = [];
      let url =
        `/game/search?serverId=${this.param.serverId}&` +
        `platform=${this.param.platform}&language=${this.param.language}` +
        `&searchStr=${this.searchStr}*`;
      console.log(url);
      this.$http
        .get(url)
        .then(res => {
          if (res.length == 0) {
            this.isEmpty = true;
          }
          res.forEach(
            item => (item.intPsnScore = Math.round(item.psnScore / 100))
          );
          this.result = res;
          this.isSearching = false;
          this.searchFocus = false;
        })
        .catch(err => (this.isSearching = false));
    }
  }
};
</script>

<style scoped>
.fixed {
  position: fixed;
  width: 100%;
  z-index: 1000;
}

.list {
  padding-top: 150rpx;
}
</style>
