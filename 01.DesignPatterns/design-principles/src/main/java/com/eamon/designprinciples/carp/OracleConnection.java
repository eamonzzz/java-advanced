package com.eamon.designprinciples.carp;

/**
 * @author eamon.zhang
 * @date 2019-09-26 上午10:47
 */
public class OracleConnection extends DBConnection {
    @Override
    public String getConnection() {
        return "Oracle 数据库连接";
    }
}
