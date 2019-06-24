<template>
  <div style="width:100%;height:500rpx;position:relative">
    <van-button type="primary" size="mini" @click="change" style="position:absolute;right:5%;top:5rpx;z-index:100">切换</van-button>
    <mpvue-echarts :echarts="echarts" :onInit="initChart"></mpvue-echarts>
  </div>
</template>

<script>
import moment from "moment";
import * as echarts from "../../../node_modules/echarts/dist/echarts.min.js";
import mpvueEcharts from "mpvue-echarts";
let chart = null;
export default {
  name: "priceHistory",
  props: {
    history: Array,
    moneySymbol: String
  },
  data() {
    return {
      echarts,
      isPlusHistory: false,
      plusHistory: [],
      noPlusHistory: []
    }
  },
  components: {
    mpvueEcharts
  },
  onLoad() {
    this.isPlusHistory = false;
    this.dealData(this.history);
  },
  watch: {
    history(newValue) {
      this.dealData(newValue);
    }
  },
  methods: {
    change() {
      this.isPlusHistory = !this.isPlusHistory;
      this.setData();
    },
    dealData(newValue) {
      let length = newValue.length;
      if (length == 0) {
        return;
      }
      let history = [];
      let plusHistory = [];
      newValue.forEach((item, index) => {
        let date = moment(item.date).format("YYYY-MM-DD");
        history.push([date, item.price / 100]);
        plusHistory.push([date, item.plusPrice / 100]);
        if (length > 1 && index < length - 1 && item.date + 24 * 3600 * 1000 < newValue[index + 1].date) {
          date = moment(newValue[index + 1].date - 24 * 3600 * 1000).format("YYYY-MM-DD");
          history.push([date, item.price / 100]);
          plusHistory.push([date, item.plusPrice / 100]);
        }
      });
      let lastOne = newValue[length - 1];
      if (moment(lastOne).format('YY-MM-DD') != moment(Date.now()).format('YY-MM-DD')) {
        history.push([moment(Date.now()).format('YYYY-MM-DD'), lastOne.price / 100]);
        plusHistory.push([moment(Date.now()).format('YYYY-MM-DD'), lastOne.plusPrice / 100]);
      }
      this.noPlusHistory = history;
      this.plusHistory = plusHistory;
      console.log(this.history, this.plusHistory);
      if (chart != null) {
        this.setData();
      }
    },
    setData() {
      let _this = this;
      let option = {
        title: {
          text: this.isPlusHistory ? "PLUS历史价格" : '非PLUS历史价格'
        },
        tooltip: {
          trigger: 'axis',
          position: function (pos, params, dom, rect, size) {
            // 鼠标在左侧时 tooltip 显示到右侧，鼠标在右侧时 tooltip 显示到左侧。
            var obj = { top: 60 };
            obj[['left', 'right'][+(pos[0] < size.viewSize[0] / 2)]] = 5;
            return obj;
          },
          formatter: function (params, ticket, callback) {
            let data = params[0].data;
            return data[0] + ": " + _this.moneySymbol + data[1];
          }
        },
        grid: {
          left: '3%',
          right: '5%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'time',
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          name: this.isPlusHistory ? "PLUS价格" : "非PLUS价格",
          type: 'line',
          stack: '总量',
          data: this.isPlusHistory ? this.plusHistory : this.noPlusHistory
        }]
      };
      console.log(option);
      chart.setOption(option);
    },
    initChart(canvas, width, height) {
      chart = echarts.init(canvas, 'light', {
        width,
        height
      })
      canvas.setChart(chart);
      console.log("chart初始化完成");
      this.setData();
      return chart;
    },


  }
}
</script>

<style>
</style>
