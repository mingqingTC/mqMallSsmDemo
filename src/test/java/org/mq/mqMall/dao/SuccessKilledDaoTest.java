package org.mq.mqMall.dao;

import org.junit.Test;
import org.mq.mqMall.entity.SuccessKilled;
import org.springframework.beans.factory.annotation.Autowired;

public class SuccessKilledDaoTest extends DaoBaseTest {

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() {
        long secKillId = 1001;
        long userPhone = 13516489562L;
        int result = successKilledDao.insertSuccessKilled(secKillId, userPhone);
        System.out.println("result: " + result);
    }

    @Test
    public void querySuccessKilledWithSecKill() {
        long secKillId = 1001;
        long userPhone = 13516489565L;
        SuccessKilled successKilled = successKilledDao.querySuccessKilledWithSecKill(secKillId, userPhone);
        if (successKilled != null)
            System.out.println("successKilled: " + successKilled.toString());
        else
            System.out.println("没有数据");
    }
}