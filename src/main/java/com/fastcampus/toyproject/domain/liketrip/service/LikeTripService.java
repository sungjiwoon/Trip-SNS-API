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

                like.setIsLike(!like.getIsLike());
                return likeTripRepository.save(like);
            })
            .orElseGet(() -> {

                return likeTripRepository.save(new LikeTrip(user, trip, true));
            });

        return convertToResponse(likeTrip);
    }

    private LikeTripResponse convertToResponse(LikeTrip likeTrip) {
        LikeTripResponse response = new LikeTripResponse();
        response.setLikeTripId(likeTrip.getLikeTripId());
        response.setTripId(likeTrip.getTrip().getTripId());
        response.setIsLike(likeTrip.getIsLike());
        return response;
    }
}
