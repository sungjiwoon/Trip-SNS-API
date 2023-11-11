package com.fastcampus.toyproject.domain.trip.entity;

import com.fastcampus.toyproject.common.BaseTimeEntity;
import com.fastcampus.toyproject.domain.itinerary.entity.Itinerary;
import com.fastcampus.toyproject.domain.reply.entity.Reply;
import com.fastcampus.toyproject.domain.trip.dto.TripRequest;
import com.fastcampus.toyproject.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Trip {

    @OneToMany(mappedBy = "trip",
        cascade = {CascadeType.ALL},
        fetch = FetchType.EAGER,
        orphanRemoval = true)
    private List<Itinerary> itineraryList = new ArrayList<>();


    @OneToMany(mappedBy = "trip", fetch = FetchType.LAZY)
    private List<Reply> replyList;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Comment("여행 ID")
    private Long tripId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @JsonIgnore
    @Comment("사용자 FK")
    private User user;

    @Column(nullable = false)
    @Comment("여행 제목")
    private String tripName;

    @Column(nullable = false)
    @Comment("여행 시작일")
    private LocalDate startDate;

    @Column(nullable = false)
    @Comment("여행 종료일")
    private LocalDate endDate;

    @ColumnDefault("true")
    @Comment("국내여행 여부")
    private Boolean isDomestic;

    //    @ColumnDefault("0") // 검색
    @Comment("좋아요 개수")
    private Integer likesCount;

    @Embedded
    private BaseTimeEntity baseTimeEntity;

    public void delete() {
        baseTimeEntity.delete(LocalDateTime.now());
    }

    public void updateFromDTO(TripRequest tripDTO) {
        this.tripName = tripDTO.getTripName();
        this.startDate = tripDTO.getStartDate();
        this.endDate = tripDTO.getEndDate();
        this.isDomestic = tripDTO.getIsDomestic();
    }


}
