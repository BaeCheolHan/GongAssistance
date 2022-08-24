package com.herren.gongassistance.base.lock;

import com.herren.gongassistance.base.exception.GongAssistanceCode;
import com.herren.gongassistance.base.exception.GongAssistanceException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisLockService {
    private final RedisTemplate<String, Object> redisTemplate;

    private final static long LOCK_EXPIRED_MS = 1500L;
    private final static long SPIN_IDLE_MS = 50L;
    private final static int MAX_RETRY_COUNT = 30;

    public void runWithLock(String uniqueKey, Long expiredMillis, Runnable runnable) {
        int retry = 0;

        if (expiredMillis == null) expiredMillis = LOCK_EXPIRED_MS;

        while (!tryLock(uniqueKey, expiredMillis)) {
            if (MAX_RETRY_COUNT == retry++) {
                throw new GongAssistanceException(GongAssistanceCode.INTERNAL_ERROR);
            }
            try {
                Thread.sleep(SPIN_IDLE_MS);
            } catch (InterruptedException e) {
                throw new GongAssistanceException(GongAssistanceCode.INTERNAL_ERROR);
            }
        }

        try {
            runnable.run();
        } finally {
            unLock(uniqueKey);
        }

    }


    public Boolean tryLock(String key, Long expiredMillis) {
        return Optional.ofNullable(redisTemplate.opsForValue()
                .setIfAbsent(key, "lock", Duration.ofMillis(expiredMillis))
        ).orElse(Boolean.FALSE);
    }

    private void unLock(String key) {
        redisTemplate.delete(key);
    }

}
