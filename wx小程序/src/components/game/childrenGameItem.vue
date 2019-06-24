<template>
  <form @submit="formSubmit" :report-submit="true" class="child-item">
    <button style="border:0;padding:0;line-height:1em" plain="true" form-type="submit" @click="toDetail">
      <div class="img-div">
        <img :src="data.icon+'?w=200&h=200'" />
      </div>
      <div style="font-size:0.7em">{{data.name}}</div>
    </button>
  </form>
</template>

<script>
export default {
  name: "childrenGameItem",
  props: {
    data: Object
  },
  methods: {
    formSubmit(e) {
      console.log("gggg");
      let item = {
        value: e.mp.detail.formId,
        expireTime: Date.now() + 7 * 24 * 60 * 60 * 1000
      };
      this.globalData.formIdList.push(item);
    },
    toDetail() {
      let url = `/pages/index/detail/main?id=${this.data.id}&serverId=${
        this.data.serverId
      }`;
      wx.navigateTo({ url });
    }
  }
};
</script>

<style .scoped>
.child-item {
  display: inline-flex;
  width: 240rpx;
}
.img-div {
  width: 220rpx;
  height: 220rpx;
  margin: 0 auto;
}
.img-div > img {
  width: 100%;
  height: 100%;
  border-radius: 5%;
}
</style>
