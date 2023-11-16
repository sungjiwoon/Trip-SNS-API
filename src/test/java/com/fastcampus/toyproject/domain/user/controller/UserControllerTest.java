package com.fastcampus.toyproject.domain.user.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastcampus.toyproject.config.security.jwt.UserPrincipal;
import com.fastcampus.toyproject.domain.user.entity.Authority;
import com.fastcampus.toyproject.domain.user.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("UserController 테스트")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UserController userController;

    private final User user = User.builder()
        .userId(1L)
        .name("테스트")
        .authority(Authority.ROLE_USER)
        .email("test@test.com")
        .build();
    private final UserPrincipal userPrincipal = new UserPrincipal(user);
    Authentication authentication = new UsernamePasswordAuthenticationToken(
        userPrincipal, null, userPrincipal.getAuthorities()
    );

    @DisplayName("사용자 여행 검색")
    @WithMockUser
//    @Test
    public void getAllTrip_success() throws Exception {
        mockMvc.perform(get("/api/user/trip-list")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());

    }
}