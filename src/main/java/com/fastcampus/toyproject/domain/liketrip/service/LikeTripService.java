package com.fastcampus.toyproject.domain.liketrip.service;

import com.fastcampus.toyproject.domain.liketrip.dto.LikeTripRequest;
import com.fastcampus.toyproject.domain.liketrip.dto.LikeTripResponse;
import com.fastcampus.toyproject.domain.liketrip.entity.LikeTrip;
import com.fastcampus.toyproject.domain.liketrip.repository.LikeTripRepository;
import com.fastcampus.toyproject.domain.trip.dto.TripResponse;
import com.fastcampus.toyproject.domain.trip.entity.Trip;
import com.fastcampus.toyproject.domain.trip.exception.TripException;
import com.fastcampus.toyproject.domain.trip.exception.TripExceptionCode;
import com.fastcampus.toyproject.domain.trip.repository.TripRepository;
import com.fastcampus.toyproject.domain.trip.service.TripService;
import com.fastcampus.toyproject.domain.user.entity.User;
import com.fastcampus.toyproject.domain.user.repository.UserRepository;
import com.fastcampus.toyproject.domain.user.service.UserService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeTripService {

    private final LikeTripRepository likeTripRepository;
    private final UserRepository userRepository;
    private final TripService tripService;

    public LikeTripResponse toggleLike(Long userId, LikeTripRequest request) {
        User user = userRepository.getReferenceById(userId);
        Trip trip = tripService.getTripByTripId(request.getTripId());

        LikeTrip likeTrip = likeTripRepository.findByUserUserIdAndTripTripId(userId,
                request.getTripId())
            .map(like -> {
                boolean isCurrentlyLiked = like.getIsLike();
                like.setIsLike(!isCurrentlyLiked);
                tripService.updateLikesCount(trip.getTripId(), !isCurrentlyLiked);
                return likeTripRepository.save(like);
            })
            .orElseGet(() -> {
                tripService.updateLikesCount(trip.getTripId(), true);
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

    public List<TripResponse> getLikeTripByUserId(Long userId) {
        return likeTripRepository
            .findAllByUserId(userId)
            .orElse(Collections.emptyList())
            .stream().map(likeTrip -> likeTrip.getTrip())
            .map(TripResponse::fromEntity)
            .collect(Collectors.toList());
    }
}
