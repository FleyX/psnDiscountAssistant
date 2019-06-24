<template>
  <div>
    <van-toast id="van-toast"></van-toast>
    <van-tabs @change="onChange" :duration="0.5" animated sticky>
      <van-tab title="折扣"/>
      <van-tab title="plus专享"/>
      <van-tab title="全部"/>
      <van-tab title="搜索"/>
    </van-tabs>
    <gameList v-if="active!='搜索'" :current="active"></gameList>
    <search v-else :current="active"></search>
  </div>
</template>

<script>
import gameList from "@/components/game/gameList";
import search from "@/components/game/search";
import * as wxUtil from "@/utils/wxUtil";
export default {
  components: {
    gameList,
    search
  },
  data() {
    return {
      active: "折扣"
    };
  },
  onLoad() {
    console.log("主页加载了");
    this.active = "折扣";
  },
  // onPullDownRefresh() {
  //   console.log("捕获到下拉动作");
  // },
  // onReachBottom() {
  //   this.globalData.eventBus.$emit("reachBottom");
  // },
  methods: {
    onChange(event) {
      this.active = event.mp.detail.title;
    }
  }
};
</script>
<style scoped></style>
