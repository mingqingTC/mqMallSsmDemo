package org.mq.mqMall.service.impl;

import org.junit.Test;
import org.mq.mqMall.dto.ExecuteSecKillResult;
import org.mq.mqMall.dto.ExposeEntranceResult;
import org.mq.mqMall.entity.SecKill;
import org.mq.mqMall.exception.SecKillException;
import org.mq.mqMall.service.SecKillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SecKillServiceImplTest extends ServiceBaseTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private SecKillService secKillService;

    @Test
    public void querySystemCurrentTime() {
        long nowTime = secKillService.querySystemCurrentTime();
        logger.info("nowTime: " + nowTime);
    }

    @Test
    public void querySecKills() {
        List<SecKill> secKillList = secKillService.querySecKills(0, 4);
        logger.info("secKillList={}", secKillList);
    }

    @Test
    public void queryOneSecKill() {
        long secKillId = 1000L;
        SecKill secKill = secKillService.queryOneSecKill(secKillId);
        logger.info("seckill={}", secKill);
    }

    @Test
    public void executeSecKill() {
        long secKillId = 1001L;
        ExposeEntranceResult result = secKillService.exposeSecKillEntrance(secKillId);
        logger.info("result={}", result);
        if (result.isExposed()) {
            String md5 = result.getMd5();
            long userPhone = 13425698653L;
            try {
                ExecuteSecKillResult executeSecKillResult = secKillService.executeSecKill(secKillId, userPhone, md5);
                logger.info("executeSecKillResult={}", executeSecKillResult);
            } catch (SecKillException e) {
                logger.error(e.getMessage());
            }
        } else {
            long startTime = result.getStartTime();
            long systemCurrentTime = result.getSystemCurrentTime();
            if (startTime != 0L) {
                if (systemCurrentTime < startTime)
                    logger.info("商品秒杀未开启");
                else
                    logger.info("商品秒杀已结束");
            } else {
                logger.info("未查询到秒杀商品");
            }
        }
    }

}