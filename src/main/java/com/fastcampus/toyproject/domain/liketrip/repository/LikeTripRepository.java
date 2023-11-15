package com.fastcampus.toyproject.domain.liketrip.repository;

import com.fastcampus.toyproject.domain.liketrip.entity.LikeTrip;
import com.fastcampus.toyproject.domain.trip.entity.Trip;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeTripRepository extends JpaRepository<LikeTrip, Long> {
    Optional<LikeTrip> findByUserUserIdAndTripTripId(Long userId, Long tripId);
    @Query("SELECT t FROM LikeTrip t WHERE t.user.userId = :userId")
    Optional<List<LikeTrip>> findAllByUserId(@Param("userId")Long userId);
}
