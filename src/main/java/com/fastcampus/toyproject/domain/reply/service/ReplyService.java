package com.fastcampus.toyproject.domain.reply.service;


import static com.fastcampus.toyproject.domain.trip.exception.TripExceptionCode.NO_SUCH_TRIP;


import com.fastcampus.toyproject.common.BaseTimeEntity;
import com.fastcampus.toyproject.domain.reply.dto.ReplyResponseDTO;
import com.fastcampus.toyproject.domain.reply.entity.Reply;
import com.fastcampus.toyproject.domain.reply.exception.ReplyException;
import com.fastcampus.toyproject.domain.reply.exception.ReplyExceptionCode;
import com.fastcampus.toyproject.domain.reply.repository.ReplyRepository;
import com.fastcampus.toyproject.domain.trip.entity.Trip;
import com.fastcampus.toyproject.domain.trip.exception.TripException;
import com.fastcampus.toyproject.domain.trip.service.TripService;
import com.fastcampus.toyproject.domain.user.entity.User;
import com.fastcampus.toyproject.domain.user.service.UserService;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final TripService tripService;
    private final UserService userService;

    public ReplyService(ReplyRepository replyRepository, TripService tripService,
        UserService userService) {
        this.replyRepository = replyRepository;
        this.tripService = tripService;
        this.userService = userService;
    }

    public static ReplyResponseDTO fromEntity(Reply reply) {

        return ReplyResponseDTO.builder()
            .replyId(reply.getReplyId())
            .userId(reply.getUser().getUserId())
            .tripId(reply.getTrip().getTripId())
            .content(reply.getContent())
            .build();
    }

    public ReplyResponseDTO addReply(Long userId, Long tripId, String content) {
        User user = userService.getUser(userId);
        Trip trip = tripService.getTripByTripId(tripId);

        if (trip == null) {
            throw new TripException(NO_SUCH_TRIP);
        }

        Reply newReply = Reply.builder()
            .user(user)
            .trip(trip)
            .content(content)
            .baseTimeEntity(new BaseTimeEntity())
            .build();

        Reply savedReply = replyRepository.save(newReply);
        return ReplyResponseDTO.fromEntity(savedReply);
    }

    @Transactional(readOnly = true)
    public List<ReplyResponseDTO> getAllRepliesByTripId(Long tripId) {
        List<Reply> replies = replyRepository.findAllByTripTripId(tripId);
        return replies.stream()
            .map(ReplyResponseDTO::fromEntity)
            .collect(Collectors.toList());
    }

    public ReplyResponseDTO updateReply(Long userId, Long replyId, String content) {
        Reply reply = replyRepository.findById(replyId)
            .orElseThrow(() -> new ReplyException(ReplyExceptionCode.REPLY_NOT_FOUND));

        if (reply.getUser() == null || !reply.getUser().getUserId().equals(userId)) {
            throw new ReplyException(ReplyExceptionCode.INVALID_REPLY_OPERATION);
        }

        reply.setContent(content);
        Reply updatedReply = replyRepository.save(reply);
        return ReplyResponseDTO.fromEntity(updatedReply);
    }

    public void deleteReply(Long userId, Long replyId) {
        Reply reply = replyRepository.findById(replyId)
            .orElseThrow(() -> new ReplyException(ReplyExceptionCode.REPLY_NOT_FOUND));

        if (reply.getUser() == null || !reply.getUser().getUserId().equals(userId)) {
            throw new ReplyException(ReplyExceptionCode.INVALID_REPLY_OPERATION);
        }

        if (reply.getBaseTimeEntity().getDeletedAt() == null) {
            reply.getBaseTimeEntity().delete(LocalDateTime.now());
            replyRepository.save(reply);
        }
    }

}
