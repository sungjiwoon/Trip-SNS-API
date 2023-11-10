package com.fastcampus.toyproject.domain.reply;


import com.fastcampus.toyproject.common.BaseTimeEntity;
import com.fastcampus.toyproject.domain.user.entity.User;
import com.fastcampus.toyproject.domain.trip.entity.Trip;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Reply {

    @Id
    private Long id;

    @ManyToOne
    private User user;

    @OneToOne
    private Trip trip;

    private String content;

    @Embedded
    private BaseTimeEntity baseTimeEntity;

}
