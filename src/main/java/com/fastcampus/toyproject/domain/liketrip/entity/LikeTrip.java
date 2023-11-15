package com.fastcampus.toyproject.domain.liketrip.entity;

import com.fastcampus.toyproject.domain.trip.entity.Trip;
import com.fastcampus.toyproject.domain.user.entity.User;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class LikeTrip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("좋아요 ID")
    private Long likeTripId;

    @ManyToOne
    @JoinColumn(name = "userId")
    @Comment("사용자 FK")
    private User user;

    @OneToOne
    @JoinColumn(name = "tripId")
    @Comment("여행 FK")
    private Trip trip;

    @Comment("좋아요 여부")
    private Boolean isLike;



    public LikeTrip(User user, Trip trip, Boolean isLike) {
        this.user = user;
        this.trip = trip;
        this.isLike = isLike;
    }
}
