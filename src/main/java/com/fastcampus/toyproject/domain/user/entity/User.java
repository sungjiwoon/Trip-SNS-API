package com.fastcampus.toyproject.domain.user.entity;

import com.fastcampus.toyproject.common.BaseTimeEntity;
import com.fastcampus.toyproject.domain.liketrip.LikeTrip;
import com.fastcampus.toyproject.domain.reply.Reply;
import com.fastcampus.toyproject.domain.trip.entity.Trip;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
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

    @OneToMany
    private List<Trip> tripList;

    @OneToMany
    private List<LikeTrip> likeTripList;

    @OneToMany
    private List<Reply> replyList;

    @Embedded
    private BaseTimeEntity baseTimeEntity;

}
