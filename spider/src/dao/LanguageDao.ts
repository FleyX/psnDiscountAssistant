import * as mysql from '../util/MysqlUtil'
class LanguageDao {
  /**
   * 获取全部地区服
   */
  static async insertOne(gameId: number, language: string, conn: mysql.mysql.PoolConnection): Promise<void> {
    await mysql.MysqlUtil.execute('insert into language value(?,?)', [gameId, language], conn);
  }
}

export default LanguageDao;
