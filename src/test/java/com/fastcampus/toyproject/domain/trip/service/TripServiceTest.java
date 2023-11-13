package com.fastcampus.toyproject.domain.trip.service;

import static org.junit.jupiter.api.Assertions.*;

import com.fastcampus.toyproject.common.BaseTimeEntity;
import com.fastcampus.toyproject.domain.itinerary.dto.ItineraryRequest;
import com.fastcampus.toyproject.domain.itinerary.entity.Itinerary;
import com.fastcampus.toyproject.domain.itinerary.service.ItineraryService;
import com.fastcampus.toyproject.domain.itinerary.type.ItineraryType;
import com.fastcampus.toyproject.domain.trip.entity.Trip;
import com.fastcampus.toyproject.domain.trip.repository.TripRepository;
import com.fastcampus.toyproject.domain.user.entity.User;
import com.fastcampus.toyproject.domain.user.repository.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TripServiceTest {
    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private ItineraryService itineraryService;

    @Autowired
    private TripService tripService;

    @Autowired
    private UserRepository userRepository;

    Long userId;

    LocalDateTime now = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        User saveUser = userRepository.save(user);
        userId = saveUser.getUserId();
        itineraryRequestList.add(ir1);
        itineraryRequestList.add(ir2);
        itineraryRequestList.add(ir3);
    }
    @Test
    void 여행_삭제_성공() {
        Trip ti = tripRepository.save(trip);
        itineraryService.insertItineraries(ti.getTripId(), itineraryRequestList);
        tripService.deleteTrip(ti.getTripId());
        Optional<Trip> saveTrip = tripRepository.findById(ti.getTripId());
        Assertions.assertNotEquals(saveTrip.get().getBaseTimeEntity().getDeletedAt(), null);
        for (Itinerary it : saveTrip.get().getItineraryList()) {
            Assertions.assertNotEquals(it.getBaseTimeEntity().getDeletedAt(), null);
        }
    }



    User user = User.builder()
            .email("test@mail.com")
            .password("1234")
            .build()
            ;
    Trip trip = Trip.builder()
            .tripName("일본여행")
            .startDate(LocalDate.now())
            .endDate(LocalDate.now())
            .baseTimeEntity(new BaseTimeEntity())
            .user(user)
            .build();

    List<ItineraryRequest> itineraryRequestList = new ArrayList<>();
    ItineraryRequest ir1 = ItineraryRequest.builder()
            .name("비행기")
            .type(ItineraryType.MOVEMENT)
            .startDate(now).endDate(now)
            .departurePlace("인천 공항").arrivalPlace("도쿄 공항")
            .order(1)
            .build();

    ItineraryRequest ir2 = ItineraryRequest.builder()
            .name("도쿄 디즈니 월드")
            .type(ItineraryType.STAY)
            .startDate(now).endDate(now)
            .order(2)
            .build();

    ItineraryRequest ir3 = ItineraryRequest.builder()
            .name("신주쿠 워싱턴 호텔")
            .type(ItineraryType.LODGEMENT)
            .startDate(now).endDate(now)
            .order(3)
            .build();

}