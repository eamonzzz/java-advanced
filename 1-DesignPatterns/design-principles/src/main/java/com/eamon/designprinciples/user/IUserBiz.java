package com.eamon.designprinciples.user;

/**
 * @author eamon.zhang
 * @date 2019-09-25 下午4:18
 */
public interface IUserBiz {
    boolean changePassword(String oldPassword);
    boolean deleteUser();
    void mapUser();
    boolean addOrg(int orgID);
    boolean addRole(int roleID);
}
