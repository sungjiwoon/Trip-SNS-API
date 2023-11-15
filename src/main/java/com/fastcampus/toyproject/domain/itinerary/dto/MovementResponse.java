package com.fastcampus.toyproject.domain.itinerary.dto;

import com.fastcampus.toyproject.common.util.DateUtil;
import com.fastcampus.toyproject.common.util.LocationUtil;
import com.fastcampus.toyproject.domain.itinerary.entity.Movement;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MovementResponse extends ItineraryResponse {


    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private String departurePlace;
    private String arrivalPlace;
    private String timeDifference;
    private String departureLocation;
    private String arrivalLocation;

    public static MovementResponse fromEntity(Movement entity) {
        return MovementResponse
            .builder()
            .id(entity.getItineraryId())
            .itineraryName(entity.getItineraryName())
            .itineraryOrder(entity.getItineraryOrder())
            .itineraryType(entity.getItineraryType())
            .departureDate(entity.getDepartureDate())
            .arrivalDate(entity.getArrivalDate())
            .departurePlace(entity.getDeparturePlace())
            .arrivalPlace(entity.getArrivalPlace())
            .timeDifference(
                DateUtil.getTimeBetweenDate(entity.getDepartureDate(), entity.getArrivalDate()))
            .departureLocation(LocationUtil.requestKeywordSearch(entity.getDeparturePlace()))
            .arrivalLocation(LocationUtil.requestKeywordSearch(entity.getArrivalPlace()))
            .build();
    }
}
