import Vue from "vue";
import App from "./App";
import * as wxUtil from "./utils/wxUtil";
import util from "./utils/index";

var Fly = require("flyio/dist/npm/wx");
var fly = new Fly();

//配置请求路径
fly.config.baseURL = 'http://localhost:8082/psn/api';
//配置请求超时时长
fly.config.timeout = 10000;
//请求头预处理
fly.interceptors.request.use(request => {
  request.headers["jwt-token"] = wxUtil.getGlobalData("jwt-token");
  //如果有formId就放到header里送过去
  let formIdList = getApp().globalData.formIdList;
  if (formIdList.length > 0) {
    request.headers["formIdList"] = JSON.stringify(formIdList);
    getApp().globalData.formIdList = [];
  }
  if (request.method == "GET") {
    request.params["_t"] = new Date().getTime();
  }
  return request;
});

//响应预处理
fly.interceptors.response.use(
  res => {
    if (res.data.code == 1) {
      return res.data.data;
    } else {
      if (res.data.code == -1) {
        //如果当前页面不是init页面就跳转到init页面去
        let pages = getCurrentPages();
        let page = pages[pages.length - 1];
        let paramStr = util.objToUrlParam(page.options);
        let currentUrl = `/${page.route}${paramStr.length > 0 ? "?" + paramStr : ""}`;
        console.log(currentUrl);
        if (currentUrl.startsWith("/pages/init/main")) {
          console.log("已经在初始页面，不做处理");
        } else {
          console.log(currentUrl + "/无授权，跳转到init页");
          wx.redirectTo({
            url: "/pages/init/main?redirect=" + util.base64_encode(currentUrl)
          });
          return;
        }
      }
      if (res.data.message.length > 0) {
        wxUtil.showToast("ERROR:" + res.data.message);
      } else {
        wxUtil.showToast("请求出错，请稍后重试");
      }
      return Promise.reject(res.data);
    }
  },
  err => {
    wxUtil.showToast("请求出错，请稍后重试");
    return Promise.reject(err);
  }
);

Vue.config._mpTrace=true;
Vue.config.productionTip = false;
App.mpType = "app";

Vue.prototype.$http = fly;
const app = new Vue(App);

app.$mount();

let globalData = getApp().globalData;
//挂载eventBus
globalData.eventBus = new Vue();
//挂载http请求工具
globalData.$http = fly;
//挂载serverList
globalData.serverList = [];
//挂载formId list
globalData.formIdList = [];
//将globalDta放到Vue的原型链上
Vue.prototype.globalData = globalData;
