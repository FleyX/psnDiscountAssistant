import Toast from "../../static/vant/toast/toast";
import Notify from "../../static/vant/notify/notify";
import Dialog from "../../static/vant/dialog/dialog";

/**
 * type可选值：loading,success,fail,html,text
 * @param {*} id
 * @param {*} type
 * @param {*} message
 * @param {*} mask
 */
export function showToast(id, type, message, mask, forbidClick = true) {
  let duration = 1000;
  if (type == "loading") {
    duration = 0;
  }
  return Toast({
    type,
    message,
    duration,
    forbidClick,
    mask,
    zIndex:10000,
    selector: "#" + id
  });
}

export function showDialog(id, title, message) {
  return Dialog({
    title,
    message,
    selector: "#" + id,
    asyncClose: true,
    showCancelButton: true
  });
}

export function closeDialog() {
  Dialog.close();
}

export function showErrorNotify(message) {
  Notify({
    text: message,
    duration: 1000,
    selector: "#van-notify"
  });
}
