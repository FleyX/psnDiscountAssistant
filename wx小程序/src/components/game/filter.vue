<template>
  <div class="main" :class="{'fixed':isFixed}" v-if="isShow">
    <div class="between">
      <div>
        <van-button size="mini" style="margin-right:1em" @click="chooseItem('serverAndPlatform','选择平台')" type="primary">{{serverAndPlatform}}</van-button>
        <van-button size="mini" style="margin-right:1em" @click="chooseItem('language','选择游戏语言')" type="primary">{{param.language}}</van-button>
        <van-button style="margin-right:1em" v-if="showType" size="mini" @click="chooseItem('type','选择游戏类别')" type="primary">{{param.type}}</van-button>
        <van-button v-if="showSort" size="mini" @click="chooseItem('sort','选择排序')" type="primary">排序</van-button>
      </div>
      <div v-if="count!=undefined" style="font-size:0.7em">
        共计:
        <span style="color:#968e8e">{{count}}</span>
      </div>
    </div>

    <!-- 服务器选择弹出框 -->
    <van-popup :show="type=='serverAndPlatform'" position="bottom" @close="onClose">
      <van-picker id="serverAndPlatformSelect" show-toolbar :title="title" :columns="serverPlatformColumns" value-key="name" @cancel="type=''" @confirm="onConfirm" />
    </van-popup>
    <!-- 语言选择弹出框 -->
    <van-popup :show="type=='language'" position="bottom" @close="onClose">
      <van-picker id="languageSelect" show-toolbar :title="title" :columns="languageColumns" @cancel="type=''" @confirm="onConfirm" />
    </van-popup>
    <!-- 排序选择框  -->
    <van-popup :show="type=='sort'" position="bottom" @close="onClose">
      <van-picker id="sortSelect" show-toolbar :title="title" :columns="sortColumns" @cancel="type=''" @confirm="onConfirm" />
    </van-popup>
    <!-- 类别选择框 -->
    <van-popup :show="type=='type'" position="bottom" @close="onClose">
      <van-picker id="typeSelect" show-toolbar :title="title" :columns="typeColumns" @cancel="type=''" @confirm="onConfirm" />
    </van-popup>
  </div>
</template>

<script>
export default {
  name: "gameFilter",
  props: {
    param: Object,
    showSort: Boolean,
    showCount: Boolean,
    showType: Boolean,
    isFixed: Boolean,
    count: Number
  },
  data() {
    return {
      serverPlatformColumns: [
        {
          values: ["港服"]
        },
        {
          values: ["PS4", "PS3", "PSV"]
        }
      ],
      typeColumns: [
        "全部类别",
        "动作",
        "冒险",
        "街机",
        "桌上游戏",
        "大脑训练游戏",
        "休闲",
        "家庭",
        "格斗",
        "恐怖",
        "音乐&节奏",
        "排队",
        "益智",
        "问答游戏",
        "赛车",
        "角色扮演游戏",
        "射击",
        "模拟",
        "运动",
        "战略",
        "独特游戏",
        "MUSIC/RHYTHM",
        "其他",
        "派对"
      ],
      languageColumns: [
        {
          values: ["全部语言", "中", "日", "韩", "英"],
          defaultIndex: 1
        }
      ],
      sortColumns: [
        {
          values: ["热度", "价格", "发布时间"],
          defaultIndex: 0
        },
        {
          values: ["降序", "升序"],
          defaultIndex: 0
        }
      ],
      type: "",
      title: "",
      serverAndPlatform: "港服 | PS4",
      isShow: true
    };
  },
  onLoad() {
    this.globalData.eventBus.$on("refreshFilter", () => {
      console.log("重置filter");
      this.$root.$mp.page
        .selectComponent("#serverAndPlatformSelect")
        .setIndexes([0, 0]);
      this.$root.$mp.page.selectComponent("#sortSelect").setIndexes([0, 0]);
      this.$root.$mp.page.selectComponent("#languageSelect").setIndexes([0]);
      this.$root.$mp.page.selectComponent("#typeSelect").setIndexes([0]);
    });
  },
  methods: {
    onConfirm(event) {
      console.log(event);
      let value = event.mp.detail.value;
      let data = {};
      if (this.type == "sort") {
        if (value[0] == "热度") {
          data["orderKey"] = "psnScoreNum";
        } else if (value[0] == "发布时间") {
          data["orderKey"] = "publishTime";
        } else {
          data["orderKey"] = "currentPrice";
        }
        data["isAsc"] = value[1] == "升序" ? "1" : "0";
      } else if (this.type == "serverAndPlatform") {
        this.serverAndPlatform = value.join(" | ");
        data["serverId"] = this.globalData.serverList.find(
          item => item.name == value[0]
        ).serverId;
        data["platform"] = value[1];
      } else {
        data[this.type] = value;
      }
      this.$emit("dataChange", data);
      this.type = 0;
    },
    chooseItem(type, title) {
      this.type = type;
      this.title = title;
    }
  }
};
</script>

<style scoped>
.main {
  width: 96%;
  padding-left: 2%;
  padding-right: 2%;
  background-color: white;
}
.fixed {
  position: fixed;
  z-index: 1000;
}
.between {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
