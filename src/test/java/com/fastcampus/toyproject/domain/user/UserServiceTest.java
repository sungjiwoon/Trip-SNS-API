package com.fastcampus.toyproject.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fastcampus.toyproject.config.security.jwt.TokenProvider;
import com.fastcampus.toyproject.domain.user.dto.UserRequestDTO;
import com.fastcampus.toyproject.domain.user.dto.UserResponseDTO;
import com.fastcampus.toyproject.domain.user.entity.Authority;
import com.fastcampus.toyproject.domain.user.entity.User;
import com.fastcampus.toyproject.domain.user.exception.UserException;
import com.fastcampus.toyproject.domain.user.exception.UserExceptionCode;
import com.fastcampus.toyproject.domain.user.repository.RefreshTokenRepository;
import com.fastcampus.toyproject.domain.user.repository.UserRepository;
import com.fastcampus.toyproject.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DisplayName("사용자 서비스 테스트")
public class UserServiceTest {

    //테스트를 할 대상입니다.
    //UserService 테스트는 여러 객체를 주입받아야 하는데
    //회원의 비밀번호 암호화등 실제 작동해야 하는 부분은 Autowired로 주입
    //작동하지 않아야 하는 DB 부분 userRepository는 Mock으로 대체 합니다.
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Mock
    private UserRepository userRepository;

    //자주 사용하는 변수들은 다음과 같이 미리 선언하여 반복을 줄입니다.
    private UserRequestDTO userRequestDTO;
    private User user;

    //자주 사용할 변수들을 매 테스트 마다 만들도록 합니다.
    @BeforeEach
    public void setUp() {

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

        //userService는 여러 객체를 주입 받습니다.
        //위에서 userRepository를 mock이고 나머지는 autowired로 선언했습니다.
        //내가 테스트할 상황에 맞게 객체를 생성해 줍니다.
        userService = new UserService(
            userRepository,
            passwordEncoder,
            tokenProvider,
            refreshTokenRepository
        );

    }

    //비슷한 테스트 케이스별로 이너클래스로 나눕니다.
    @Nested
    @DisplayName("회원가입 테스트")
    class insertUser {

        @Test
        @DisplayName("회원가입 성공")
        public void insertUser_success() {

            //given 주어진 변수들
            //when
            //Mockito.when() 을 통해 가짜 UserRepository의 save메서드가 어떻게 작동할지 정의 합니다.
            //이메일 테스트는 통과, 저장은 user 리턴하도록 설정
            when(userRepository.existsByEmail(any(String.class))).thenReturn(false);
            when(userRepository.save(any(User.class))).thenReturn(user);
            UserResponseDTO userResponseDTO = userService.insertUser(userRequestDTO);

            //then
            //Assertions.assertThat 을 이용해 내가 의도한 결과가 나오는지 확인합니다.
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
        public void inserUser_existed() {

            //given 주어진 변수들
            //when
            //중복된 이메일 테스트이므로 이메일이 존재한다고 가정합니다.
            when(userRepository.existsByEmail(any(String.class))).thenReturn(true);

            //then
            //중복된 이메일이 있을경우 예외를 뱉어내므로 뱉어낸 예외가 올바른지
            //Assertions.assertThatExceptionOfType 으로 확인 합니다.
            assertThatExceptionOfType(UserException.class)
                .isThrownBy(() -> userService.insertUser(userRequestDTO))
                .withMessage("중복 된 이메일이 있습니다.")
                .extracting("errorCode") //errorCode 필드를 확인합니다.
                .isEqualTo(UserExceptionCode.EXSITED_EMAIL);


        }
    }

}
