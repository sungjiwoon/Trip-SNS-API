package com.fastcampus.toyproject.domain.reply.service;

import com.fastcampus.toyproject.domain.reply.dto.ReplyResponseDTO;
import com.fastcampus.toyproject.domain.reply.entity.Reply;

import com.fastcampus.toyproject.domain.reply.repository.ReplyRepository;
import com.fastcampus.toyproject.domain.trip.entity.Trip;

import com.fastcampus.toyproject.domain.trip.service.TripService;
import com.fastcampus.toyproject.domain.user.entity.User;
import com.fastcampus.toyproject.domain.user.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReplyServiceTest {

    @InjectMocks
    private ReplyService replyService;

    @Mock
    private ReplyRepository replyRepository;

    @Mock
    private TripService tripService;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddReply() {

        Long userId = 1L;
        Long tripId = 2L;
        String content = "Test reply content";

        User user = User.builder().userId(userId).build();
        Trip trip = Trip.builder().tripId(tripId).build();
        Reply reply = Reply.builder().user(user).trip(trip).content(content).build();

        when(userService.getUser(userId)).thenReturn(user);
        when(tripService.getTripByTripId(tripId)).thenReturn(trip);
        when(replyRepository.save(any())).thenReturn(reply);

        ReplyResponseDTO responseDTO = replyService.addReply(userId, tripId, content);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getUserId()).isEqualTo(userId);
        assertThat(responseDTO.getTripId()).isEqualTo(tripId);
        assertThat(responseDTO.getContent()).isEqualTo(content);
    }


}
