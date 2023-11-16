package com.fastcampus.toyproject.domain.reply.repository;

import com.fastcampus.toyproject.domain.reply.entity.Reply;
import com.fastcampus.toyproject.domain.trip.entity.Trip;
import com.fastcampus.toyproject.domain.trip.repository.TripRepository;
import com.fastcampus.toyproject.domain.user.entity.User;
import com.fastcampus.toyproject.domain.user.entity.Authority;
import com.fastcampus.toyproject.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;

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
            .build();
        userRepository.save(user);

        trip = Trip.builder()
            .tripName("Test Trip")
            .startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(1))
            .isDomestic(true)
            .user(user)
            .build();
        tripRepository.save(trip);

        Reply reply = Reply.builder()
            .user(user)
            .trip(trip)
            .content("댓글 내용 test")
            .build();
        replyRepository.save(reply);
    }

    @Test
    public void findAllByTripTripId_ReturnsReplyList() {
        List<Reply> replies = replyRepository.findAllByTripTripId(trip.getTripId());
        assertThat(replies).isNotEmpty();
        assertThat(replies).hasSize(1);
        Reply reply = replies.get(0);
        assertThat(reply.getUser()).isEqualTo(user);
        assertThat(reply.getTrip()).isEqualTo(trip);
        assertThat(reply.getContent()).isEqualTo("댓글 내용 test");
    }
}
