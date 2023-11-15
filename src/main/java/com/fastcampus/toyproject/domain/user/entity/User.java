package com.fastcampus.toyproject.domain.user.entity;

import com.fastcampus.toyproject.common.BaseTimeEntity;
import com.fastcampus.toyproject.domain.liketrip.entity.LikeTrip;
import com.fastcampus.toyproject.domain.reply.entity.Reply;
import com.fastcampus.toyproject.domain.trip.entity.Trip;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("사용자 고유 번호(ID)")
    private Long userId;

    @Column(nullable = false)
    @Comment("이메일")
    private String email;

    @Column(nullable = false)
    @Comment("비밀번호")
    private String password;

    @Comment("이름")
    private String name;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Trip> tripList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<LikeTrip> likeTripList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Reply> replyList;

    @Embedded
    private BaseTimeEntity baseTimeEntity;

}
