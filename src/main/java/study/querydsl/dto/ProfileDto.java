package study.querydsl.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import study.querydsl.entity.Profile;

@Data
public class ProfileDto {

    private Long memberNum;

    private String nickName;

    private String imgUrl;

    private String crtrId;

    private String amnrId;

    private String dltYsno;

    @QueryProjection
    public ProfileDto(Long memberNum, String nickName, String imgUrl, String crtrId, String amnrId, String dltYsno) {
        this.memberNum = memberNum;
        this.nickName = nickName;
        this.imgUrl = imgUrl;
        this.crtrId = crtrId;
        this.amnrId = amnrId;
        this.dltYsno = dltYsno;
    }

    public Profile toEntity() {
        return Profile.builder()
                .memberNum(memberNum)
                .nickName(nickName)
                .imgUrl(imgUrl)
                .crtrId(crtrId)
                .amnrId(amnrId)
                .dltYsno(dltYsno)
                .build();
    }
}
