import * as mysql from '../util/MysqlUtil';

class PriceHisotryDao {
  /**
   * 获取全部地区服
   */
  static async getLowestPrice(gameId: number): Promise<number> {
    let res = await mysql.MysqlUtil.getSingle('select price from price_history where gameId=? order by price asc limit 0,1'
      , [gameId]);
    return res;
  }

  static async insertOne(date: number, gameId: number, price: number, plusPlus: number, conn: mysql.mysql.PoolConnection): Promise<void> {
    await mysql.MysqlUtil.execute(
      'delete from price_history where date=? and gameId=?;insert into price_history value(?,?,?,?)'
      , [date, gameId, date, gameId, price, plusPlus], conn);
  }
}

export default PriceHisotryDao;
