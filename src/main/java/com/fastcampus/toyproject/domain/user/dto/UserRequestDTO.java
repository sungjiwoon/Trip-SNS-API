package com.fastcampus.toyproject.domain.user.dto;

import com.fastcampus.toyproject.common.BaseTimeEntity;
import com.fastcampus.toyproject.domain.user.entity.Authority;
import com.fastcampus.toyproject.domain.user.entity.User;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
public class UserRequestDTO {

    @NotNull(message = "이메일을 입력하셔야 합니다.")
    private String email;

    @NotNull(message = "비밀번호를 입력하셔야 합니다.")
    private String password;

    @NotNull(message = "이름을 입력하셔야 합니다.")
    private String name;

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
            .email(email)
            .password(passwordEncoder.encode(password))
            .name(name)
            .authority(Authority.ROLE_USER)
            .baseTimeEntity(new BaseTimeEntity())
            .build();
    }
}
