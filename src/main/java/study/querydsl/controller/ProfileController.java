package study.querydsl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.querydsl.dto.ProfileDto;
import study.querydsl.service.ProfileService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    /**
     * 프로필 조회
     * @param memberNum
     * @return
     */
    @GetMapping("/api/profile-select")
    public ProfileDto getProfile(@RequestParam(name="mmbrNum" , required = true) String memberNum) {
        return profileService.getProfile(Long.parseLong(memberNum));
    }

    /**
     * 프로필 등록
     * @param request
     * @return
     */
    @PostMapping("/api/profile-add")
    public void addProfile(@RequestBody ProfileDto request) {
        profileService.addProfile(request);
    }

    /**
     * 프로필 수정
     * @param request
     * @return
     */
    @PutMapping("/api/profile-modify")
    public void modifyProfile(@RequestBody ProfileDto request) {
        profileService.modifyProfile(request);
    }
}
