package com.fastcampus.toyproject.domain.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TokenRequestDto {

    private String accessToken;
    private String refreshToken;

}
