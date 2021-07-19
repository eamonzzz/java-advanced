package com.eamon.javadesignpatterns.delegate;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author EamonZzz
 * @date 2019-10-26 15:23
 */
public class BossTest {

    @Test
    public void test() {
        Boss boss = new Boss();
        boss.command("盖楼", new Contractor());
        boss.command("刷漆", new Contractor());
    }

}