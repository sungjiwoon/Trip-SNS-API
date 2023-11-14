package com.fastcampus.toyproject.domain.liketrip.controller;

import com.fastcampus.toyproject.common.dto.ResponseDTO;
import com.fastcampus.toyproject.config.security.jwt.UserPrincipal;
import com.fastcampus.toyproject.domain.liketrip.dto.LikeTripRequest;
import com.fastcampus.toyproject.domain.liketrip.dto.LikeTripResponse;
import com.fastcampus.toyproject.domain.liketrip.service.LikeTripService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trip")
@RequiredArgsConstructor
public class LikeTripController {

    private final LikeTripService likeTripService;

    @PostMapping("/{tripId}/like")
    public ResponseDTO<LikeTripResponse> toggleLike(
        UserPrincipal userPrincipal,
        @PathVariable final Long tripId
    ) {
        Long userId = userPrincipal.getUserId();

        LikeTripRequest request = new LikeTripRequest();
        request.setTripId(tripId);

        LikeTripResponse likeResponse = likeTripService.toggleLike(userId, request);
        return ResponseDTO.ok("좋아요 상태 변경 완료", likeResponse);
    }
}
