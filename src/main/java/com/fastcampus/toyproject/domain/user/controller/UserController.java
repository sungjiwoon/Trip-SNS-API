package com.fastcampus.toyproject.domain.user.controller;

import com.fastcampus.toyproject.common.dto.ResponseDTO;
import com.fastcampus.toyproject.domain.user.dto.UserRequestDTO;
import com.fastcampus.toyproject.domain.user.dto.UserResponseDTO;
import com.fastcampus.toyproject.domain.user.entity.User;
import com.fastcampus.toyproject.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseDTO<UserResponseDTO> insert(@RequestBody UserRequestDTO userRequestDTO) {
        User savedUser = userService.insertUser(userRequestDTO);
        UserResponseDTO responseData = new UserResponseDTO(savedUser.getUserId(),
            savedUser.getEmail());
        return ResponseDTO.ok("회원 등록 성공!", responseData);
    }


}

