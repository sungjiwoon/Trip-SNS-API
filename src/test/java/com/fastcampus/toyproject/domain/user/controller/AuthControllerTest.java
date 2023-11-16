package com.fastcampus.toyproject.domain.user.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastcampus.toyproject.domain.user.dto.LoginDto;
import com.fastcampus.toyproject.domain.user.dto.TokenDto;
import com.fastcampus.toyproject.domain.user.dto.UserRequestDTO;
import com.fastcampus.toyproject.domain.user.dto.UserResponseDTO;
import com.fastcampus.toyproject.domain.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

class AuthControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

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

//        when(userService.insertUser(userRequestDTO))
//            .thenReturn(mock(UserResponseDTO.class));
//
//        mockMvc.perform(post("/signup")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsBytes(userRequestDTO)))
//            .andDo(print())
//            .andExpect(status().isOk());

    }

    @Test
    public void 로그인() throws Exception {

//        when(userService.login(loginDto))
//            .thenReturn(mock(TokenDto.class));
//
//        mockMvc.perform(post("/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsBytes(userRequestDTO)))
//            .andDo(print())
//            .andExpect(status().isOk());

    }
}