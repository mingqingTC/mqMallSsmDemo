package org.mq.mqMall.dao;

import org.junit.Test;
import org.mq.mqMall.entity.SecKill;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

public class SecKillDaoTest extends DaoBaseTest {

    @Autowired
    private SecKillDao secKillDao;

    @Test
    public void queryOneDataById() {
        long secKillId = 1000;
        SecKill secKill = secKillDao.queryOneDataById(secKillId);
        System.out.println("secKill: " + secKill.toString());
    }

    @Test
    public void queryAllData() {
        List<SecKill> secKillList = secKillDao.queryAllData(0, 4);
        System.out.println("size: " + secKillList.size());
    }

    @Test
    public void testReduceNumber() {
        long secKillId = 1003;
        Date now = new Date();
        int result = secKillDao.reduceNumber(secKillId, now);
        System.out.println("result: " + result);
    }
}