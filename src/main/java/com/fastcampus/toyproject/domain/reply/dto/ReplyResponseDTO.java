package com.fastcampus.toyproject.domain.reply.dto;

import com.fastcampus.toyproject.domain.reply.entity.Reply;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class ReplyResponseDTO {
    private final Long replyId;
    private final Long userId;
    private final Long tripId;
    private final String content;



    public static ReplyResponseDTO fromEntity(Reply reply) {
        return ReplyResponseDTO.builder()
            .replyId(reply.getReplyId())
            .userId(reply.getUser().getUserId())
            .tripId(reply.getTrip().getTripId())
            .content(reply.getContent())
            .build();
    }
}
