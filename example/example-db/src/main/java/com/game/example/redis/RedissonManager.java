package com.game.example.redis;

import com.game.example.redis.lock.DistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @author: liulongling
 * @date: 2024/2/29
 */
@Slf4j
public class RedissonManager {

    private static final RedissonManager instance = new RedissonManager();

    public RedissonClient redissonClient = null;

    /**
     * 项目启动时，初始化参数到缓存
     */
    public void loadRedisCondifg() {
        redissonClient = getRedissonClientFromConfig();
    }

    public static RedissonManager getInstance() {
        return instance;
    }

    public DistributedLock distributedLock() {
        return new DefaultRedissonDistributedLock(redissonClient);
    }

    public RedissonClient getRedissonClientFromConfig() {
        Config config = null;
        try {
            config = Config.fromYAML(RedissonManager.class.getClassLoader().getResource("redisson-config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
