package com.fastcampus.toyproject.domain.liketrip.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeTripRequest {

    private Long tripId;
    private Boolean isLike;

}
