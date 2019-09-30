package com.eamon.designprinciples.carp;

/**
 * @author eamon.zhang
 * @date 2019-09-26 上午10:48
 */
public class CompositeTest {
    public static void main(String[] args) {
        ProductDao productDao = new ProductDao();
        DBConnection connection = new OracleConnection();
        productDao.setDbConnection(connection);
        productDao.addProduct();
    }
}
