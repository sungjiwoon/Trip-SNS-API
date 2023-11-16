package com.fastcampus.toyproject.domain.trip.repository;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.fastcampus.toyproject.common.BaseTimeEntity;
import com.fastcampus.toyproject.domain.trip.entity.Trip;
import com.fastcampus.toyproject.domain.user.entity.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DisplayName("여행 리포지토리 테스트")
public class TripRepositoryTest {

    @Autowired
    private TripRepository tripRepository;

    private Trip trip;
    private Trip trip2;
    private Trip trip_deleted;
    private User user;
    private LocalDate now = LocalDate.now();

    @BeforeEach
    public void setup(){

        user = User.builder()
            .userId(1L)
            .email("test@mail.com")
            .password("1234")
            .build();

        trip = Trip.builder()
            .tripName("일본여행")
            .startDate(now)
            .endDate(now)
            .baseTimeEntity(new BaseTimeEntity())
            .isDomestic(true)
            .itineraryList(List.of())
            .user(user)
            .build();

        trip2 = Trip.builder()
            .tripName("일본여행2")
            .startDate(now)
            .endDate(now)
            .baseTimeEntity(new BaseTimeEntity())
            .isDomestic(false)
            .itineraryList(List.of())
            .user(user)
            .build();

        trip_deleted = Trip.builder()
            .tripName("일본여행3")
            .startDate(now)
            .endDate(now)
            .baseTimeEntity(BaseTimeEntity.builder().deletedAt(LocalDateTime.now()).build())
            .isDomestic(false)
            .itineraryList(List.of())
            .user(user)
            .build();

        tripRepository.save(trip);
        tripRepository.save(trip2);
        tripRepository.save(trip_deleted);
    }

    @Test
    @DisplayName("여행 findById 성공")
    void trip_find_by_id(){

        //given
        //when
        Optional<Trip> result = tripRepository.findById(trip.getTripId());

        //then
        assertThat(result.get())
            .extracting("tripId").isEqualTo(trip.getTripId());

        assertThat(result.get())
            .usingRecursiveComparison()
            .ignoringFields("tripId")
            .isEqualTo(trip);



    }

    @Test
    @DisplayName("여행 findById 실패")
    void trip_find_by_id_fail(){
        //given
        Long tripId = 999L;
        //when
        Optional<Trip> result = tripRepository.findById(tripId);

        //then
        assertThat(result).isEqualTo(Optional.empty());

    }

    @Test
    @DisplayName("여행 전체 조회")
    void trip_find_all(){
        //given
        //when
        List<Trip> result = tripRepository.findAll();

        //then
        assertThat(result)
            .usingRecursiveFieldByFieldElementComparator()
            .contains(trip, trip2, trip_deleted);
    }

    @Test
    @DisplayName("여행 저장 성공")
    void trip_save(){
        //given
        Trip triptest  = Trip.builder()
            .tripName("일본여행")
            .startDate(now)
            .endDate(now)
            .baseTimeEntity(new BaseTimeEntity())
            .isDomestic(true)
            .itineraryList(List.of())
            .user(user)
            .build();

        //when
        Trip result = tripRepository.save(triptest);

        //then
        assertThat(result).extracting("tripId").isNotNull();
    }

    @Test
    @DisplayName("여행 검색어로 검색 성공")
    void trip_by_keyword(){

        //given
        String keyword = "일본";
        //when
        List<Trip> result = tripRepository.findByTripNameContains(keyword).get();
        //then

        assertThat(result)
            .usingRecursiveFieldByFieldElementComparator()
            .contains(trip, trip2, trip_deleted);

    }



}
