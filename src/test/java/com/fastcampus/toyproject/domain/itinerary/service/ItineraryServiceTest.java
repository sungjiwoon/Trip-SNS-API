package com.fastcampus.toyproject.domain.itinerary.service;

import com.fastcampus.toyproject.domain.itinerary.dto.ItineraryRequest;
import com.fastcampus.toyproject.domain.itinerary.dto.ItineraryResponse;
import com.fastcampus.toyproject.domain.itinerary.exception.ItineraryException;
import com.fastcampus.toyproject.domain.itinerary.exception.ItineraryExceptionCode;
import com.fastcampus.toyproject.domain.itinerary.type.ItineraryType;
import com.fastcampus.toyproject.domain.trip.entity.Trip;
import com.fastcampus.toyproject.domain.trip.exception.TripException;
import com.fastcampus.toyproject.domain.trip.exception.TripExceptionCode;
import com.fastcampus.toyproject.domain.trip.repository.TripRepository;
import com.fastcampus.toyproject.domain.user.entity.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ItineraryServiceTest {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private ItineraryService itineraryService;

    LocalDateTime now = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        itineraryRequestList.add(ir1);
        itineraryRequestList.add(ir2);
        itineraryRequestList.add(ir3);
    }

//    @Test
//    void 여정리스트_삽입_성공() {
//        Trip trip1 = tripRepository.save(trip);
//
//        List<ItineraryResponse> itineraryResponseList = itineraryService.insertItineraries(
//                trip1.getTripId(), itineraryRequestList
//        );
//
//        for (ItineraryResponse ir : itineraryResponseList) {
//            System.out.println(ir.getItineraryType() + ": " + ir.getItineraryName() + " " + ir.getItineraryOrder());
//        }
//    }
//
//    @Test
//    void 여정리스트_삽입_실패_tripId_없는경우() {
//        try {
//            List<ItineraryResponse> itineraryResponseList = itineraryService.insertItineraries(
//                    99L, itineraryRequestList
//            );
//        } catch (TripException e) {
//            Assertions.assertEquals(e.getErrorCode(), TripExceptionCode.NO_SUCH_TRIP);
//        }
//    }
//
//    @Test
//    void 여정리스트_삽입_실패_순서중복() {
//        ItineraryRequest ir4 = ItineraryRequest.builder()
//                .name("도톤보리").type(ItineraryType.STAY)
//                .startDate(now).endDate(now).order(3) //ir3과 중복.
//                .build();
//        itineraryRequestList.add(ir4);
//
//        Trip trip1 = tripRepository.save(trip);
//
//        try {
//            List<ItineraryResponse> itineraryResponseList = itineraryService.insertItineraries(
//                    trip1.getTripId(), itineraryRequestList
//            );
//        } catch (ItineraryException e) {
//            Assertions.assertEquals(e.getErrorCode(), ItineraryExceptionCode.DUPLICATE_ITINERARY_ORDER);
//        }
//    }

    User user = User.builder()
            .email("test@mail.com")
            .password("1234")
            .build()
            ;
    Trip trip = Trip.builder()
            .tripName("일본여행")
            .startDate(LocalDate.now())
            .endDate(LocalDate.now())
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