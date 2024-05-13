package study.querydsl.infra.redis.config;

import io.lettuce.core.ReadFrom;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.time.Duration;

@Configuration
public class MyrRedisConfig {
    @Value("${spring.redis.lettuce.ssl:false}")
    private boolean ssl;

    @Bean("myrRedisConnectionFactory")
    @Primary
    public RedisConnectionFactory onkRedisConnectionFactory(RedisProperties props) {
        RedisStandaloneConfiguration redisConfig = setDefaultRedisConfig(props);
        LettuceClientConfiguration.LettuceClientConfigurationBuilder lettuceClientBuilder = setLettuceClientConfiguration();
        return new LettuceConnectionFactory(redisConfig, lettuceClientBuilder.build());
    }

    /**
     * 레디스 기본 정보 설정
     *
     * @param props
     * @return
     */
    private static RedisStandaloneConfiguration setDefaultRedisConfig(RedisProperties props) {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName("localhost");
        redisConfig.setPort(6379);
//        redisConfig.setPassword(props.getPassword());
//        redisConfig.setUsername(props.getUsername());
        return redisConfig;
    }

    /**
     * LettuceClient 설정
     *
     * @return
     */
    private LettuceClientConfiguration.LettuceClientConfigurationBuilder setLettuceClientConfiguration() {
        LettuceClientConfiguration.LettuceClientConfigurationBuilder lettuceClientBuilder = LettuceClientConfiguration.builder();
        if (ssl) {
            lettuceClientBuilder.useSsl();
        }
        lettuceClientBuilder.readFrom(ReadFrom.REPLICA_PREFERRED)
                .commandTimeout(Duration.ofSeconds(2))
                .shutdownTimeout(Duration.ZERO)
                .clientName("myr-service-redis");

        return lettuceClientBuilder;
    }
}
