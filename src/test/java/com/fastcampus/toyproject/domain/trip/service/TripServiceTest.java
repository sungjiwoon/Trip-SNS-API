package com.fastcampus.toyproject.domain.trip.service;

import static com.fastcampus.toyproject.domain.trip.exception.TripExceptionCode.NOT_MATCH_BETWEEN_USER_AND_TRIP;
import static com.fastcampus.toyproject.domain.trip.exception.TripExceptionCode.NO_SUCH_TRIP;
import static com.fastcampus.toyproject.domain.trip.exception.TripExceptionCode.TRIP_ALREADY_DELETED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.fastcampus.toyproject.common.BaseTimeEntity;
import com.fastcampus.toyproject.common.util.DateUtil;
import com.fastcampus.toyproject.domain.itinerary.dto.ItineraryRequest;
import com.fastcampus.toyproject.domain.itinerary.dto.LodgementResponse;
import com.fastcampus.toyproject.domain.itinerary.entity.Itinerary;
import com.fastcampus.toyproject.domain.itinerary.entity.Lodgement;
import com.fastcampus.toyproject.domain.itinerary.service.ItineraryService;
import com.fastcampus.toyproject.domain.itinerary.type.ItineraryType;
import com.fastcampus.toyproject.domain.trip.dto.TripDetailResponse;
import com.fastcampus.toyproject.domain.trip.dto.TripRequest;
import com.fastcampus.toyproject.domain.trip.dto.TripResponse;
import com.fastcampus.toyproject.domain.trip.entity.Trip;
import com.fastcampus.toyproject.domain.trip.exception.TripException;
import com.fastcampus.toyproject.domain.trip.exception.TripExceptionCode;
import com.fastcampus.toyproject.domain.trip.repository.TripRepository;
import com.fastcampus.toyproject.domain.user.entity.User;
import com.fastcampus.toyproject.domain.user.repository.UserRepository;
import com.fastcampus.toyproject.domain.user.service.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DisplayName("여행 서비스 테스트")
class TripServiceTest {


    private ItineraryService itineraryService;

    private TripService tripService;

    @Mock
    private TripRepository tripRepository;

    @Mock
    private UserRepository userRepository;


    private User user;
    private Trip trip;
    private Trip trip_deleted;
    private Trip trip_details;
    private List<ItineraryRequest> itineraryRequestList;
    private LocalDateTime now = LocalDateTime.now();

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

        trip_deleted = Trip.builder()
            .tripId(1L)
            .tripName("일본여행")
            .startDate(LocalDate.now())
            .endDate(LocalDate.now())
            .baseTimeEntity(BaseTimeEntity.builder().deletedAt(now).build())
            .user(user)
            .build();


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

        itineraryRequestList = new ArrayList<>();
        itineraryRequestList.add(ir1);
        itineraryRequestList.add(ir2);
        itineraryRequestList.add(ir3);

