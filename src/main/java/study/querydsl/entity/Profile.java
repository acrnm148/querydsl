package study.querydsl.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Profile {

    @Id
    @Column(name = "mmbr_num")
    private Long memberNum;

    @Column(name = "ncnm")
    private String nickName;

    @Column(name = "pfile_phtg_file_urladrs")
    private String imgUrl;

    @Column(name = "crtr_id")
    private String crtrId;

    @Column(name = "amnr_id")
    private String amnrId;

    @Column(name = "dlt_ysno")
    private String dltYsno;
}
