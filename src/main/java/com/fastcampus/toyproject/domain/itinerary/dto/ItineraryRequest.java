package com.fastcampus.toyproject.domain.itinerary.dto;

import static com.fastcampus.toyproject.domain.itinerary.exception.ItineraryExceptionCode.EMPTY_ARRIVAL_PLACE;
import static com.fastcampus.toyproject.domain.itinerary.exception.ItineraryExceptionCode.EMPTY_DEPARTURE_PLACE;

import com.fastcampus.toyproject.domain.itinerary.exception.ItineraryException;
import com.fastcampus.toyproject.domain.itinerary.type.ItineraryType;
import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ItineraryRequest {

    @NotNull(message = "여정 타입을 입력하세요. ")
    private ItineraryType type;

    @NotNull(message = "여정 이름을 입력하세요. ")
    private String name;

    @NotNull(message = "출발 날짜를 입력하세요.")
    private LocalDateTime startDate;

    @NotNull(message = "도착 날짜를 입력하세요.")
    private LocalDateTime endDate;

    @NotNull(message = "여정 순서를 입력하세요.")
    @Min(1)
    private Integer order;

    private String departurePlace;
    private String arrivalPlace;

}
