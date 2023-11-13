package com.fastcampus.toyproject.domain.liketrip.repository;

import com.fastcampus.toyproject.domain.liketrip.entity.LikeTrip;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeTripRepository extends JpaRepository<LikeTrip, Long> {
    Optional<LikeTrip> findByUserUserIdAndTripTripId(Long userId, Long tripId);
}
