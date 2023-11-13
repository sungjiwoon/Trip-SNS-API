package com.fastcampus.toyproject.domain.user.controller;

import com.fastcampus.toyproject.common.dto.ResponseDTO;
import com.fastcampus.toyproject.domain.user.dto.LoginDto;
import com.fastcampus.toyproject.domain.user.dto.TokenDto;
import com.fastcampus.toyproject.domain.user.dto.TokenRequestDto;
import com.fastcampus.toyproject.domain.user.dto.UserRequestDTO;
import com.fastcampus.toyproject.domain.user.dto.UserResponseDTO;
import com.fastcampus.toyproject.domain.user.entity.User;
import com.fastcampus.toyproject.domain.user.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseDTO<UserResponseDTO> insert(
        @Valid @RequestBody UserRequestDTO userRequestDTO) {

        UserResponseDTO user = userService.insertUser(userRequestDTO);
        return ResponseDTO.ok("회원 등록 성공!", user);
    }

    @PostMapping("/login")
    public ResponseDTO<TokenDto> login(@Valid @RequestBody LoginDto loginDto){
        return ResponseDTO.ok("로그인 성공", userService.login(loginDto));
    }

    @PostMapping("/reissue")
    public ResponseDTO<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto){
        return ResponseDTO.ok("Refesh 성공", userService.reissue(tokenRequestDto));
    }


}

