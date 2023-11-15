package com.fastcampus.toyproject.domain.reply.controller;

import com.fastcampus.toyproject.common.dto.ResponseDTO;
import com.fastcampus.toyproject.config.security.jwt.UserPrincipal;
import com.fastcampus.toyproject.domain.reply.dto.ReplyRequestDTO;
import com.fastcampus.toyproject.domain.reply.dto.ReplyResponseDTO;
import com.fastcampus.toyproject.domain.reply.service.ReplyService;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trip/{tripId}/reply")
public class ReplyController {

    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping
    public ResponseDTO<ReplyResponseDTO> addReply(
        @PathVariable Long tripId,
        final UserPrincipal userPrincipal,
        @RequestBody ReplyRequestDTO request
    ) {
        Long userId = userPrincipal.getUserId();
        ReplyResponseDTO reply = replyService.addReply(userId, tripId, request.getContent());
        return ResponseDTO.ok("댓글이 등록되었습니다.", reply);
    }

    @GetMapping
    public ResponseDTO<List<ReplyResponseDTO>> getAllRepliesByTripId(@PathVariable Long tripId) {
        List<ReplyResponseDTO> replies = replyService.getAllRepliesByTripId(tripId);
        return ResponseDTO.ok("댓글 성공적으로 조회되었습니다.", replies);
    }

    @PutMapping("/{replyId}")
    public ResponseDTO<ReplyResponseDTO> updateReply(
        @PathVariable Long tripId,
        @PathVariable Long replyId,
        @RequestBody ReplyRequestDTO request,
        final UserPrincipal userPrincipal
    ) {
        ReplyResponseDTO updatedReply = replyService.updateReply(userPrincipal.getUserId(), replyId, request.getContent());
        return ResponseDTO.ok("댓글이 성공적으로 수정되었습니다.", updatedReply);
    }

    @DeleteMapping("/{replyId}")
    public ResponseDTO<Void> deleteReply(
        @PathVariable Long tripId,
        @PathVariable Long replyId,
        final UserPrincipal userPrincipal
    ) {
        replyService.deleteReply(userPrincipal.getUserId(), replyId);
        return ResponseDTO.ok("댓글이 성공적으로 삭제되었습니다.");
    }
}



