package com.fastcampus.toyproject.domain.user.repository;

import com.fastcampus.toyproject.domain.user.dto.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByKey(String key);

}
