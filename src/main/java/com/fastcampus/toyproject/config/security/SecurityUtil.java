package com.fastcampus.toyproject.config.security;

import static com.fastcampus.toyproject.config.security.exception.SecurityExcpetionCode.INTENAL_SERVER_ERROR;

import com.fastcampus.toyproject.config.security.exception.CustomSecurityException;
import com.fastcampus.toyproject.config.security.exception.SecurityExcpetionCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    private SecurityUtil() {
    };

    public static Long getCurrentMemberId() {

        final Authentication authentication = SecurityContextHolder.getContext()
            .getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new CustomSecurityException(INTENAL_SERVER_ERROR);
        }

        return Long.parseLong(authentication.getName());

    }

}
