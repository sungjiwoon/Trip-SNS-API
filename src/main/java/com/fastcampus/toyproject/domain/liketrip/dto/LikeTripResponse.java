package com.fastcampus.toyproject.domain.liketrip.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LikeTripResponse {

    private Long likeTripId;
    private Long tripId;
    private Boolean isLike;

}
