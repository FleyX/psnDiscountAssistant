import * as mysql from "../util/MysqlUtil";
import ServerEntity from "../entity/ServerEntity";

class ServerDao {
  /**
   * 获取全部地区服
   */
  static async getAll(): Promise<Array<ServerEntity>> {
    let res = await mysql.MysqlUtil.getRows("select * from server", []);
    return res as Array<ServerEntity>;
  }
}

export default ServerDao;
