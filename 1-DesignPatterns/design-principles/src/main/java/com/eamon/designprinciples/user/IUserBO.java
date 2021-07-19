package com.eamon.designprinciples.user;

/**
 * @author eamon.zhang
 * @date 2019-09-25 下午4:18
 */
public interface IUserBO {
    void setUserID(String userID);
    String getUserID();
    void setPassword(String password);
    String getPassword();
    void setUserName(String userName);
    String getUserName();
}
