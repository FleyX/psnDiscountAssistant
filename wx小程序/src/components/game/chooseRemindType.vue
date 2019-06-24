<template>
  <div class="main">
    <van-radio-group :value="radio" @change="chooseChange">
      <van-radio name="once_cut">只要打折就提醒</van-radio>
      <van-radio name="lowest">史低或持平史低提醒</van-radio>
      <van-radio name="lower">
        <div style="display:flex;align-items: center;">
          <div>低于{{moneySymbol}}</div>
          <input class="input" type="number" @change="priceChange">
          <div>提醒</div>
        </div>
      </van-radio>
    </van-radio-group>
    <div class="btns">
      <van-button type="default" size="small" @click="close">取消</van-button>
      <van-button type="info" size="small" @click="submit">确定</van-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "chooseRemindType",
  props: ["moneySymbol"],
  data() {
    return {
      radio: "once_cut",
      price: 0
    };
  },
  methods: {
    chooseChange(event) {
      let type = event.mp.detail;
      this.radio = type;
    },
    close() {
      this.$emit("close");
    },
    priceChange(event) {
      console.log(event);
      this.price = event.mp.detail.value;
    },
    submit() {
      let res = { type: this.radio, value: this.price * 100 };
      this.$emit("change", res);
    }
  }
};
</script>

<style scoped>
.main {
  width: 500rpx;
  margin: 50rpx;
  z-index: 101;
}
.btns {
  padding-top: 20rpx;
  display: flex;
  justify-content: space-between;
}
.input {
  border: 1px solid black;
  display: inline-block;
  position: relative;
  height: 1.2em;
  width: 4em;
}
</style>
