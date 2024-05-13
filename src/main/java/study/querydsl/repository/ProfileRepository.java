package study.querydsl.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import study.querydsl.dto.ProfileDto;
import study.querydsl.dto.QProfileDto;
import study.querydsl.entity.Profile;

import java.util.Optional;

import static study.querydsl.entity.QMember.member;
import static study.querydsl.entity.QProfile.profile;

@Repository
public class ProfileRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;


    public ProfileRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public void save(Profile profile) {
        em.persist(profile);
    }

    public void update(Profile profile1) {
        queryFactory
                .update(profile)
                .set(profile.nickName, profile1.getNickName())
                .set(profile.imgUrl, profile1.getImgUrl())
                .set(profile.crtrId, profile1.getCrtrId())
                .set(profile.amnrId, profile1.getAmnrId())
                .set(profile.dltYsno, profile1.getDltYsno())
                .where(profile.memberNum.eq(profile1.getMemberNum()))
                .execute();
    }

    public Optional<Profile> findById(Long id) {
        Profile profile = em.find(Profile.class, id);
        return Optional.ofNullable(profile);
    }

    public ProfileDto getProfileById(Long memberNum) {
        return queryFactory
                .select(new QProfileDto(
                        profile.memberNum.as("memberNum")
                        ,profile.nickName
                        ,profile.imgUrl
                        ,profile.crtrId
                        ,profile.amnrId
                        ,profile.dltYsno
                ))
                .from(profile)
                .where(memberNumEq(memberNum))
                .fetchOne();
    }

    private BooleanExpression memberNumEq(Long memberNum) {
        return profile.memberNum.eq(memberNum);
    }
}
