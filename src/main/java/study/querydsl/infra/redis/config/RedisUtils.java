package study.querydsl.infra.redis.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtils {

    private final RedisTemplate<String, Object> redisTemplate;

    public void put(String key, Object value, Long expirationTime){
        if (expirationTime != null){
            redisTemplate.opsForValue().set(key, value, expirationTime, TimeUnit.SECONDS);
        } else{
            redisTemplate.opsForValue().set(key, value);
        }
    }

    public void delete(String key){
        redisTemplate.delete(key);
    }

    public <T> T get(String key, Class<T> clazz){
        Object o = redisTemplate.opsForValue().get(key);
        if(o != null) {
            return clazz.cast(o);
        }
        return null;
    }

    public boolean isExists(String key){
        return redisTemplate.hasKey(key);
    }

    public void setExpireTime(String key, long expirationTime){
        redisTemplate.expire(key, expirationTime, TimeUnit.SECONDS);
    }

    public long getExpireTime(String key){
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }
}
