package com.fastcampus.toyproject.domain.liketrip.service;

import com.fastcampus.toyproject.domain.liketrip.entity.LikeTrip;
import com.fastcampus.toyproject.domain.liketrip.repository.LikeTripRepository;
import com.fastcampus.toyproject.domain.trip.entity.Trip;
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

    public LikeTrip toggleLike(Long userId, Long tripId) {
        User user = userService.getUser(userId);
        Trip trip = tripRepository.findById(tripId)
            .orElseThrow(() -> new RuntimeException("Trip not found"));

        return likeTripRepository.findByUserUserIdAndTripTripId(userId, tripId)
            .map(likeTrip -> {
                likeTrip.setIsLike(!likeTrip.getIsLike());
                return likeTripRepository.save(likeTrip);
            })
            .orElseGet(() -> {
                LikeTrip newLike = new LikeTrip(user, trip, true);
                return likeTripRepository.save(newLike);
            });
    }
}
