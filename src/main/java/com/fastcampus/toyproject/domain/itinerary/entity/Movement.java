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
public class Movement extends Itinerary {

    @Column(nullable = false)
    @Comment("출발 일시")
    private LocalDateTime departureDate;

    @Column(nullable = false)
    @Comment("도착 일시")
    private LocalDateTime arrivalDate;

    @Column(nullable = false)
    @Comment("출발 장소")
    private String departurePlace;

    @Column(nullable = false)
    @Comment("도착 장소")
    private String arrivalPlace;

    @Comment("출발지 위치 정보")
    private String departurePlaceInfo;

    @Comment("도착지 위치 정보")
    private String arrivalPlaceInfo;


    public void updateMovement(ItineraryUpdateRequest req) {
        super.updateItineraryName(req.getName());
        super.updateItineraryOrder(req.getOrder());
        this.departureDate = req.getStartDate();
        this.departurePlace = req.getDeparturePlace();
        this.arrivalDate = req.getEndDate();
        this.arrivalPlace = req.getArrivalPlace();
        this.departurePlaceInfo = LocationUtil.requestKeywordSearch(req.getDeparturePlace());
        this.arrivalPlaceInfo = LocationUtil.requestKeywordSearch(req.getArrivalPlace());
    }
}
