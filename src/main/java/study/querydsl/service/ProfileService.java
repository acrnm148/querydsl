package study.querydsl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.ProfileDto;
import study.querydsl.infra.redis.config.RedisUtils;
import study.querydsl.repository.ProfileRepository;

import java.time.Duration;

import static study.querydsl.infra.redis.config.MyrRedisKeys.PROFILE_DETAIL;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    private final RedisUtils redisUtils;

    @Cacheable(cacheNames = PROFILE_DETAIL, key = "#memberNum")
    public ProfileDto getProfile(Long memberNum) {
        System.out.println("DB 접근 => " + memberNum);
        return profileRepository.getProfileById(memberNum);
    }

    @Transactional
//    @CacheEvict(cacheNames = PROFILE_DETAIL, key = "#request.memberNum", condition = "#request.memberNum != null")
    public void addProfile(ProfileDto request) {
        System.out.println("프로필 추가");
        redisUtils.put(String.valueOf(request.getMemberNum()), request.toString(), Duration.ofDays(7).toSeconds());
        profileRepository.save(request.toEntity());
    }

    @Transactional
//    @CacheEvict(cacheNames = PROFILE_DETAIL, key = "#request.memberNum", condition = "#request.memberNum != null")
    public void modifyProfile(ProfileDto request) {
        final String key = PROFILE_DETAIL + "::" + String.valueOf(request.getMemberNum());
        System.out.println("Key: " + key);
        redisUtils.delete(key);
        redisUtils.put(key, request, Duration.ofDays(7).toSeconds());

        profileRepository.update(request.toEntity());

    }
}
