import mysql from "mysql2/promise"

interface Res {
    rows: any;
    fields: mysql.FieldPacket;
}

class MysqlUtil {
    public static pool: mysql.Pool = null;

    static async createPool(config: any) {
        MysqlUtil.pool = mysql.createPool(config);
    }

    static async getRows(sql: string, params: Array<any>, connection: mysql.PoolConnection = null): Promise<Array<any>> {
        return (await MysqlUtil.execute(sql, params, connection)).rows;
    }


    static async getRow(sql: string, params: Array<any>, connection: mysql.PoolConnection = null): Promise<any> {
        let rows = (await MysqlUtil.execute(sql, params, connection)).rows;
        return rows.length > 0 ? rows[0] : null;
    }

    static async getSingle(sql: string, params: Array<any>, connection: mysql.PoolConnection = null): Promise<any> {
        let rows = (await MysqlUtil.execute(sql, params, connection)).rows;
        if (rows.length == 0) {
            return null;
        }
        let row = rows[0];
        return row[Object.keys(row)[0]];
    }



    static async execute(sql: string, params: Array<any>, connection: mysql.PoolConnection = null): Promise<Res> {
        let res: any = {};
        if (connection == null) {
            let [rows, fields] = await MysqlUtil.pool.query(sql, params);
            res['rows'] = rows;
            res['fields'] = fields;
        } else {
            let [rows, fields] = await connection.query(sql, params);
            res['rows'] = rows;
            res['fields'] = fields;
        }
        return res;
    }


    static async test() {
        let connection = await MysqlUtil.pool.getConnection();
        connection.beginTransaction();
        connection.query(`insert into url value(6,"GET","asd","public")`);
        connection.query(`insert into url value(7,"GET","asd","public")`);
        await connection.commit();
        connection.release();
    }
}

export {
    MysqlUtil,
    Res,
    mysql
}