import * as mysql from '../util/MysqlUtil';
import CutMessage from '../entity/CutMessage';

class ServerDao {
  /**
   * 插入一条降价记录
   */
  static async inserOne(message: CutMessage, conn: mysql.mysql.PoolConnection): Promise<void> {
    await mysql.MysqlUtil.execute('insert into cut_message(gameId,createTime) value(?,?)'
      , [message.gameId, message.createTime], conn);
  }
}

export default ServerDao;
