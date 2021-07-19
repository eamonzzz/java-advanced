package com.eamon.designprinciples.carp;

/**
 * @author eamon.zhang
 * @date 2019-09-26 上午10:43
 */
public class ProductDao {
    private DBConnection dbConnection;

    public void setDbConnection(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void addProduct() {
        String conn = dbConnection.getConnection();
        System.out.println("使用" + conn + "增加产品");
    }
}
