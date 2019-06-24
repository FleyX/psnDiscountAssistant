/**
 * 和微信相关函数写在这里
 */

/**
 * 从globalData里取数据
 * @param {*} key
 */
export function getGlobalData(key) {
  return getApp().globalData[key];
}

/**
 * show toast
 * @param {*} message
 */
export function showToast(message) {
  wx.showToast({
    title: message,
    icon: "none",
    duration: 2000
  });
}
