package org.mq.mqMall.service.impl;

import org.mq.mqMall.dao.SecKillDao;
import org.mq.mqMall.dao.SuccessKilledDao;
import org.mq.mqMall.dto.ExecuteSecKillResult;
import org.mq.mqMall.dto.ExposeEntranceResult;
import org.mq.mqMall.entity.SecKill;
import org.mq.mqMall.entity.SuccessKilled;
import org.mq.mqMall.enums.SecKillStateEnum;
import org.mq.mqMall.exception.DataRewriteException;
import org.mq.mqMall.exception.RepeatKillException;
import org.mq.mqMall.exception.SecKillEndException;
import org.mq.mqMall.exception.SecKillException;
import org.mq.mqMall.service.SecKillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class SecKillServiceImpl implements SecKillService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecKillDao secKillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Override
    public Long querySystemCurrentTime() {
        return new Date().getTime();
    }

    @Override
    public List<SecKill> querySecKills(int offset, int limit) {
        if (offset < 0 || limit < 0) {
            return null;
        }
        return secKillDao.queryAllData(offset, limit);
    }

    @Override
    public SecKill queryOneSecKill(long secKillId) {
        return secKillDao.queryOneDataById(secKillId);
    }

    @Override
    public ExposeEntranceResult exposeSecKillEntrance(long secKillId) {
        SecKill secKill = secKillDao.queryOneDataById(secKillId);
        if (secKill == null) {
            return new ExposeEntranceResult(secKillId, false, null);
        }
        long systemCurrentTime = new Date().getTime();
        long startTime = secKill.getStartTime().getTime();
        long endTime = secKill.getEndTime().getTime();
        if (systemCurrentTime < startTime || systemCurrentTime > endTime) {
            //秒杀未开始或秒杀已结束
            SecKill secKill1 = secKillDao.queryOneDataById(secKillId);
            return new ExposeEntranceResult(secKillId, false, systemCurrentTime, startTime, endTime, secKill1);
        } else {
            String md5 = getMD5(secKillId);
            SecKill secKill1 = secKillDao.queryOneDataById(secKillId);
            return new ExposeEntranceResult(secKillId, true, md5, secKill1);
        }
    }

    private String getMD5(long secKillId) {
        //用于md5加密的字符串
        final String SLAT = "faghjlajg280w8jgsljgkl@*(*)jsgljgklssjgl";
        String str = secKillId + "/" + SLAT;
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    @Override
    @Transactional
    public ExecuteSecKillResult executeSecKill(long secKillId, long userPhone, String md5) throws SecKillException {
        if (md5 == null || !md5.equals(getMD5(secKillId))) {
            logger.info("md5错误");
            throw new DataRewriteException(SecKillStateEnum.DATA_REWRITE.getStateInfo());
        }
        try {
            //减库存 保存记录
            int updateCount = secKillDao.reduceNumber(secKillId, new Date());
            if (updateCount == 1) {
                logger.info(secKillId + "库存减少1");
                int insertCount = successKilledDao.insertSuccessKilled(secKillId, userPhone);
                if (insertCount == 1) {
                    logger.info("插入1条秒杀记录");
                    SuccessKilled successKilled = successKilledDao.querySuccessKilledWithSecKill(secKillId, userPhone);
                    return new ExecuteSecKillResult(secKillId, SecKillStateEnum.SUCCESS, successKilled);
                } else {
                    logger.info("用户重复秒杀");
                    throw new RepeatKillException(SecKillStateEnum.REPEAT_KILL.getStateInfo());
                }
            } else {
                logger.info("更新数据库数据不唯一");
                throw new SecKillEndException(SecKillStateEnum.END.getStateInfo());
            }
        } catch (RepeatKillException | SecKillEndException e) {
            logger.info("自定义异常:" + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("系统异常:" + e.getMessage());
            throw new SecKillException(SecKillStateEnum.INNER_ERROR.getStateInfo());
        }
    }
}
