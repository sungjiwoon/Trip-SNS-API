package com.fastcampus.toyproject.domain.itinerary.dto;

import com.fastcampus.toyproject.common.util.DateUtil;
import com.fastcampus.toyproject.domain.itinerary.entity.Stay;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class StayResponse extends ItineraryResponse {

    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private String timeDifference;
    private String placeInfo;

    public static StayResponse fromEntity(Stay entity) {
        return StayResponse
            .builder()
            .id(entity.getItineraryId())
            .itineraryName(entity.getItineraryName())
            .placeInfo(entity.getPlaceInfo())
            .itineraryOrder(entity.getItineraryOrder())
            .itineraryType(entity.getItineraryType())
            .departureDate(entity.getDepartureDate())
            .arrivalDate(entity.getArrivalDate())
            .timeDifference(
                DateUtil.getTimeBetweenDate(entity.getDepartureDate(), entity.getArrivalDate()))
            .build();
    }
}
