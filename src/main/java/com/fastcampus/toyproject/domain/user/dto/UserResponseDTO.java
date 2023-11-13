package com.fastcampus.toyproject.domain.user.dto;


import com.fastcampus.toyproject.domain.user.entity.Authority;
import com.fastcampus.toyproject.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private Long userId;
    private String email;
    private String name;
    private Authority authority;

    public static UserResponseDTO of(User user) {
        return UserResponseDTO.builder()
            .userId(user.getUserId())
            .email(user.getEmail())
            .name(user.getName())
            .authority(user.getAuthority())
            .build();

    }
}