        tripService = new TripService(tripRepository, userRepository);

    }

    @Nested
    @DisplayName("여행 조회 기능 테스트")
    class SelectTrip {

        @Test
        @DisplayName("여행 전체 조회 성공")
        void selectTrips_success(){

            //given

            //when
            when(tripRepository.findAll()).thenReturn(List.of(trip, trip_deleted));
            List<TripResponse> result = tripService.getAllTrips();

            //then
            assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(List.of(TripResponse.fromEntity(trip)));
        }


        @Test
        @DisplayName("여행 상세 조회 기능 성공")
        void selectTrip_detail(){

            //given
            Long tripId = 1L;

            //when
            when(tripRepository.findById(anyLong())).thenReturn(Optional.ofNullable(trip));
            TripDetailResponse tripDetailResponse = tripService.getTripDetail(tripId);

            //then
            assertThat(tripDetailResponse).usingRecursiveComparison()
                    .isEqualTo(TripDetailResponse.builder()
                        .tripId(1L)
                        .userId(user.getUserId())
                        .tripName("일본여행")
                        .startDate(LocalDate.now())
                        .endDate(LocalDate.now())
                        .isDomestic(true)
                        .itineraryList(List.of())
                        .build()
            );
        }

        @Test
        @DisplayName("여행 상세 조회 여행 아이디 없음")
        void selectTrip_detail_no_such_trip(){

            //given
            Long id = 1L;

            //when
            when(tripRepository.findById(anyLong())).thenThrow(new TripException(NO_SUCH_TRIP));

            //then
            assertThatExceptionOfType(TripException.class)
                .isThrownBy(() -> tripService.getTripDetail(id))
                .withMessage("해당하는 여행 정보가 없습니다.")
                .extracting("errorCode")
                .isEqualTo(NO_SUCH_TRIP);

        }

        @Test
        @DisplayName("여행 상세 조회 여행 삭제 됨")
        void selectTrip_detail_already_deleted(){

            //given
            Long id = 1L;

            //when
            when(tripRepository.findById(anyLong())).thenReturn(Optional.of(trip_deleted));

            //then
            assertThatExceptionOfType(TripException.class)
                .isThrownBy(() -> tripService.getTripDetail(id))
                .withMessage("이미 삭제된 여행 정보입니다.")
                .extracting("errorCode")
                .isEqualTo(TRIP_ALREADY_DELETED);

        }

        @Test
        @DisplayName("여행 키워드 조회 성공")
        void selectTrip_keyword_success(){

            //given
            String keyword = "여행";

            //when
            when(tripRepository.findByTripNameContains(anyString())).thenReturn(
                Optional.of(List.of(trip, trip_deleted)));

            Optional<List<TripResponse>> result
                = tripService.getTripByKeyword(keyword);

            //then
            assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(Optional.of(List.of(
                    TripResponse.builder()
                        .tripId(1L)
                        .userId(1L)
                        .tripName("일본여행")
                        .startDate(LocalDate.now())
                        .endDate(LocalDate.now())
                        .isDomestic(true)
                        .tripPeriod(DateUtil.getDaysBetweenDate(now, now))
                        .build()
                )));

        }

    }


    @Nested
    @DisplayName("여행 추가,삭제,수정")
    class Trip_CUD{

        TripRequest tripRequest;
        TripResponse tripResponse;

        @BeforeEach
        void trip_cud_setup(){
            tripRequest = TripRequest.builder()
                .tripName("일본여행")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .isDomestic(true)
                .build();
            tripResponse = TripResponse.fromEntity(trip);

        }

        @Test
        @DisplayName("여행 추가 성공")
        void insertTrip_success(){

            //given
            //when
            when(tripRepository.save(any(Trip.class))).thenReturn(trip);
            TripResponse result = tripService.insertTrip(user.getUserId(), tripRequest);
            //then
            assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(tripResponse);
        }
        
        @Test
        @DisplayName("여행 수정 성공")
        void updateTrip_success(){
            //given
            //when
            when(tripRepository.findById(anyLong())).thenReturn(Optional.of(trip));
            when(tripRepository.save(any(Trip.class))).thenReturn(trip);
            TripResponse result = tripService.updateTrip(
                user.getUserId(),
                trip.getTripId(),
                tripRequest);
            //then
            assertThat(result).usingRecursiveComparison()
                .isEqualTo(tripResponse);
        }

        @Test
        @DisplayName("여행 수정 실패 해당 여행 없음")
        void updateTrip_no_such_trip(){
            //given
            //when
            when(tripRepository.findById(anyLong())).thenThrow(new TripException(NO_SUCH_TRIP));
            //then
            assertThatExceptionOfType(TripException.class)
                .isThrownBy(() -> tripService.updateTrip(1L, 1L, tripRequest))
                .withMessage("해당하는 여행 정보가 없습니다.")
                .extracting("errorCode")
                .isEqualTo(NO_SUCH_TRIP);
        }

        @Test
        @DisplayName("여행 수정 실패 삭제 된 여행")
        void updateTrip_already_deleted(){

            //given
            //when
            when(tripRepository.findById(anyLong())).thenReturn(Optional.of(trip_deleted));
            //then
            assertThatExceptionOfType(TripException.class)
                .isThrownBy(() -> tripService.updateTrip(1L, 1L, tripRequest))
                .withMessage("이미 삭제된 여행 정보입니다.")
                .extracting("errorCode")
                .isEqualTo(TRIP_ALREADY_DELETED);

        }

        @Test
        @DisplayName("여행 수정 실패 유저가 다름")
        void updateTrip_user_trip_difference(){
            //given
            //when
            when(tripRepository.findById(anyLong())).thenReturn(Optional.of(trip));

            //then
            assertThatExceptionOfType(TripException.class)
                .isThrownBy(() -> tripService.updateTrip(2L, 1L, tripRequest))
                .withMessage("로그인 유저와 유저의 여행 정보가 일치하지 않습니다.")
                .extracting("errorCode")
                .isEqualTo(NOT_MATCH_BETWEEN_USER_AND_TRIP);
        }

        @Test
        @DisplayName("여행 삭제 성공")
        void deleteTrip_success(){
            //given
            //when
            when(tripRepository.findById(anyLong())).thenReturn(Optional.of(trip));
            when(tripRepository.save(any(Trip.class))).thenReturn(trip);
            TripResponse result = tripService.deleteTrip(1L,1L);

            //then
            assertThat(result).usingRecursiveComparison()
                .isEqualTo(tripResponse);
        }




    }





}
