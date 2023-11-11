package com.fastcampus.toyproject.domain.user.dto;

import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserRequestDTO {

    @NotNull(message = "이메일을 입력하셔야 합니다.")
    private String email;

    @NotNull(message = "비밀번호를 입력하셔야 합니다.")
    private String password;

}
