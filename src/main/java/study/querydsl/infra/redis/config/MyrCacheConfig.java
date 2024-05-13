package study.querydsl.infra.redis.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static study.querydsl.infra.redis.config.MyrRedisKeys.PROFILE_DETAIL;

@Configuration
@RequiredArgsConstructor
public class MyrCacheConfig {
    private static final Duration ONE_MINUTE = Duration.ofSeconds(60);
    private static final Duration SEVEN_DAYS = Duration.ofDays(7);

    @Value("${spring.cache.redis.time-to-live:600}")
    public Duration defaultTtl;

    @Qualifier("myrRedisConnectionFactory")
    private final RedisConnectionFactory redisConnectionFactory;

    @Bean
    @Primary
    public CacheManager myrCacheManager() {
        RedisCacheConfiguration redisCacheConfiguration = createRedisCacheConfiguration();

        // Individual Setting
        Map<String, RedisCacheConfiguration> cacheConfiguration = createCacheConfigurations(redisCacheConfiguration);

        return RedisCacheManager
                .RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .withInitialCacheConfigurations(cacheConfiguration)
                .build();
    }

    /**
     * 기본 설정
     *
     * @return
     */
    private RedisCacheConfiguration createRedisCacheConfiguration() {
        return RedisCacheConfiguration
                .defaultCacheConfig()
                .disableCachingNullValues()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .entryTtl(defaultTtl);
    }

    /**
     * 커스텀 설정
     *
     * @param redisCacheConfiguration
     * @return
     * @return
     */
    private Map<String, RedisCacheConfiguration> createCacheConfigurations(
            RedisCacheConfiguration redisCacheConfiguration) {
        Map<String, RedisCacheConfiguration> cacheConfiguration = new HashMap<>();
        cacheConfiguration.put(PROFILE_DETAIL, redisCacheConfiguration.entryTtl(SEVEN_DAYS));
        return cacheConfiguration;
    }
}
