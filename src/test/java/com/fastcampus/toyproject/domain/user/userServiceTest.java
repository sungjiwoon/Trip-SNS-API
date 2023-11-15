package com.fastcampus.toyproject.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fastcampus.toyproject.config.security.jwt.TokenProvider;
import com.fastcampus.toyproject.domain.user.dto.UserRequestDTO;
import com.fastcampus.toyproject.domain.user.dto.UserResponseDTO;
import com.fastcampus.toyproject.domain.user.entity.Authority;
import com.fastcampus.toyproject.domain.user.entity.User;
import com.fastcampus.toyproject.domain.user.exception.UserException;
import com.fastcampus.toyproject.domain.user.repository.RefreshTokenRepository;
import com.fastcampus.toyproject.domain.user.repository.UserRepository;
import com.fastcampus.toyproject.domain.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DisplayName("사용자 서비스 테스트")
public class userServiceTest {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private UserRequestDTO userRequestDTO;

    private User user;
    @BeforeEach
    public void setUp(){

        userRequestDTO = new UserRequestDTO(
            "test@test.co.kr",
            "asdf",
            "테스트"
        );

        user = User.builder()
            .userId(1L)
            .name("테스트")
            .authority(Authority.ROLE_USER)
            .email("test@test.com")
            .build();

        userService = new UserService(
            userRepository,
            passwordEncoder,
            tokenProvider,
            refreshTokenRepository
        );

    }

    @Nested
    @DisplayName("회원가입 테스트")
    class insertUser{
        @Test
        @DisplayName("회원가입 성공")
        public void insertUser_success(){

            //given 주어진 변수들
            //when
            when(userRepository.save(any(User.class))).thenReturn(user);
            UserResponseDTO userResponseDTO = userService.insertUser(userRequestDTO);

            //then
            assertThat(userResponseDTO).isEqualTo(
                UserResponseDTO.builder()
                    .userId(1L)
                    .email("test@test.com")
                    .authority(Authority.ROLE_USER)
                    .name("테스트")
                    .build()
            );
        }


        @Test
        @DisplayName("중복 된 이메일")
        public void inserUser_existed(){

            //given 주어진 변수들
            //when
            when(userRepository.existsByEmail(any(String.class))).thenReturn(true);

            //then
            assertThatExceptionOfType(UserException.class)
                .isThrownBy(() -> userService.insertUser(userRequestDTO))
                .withMessage("중복 된 이메일이 있습니다.");

        }
    }

}
