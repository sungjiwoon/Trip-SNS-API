package com.fastcampus.toyproject.domain.user.entity;

import com.fastcampus.toyproject.common.BaseTimeEntity;
import com.fastcampus.toyproject.domain.liketrip.entity.LikeTrip;
import com.fastcampus.toyproject.domain.reply.entity.Reply;
import com.fastcampus.toyproject.domain.trip.entity.Trip;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("사용자 고유 번호(ID)")
    private Long userId;

    @Column(nullable = false)
    @Comment("비밀번호")
    private String password;

    @Column(nullable = false)
    @Comment("이메일")
    private String email;

    private String authority;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Trip> tripList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<LikeTrip> likeTripList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Reply> replyList;

    @Embedded
    private BaseTimeEntity baseTimeEntity;

}
