package com.fastcampus.toyproject.domain.liketrip.repository;

import com.fastcampus.toyproject.common.BaseTimeEntity;
import com.fastcampus.toyproject.domain.liketrip.entity.LikeTrip;
import com.fastcampus.toyproject.domain.trip.entity.Trip;
import com.fastcampus.toyproject.domain.trip.repository.TripRepository;
import com.fastcampus.toyproject.domain.user.entity.Authority;
import com.fastcampus.toyproject.domain.user.entity.User;
import com.fastcampus.toyproject.domain.user.repository.UserRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LikeTripRepositoryTest {

    @Autowired
    private LikeTripRepository likeTripRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TripRepository tripRepository;

    private User user;
    private Trip trip;

    @BeforeEach
    public void setUp() {

        user = User.builder()
            .email("user@example.com")
            .password("password")
            .name("Test User")
            .authority(Authority.ROLE_USER)
            .baseTimeEntity(new BaseTimeEntity())
            .build();
        userRepository.save(user);

        trip = Trip.builder()
            .tripName("Test Trip")
            .startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(1))
            .isDomestic(true)
            .user(user)
            .baseTimeEntity(new BaseTimeEntity())
            .build();
        tripRepository.save(trip);

        LikeTrip likeTrip = new LikeTrip(user, trip, true);
        likeTripRepository.save(likeTrip);
    }

    @Test
    public void findByUserUserIdAndTripTripId_ReturnsLikeTrip() {
        Optional<LikeTrip> found = likeTripRepository.findByUserUserIdAndTripTripId(
            user.getUserId(), trip.getTripId());
        assertThat(found).isPresent();
        found.ifPresent(like -> {
            assertThat(like.getUser()).isEqualTo(user);
            assertThat(like.getTrip()).isEqualTo(trip);
            assertThat(like.getIsLike()).isTrue();
        });
    }
}
