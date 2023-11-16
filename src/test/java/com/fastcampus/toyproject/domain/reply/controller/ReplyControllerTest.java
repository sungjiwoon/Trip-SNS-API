package com.fastcampus.toyproject.domain.reply.controller;

import com.fastcampus.toyproject.config.security.jwt.UserPrincipal;
import com.fastcampus.toyproject.domain.reply.dto.ReplyRequestDTO;
import com.fastcampus.toyproject.domain.reply.dto.ReplyResponseDTO;
import com.fastcampus.toyproject.domain.reply.service.ReplyService;
import com.fastcampus.toyproject.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;


import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
public class ReplyControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReplyService replyService;

    @InjectMocks
    private ReplyController replyController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(replyController).build();
    }

    private UserPrincipal createUserPrincipal() {

        User user = new User();
        return new UserPrincipal(user);
    }

    @Test
    public void testAddReply() throws Exception {
        // Arrange
        ReplyRequestDTO request = new ReplyRequestDTO();
        request.setContent("테스트 댓글");

        UserPrincipal userPrincipal = createUserPrincipal();
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(userPrincipal, null,
                userPrincipal.getAuthorities()));

        ReplyResponseDTO expectedResponse = ReplyResponseDTO.builder()
            .replyId(123L)
            .userId(userPrincipal.getUserId())
            .tripId(456L)
            .content("테스트 댓글")
            .build();

        given(replyService.addReply(userPrincipal.getUserId(), 456L,
            request.getContent())).willReturn(expectedResponse);

        String jsonRequest = "{\"content\": \"테스트 댓글\"}";

        mockMvc.perform(post("/trip/456/reply")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.replyId").value(expectedResponse.getReplyId()));
    }
}
