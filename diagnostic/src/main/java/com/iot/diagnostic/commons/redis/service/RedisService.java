package com.iot.diagnostic.commons.redis.service;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
public class RedisService {

    private static final String REDIS_SUCCESS_RESPONSE = "OK";
    private final RedisClient redisClient;
    private final StatefulRedisConnection<String, String> statefulRedisConnection;
    private final RedisCommands<String, String> syncCommands;

    public RedisService() {
        this.redisClient = RedisClient.create(RedisURI.Builder
                .redis("localhost", 6379)
                .withPassword("password".toCharArray())
                .withDatabase(1)
                .withTimeout(Duration.ofSeconds(60L))
                .build());
        this.statefulRedisConnection = this.redisClient.connect();
        this.syncCommands = this.statefulRedisConnection.sync();
    }

    public boolean hasKey(String key) {
        return this.syncCommands.exists(key) == 1;
    }

    public boolean set(String key, String value) {
        return this.syncCommands.set(key, value)
                .equalsIgnoreCase(REDIS_SUCCESS_RESPONSE);
    }

    public boolean set(String key, String value, long timeout, TimeUnit timeUnit) {
        final long milliseconds = (int) timeUnit.toMillis(timeout);
        final String response = this.syncCommands.set(key, value,
                SetArgs.Builder
                        .nx()
                        .px(milliseconds));
        return response.equalsIgnoreCase(REDIS_SUCCESS_RESPONSE);
    }

    public String get(String key) {
        return this.syncCommands.get(key);
    }

}
