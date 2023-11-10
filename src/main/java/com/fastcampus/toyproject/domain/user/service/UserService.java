package com.fastcampus.toyproject.domain.user.service;

import com.fastcampus.toyproject.domain.user.dto.UserRequestDTO;
import com.fastcampus.toyproject.domain.user.entity.User;
import com.fastcampus.toyproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository user;

    public User insertMember(UserRequestDTO memberRequestDTO) {
        User user = User.builder().email(memberRequestDTO.getEmail()).build();
        return this.user.save(user);
    }

}
