package com.fastcampus.toyproject.domain.liketrip.service;

import com.fastcampus.toyproject.domain.liketrip.dto.LikeTripRequest;
import com.fastcampus.toyproject.domain.liketrip.dto.LikeTripResponse;
import com.fastcampus.toyproject.domain.liketrip.entity.LikeTrip;
import com.fastcampus.toyproject.domain.liketrip.repository.LikeTripRepository;
import com.fastcampus.toyproject.domain.trip.entity.Trip;
import com.fastcampus.toyproject.domain.trip.service.TripService;
import com.fastcampus.toyproject.domain.user.entity.User;
import com.fastcampus.toyproject.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class LikeTripServiceTest {

    @InjectMocks
    private LikeTripService likeTripService;

    @Mock
    private LikeTripRepository likeTripRepository;

    @Mock
    private TripService tripService;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("좋아요")
    public void testToggleLike_NewLike() {
        Long userId = 1L;
        Long tripId = 1L;
        User user = new User();
        Trip trip = new Trip();
        trip.setTripId(tripId);
        LikeTripRequest request = new LikeTripRequest();
        request.setTripId(tripId);

        when(userService.getUser(userId)).thenReturn(user);
        when(tripService.getTripByTripId(tripId)).thenReturn(trip);
        when(likeTripRepository.findByUserUserIdAndTripTripId(userId, tripId)).thenReturn(
            Optional.empty());
        when(likeTripRepository.save(any(LikeTrip.class))).thenAnswer(i -> {
            LikeTrip savedLike = (LikeTrip) i.getArguments()[0];
            savedLike.setTrip(trip);
            return savedLike;
        });

        LikeTripResponse response = likeTripService.toggleLike(userId, request);

        assertEquals(tripId, response.getTripId());
        assertEquals(true, response.getIsLike());
    }

    @Test
    @DisplayName("좋아요 취소")
    public void testToggleLike_ExistingLike() {
        Long userId = 1L;
        Long tripId = 1L;
        User user = new User();
        Trip trip = new Trip();
        trip.setTripId(tripId);
        LikeTrip existingLike = new LikeTrip(user, trip, true);
        existingLike.setTrip(trip);
        LikeTripRequest request = new LikeTripRequest();
        request.setTripId(tripId);

        when(userService.getUser(userId)).thenReturn(user);
        when(tripService.getTripByTripId(tripId)).thenReturn(trip);
        when(likeTripRepository.findByUserUserIdAndTripTripId(userId, tripId)).thenReturn(
            Optional.of(existingLike));
        when(likeTripRepository.save(any(LikeTrip.class))).thenAnswer(i -> {
            LikeTrip savedLike = (LikeTrip) i.getArguments()[0];
            savedLike.setTrip(trip);
            return savedLike;
        });

        LikeTripResponse response = likeTripService.toggleLike(userId, request);

        assertEquals(tripId, response.getTripId());
        assertEquals(false, response.getIsLike());
    }

}
