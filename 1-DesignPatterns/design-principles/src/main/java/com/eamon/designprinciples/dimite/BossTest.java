package com.eamon.designprinciples.dimite;

/**
 * @author eamon.zhang
 * @date 2019-09-26 上午9:19
 */
public class BossTest {
    public static void main(String[] args) {
        Boss boss = new Boss();
        TeamLeader teamLeader = new TeamLeader();
        boss.commandCheckNumber(teamLeader);
    }
}
