package com.fastcampus.toyproject.domain.trip.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TripRequest {

    @NotNull(message = "여행 이름을 입력하세요.")
    private String tripName;

    @NotNull(message = "출발 날짜를 입력하세요.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "도착 날짜를 입력하세요.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotNull(message = "국내 여행 여부를 입력하세요.")
    private Boolean isDomestic;
}
