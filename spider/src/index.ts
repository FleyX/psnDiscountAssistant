import { MysqlUtil } from './util/MysqlUtil'
import config from "./config";
import AsiaService from "./service/AsiaService";
import ServerDao from "./dao/ServerDao";
//mysql初始化配置
MysqlUtil.createPool(config.mysqlConfig);

import schdule from "node-schedule";

let isRunning = false;

console.log(config);

//入口函数
async function start() {
  if (isRunning) {
    console.log("正在运行，跳过");
    return;
  }
  try {
    console.log("开始进行一次数据更新");
    let res = await ServerDao.getAll();
    for (let i = 0; i < res.length; i++) {
      switch (res[i].name) {
        case "港服":
          await new AsiaService(res[0]).start();
          break;
        default:
          break;
      }
    }
  } catch (error) {
    console.error(error);
  } finally {
    isRunning = false;
  }
}

(() => {
  //调度，定时运行
  schdule.scheduleJob(config.cron, start);
  //首次运行
  start();
})();

//处理异常
process.on("uncaughtException", dealError);

process.on("unhandledRejection", dealError);

function dealError(err: any) {
  console.error(`存在在未处理异常: ${err.message}\n${err.stack}`);
  console.error(`进程结束。。`);
  process.exit(0);
}
