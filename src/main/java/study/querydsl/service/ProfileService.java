package study.querydsl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.ProfileDto;
import study.querydsl.entity.Profile;
import study.querydsl.repository.ProfileRepository;

import java.util.List;
import java.util.Optional;

import static study.querydsl.infra.redis.config.MyrRedisKeys.PROFILE_DETAIL;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    @Cacheable(value = PROFILE_DETAIL, key = "#memberNum")
    public ProfileDto getProfile(Long memberNum) {
        System.out.println("DB 접근 => " + memberNum);

        return profileRepository.getProfileById(memberNum);
    }

    @Transactional
    public void addProfile(ProfileDto request) {
        profileRepository.save(request.toEntity());
    }

    @Transactional
    public void modifyProfile(ProfileDto request) {
        profileRepository.update(request.toEntity());
    }
}
