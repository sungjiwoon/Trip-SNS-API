package com.fastcampus.toyproject.domain.liketrip.controller;
import com.fastcampus.toyproject.domain.liketrip.entity.LikeTrip;
import com.fastcampus.toyproject.domain.liketrip.service.LikeTripService;
import com.fastcampus.toyproject.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeTripController {

    private final LikeTripService likeTripService;

    @PostMapping("/{tripId}")
    public ResponseEntity<?> toggleLike(
        Authentication authentication,
        @PathVariable Long tripId
    ) {
        User user = (User) authentication.getPrincipal();
        LikeTrip like = likeTripService.toggleLike(user.getUserId(), tripId);
        return ResponseEntity.ok().body(like);
    }

}
