package com.reservation.backend.auth.infrastructure;

import com.reservation.backend.auth.exception.AuthErrorCode;
import com.reservation.backend.auth.exception.AuthException;
import java.time.Duration;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RefreshTokenRepository {

    private static final String KEY_PREFIX = "auth:refresh_token:";

    private final StringRedisTemplate redisTemplate;

    public void save(Long memberId, String hashedRefreshToken, Duration ttl) {
        try {
            validateRefreshTokenTtl(ttl);

            redisTemplate.opsForValue()
                    .set(key(memberId), hashedRefreshToken, ttl);
        } catch (RedisConnectionFailureException | RedisSystemException e) {
            throw new AuthException(AuthErrorCode.TOKEN_STORE_UNAVAILABLE);
        }
    }

    public Optional<String> findByMemberId(Long memberId) {
        try {
            return Optional.ofNullable(
                    redisTemplate.opsForValue().get(key(memberId))
            );
        } catch (RedisConnectionFailureException | RedisSystemException e) {
            throw new AuthException(AuthErrorCode.TOKEN_STORE_UNAVAILABLE);
        }
    }


    public void deleteByMemberId(Long memberId) {
        try {
            redisTemplate.delete(key(memberId));
        } catch (RedisConnectionFailureException | RedisSystemException e) {
            throw new AuthException(AuthErrorCode.TOKEN_STORE_UNAVAILABLE);
        }
    }

    private void validateRefreshTokenTtl(Duration ttl) {
        if (ttl == null || ttl.isZero() || ttl.isNegative()) {
            throw new AuthException(AuthErrorCode.TOKEN_STORE_UNAVAILABLE);
        }
    }

    private String key(Long memberId) {
        return KEY_PREFIX + memberId;
    }
}
