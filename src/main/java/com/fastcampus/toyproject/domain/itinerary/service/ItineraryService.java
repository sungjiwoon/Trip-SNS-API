package com.fastcampus.toyproject.domain.itinerary.service;

import static com.fastcampus.toyproject.domain.itinerary.exception.ItineraryExceptionCode.EMPTY_ITINERARY;
import static com.fastcampus.toyproject.domain.itinerary.exception.ItineraryExceptionCode.ITINERARY_ALREADY_DELETED;
import static com.fastcampus.toyproject.domain.itinerary.exception.ItineraryExceptionCode.ITINERARY_NOT_MATCH_TRIP;
import static com.fastcampus.toyproject.domain.itinerary.exception.ItineraryExceptionCode.ITINERARY_SAVE_FAILED;
import static com.fastcampus.toyproject.domain.itinerary.exception.ItineraryExceptionCode.NO_ITINERARY;
import static com.fastcampus.toyproject.domain.trip.exception.TripExceptionCode.NOT_MATCH_BETWEEN_USER_AND_TRIP;
import static com.fastcampus.toyproject.domain.trip.exception.TripExceptionCode.NO_SUCH_TRIP;

import com.fastcampus.toyproject.domain.itinerary.dto.ItineraryRequest;
import com.fastcampus.toyproject.domain.itinerary.dto.ItineraryResponse;
import com.fastcampus.toyproject.domain.itinerary.dto.ItineraryResponseFactory;
import com.fastcampus.toyproject.domain.itinerary.dto.ItineraryUpdateRequest;
import com.fastcampus.toyproject.domain.itinerary.entity.Itinerary;
import com.fastcampus.toyproject.domain.itinerary.entity.ItineraryFactory;
import com.fastcampus.toyproject.domain.itinerary.exception.ItineraryException;
import com.fastcampus.toyproject.domain.itinerary.repository.ItineraryRepository;
import com.fastcampus.toyproject.domain.itinerary.util.ItineraryOrderUtil;
import com.fastcampus.toyproject.domain.trip.entity.Trip;
import com.fastcampus.toyproject.domain.trip.exception.TripException;
import com.fastcampus.toyproject.domain.trip.repository.TripRepository;
import com.fastcampus.toyproject.domain.trip.service.TripService;
import com.fastcampus.toyproject.domain.user.service.UserService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItineraryService {

    private final TripService tripService;
    private final ItineraryRepository itineraryRepository;

    /**
     * trip 객체를 이용하여 연관된 itinerary 리스트 반환하는 메소드
     *
     * @param trip
     * @return List<Itinerary>
     */
    private static List<Itinerary> getItineraryList(Trip trip) {
        List<Itinerary> itineraryList = trip.getItineraryList()
            .stream().filter(it -> it.getBaseTimeEntity().getDeletedAt() == null)
            .collect(Collectors.toList());

        if (itineraryList == null) {
            itineraryList = new ArrayList<>();
        }

        return itineraryList;
    }

    /**
     * itinerary 리스트 순서 재정의 및 여정 response List에 담아 반환하는 메소드
     *
     * @param itineraries
     * @return itineraryResponseList
     */
    private static void sortAgainItineraryOrder(List<Itinerary> itineraries) {
        Collections.sort(itineraries, Comparator.comparingInt(Itinerary::getItineraryOrder));

        for (int order = 1; order <= itineraries.size(); order++) {
            //리스트 순대로 order update
            Itinerary it = itineraries.get(order - 1);
            it.updateItineraryOrder(order);
        }
    }

    /**
     * itinerary (1개 이상) 삽입하는 메소드
     *
     * @param tripId
     * @param itineraryRequests
     * @return
     */
    @Transactional
    public List<ItineraryResponse> insertItineraries(
        Long tripId, Long userId, List<ItineraryRequest> itineraryRequests
    ) {
        /*
        1. tripid를 통한 trip 객체 찾기. (method : getTrip(tripId))
        2. 여정 테이블에서 모든 값 가져오기.
        3. 현 request 에서 entity로 변환하여 리스트에 추가.
        4. response 리스트 정렬. (오더 순서대로)
         */
        List<ItineraryResponse> itineraryResponseList = new ArrayList<>();
        Trip trip = getTrip(tripId);
        isMatchUserAndTrip(userId, trip);

        validateItineraryRequestOrder(itineraryRequests, trip);

        List<Itinerary> itineraryList = new ArrayList<>();
        for (ItineraryRequest ir : itineraryRequests) {
            itineraryList.add(ItineraryFactory.getItineraryEntity(trip, ir));
        }

        List<Itinerary> saveItineraryList = itineraryRepository.saveAll(itineraryList);
        if (saveItineraryList == null) {
            throw new ItineraryException(ITINERARY_SAVE_FAILED);
        }

        for (Itinerary it : saveItineraryList) {
            itineraryResponseList.add(
                    ItineraryResponseFactory.getItineraryResponse(it)
            );
        }
        ItineraryOrderUtil.sortItineraryResponseListByOrder(itineraryResponseList);
        return itineraryResponseList;
    }

    /**
     * 요청으로 들어온 itinerary 리스트의 순서가 맞는지 확인
     *
     * @param itineraryReqList
     * @param trip
     */
    private void validateItineraryRequestOrder(List<ItineraryRequest> itineraryReqList, Trip trip){
        List<Integer> orderList = getItineraryList(trip)
            .stream().map(Itinerary::getItineraryOrder)
            .collect(Collectors.toList());

        for (ItineraryRequest ir : itineraryReqList) {
            orderList.add(ir.getOrder());
        }
        ItineraryOrderUtil.validateItinerariesOrder(orderList);
    }

    /**
     * trip 객체로 연관된 itinerary response 리스트를 정렬된 여정 응답 리스트로 변환하여 반환하는 메소드
     *
     * @param trip
     * @return
     */
    public List<ItineraryResponse> getItineraryResponseListByTrip(Trip trip) {
        List<Itinerary> itineraryList = getItineraryList(trip);

        List<ItineraryResponse> itineraryResponseList = new ArrayList<>();
        for (Itinerary itinerary : itineraryList) {
            itineraryResponseList.add(
                ItineraryResponseFactory.getItineraryResponse(itinerary)
            );
        }

        ItineraryOrderUtil.sortItineraryResponseListByOrder(itineraryResponseList);
        return itineraryResponseList;
    }

    /**
     * tripService 를 이용해 trip 객체 반환하는 메소드
     *
     * @param tripId
     * @return
     */
    private Trip getTrip(Long tripId) {
        return tripService.getTripByTripId(tripId);
    }

    /**
     * trip 과 userId가 맞는지 검증
     * @param userId
     * @param trip
     */
    private static void isMatchUserAndTrip(Long userId, Trip trip) {
        if (trip.getUser().getUserId() != userId) {
            throw new TripException(NOT_MATCH_BETWEEN_USER_AND_TRIP);
        }
    }

    /**
     * 여정 아이디 리스트 가져와 itinerary 들 삭제하는 메소드
     *
     * @param tripId
     * @param deleteIdList (삭제된 여정들)
     * @return
     */
    @Transactional
    public List<ItineraryResponse> deleteItineraries(
        Long tripId, Long userId, List<Long> deleteIdList
    ) {
        Trip trip = getTrip(tripId);
        isMatchUserAndTrip(userId, trip);

        //1. 해당 트립에, 삭제할 아이디들이 일단 존재하는지 확인.
        for (Long id : deleteIdList) {
            Itinerary it = getItinerary(id);
            if (it.getTrip().getTripId() != tripId) {
                throw new ItineraryException(NO_ITINERARY);
            }
            if (it.getBaseTimeEntity().getDeletedAt() != null) {
                throw new ItineraryException(ITINERARY_ALREADY_DELETED);
            }
        }

        List<ItineraryResponse> deleteItList = new ArrayList<>();

        //2. 여정들 가져오기 (id만 적힌것들) -> delete 처리
        for (Long id : deleteIdList) {
            Itinerary it = getItinerary(id);
            it.delete();
            Itinerary saveIt = itineraryRepository.save(it);
            if (saveIt == null) {
                throw new ItineraryException(ITINERARY_SAVE_FAILED);
            }
            deleteItList.add(ItineraryResponse.fromEntity(saveIt));
        }

        //3. 남은 여정들의 순서 재정의 - 여정 순서대로 entity 정렬
        sortAgainItineraryOrder(getItineraryList(getTrip(tripId)));
        return deleteItList;
    }

    private Itinerary getItinerary(Long id) {
        Itinerary it = itineraryRepository
            .findById(id)
            .orElseThrow(() -> new ItineraryException(NO_ITINERARY));
        return it;
    }

    /**
     * itinerary (1개 이상) 수정하는 메소드
     *
     * @param tripId
     * @param itineraryUpdateRequests
     * @return
     */
    @Transactional(readOnly = false)
    public List<ItineraryResponse> updateItineraries(Long tripId,
        Long userId, List<ItineraryUpdateRequest> itineraryUpdateRequests) {

        if (itineraryUpdateRequests == null || itineraryUpdateRequests.isEmpty()) {
            throw new ItineraryException(EMPTY_ITINERARY);
        }

        Trip trip = getTrip(tripId);
        isMatchUserAndTrip(userId, trip);

        //trip에 있는 여정들인지 확인.
        Map<Long, Boolean> map = new HashMap<>();
        for (Itinerary it : trip.getItineraryList()) {
            map.put(it.getItineraryId(), true);
        }

        for (ItineraryUpdateRequest req : itineraryUpdateRequests) {
            Itinerary itinerary = getItinerary(req.getItineraryId());

            if (!map.containsKey(req.getItineraryId())) {
                throw new ItineraryException(ITINERARY_NOT_MATCH_TRIP);
            }
            itinerary.update(req);
            Itinerary saveIt = itineraryRepository.save(itinerary);
            if (saveIt == null) {
                throw new ItineraryException(ITINERARY_SAVE_FAILED);
            }
        }

        return getItineraryResponseListByTrip(trip);
    }

}
