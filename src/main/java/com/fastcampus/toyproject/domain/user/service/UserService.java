package com.fastcampus.toyproject.domain.user.service;

import static com.fastcampus.toyproject.domain.user.exception.UserExceptionCode.EXSITED_EMAIL;
import static com.fastcampus.toyproject.domain.user.exception.UserExceptionCode.NO_SUCH_EMAIL;
import static com.fastcampus.toyproject.domain.user.exception.UserExceptionCode.WRONG_PASSWORD;

import com.fastcampus.toyproject.config.security.jwt.TokenProvider;
import com.fastcampus.toyproject.config.security.jwt.UserPrincipal;
import com.fastcampus.toyproject.domain.user.dto.LoginDto;
import com.fastcampus.toyproject.domain.user.dto.RefreshToken;
import com.fastcampus.toyproject.domain.user.dto.TokenDto;
import com.fastcampus.toyproject.domain.user.dto.TokenRequestDto;
import com.fastcampus.toyproject.domain.user.dto.UserRequestDTO;
import com.fastcampus.toyproject.domain.user.dto.UserResponseDTO;
import com.fastcampus.toyproject.domain.user.entity.User;
import com.fastcampus.toyproject.domain.user.exception.UserException;
import com.fastcampus.toyproject.domain.user.exception.UserExceptionCode;
import com.fastcampus.toyproject.domain.user.repository.RefreshTokenRepository;
import com.fastcampus.toyproject.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public UserResponseDTO insertUser(UserRequestDTO userRequestDTO) {

        if(userRepository.existsByEmail(userRequestDTO.getEmail())){
            throw new UserException(EXSITED_EMAIL);
        }

        User user = userRequestDTO.toUser(passwordEncoder);

        return UserResponseDTO.of(userRepository.save(user));
    }

    public User getUser(Long userId) {
        return userRepository.getReferenceById(userId);
    }

    public TokenDto login(LoginDto loginDto) {

        User user = userRepository.findByEmail(loginDto.getEmail())
            .orElseThrow(() -> new UserException(NO_SUCH_EMAIL));

        if(!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())){
            throw new UserException(WRONG_PASSWORD);
        }

        UserPrincipal authentication = new UserPrincipal(user);

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
            .key(authentication.getName())
            .value(tokenDto.getRefreshToken())
            .build();

        refreshTokenRepository.save(refreshToken);

        return tokenDto;
    }

    public TokenDto reissue(TokenRequestDto tokenRequestDto) {

        if(!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())){
            throw new RuntimeException("Refresh Token이 유효하지 않습니다.");
        }

        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
            .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자 입니다."));

        if(!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())){
            throw new RuntimeException("토큰의 유저정보가 일치하지 않습니다.");
        }

        TokenDto tokenDto = tokenProvider.generateTokenDto((UserPrincipal) authentication);

        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        return tokenDto;

    }
}
