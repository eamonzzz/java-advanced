package com.eamon.designprinciples.carp;

/**
 * @author eamon.zhang
 * @date 2019-09-26 上午10:46
 */
public class MySQLConnection extends DBConnection {
    @Override
    public String getConnection() {
        return "MySQL 数据库连接";
    }
}
