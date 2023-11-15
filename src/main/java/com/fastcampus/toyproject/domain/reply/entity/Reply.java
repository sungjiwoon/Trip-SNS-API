package com.fastcampus.toyproject.domain.reply.entity;


import com.fastcampus.toyproject.common.BaseTimeEntity;
import com.fastcampus.toyproject.domain.trip.entity.Trip;
import com.fastcampus.toyproject.domain.user.entity.User;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("댓글 ID")
    private Long replyId;

    @ManyToOne
    @JoinColumn(name = "userId")
    @Comment("사용자 FK")
    private User user;

    @ManyToOne
    @JoinColumn(name = "tripId")
    @Comment("여행 FK")
    private Trip trip;

    @Comment("댓글 내용")
    private String content;

    @Embedded
    private BaseTimeEntity baseTimeEntity;

}
