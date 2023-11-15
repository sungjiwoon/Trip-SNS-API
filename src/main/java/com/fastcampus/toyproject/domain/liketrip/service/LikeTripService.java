package com.fastcampus.toyproject.domain.liketrip.service;

import com.fastcampus.toyproject.domain.liketrip.dto.LikeTripRequest;
import com.fastcampus.toyproject.domain.liketrip.dto.LikeTripResponse;
import com.fastcampus.toyproject.domain.liketrip.entity.LikeTrip;
import com.fastcampus.toyproject.domain.liketrip.repository.LikeTripRepository;
import com.fastcampus.toyproject.domain.trip.entity.Trip;
import com.fastcampus.toyproject.domain.trip.exception.TripException;
import com.fastcampus.toyproject.domain.trip.exception.TripExceptionCode;
import com.fastcampus.toyproject.domain.trip.repository.TripRepository;
import com.fastcampus.toyproject.domain.user.entity.User;
import com.fastcampus.toyproject.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeTripService {

    private final LikeTripRepository likeTripRepository;
    private final TripRepository tripRepository;
    private final UserService userService;

    public LikeTripResponse toggleLike(Long userId, LikeTripRequest request) {
        User user = userService.getUser(userId);
        Trip trip = tripRepository.findById(request.getTripId())
            .orElseThrow(() -> new TripException(TripExceptionCode.NO_SUCH_TRIP));

        LikeTrip likeTrip = likeTripRepository.findByUserUserIdAndTripTripId(userId, request.getTripId())
            .map(like -> {
                boolean isCurrentlyLiked = like.getIsLike();
                like.setIsLike(!isCurrentlyLiked); // 상태 변경
                updateLikesCount(trip, !isCurrentlyLiked); // 좋아요 개수 업데이트
                return likeTripRepository.save(like);
            })
            .orElseGet(() -> {
                updateLikesCount(trip, true); // 좋아요 추가
                return likeTripRepository.save(new LikeTrip(user, trip, true));
            });

        return convertToResponse(likeTrip);
    }

    private void updateLikesCount(Trip trip, boolean increase) {
        int currentLikes = trip.getLikesCount() != null ? trip.getLikesCount() : 0;
        if (increase) {
            trip.setLikesCount(currentLikes + 1);
        } else if (currentLikes > 0) {
            trip.setLikesCount(currentLikes - 1);
        }
        tripRepository.save(trip);
    }


    private LikeTripResponse convertToResponse(LikeTrip likeTrip) {
        LikeTripResponse response = new LikeTripResponse();
        response.setLikeTripId(likeTrip.getLikeTripId());
        response.setTripId(likeTrip.getTrip().getTripId());
        response.setIsLike(likeTrip.getIsLike());
        return response;
    }
}
