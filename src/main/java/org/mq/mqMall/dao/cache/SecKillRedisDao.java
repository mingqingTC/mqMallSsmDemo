package org.mq.mqMall.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.mq.mqMall.entity.SecKill;
import org.mq.mqMall.enums.SecKillStateEnum;
import org.mq.mqMall.exception.SecKillException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

//TODO 未测试
public class SecKillRedisDao {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //redis客户端连接池
    private JedisPool jedisPool;

    //通过反射获取SecKill的schema
    private RuntimeSchema<SecKill> schema = RuntimeSchema.createFrom(SecKill.class);

    public SecKillRedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }

    public SecKill getSecKill(long secKillId) throws SecKillException {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.select(0); //选择数据库
            String key = "SecKill:" + secKillId;
            byte[] bytes = jedis.get(key.getBytes());
            //将二进制数组反序列化
            SecKill secKill = schema.newMessage();
            ProtostuffIOUtil.mergeFrom(bytes, secKill, schema);
            return secKill;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new SecKillException(SecKillStateEnum.INNER_ERROR.getStateInfo());
        }
    }

    public String setSecKill(SecKill secKill) throws SecKillException {
        if (secKill == null) {
            return null;
        }
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.select(0);
            String key = "SecKill:" + secKill.getSecKillId();
            //将类对象序列化
            byte[] bytes = ProtostuffIOUtil.toByteArray(secKill, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
            //存放到redis中
            int timeout = 60 * 60; //保存时间1小时
            return jedis.setex(key.getBytes(), timeout, bytes);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new SecKillException(SecKillStateEnum.INNER_ERROR.getStateInfo());
        }
    }
}
