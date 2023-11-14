package com.fastcampus.toyproject.domain.trip.controller;

import com.fastcampus.toyproject.common.dto.ResponseDTO;
import com.fastcampus.toyproject.common.util.DateUtil;
import com.fastcampus.toyproject.config.security.jwt.UserPrincipal;
import com.fastcampus.toyproject.domain.trip.dto.TripDetailResponse;
import com.fastcampus.toyproject.domain.trip.dto.TripRequest;
import com.fastcampus.toyproject.domain.trip.dto.TripResponse;
import com.fastcampus.toyproject.domain.trip.service.TripService;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 여행과 관련된 Trip Rest Controller trip 삽입, 수정, 삭제, 전체 조회, 상세 조회 기능
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/trip")
@Slf4j
public class TripController {

    private final TripService tripService;

    @GetMapping("/all")
    public ResponseDTO<List<TripResponse>> getAllTrips(
    ) {
        return ResponseDTO.ok("모든 여행 조회 완료",
            tripService.getAllTrips()
        );
    }


    @GetMapping("/{tripId}")
    public ResponseDTO<TripDetailResponse> getTripDetail(
        @PathVariable final Long tripId
    ) {
        return ResponseDTO.ok("상세 여행 조회 완료",
            tripService.getTripDetail(tripId)
        );
    }

    @GetMapping("/search")
    public ResponseDTO<List<TripResponse>> searchTripListByKeyword(

            @Valid @RequestParam("keyword")
            @NotBlank(message = "검색어를 채워주세요")
            @Range(min = 1, max = 10, message = "검색어는 한 글자 이상이어야 합니다.")
            final String keyword

    ) {
        System.out.println("keyword : " + keyword);
        Optional<List<TripResponse>> optional = tripService.getTripByKeyword(keyword);
        if (optional.get().size() == 0) {
            return ResponseDTO.ok(
                "검색된 여행이 없습니다.", null
            );
        }
        return ResponseDTO.ok("여행 검색 완료", optional.get());
    }

    @PostMapping()
    public ResponseDTO<TripResponse> insertTrip(

        UserPrincipal userPrincipal,

        @Valid @RequestBody final TripRequest tripRequest
    ) {
        DateUtil.isStartDateEarlierThanEndDate(
            tripRequest.getStartDate(),
            tripRequest.getEndDate()
        );

        Long userId = userPrincipal.getUserId();
        log.info("TripController:: user ID : {} ", userPrincipal.getUserId());
        return ResponseDTO.ok("여행 삽입 완료",

            tripService.insertTrip(userId, tripRequest)

        );
    }

    @PutMapping("/{tripId}")
    public ResponseDTO<TripResponse> updateTrip(

        @PathVariable final Long tripId,
        final UserPrincipal userPrincipal,
        @Valid @RequestBody final TripRequest tripRequest
    ) {
        DateUtil.isStartDateEarlierThanEndDate(
            tripRequest.getStartDate(),
            tripRequest.getEndDate()
        );

        Long userId = userPrincipal.getUserId();
        log.info("TripController:: user ID : {} ", userId);
        return ResponseDTO.ok("여행 수정 완료",

            tripService.updateTrip(userId, tripId, tripRequest)

        

        );
    }


    @DeleteMapping("/{tripId}")

    public ResponseDTO<Void> deleteTrip(
        UserPrincipal userPrincipal,
        @PathVariable final Long tripId
    ) {
        Long userId = userPrincipal.getUserId();
        log.info("TripController:: user ID : {} ", userId);
        tripService.deleteTrip(userId,tripId);
        return ResponseDTO.ok("여행 삭제 완료");

    }

}
