package com.eamon.javadesignpatterns.delegate;

/**
 * 我，需要盖楼的人，给包工头下达盖楼、刷漆的命令
 *
 * @author EamonZzz
 * @date 2019-10-26 15:06:09
 **/
public class Boss {
    /**
     * 下达 请求
     *
     * @param command
     * @param contractor
     */
    public void command(String command, Contractor contractor) {
        contractor.doWork(command);
    }
}
