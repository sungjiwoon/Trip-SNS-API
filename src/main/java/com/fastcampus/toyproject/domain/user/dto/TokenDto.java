package com.fastcampus.toyproject.domain.user.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class TokenDto {

    private String grantType;

    private String accessToken;

    private Long accessTokenExpiresIn;

    private String refreshToken;

}
