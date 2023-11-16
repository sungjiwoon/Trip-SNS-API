package com.fastcampus.toyproject.domain.itinerary.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.fastcampus.toyproject.common.BaseTimeEntity;
import com.fastcampus.toyproject.domain.itinerary.dto.ItineraryRequest;
import com.fastcampus.toyproject.domain.itinerary.entity.Itinerary;
import com.fastcampus.toyproject.domain.itinerary.entity.Lodgement;
import com.fastcampus.toyproject.domain.itinerary.entity.Movement;
import com.fastcampus.toyproject.domain.itinerary.entity.Stay;
import com.fastcampus.toyproject.domain.itinerary.type.ItineraryType;
import com.fastcampus.toyproject.domain.trip.entity.Trip;
import com.fastcampus.toyproject.domain.trip.repository.TripRepository;
import com.fastcampus.toyproject.domain.trip.service.TripService;
import com.fastcampus.toyproject.domain.user.entity.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DisplayName("여정 리포지토리 테스트")
class ItineraryRepositoryTest {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private ItineraryRepository itineraryRepository;

    private User user;
    private Trip trip;
    private Trip trip_deleted;
    private List<ItineraryRequest> itineraryRequestList;
    private LocalDateTime now = LocalDateTime.now();
    private Itinerary itinerary;
    private Movement movement;
    private Lodgement lodgement;
    private Stay stay;

    @BeforeEach
    private void setup(){

        user = User.builder()
                .userId(1L)
                .email("test@mail.com")
                .password("1234")
                .build();
        trip = Trip.builder()
                .tripId(1L)
                .tripName("일본여행")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .baseTimeEntity(new BaseTimeEntity())
                .isDomestic(true)
                .itineraryList(List.of())
                .user(user)
                .build();

        movement = Movement.builder()
                .itineraryName("비행기")
                .itineraryType(ItineraryType.MOVEMENT)
                .departurePlace("인천 공항").arrivalPlace("도쿄 공항")
                .departureDate(now).arrivalDate(now)
                .itineraryOrder(1)
                .trip(trip)
                .build();

        lodgement = Lodgement.builder()
                .itineraryName("도쿄 디즈니 월드")
                .itineraryType(ItineraryType.LODGEMENT)
                .checkIn(now)
                .checkOut(now)
                .itineraryOrder(2)
                .trip(trip)
                .build();

        stay = Stay.builder()
                .itineraryName("신주쿠 워싱턴 호텔")
                .itineraryType(ItineraryType.MOVEMENT)
                .departureDate(now).arrivalDate(now)
                .itineraryOrder(3)
                .trip(trip)
                .build();

        itineraryRepository.save(movement);
        itineraryRepository.save(lodgement);
        itineraryRepository.save(stay);
    }

    @Test
    @DisplayName("여정 findById 성공")
    void itineray_find_by_id(){

        //given
        //when
        Optional<Itinerary> result = itineraryRepository.findById(movement.getItineraryId());

        //then
        assertThat(result.get())
                .extracting("itineraryId").isEqualTo(movement.getItineraryId());

        assertThat(result.get())
                .usingRecursiveComparison()
                .ignoringFields("itineraryId")
                .isEqualTo(movement);
    }

    @Test
    @DisplayName("여정 findById 실패")
    void itineray_find_by_id_fail(){
        //given
        Long itId = 999L;
        //when
        Optional<Itinerary> res = itineraryRepository.findById(itId);

        //then
        assertThat(res).isEqualTo(Optional.empty());

    }

    @Test
    @DisplayName("여정 전체 조회")
    void itineray_find_all(){
        //given
        //when
        List<Itinerary> res = itineraryRepository.findAll();

        //then
        assertThat(res)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(movement, lodgement, stay);
    }

    @Test
    @DisplayName("여정 저장 성공")
    void itinerary_save(){
        //given
        Itinerary itTest = Movement.builder()
                .itineraryName("비행기")
                .itineraryType(ItineraryType.MOVEMENT)
                .departurePlace("인천 공항").arrivalPlace("도쿄 공항")
                .departureDate(now).arrivalDate(now)
                .itineraryOrder(1)
                .trip(trip)
                .build();

        //when
        Itinerary res = itineraryRepository.save(itTest);

        //then
        assertThat(res).extracting("itineraryId").isNotNull();
    }


}
