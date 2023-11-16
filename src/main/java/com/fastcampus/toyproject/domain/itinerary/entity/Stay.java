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
public class Stay extends Itinerary {

    @Column(nullable = false)
    @Comment("출발 일시")
    private LocalDateTime departureDate;

    @Column(nullable = false)
    @Comment("도착 일시")
    private LocalDateTime arrivalDate;

    @Comment("위치 정보")
    private String placeInfo;

    public void updateStay(ItineraryUpdateRequest req) {
        super.updateItineraryName(req.getName());
        super.updateItineraryOrder(req.getOrder());
        this.departureDate = req.getStartDate();
        this.arrivalDate = req.getEndDate();
        this.placeInfo = LocationUtil.requestKeywordSearch(req.getName());
    }
}
