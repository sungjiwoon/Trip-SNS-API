package com.fastcampus.toyproject.domain.user.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastcampus.toyproject.domain.user.dto.LoginDto;
import com.fastcampus.toyproject.domain.user.dto.TokenDto;
import com.fastcampus.toyproject.domain.user.dto.TokenRequestDto;
import com.fastcampus.toyproject.domain.user.dto.UserRequestDTO;
import com.fastcampus.toyproject.domain.user.dto.UserResponseDTO;
import com.fastcampus.toyproject.domain.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthController authController;

    private final UserRequestDTO userRequestDTO = new UserRequestDTO(
        "email@email.com",
        "passwrod",
        "name"
    );

    private final LoginDto loginDto = new LoginDto(
        "email@email.com",
        "passwrod"
    );

    @Test
    public void 회원가입() throws Exception {
        mockMvc.perform(post("/auth/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userRequestDTO)))
            .andDo(print())
            .andExpect(status().isOk());

    }

    @Test
    public void 회원가입_후_로그인() throws Exception {
        authController.insert(userRequestDTO);

        HttpServletResponse response = new MockHttpServletResponse();

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(loginDto))
                .requestAttr("response", response))
            .andDo(print())
            .andExpect(status().isOk());

    }

}