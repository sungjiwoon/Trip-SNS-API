package com.fastcampus.toyproject.domain.itinerary.entity;

import com.fastcampus.toyproject.common.util.LocationUtil;
import com.fastcampus.toyproject.domain.itinerary.dto.ItineraryUpdateRequest;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Lodgement extends Itinerary {

    @Column(nullable = false)
    @Comment("체크인 일시")
    private LocalDateTime checkIn;

    @Column(nullable = false)
    @Comment("도착지 일시")
    private LocalDateTime checkOut;

    @Comment("위치 정보")
    private String placeInfo;


    public void updateLodgement(ItineraryUpdateRequest req) {
        super.updateItineraryName(req.getName());
        super.updateItineraryOrder(req.getOrder());
        this.checkIn = req.getStartDate();
        this.checkOut = req.getEndDate();
        this.placeInfo = LocationUtil.requestKeywordSearch(req.getName());
    }

}
