import path from "path";

let envPort: any = process.env.MYSQL_PORT;
if (envPort != undefined) {
  envPort = parseInt(envPort);
} else {
  envPort = 3306;
}

export default {
  /**
   * 项目绝对路径
   */
  env: process.env.env || "product",
  rootPath: path.join(__dirname, ".."),
  mysqlConfig: {
    host: process.env.MYSQL_HOST || "192.168.88.128",
    database: "psn",
    port: envPort,
    user: process.env.MYSQL_USER || "root",
    password: process.env.MYSQL_PASS || "123456",
    multipleStatements: true
  },
  cron: "0 0 1,7,15 * * *"
};
