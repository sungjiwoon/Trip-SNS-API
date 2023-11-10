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

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    private String authority;

    @OneToMany(mappedBy = "trip", fetch = FetchType.LAZY)
    private List<Trip> tripList;

    @OneToMany(mappedBy = "likeTrip", fetch = FetchType.LAZY)
    private List<LikeTrip> likeTripList;

    @OneToMany(mappedBy = "reply", fetch = FetchType.LAZY)
    private List<Reply> replyList;

    @Embedded
    private BaseTimeEntity baseTimeEntity;

}
