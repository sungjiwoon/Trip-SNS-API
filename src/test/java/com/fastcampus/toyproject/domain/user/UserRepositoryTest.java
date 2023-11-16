package com.fastcampus.toyproject.domain.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.fastcampus.toyproject.domain.user.entity.Authority;
import com.fastcampus.toyproject.domain.user.entity.User;
import com.fastcampus.toyproject.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DisplayName("사용자 리포지토리 테스트")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {

        user = User.builder()
            .password("testpassword")
            .name("테스트")
            .authority(Authority.ROLE_USER)
            .email("test@test.com")
            .build();

    }

    @Test
    @DisplayName("회원 저장")
    public void save() {

        //given
        //System.out.println(user);
        //User(userId=null, email=test@test.com, password=testpassword, name=테스트, authority=ROLE_USER,
        //저장전의 user는 UserId가 null입니다.

        //when
        User savedUser = userRepository.save(user);

        //System.out.println(savedUser);
        //User(userId=6, email=test@test.com, password=testpassword, name=테스트, authority=ROLE_USER
        //저장 후의 userId 6이 생성 되었습니다!
        //JPA가 user객체에 userId를 넣어준것으로 정상 작동함을 알 수 있습니다.
        //then
        assertThat(user).isEqualTo(savedUser)
            .extracting("userId").isNotNull();


    }

}
