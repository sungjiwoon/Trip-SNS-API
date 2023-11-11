package com.fastcampus.toyproject.domain.user.service;

import com.fastcampus.toyproject.domain.user.dto.UserRequestDTO;
import com.fastcampus.toyproject.domain.user.entity.User;
import com.fastcampus.toyproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User insertUser(UserRequestDTO userRequestDTO) {
        User user = User.builder()
                .email(userRequestDTO.getEmail())
                .password(userRequestDTO.getPassword())
                .authority("ROLE_USER")
                .build();
        User saveUser = userRepository.save(user);
        if (saveUser != null) {
            return saveUser;
        }
        return null; //이거 수정 예정!!
    }

    public User getUser(Long userId) {
        return userRepository.getReferenceById(userId);
    }

}
